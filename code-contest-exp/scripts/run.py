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


def compile_solution(language: Language, solution_dir: Path, solution_code: str):
    """Compiles solution based on language."""
    if language == Language.JAVA:
        class_match = re.search(r"public\s+class\s+(\w+)", solution_code)
        if not class_match:
            return "error"
        class_name = class_match.group(1)
        file_path = solution_dir / f"{class_name}.java"
        with open(file_path, "w", encoding="utf-8") as file:
            file.write(solution_code)
        compile_process = subprocess.run(
            ["javac", file_path], capture_output=True, check=False
        )
        if compile_process.returncode != 0:
            return "error"
        return class_name
    if language == Language.CPP:
        file_path = solution_dir / "solution.cpp"
        with open(file_path, "w", encoding="utf-8") as file:
            file.write(solution_code)
        compile_process = subprocess.run(
            ["g++", "-std=c++14", file_path, "-o", solution_dir / "solution"],
            capture_output=True,
            check=False,
        )
        if compile_process.returncode != 0:
            return "error"
        return "solution"
    if language in [Language.PYTHON, Language.PYTHON3]:
        file_path = solution_dir / "solution.py"
        with open(file_path, "w", encoding="utf-8") as file:
            file.write(solution_code)
        return "solution.py"
    return "error"


def run_solution(
    solution_dir: Path,
    solution_file_name: str,
    language: Language,
    input_dir: Path,
    output_dir: Path,
    write_output: bool = False,
    time_limit: float = 1,
    repeat_test_idx: int = 0,
):
    """Runs solution on tests.

    Args:
        soltuion_dir (Path): The directory of the solution.
        solution_file_name (str): The file name of the solution.
        language (Language): The solution language.
        input_dir (Path): The directory which stores input files.
        output_dir (Path): The directory which stores output files.
        write_output (bool): Whether to write the output to the output_dir or not. Set true for the first run with correct solutions to obtain the gt output. Set false for the later runs to compare the output with the gt output.
        time_limit (float): The actual time limit for this problem.
        repeat_test_idx (int): The index of repeat test.

    Returns:
        test_results (Dict): A dictionary to store the test results.
            verdict: "AC" / "WA" / "TLE" / "KILL"
    """

    assert (
        repeat_test_idx == 0 or not write_output
    ), "write_output should be true for gt solution and false for submitted solutions"

    with open(solution_dir / solution_file_name, "r", encoding="utf-8") as file:
        solution_code = file.read()

    with tempdir.TempDir() as tmp_dir:
        tmp_dir = Path(tmp_dir)
        executable_name = compile_solution(language, tmp_dir, solution_code)
        if executable_name == "error":
            return

        max_time = 0
        test_cnt = 0
        total_time = 0
        time_dict = {}
        wrong_answer_flag = False
        for input_test in os.listdir(input_dir):
            input_path = input_dir / input_test
            output_path = output_dir / f"{input_test[:-3]}.out"
            with open(input_path, "r", encoding="utf-8") as input_file:
                try:
                    start_time = time.time()
                    command = []
                    if language == Language.JAVA:
                        command = ["java", "-Xmx512m", "-cp", tmp_dir, executable_name]
                    elif language == Language.CPP:
                        command = [tmp_dir / "solution"]
                    elif language == Language.PYTHON:
                        command = ["python", tmp_dir / "solution.py"]
                    elif language == Language.PYTHON3:
                        command = ["python3", tmp_dir / "solution.py"]

                    run_process = subprocess.run(
                        command,
                        stdin=input_file,
                        stdout=subprocess.PIPE,
                        stderr=subprocess.PIPE,
                        timeout=config["max_time_limit"],
                        check=False,
                    )
                    runtime = time.time() - start_time
                    if run_process.returncode == 0 and run_process.stdout:
                        if not write_output:
                            try:
                                program_output = run_process.stdout.decode(
                                    "utf-8"
                                ).split()
                                with open(output_path, "r", encoding="utf-8") as file:
                                    actual_output = file.read().split()
                                if actual_output != program_output:
                                    wrong_answer_flag = True
                            except UnicodeError:
                                wrong_answer_flag = True
                        else:
                            with open(output_path, "w", encoding="utf-8") as file:
                                file.write(run_process.stdout.decode("utf-8"))
                except subprocess.TimeoutExpired:
                    runtime = config["max_time_limit"]
            max_time = max(max_time, runtime)
            total_time += runtime
            test_cnt += 1
            time_dict[input_test] = runtime
            if wrong_answer_flag or runtime == config["max_time_limit"]:
                break

    test_result = {
        "verdict": "AC",
        "average_time": total_time / test_cnt,
        "max_time": max_time,
        "time_dict": time_dict,
    }
    if wrong_answer_flag:
        test_result["verdict"] = "WA"
    elif max_time == config["max_time_limit"]:
        test_result["verdict"] = "KILL"
    elif max_time > time_limit:
        test_result["verdict"] = "TLE"
    return test_result


