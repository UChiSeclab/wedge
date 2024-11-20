import json
import os
from pathlib import Path
from typing import List, Tuple, Dict, Union
from tqdm import tqdm
import tempdir
import subprocess
import shutil
import numpy as np
from fire import Fire
import pprint

from config import config
from utils import squeeze_time_dict, get_experiment_result
from utils import problem_has_failed_test_gen, mean, squeeze_time_dict
from analysis.time_statistics import *

def split_time_dict(time_dict: Dict[str, float], input_status_dict: Dict) -> Tuple[Dict[str, float], Dict[str, float]]:
    """split time dict into crash and not-crash inptus"""
    crash_time_dict = {}
    not_crash_time_dict = {}
    for input_file_name, time in time_dict.items():
        if input_status_dict[input_file_name]["status"] == "Crash":
            crash_time_dict[input_file_name] = time
        elif input_status_dict[input_file_name]["status"] == "Pass":
            not_crash_time_dict[input_file_name] = time
        else:
            print(f"[Warning] unexpected status {input_status_dict[input_file_name]['status']} for {input_file_name}")

    return crash_time_dict, not_crash_time_dict


def get_input_status(strategy: str, problem_id: str, constraint_solution_id: str, constraint_input_pair_id, input_file_name: str) -> str:
    input_classify_dir = Path(config["input_classify_dir"])
    result_file = input_classify_dir / problem_id / constraint_solution_id / constraint_input_pair_id / f"{strategy}.json"
    assert result_file.exists(), f"{result_file} does not exist"
    with open(result_file, "r") as f:
        result = json.load(f)
    return result[input_file_name]["status"]

def get_input_status_dict(strategy: str, problem_id: str, constraint_solution_id: str, constraint_input_pair_id) -> Dict[str, str]:
    input_classify_dir = Path(config["input_classify_dir"])
    result_file = input_classify_dir / problem_id / constraint_solution_id / constraint_input_pair_id / f"{strategy}.json"
    assert result_file.exists(), f"{result_file} does not exist"
    with open(result_file, "r") as f:
        result = json.load(f)
    return result

def process_strategy(
    strategy: str,
    problem_id: str,
    constraint_solution_id: str,
    constraint_input_pair_id: str,
    problem_root_dir: Path,
    top_k_slow_inputs: int = 5,
) -> Tuple[Dict[str, Dict[str, Dict[str, float]]], Dict[str, Dict[str, Dict[str, float]]]]:
    problem_strategy_result = get_experiment_result(problem_id, strategy)
    crash_input_solutions_data = {"cpp": {}, "java": {}, "python": {}} # language -> input -> solution -> time
    not_crash_input_solutions_data = {"cpp": {}, "java": {}, "python": {}}
    for solution, data in problem_strategy_result.items():
        if solution == "time_limit":
            continue
        if solution.startswith("incorrect_solutions"):
            continue
        if not all(verdict in ["AC", "TLE"] for verdict in data["verdict"]):
            continue
        language = data["language"].lower()
        if language == "python3":
            language = "python"

        time_stat = squeeze_time_dict(data["time_dict"], use_max_or_avg="avg")
        input_status_dict = get_input_status_dict(strategy, problem_id, constraint_solution_id, constraint_input_pair_id)
        if input_status_dict == {"error": "Compile Error"}:
            raise ValueError(f"Compile Error for {strategy} {problem_id} {constraint_solution_id} {constraint_input_pair_id}")
        crash_time_dict, not_crash_time_dict = split_time_dict(time_stat, input_status_dict)

        for input_file_name, time in crash_time_dict.items():
            if input_file_name not in crash_input_solutions_data[language]:
                crash_input_solutions_data[language][input_file_name] = {}
            crash_input_solutions_data[language][input_file_name][solution] = time

        for input_file_name, time in not_crash_time_dict.items():
            if input_file_name not in not_crash_input_solutions_data[language]:
                not_crash_input_solutions_data[language][input_file_name] = {}
            not_crash_input_solutions_data[language][input_file_name][solution] = time

    return crash_input_solutions_data, not_crash_input_solutions_data

