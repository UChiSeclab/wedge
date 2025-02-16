from pathlib import Path
import json
from typing import List, Dict, Literal

from config import config
from utils import get_cf_problems, filter_problems, problem_to_id, run_result_exists
from evaluate.usefulness.prompt_exp.profile_utils import get_input_output_pairs
from evaluate.usefulness.prompt_exp.merge_compare_improvement import get_intersection_problem_solution, get_union_problem_solution

ORI_SOLUTIONS_DIR = Path(config["pie_dir"]) / "ori_human_solutions"
OPTIMIZED_SOLUTIONS_DIR = Path(config["pie_dir"]) / "optimized_human_solutions" / "alphacode_none"
ORI_SOLUTION_PROFILE_EVAL_DIR = Path(config["pie_dir"]) / "ori_human_solution_profile_eval"
OPTIMIZED_SOLUTION_PROFILE_EVAL_DIR = Path(config["pie_dir"]) / "optimized_human_solution_profile_eval"

metrics = ["instruction_cnt_speedup", "exec_time_speedup", "instruction_cnt_opt", "exec_time_opt"]

def truncate_to_cutoff(value: float, cutoff: float=10) -> float:
    # cutoff to mitigate the effect of outliers, e.g., 100x speedup
    return max(min(value, cutoff), -cutoff)

def get_subset_profile_stats(profile_stats: Dict, input_id_list: List[str]) -> Dict:
    """
    get subset of profile stats
    """
    subset_profile_stats = {
        "verdict": profile_stats["verdict"],
        "inputs": {input_id: profile_stats["inputs"][input_id] for input_id in input_id_list},
    }
    return subset_profile_stats


def calculate_opt_stats(input_set: str, input_selection_type: str, model_name: str, problem_solution_id_list: List[str]) -> Dict:
    """
    calculate optimization stats: {
        problem_solution_id: {
            instruction_cnt_opt: float,
            exec_time_opt: float,
            instruction_cnt_speedup: float,
            exec_time_speedup: float,
            correctness: str,
            }
        }
    """

    ori_incorrect_cnt = 0
    optimized_incorrect_cnt = 0
    opt_stats = {}
    for problem_solution_id in problem_solution_id_list:
        problem_id, solution_id = problem_solution_id.split("#")
        ori_solution_profile_dir = ORI_SOLUTION_PROFILE_EVAL_DIR / f"{input_set}_{input_selection_type}" / problem_id / solution_id
        optimized_solution_profile_dir = OPTIMIZED_SOLUTION_PROFILE_EVAL_DIR / f"{input_set}_{input_selection_type}" / problem_id / model_name / f"{solution_id}_optimized"

        if input_set == "alphacode": # fetch stats from alphacode_all and get subset of stats
            ori_solution_profile_dir = ORI_SOLUTION_PROFILE_EVAL_DIR / f"alphacode_all" / problem_id / solution_id
            optimized_solution_profile_dir = OPTIMIZED_SOLUTION_PROFILE_EVAL_DIR / f"alphacode_all" / problem_id / model_name / f"{solution_id}_optimized"

        ori_profile_stats_file = ori_solution_profile_dir / "exec_status.json"
        optimized_profile_stats_file = optimized_solution_profile_dir / "exec_status.json"

        ori_profile_stats = json.loads(ori_profile_stats_file.read_text())
        optimized_profile_stats = json.loads(optimized_profile_stats_file.read_text())

        if ori_profile_stats["verdict"] != "correct":
            ori_incorrect_cnt += 1
        if optimized_profile_stats["verdict"] != "correct":
            optimized_incorrect_cnt += 1
        if ori_profile_stats["verdict"] == "correct" and optimized_profile_stats["verdict"] == "correct":

            if input_selection_type != "all":
                # get subset of stats
                input_output_pairs = get_input_output_pairs(problem_id, input_set, input_selection_type, suppress_warning=True)
                input_id_list = [input_file.name for input_file, _ in input_output_pairs]
                ori_profile_stats = get_subset_profile_stats(ori_profile_stats, input_id_list)
                optimized_profile_stats = get_subset_profile_stats(optimized_profile_stats, input_id_list)

            ori_instruction_cnt = sum(v["instruction_cnt"] for v in ori_profile_stats["inputs"].values())
            optimized_instruction_cnt = sum(v["instruction_cnt"] for v in optimized_profile_stats["inputs"].values())
            ori_exec_time = sum(v["execution_time"] for v in ori_profile_stats["inputs"].values())
            optimized_exec_time = sum(v["execution_time"] for v in optimized_profile_stats["inputs"].values())
            instruction_cnt_speedup = ori_instruction_cnt / optimized_instruction_cnt
            exec_time_speedup = ori_exec_time / optimized_exec_time
            instruction_cnt_opt = (ori_instruction_cnt - optimized_instruction_cnt) / ori_instruction_cnt
            exec_time_opt = (ori_exec_time - optimized_exec_time) / ori_exec_time
            correctness = "correct"

            opt_stats[problem_solution_id] = {
                "instruction_cnt_opt": instruction_cnt_opt,
                "exec_time_opt": exec_time_opt,
                "instruction_cnt_speedup": instruction_cnt_speedup,
                "exec_time_speedup": exec_time_speedup,
                "correctness": correctness,
            }
    print(f"ori_incorrect_cnt: {ori_incorrect_cnt}, optimized_incorrect_cnt: {optimized_incorrect_cnt}")

    return opt_stats

