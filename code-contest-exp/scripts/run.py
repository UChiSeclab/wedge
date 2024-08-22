"""Run solutions on the tests."""
import os
import subprocess
import re
from pathlib import Path
import random
import json
from multiprocessing import Pool
import time
from fire import Fire
from tqdm import tqdm
import tempdir

from common import Language
from config import config
from utils import get_cf_problems, filter_problems


def compile_solution(tmp_dir: Path, solution_code: str, language: Language):
    """Compiles solution based on language."""
    if language == Language.JAVA:
        class_match = None
        for regex in [r"public\s+class\s+(\w+)", r"class\s+(\w+)"]:
            class_match = re.search(regex, solution_code)
            if class_match:
                break
        if not class_match:
            return "Judge Error"
        class_name = class_match.group(1)
        file_path = tmp_dir / f"{class_name}.java"
        with open(file_path, "w", encoding="utf-8") as file:
            file.write(solution_code)
        compile_process = subprocess.run(
            ["javac", file_path], capture_output=True, check=False
        )
        if compile_process.returncode != 0:
            return "Compile Error"
        return class_name

    if language == Language.CPP:
        file_path = tmp_dir / "solution.cpp"
        with open(file_path, "w", encoding="utf-8") as file:
            file.write(solution_code)
        for cpp_version in ["c++14", "c++17"]:
            compile_process = subprocess.run(
                [
                    "g++",
                    f"-std={cpp_version}",
                    "-O2",
                    file_path,
                    "-o",
                    tmp_dir / "solution",
                ],
                capture_output=True,
                check=False,
            )
            if compile_process.returncode == 0:
                return "solution"
        return "Compile Error"

    if language in [Language.PYTHON, Language.PYTHON3]:
        file_path = tmp_dir / "solution.py"
        with open(file_path, "w", encoding="utf-8") as file:
            file.write(solution_code)
        return "solution.py"

    return "Judge Error"


def check_same_output(output_A: str, output_B: str):
    if len(output_A) != len(output_B):
        return False
    for idx, Ai in enumerate(output_A):
        Bi = output_B[idx]
        try:
            Ai = float(Ai)
            Bi = float(Bi)
            if abs(Ai - Bi) > 1e-5:
                return False
        except ValueError:
            if Ai != Bi:
                return False
    return True


def run_solution(
    solution_path: Path,
    language: Language,
    input_dir: Path,
    output_dir: Path,
    time_limit: float = 1,
    write_output: bool = False,
):
    """Runs solution on tests.

    Args:
        soltuion_path: The path of the solution.
        language: The solution language.
        input_dir: The directory which stores input files.
        output_dir: The directory which stores output files.
        time_limit: The actual time limit for this problem.
        write_output: Whether to write the output to the output_dir or not. Set true for the first run with correct solutions to obtain the gt output. Set false for the later runs to compare the output with the ground truth output.

    Returns:
        test_results (Dict): A dictionary to store the test results.
            verdict: "AC" / "WA" / "TLE" / "KILL"
    """

    with open(solution_path, "r", encoding="utf-8") as file:
        solution_code = file.read()

    with tempdir.TempDir() as tmp_dir:
        tmp_dir = Path(tmp_dir)
        executable_name = compile_solution(tmp_dir, solution_code, language)
        if executable_name == "Compile Error":
            return {"verdict": "CE"}
        if executable_name == "Judge Error":
            return {"verdict": "JE"}

        max_runtime = 0
        test_cnt = 0
        total_runtime = 0
        runtime_dict = {}
        wrong_answer_flag = False
        for input_test in os.listdir(input_dir):
            input_path = input_dir / input_test
            output_path = output_dir / f"{input_test[:-3]}.out"
            with open(input_path, "r", encoding="utf-8") as input_file:
                try:
                    command = []
                    if language == Language.JAVA:
                        command = ["java", "-Xmx4M", "-cp", tmp_dir, executable_name]
                    elif language == Language.CPP:
                        command = [tmp_dir / "solution"]
                    elif language == Language.PYTHON:
                        command = ["python", tmp_dir / "solution.py"]
                    elif language == Language.PYTHON3:
                        command = ["python3", tmp_dir / "solution.py"]

                    start_time = time.time()
                    run_process = subprocess.run(
                        command,
                        stdin=input_file,
                        stdout=subprocess.PIPE,
                        stderr=subprocess.PIPE,
                        timeout=config["max_time_limit"],
                        check=False,
                    )
                    runtime = time.time() - start_time
                    if run_process.stdout:
                        try:
                            program_output = run_process.stdout.decode("utf-8")
                            if write_output:
                                with open(output_path, "w", encoding="utf-8") as file:
                                    file.write(program_output)
                            else:
                                program_output = program_output.split()
                                with open(output_path, "r", encoding="utf-8") as file:
                                    actual_output = file.read().split()
                                if not check_same_output(actual_output, program_output):
                                    wrong_answer_flag = True
                        except UnicodeError:
                            wrong_answer_flag = True
                    else:
                        wrong_answer_flag = True
                except subprocess.TimeoutExpired:
                    runtime = config["max_time_limit"]
            max_runtime = max(max_runtime, runtime)
            total_runtime += runtime
            test_cnt += 1
            runtime_dict[input_test] = runtime
            if wrong_answer_flag or runtime >= config["max_time_limit"]:
                break

    test_result = {
        "verdict": "AC",
        "average_time": total_runtime / test_cnt,
        "max_time": max_runtime,
        "time_dict": runtime_dict,
    }
    if wrong_answer_flag:
        test_result["verdict"] = "WA"
    elif max_runtime == config["max_time_limit"]:
        test_result["verdict"] = "KILL"
    elif max_runtime > time_limit:
        test_result["verdict"] = "TLE"
    return test_result


