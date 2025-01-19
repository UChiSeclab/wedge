from pathlib import Path
import re
import sys

def add_line_profiler_decorator_to_python_file(ori_file_path: Path, decorated_file_path: Path):
    """
    Adds the @profile decorator to all function definitions in a Python file, including nested functions.

    Parameters:
        ori_file_path (Path): The path to the original Python file.
        decorated_file_path (Path): The path to save the decorated Python file.
    """
    # Regular expression to match function definitions
    func_def_pattern = re.compile(r'^\s*def\s+\w+\s*\(.*\)\s*:')

    # Read the original file
    with ori_file_path.open('r') as ori_file:
        lines = ori_file.readlines()

    decorated_lines = []
    for line in lines:
        # Check if the current line is a function definition
        if func_def_pattern.match(line):
            # Add @profile decorator before the function definition
            # Maintain the same level of indentation as the function definition
            indentation = line[:len(line) - len(line.lstrip())]
            decorated_lines.append(f"{indentation}@profile\n")

        # Append the original line
        decorated_lines.append(line)

    # Write the decorated file
    with decorated_file_path.open('w') as decorated_file:
        decorated_file.writelines(decorated_lines)

def add_mem_profiler_decorator_to_python_file(ori_file_path: Path, decorated_file_path: Path):
    """
    Adds the @profile decorator to all function definitions in a Python file, including nested functions.

    Parameters:
        ori_file_path (Path): The path to the original Python file.
        decorated_file_path (Path): The path to save the decorated Python file.
    """
    # Regular expression to match function definitions
    func_def_pattern = re.compile(r'^\s*def\s+\w+\s*\(.*\)\s*:')

    # Read the original file
    with ori_file_path.open('r') as ori_file:
        lines = ori_file.readlines()

    decorated_lines = []
    for line in lines:
        # Check if the current line is a function definition
        if func_def_pattern.match(line):
            # Add @profile decorator before the function definition
            # Maintain the same level of indentation as the function definition
            indentation = line[:len(line) - len(line.lstrip())]
            decorated_lines.append(f"{indentation}@profile(precision=1)\n")

        # Append the original line
        decorated_lines.append(line)

    # Write the decorated file
    with decorated_file_path.open('w') as decorated_file:
        decorated_file.writelines(decorated_lines)

@DeprecationWarning
def old_add_mem_profiler_decorator_to_python_file(ori_file_path: Path, decorated_file_path: Path):
    """
    Adds the @profile(stream=profile_stream, precision=PROFILE_PRECISION) decorator to all function definitions in a Python file, including nested functions.

    Parameters:
        ori_file_path (Path): The path to the original Python file.
        decorated_file_path (Path): The path to save the decorated Python file.
    """

    memory_profiler_prompt = r"""
def parse_profile_table(profile_table: str):
    table = {"filename": None, "rows": []}
    for line in profile_table.strip().split("\n"):
        if line.startswith("Filename:"):
            table["filename"] = line.split(": ")[1]
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

def print_averaged_results(profile_log: str, precision: int = 1):
    tables = [parse_profile_table(table) for table in profile_log.split("\n\n\n")]
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

    output_file = __file__.replace('.py', '_mprof.txt')
    stream = open(output_file, 'w')
    template = '{0:>6} {1:>12} {2:>12}  {3:>10}   {4:<}'

    for filename, lines in averaged_table.items():
        header = template.format('Line #', 'Mem usage', 'Increment', 'Occurrences', 'Line Contents')

        stream.write(u'Filename: ' + filename + '\n\n')
        stream.write(header + u'\n')
        stream.write(u'=' * len(header) + '\n')

        all_lines = linecache.getlines(filename)

        float_format = u'{0}.{1}f'.format(precision + 4, precision)
        template_mem = u'{0:' + float_format + '} MiB'

        for lineno, mem_values in lines.items():
            # TODO: should average the rest or not?
            # mem_values = [(50.1, 0.0, 4), (51.1, 0.0, 6), ()]
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

print_averaged_results(profile_stream.getvalue(), precision=PROFILE_PRECISION)
"""

    memory_profiler_pkgs = r"""
from collections import defaultdict, deque
from memory_profiler import profile
import io
profile_stream = io.StringIO()
PROFILE_PRECISION = 1
import re
import sys
import linecache
"""
    # Regular expression to match function definitions
    func_def_pattern = re.compile(r'^\s*def\s+\w+\s*\(.*\)\s*:')

    # Read the original file
    with ori_file_path.open('r') as ori_file:
        lines = ori_file.readlines()

    decorated_lines = []
    for line in lines:
        # Check if the current line is a function definition
        if func_def_pattern.match(line):
            # Add @profile decorator before the function definition
            # Maintain the same level of indentation as the function definition
            indentation = line[:len(line) - len(line.lstrip())]
            decorated_lines.append(f"{indentation}@profile(stream=profile_stream, precision=PROFILE_PRECISION)\n")

        # Append the original line
        decorated_lines.append(line)

    # Write the decorated file
    with decorated_file_path.open('w') as decorated_file:
        decorated_file.write(memory_profiler_pkgs)
        decorated_file.write('\n')
        decorated_file.writelines(decorated_lines)
        decorated_file.write(memory_profiler_prompt)

if __name__ == '__main__':
    ori_file_path = Path(sys.argv[1])
    decorated_file_path = Path(sys.argv[2])
    add_line_profiler_decorator_to_python_file(ori_file_path, decorated_file_path)