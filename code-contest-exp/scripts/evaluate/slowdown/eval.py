import numpy as np
from matplotlib import pyplot as plt
from pathlib import Path
from typing import Dict, List, Literal

from utils import get_cf_problems, filter_problems, get_instruction_cnt, mean, get_alphacode_result, get_experiment_result
from selector.select_solution import select_evaluate_subset_solutions_lang_set_type
from common import Language

"""
1. plot a histogram where x is problem ids (problem-language) and show the per-problem average instruction count of solutions (fast-5, slow-5, constraint-src-5) over inputs produced by different strategies, log scale
2. plot a histogram where x is problem ids (problem-language) and show the per-problem average slow down percentage of solutions (fast-5, slow-5, constraint-src-5) over inputs produced by different strategies compared with alphacode tests
3. plot pie-chart to show how many problems (problem-language) each strategy can achieve the the performance (max instruction count, average instruction count) of each group (problem-solution group)

data structure:
problem_stats = {
    problem_id-lang: {
        strategy: {
            "fast-5": {
                "instruction_count": [float],
                "slowdown": [float],
            },
            "slow-5": {
                "instruction_count": [float],
                "slowdown": [float],
            },
            "constraint-src-5": {
                "instruction_count": [float],
                "slowdown": [float],
            },
        }
    }
}
"""

def get_solution_instruction_cnt_list_over_given_inputs(
    experiment_result: Dict,
    solution_id: str,
    input_ids: List[str]
) -> List[float]:
    """Get the instruction count of the solution on the input"""
    instruction_cnt_dict = experiment_result[solution_id]["instruction_cnt_dict"]
    instruction_cnt_list = []
    for input_id in input_ids:
        # instruction count of the solution on the input
        instruction_cnt_list += mean(instruction_cnt_dict[input_id])

    return instruction_cnt_list

def get_solution_avg_or_max_instruction_all_inputs(
    experiment_result: Dict,
    solution_id: str,
    mode: Literal["avg", "max"],
) -> float:
    """Get the instruction count of the solution on the input"""
    instruction_cnt_dict = experiment_result[solution_id]["instruction_cnt_dict"]
    instruction_cnt_list = []
    for input_id in instruction_cnt_dict:
        # instruction count of the solution on the input
        instruction_cnt_list += mean(instruction_cnt_dict[input_id])

    if mode == "avg":
        return mean(instruction_cnt_list)
    elif mode == "max":
        return max(instruction_cnt_list)
    else:
        raise ValueError(f"Invalid mode: {mode}")

def get_solution_avg_or_max_instruction_cnt_over_given_inputs(
    experiment_result: Dict,
    solution_id: str,
    input_ids: List[str],
    mode: Literal["avg", "max"],
) -> float:
    """Get the instruction count of the solution on the input"""
    instruction_cnt_list = get_solution_instruction_cnt_list_over_given_inputs(
        experiment_result, solution_id, input_ids
    )

    if mode == "avg":
        return mean(instruction_cnt_list)
    elif mode == "max":
        return max(instruction_cnt_list)
    else:
        raise ValueError(f"Invalid mode: {mode}")

def init_problem_stats(problems: List[Dict], strategies: List[str]) -> Dict:
    problem_stats = {}
    for problem in problems:
        problem_id = problem["name"].split(".")[0]
        for lang in [Language.CPP, Language.PYTHON, Language.JAVA]:
            problem_stats[f"{problem_id}-{lang}"] = {}
            for strategy in strategies:
                problem_stats[f"{problem_id}-{lang}"][strategy] = {}
                for solution_selection_type in ["multi_slow", "multi_fast", "constraint_src"]:
                    problem_stats[f"{problem_id}-{lang}"][strategy][solution_selection_type] = {
                        "instruction_count": [],
                        "slowdown": []
                    }

    return problem_stats

