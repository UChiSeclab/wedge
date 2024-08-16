"""Generates input / output tests by prompting llm to write the test generator."""
import os
import subprocess
import random
from pathlib import Path
from typing import List, Dict, Literal, Tuple
from tqdm import tqdm
from fire import Fire
import json

from common import Language
from config import config
from utils import get_cf_problems, filter_problems, mean
from gpt_caller import write_test_generator
from cluster import code_clustering
from run import run_solution


def get_solutions_in_language(
    problem: Dict, sol_language: Language
) -> Tuple[List[str], List[int]]:
    """Selects all solutions for a given language."""
    solutions = [
        (solution, idx)
        for idx, (solution, language) in enumerate(
            zip(problem["solutions"]["solution"], problem["solutions"]["language"])
        )
        if language == sol_language.value
    ]

    raw_solutions, solution_idxs = zip(*solutions) if solutions else ([], [])

    return list(raw_solutions), list(solution_idxs)


def select_solutions(
    problem_id: str, problem: Dict, prompt_language: Language
) -> Tuple[List[int], List[str]]:
    """Selects solutions for feeding to the llm."""
    raw_solutions, solution_idxs = get_solutions_in_language(problem, prompt_language)
    selected_solutions = []
    selected_solution_idxs = []

    if config["solution_selection"] == "random":
        # Select 5 solutions randomly
        selected_solution_idxs = random.sample(solution_idxs, 5)
        selected_solutions = [
            problem["solutions"]["solution"][idx] for idx in selected_solution_idxs
        ]

    elif config["solution_selection"] == "cluster_diff":
        # Select 5 solutions from different clusters
        solutions, labels = code_clustering(raw_solutions)
        selected_solutions = ["", "", "", "", ""]
        for idx, label in enumerate(labels):
            selected_solutions[label] = solutions[idx]
            # TODO: set selected_solution_idxs

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
                # TODO: set selected_solution_idxs

    elif config["solution_selection"] == "time_contrast":
        alphacode_dir = Path("./results/alphacode")
        with open(alphacode_dir / f"{problem_id}.json", "r", encoding="utf-8") as file:
            result = json.load(file)
        solution_idxs.sort(
            key=lambda idx: mean(result[f"solutions_{idx:04}"]["average_time"])
        )
        fast_solution_id = solution_idxs[0]
        slow_solution_id = solution_idxs[-1]
        selected_solutions = [
            problem["solutions"]["solution"][fast_solution_id],
            problem["solutions"]["solution"][slow_solution_id],
        ]
        selected_solution_idxs = [fast_solution_id, slow_solution_id]

    return selected_solution_idxs, selected_solutions


def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    run_tests: bool = True,
    run_tests_language: Literal["python", "cpp", "python3", "java"] = "java",
):
    """Generates tests by test generator created by LLM.

    Args:
    - experiment_name: The folder name for the experiement
    - problem_root_dir: The directory to put the problem in
    """
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]
        problem_dir = problem_root_dir / problem_id
        experiment_dir = problem_dir / experiment_name
        experiment_dir.mkdir(exist_ok=True, parents=True)
        _, selected_solutions = select_solutions(
            problem_id, problem, config["prompt_language"]
        )

        test_generator_path = experiment_dir / "gen.py"

        if not test_generator_path.exists():
            if not config["manual_prompt"]:
                cost = write_test_generator(
                    experiment_dir, problem["description"], selected_solutions
                )
                print("Cost on API call:", cost)
            else:
                print("Write file into", experiment_dir / "gen.py")
                input("Press Enter to continue...")
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

            if run_tests_language == str(Language.JAVA):
                solution_dir = problem_dir / "solutions" / str(run_tests_language)
                for solution_file_name in os.listdir(solution_dir):
                    run_solution(
                        solution_dir,
                        solution_file_name,
                        Language.JAVA,
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
