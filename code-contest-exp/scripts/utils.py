"""Utility functions."""
import os
import pickle
from datasets import load_dataset, concatenate_datasets
import tiktoken

from config import abandoned_list

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
        parquet_files = [os.path.join(dataset_path, f) for f in os.listdir(dataset_path) if f.endswith('.parquet')]
        
        # Load and combine datasets incrementally
        all_problems = None
        for parquet_file in parquet_files[:3]:
            temp_dataset = load_dataset('parquet', data_files=parquet_file)['train']
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