def get_top_k_slow_inputs(
    experiment_result: Dict,
    lang: Language,
    top_k: int = 5,
) -> List[str]:
    input_solution_stats = {}
    for solution_id in experiment_result:
        if experiment_result[solution_id]["language"] != str(lang):
            continue
        for input_id in experiment_result[solution_id]["instruction_cnt_dict"]:
            instruction_cnt = mean(experiment_result[solution_id]["instruction_cnt_dict"][input_id])
            input_solution_stats[input_id] = input_solution_stats.get(input_id, [])
            input_solution_stats[input_id].append(instruction_cnt)

    top_k_slow_inputs = sorted(input_solution_stats, key=lambda x: mean(input_solution_stats[x]), reverse=True)[:top_k]

    return top_k_slow_inputs

def clean_experiment_result(experiment_result: Dict) -> Dict:
    """Remove the solutions that are not AC or TLE"""
    cleaned_experiment_result = {}
    for solution_id in experiment_result:
        if solution_id == "time_limit":
            continue
        verdicts = experiment_result[solution_id]["verdict"]
        if all(verdict in ["AC", "TLE"] for verdict in verdicts):
            cleaned_experiment_result[solution_id] = experiment_result[solution_id]

    return cleaned_experiment_result

def calculate_problem_stats(problem_lang_stats: Dict[str, Dict[str, Dict[str, List[float]]]], problem_id_lang: str, strategies: List[str]) -> Dict:
    # avg denotes the average instruction count among the up to 5 solutions that the 5 slowest inputs can slow down (avg over solutions)
    # max denotes the largest instruction count among the up to 5 solutions that the 5 slowest inputs can slow down (max over solutions)
    problem_id, lang = problem_id_lang.split("-")
    assert problem_lang_stats is not None, f"Problem {problem_id} not found in problem_stats"
    alphacode_result = get_alphacode_result(problem_id)
    alphacode_result = clean_experiment_result(alphacode_result)
    alphacode_slow_inputs = get_top_k_slow_inputs(alphacode_result, lang, top_k=5)
    for strategy in strategies:
        strategy_result = get_experiment_result(problem_id, strategy)
        strategy_result = clean_experiment_result(strategy_result)
        strategy_slow_inputs = get_top_k_slow_inputs(strategy_result, lang, top_k=5)
        for solution_selection_type in ["multi_slow", "multi_fast", "constraint_src"]:
            if solution_selection_type == "constraint_src" and lang != Language.CPP:
                # remove constraint_src entry for non-CPP languages if exists
                if problem_lang_stats[strategy].get("constraint_src") is not None:
                    del problem_lang_stats[strategy]["constraint_src"]
                continue

            subset_solution_ids = select_evaluate_subset_solutions_lang_set_type(
                alphacode_result, lang, solution_selection_type, top_k=5
            ) # slow_5, fast_5 or constraint-src-5 solutions
            for solution_id in subset_solution_ids:
                solution_instruction_cnt = get_solution_avg_or_max_instruction_cnt_over_given_inputs(
                    strategy_result, solution_id, strategy_slow_inputs, "avg"
                )
                problem_lang_stats[strategy][solution_selection_type]["instruction_count"].append(solution_instruction_cnt)

                alphacode_instruction_cnt = get_solution_avg_or_max_instruction_cnt_over_given_inputs(
                    alphacode_result, solution_id, alphacode_slow_inputs, "avg"
                )
                slowdown = solution_instruction_cnt / alphacode_instruction_cnt
                problem_lang_stats[strategy][solution_selection_type]["slowdown"].append(slowdown)

    return problem_lang_stats