def run_solution_wrapper(args):
    """Wrapper function to unpack arguments."""
    return run_solution(*args)


def input_sanitization(input_dir: Path, output_dir: Path):
    invalid_input_files = []
    for input_test in os.listdir(input_dir):
        output_path = output_dir / f"{input_test[:-3]}.out"
        if not os.path.isfile(output_path):
            invalid_input_files.append(input_test)

    for invalid_input_file in invalid_input_files:
        print(
            f"[WARNING] input file {invalid_input_file} does not have \
            corresponding output file. The input likely runs into problems."
        )
        print(f"[WARNING] Removing invalid input file: {invalid_input_file}")
        os.remove(input_dir / invalid_input_file)


def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    result_root_dir: str = config["result_root_dir"],
):
    """Runs all solutions in the folder."""
    problem_root_dir = Path(problem_root_dir)
    result_root_dir = Path(result_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]

        problem_dir = problem_root_dir / problem_id
        if experiment_name == "alphacode":
            experiment_dir = problem_dir
        else:
            experiment_dir = problem_dir / experiment_name
            Path(result_root_dir / experiment_name).mkdir(exist_ok=True, parents=True)
        result_path = Path(result_root_dir / experiment_name / f"{problem_id}.json")
        solution_dir = problem_dir / "solutions"
        input_dir = experiment_dir / "input"
        output_dir = experiment_dir / "output"
        time_limit = (
            problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9
        )

        problem_res = {}

        print(problem["name"])
        print(f"# of tests: {len(os.listdir(input_dir))}")
        input_sanitization(input_dir, output_dir)
        test_args = []
        sol_type = "solutions"
        for solution_idx, _ in enumerate(problem[sol_type]["solution"]):
            language = Language(problem[sol_type]["language"][solution_idx])
            solution_file_name = f"{sol_type}_{solution_idx:04}.{language.to_suffix()}"
            solution_path = solution_dir / language.name.lower() / solution_file_name
            for _ in range(config["repeat_test"]):
                test_args.append(
                    (
                        solution_path,
                        language,
                        input_dir,
                        output_dir,
                        time_limit,
                        False,
                    )
                )
        random.shuffle(test_args)

        max_workers = max(1, int(0.75 * os.cpu_count()))
        with Pool(processes=max_workers) as pool:
            res = list(
                tqdm(pool.imap(run_solution_wrapper, test_args), total=len(test_args))
            )

        for idx, test_arg in enumerate(test_args):
            if not res[idx]:
                continue
            online_judge_verdict = (
                "incorrect" if "incorrect" in str(test_arg[0]) else "correct"
            )
            problem_res["time_limit"] = test_arg[4]
            problem_id = (str(test_arg[0]).split("/")[-1]).split(".")[0]
            if problem_id not in problem_res.keys():
                problem_res[problem_id] = {
                    "language": test_arg[1].name.lower(),
                    "online_judge_verdict": online_judge_verdict,
                    "verdict": [],
                    "average_time": [],
                    "max_time": [],
                    "time_dict": {},
                }
            solution_res = problem_res[problem_id]
            solution_res["verdict"].append(res[idx]["verdict"])
            if res[idx]["verdict"] in ["CE", "JE"]:
                continue
            solution_res["average_time"].append(res[idx]["average_time"])
            solution_res["max_time"].append(res[idx]["max_time"])
            for test_name in res[idx]["time_dict"].keys():
                if test_name not in solution_res["time_dict"]:
                    solution_res["time_dict"][test_name] = []
                solution_res["time_dict"][test_name].append(
                    res[idx]["time_dict"][test_name]
                )
        with open(result_path, "w", encoding="utf-8") as file:
            file.write(json.dumps(problem_res, indent=4))


if __name__ == "__main__":
    Fire(main)
