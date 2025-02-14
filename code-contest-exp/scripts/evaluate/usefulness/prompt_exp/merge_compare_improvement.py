import json
import sys
from typing import List, Dict, Literal

from evaluate.usefulness.prompt_exp.eval import extract_negative_improvement

metrics = ["time_ratio", "total_mem_ratio", "max_mem_ratio", "instruction_cnt_ratio", "time_speedup", "instruction_cnt_speedup"]

def truncate_to_cutoff(value: float) -> float:
    # cutoff to [-10, 10]
    return max(-10, min(10, value))

def get_intersection_problem_solution(improvement_dict_list: List[Dict[str, Dict[str, float]]]) -> List[str]:
    overlap_problem_solution_id_list = list(set(improvement_dict_list[0].keys()))
    for improvement_dict in improvement_dict_list[1:]:
        overlap_problem_solution_id_list = list(set(overlap_problem_solution_id_list) & set(improvement_dict.keys()))

    # sort by problem_id and solution_id
    overlap_problem_solution_id_list = sorted(overlap_problem_solution_id_list, key=lambda x: (int(x.split("#")[0].split("_")[0]), int(x.split("#")[1].split("_")[1])))

    return overlap_problem_solution_id_list

def get_union_problem_solution(improvement_dict_list: List[Dict[str, Dict[str, float]]]) -> List[str]:
    union_problem_solution_id_list = list(set(improvement_dict_list[0].keys()))
    for improvement_dict in improvement_dict_list[1:]:
        union_problem_solution_id_list = list(set(union_problem_solution_id_list) | set(improvement_dict.keys()))

    # sort by problem_id and solution_id
    union_problem_solution_id_list = sorted(union_problem_solution_id_list, key=lambda x: (int(x.split("#")[0].split("_")[0]), int(x.split("#")[1].split("_")[1])))

    return union_problem_solution_id_list

def merge_compare_improvement(improvement_dict_list: List[Dict[str, Dict[str, float]]], mode: Literal["intersection", "union"], exclude_invalid_problem_solution: bool = False) -> List[Dict[str, Dict[str, float]]]:
    if mode == "intersection":
        problem_solution_id_list = get_intersection_problem_solution(improvement_dict_list)
    elif mode == "union":
        problem_solution_id_list = get_union_problem_solution(improvement_dict_list)
        for improvement_dict in improvement_dict_list:
            for problem_solution_id in problem_solution_id_list:
                if problem_solution_id not in improvement_dict:
                    improvement_dict[problem_solution_id] = {metric: 1 if metric.endswith("speedup") else 0 for metric in metrics}
    else:
        raise ValueError(f"Unknown mode: {mode}")
    
    if exclude_invalid_problem_solution:
        exclude_problem_solution_id_list = []
        for improvement_dict in improvement_dict_list:
            negative_improvement_stats = extract_negative_improvement(improvement_dict)
            exclude_problem_solution_id_list.extend(list(negative_improvement_stats.keys()))

        problem_solution_id_list = [problem_solution_id for problem_solution_id in problem_solution_id_list if problem_solution_id not in exclude_problem_solution_id_list]

    print(f"Number of problem_solution_id: {len(problem_solution_id_list)}")

    # calculate the average improvement for each metric
    avg_improvement_stats_list = []
    for improvement_dict in improvement_dict_list:
        avg_improvement_stats = {}
        for metric in metrics:
            avg_improvement_stats[metric] = sum([truncate_to_cutoff(improvement_dict[problem_solution_id][metric]) for problem_solution_id in problem_solution_id_list]) / len(problem_solution_id_list)
        avg_improvement_stats_list.append(avg_improvement_stats)

    return avg_improvement_stats_list

def display_summary_improvement(avg_improvement_stats_list: List[Dict[str, Dict[str, float]]]):
    for i in range(1, len(avg_improvement_stats_list) + 1):
        avg_improvement_stats = avg_improvement_stats_list[i - 1]
        print("--------------------------------------------------")
        print(f"Improvement {i}:")
        for metric in metrics:
            print(f"{metric}: {avg_improvement_stats[metric]}")

if __name__ == "__main__":
    improvement_dict_list = []
    for i in range(1, len(sys.argv)):
        with open(sys.argv[i], "r") as file:
            improvement_dict_list.append(json.load(file))
    avg_improvement_stats_list = merge_compare_improvement(improvement_dict_list, mode="union", exclude_invalid_problem_solution=False)
    # avg_improvement_stats_list = merge_compare_improvement(improvement_dict_list, mode="intersection", exclude_invalid_problem_solution=False)
    display_summary_improvement(avg_improvement_stats_list)