### Plotting functions ###
def plot_instruction_count_histogram(problem_stats: Dict):
    """
    Plots a histogram where x is problem ids (problem-language) and shows the per-problem average 
    instruction count of solutions (fast-5, slow-5, constraint-src-5) over inputs produced by 
    different strategies, in log scale.
    """
    problem_ids = list(problem_stats.keys())
    strategies = next(iter(problem_stats.values())).keys()
    
    for strategy in strategies:
        avg_instruction_counts = {
            "fast-5": [],
            "slow-5": [],
            "constraint-src-5": []
        }
        for problem_id in problem_ids:
            for group in avg_instruction_counts.keys():
                avg_instruction_counts[group].append(
                    np.mean(problem_stats[problem_id][strategy][group]["instruction_count"])
                )
        
        x = np.arange(len(problem_ids))
        width = 0.25
        
        fig, ax = plt.subplots()
        ax.bar(x - width, avg_instruction_counts["fast-5"], width, label="fast-5")
        ax.bar(x, avg_instruction_counts["slow-5"], width, label="slow-5")
        ax.bar(x + width, avg_instruction_counts["constraint-src-5"], width, label="constraint-src-5")

        ax.set_yscale("log")
        ax.set_xlabel("Problem IDs (problem-language)")
        ax.set_ylabel("Average Instruction Count (log scale)")
        ax.set_title(f"Instruction Count per Problem ({strategy})")
        ax.set_xticks(x)
        ax.set_xticklabels(problem_ids, rotation=90)
        ax.legend()
        plt.tight_layout()
        plt.show()


def plot_slowdown_histogram(problem_stats: Dict):
    """
    Plots a histogram where x is problem ids (problem-language) and shows the per-problem average 
    slowdown percentage of solutions (fast-5, slow-5, constraint-src-5) over inputs produced by 
    different strategies compared with AlphaCode tests.
    """
    problem_ids = list(problem_stats.keys())
    strategies = next(iter(problem_stats.values())).keys()
    
    for strategy in strategies:
        avg_slowdowns = {
            "fast-5": [],
            "slow-5": [],
            "constraint-src-5": []
        }
        for problem_id in problem_ids:
            for group in avg_slowdowns.keys():
                avg_slowdowns[group].append(
                    np.mean(problem_stats[problem_id][strategy][group]["slowdown"])
                )
        
        x = np.arange(len(problem_ids))
        width = 0.25

        fig, ax = plt.subplots()
        ax.bar(x - width, avg_slowdowns["fast-5"], width, label="fast-5")
        ax.bar(x, avg_slowdowns["slow-5"], width, label="slow-5")
        ax.bar(x + width, avg_slowdowns["constraint-src-5"], width, label="constraint-src-5")

        ax.set_xlabel("Problem IDs (problem-language)")
        ax.set_ylabel("Average Slowdown Percentage")
        ax.set_title(f"Slowdown Percentage per Problem ({strategy})")
        ax.set_xticks(x)
        ax.set_xticklabels(problem_ids, rotation=90)
        ax.legend()
        plt.tight_layout()
        plt.show()


def plot_strategy_performance_pie_chart(problem_stats: Dict, performance_type: Literal["max", "avg"]):
    """
    Plots a pie chart to show how many problems (problem-language) each strategy can achieve 
    the performance (max instruction count, average instruction count) of each group 
    (problem-solution group).
    """
    problem_ids = list(problem_stats.keys())
    strategies = next(iter(problem_stats.values())).keys()
    groups = ["fast-5", "slow-5", "constraint-src-5"]
    
    for group in groups:
        strategy_counts = {strategy: 0 for strategy in strategies}
        
        for problem_id in problem_ids:
            max_or_avg_counts = {
                strategy: np.mean(problem_stats[problem_id][strategy][group]["instruction_count"]) if performance_type == "avg" 
                else max(problem_stats[problem_id][strategy][group]["instruction_count"])
                for strategy in strategies
            }
            best_strategy = max(max_or_avg_counts, key=max_or_avg_counts.get)
            strategy_counts[best_strategy] += 1
        
        fig, ax = plt.subplots()
        ax.pie(strategy_counts.values(), labels=strategy_counts.keys(), autopct='%1.1f%%', startangle=140)
        ax.set_title(f"Strategy Performance ({group}, {performance_type} Instruction Count)")
        plt.show()
