"""Utility functions."""
import os
import pickle
from datasets import load_dataset
import tiktoken

from config import problem_list, abandoned_list

def num_tokens_from_string(string: str, encoding_name: str = "cl100k_base") -> int:
    """Counts number of token for a given string."""
    encoding = tiktoken.get_encoding(encoding_name)
    num_tokens = len(encoding.encode(string))
    return num_tokens

def get_cf_problems():
    """Get Codeforces datasets
    
    Load the datasets from dataset.pkl (if existed) or deepmind/code_contests
    and filter codeforces problems.

    Returns:
    - A list of all codeforces problems
    """
    # Load the dataset
    if os.path.exists("dataset.pkl"):
        with open("dataset.pkl", "rb") as file:
            dataset = pickle.load(file)
    else:
        dataset = load_dataset("deepmind/code_contests")
    all_problems = dataset["train"]

    # Filter codeforces data
    cf_problems = all_problems.filter(lambda example: example["source"] == 2)

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
        if problem["name"] in problem_list
        and not any(abandoned in problem["description"] for abandoned in abandoned_list)
    ]
    return filtered_problems