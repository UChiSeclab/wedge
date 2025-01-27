import numpy as np
from matplotlib import pyplot as plt
from pathlib import Path
from typing import Dict, List, Literal, Any
from multiprocessing import Pool, Manager
import os

from utils import get_cf_problems, filter_problems, get_instruction_cnt, mean, get_alphacode_result, get_experiment_result, problem_to_id
from selector.select_solution import select_evaluate_subset_solutions_lang_set_type
from common import Language

"""
1. plot a histogram where x is problem ids (problem-language) and show the per-problem average instruction count of solutions (multi_fast, multi_slow, constraint_src) over inputs produced by different strategies, log scale
2. plot a histogram where x is problem ids (problem-language) and show the per-problem average slow down percentage of solutions (multi_fast, multi_slow, constraint_src) over inputs produced by different strategies compared with alphacode tests
3. plot pie-chart to show how many problems (problem-language) each strategy can achieve the the performance (max instruction count, average instruction count) of each group (problem-solution group)

data structure:
problem_stats = {
    problem_id-lang: {
        strategy: {
            "multi_fast": {
                "instruction_count": [float],
                "slowdown": [float],
            },
            "multi_slow": {
                "instruction_count": [float],
                "slowdown": [float],
            },
            "constraint_src": {
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
    """Get the instruction count of the solution on the given inputs"""
    print("debug", solution_id)
    instruction_cnt_dict = experiment_result[solution_id]["instruction_cnt_dict"]
    instruction_cnt_list = []
    for input_id in input_ids:
        # instruction count of the solution on the input
        instruction_cnt_list.append(mean(instruction_cnt_dict[input_id]))

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

def calculate_problem_stats(problem_lang_stats: Dict[str, Dict[str, Dict[str, List[float]]]], problem_id_lang: str, strategies: List[str], problem: Dict[str, Any]) -> Dict:
    # avg denotes the average instruction count among the up to 5 solutions that the 5 slowest inputs can slow down (avg over solutions)
    # max denotes the largest instruction count among the up to 5 solutions that the 5 slowest inputs can slow down (max over solutions)
    problem_id, lang = problem_id_lang.split("-")
    assert problem_lang_stats is not None, f"Problem {problem_id} not found in problem_stats"
    alphacode_result = get_alphacode_result(problem_id)
    alphacode_result = clean_experiment_result(alphacode_result)
    alphacode_slow_inputs = get_top_k_slow_inputs(alphacode_result, Language.str_to_lang(lang), top_k=5)
    for strategy in strategies:
        strategy_result = get_experiment_result(problem_id, strategy)
        strategy_result = clean_experiment_result(strategy_result)
        strategy_slow_inputs = get_top_k_slow_inputs(strategy_result, Language.str_to_lang(lang), top_k=5)
        for solution_selection_type in ["multi_slow", "multi_fast", "constraint_src"]:
            print("debug", problem_id, lang, strategy, solution_selection_type)
            if solution_selection_type == "constraint_src" and Language.str_to_lang(lang) != Language.CPP:
                # remove constraint_src entry for non-CPP languages if exists
                if problem_lang_stats[strategy].get("constraint_src") is not None:
                    del problem_lang_stats[strategy]["constraint_src"]
                continue

            subset_solution_ids = select_evaluate_subset_solutions_lang_set_type(
                problem, Language.str_to_lang(lang), solution_selection_type, top_k=5
            ) # slow_5, fast_5 or constraint_src solutions
            for solution_id in subset_solution_ids:
                if solution_id not in strategy_result:
                    print(f"[Warning] Solution {solution_id} of problem {problem_id} not found in strategy {strategy}")
                    continue
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
    Plots a histogram where x is problem ids (problem-language), and compares 
    per-problem average instruction count of solutions across strategies, for each group (multi_fast, multi_slow, constraint_src).
    Bars for different strategies are grouped together for the same problem_id-lang.
    """
    problem_id_langs = list(problem_stats.keys())
    strategies = list(next(iter(problem_stats.values())).keys())
    groups = ["multi_fast", "multi_slow", "constraint_src"]

    for group in groups:
        fig, ax = plt.subplots()
        width = 0.15  # Width of each bar

        # Aggregate data for the current group
        strategy_avg_instruction_counts = {strategy: [] for strategy in strategies}
        valid_problem_id_langs = []

        for problem_id_lang in problem_id_langs:
            if group == "constraint_src" and Language.str_to_lang(problem_id_lang.split("-")[1]) != Language.CPP:
                continue  # Skip non-CPP languages for constraint_src
            valid_problem_id_langs.append(problem_id_lang)
            for strategy in strategies:
                counts = problem_stats[problem_id_lang][strategy][group]["instruction_count"]
                strategy_avg_instruction_counts[strategy].append(np.mean(counts) if counts else 0)

        # Plot bars for each problem, grouped by strategies
        x = np.arange(len(valid_problem_id_langs))  # Positions for problem_id-lang
        for i, strategy in enumerate(strategies):
            ax.bar(
                x + i * width - (len(strategies) / 2 - 0.5) * width,  # Center groups for each problem_id-lang
                strategy_avg_instruction_counts[strategy],
                width,
                label=strategy
            )

        # Finalize the plot
        ax.set_yscale("log")
        ax.set_xlabel("Problem IDs (problem-language)")
        ax.set_ylabel("Average Instruction Count (log scale)")
        ax.set_title(f"Instruction Count Comparison ({group})")
        ax.set_xticks(x)
        ax.set_xticklabels(valid_problem_id_langs, rotation=45, ha="right")
        ax.legend(title="Strategies")
        # set the figure size to be larger
        fig.set_size_inches(18.5, 10.5)
        plt.savefig(f"{group}_instruction_count_comparison_grouped.png")
        plt.close()



