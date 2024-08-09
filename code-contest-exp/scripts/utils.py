"""Utility functions."""
import os
import pickle
from datasets import load_dataset, concatenate_datasets
import tiktoken
from typing import Dict
from pathlib import Path

from config import abandoned_list
from common import Language


def num_tokens_from_string(string: str, encoding_name: str = "cl100k_base") -> int:
    """Counts number of token for a given string."""
    encoding = tiktoken.get_encoding(encoding_name)
    num_tokens = len(encoding.encode(string))
    return num_tokens


def get_cf_problems():
    """Get Codeforces datasets

    Load the datasets from dataset.pkl (if existed) or code_contests
    and filter codeforces problems.

    Returns:
    - A list of all codeforces problems
    """
    # Load the dataset
    if os.path.exists("dataset.pkl"):
        with open("dataset.pkl", "rb") as file:
            all_problems = pickle.load(file)
    else:
        dataset_path = "/zp_goku/scratch_lb/casperwang/code_contests/data"
        parquet_files = [
            os.path.join(dataset_path, f)
            for f in os.listdir(dataset_path)
            if f.endswith(".parquet")
        ]

        # Load and combine datasets incrementally
        all_problems = None
        for parquet_file in parquet_files[:3]:
            temp_dataset = load_dataset("parquet", data_files=parquet_file)["train"]
            if all_problems is None:
                all_problems = temp_dataset
            else:
                all_problems = concatenate_datasets([all_problems, temp_dataset])

        with open(r"dataset.pkl", "wb") as file:
            pickle.dump(all_problems, file)

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
        if not any(abandoned in problem["description"] for abandoned in abandoned_list)
    ]
    return filtered_problems


def dump_solutions_if_not_exist(problem: Dict, solutions_dir: Path, language: Language):
    """Dump solutions if not exist"""
    solution_dir = solutions_dir / str(language)
    solution_dir.mkdir(exist_ok=True, parents=True)
    for sol_type in ["solutions", "incorrect_solutions"]:
        for solution in problem[sol_type]["solution"]:
            for solution_idx, solution in enumerate(problem[sol_type]["solution"]):
                if problem[sol_type]["language"][solution_idx] == language.value:
                    solution_file_name_stem = (
                        f"{sol_type}_{solution_idx:03}"  # file stem, no suffix
                    )
                    solution_file_path = (
                        solution_dir
                        / f"{solution_file_name_stem}.{language.to_suffix()}"
                    )
                    if not solution_file_path.exists():
                        with open(solution_file_path, "w", encoding="utf-8") as file:
                            file.write(solution)
