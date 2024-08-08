"""Run java solutions on the tests."""
import os
import subprocess
import re
from pathlib import Path
import json
from multiprocessing import Pool
import time
import shutil
from fire import Fire
from tqdm import tqdm

from config import config, Language
from utils import get_cf_problems, filter_problems

def compile_java(solution_dir: Path, solution_code: str):
    """Compiles java solution.
    
    Args:
    - solution_dir (Path): The directory to store the solution.
    - solution_code (str): The java solution code
    """
    class_match = re.search(r"public\s+class\s+(\w+)", solution_code)
    if not class_match:
        return "error"
    class_name = class_match.group(1)
    file_path = solution_dir / f"{class_name}.java"
    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(solution_code)
    compile_process = subprocess.run(["javac", file_path], capture_output=True, check=False)
    if compile_process.returncode != 0:
        return "error"
    return class_name

def run_java(
        solution_dir: Path,
        solution_file_name: str,
        input_dir: Path,
        output_dir: Path,
        write_output: bool = False,
        time_limit: float = 1
        ):
    """Runs java solution on tests."""

    tmp_dir = Path(solution_file_name)
    tmp_dir.mkdir(exist_ok=True)
    with open(solution_dir / solution_file_name, 'r', encoding='utf-8') as file:
        solution_code = file.read()
    class_name = compile_java(tmp_dir, solution_code)

    max_time = 0
    total_time = 0
    time_list = []
    wrong_answer_flag = False
    for input_test in os.listdir(input_dir):
        input_path = input_dir / input_test
        output_path = output_dir / f"{input_test[:-3]}.out"
        with open(input_path, 'r', encoding='utf-8') as input_file:
            try:
                start_time = time.time()
                run_process = subprocess.run(
                    ["java", "-cp", tmp_dir, class_name],
                    stdin=input_file,
                    stdout=subprocess.PIPE,
                    stderr=subprocess.PIPE,
                    timeout=config['max_time_limit'],
                    check=False
                )
                runtime = time.time() - start_time
                if run_process.returncode == 0 and run_process.stdout:
                    if not write_output:
                        program_output = run_process.stdout.decode('utf-8').split()
                        with open(output_path, 'r', encoding='utf-8') as file:
                            actual_output = file.read().split()
                        if actual_output != program_output:
                            wrong_answer_flag = True
                    else:
                        with open(output_path, 'w', encoding='utf-8') as file:
                            file.write(run_process.stdout.decode('utf-8'))
            except subprocess.TimeoutExpired:
                runtime = config['max_time_limit']
        max_time = max(max_time, runtime)
        total_time += runtime
        time_list.append((input_test, runtime))

    shutil.rmtree(tmp_dir)

    test_result = {}
    test_result['verdict'] = 'AC'
    test_result['average_time'] = total_time / len(os.listdir(input_dir))
    test_result['max_time'] = max_time
    test_result['times'] = time_list
    if wrong_answer_flag:
        test_result['verdict'] = 'WA'
    if max_time == config['max_time_limit']:
        test_result['verdict'] = 'KILL'
    if max_time > time_limit:
        test_result['verdict'] = 'TLE'
    return test_result

def main(
    output_file: str = config["output_file"],
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
):
    """Runs all java solutions in the folder"""
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(get_cf_problems())

    output_file = Path(output_file)
    if not output_file.exists():
        with open(output_file, 'w', encoding='utf-8') as file:
            json.dump({}, file)
    with open(output_file, 'r', encoding='utf-8') as file:
        results = json.load(file)

    for problem in tqdm(filtered_problems):
        problem_dir = problem_root_dir / str(problem["name"].split(".")[0])
        if experiment_name == 'none':
            experiment_dir = problem_dir
        else:
            experiment_dir = problem_dir / experiment_name
        solution_dir = problem_dir / "solutions" / "java"
        input_dir = experiment_dir / "input"
        output_dir = experiment_dir / "output"
        time_limit = problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9

        results[problem["name"]] = []

        test_args = []
        for sol_type in ["solutions", "incorrect_solutions"]:
            for solution_idx, _ in enumerate(tqdm(problem[sol_type]["solution"])):
                if Language.idx_to_lang(problem[sol_type]["language"][solution_idx]) != str(Language.JAVA):
                    continue
                solution_file_name = f"{sol_type}_{solution_idx:03}"
                test_args.append(
                    (solution_dir, solution_file_name, input_dir, output_dir, False, time_limit)
                )

        with Pool(processes=os.cpu_count()) as pool:
            res = list(tqdm(pool.starmap(run_java, test_args), total=len(test_args)))
            print(len(res))
            for idx, test_arg in enumerate(test_args):
                online_judge_verdict = "incorrect" if "incorrect" in test_arg[1] else "correct"
                results[problem["name"]].append({
                    "solution_id": test_arg[1],
                    "language": "java",
                    "online_judge_verdict": online_judge_verdict,
                    "time_limit": test_arg[5],
                    "verdict": res[idx]['verdict'],
                    "average_time": res[idx]['average_time'],
                    "max_time": res[idx]['max_time'],
                    "times": res[idx]['times']
                })
    
    with open(config['output_file'], 'w+', encoding='utf-8') as file:
        file.write(json.dumps(results, indent=4))

if __name__ == "__main__":
    Fire(main)
