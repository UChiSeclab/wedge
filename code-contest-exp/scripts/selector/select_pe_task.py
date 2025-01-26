from pathlib import Path
from fire import Fire
import os
from typing import List, Any, Dict
import numpy as np
import matplotlib.pyplot as plt
import json
import csv
from functools import partial
from multiprocessing import Pool, cpu_count
from tqdm import tqdm

from utils import get_alphacode_result, problem_to_id, mean, have_multiple_correct_solutions, get_cf_problems
from config import config

"""
credit to: https://github.com/evalplus/evalplus. code partially borrowed from evalplus
"""

INSTRUCTION_COUNT_THRESHOLD = 100000
CORRECT_SOLUTION_CNT_THRESHOLD = 10
TEST_INPUT_CNT_THRESHOLD = 5
CONSISTENCY_THRESHOLD = 0.95
CV_THRESHOLD = 150

CACHE_DIR = "cache"  # Directory to store cached results
os.makedirs(CACHE_DIR, exist_ok=True)  # Ensure the directory exists

def load_from_cache(problem_id):
    """Loads cached data for a problem if it exists."""
    cache_path = os.path.join(CACHE_DIR, f"{problem_id}.json")
    if os.path.exists(cache_path):
        with open(cache_path, "r") as f:
            return json.load(f)
    return {}

def save_to_cache(problem_id, data):
    """Saves data to the cache for a problem."""
    cache_path = os.path.join(CACHE_DIR, f"{problem_id}.json")
    with open(cache_path, "w") as f:
        json.dump(data, f)

def get_cached_result(problem_id, key, compute_func):
    """Gets a cached result or computes and caches it."""
    cached_result = load_from_cache(problem_id)
    if key in cached_result:
        # print(f"[INFO] Using cached result for {problem_id} ({key})")
        return cached_result[key]

    result = compute_func()
    cached_result[key] = result
    # print(f"[INFO] Caching result for {problem_id} ({key})")
    save_to_cache(problem_id, cached_result)
    return result

def get_min_instruction_cnt(problem_id, result_dict: Dict[str, Any]) -> int:
    """Returns the minimum instruction count with caching."""
    def compute_min_instruction_cnt():
        min_instruction_cnt = 1000000000
        assert len(result_dict) > 0
        for solution_id in result_dict:
            if solution_id == "time_limit":
                continue
            if not all(label in ["AC", "TLE"] for label in result_dict[solution_id]["verdict"]):
                continue
            instruction_cnt_dict = result_dict[solution_id]["instruction_cnt_dict"]
            solution_min_instruction_cnt = min(
                [mean(instruction_cnt_list) for instruction_cnt_list in instruction_cnt_dict.values()]
            )
            min_instruction_cnt = min(min_instruction_cnt, solution_min_instruction_cnt)
        return min_instruction_cnt

    return get_cached_result(problem_id, "min_instruction_cnt", compute_min_instruction_cnt)

def get_problem_cv(problem_id, result_dict: Dict[str, Any]) -> float:
    """Returns the coefficient of variation of the problem with caching."""
    def compute_problem_cv():
        instruction_cnts = []
        assert len(result_dict) > 0
        for solution_id in result_dict:
            if solution_id == "time_limit":
                continue
            if not all(label in ["AC", "TLE"] for label in result_dict[solution_id]["verdict"]):
                continue
            avg_instruction_cnt = mean(result_dict[solution_id]["average_instruction_cnt"])
            instruction_cnts.append(avg_instruction_cnt)
        return np.std(instruction_cnts) / np.mean(instruction_cnts) * 100

    return get_cached_result(problem_id, "problem_cv", compute_problem_cv)

def get_problem_consistency(problem_id, result_dict):
    def compute_consistency():
        labeled_correct = 0
        actual_correct = 0
        assert len(result_dict) > 0
        for solution_id in result_dict:
            if solution_id.startswith("solutions_"):
                labeled_correct += 1
                if all(label in ["AC", "TLE"] for label in result_dict[solution_id]["verdict"]):
                    actual_correct += 1
        return actual_correct / labeled_correct
    
    return get_cached_result(problem_id, "consistency", compute_consistency)


def init_worker(shared_result_dict):
    global result_dict_dict
    result_dict_dict = shared_result_dict

def apply_filter_worker(problem, filter_func, threshold):
    return filter_func(problem, result_dict_dict, threshold)

