"""Run cpp solutions on the tests."""
import os
import subprocess
from pathlib import Path
import random
import json
from multiprocessing import Pool
import time
import shutil
from fire import Fire
from tqdm import tqdm

from common import Language
from config import config
from utils import get_cf_problems, filter_problems

def compile_cpp(solution_dir: Path, solution_code: str):
    """Compiles C++ solution.
    
    Args:
    - solution_dir (Path): The directory to store the solution.
    - solution_code (str): The C++ solution code
    """
    file_path = solution_dir / "solution.cpp"
    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(solution_code)
    compile_process = subprocess.run(["g++", "-std=c++17", file_path, "-o", solution_dir / "solution"], capture_output=True, check=False)
    if compile_process.returncode != 0:
        return "error"
    return "solution"

def run_cpp(
        solution_dir: Path,
        solution_file_name: str,
        input_dir: Path,
        output_dir: Path,
        write_output: bool = False,
        time_limit: float = 1,
        repeat_test_idx: int = 0,
        ):
    """Runs C++ solution on tests.
    
    Args:
        solution_dir (Path): The directory of the solution.
        solution_file_name (str): The file name of the executable.
        input_dir (Path): The directory which stores input files.
        output_dir (Path): The directory which stores output files.
        write_output (bool): Whether to write the output to the output_dir or not.
        time_limit (float): The actual time limit for this problem.
        repeat_test_idx (int): The index of repeat test.
    """

    tmp_dir = Path(f"{solution_file_name}_{repeat_test_idx}")
    tmp_dir.mkdir(exist_ok=True)
    with open(solution_dir / f"{solution_file_name}", 'r', encoding='utf-8') as file:
        solution_code = file.read()
    executable_name = compile_cpp(tmp_dir, solution_code)
    if executable_name == "error":
        shutil.rmtree(tmp_dir)
        return

    max_time = 0
    test_cnt = 0
    total_time = 0
    time_dict = {}
    wrong_answer_flag = False
    for input_test in os.listdir(input_dir):
        input_path = input_dir / input_test
        output_path = output_dir / f"{input_test[:-3]}.out"
        with open(input_path, 'r', encoding='utf-8') as input_file:
            try:
                start_time = time.time()
                run_process = subprocess.run(
                    [tmp_dir / executable_name],
                    stdin=input_file,
                    stdout=subprocess.PIPE,
                    stderr=subprocess.PIPE,
                    timeout=config['max_time_limit'],
                    check=False
                )
                runtime = time.time() - start_time
                if run_process.returncode == 0 and run_process.stdout:
                    try:
                        program_output = run_process.stdout.decode('utf-8').split()
                        if not write_output:
                            with open(output_path, 'r', encoding='utf-8') as file:
                                actual_output = file.read().split()
                            if actual_output != program_output:
                                wrong_answer_flag = True
                        else:
                            with open(output_path, 'w', encoding='utf-8') as file:
                                file.write(run_process.stdout.decode('utf-8'))
                    except UnicodeDecodeError:
                        wrong_answer_flag = True
            except subprocess.TimeoutExpired:
                runtime = config['max_time_limit']
        max_time = max(max_time, runtime)
        total_time += runtime
        test_cnt += 1
        time_dict[input_test] = runtime
        if wrong_answer_flag or runtime == config['max_time_limit']:
            break

    shutil.rmtree(tmp_dir)

    test_result = {}
    test_result['verdict'] = 'AC'
    test_result['average_time'] = total_time / test_cnt
    test_result['max_time'] = max_time
    test_result['time_dict'] = time_dict
    if wrong_answer_flag:
        test_result['verdict'] = 'WA'
    elif max_time == config['max_time_limit']:
        test_result['verdict'] = 'KILL'
    elif max_time > time_limit:
        test_result['verdict'] = 'TLE'
    return test_result

def run_cpp_wrapper(args):
    """Wrapper function to unpack arguments."""
    return run_cpp(*args)

def main(
    output_file: str = config["output_file"],
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
):
    """Runs all C++ solutions in the folder"""
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(get_cf_problems())

    output_file = Path(output_file)
    if not output_file.exists():
        with open(output_file, 'w', encoding='utf-8') as file:
            json.dump({}, file)
    with open(output_file, 'r', encoding='utf-8') as file:
        results = json.load(file)

    for problem in tqdm(filtered_problems[:100]):
        problem_dir = problem_root_dir / str(problem["name"].split(".")[0])
        if experiment_name == 'none':
            experiment_dir = problem_dir
        else:
            experiment_dir = problem_dir / experiment_name
        solution_dir = problem_dir / "solutions" / "cpp"
        input_dir = experiment_dir / "input"
        output_dir = experiment_dir / "output"
        time_limit = problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9

        results[problem["name"]] = {}

        print(problem['name'])
        print(f"# of tests: {len(os.listdir(input_dir))}")
        test_args = []
        for sol_type in ["solutions", "incorrect_solutions"]:
            for solution_idx, _ in enumerate(problem[sol_type]["solution"]):
                if Language.idx_to_lang(problem[sol_type]["language"][solution_idx]) != str(Language.CPP):
                    continue
                solution_file_name = f"{sol_type}_{solution_idx:03}"
                for idx in range(config["repeat_test"]):
                    test_args.append(
                        (solution_dir, solution_file_name, input_dir, output_dir, False, time_limit, idx)
                    )
        random.shuffle(test_args)

        max_workers = max(1, int(0.75 * os.cpu_count()))
        with Pool(processes=max_workers) as pool:
            res = list(tqdm(pool.imap(run_cpp_wrapper, test_args), total=len(test_args)))
        for idx, test_arg in enumerate(test_args):
            if not res[idx]:
                continue
            online_judge_verdict = "incorrect" if "incorrect" in test_arg[1] else "correct"
            if test_arg[1] not in results[problem["name"]]:
                results[problem["name"]][test_arg[1]] = {
                    "language": "cpp",
                    "online_judge_verdict": online_judge_verdict,
                    "verdict": [],
                    "average_time": [],
                    "max_time": [],
                    "time_dict": {}
                }
            results[problem["name"]][test_arg[1]]["language"] = "cpp"
            results[problem["name"]][test_arg[1]]["online_judge_verdict"] = online_judge_verdict
            results[problem["name"]]["time_limit"] = test_arg[5]
            results[problem["name"]][test_arg[1]]["verdict"].append(res[idx]['verdict'])
            results[problem["name"]][test_arg[1]]["average_time"].append(res[idx]['average_time'])
            results[problem["name"]][test_arg[1]]["max_time"].append(res[idx]['max_time'])
            for test_name in res[idx]['time_dict'].keys():
                if test_name not in results[problem["name"]][test_arg[1]]["time_dict"]:
                    results[problem["name"]][test_arg[1]]["time_dict"][test_name] = []
                results[problem["name"]][test_arg[1]]["time_dict"][test_name].append(
                    res[idx]['time_dict'][test_name]
                )
    
        with open(config['output_file'], 'w', encoding='utf-8') as file:
            file.write(json.dumps(results, indent=4))

if __name__ == "__main__":
    Fire(main)
