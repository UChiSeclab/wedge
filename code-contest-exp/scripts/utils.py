"""Utility functions."""
import os
import pickle
from datasets import load_dataset
import tiktoken
from typing import Dict, List, Literal
from pathlib import Path
import json
import datetime

from config import abandoned_list, config


def squeeze_time_dict(
    time_dict: Dict[str, List[float]], use_max_or_avg: Literal["max", "avg"]
) -> Dict[str, float]:
    result = {}
    for input_name, time_list in time_dict.items():
        if use_max_or_avg == "max":
            result[input_name] = max(time_list)
        elif use_max_or_avg == "avg":
            result[input_name] = sum(time_list) / len(time_list)

    return result


def get_alphacode_result(problem_id: str) -> Dict:
    return get_experiment_result(problem_id, "alphacode")

def get_experiment_result(problem_id: str, experiment_name: str) -> Dict:
    experiment_result_dir = Path(config["result_root_dir"]) / experiment_name
    with open(experiment_result_dir / f"{problem_id}.json", "r", encoding="utf-8") as file:
        result = json.load(file)
    return result


def record_failing_problem(problem_id: str, experiment_name: str, reason: str, try_cnt: int = 10):
    """Record the failing problem in the failing_problems.json"""
    failing_problems = {}
    failing_problems_path = Path(config["gen_tests_failing_problem_record"])
    if failing_problems_path.exists():
        with open(failing_problems_path, "r") as file:
            failing_problems = json.load(file)
    failing_problems[problem_id] = failing_problems.get(problem_id, {})

    # Skip if the experiment_name is already in the record
    if experiment_name in failing_problems[problem_id]:
        return

    failing_problems[problem_id][experiment_name] = {
        "reason": reason,
        "try_cnt": try_cnt,
        "timestamp": str(datetime.datetime.now())
    }
    with open(failing_problems_path, "w") as file:
        json.dump(failing_problems, file, indent=4)


def problem_test_gen_failed(problem_id: str, experiment_name: str):
    gen_tests_failing_problem_record = config["gen_tests_failing_problem_record"]
    if not os.path.exists(gen_tests_failing_problem_record):
        return False
    data = json.load(open(gen_tests_failing_problem_record, "r"))
    return problem_id in data and experiment_name in data[problem_id]


def problem_has_failed_test_gen(problem_id: str):
    gen_tests_failing_problem_record = config["gen_tests_failing_problem_record"]
    if not os.path.exists(gen_tests_failing_problem_record):
        return False
    data = json.load(open(gen_tests_failing_problem_record, "r"))
    return problem_id in data

def mean(lst):
    return sum(lst) / len(lst)


def num_tokens_from_string(string: str, encoding_name: str = "cl100k_base") -> int:
    """Counts number of token for a given string."""
    encoding = tiktoken.get_encoding(encoding_name)
    try:
        num_tokens = len(encoding.encode(string))
    except Exception as e:
        print(f"Error: {e}")
        print(f"String: {string}")
        num_tokens = 0
    return num_tokens


def get_cf_problems(use_specified_problem: bool = False):
    """Get Codeforces datasets.

    Load the datasets from dataset.pkl (if existed) or code_contests
    and filter codeforces problems.

    Returns:
    - A list of all codeforces problems
    """
    # Load the dataset
    if not use_specified_problem:
        if os.path.exists("dataset.pkl"):
            with open("dataset.pkl", "rb") as file:
                cf_problems = pickle.load(file)
        else:
            dataset = load_dataset("deepmind/code_contests")
            all_problems = dataset["train"]

            # Filter codeforces data
            cf_problems = all_problems.filter(lambda example: example["source"] == 2)
            with open(r"dataset.pkl", "wb") as file:
                pickle.dump(cf_problems, file)
    else:
        assert len(config["specified_problem"]) > 0, "No specified problem is provided."
        if os.path.exists("specified_problem_dataset.pkl"):
            with open(r"specified_problem_dataset.pkl", "rb") as file:
                cf_problems = pickle.load(file)
                cf_problems = cf_problems.filter(
                    lambda example: example["name"].split(".")[0]
                    in config["specified_problem"]
                )
        else:
            dataset = load_dataset("deepmind/code_contests")
            all_problems = dataset["train"]

            # Filter codeforces data
            cf_problems = all_problems.filter(lambda example: example["source"] == 2)
            cf_problems = cf_problems.filter(
                lambda example: example["name"].split(".")[0]
                in config["specified_problem"]
            )
            with open(r"specified_problem_dataset.pkl", "wb") as file:
                pickle.dump(cf_problems, file)

    return cf_problems


def filter_problems(problems, filter_with_inconsistency_threshold=True):
    """Filter problems.

    Skip if the problem accept multiple answers or the problem is not on the list.
    Skip if the problem has >5% "correct" solutions that do not pass the test cases.

    Args:
    - A list of problems

    Returns:
    - A list of filtered problems.
    """
    filtered_problems = [
        problem
        for problem in problems
        if not any(abandoned in problem["description"] for abandoned in abandoned_list)
    ]
    
    if filter_with_inconsistency_threshold:
        alphacode_dir = Path(config["result_root_dir"]) / "alphacode"
        filtered_problems_with_inconsistency = []
        for problem in filtered_problems:
            problem_name = problem["name"].split(".")[0]
            # hack, to be removed
            if problem_name in ["1105_E"]:
                print(f"hack: filtered on vegeta {problem_name}")
                continue
            result_path = alphacode_dir / f"{problem_name}.json"
            labeled_correct = 0
            actual_correct = 0
            data = json.load(open(result_path, "r"))
            for item in data:
                if item.startswith("solutions_"):
                    labeled_correct += 1
                    if all(label in ["AC", "TLE"] for label in data[item]["verdict"]):
                        actual_correct += 1

            if actual_correct / labeled_correct > 0.95:
                filtered_problems_with_inconsistency.append(problem)
            else:
                print(f"Problem {problem_name} has more than 5% incorrect solutions labeled as correct.")

        return filtered_problems_with_inconsistency
    else:
        return filtered_problems

def get_run_time(experiment_result: Dict, solution_id: str, input_id: str) -> float:
    """Get the run time of the solution on the input."""
    time_dict = experiment_result[solution_id]["time_dict"]
    run_time_list = time_dict[input_id]

    return mean(run_time_list)