def filter_problems_parallel(filter_func, problems, result_dict_dict, threshold):
    """Filters problems in parallel using a shared result dictionary."""
    with Pool(
        processes=min(cpu_count(), 8),  # Limit processes to avoid overloading the system
        initializer=init_worker,
        initargs=(result_dict_dict,),
    ) as pool:
        results = list(
            tqdm(pool.imap(partial(apply_filter_worker, filter_func=filter_func, threshold=threshold), problems),
                 total=len(problems))
        )
    return [problem for problem, keep in zip(problems, results) if keep]

def filter_by_compute_cost_single(problem, result_dict_dict, threshold):
    problem_id = problem_to_id(problem)
    result = result_dict_dict.get(problem_id, {})
    min_instruction_cnt = get_min_instruction_cnt(problem_id, result)
    return min_instruction_cnt >= threshold

def filter_by_correct_solution_cnt_single(problem, result_dict_dict, threshold):
    return have_multiple_correct_solutions(problem, threshold)

def filter_by_test_input_cnt_single(problem, result_dict_dict, threshold):
    public_test_input_cnt = len(problem["public_tests"]["input"])
    private_test_input_cnt = len(problem["private_tests"]["input"])
    test_input_cnt = public_test_input_cnt + private_test_input_cnt
    return test_input_cnt >= threshold

def filter_by_consistency_single(problem, result_dict_dict, threshold):
    problem_id = problem_to_id(problem)
    result = result_dict_dict.get(problem_id, {})
    problem_consistency = get_problem_consistency(problem_id, result)
    return problem_consistency >= threshold

def filter_by_cv_single(problem, result_dict_dict, threshold):
    problem_id = problem_to_id(problem)
    result = result_dict_dict.get(problem_id, {})
    try:
        problem_cv = get_problem_cv(problem_id, result)
    except Exception as e:
        print(e)
        print(f"problem_id: {problem_id}")
        return False
    return problem_cv >= threshold

def load_result(problem):
    problem_id = problem_to_id(problem)
    result_dict = get_alphacode_result(problem_id)
    return problem_id, result_dict

def load_required_results_parallel(problems) -> Dict[str, Dict[str, Any]]:
    required_properties = ["min_instruction_cnt", "problem_cv", "consistency"]
    problem_id_list = [problem_to_id(problem) for problem in problems]
    computed_problem_list = []
    for problem_id in problem_id_list:
        cached_result_file = os.path.join(CACHE_DIR, f"{problem_id}.json")
        if os.path.exists(cached_result_file):
            with open(cached_result_file, "r") as f:
                cached_result = json.load(f)
                if all(prop in cached_result for prop in required_properties):
                    computed_problem_list.append(problem_id)

    remaining_problems = [problem for problem in problems if problem_to_id(problem) not in computed_problem_list]
    print(f"Number of remaining problems to be calculalted: {len(remaining_problems)}")

    return load_results_parallel(remaining_problems)

def load_results_parallel(problems):
    """Loads alphacode results for problems in parallel."""

    with Pool(int(0.5 * cpu_count())) as pool:
        results = list(tqdm(pool.imap(load_result, problems), total=len(problems)))

    return {problem_id: result_dict for problem_id, result_dict in results}

