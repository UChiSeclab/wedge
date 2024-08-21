import os
import json
from pathlib import Path
import pygount
from tqdm import tqdm
from multiprocessing import Pool, cpu_count
import random
from fire import Fire

from config import config
from utils import filter_problems, get_cf_problems


def main(problem_root_dir: str = config["problem_root_dir"], max_cpu: int = 96):
    """Calculate total lines of code for each problem and sort them."""
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(get_cf_problems())

    sorted_result_path = Path(f"results/loc_sorted.json")
    with open(sorted_result_path, "r", encoding="utf-8") as file:
        problem_locs = json.load(file)

    loc_dict = {}
    for (problem_id, loc) in tqdm(problem_locs):
        loc_dict[problem_id] = loc

    average_loc = []
    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]
        num_solutions = len(problem["solutions"]["solution"]) + len(
            problem["incorrect_solutions"]["solution"]
        )
        if num_solutions == 0:
            continue
        average_loc.append(
            (problem_id, loc_dict[problem_id] / num_solutions, num_solutions)
        )

    average_loc.sort(key=lambda x: x[1], reverse=True)

    # Output the results
    sorted_result_path = Path(f"results/average_loc_sorted.json")
    sorted_result_path.parent.mkdir(parents=True, exist_ok=True)
    with open(sorted_result_path, "w", encoding="utf-8") as file:
        json.dump(average_loc, file, indent=4)


if __name__ == "__main__":
    Fire(main)
