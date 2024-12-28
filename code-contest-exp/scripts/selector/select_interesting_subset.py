import json
import os, sys
from pathlib import Path
from typing import List, Tuple, Dict, Optional

from config import config
from common import Language
from utils import filter_problems, get_cf_problems

def get_max_median_mean_input_size(problem_id: str, strategy: Optional[str] = "") -> Tuple[int, int, int]:
    """get the max, median and mean input size (number of elements) of the input files of the problem"""
    problem_root_dir = Path(config["problem_root_dir"])
    problem_dir = problem_root_dir / problem_id
    if strategy:
        input_dir = problem_dir / strategy / "input"
    else:
        input_dir = problem_dir / "input"
    if not input_dir.exists() or len(list(input_dir.glob("*.in"))) == 0:
        return 0, 0, 0
    input_files = list(input_dir.glob("*.in"))
    input_sizes = [len(open(input_file, "r").read().split()) for input_file in input_files]

    return max(input_sizes), sorted(input_sizes)[len(input_sizes) // 2], sum(input_sizes) / len(input_sizes)

def problem_not_size_sensitive(problem_id: str, input_size_threshold: int = 50) -> bool:
    """Check if the problem is not size sensitive"""
    max_input_size, median_input_size, mean_input_size = get_max_median_mean_input_size(problem_id, "plain_problem")
    return max_input_size <= input_size_threshold

if __name__ == '__main__':
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"]),
        filter_with_inconsistency_threshold=True
    )
    problem_ids = [problem["name"].split(".")[0] for problem in filtered_problems]
    valid_problem_ids = problem_ids.copy()

    problem_size_dict = {"alphacode": {}, "plain_problem": {}}
    for problem_id in problem_ids:
        max_input_size, median_input_size, mean_input_size = get_max_median_mean_input_size(problem_id)
        plain_problem_max_input_size, plain_problem_median_input_size, plain_problem_mean_input_size = get_max_median_mean_input_size(problem_id, "plain_problem")
        if (plain_problem_max_input_size, plain_problem_median_input_size, plain_problem_mean_input_size) == (0, 0, 0):
            print(f"problem {problem_id} has no plain_problem input")
            valid_problem_ids.remove(problem_id)
            continue
        # print(f"{problem_id}: {max_input_size} {median_input_size} {mean_input_size}")
        problem_size_dict["alphacode"][problem_id] = (max_input_size, median_input_size, mean_input_size)
        problem_size_dict["plain_problem"][problem_id] = (plain_problem_max_input_size, plain_problem_median_input_size, plain_problem_mean_input_size)

    print(problem_size_dict["plain_problem"].keys())

    print("number of problems:", len(valid_problem_ids))
    print("problems max input size < 50:", [problem_id for problem_id in valid_problem_ids if problem_size_dict["alphacode"][problem_id][0] < 50])
    print("problems 'plain_problem' max input size < 50:", [problem_id for problem_id in valid_problem_ids if problem_size_dict["plain_problem"][problem_id][0] < 50])
    for problem_id in valid_problem_ids:
        if problem_size_dict["plain_problem"][problem_id][0] < 50:
            print((Path(config["problem_root_dir"]) / problem_id / "problem_statement.txt").absolute())
        # if problem_size_dict["plain_problem"][problem_id][0] == 1:
        #     print("only 1 element", (Path(config["problem_root_dir"]) / problem_id / "problem_statement.txt").absolute())
    # print("problems median input size < 50:", [problem_id for problem_id in problem_ids if problem_size_dict[problem_id][1] < 50])
    # print("problems mean input size < 50:", [problem_id for problem_id in problem_ids if problem_size_dict[problem_id][2] < 50])
