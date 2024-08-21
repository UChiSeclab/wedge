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
) -> Tuple[List[int], List[str]]:
    """Selects all solutions for a given language. Note that only correct solutions are considered."""
    solutions = [
        (idx, solution)
        for idx, (solution, language) in enumerate(
            zip(problem["solutions"]["solution"], problem["solutions"]["language"])
        )
        if language == sol_language.value
    ]

    solution_idxs, raw_solutions = zip(*solutions) if solutions else ([], [])

    return list(solution_idxs), list(raw_solutions)


def __filter_solution_idx(
    problem_id: str, language: str, result: Dict, solution_idxs: List[int]
) -> List[int]:
    """Filter out the solution idxs that are not in the result (corner case).\
        Also filter out the solution idxs that are not AC."""
    black_listed_solution_ids = [
        "1056_A_java_0607",  # This solution's format is in a mess
        "484_B_java_0260",  # This solution does not work with cobertura
    ]

    filtered_solution_idxs = []
    for solution_idx in solution_idxs:
        if f"{problem_id}_{language}_{solution_idx:04}" in black_listed_solution_ids:
            print(f"{problem_id}_{language}_{solution_idx:04} is blacklisted")
            continue
        if f"solutions_{solution_idx:04}" in result:
            if all(
                v == "AC" for v in result[f"solutions_{solution_idx:04}"]["verdict"]
            ):
                filtered_solution_idxs.append(solution_idx)
            else:
                print(f"solutions_{solution_idx:04} is not AC")
        else:
            print(f"Solution {solution_idx} is not in the result")

    return filtered_solution_idxs


def select_solutions(
    problem_id: str, problem: Dict, prompt_language: Language
) -> Tuple[List[str], List[str]]:
    """Selects solutions for feeding to the llm. Note that only correct solutions are considered."""
    solution_idxs, raw_solutions = get_solutions_in_language(problem, prompt_language)
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
        filtered_solution_idxs = __filter_solution_idx(
            problem_id, prompt_language, result, solution_idxs
        )
        if len(filtered_solution_idxs) < 2:
            return selected_solution_idxs, selected_solutions
        filtered_solution_idxs.sort(
            key=lambda idx: mean(result[f"solutions_{idx:04}"]["average_time"])
        )
        fast_solution_idx = filtered_solution_idxs[0]
        slow_solution_idx = filtered_solution_idxs[-1]
        selected_solutions = [
            problem["solutions"]["solution"][fast_solution_idx],
            problem["solutions"]["solution"][slow_solution_idx],
        ]
        selected_solution_idxs = [fast_solution_idx, slow_solution_idx]

    selected_solution_ids = [f"solutions_{idx:04}" for idx in selected_solution_idxs]
    return selected_solution_ids, selected_solutions


def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    run_tests: bool = True,
    run_tests_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_template: str = "prompt_template.txt",
    feedback_prompt_type: Literal["diff_solution", "diff_input"] = None,
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
        print("problem_id:", problem_id)
        problem_dir = problem_root_dir / problem_id
        experiment_dir = problem_dir / experiment_name
        experiment_dir.mkdir(exist_ok=True, parents=True)
        selected_solution_ids, selected_solutions = select_solutions(
            problem_id, problem, config["prompt_language"]
        )

        if len(selected_solution_ids) < 2:
            print(f"Not enough solutions to select for {problem_id}", end=" ")
            print("language:", config["prompt_language"])
            continue

        test_generator_path = experiment_dir / "gen.py"

        if not test_generator_path.exists():
            if not config["manual_prompt"]:
                cost = write_test_generator(
                    experiment_dir,
                    problem,
                    selected_solutions,
                    prompt_template=prompt_template,
                    prompt_language=prompt_language,
                    feedback_prompt_type=feedback_prompt_type,
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
                    if "incorrect" in solution_file_name:
                        continue
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