def run_solution_wrapper(args):
    """Wrapper function to unpack arguments."""
    return run_solution(*args)


def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
):
    """Runs all java solutions in the folder"""
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(get_cf_problems())

    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]
        if (
            config["specified_problem"]
            and problem_id not in config["specified_problem"]
        ):
            continue

        problem_dir = problem_root_dir / problem["name"].split(".")[0]
        if experiment_name == "none":
            experiment_dir = problem_dir
        else:
            experiment_dir = problem_dir / experiment_name
        solution_dir = problem_dir / "solutions"
        input_dir = experiment_dir / "input"
        output_dir = experiment_dir / "output"
        time_limit = (
            problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9
        )

        problem_res = {}

        print(problem["name"])
        print(f"# of tests: {len(os.listdir(input_dir))}")
        test_args = []
        for sol_type in ["solutions", "incorrect_solutions"]:
            for solution_idx, _ in enumerate(problem[sol_type]["solution"]):
                language = Language(problem[sol_type]["language"][solution_idx])
                solution_file_name = (
                    f"{sol_type}_{solution_idx:04}.{language.to_suffix()}"
                )
                for idx in range(config["repeat_test"]):
                    test_args.append(
                        (
                            solution_dir / language.name.lower(),
                            solution_file_name,
                            language,
                            input_dir,
                            output_dir,
                            False,
                            time_limit,
                            idx,
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
                "incorrect" if "incorrect" in test_arg[1] else "correct"
            )
            problem_res["time_limit"] = test_arg[6]
            if test_arg[1].split(".")[0] not in problem_res.keys():
                problem_res[test_arg[1].split(".")[0]] = {
                    "language": language.name.lower(),
                    "online_judge_verdict": online_judge_verdict,
                    "verdict": [],
                    "average_time": [],
                    "max_time": [],
                    "time_dict": {},
                }
            solution_res = problem_res[test_arg[1].split(".")[0]]
            solution_res["language"] = language.name
            solution_res["online_judge_verdict"] = online_judge_verdict
            solution_res["verdict"].append(res[idx]["verdict"])
            solution_res["average_time"].append(res[idx]["average_time"])
            solution_res["max_time"].append(res[idx]["max_time"])
            for test_name in res[idx]["time_dict"].keys():
                if test_name not in solution_res["time_dict"]:
                    solution_res["time_dict"][test_name] = []
                solution_res["time_dict"][test_name].append(
                    res[idx]["time_dict"][test_name]
                )
        if config["experiment_name"] == "none":
            with open(
                f"results/alphacode/{problem_id}.json", "w", encoding="utf-8"
            ) as file:
                file.write(json.dumps(problem_res, indent=4))
        else:
            exp_folder = config["experiment_name"]
            with open(
                f"results/{exp_folder}/{problem_id}.json", "w", encoding="utf-8"
            ) as file:
                file.write(json.dumps(problem_res, indent=4))


if __name__ == "__main__":
    Fire(main)