def plot_slowdown_histogram(problem_stats: Dict):
    """
    Plots a histogram where x is problem ids (problem-language), and compares 
    per-problem average slowdown percentage of solutions across strategies, for each group (multi_fast, multi_slow, constraint_src).
    Bars for different strategies are grouped together for the same problem_id-lang.
    """
    problem_id_langs = list(problem_stats.keys())
    strategies = list(next(iter(problem_stats.values())).keys())
    groups = ["multi_fast", "multi_slow", "constraint_src"]

    for group in groups:
        fig, ax = plt.subplots()
        width = 0.15  # Width of each bar

        # Aggregate data for the current group
        strategy_avg_slowdowns = {strategy: [] for strategy in strategies}
        valid_problem_id_langs = []

        for problem_id_lang in problem_id_langs:
            if group == "constraint_src" and Language.str_to_lang(problem_id_lang.split("-")[1]) != Language.CPP:
                continue  # Skip non-CPP languages for constraint_src
            valid_problem_id_langs.append(problem_id_lang)
            for strategy in strategies:
                slowdowns = problem_stats[problem_id_lang][strategy][group]["slowdown"]
                strategy_avg_slowdowns[strategy].append(np.mean(slowdowns) if slowdowns else 0)

        # Plot bars for each problem, grouped by strategies
        x = np.arange(len(valid_problem_id_langs))  # Positions for problem_id-lang
        for i, strategy in enumerate(strategies):
            ax.bar(
                x + i * width - (len(strategies) / 2 - 0.5) * width,  # Center groups for each problem_id-lang
                strategy_avg_slowdowns[strategy],
                width,
                label=strategy
            )

        # Finalize the plot
        ax.set_xlabel("Problem IDs (problem-language)")
        ax.set_ylabel("Average Slowdown Percentage")
        ax.set_title(f"Slowdown Percentage Comparison ({group})")
        ax.set_xticks(x)
        ax.set_xticklabels(valid_problem_id_langs, rotation=45, ha="right")
        ax.legend(title="Strategies")
        fig.set_size_inches(18.5, 10.5)
        plt.savefig(f"{group}_slowdown_comparison_grouped.png")
        plt.close()