def summarize_opt_stats(opt_stats: Dict) -> Dict:
    """
    summary optimization stats: {
        instruction_cnt_opt: float,
        exec_time_opt: float,
        instruction_cnt_speedup: float,
        exec_time_speedup: float,
        instruction_cnt_optimized_ratio: float,
        exec_time_optimized_ratio: float,
        }
    """
    instruction_cnt_opt_list = [truncate_to_cutoff(v["instruction_cnt_opt"]) for v in opt_stats.values()]
    exec_time_opt_list = [truncate_to_cutoff(v["exec_time_opt"]) for v in opt_stats.values()]
    instruction_cnt_speedup_list = [truncate_to_cutoff(v["instruction_cnt_speedup"]) for v in opt_stats.values()]
    exec_time_speedup_list = [truncate_to_cutoff(v["exec_time_speedup"]) for v in opt_stats.values()]

    instruction_cnt_optimized_num = 0
    exec_time_optimized_num = 0
    for problem_solution_id in opt_stats:
        if opt_stats[problem_solution_id]["instruction_cnt_speedup"] > 1.1:
            instruction_cnt_optimized_num += 1
        if opt_stats[problem_solution_id]["exec_time_speedup"] > 1.1:
            exec_time_optimized_num += 1

    summary = {
        "instruction_cnt_opt": sum(instruction_cnt_opt_list) / len(instruction_cnt_opt_list),
        "exec_time_opt": sum(exec_time_opt_list) / len(exec_time_opt_list),
        "instruction_cnt_speedup": sum(instruction_cnt_speedup_list) / len(instruction_cnt_speedup_list),
        "exec_time_speedup": sum(exec_time_speedup_list) / len(exec_time_speedup_list),
        "instruction_cnt_optimized_ratio": instruction_cnt_optimized_num / len(opt_stats),
        "exec_time_optimized_ratio": exec_time_optimized_num / len(opt_stats),
    }

    return summary

