import json
import os
from pathlib import Path
from typing import List, Tuple, Dict, Union
from multiprocessing import Pool, Manager
from tqdm import tqdm
import tempdir
import subprocess
import shutil
from fire import Fire

from config import config


def parse_stack_trace(stack_trace: str) -> Tuple[str, int]:
    """
    Parse the stack trace to get the checking constraint and line number
    """
    pass


def compile_solution(tmp_dir: Path, instrumented_program: Path) -> Union[Path, str]:
    file_path = tmp_dir / instrumented_program.name
    shutil.copy(instrumented_program, file_path)
    for cpp_version in ["c++17", "c++14", "c++11"]:
        compile_process = subprocess.run(
            [
                "g++",
                "-Wall",
                "-Wextra",
                "-Wconversion",
                "-static",
                "-DONLINE_JUDGE",
                f"-std={cpp_version}",
                "-g",  # additional flag to enable debugging
                "-O2",
                file_path,
                "-o",
                tmp_dir / instrumented_program.stem,
            ],
            capture_output=True,
            check=False,
        )
        if compile_process.returncode == 0:
            return tmp_dir / instrumented_program.stem

    return "Compile Error"


def run_classifier(input_file_list: List[Path], instrumented_program: Path) -> Dict[str, Dict]:
    """
    Run the classifier on the given input file list and monitor whether there is a crash,
    and use the stack trace to obtain the corresponding checking constraint and line number.
    """
    res_dict = {}

    with tempdir.TempDir() as tmp_dir:
        tmp_dir = Path(tmp_dir)
        # Compile the instrumented program
        executable_file = compile_solution(tmp_dir, instrumented_program)

        if executable_file == "Compile Error":
            print(f"Compile Error: {instrumented_program}")
            return {"error": "Compile Error"}

        for input_file in input_file_list:
            input_file_name = input_file.name
            res_dict[input_file_name] = {}
            with open(input_file, "r") as f:
                try:
                    res = subprocess.run(
                        [executable_file.as_posix()],
                        stdin=f,
                        timeout=config["max_time_limit"],
                        check=True,
                        stdout=subprocess.DEVNULL,
                        stderr=subprocess.DEVNULL,
                    )
                except subprocess.TimeoutExpired:
                    print(f"Timeout for {input_file}")
                    res_dict[input_file_name]["status"] = "Timeout"
                    continue
                except subprocess.CalledProcessError as e:
                    print(f"Crash for {input_file}")
                    res_dict[input_file_name]["status"] = "Crash"
                    res_dict[input_file_name]["error_message"] = str(e)
                    continue
                except Exception as e:
                    print(f"Unknown error for {input_file}: {e}")
                    res_dict[input_file_name]["status"] = "Unknown"
                    res_dict[input_file_name]["error_message"] = str(e)
                    continue
                res_dict[input_file_name]["status"] = "Pass"

    return res_dict


def process_strategy(
    strategy: str,
    problem_id: str,
    constraints_dir: Path,
    problem_root_dir: Path,
    input_classify_dir: Path,
):
    """
    Process a single strategy for a specific problem ID.
    """
    input_dir = problem_root_dir / problem_id / strategy / "input"
    if strategy == "alphacode":
        input_dir = problem_root_dir / problem_id / "input"

    input_file_list = list(input_dir.glob("*.in"))

    for solution_id in os.listdir(constraints_dir / problem_id):
        for input_pair in os.listdir(constraints_dir / problem_id / solution_id):
            instrumented_program = constraints_dir / problem_id / solution_id / input_pair / "transformed_program.cpp"

            assert instrumented_program.exists(), f"{instrumented_program} does not exist"
            res = run_classifier(input_file_list, instrumented_program)
            result_file = input_classify_dir / problem_id / solution_id / input_pair / f"{strategy}.json"
            result_file.parent.mkdir(parents=True, exist_ok=True)
            with open(result_file, "w") as f:
                json.dump(res, f, indent=4)


def main(problem_root_dir: str = config["problem_root_dir"]):
    constraints_dir = Path(config["constraints_dir"])
    problem_root_dir = Path(problem_root_dir)
    input_classify_dir = Path(config["input_classify_dir"])
    strategies = ["alphacode", "plain_problem", "slow_solution", "diff_solution_one_input", "random_solution", "evalperf_slow_solution", "evalperf_random_solution", "corpus"]

    tasks = []
    for strategy in strategies:
        for problem_id in os.listdir(constraints_dir):
            tasks.append((strategy, problem_id, constraints_dir, problem_root_dir, input_classify_dir))

    with Pool(processes=int(0.5 * os.cpu_count())) as pool:
        for _ in tqdm(pool.starmap(process_strategy, tasks), total=len(tasks)):
            pass


if __name__ == "__main__":
    Fire(main)