def aggregate_over_strategies(problem_statistics: Dict[str, Dict[str, List[Tuple[Dict[str, Dict[str, float]], Dict[str, Dict[str, float]]]]]]) -> Dict[str, List[Tuple[Dict[str, Dict[str, float]], Dict[str, Dict[str, float]]]]]:
    """Aggregate over strategies"""
    all_strategies_data = {} # problem_lang -> [(crash_input_solutions_data, not_crash_input_solutions_data)]
    for problem_lang, strategy_data in problem_statistics.items():
        for strategy, data in strategy_data.items():
            all_strategies_data[problem_lang] = all_strategies_data.get(problem_lang, [])
            all_strategies_data[problem_lang].extend(data)

    return all_strategies_data

def statistics_of_input_solutions_data(input_solutions_data: Dict[str, Dict[str, float]]) -> Dict[str, float]:
    """calculate max_time, avg_time, and cv of inputs"""
    max_time_dict = {}
    avg_time_dict = {}
    cv_time_dict = {}
    for input_file_name, solution_time in input_solutions_data.items():
        max_time_dict[input_file_name] = max(solution_time.values())
        avg_time_dict[input_file_name] = mean(solution_time.values())
        cv_time_dict[input_file_name] = np.std(list(solution_time.values())) / avg_time_dict[input_file_name]
    
    if len(max_time_dict) == 0:
        return {
            "max_time": None,
            "avg_time": None,
            "cv": None
        }

    return {
        "max_time": mean(max_time_dict.values()),
        "avg_time": mean(avg_time_dict.values()),
        "cv": mean(cv_time_dict.values())
    }

def statistics_per_problem(problem_data: Dict[str, List[Tuple[Dict[str, Dict[str, float]], Dict[str, Dict[str, float]]]]]) -> None:
    # problem_id -> [(crash_input_solutions_data, not_crash_input_solutions_data)]
    statistics_dict = {"crash": { "max_time": [], "avg_time": [], "cv": [] }, "not_crash": { "max_time": [], "avg_time": [], "cv": [] }}
    problem_statistics = {}
    for problem_lang, data in problem_data.items():
        print(f"Problem {problem_lang}")
        crash_max_time = []
        crash_avg_time = []
        crash_cv = []
        not_crash_max_time = []
        not_crash_avg_time = []
        not_crash_cv = []

        for crash_input_solutions_data, not_crash_input_solutions_data in data:
            if len(crash_input_solutions_data) == 0 and len(not_crash_input_solutions_data) == 0:
                continue
            print(f"Crash inputs: {len(crash_input_solutions_data)}, Not crash inputs: {len(not_crash_input_solutions_data)}")
            crash_statistics = statistics_of_input_solutions_data(crash_input_solutions_data)
            not_crash_statistics = statistics_of_input_solutions_data(not_crash_input_solutions_data)

            if all([crash_statistics[key] is None for key in crash_statistics])\
                and all([not_crash_statistics[key] is None for key in not_crash_statistics]):
                continue
            print("Crash:")
            pprint.pprint(crash_statistics)
            print("Not crash:")
            pprint.pprint(not_crash_statistics)

            if all([crash_statistics[key] is not None for key in crash_statistics]):
                crash_max_time.append(crash_statistics["max_time"])
                crash_avg_time.append(crash_statistics["avg_time"])
                crash_cv.append(crash_statistics["cv"])
                statistics_dict["crash"]["max_time"].append(crash_statistics["max_time"])
                statistics_dict["crash"]["avg_time"].append(crash_statistics["avg_time"])
                statistics_dict["crash"]["cv"].append(crash_statistics["cv"])
            if all([not_crash_statistics[key] is not None for key in not_crash_statistics]):
                not_crash_max_time.append(not_crash_statistics["max_time"])
                not_crash_avg_time.append(not_crash_statistics["avg_time"])
                not_crash_cv.append(not_crash_statistics["cv"])
                statistics_dict["not_crash"]["max_time"].append(not_crash_statistics["max_time"])
                statistics_dict["not_crash"]["avg_time"].append(not_crash_statistics["avg_time"])
                statistics_dict["not_crash"]["cv"].append(not_crash_statistics["cv"])

        problem_statistics[problem_lang] = {
            "crash": {
                "max_time": crash_max_time,
                "avg_time": crash_avg_time,
                "cv": crash_cv
            },
            "not_crash": {
                "max_time": not_crash_max_time,
                "avg_time": not_crash_avg_time,
                "cv": not_crash_cv
            }
        }

    print("Overall statistics")
    for status, status_statistics in statistics_dict.items():
        print(f"{status}:")
        for key, value in status_statistics.items():
            print(f"{key}: {mean(value)}")

    # number of problems with both crash inputs and not crash inputs
    compare_problem_statistics = {problem_lang: data for problem_lang, data in problem_statistics.items() if len(data["crash"]["max_time"]) > 0 and len(data["not_crash"]["max_time"]) > 0}
    print(f"Number of problems with both crash inputs and not crash inputs: {len(compare_problem_statistics)}")
    
    # number of problems where crash inputs are better than not crash inputs
    crash_max_better_problems = len([problem_lang for problem_lang, data in compare_problem_statistics.items() if mean(data["crash"]["max_time"]) > mean(data["not_crash"]["max_time"])])
    crash_avg_better_problems = len([problem_lang for problem_lang, data in compare_problem_statistics.items() if mean(data["crash"]["avg_time"]) > mean(data["not_crash"]["avg_time"])])
    crash_cv_better_problems = len([problem_lang for problem_lang, data in compare_problem_statistics.items() if mean(data["crash"]["cv"]) > mean(data["not_crash"]["cv"])])
    
    print(f"Number of problem-langs where crash inputs are better than not crash inputs (max_time): {crash_max_better_problems}")
    print(f"Number of problem-langs where crash inputs are better than not crash inputs (avg_time): {crash_avg_better_problems}")
    print(f"Number of problem-langs where crash inputs are better than not crash inputs (cv): {crash_cv_better_problems}")