def main():
    """Selects performance-exercising tasks."""
    problems = get_cf_problems(use_specified_problem=True)
    black_list = ["1003_B"] # alphacode tests run forever
    black_list += ["378_C", "687_A", "1328_F", "1148_C", "1396_A", "141_C", "377_A", "936_A", "1458_B", "1427_D", "1406_C", "1025_B", "1523_B", "1325_C", "389_D", "879_C", "980_A", "1269_A", "937_C", "406_B", "146_D", "1348_D", "29_C", "323_A", "415_C", "22_C", "327_D", "659_B", "1364_A", "1282_B1", "1526_D", "1440_C1", "1427_E", "1474_C", "1385_A", "1266_C", "329_C", "630_P", "749_B", "509_B", "66_C", "516_E", "936_C", "1324_F", "506_E", "1154_A", "1325_F", "1498_D", "1520_C", "512_A", "814_B", "815_A", "1467_A", "688_C", "847_C", "995_A", "1129_B", "1082_D", "1472_E", "1162_A", "1367_F1", "1453_D", "1409_D", "584_C", "1331_F", "1157_D", "1342_D", "1364_D", "1450_C1", "1148_D", "1438_D", "1005_E1", "1283_C", "1477_C", "1504_A", "1461_A", "1095_A", "1505_B", "452_B", "1075_A", "85_A", "1316_D", "1517_C", "598_C", "1254_A", "1495_C", "995_C", "1264_C", "1508_A", "1266_G", "1421_C", "1130_E", "1511_B", "281_C", "1515_F", "686_B", "1028_E", "1503_A", "1384_A", "26_C", "1469_D", "1505_E", "1068_C", "1157_C1", "1342_B", "1450_A", "388_B", "878_A", "1462_C", "798_D", "290_B", "1382_C2", "1157_G", "177_E1", "1294_F", "1536_A", "1015_D", "1399_E1", "622_C", "977_F", "1343_D", "280_A", "906_B", "278_D", "1439_A1", "1526_A", "1040_B", "135_B", "1439_A2", "1420_C1", "1176_E", "1497_C2", "996_E", "1108_E1", "1196_E", "1213_G", "1237_C1", "1032_B", "816_C", "1474_E", "1343_B", "1278_E", "1237_A", "291_D", "316_E1", "998_A", "1301_D", "1031_C", "376_C", "1118_E", "327_B", "510_C", "656_A", "1118_F1", "1509_A", "708_B", "1514_C", "1088_A", "241_G", "290_A", "550_D", "996_C", "989_C", "1515_A", "916_C", "346_B", "414_A", "472_A", "1374_F", "1485_D", "1397_C"]
    problems = [problem for problem in problems if problem_to_id(problem) not in black_list]
    print(f"Total number of problems: {len(problems)}")

    result_dict_dict = load_required_results_parallel(problems)
    print("alphacode results loaded.")

    filtered_problems = problems

    filtered_problems = filter_problems_parallel(filter_by_compute_cost_single, problems, result_dict_dict, INSTRUCTION_COUNT_THRESHOLD)
    print(f"Number of problems after filtering by compute cost: {len(filtered_problems)}")

    filtered_problems = filter_problems_parallel(filter_by_correct_solution_cnt_single, filtered_problems, result_dict_dict, CORRECT_SOLUTION_CNT_THRESHOLD)
    print(f"Number of problems after filtering by correct solution count: {len(filtered_problems)}")

    filtered_problems = filter_problems_parallel(filter_by_test_input_cnt_single, filtered_problems, result_dict_dict, TEST_INPUT_CNT_THRESHOLD)
    print(f"Number of problems after filtering by test input count: {len(filtered_problems)}")

    filtered_problems = filter_problems_parallel(filter_by_consistency_single, filtered_problems, result_dict_dict, CONSISTENCY_THRESHOLD)
    print(f"Number of problems after filtering by consistency: {len(filtered_problems)}")

    filtered_problems = filter_problems_parallel(filter_by_cv_single, filtered_problems, result_dict_dict, CV_THRESHOLD)
    print(f"Number of problems after filtering by CV: {len(filtered_problems)}")

    problem_stats = {}
    for problem in filtered_problems:
        problem_id = problem_to_id(problem)
        result_dict = result_dict_dict.get(problem_id, {})
        problem_cv = get_problem_cv(problem_id, result_dict)
        min_instruction_cnt = get_min_instruction_cnt(problem_id, result_dict)
        problem_consistency = get_problem_consistency(problem_id, result_dict)
        test_input_cnt = len(problem["public_tests"]["input"]) + len(problem["private_tests"]["input"])
        correct_solution_cnt = len(problem["solutions"]["language"])
        problem_stats[problem_id] = {
            "cv": problem_cv,
            "min_instruction_cnt": min_instruction_cnt,
            "consistency": problem_consistency,
            "test_input_cnt": test_input_cnt,
            "correct_solution_cnt": correct_solution_cnt,
        }

    # sort problems by cv
    sorted_problems = sorted(problem_stats.items(), key=lambda x: x[1]["cv"], reverse=True)
    # write the selected problems to a csv file
    with open("selected_problems.csv", "w") as file:
        writer = csv.writer(file)
        writer.writerow(["problem_id", "cv", "min_instruction_cnt", "consistency", "test_input_cnt", "correct_solution_cnt"])
        for problem_id, stats in sorted_problems:
            writer.writerow([problem_id, stats["cv"], stats["min_instruction_cnt"], stats["consistency"], stats["test_input_cnt"], stats["correct_solution_cnt"]])

if __name__ == "__main__":
    main()
