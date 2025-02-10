from typing import List, Dict, Tuple
from pathlib import Path
import subprocess
import multiprocessing
import shutil

from config import config
from utils import check_same_output
from evaluate.usefulness.prompt_exp.decorate import add_line_profiler_decorator_to_python_file, add_mem_profiler_decorator_to_python_file
from evaluate.usefulness.prompt_exp.merge_stats import merge_instruction_cnt_profiles, merge_mem_profiles, merge_line_profiles, merge_script_profiles

CODE_PERF_SCRIPT_PATH = Path(config["root_dir"]) / "scripts_hub/feedback_collect/code_perf.sh"

def script_profiler_solution(solution_file: Path, input_file: Path, work_dir: Path, profile_file: Path, timeout: int = 10):
    command = [
        CODE_PERF_SCRIPT_PATH.absolute().as_posix(),
        solution_file.absolute().as_posix(),
        profile_file.absolute().as_posix(),
        str(timeout),
        input_file.absolute().as_posix(),
    ]
    subprocess.run(command, check=True, capture_output=True, text=True)

def line_profiler_solution(solution_file: Path, input_file: Path, work_dir: Path, profile_file: Path, timeout: int = 10):
    """decorate and run the solution with line_profiler."""
    decorated_solution_file = work_dir / f"{solution_file.stem}_line_decorated.py"
    add_line_profiler_decorator_to_python_file(solution_file, decorated_solution_file)
    command = ["timeout", str(timeout)]
    command.extend(["kernprof", "-l", "-o", profile_file.absolute().as_posix(), decorated_solution_file.name])
    subprocess.run(command, cwd=work_dir, stdin=input_file.open('r'))

def mem_profiler_solution(solution_file: Path, input_file: Path, work_dir: Path, profile_file: Path, timeout: int = 10):
    """run the solution with memory_profiler."""
    decorated_solution_file = work_dir / f"{solution_file.stem}_mem_decorated.py"
    add_mem_profiler_decorator_to_python_file(solution_file, decorated_solution_file)
    profile_file.unlink(missing_ok=True)

    command = ["python", "-m", "memory_profiler", "-o", profile_file.absolute().as_posix(), decorated_solution_file.name]
    try:
        # execute the solution and catch potential timeout errors
        subprocess.run(command, cwd=work_dir, stdin=input_file.open('r'), timeout=timeout)
    except subprocess.TimeoutExpired:
        print(f"Timeout expired for {decorated_solution_file}")
        profile_file.write_text("Timeout expired")

def instruction_cnt_solution(solution_file: Path, input_file: Path, work_dir: Path, profile_file: Path, timeout: int = 10, record_perf: bool = False) -> Path:
    """run the solution and count the number of instructions."""
    command = ["timeout", str(timeout)]
    if record_perf:
        command.extend(["perf", "stat", "-e", "instructions:u", "-x", "<PERF_SEP>", "-o", profile_file.absolute().as_posix()])
    command.extend(["python", solution_file.name])
    output_file = work_dir / "output.txt"
    try:
        # execute the solution and catch all errors, including compilation error
        subprocess.run(command, cwd=work_dir, stdin=input_file.open('r'), stdout=output_file.open('w'), stderr=subprocess.STDOUT, check=True)
    except subprocess.CalledProcessError as e:
        print(f"Error while running solution: {solution_file} with error: {e}")
        output_file = None

    return output_file

def check_output_correctness(output_file: Path, gt_output_file: Path) -> bool:
    if not output_file:
        return False
    if not output_file.exists():
        print(f"[Warning] Output file {output_file} does not exist")
        return False

    return check_same_output(output_file.read_text().split(), gt_output_file.read_text().split())

def run_solution_one_input(solution_file: Path, input_file: Path, gt_output_file: Path, work_dir: Path, timeout: int = 10, early_stop: bool = True, include_instruction_cnt: bool = False) -> Tuple[Path, Path, Path, Path, bool]:
    """run the solution and collect correctness and performance statistics \
        (line_profiler, memory_profiler, instruction_cnt)."""
    # copy the solution file to the working directory
    work_dir.mkdir(exist_ok=True)
    work_dir_solution_file = work_dir / solution_file.name
    shutil.copy(solution_file, work_dir_solution_file)

    exec_status_file = work_dir / "exec_status.txt"
    if exec_status_file.exists() and exec_status_file.read_text() == "incorrect":
        return None, None, None, None, False

    instruction_cnt_profile_file = work_dir / f"{solution_file.stem}.perf_stat"
    script_profile_file = work_dir / f"{solution_file.stem}_script_profile.txt"
    line_profiler_profile_file = work_dir / f"{solution_file.stem}_line_decorated.lprof"
    mem_profiler_profile_file = work_dir / f"{solution_file.stem}_mem_decorated_mprof.txt"

    assert early_stop, "non early stop is not supported yet"

    if include_instruction_cnt:
        if not instruction_cnt_profile_file.exists() or instruction_cnt_profile_file.stat().st_size == 0:
            output_file = instruction_cnt_solution(work_dir_solution_file, input_file, work_dir, instruction_cnt_profile_file, timeout=timeout, record_perf=include_instruction_cnt)
    if all([script_profile_file.exists(), line_profiler_profile_file.exists(), mem_profiler_profile_file.exists(), instruction_cnt_profile_file.exists()]):
        # skip the solution if all the performance statistics are already collected
        exec_status_file.write_text("correct")
        return script_profile_file, line_profiler_profile_file, mem_profiler_profile_file, instruction_cnt_profile_file, True

    output_file = work_dir / "output.txt"
    if not output_file.exists():
        output_file = instruction_cnt_solution(work_dir_solution_file, input_file, work_dir, instruction_cnt_profile_file, timeout=timeout, record_perf=include_instruction_cnt)
    if not exec_status_file.exists() or exec_status_file.stat().st_size == 0:
        correctness = check_output_correctness(output_file, gt_output_file)
    else:
        correctness = exec_status_file.read_text() == "correct"
    if early_stop:
        if not correctness:
            exec_status_file.write_text("incorrect")
            return None, None, None, None, False

    if not script_profile_file.exists() or script_profile_file.stat().st_size == 0:
        script_profiler_solution(work_dir_solution_file, input_file, work_dir, script_profile_file, timeout)
    if not line_profiler_profile_file.exists() or line_profiler_profile_file.stat().st_size == 0:
        line_profiler_solution(work_dir_solution_file, input_file, work_dir, line_profiler_profile_file, timeout)
    if not mem_profiler_profile_file.exists() or mem_profiler_profile_file.stat().st_size == 0:
        mem_profiler_solution(work_dir_solution_file, input_file, work_dir, mem_profiler_profile_file, timeout)

    exec_status_file.write_text("correct")
    return script_profile_file, line_profiler_profile_file, mem_profiler_profile_file, instruction_cnt_profile_file, correctness

