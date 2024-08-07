import os
import subprocess
import random
from tqdm import tqdm
import json
from pathlib import Path

from __init__ import *
from gpt_interactor import write_test_generator
from cluster import cluster_code_snippets
from typing import List


def select_solutions(raw_solutions: List[str]) -> List[str]:
    selected_solutions = list()
    if config["exp_type"] == "random":
        # Randomly select 5 solutions from the list of Java solutions
        selected_solutions = random.sample(raw_solutions, 5)
    elif config["exp_type"] == "cluster_diff":
        solutions, labels = cluster_code_snippets(raw_solutions)
        print(labels)
        print(
            f"Use {len(solutions) / len(raw_solutions)} part of solutions for clustering"
        )
        selected_solutions = ["", "", "", "", ""]
        for i in range(len(labels)):
            selected_solutions[labels[i]] = solutions[i]
    elif config["exp_type"] == "cluster_same":
        solutions, labels = cluster_code_snippets(raw_solutions)
        print(labels)
        print(
            f"Use {len(solutions) / len(raw_solutions)} part of solutions for clustering"
        )
        cnt_solutions = [0, 0, 0, 0, 0]
        label = -1
        for i in range(len(labels)):
            cnt_solutions[labels[i]] += 1
            if cnt_solutions[labels[i]] >= cnt_solutions[label]:
                label = labels[i]
        for i in range(len(labels)):
            if labels[i] == label and len(selected_solutions) < 5:
                selected_solutions.append(solutions[i])

    return selected_solutions


def main(
    output_file: str = config["output_file"],
    exp_type: str = config["exp_type"],
    problem_root_dir: str = os.getcwd(),
):
    output_file = Path(output_file)
    problem_root_dir = Path(problem_root_dir)
    cf_dataset = get_cf_dataset()

    # Result Dict
    if not output_file.exists():
        with open(output_file, "w") as file:
            json.dump({}, file)
    with open(output_file, "r") as file:
        results = json.load(file)

    filtered_problems = filter_problems(cf_dataset)
    for problem in tqdm(filtered_problems):
        print(problem["name"])
        if problem["name"] in results.keys():
            continue
        results[problem["name"]] = dict()
        problem_dir = problem_root_dir / str(problem["name"].split(".")[0])
        (
            input_dir,
            output_dir,
            gpt_input_dir,
            gpt_output_dir,
            solution_dir,
        ) = init_folder(problem_dir, exp_type)

        # Create prompt with problem description and 5 randomly picked solution codes
        sol_cnt = len(problem["solutions"]["solution"])
        raw_solutions = [
            problem["solutions"]["solution"][i]
            for i in range(sol_cnt)
            if problem["solutions"]["language"][i] == 4
        ]
        if len(raw_solutions) < 5:
            print("Not enough Java solutions to create the prompt.")
            continue

        selected_solutions = select_solutions(raw_solutions)

        test_gen_script_file = problem_dir / exp_type / "gen.py"
        if not test_gen_script_file.exists():
            cost = write_test_generator(
                problem_dir, exp_type, problem["description"], selected_solutions
            )
            print("Cost on API call:", cost)
            results[problem["name"]]["cost"] = cost

        if not (output_dir / "public_tests_01.out").exists():
            # Write test inputs/outputs
            for tests_type in {"public_tests", "private_tests", "generated_tests"}:
                # Write test inputs to file
                for test_idx, test_input in enumerate(
                    problem[tests_type]["input"], start=1
                ):
                    file_path = input_dir / f"{tests_type}_{test_idx:02}.in"
                    with open(file_path, "w") as file:
                        file.write(test_input)
                # Write test outputs to file
                for test_idx, test_output in enumerate(
                    problem[tests_type]["output"], start=1
                ):
                    file_path = output_dir / f"{tests_type}_{test_idx:02}.out"
                    with open(file_path, "w") as file:
                        file.write(test_output)

        # Execute gen.py and write its output to a file
        if not (gpt_input_dir / "test_01.in").exists():
            try:
                gen_result = subprocess.run(
                    ["python", test_gen_script_file.as_posix(), gpt_input_dir],
                    capture_output=True,
                    text=True,
                )
                gen_result.check_returncode()
            except subprocess.CalledProcessError as e:
                print("Error during execution of gen.py:", e)
                ill_tests = test_gen_script_file.read_text()
                cost = write_test_generator(
                    problem_dir,
                    exp_type,
                    problem["description"],
                    selected_solutions,
                    ill_tests=ill_tests,
                    error=e,
                )
                print("Cost on API call:", cost)
                results[problem["name"]]["cost"] = cost

    with open(output_file, "w") as file:
        file.write(json.dumps(results, indent=2))


if __name__ == "__main__":
    from fire import Fire

    Fire(main)
