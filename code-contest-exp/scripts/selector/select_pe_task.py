from pathlib import Path
from fire import Fire
import os
from typing import List, Any, Dict
import statistics
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
import json

from utils import mean
from config import config

def filter_blacklisted_problems(problem_id_list: List[str]) -> List[str]:
    # blacklisted_problems = ["207_D3", "36_B", "188_E", "345_B", "1462_C", "389_D", "1474_C", "512_A", "1170_A", "506_E", "1214_H", "1257_F"]
    blacklisted_problems = [
        "1005_F",
        "1015_D",
        "1089_A",
        "1129_B",
        "1170_A",
        "1170_B",
        "1178_F1",
        "1190_F",
        "120_B",
        "120_F",
        "1214_H",
        "1250_L",
        "1252_J",
        "1257_F",
        "1261_E",
        "1278_E",
        "1302_B",
        "1302_G",
        "1331_H",
        "1342_B",
        "1346_B",
        "1346_H",
        "1348_D",
        "1372_F",
        "1384_A",
        "1391_E",
        "1402_C",
        "1424_C",
        "1431_G",
        "1462_C",
        "1474_C",
        "1515_A",
        "188_E",
        "241_D",
        "241_G",
        "277_B",
        "345_B",
        "36_B",
        "389_D",
        "405_E",
        "414_A",
        "506_E",
        "512_A",
        "578_E",
        "64_A",
        "64_F",
        "64_G",
        "661_A",
        "661_C",
        "661_G",
        "690_B2",
        "720_C",
        "72_G",
        "775_A",
        "835_E",
        "906_B",
        "921_01",
        "995_C",
    ]
    blacklisted_problems.extend(
        [
            "207_D3", # all time zero
            "52_C", # no correct cpp solutions
        ]
    )
    return [problem_id for problem_id in problem_id_list if problem_id not in blacklisted_problems]


def plot_dist(data: List[float], title: str):
    # plot the distribution of the data
    sns.histplot(data, bins=100)
    plt.title(title)
    plt.savefig(f"{title}.png")
    plt.close()


def get_stats(result_dir: Path, problem_id_list:List[str]) -> Dict[str, Dict[str, Any]]:
    stats = {} # problem_id -> {"num_solutions": int, "num_java_solutions": int, "num_cpp_solutions": int, "num_python_solutions": int, "cpp_time_list": List[float], "cpp_time_median": float, "cpp_time_mean": float, "cpp_time_cv": float}
    for problem_id in problem_id_list:
        stats[problem_id] = {}
        num_solutions = 0
        num_java_solutions = 0
        num_cpp_solutions = 0
        num_python_solutions = 0
        with open(result_dir / f'{problem_id}.json') as f:
            data = json.load(f)
        stats[problem_id]["cpp_solution_time"] = {}
        for solution_id in data.keys():
            if solution_id.startswith("solutions_"):
                if all(v in ["AC", "TLE"] for v in data[solution_id]["verdict"]):
                    time_dict = data[solution_id]["time_dict"]
                    num_inputs = len([input_name for input_name in time_dict.keys()\
                        if input_name.startswith("public") or input_name.startswith("private")])
                    if "java" in data[solution_id]["language"]:
                        num_java_solutions += 1
                    elif "cpp" in data[solution_id]["language"]:
                        num_cpp_solutions += 1
                        stats[problem_id]["cpp_solution_time"][solution_id] = mean(data[solution_id]["average_time"])
                    elif "python" in data[solution_id]["language"]:
                        num_python_solutions += 1
                    num_solutions += 1
                    if "cpp" in data[solution_id]["language"]:
                        for input_name, time_list in time_dict.items():
                            stats[problem_id]["cpp_time_list"] = stats[problem_id].get("cpp_time_list", [])
                            stats[problem_id]["cpp_time_list"].append(mean(time_list))
        
        # remove all problems that don't have cpp solutions
        if num_cpp_solutions < 10:
            del stats[problem_id]
            continue

        print(f"Problem {problem_id}: num_solutions: {num_solutions}, num_java_solutions: {num_java_solutions}, num_cpp_solutions: {num_cpp_solutions}, num_python_solutions: {num_python_solutions}")
        stats[problem_id]["num_inputs"] = num_inputs
        stats[problem_id]["num_solutions"] = num_solutions
        stats[problem_id]["num_java_solutions"] = num_java_solutions
        stats[problem_id]["num_cpp_solutions"] = num_cpp_solutions
        stats[problem_id]["num_python_solutions"] = num_python_solutions
        stats[problem_id]["cpp_time_median"] = np.median(stats[problem_id]["cpp_time_list"])
        stats[problem_id]["cpp_time_mean"] = mean(stats[problem_id]["cpp_time_list"])
        stats[problem_id]["cpp_time_cv"] = np.std(stats[problem_id]["cpp_time_list"]) / stats[problem_id]["cpp_time_mean"] * 100


    return stats

# filter 1: select the problems that run sufficiently long
def filter_long_running_problems_mean(stats: Dict[str, Dict[str, Any]]) -> Dict[str, Dict[str, Any]]:
    return {problem_id: stat for problem_id, stat in stats.items() if stat["cpp_time_mean"] >= 0.2}

def filter_long_running_problems_median(stats: Dict[str, Dict[str, Any]]) -> Dict[str, Dict[str, Any]]:
    return {problem_id: stat for problem_id, stat in stats.items() if stat["cpp_time_median"] >= 0.2}

