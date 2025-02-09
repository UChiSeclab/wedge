from pathlib import Path
from typing import Dict, List
import json
from fire import Fire

from config import config
from evaluate.usefulness.prompt_exp.stats import get_correct_problem_solution_from_profile_stats

def calculate_improvement(problem_solution_dict: Dict, ori_profile_stats: Dict, optimized_profile_stats: Dict) -> Dict:
    improvement_stats = {}
    for problem_id in problem_solution_dict:
        for solution_id in problem_solution_dict[problem_id]:
            problem_solution_id = f"{problem_id}#{solution_id}"
            ori_stats = ori_profile_stats[problem_id][solution_id]
            optimized_stats = optimized_profile_stats[problem_id][solution_id]
            ori_execution_time = ori_stats["merged_script_stats"]["total_execution_time"]
            optimized_execution_time = optimized_stats["merged_script_stats"]["total_execution_time"]
            ori_total_mem = ori_stats["merged_script_stats"]["total_memory_usage"]
            optimized_total_mem = optimized_stats["merged_script_stats"]["total_memory_usage"]
            ori_max_mem = ori_stats["merged_script_stats"]["max_peak_memory_usage"]
            optimized_max_mem = optimized_stats["merged_script_stats"]["max_peak_memory_usage"]
            improvement_stats[problem_solution_id] = {
                "time_ratio": (ori_execution_time - optimized_execution_time) / ori_execution_time,
                "total_mem_ratio": (ori_total_mem - optimized_total_mem) / ori_total_mem,
                "max_mem_ratio": (ori_max_mem - optimized_max_mem) / ori_max_mem,
                "instruction_cnt_ratio": (ori_stats["merged_instruction_cnt"] - optimized_stats["merged_instruction_cnt"]) / ori_stats["merged_instruction_cnt"]
            }

    # sort by problem_id
    improvement_stats = dict(sorted(improvement_stats.items(), key=lambda x: x[0]))

    return improvement_stats

def calculate_avg_improvement(improvement_stats: Dict) -> Dict:
    avg_improvement_stats = {}
    for problem_solution_id in improvement_stats:
        for key in improvement_stats[problem_solution_id]:
            if key not in avg_improvement_stats:
                avg_improvement_stats[key] = []
            avg_improvement_stats[key].append(improvement_stats[problem_solution_id][key])
    for key in avg_improvement_stats:
        avg_improvement_stats[key] = sum(avg_improvement_stats[key]) / len(avg_improvement_stats[key])
    return avg_improvement_stats

def main(
    input_set: str,
    input_selection_type: str,
    model_name: str,
):
    ori_profile_stats_file = Path(config["effi_learner_dir"]) / f"ori_human_profile_stats.json"
    optimized_profile_stats_file = Path(config["effi_learner_dir"]) / f"optimized_human_profile_stats_{input_set}_{input_selection_type}_{model_name}.json"
    improvement_stats_file = Path(config["effi_learner_dir"]) / f"improvement_stats_{input_set}_{input_selection_type}_{model_name}.json"

    ori_profile_stats = json.load(open(ori_profile_stats_file, "r"))
    optimized_profile_stats = json.load(open(optimized_profile_stats_file, "r"))

    problem_solution_dict = get_correct_problem_solution_from_profile_stats(ori_profile_stats, optimized_profile_stats)
    # Calculate the improvement stats
    improvement_stats = calculate_improvement(problem_solution_dict, ori_profile_stats, optimized_profile_stats)

    # dump the improvement stats
    with open(improvement_stats_file, "w") as f:
        json.dump(improvement_stats, f, indent=4)

    avg_improvement_stats = calculate_avg_improvement(improvement_stats)

    # Print the average improvement stats
    print(avg_improvement_stats)

    # pinpoint the most improved solution in terms of different metrics
    for key in avg_improvement_stats:
        most_improved = max(improvement_stats.items(), key=lambda x: x[1][key])
        print("=" * 20)
        print(f"Most improved in {key}: {most_improved[0]}")
        print(f"Improvement: {most_improved[1][key]}")
        problem_id, solution_id = most_improved[0].split("#")
        print(f"original solution path: {Path(config['effi_learner_dir']) / 'initial_code_generation' / problem_id / 'gpt-4o' / f'{solution_id}.py'}")
        print(f"optimized solution path: {Path(config['effi_learner_dir']) / 'optimized_code_generation' / 'alphacode_slow_5' / problem_id / 'gpt-4o' / f'{solution_id}_edited.py'}")

if __name__ == "__main__":
    Fire(main)