def plot_strategy_performance_pie_chart(problem_stats: Dict, performance_type: Literal["max", "avg"]):
    """
    Plots a pie chart to show how often each strategy achieves the best performance (max/avg instruction count)
    within each group (multi_fast, multi_slow, constraint_src).
    """
    problem_id_langs = list(problem_stats.keys())
    strategies = next(iter(problem_stats.values())).keys()
    groups = ["multi_fast", "multi_slow", "constraint_src"]

    for group in groups:
        strategy_counts = {strategy: 0 for strategy in strategies}

        for problem_id_lang in problem_id_langs:
            if group == "constraint_src" and Language.str_to_lang(problem_id_lang.split("-")[1]) != Language.CPP:
                continue
            max_or_avg_counts = {
                strategy: np.mean(problem_stats[problem_id_lang][strategy][group]["instruction_count"]) if performance_type == "avg" 
                else max(problem_stats[problem_id_lang][strategy][group]["instruction_count"], default=0)
                for strategy in strategies
            }
            best_strategy = max(max_or_avg_counts, key=max_or_avg_counts.get)
            strategy_counts[best_strategy] += 1

        fig, ax = plt.subplots()
        ax.pie(strategy_counts.values(), labels=strategy_counts.keys(), autopct='%1.1f%%', startangle=140)
        ax.set_title(f"Strategy Performance ({group}, {performance_type} Instruction Count)")
        plt.tight_layout()
        plt.savefig(f"{group}_{performance_type}_strategy_performance_pie_chart.png")
        plt.close()


def process_problem_lang(args):
    """
    A helper function to process a single (problem, lang) pair.
    The arguments are unpacked from a tuple to allow compatibility with multiprocessing.
    """
    problem, lang, strategies, problem_stats = args
    problem_id_lang = problem_to_id(problem) + f"-{lang}"
    problem_lang_stats = problem_stats.get(problem_id_lang)
    problem_lang_stats = calculate_problem_stats(problem_lang_stats, problem_id_lang, strategies, problem)
    return problem_id_lang, problem_lang_stats

def parallel_problem_stats(problems, strategies):
    """
    Distribute the work of calculating problem stats across multiple processes.
    """
    # Initialize problem stats in a shared manager dict
    with Manager() as manager:
        problem_stats = manager.dict(init_problem_stats(problems, strategies))  # Shared dict for multiprocessing
        langs = [Language.CPP, Language.PYTHON, Language.JAVA]

        # Create a list of all (problem, lang) combinations
        tasks = [(problem, lang, strategies, problem_stats) for problem in problems for lang in langs]

        # Use a Pool to distribute tasks across processes
        with Pool(processes=max(1, int(0.5 * os.cpu_count()))) as pool:
            results = pool.map(process_problem_lang, tasks)

        # Update problem_stats with the results
        for problem_id_lang, problem_lang_stats in results:
            problem_stats[problem_id_lang] = problem_lang_stats

        # Convert the managed dict back to a regular dict before returning
        return dict(problem_stats)

if __name__ == '__main__':
    problem_id_list = ['1067_B', '1096_F', '1165_F1', '1184_C1', '1204_E', '1213_D1', '1216_E2', '1225_D', '1230_C', '1286_A', '131_E', '1322_B', '1328_B', '1332_E', '1446_C', '1447_E', '16_B', '288_B', '289_D', '301_B', '351_E', '479_E', '520_B', '546_C', '63_B', '706_D', '758_A', '773_B', '787_A', '808_E', '846_B', '894_B', '903_A', '911_C', '932_E', '937_B', '938_B', '999_F']
    problem_id_list = problem_id_list
    strategies = ["alphacode", "evalperf_random_solution", "evalperf_slow_solution", "plain_problem", "corpus_instrument_fuzz_mutator_with_constraint_per_solution"]
    problems = filter_problems(get_cf_problems(use_specified_problem=True))
    problems = [problem for problem in problems if problem_to_id(problem) in problem_id_list]

    problem_stats = parallel_problem_stats(problems, strategies)

    plot_instruction_count_histogram(problem_stats)
    plot_slowdown_histogram(problem_stats)
    plot_strategy_performance_pie_chart(problem_stats, "max")