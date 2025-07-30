import os
from pathlib import Path
from concurrent.futures import ProcessPoolExecutor, as_completed
from multiprocessing import cpu_count
from fire import Fire
from typing import Dict

from config import config
from utils import get_alphacode_result, filter_problems, get_cf_problems
from common import Language
from gen_tests import check_consistency_of_gen_tests_output
from cgig.cgig_utils import problem_has_extracted_constraint


def process_problem(problem: Dict, strategy: str):
    problem_id = problem["name"].split(".")[0]
    print(f"Processing problem: {problem_id}")
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    input_dir = problem_dir / strategy / "input"
    output_dir = problem_dir / strategy / "output"
    solution_dir = problem_dir / "solutions" / str(Language.JAVA)  # only use Java solutions for now
    num_ori_inputs = len(list(input_dir.glob("*.in")))
    if not input_dir.exists() or num_ori_inputs == 0:
        # raise ValueError(f"No inputs found for problem {problem_id}")
        print(f"[Warning] No inputs found for problem {problem_id}")
        return

    if output_dir.exists() and \
        len(list(output_dir.glob("*.out"))) == num_ori_inputs:
        print(f"Outputs already exist for problem {problem_id}")
        return

    output_dir.mkdir(parents=True, exist_ok=True)
    alphacode_result = get_alphacode_result(problem_id)
    correct_solution_file_names = [
        solution_file_name
        for solution_file_name in os.listdir(solution_dir)
        if ("incorrect" not in solution_file_name) and \
            all(v in ["AC", "TLE"] for v in alphacode_result[solution_file_name.split(".")[0]]["verdict"])
    ]
    correct_solution_file_names = sorted(correct_solution_file_names)
    correct_solution_file_names = correct_solution_file_names[:config["max_num_solutions_for_consistency_check"]]
    time_limit = problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9
    inconsistent_input_file_names, solution_major_output_dict = check_consistency_of_gen_tests_output(
        input_dir,
        solution_dir,
        time_limit,
        correct_solution_file_names,
        early_stop=False
    )

    print(f"[INFO] number of inconsistent_input_file_names: {len(inconsistent_input_file_names)} out of {num_ori_inputs} will be removed")

    if inconsistent_input_file_names:
        for inconsistent_input_file_name in inconsistent_input_file_names:
            file_path = input_dir / inconsistent_input_file_name
            if file_path.exists():
                file_path.unlink()

    for input_file_name, majority_output in solution_major_output_dict.items():
        with open(output_dir / f"{input_file_name[:-3]}.out", "w") as f:
            f.write(majority_output)

def main(strategy: str, problem_with_extracted_constraint_only: bool = False):
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )[:30]

    if problem_with_extracted_constraint_only:
        filtered_problems = [
            problem for problem in filtered_problems
            if problem_has_extracted_constraint(problem["name"].split(".")[0])
        ]

    max_workers = min(int(0.5 * cpu_count()), len(filtered_problems))  # Limit parallel workers

    with ProcessPoolExecutor(max_workers=max_workers) as executor:
        future_to_problem = {executor.submit(process_problem, problem, strategy): problem for problem in filtered_problems}

        for future in as_completed(future_to_problem):
            try:
                future.result()  # Handle any exceptions raised by the processes
            except Exception as e:
                problem_id = future_to_problem[future]["name"]
                print(f"[Error] Processing problem {problem_id} failed with error: {e}")

if __name__ == "__main__":
    Fire(main)
