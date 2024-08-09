"""Generates input / output tests by prompting llm to write the test generator."""
import os
import subprocess
import random
from pathlib import Path
from typing import List, Dict
from tqdm import tqdm
from fire import Fire

from common import Language
from config import config
from utils import get_cf_problems, filter_problems
from gpt_caller import write_test_generator
from cluster import code_clustering
from run_java import run_java
from utils import dump_solutions_if_not_exist


def get_solutions_in_language(problem: Dict, sol_language: Language) -> List[str]:
    """Selects all solutions for a given language."""
    raw_solutions = [
        solution
        for solution_idx, solution in enumerate(problem["solutions"]["solution"])
        if problem["solutions"]["language"][solution_idx] == sol_language.value
    ]

    return raw_solutions


def select_solutions(problem: Dict, prompt_language: Language) -> List[str]:
    """Selects solutions for feeding to the llm."""
    raw_solutions = get_solutions_in_language(problem, prompt_language)
    if len(raw_solutions) < 5:
        print(f"Not enough {str(prompt_language)} solutions to create the prompt.")
        return []
    selected_solutions = []

    if config["solution_selection"] == "random":
        # Select 5 solutions randomly
        selected_solutions = random.sample(raw_solutions, 5)

    elif config["solution_selection"] == "cluster_diff":
        # Select 5 solutions from different clusters
        solutions, labels = code_clustering(raw_solutions)
        selected_solutions = ["", "", "", "", ""]
        for idx, label in enumerate(labels):
            selected_solutions[label] = solutions[idx]

    elif config["solution_selection"] == "cluster_same":
        # Select 5 solutions from the same cluster
        solutions, labels = code_clustering(raw_solutions)
        cnt_solutions = [0, 0, 0, 0, 0]
        max_label = -1
        for idx, label in enumerate(labels):
            cnt_solutions[label] += 1
            if cnt_solutions[label] >= cnt_solutions[max_label]:
                max_label = labels[idx]
        for idx, label in enumerate(labels):
            if labels[idx] == max_label and len(selected_solutions) < 5:
                selected_solutions.append(solutions[idx])

    return selected_solutions


def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    run_tests: bool = False,
    run_tests_language: Language = Language.JAVA,
):
    """Generates tests by test generator created by LLM.

    Args:
    - experiment_name: The folder name for the experiement
    - problem_root_dir: The directory to put the problem in
    """
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(get_cf_problems())

    for problem in tqdm(filtered_problems[:100]):
        problem_dir = problem_root_dir / str(problem["name"].split(".")[0])
        experiment_dir = problem_dir / experiment_name
        experiment_dir.mkdir(exist_ok=True, parents=True)
        selected_solutions = select_solutions(problem, config["prompt_language"])

        test_generator_path = experiment_dir / "gen.py"

        if not test_generator_path.exists():
            cost = write_test_generator(
                experiment_dir, problem["description"], selected_solutions
            )
            print("Cost on API call:", cost)
        else:
            raise FileExistsError("Test generator already exist.")

        # Execute gen.py and write its output to a file
        experiment_input_dir = experiment_dir / "input"
        experiment_input_dir.mkdir(exist_ok=True, parents=True)
        try:
            subprocess.run(
                [
                    "python",
                    test_generator_path.as_posix(),
                    experiment_input_dir.as_posix(),
                ],
                capture_output=True,
                text=True,
                check=True,
            ).check_returncode()
        except subprocess.CalledProcessError as e:
            print("Error during execution of gen.py:", e)
            continue

        if run_tests:
            experiment_output_dir = experiment_dir / "output"
            experiment_output_dir.mkdir(exist_ok=True, parents=True)

            if run_tests_language == Language.JAVA:
                solution_dir = problem_dir / "solutions" / str(run_tests_language)
                dump_solutions_if_not_exist(
                    problem, (problem_dir / "solutions"), run_tests_language
                )
                for solution_file_name in os.listdir(solution_dir):
                    run_java(
                        solution_dir,
                        solution_file_name,
                        experiment_input_dir,
                        experiment_output_dir,
                        write_output=True,
                    )
                    if len(os.listdir(experiment_input_dir)) == len(
                        os.listdir(experiment_output_dir)
                    ):
                        break
            else:
                raise NotImplementedError(
                    "Only JAVA is supported for running tests here."
                )


if __name__ == "__main__":
    Fire(main)
