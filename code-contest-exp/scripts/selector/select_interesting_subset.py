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
    # filtered_problems = filter_problems(
    #     get_cf_problems(use_specified_problem=config["use_specified_problem"]),
    #     filter_with_inconsistency_threshold=True
    # )
    # problem_ids = [problem["name"].split(".")[0] for problem in filtered_problems]
    problem_ids = ['1041_F', '1057_C', '1061_B', '1061_C', '1064_A', '1096_F', '1097_C', '1102_C', '1114_C', '111_B', '1141_A', '1201_C', '1203_D1', '1204_E', '1210_A', '1213_D1', '1216_E2', '1225_D', '1230_C', '1242_B', '1243_D', '1261_B1', '128_D', '131_E', '1322_B', '1340_B', '1340_C', '1355_E', '1359_E', '1397_B', '1408_D', '1428_E', '1433_F', '143_D', '1446_C', '16_B', '181_B', '191_B', '199_B', '237_C', '278_B', '2_A', '301_B', '321_B', '349_B', '351_E', '366_C', '44_B', '479_E', '484_B', '525_C', '529_E', '546_C', '554_C', '559_C', '574_D', '577_A', '583_D', '603_C', '63_B', '662_D', '663_B', '676_E', '687_C', '69_B', '713_C', '731_F', '758_A', '768_C', '768_E', '773_B', '774_J', '791_B', '803_F', '846_B', '863_B', '889_B', '898_E', '911_C', '926_B', '937_B', '993_A', '993_B', '996_B', '999_E', '999_F'] # >= 50 inputs
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
