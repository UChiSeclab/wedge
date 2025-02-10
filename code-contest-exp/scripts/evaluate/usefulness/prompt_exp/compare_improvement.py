import json
import sys
from typing import List, Dict

metrics = ["time_ratio", "total_mem_ratio", "max_mem_ratio", "instruction_cnt_ratio"]

def get_overlap_valid_problem_solution(improvement_dict_1: Dict[str, Dict[str, float]], improvement_dict_2: Dict[str, Dict[str, float]]) -> List[str]:
    overlap_problem_solution_id_list = list(set(improvement_dict_1.keys()) & set(improvement_dict_2.keys()))
    # discard problem_solution_id where the improvement is invalid, i.e., any metric improvement is <= -0.5
    overlap_problem_solution_id_list = [problem_solution_id for problem_solution_id in overlap_problem_solution_id_list if all([improvement_dict_1[problem_solution_id][metric] > -0.5 for metric in metrics]) and all([improvement_dict_2[problem_solution_id][metric] > -0.5 for metric in metrics])]

    # sort by problem_id and solution_id
    overlap_problem_solution_id_list = sorted(overlap_problem_solution_id_list, key=lambda x: (int(x.split("#")[0].split("_")[0]), int(x.split("#")[1].split("_")[1])))

    return overlap_problem_solution_id_list

def compare_improvement(improvement_stats_1: Dict[str, Dict[str, float]], improvement_stats_2: Dict[str, Dict[str, float]]) -> Dict[str, Dict[str, float]]:
    overlap_problem_solution_id_list = get_overlap_valid_problem_solution(improvement_stats_1, improvement_stats_2)

    improvement_diff = {}
    for problem_solution_id in overlap_problem_solution_id_list:
        improvement_diff[problem_solution_id] = {}
        for metric in metrics:
            improvement_diff[problem_solution_id][metric] = improvement_stats_2[problem_solution_id][metric] - improvement_stats_1[problem_solution_id][metric]

    return improvement_diff

def summary_improvement_diff(improvement_diff: Dict[str, Dict[str, float]]):
    """
    1. show the average difference for each metric
    2. show on how many solutions improvement_2 is better than improvement_1 for each metric
    3. show on how many solutions improvement_1 is better than improvement_2 for each metric
    """
    avg_diff = {}
    avg_diff_2_better = {}
    improvement_2_better = {}
    improvement_1_better = {}
    for metric in metrics:
        avg_diff[metric] = sum([improvement_diff[problem_solution_id][metric] for problem_solution_id in improvement_diff]) / len(improvement_diff)
        improvement_2_better[metric] = sum([1 for problem_solution_id in improvement_diff if improvement_diff[problem_solution_id][metric] > 0])
        improvement_1_better[metric] = sum([1 for problem_solution_id in improvement_diff if improvement_diff[problem_solution_id][metric] < 0])
        avg_diff_2_better[metric] = sum([improvement_diff[problem_solution_id][metric] for problem_solution_id in improvement_diff if improvement_diff[problem_solution_id][metric] > 0]) / improvement_2_better[metric]

    return avg_diff, improvement_2_better, improvement_1_better, avg_diff_2_better

def display_summary_improvement_diff(avg_diff: Dict[str, float], improvement_2_better: Dict[str, int], improvement_1_better: Dict[str, int], avg_diff_2_better: Dict[str, float]):
    for metric in metrics:
        print("=====================================")
        print(f"{metric}:")
        print(f"Average difference: {avg_diff[metric]}")
        print(f"Improvement 2 is better on {improvement_2_better[metric]} solutions")
        print(f"Improvement 1 is better on {improvement_1_better[metric]} solutions")
        print(f"Average difference when improvement 2 is better: {avg_diff_2_better[metric]}")

if __name__ == "__main__":
    improvement_stats_file_1 = sys.argv[1]
    improvement_stats_file_2 = sys.argv[2]
    improvement_stats_1 = json.load(open(improvement_stats_file_1, "r"))
    improvement_stats_2 = json.load(open(improvement_stats_file_2, "r"))

    improvement_diff = compare_improvement(improvement_stats_1, improvement_stats_2)
    avg_diff, improvement_2_better, improvement_1_better, avg_diff_2_better = summary_improvement_diff(improvement_diff)
    display_summary_improvement_diff(avg_diff, improvement_2_better, improvement_1_better, avg_diff_2_better)