def main(
    top_k_slow_inputs: int = 5,
):
    constraints_dir = Path(config["constraints_dir"])
    problem_root_dir = Path(config["problem_root_dir"])
    # strategies = ["alphacode", "plain_problem", "slow_solution", "diff_solution_one_input", "random_solution", "evalperf_slow_solution", "evalperf_random_solution", "corpus"]
    strategies = ["corpus"]

    problem_statistics = {} # problem_id -> strategy -> [(crash_input_solutions_data, not_crash_input_solutions_data)]
    strategy_statistics = {} # strategy -> problem_id -> [(crash_input_solutions_data, not_crash_input_solutions_data)]
    # input_solutions_data = {} # input -> solution -> time

    for problem_id in os.listdir(constraints_dir):
        if problem_has_failed_test_gen(problem_id):
            print(f"[Warning] Skip {problem_id} due to failed test gen on some strategies")
            continue
        for constraint_solution_id in os.listdir(constraints_dir / problem_id):
            for constraint_input_pair_id in os.listdir(constraints_dir / problem_id / constraint_solution_id):

                for strategy in strategies:
                    strategy_statistics[strategy] = strategy_statistics.get(strategy, {})
                    try:
                        crash_input_solutions_data, not_crash_input_solutions_data = process_strategy(strategy, problem_id, constraint_solution_id, constraint_input_pair_id, problem_root_dir, top_k_slow_inputs=top_k_slow_inputs)
                    except Exception as e:
                        print(f"Error for {strategy} {problem_id} {constraint_solution_id} {constraint_input_pair_id}: {e}")
                        continue

                    for lang in ["cpp", "java", "python"]:
                        problem_lang = problem_id + "_" + lang
                        problem_statistics[problem_lang] = problem_statistics.get(problem_lang, {})
                        problem_statistics[problem_lang][strategy] = problem_statistics[problem_lang].get(strategy, [])
                        strategy_statistics[strategy][problem_lang] = strategy_statistics[strategy].get(problem_lang, [])
                        problem_statistics[problem_lang][strategy].append((crash_input_solutions_data[lang], not_crash_input_solutions_data[lang]))
                        strategy_statistics[strategy][problem_lang].append((crash_input_solutions_data[lang], not_crash_input_solutions_data[lang]))

    problem_data = aggregate_over_strategies(problem_statistics)
    statistics_per_problem(problem_data)

if __name__ == "__main__":
    Fire(main)