# filter 2: select the problems that have enough number of java/cpp/python submissions
def filter_sufficient_num_solutions(stats: Dict[str, Dict[str, Any]]) -> Dict[str, Dict[str, Any]]:
    return {problem_id: stat for problem_id, stat in stats.items() if stat["num_java_solutions"] >= 5 and stat["num_cpp_solutions"] >= 5 and stat["num_python_solutions"] >= 5}

# filter 3: select the problems that have enough performance diversity across the submissions
def filter_diverse_performance(stats: Dict[str, Dict[str, Any]]) -> Dict[str, Dict[str, Any]]:
    return {problem_id: stat for problem_id, stat in stats.items() if stat["cpp_time_cv"] >= 25}

# filter 4: select the problems that have public/private test cases
def filter_problems_with_num_tests(stats: Dict[str, Dict[str, Any]]) -> Dict[str, Dict[str, Any]]:
    return {problem_id: stat for problem_id, stat in stats.items() if stat["num_inputs"] >= 5}

def problem_has_n_cpp_solution(problem_id: str, alphacode_result_dir: Path, n : int = 10) -> bool:
    data = json.load(open(alphacode_result_dir / f'{problem_id}.json'))
    cnt = 0
    for solution_id in data.keys():
        if solution_id.startswith("solutions_"):
            if "cpp" in data[solution_id]["language"]:
                if all(v in ["AC", "TLE"] for v in data[solution_id]["verdict"]):
                    cnt += 1

    return cnt >= n

def main(alphacode_result_dir: str):
    problem_root_dir = config["problem_root_dir"]
    alphacode_result_dir = Path(alphacode_result_dir)
    candidate_problem_list = filter_blacklisted_problems(
        [file.stem for file in alphacode_result_dir.glob("*.json")]
    )
    # candidate_problem_list = [file.stem for file in alphacode_result_dir.glob("*.json")]
    
    # filter problems that have cpp solutions
    # candidate_problem_list = [problem_id for problem_id in candidate_problem_list if problem_has_n_cpp_solution(problem_id, alphacode_result_dir)]

    print(f"Number of problems: {len(candidate_problem_list)}")

    stats = get_stats(alphacode_result_dir, candidate_problem_list)
    props = ["num_solutions", "num_java_solutions", "num_cpp_solutions", "num_python_solutions", "cpp_time_median", "cpp_time_mean", "cpp_time_cv"]
    for prop in props:
        data = [float(stats[problem_id][prop]) for problem_id in candidate_problem_list]
        plot_dist(data, prop)

    print(f"Before filtering: {len(candidate_problem_list)}")
    print(f"mean time >= 1.0: {len(filter_long_running_problems_mean(stats))}, proportion: {len(filter_long_running_problems_mean(stats)) / len(candidate_problem_list)}")
    print(f"median time >= 1.0: {len(filter_long_running_problems_median(stats))}, proportion: {len(filter_long_running_problems_median(stats)) / len(candidate_problem_list)}")
    print(f"num solutions >= 5: {len(filter_sufficient_num_solutions(stats))}, proportion: {len(filter_sufficient_num_solutions(stats)) / len(candidate_problem_list)}")
    print(f"performance cv >= 25: {len(filter_diverse_performance(stats))}, proportion: {len(filter_diverse_performance(stats)) / len(candidate_problem_list)}")
    print(f"num inputs >= 5: {len(filter_problems_with_num_tests(stats))}, proportion: {len(filter_problems_with_num_tests(stats)) / len(candidate_problem_list)}")
    
    # performance cv >= 25
    stats = filter_diverse_performance(stats)
    print(f"After filtering performance cv: {len(stats)}")
    
    # rank cv and print the top 10 problems
    problem_id_list = list(stats.keys())
    problem_id_list.sort(key=lambda problem_id: stats[problem_id]["cpp_time_cv"], reverse=True)
    print("problem_id,cv,num_solutions,num_inputs,fastest_solution_id,slowest_solution_id,fastest_solution_time,slowest_solution_time")
    for problem_id in problem_id_list[:100]:
        print("========================================")
        print(f"{problem_id}: cv: {stats[problem_id]['cpp_time_cv']}, num of solutions: {stats[problem_id]['num_solutions']}, num of inputs: {stats[problem_id]['num_inputs']}")
        sorted_cpp_solution_time = sorted(stats[problem_id]['cpp_solution_time'].items(), key=lambda x: x[1])
        print(f"fastest solution: {sorted_cpp_solution_time[0]}")
        print(f"{problem_root_dir}/{problem_id}/solutions/cpp/{sorted_cpp_solution_time[0][0]}.cpp")
        print(f"slowest solution: {sorted_cpp_solution_time[-1]}")
        print(f"{problem_root_dir}/{problem_id}/solutions/cpp/{sorted_cpp_solution_time[-1][0]}.cpp")
        # print(",".join([problem_id, str(stats[problem_id]['cpp_time_cv']), str(stats[problem_id]['num_solutions']), str(stats[problem_id]['num_inputs']), sorted_cpp_solution_time[0][0], sorted_cpp_solution_time[-1][0], str(sorted_cpp_solution_time[0][1]), str(sorted_cpp_solution_time[-1][1])]))
    
    # print(problem_id_list[100:200])
    
    # # mean time >= 0.1
    # stats = filter_long_running_problems_mean(stats)
    # print(f"After filtering mean time: {len(stats)}")
    # # num solutions >= 5
    # stats = filter_sufficient_num_solutions(stats)
    # print(f"After filtering num solutions: {len(stats)}")
    # # num inputs >= 5
    # stats = filter_problems_with_num_tests(stats)
    # print(f"After filtering num inputs: {len(stats)}")

if __name__ == '__main__':
    Fire(main)
