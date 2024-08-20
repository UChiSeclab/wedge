"""Utility functions."""
import os
import pickle
from datasets import load_dataset, concatenate_datasets
import tiktoken
from typing import Dict
from pathlib import Path

from config import abandoned_list, config
from common import Language


def mean(lst):
    return sum(lst) / len(lst)


def num_tokens_from_string(string: str, encoding_name: str = "cl100k_base") -> int:
    """Counts number of token for a given string."""
    encoding = tiktoken.get_encoding(encoding_name)
    num_tokens = len(encoding.encode(string))
    return num_tokens


def get_cf_problems(use_specified_problem: bool = False):
    """Get Codeforces datasets

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


def filter_problems(problems):
    """Filters problems

    Skip if the problem accept multiple answers or the problem is not on the list.

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
    return filtered_problems