def run_solution_one_input_parallel(q, args):
    """Worker function that processes a single input and puts result in queue."""
    result = run_solution_one_input(*args)
    q.put(result)

def run_solution_multi_inputs(solution_file: Path, input_file_list: List[Path], gt_output_file_list: List[Path], solution_profile_dir: Path, timeout: int = 10, early_stop: bool = True, include_instruction_cnt: bool = False) -> Tuple[Path, Path, Path, Path, bool]:
    """run the solution with multiple inputs and collect correctness and performance statistics."""

    print(f"profiling solution {solution_file} with {len(input_file_list)} inputs")

    decorated_solution_file = solution_profile_dir / f"{solution_file.stem}_line_decorated.py"
    add_line_profiler_decorator_to_python_file(solution_file, decorated_solution_file)

    args_list = [(solution_file, input_file, gt_output_file, solution_profile_dir / input_file.stem, timeout, early_stop, include_instruction_cnt) for input_file, gt_output_file in zip(input_file_list, gt_output_file_list)]

    max_workers = min(5, multiprocessing.cpu_count())  # Limit to 5 workers max
    q = multiprocessing.Queue()
    processes = []
    results = []

    for args in args_list:
        if len(processes) >= max_workers:  # Limit concurrent processes
            processes[0].join()
            processes.pop(0)

        p = multiprocessing.Process(target=run_solution_one_input_parallel, args=(q, args))
        p.daemon = False  # Explicitly set daemon to False
        p.start()
        processes.append(p)

    # Collect results
    for _ in range(len(processes)):
        results.append(q.get())

    for p in processes:
        p.join()

    if not results:  # Ensure no empty zip() error
        return None, None, None, None, False

    script_profile_files, line_profiler_profile_files, mem_profiler_profile_files, instruction_cnt_profile_files, correctness_list = zip(*results)

    if early_stop and not all(correctness_list):
        return None, None, None, None, False    

    # merge the performance statistics
    merged_script_stats = merge_script_profiles(script_profile_files)
    merged_line_profile_file = solution_profile_dir / f"{solution_file.stem}_merge_line_profile.txt"
    merge_line_profiles(line_profiler_profile_files, merged_line_profile_file)
    merged_mem_profile_file = solution_profile_dir / f"{solution_file.stem}_merge_mem_profile.txt"
    merge_mem_profiles(mem_profiler_profile_files, merged_mem_profile_file)
    if include_instruction_cnt:
        merged_instruction_cnt = merge_instruction_cnt_profiles(instruction_cnt_profile_files)
    else:
        merged_instruction_cnt = None

    # check the correctness
    correctness = all(correctness_list)

    return merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt, correctness

def generate_overhead_report(merged_script_stats: Dict[str, float], merged_line_profile_file: Path, merged_mem_profile_file: Path, merged_instruction_cnt: int, include_instruction_cnt: bool = False) -> str:
    # produce the final overhead report
    overhead = f"""
The total memory usage during the code execution is: {merged_script_stats["total_memory_usage"]} MB*s.
The total execution time is: {merged_script_stats["total_execution_time"]} s.
The maximum memory peak requirement is: {merged_script_stats["max_peak_memory_usage"]} MB.
# The line_profiler results are: 
# {merged_line_profile_file.read_text()}
# The memory profiler results are: 
# {merged_mem_profile_file.read_text()}
"""
    if include_instruction_cnt:
        overhead += f"""
# The total number of instructions executed is: {merged_instruction_cnt}.
"""

    return overhead

if __name__ == '__main__':
    # adhoc test case
    root_dir = config["root_dir"]
    solution_file = Path(f"{root_dir}/code-contest-exp/problems/8_B/solutions/python3/solutions_0004.py")
    # the below two lines are buggy
    input_file_list = Path(f"{root_dir}/code-contest-exp/problems/8_B/time_contrast/input").glob("*.in")
    gt_output_file_list = Path(f"{root_dir}/code-contest-exp/problems/8_B/time_contrast/output").glob("*.out")
    # make sure all input files have corresponding output files, otherwise the input will be discarded
    solution_work_dir = Path(f"{root_dir}/code-contest-exp/tmp_work")

    merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt, correctness = run_solution_multi_inputs(solution_file, input_file_list, gt_output_file_list, solution_work_dir)

    if correctness:
        overhead = generate_overhead_report(merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt)
    else:
        overhead = "The code execution failed."
