from line_profiler import load_stats, LineProfiler
import pickle
import sys
from typing import List, Dict
from pathlib import Path
import re
import linecache
from collections import defaultdict
import subprocess

from run import parse_instruction_count_with_retry
from evaluate.usefulness.prompt_exp.calculate_memory_usage import calculate_memory_usage, calculate_runtime, report_max_memory_usage

def merge_line_profiles(profile_files: List[Path], output_file: Path):
    merged_timings = {}
    merged_unit = None

    # Load and merge profiling data
    for file in profile_files: # .lprof files
        if not file.exists():
            # profiling likely timed out
            print(f"[Warning] Profile file {file} not found, skipping")
            continue
        stats = load_stats(file)
        if merged_unit is None:
            merged_unit = stats.unit  # Set timer unit from the first file
        for func_key, timing_data in stats.timings.items():
            if func_key not in merged_timings:
                merged_timings[func_key] = {}
            for line_no, hits, total_time in timing_data:
                if line_no not in merged_timings[func_key]:
                    merged_timings[func_key][line_no] = [0, 0]  # [hits, total_time]
                merged_timings[func_key][line_no][0] += hits
                merged_timings[func_key][line_no][1] += total_time

    if not merged_timings:
        print("[Warning] No profile data found")
        output_file.write_text("Line profiler data not available")
        return

    # Convert merged data into a LineStats-compatible format
    final_timings = {}
    for func_key, line_data in merged_timings.items():
        final_timings[func_key] = []
        for line_no, (hits, total_time) in line_data.items():
            final_timings[func_key].append((line_no, hits, total_time))

    # Create a LineStats object using LineProfiler
    profiler = LineProfiler()
    stats = profiler.get_stats()
    stats.timings = final_timings
    stats.unit = merged_unit

    lprof_output_file = output_file.with_suffix('.lprof')
    # Save the LineStats object
    with open(lprof_output_file, 'wb') as f:
        pickle.dump(stats, f)
    print(f"Merged profiling data saved to {lprof_output_file}")
    # display the results
    subprocess.run(["python", "-m", "line_profiler", lprof_output_file.name], stdout=open(output_file, 'w'), cwd=output_file.parent)

def parse_profile_table(profile_table: str):
    table = {"filename": None, "rows": []}
    for line in profile_table.strip().split("\n"):
        if line.startswith("Filename:"):
            table["filename"] = line.split(": ")[1].split('/')[-1]
        elif re.match(r"^\s*\d+", line):
            parts = re.split(r"\s{2,}", line.strip(), maxsplit=4)
            if len(parts) == 5 and "iB" in parts[1] and "iB" in parts[2]:
                table["rows"].append({
                    "line": int(parts[0]),
                    "mem_usage": parts[1],
                    "increment": parts[2],
                    "occurrences": int(parts[3]),
                    "line_contents": parts[4],
                })
            else:
                parts = re.split(r"\s{2,}", line.strip(), maxsplit=1)
                table["rows"].append({
                    "line": int(parts[0]),
                    "line_contents": parts[1] if len(parts) == 2 else "",
                })
    return table

def merge_mem_profiles(profile_files: List[Path], output_file: Path, precision: int = 1):
    tables = []
    for profile_file in profile_files:
        if not profile_file.exists():
            # profiling likely timed out
            print(f"[Warning] Profile file {profile_file} not found, skipping")
            continue
        sub_tables = [parse_profile_table(profile_table) for profile_table in profile_file.read_text().split("\n\n\n")]
        tables.extend(sub_tables)
    
    if not tables:
        print("[Warning] No profile data found")
        output_file.write_text("Memory profiler data not available")
        return
    averaged_table = defaultdict(lambda: defaultdict(list))

    for table in tables:
        filename = table["filename"]
        for row in table["rows"]:
            line = row["line"]
            if "mem_usage" in row:
                mem_usage = float(row["mem_usage"].split()[0])
                increment = float(row["increment"].split()[0])
                occurrences = row["occurrences"]
                averaged_table[filename][line].append((mem_usage, increment, occurrences))
            else:
                averaged_table[filename][line].append(tuple())

    stream = open(output_file, 'w')
    template = '{0:>6} {1:>12} {2:>12}  {3:>10}   {4:<}'

    for filename, lines in averaged_table.items():
        header = template.format('Line #', 'Mem usage', 'Increment', 'Occurrences', 'Line Contents')

        stream.write(u'Filename: ' + filename + '\n\n')
        stream.write(header + u'\n')
        stream.write(u'=' * len(header) + '\n')

        all_lines = linecache.getlines((profile_files[0].parent / filename).absolute().as_posix())

        float_format = u'{0}.{1}f'.format(precision + 4, precision)
        template_mem = u'{0:' + float_format + '} MiB'

        for lineno, mem_values in lines.items():
            # TODO: should average the rest or not?
            # mem_values = [(50.1, 0.0, 4), (51.1, 0.0, 6), ()]

            # Fix: line number is not found in the file (for code outside functions)
            if lineno > len(all_lines):
                continue

            if any([len(m) == 0 for m in mem_values]):
                tmp = template.format(lineno, "", "", "", all_lines[lineno - 1])
            else:
                mem_usage_sum = sum(m[0] for m in mem_values)
                increment_sum = sum(m[1] for m in mem_values)
                occurrences_sum = sum(m[2] for m in mem_values)
                count = len(mem_values)

                avg_mem_usage = mem_usage_sum / count
                avg_increment = increment_sum / count
                avg_occurrences = occurrences_sum / count

                avg_mem_usage_str = template_mem.format(avg_mem_usage)
                avg_increment_str = template_mem.format(avg_increment)

                tmp = template.format(lineno, avg_mem_usage_str, avg_increment_str, int(avg_occurrences), all_lines[lineno - 1])
            stream.write(tmp)

    stream.close()

def merge_instruction_cnt_profiles(profile_files: List[Path]) -> int:
    total_instructions = 0
    for file in profile_files:
        total_instructions += parse_instruction_count_with_retry(file)
    return total_instructions

def merge_script_profiles(profile_files: List[Path]) -> Dict[str, str]:
    total_memory_usage = 0
    total_execution_time = 0
    max_peak_memory_usage = 0
    for file in profile_files:
        memory_usage = calculate_memory_usage(file)
        execution_time = calculate_runtime(file)
        peak_memory_usage = report_max_memory_usage(file)
        total_memory_usage += memory_usage
        total_execution_time += execution_time
        max_peak_memory_usage = max(max_peak_memory_usage, peak_memory_usage)

    return {
        "total_memory_usage": total_memory_usage,
        "total_execution_time": total_execution_time,
        "max_peak_memory_usage": max_peak_memory_usage
    }

if __name__ == "__main__":
    # List of profiling files
    profile_files = [Path(file) for file in sys.argv[1:-1]]
    assert len(profile_files) > 1, "At least two profiling files are required for merging"
    output_file = Path(sys.argv[-1])

    merge_mem_profiles(profile_files, output_file)
