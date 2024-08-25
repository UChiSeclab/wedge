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
from utils import get_cf_problems, filter_problems, mean, get_alphacode_result, \
    record_failing_problem
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


def __solution_too_long(problem:Dict, solution_idx:int, threshold:int=500) -> bool:
    """check if the solution is longer than {threshold} lines"""
    solution_code = problem["solutions"]["solution"][solution_idx]
    solution_len = len(solution_code.splitlines())
    
    return solution_len > threshold


def __filter_solution_idx(
    problem:Dict, problem_id: str, language: str, result: Dict, solution_idxs: List[int]
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
        if __solution_too_long(problem, solution_idx):
            print(f"solutions_{solution_idx:04} is too long")
            continue
        if f"solutions_{solution_idx:04}" in result:
            if all(
                v == "AC" for v in result[f"solutions_{solution_idx:04}"]["verdict"]
            ):
                filtered_solution_idxs.append(solution_idx)
            else:
                print(f"solutions_{solution_idx:04} is not AC")
        else:
            print(f"Solution {solution_idx} is not in the result") # TODO: to be investigated and fixed

    return filtered_solution_idxs


def select_solutions(
    problem_id: str, problem: Dict, solution_selection_type:str, prompt_language: Language, top_k: int = None
) -> Tuple[List[str], List[str]]:
    """Selects solutions for feeding to the llm. Note that only correct solutions are considered."""
    solution_idxs, raw_solutions = get_solutions_in_language(problem, prompt_language)
    selected_solutions = []
    selected_solution_idxs = []

    if solution_selection_type == "random":
        # Select 5 solutions randomly
        selected_solution_idxs = random.sample(solution_idxs, 5)
        selected_solutions = [
            problem["solutions"]["solution"][idx] for idx in selected_solution_idxs
        ]

    elif solution_selection_type == "cluster_diff":
        # Select 5 solutions from different clusters
        solutions, labels = code_clustering(raw_solutions)
        selected_solutions = ["", "", "", "", ""]
        for idx, label in enumerate(labels):
            selected_solutions[label] = solutions[idx]
            # TODO: set selected_solution_idxs

    elif solution_selection_type == "cluster_same":
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

    elif solution_selection_type == "time_contrast":
        alphacode_dir = Path("./results/alphacode")
        with open(alphacode_dir / f"{problem_id}.json", "r", encoding="utf-8") as file:
            result = json.load(file)
        filtered_solution_idxs = __filter_solution_idx(
            problem, problem_id, prompt_language, result, solution_idxs
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

    elif solution_selection_type == "multi_slow":
        alphacode_dir = Path("./results/alphacode")
        with open(alphacode_dir / f"{problem_id}.json", "r", encoding="utf-8") as file:
            result = json.load(file)
        filtered_solution_idxs = __filter_solution_idx(
            problem, problem_id, prompt_language, result, solution_idxs
        )
        # select top k slow solutions
        assert top_k is not None and top_k > 1, f"top_k: {top_k}"
        # assert len(filtered_solution_idxs) >= top_k, f"len(filtered_solution_idxs): {len(filtered_solution_idxs)}"
        filtered_solution_idxs.sort(
            key=lambda idx: mean(result[f"solutions_{idx:04}"]["average_time"]), reverse=True
        )
        selected_solution_idxs = filtered_solution_idxs[:top_k]
        selected_solutions = [
            problem["solutions"]["solution"][idx] for idx in selected_solution_idxs
        ]

    else:
        raise ValueError(f"Unknown solution selection type: {solution_selection_type}")

    selected_solution_ids = [f"solutions_{idx:04}" for idx in selected_solution_idxs]
    return selected_solution_ids, selected_solutions


def create_test_generator(
    problem,
    selected_solutions,
    experiment_dir: Path,
    experiment_input_dir: Path,
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_template: str = "prompt_template.txt",
    feedback_prompt_type: Literal["diff_solution", "diff_input", "multi_solution_diff_input"] = None,
    clear_input_dir: bool = True,
) -> bool:
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

    if clear_input_dir:
        [f.unlink() for f in experiment_input_dir.iterdir()]

    try:
        subprocess.run(
            [
                "python",
                (experiment_dir / "gen.py").as_posix(),
                experiment_input_dir.as_posix(),
            ],
            capture_output=True,
            text=True,
            check=True,
            timeout=300
        ).check_returncode()
    except subprocess.CalledProcessError as e:
        print("Error during execution of gen.py:", e)
        return False
    except subprocess.TimeoutExpired as e:
        print("Timeout during execution of gen.py:", e)
        return False

    return True

def create_test_generator_with_retry(
    experiment_name: str,
    problem,
    selected_solutions,
    experiment_dir: Path,
    experiment_input_dir: Path,
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_template: str = "prompt_template.txt",
    feedback_prompt_type: Literal["diff_solution", "diff_input", "multi_solution_diff_input"] = None,
    max_retry: int = 10,
    run_tests: bool = True,
    run_tests_language: Literal["python", "cpp", "python3", "java"] = "java",
) -> bool:
    problem_id = problem["name"].split(".")[0]
    alphacode_result = get_alphacode_result(problem_id)
    try_cnt = 0
    experiment_output_dir = experiment_dir / "output"
    experiment_output_dir.mkdir(exist_ok=True, parents=True)
    while try_cnt < max_retry and \
        (len(os.listdir(experiment_input_dir)) < config["num_tests"] / 2 or \
            (run_tests and \
                len(os.listdir(experiment_output_dir)) < len(os.listdir(experiment_input_dir)) / 2)):
        # Retry if the test generator fails to generate enough tests
        try_cnt += 1
        print(f"[INFO] gen_tests try {try_cnt}th for {problem_id}")

        gen_py_success = create_test_generator(
            problem,
            selected_solutions,
            experiment_dir,
            experiment_input_dir,
            prompt_language,
            prompt_template,
            feedback_prompt_type,
        )
        
        if not gen_py_success:
            print(f"[Error] gen_tests failed to generate tests for {problem_id}, try count: {try_cnt}")
            continue

        if run_tests:
            if run_tests_language == str(Language.JAVA):
                solution_dir = experiment_dir.parent / "solutions" / str(run_tests_language)
                for solution_file_name in os.listdir(solution_dir):
                    if "incorrect" in solution_file_name:
                        continue
                    alphacode_verdict = alphacode_result[solution_file_name.split(".")[0]]["verdict"]
                    if all(v == "AC" for v in alphacode_verdict):
                        run_solution(
                            experiment_name,
                            solution_dir / solution_file_name,
                            Language.JAVA,
                            experiment_input_dir,
                            experiment_output_dir,
                            write_output=True,
                        )
                        if len(os.listdir(experiment_output_dir)) >= len(os.listdir(experiment_input_dir)) / 2:
                            break
                        else:
                            [f.unlink() for f in experiment_output_dir.iterdir()]

                if len(os.listdir(experiment_output_dir)) < len(os.listdir(experiment_input_dir)) / 2:
                    print(f"[Warning] too manys inputs are invalid as so few output is generated for {problem_id} from all solutions, try count: {try_cnt}")
            else:
                raise NotImplementedError(
                    "Only JAVA is supported for running tests here."
                )

    return (len(os.listdir(experiment_input_dir)) >= config["num_tests"] / 2 and \
        (not run_tests or len(os.listdir(experiment_output_dir)) >= len(os.listdir(experiment_input_dir)) / 2))

def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    run_tests: bool = True,
    run_tests_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_template: str = "prompt_template.txt",
    feedback_prompt_type: Literal["diff_solution", "diff_input", "multi_solution_diff_input"] = None,
    solution_selection_type: Literal["random", "time_contrast", "multi_slow"] = config["solution_selection"],
    top_k: int = None,
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
            problem_id, problem, solution_selection_type, Language.str_to_lang(prompt_language), top_k=top_k
        )

        if len(selected_solution_ids) < 2 or (top_k and len(selected_solution_ids) < top_k):
            print(f"Not enough solutions to select for {problem_id}", end=" ")
            print("language:", prompt_language)
            continue

        experiment_input_dir = experiment_dir / "input"
        experiment_input_dir.mkdir(exist_ok=True, parents=True)
        test_generator_path = experiment_dir / "gen.py"
        if test_generator_path.exists() and len(os.listdir(experiment_input_dir)) > 0\
            and (not run_tests or len(os.listdir(experiment_dir / "output")) > 0):
            print(f"Experiment {experiment_name} already exists for {problem_id}")
            continue

        if not create_test_generator_with_retry(
            experiment_name,
            problem,
            selected_solutions,
            experiment_dir,
            experiment_input_dir,
            prompt_language=prompt_language,
            prompt_template=prompt_template,
            feedback_prompt_type=feedback_prompt_type,
            run_tests=run_tests,
            run_tests_language=run_tests_language,
        ):
            print(f"[Error] Failed to generate enough valid tests for {problem_id}")
            record_failing_problem(problem_id, experiment_name, "Failed to generate enough valid tests")

if __name__ == "__main__":
    Fire(main)
