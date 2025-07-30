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
from typing import List, Dict, Literal

from common import Language
from config import config
from utils import get_cf_problems, filter_problems, problem_test_gen_failed, get_problem_test_gen_fail_reason, check_same_output
from cgig.cgig_utils import problem_has_extracted_constraint
from selector.select_solution import select_evaluate_subset_all

def parse_instruction_count(perf_stat_file: Path) -> int:
    if not (perf_stat_file.is_file() \
        and perf_stat_file.stat().st_size > 0):
        raise FileNotFoundError(f"perf_stat_file: {perf_stat_file} does not exist or is empty.")
    lines = perf_stat_file.read_text().split("\n")
    instruction_cnt = 0
    for line in lines[::-1]:
        if "instructions:u" in line:
            # print("debug:", line)
            instruction_cnt = int(line.split("<PERF_SEP>")[0].strip())
            break

    return instruction_cnt

def parse_instruction_count_with_retry(perf_stat_file: Path, retry: int = 5) -> int:
    for i in range(retry):
        try:
            instruction_cnt = parse_instruction_count(perf_stat_file)
            return instruction_cnt
        except FileNotFoundError as e:
            print(f"attempt {i} failed to parse instruction count result {perf_stat_file} {e}")
            time.sleep(1)

    raise FileNotFoundError(f"failed to parse instruction count result {perf_stat_file}")

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
        for cpp_version in ["c++17", "c++14", "c++11"]:
            compile_process = subprocess.run(
                [
                    "g++",
                    "-Wall",
                    "-Wextra",
                    "-Wconversion",
                    "-static",
                    "-DONLINE_JUDGE",
                    # "-Wl,--stack=268435456",
                    f"-std={cpp_version}",
                    # "-lstdc++exp",
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

def run_solution(
    experiment_name: str,
    solution_path: Path,
    language: Language,
    input_dir: Path,
    output_dir: Path,
    time_limit: float = 1,
    write_output: bool = False,
    include_public_private_tests_only: bool = False,
    record_perf: bool = False,
) -> Dict:
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

        test_cnt = 0
        max_runtime = 0
        max_instruction_cnt = 0
        total_runtime = 0
        total_instruction_cnt = 0
        runtime_dict = {}
        instruction_cnt_dict = {}
        wrong_answer_flag = False
        perf_stat_dir = (input_dir / ".." / "perf_stat").absolute().resolve()
        perf_stat_dir.mkdir(exist_ok=True, parents=True)
        for input_test in os.listdir(input_dir):
            if include_public_private_tests_only and not \
                (input_test.startswith("public") or input_test.startswith("private")):
                continue
            input_path = input_dir / input_test
            output_path = output_dir / f"{input_test[:-3]}.out"
            output_path.parent.mkdir(exist_ok=True, parents=True)
            perf_stat_output_path = perf_stat_dir / f"{solution_path.stem}_{language.name}_{input_test[:-3]}.perf_stat"
            if not input_path.is_file():
                print(f"[WARNING] {input_path} has been deleted by other processes.")
                continue
            try: 
                with open(input_path, "r", encoding="utf-8") as input_file:
                    try:
                        command = ["perf", "stat", "-e", "instructions:u", "-x", "<PERF_SEP>", "-o", perf_stat_output_path]
                        if not record_perf:
                            command = []
                        assert os.environ["CONDA_PREFIX"] is not None, "CONDA_PREFIX not found"
                        conda_prefix_dir = Path(os.environ["CONDA_PREFIX"])
                        assert conda_prefix_dir.exists(), "CONDA_PREFIX not found"
                        if conda_prefix_dir.parent.name == "envs":
                            conda_prefix_dir = conda_prefix_dir.parent.parent.absolute()
                        # check environment of py27 and py38
                        assert (conda_prefix_dir / "envs" / "py27").exists(), "py27 not found"
                        assert (conda_prefix_dir / "envs" / "py38").exists(), "py38 not found"

                        # add timeout
                        command += ["timeout", str(config["max_time_limit"])]

                        if language == Language.JAVA:
                            command += [
                                "java",
                                "-XX:+UseSerialGC",
                                "-XX:TieredStopAtLevel=1",
                                "-XX:NewRatio=5",
                                "-Xms8M",
                                "-Xmx2048M",
                                "-Xss64M",
                                "-DONLINE_JUDGE=true",
                                "-cp",
                                tmp_dir,
                                executable_name,
                            ]
                        elif language == Language.CPP:
                            command += [tmp_dir / "solution"]
                        elif language == Language.PYTHON:
                            command += [
                                f"{conda_prefix_dir.absolute().as_posix()}/envs/py27/bin/python",
                                tmp_dir / "solution.py",
                            ]
                            # should change based on the path of python2.7
                        elif language == Language.PYTHON3:
                            command += [
                                f"{conda_prefix_dir.absolute().as_posix()}/envs/py38/bin/python",
                                tmp_dir / "solution.py",
                            ]
                            # should change based on the path of python3.8

                        start_time = time.perf_counter()
                        run_process = subprocess.run(
                            command,
                            stdin=input_file,
                            stdout=subprocess.PIPE,
                            stderr=subprocess.PIPE,
                            timeout=config["max_time_limit"] + 10,
                            check=False,
                        )
                        runtime = time.perf_counter() - start_time
                        if record_perf:
                            instruction_cnt = parse_instruction_count_with_retry(perf_stat_output_path)
                        else:
                            instruction_cnt = 0
                        if run_process.returncode == 124:
                            raise subprocess.TimeoutExpired(str(command), config["max_time_limit"], output="internal timeout")
                        if run_process.stdout:
                            try:
                                program_output = run_process.stdout.decode("utf-8")
                                if write_output:
                                    # write output for the first in gen_tests.py
                                    with open(output_path, "w", encoding="utf-8") as file:
                                        if len(program_output) < 1000000:
                                            file.write(program_output)
                                else:
                                    # don't write output for the later runs when 
                                    # collecting the execution statistics in run.py
                                    program_output = program_output.split()
                                    if not output_path.exists():
                                        raise ValueError(f"output_path: {output_path} does not exist.")
                                    with open(output_path, "r", encoding="utf-8") as file:
                                        gt_output = file.read().split()
                                    if not check_same_output(gt_output, program_output):
                                        print(
                                            "[WA]",
                                            gt_output[:100],
                                            "..." if len(gt_output) > 100 else "",
                                            program_output[:100],
                                            "..." if len(program_output) > 100 else ""
                                        )
                                        wrong_answer_flag = True
                            except UnicodeError:
                                print("[WA]", "Unicode Error")
                                wrong_answer_flag = True
                        else:
                            print("[WA]", "no output", solution_path, input_test, run_process.stderr.decode("utf-8"))
                            wrong_answer_flag = True
                            if write_output:
                                with open(output_path, "w", encoding="utf-8") as file:
                                    file.write("")
                    except subprocess.TimeoutExpired as e:
                        if e.output != "internal timeout":
                            print(f"[ERROR] internal timeout not working for {solution_path} with {input_file}")
                        runtime = config["max_time_limit"]
                        try:
                            if record_perf:
                                instruction_cnt = parse_instruction_count_with_retry(perf_stat_output_path)
                            else:
                                instruction_cnt = 0
                        except FileNotFoundError as e:
                            print(f"debug 273: fail to parse instruction count result {solution_path} {input_test} {perf_stat_output_path} {e}")
                            raise e
            except FileNotFoundError as e:
                print(e)
                # print(f"[WARNING] {perf_stat_output_path} does not exist or is empty.")
                continue
            max_runtime = max(max_runtime, runtime)
            max_instruction_cnt = max(max_instruction_cnt, instruction_cnt)
            total_runtime += runtime
            total_instruction_cnt += instruction_cnt
            test_cnt += 1
            runtime_dict[input_test] = runtime
            instruction_cnt_dict[input_test] = instruction_cnt
            if experiment_name == "alphacode" and \
                (wrong_answer_flag or runtime >= config["max_time_limit"]):
                # we only do early exit for alphacode
                break

    if test_cnt == 0:
        test_cnt = 1
    test_result = {
        "verdict": "AC",
        "average_time": total_runtime / test_cnt,
        "max_time": max_runtime,
        "time_dict": runtime_dict,
        "average_instruction_cnt": total_instruction_cnt / test_cnt,
        "max_instruction_cnt": max_instruction_cnt,
        "instruction_cnt_dict": instruction_cnt_dict,
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

def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    include_public_private_tests_only: bool = False,
    record_perf: bool = True, # record the perf in "run" but not for "gen"
    problem_with_extracted_constraint_only: bool = False,
    solution_set_type: Literal["full", "three_groups"] = "full",
    only_include_langs: List[str] = None,
):
    """Runs all solutions in the folder."""
    problem_root_dir = Path(problem_root_dir)
    if solution_set_type == "three_groups":
        result_root_dir = Path(config["result_three_groups_root_dir"])
    elif solution_set_type == "full":
        result_root_dir = Path(config["result_root_dir"])
    else:
        raise ValueError(f"Invalid solution_set_type: {solution_set_type}")
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"]),
        filter_with_inconsistency_threshold=experiment_name != "alphacode",
    )[:30]
    relax_flag = False
    if experiment_name in [
        "corpus",
        "instrument_fuzz",
        "raw_fuzz",
        "corpus_mutator_with_generator",
        "instrument_fuzz_mutator_with_generator",
        "raw_fuzz_mutator_with_generator",
        "corpus_instrument_fuzz_mutator_with_constraint",
        "corpus_instrument_fuzz_mutator_with_constraint_multi",
        "corpus_instrument_fuzz_mutator_with_constraint_per_solution",
        "corpus_raw_fuzz_mutator_with_constraint",
        "corpus_raw_fuzz_mutator_with_constraint_multi",
        "corpus_raw_fuzz_mutator_with_constraint_per_solution",
        "corpus_raw_fuzz_default_mutator",
    ]:
        # we didn't run gen_tests.py for fuzzing generated inputs,
        # so we need to write the output for the first run
        relax_flag = True
    if experiment_name in [
        "constraint_guided_one",
        "corpus_instrument_fuzz_mutator_with_constraint",
        "corpus_instrument_fuzz_mutator_with_constraint_multi",
        "corpus_instrument_fuzz_mutator_with_constraint_per_solution",
        "corpus_raw_fuzz_mutator_with_constraint",
        "corpus_raw_fuzz_mutator_with_constraint_multi",
        "corpus_raw_fuzz_mutator_with_constraint_per_solution",
        "corpus_raw_fuzz_default_mutator",
    ]:
        problem_with_extracted_constraint_only = True

    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]
        if problem_with_extracted_constraint_only and not problem_has_extracted_constraint(problem_id):
            continue

        problem_dir = Path(problem_root_dir) / str(problem_id)
        if experiment_name == "alphacode":
            experiment_dir = problem_dir
        else:
            if problem_test_gen_failed(problem_id, experiment_name) and get_problem_test_gen_fail_reason(problem_id, experiment_name) == "No available validator":
                print(f"[INFO] Test generation failed for {experiment_name} of {problem_id} due to no available validator, skipping.")
                continue
            experiment_dir = problem_dir / experiment_name
            Path(result_root_dir / experiment_name).mkdir(exist_ok=True, parents=True)

        if relax_flag and (not (experiment_dir / "input").exists() or \
            not len(os.listdir(experiment_dir / "input")) > 0):
                # TODO: to be fixed
                print(f"[INFO] No input files found for {experiment_name} of {problem_id}, skipping.")
                continue

        result_path = Path(result_root_dir / experiment_name / f"{problem_id}.json")
        result_path.parent.mkdir(exist_ok=True, parents=True)
        if result_path.exists():
            print(f"[INFO] {result_path} exists, skipping.")
            continue
        if solution_set_type == "three_groups":
            full_result_path = Path(config["result_root_dir"]) / experiment_name / f"{problem_id}.json"
            if full_result_path.exists():
                print(f"[INFO] {full_result_path} exists, skipping.")
                continue
        solution_dir = problem_dir / "solutions"
        input_dir = experiment_dir / "input"

        if not input_dir.exists() or len(os.listdir(input_dir)) == 0:
            print(f"[WARNING]input_dir: {input_dir} does not exist or is empty.")
            continue

        output_dir = experiment_dir / "output"
        assert output_dir.exists() and len(os.listdir(output_dir)) > 0, f"[ERROR] output_dir: {output_dir} does not exist or is empty."
        time_limit = (
            problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9
        )

        problem_res = {}

        print(problem["name"])
        print(f"# of tests: {len(os.listdir(input_dir))}")
        test_args = []
        sol_type = "solutions"
        if solution_set_type == "three_groups":
            solution_ids = select_evaluate_subset_all(problem, top_k=5)
            solution_idxs = [int(solution_id.split("_")[-1]) for solution_id in solution_ids]
        elif solution_set_type == "full":
            solution_idxs = range(len(problem[sol_type]["language"]))
        else:
            raise ValueError(f"Invalid solution_set_type: {solution_set_type}")
        if only_include_langs:
            solution_idxs = [idx for idx in solution_idxs if Language(problem[sol_type]["language"][idx]).name.lower() in only_include_langs]
        for solution_idx in solution_idxs:
            language = Language(problem[sol_type]["language"][solution_idx])
            solution_file_name = f"{sol_type}_{solution_idx:04}.{language.to_suffix()}"
            solution_path = solution_dir / language.name.lower() / solution_file_name
            for _ in range(config["repeat_test"]):
                test_args.append(
                    (
                        experiment_name,
                        solution_path,
                        language,
                        input_dir,
                        output_dir,
                        time_limit,
                        False,
                        include_public_private_tests_only,
                        record_perf,
                    )
                )
        random.shuffle(test_args)

        max_workers = max(1, int(0.5 * os.cpu_count()))
        with Pool(processes=max_workers) as pool:
            res = list(
                tqdm(pool.imap(run_solution_wrapper, test_args), total=len(test_args))
            )

        for idx, test_arg in enumerate(test_args):
            if not res[idx]:
                continue
            online_judge_verdict = (
                "incorrect" if "incorrect" in str(test_arg[1]) else "correct"
            )
            problem_res["time_limit"] = test_arg[5]
            solution_id = (str(test_arg[1]).split("/")[-1]).split(".")[0]
            if solution_id not in problem_res.keys():
                problem_res[solution_id] = {
                    "language": test_arg[2].name.lower(),
                    "online_judge_verdict": online_judge_verdict,
                    "verdict": [],
                    "average_time": [],
                    "max_time": [],
                    "time_dict": {},
                    "average_instruction_cnt": [],
                    "max_instruction_cnt": [],
                    "instruction_cnt_dict": {},
                }
            solution_res = problem_res[solution_id]
            solution_res["verdict"].append(res[idx]["verdict"])
            if res[idx]["verdict"] in ["CE", "JE"]:
                continue
            solution_res["average_time"].append(res[idx]["average_time"])
            solution_res["max_time"].append(res[idx]["max_time"])
            solution_res["average_instruction_cnt"].append(res[idx]["average_instruction_cnt"])
            solution_res["max_instruction_cnt"].append(res[idx]["max_instruction_cnt"])
            for test_name in res[idx]["time_dict"].keys():
                if test_name not in solution_res["time_dict"]:
                    solution_res["time_dict"][test_name] = []
                solution_res["time_dict"][test_name].append(
                    res[idx]["time_dict"][test_name]
                )
            for test_name in res[idx]["instruction_cnt_dict"].keys():
                if test_name not in solution_res["instruction_cnt_dict"]:
                    solution_res["instruction_cnt_dict"][test_name] = []
                solution_res["instruction_cnt_dict"][test_name].append(
                    res[idx]["instruction_cnt_dict"][test_name]
                )
        with open(result_path, "w", encoding="utf-8") as file:
            file.write(json.dumps(problem_res, indent=4))


if __name__ == "__main__":
    Fire(main)