def eval(
    input_set: str,
    input_selection_type: str,
    model_name: str
):
    print("=" * 50)
    print(f"input_set: {input_set}, input_selection_type: {input_selection_type}, model_name: {model_name}")

    opt_stats_file = Path(config["pie_dir"]) / f"opt_stats_{input_set}_{input_selection_type}_{model_name}.json"
    if opt_stats_file.exists():
        opt_stats = json.loads(opt_stats_file.read_text())
        problem_solution_id_list = list(opt_stats.keys())
    else:
        filtered_problems = filter_problems(
            get_cf_problems(use_specified_problem=config["use_specified_problem"]),
            filter_with_inconsistency_threshold=True,
        )

        problem_solution_id_list = []
        for problem in filtered_problems:
            problem_id = problem_to_id(problem)
            if not run_result_exists(problem_id, input_set):
                continue
            ori_solution_dir = ORI_SOLUTIONS_DIR / problem_id
            ori_solution_files = sorted(ori_solution_dir.glob("*.cpp"))
            for ori_solution_file in ori_solution_files:
                problem_solution_id_list.append(f"{problem_id}#{ori_solution_file.stem}")

        opt_stats = calculate_opt_stats(input_set, input_selection_type, model_name, problem_solution_id_list)
        # dump stats
        opt_stats_file.write_text(json.dumps(opt_stats))

    summary = summarize_opt_stats(opt_stats)

    print(f"opt_stats:")
    print(f"num of problem_solution_id: {len(problem_solution_id_list)}")
    print(f"num of both correct problem_solution_id: {len(opt_stats)}")
    print(summary)

    return opt_stats

def merge_compare_opt_stats(opt_stats_dict_list: List[Dict[str, Dict[str, float]]], mode: Literal["intersection", "union"]) -> List[Dict[str, Dict[str, float]]]:
    if mode == "intersection":
        problem_solution_id_list = get_intersection_problem_solution(opt_stats_dict_list)
    elif mode == "union":
        problem_solution_id_list = get_union_problem_solution(opt_stats_dict_list)
        for opt_stats_dict in opt_stats_dict_list:
            for problem_solution_id in problem_solution_id_list:
                if problem_solution_id not in opt_stats_dict:
                    opt_stats_dict[problem_solution_id] = {metric: 1 if metric.endswith("speedup") else 0 for metric in metrics}
    else:
        raise ValueError(f"Unknown mode: {mode}")

    print(f"Number of {mode} problem_solution_id: {len(problem_solution_id_list)}")

    summary_list = []
    for opt_stats_dict in opt_stats_dict_list:
        summary = summarize_opt_stats(opt_stats_dict)
        summary_list.append(summary)

    return summary_list

def display_summary_opt_stats(summary_list: List[Dict[str, Dict[str, float]]]):
    for i in range(1, len(summary_list) + 1):
        summary = summary_list[i - 1]
        print("--------------------------------------------------")
        print(f"Summary {i}:")
        for metric in summary:
            print(f"{metric},{summary[metric]}")

if __name__ == "__main__":
    opt_stats_1_1 = eval("alphacode", "public_5", "pie-hq-selfplay-13b")
    opt_stats_1_2 = eval("alphacode", "public_private_slow_5", "pie-hq-selfplay-13b")
    opt_stats_1_3 = eval("alphacode", "all", "pie-hq-selfplay-13b")
    opt_stats_1_4 = eval("corpus_instrument_fuzz_mutator_with_constraint_per_solution", "slow_5", "pie-hq-selfplay-13b")
    opt_stats_2_1 = eval("alphacode", "public_5", "pie-conditioned-13b")
    opt_stats_2_2 = eval("alphacode", "public_private_slow_5", "pie-conditioned-13b")
    opt_stats_2_3 = eval("alphacode", "all", "pie-conditioned-13b")
    opt_stats_2_4 = eval("corpus_instrument_fuzz_mutator_with_constraint_per_solution", "slow_5", "pie-conditioned-13b")

    summary_list_1 = merge_compare_opt_stats([opt_stats_1_1, opt_stats_1_2, opt_stats_1_3, opt_stats_1_4], mode="union")
    summary_list_2 = merge_compare_opt_stats([opt_stats_2_1, opt_stats_2_2, opt_stats_2_3, opt_stats_2_4], mode="union")

    # summary_list_1 = merge_compare_opt_stats([opt_stats_1_1, opt_stats_1_2, opt_stats_1_3, opt_stats_1_4], mode="intersection")
    # summary_list_2 = merge_compare_opt_stats([opt_stats_2_1, opt_stats_2_2, opt_stats_2_3, opt_stats_2_4], mode="intersection")

    print("==================================================")
    print("Summary for pie-hq-selfplay-13b:")
    display_summary_opt_stats(summary_list_1)
    print("Summary for pie-conditioned-13b:")
    display_summary_opt_stats(summary_list_2)
