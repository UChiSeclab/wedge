import numpy as np
from matplotlib import pyplot as plt
from pathlib import Path
from typing import Dict, List, Literal, Any
from multiprocessing import Pool, Manager
import os, sys
import logging

from utils import get_cf_problems, filter_problems, get_instruction_cnt, mean, get_alphacode_result, get_experiment_result, problem_to_id
from selector.select_solution import select_evaluate_subset_solutions_lang_set_type
from common import Language


# Configure logging
logging.basicConfig(
    level=logging.DEBUG,  # Set the log level
    format="%(asctime)s [%(levelname)s] %(message)s",
    handlers=[
        logging.StreamHandler(sys.stdout),  # Log to console
        logging.FileHandler("debug.log"),  # Log to a file
    ]
)
logger = logging.getLogger()

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

FIG_DIR = Path("fig") if len(sys.argv) == 1 else Path(sys.argv[1])
FIG_DIR.mkdir(exist_ok=True)

MULTI_LANG = False

def get_solution_instruction_cnt_list_over_given_inputs(
    experiment_result: Dict,
    solution_id: str,
    input_ids: List[str]
) -> List[float]:
    """Get the instruction count of the solution on the given inputs"""
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

def get_top_k_slow_inputs_over_solutions(
    experiment_result: Dict,
    solution_ids: List[str],
    lang: Language,
    top_k: int = 5,
) -> List[str]:
    input_solution_stats = {}
    for solution_id in solution_ids:
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
        if all(verdict in ["AC", "TLE", "WA"] for verdict in verdicts):
            cleaned_experiment_result[solution_id] = experiment_result[solution_id]

    return cleaned_experiment_result

def calculate_problem_stats(problem_lang_stats: Dict[str, Dict[str, Dict[str, List[float]]]], problem_id_lang: str, strategies: List[str], problem: Dict[str, Any]) -> Dict:
    # avg denotes the average instruction count among the up to 5 solutions that the 5 slowest inputs can slow down (avg over solutions)
    # max denotes the largest instruction count among the up to 5 solutions that the 5 slowest inputs can slow down (max over solutions)
    problem_id, lang = problem_id_lang.split("-")
    assert problem_lang_stats is not None, f"Problem {problem_id} not found in problem_stats"
    alphacode_result = get_alphacode_result(problem_id, sanitize=True)
    alphacode_result = clean_experiment_result(alphacode_result)
    alphacode_slow_inputs = get_top_k_slow_inputs(alphacode_result, Language.str_to_lang(lang), top_k=5)
    logger.info("=" * 80)
    logger.info(f"Problem {problem_id}, Alphacode Slow Inputs: {alphacode_slow_inputs}")
    for strategy in strategies:
        strategy_result = get_experiment_result(problem_id, strategy)
        strategy_result = clean_experiment_result(strategy_result)
        # strategy_slow_inputs = get_top_k_slow_inputs(strategy_result, Language.str_to_lang(lang), top_k=5)
        for solution_selection_type in ["multi_slow", "multi_fast", "constraint_src"]:
            if solution_selection_type == "constraint_src" and Language.str_to_lang(lang) != Language.CPP:
                # remove constraint_src entry for non-CPP languages if exists
                if problem_lang_stats[strategy].get("constraint_src") is not None:
                    del problem_lang_stats[strategy]["constraint_src"]
                continue

            subset_solution_ids = select_evaluate_subset_solutions_lang_set_type(
                problem, Language.str_to_lang(lang), solution_selection_type, top_k=5
            ) # slow_5, fast_5 or constraint_src solutions
            strategy_slow_inputs = get_top_k_slow_inputs_over_solutions(strategy_result, subset_solution_ids, Language.str_to_lang(lang), top_k=5)
            logger.info(f"Problem {problem_id}, Strategy {strategy}, Slow Inputs: {strategy_slow_inputs}, subset_solution_ids: {subset_solution_ids}")
            for solution_id in subset_solution_ids:
                if solution_id not in strategy_result:
                    logger.info(f"[Warning] Solution {solution_id} of problem {problem_id} not found in strategy {strategy}")
                    continue
                solution_instruction_cnt = get_solution_avg_or_max_instruction_cnt_over_given_inputs(
                    strategy_result, solution_id, strategy_slow_inputs, "avg"
                )
                if strategy in ["plain_problem", "corpus_instrument_fuzz_mutator_with_constraint_per_solution"]:
                    logger.info(f"Problem {problem_id}, Strategy {strategy}, Solution {solution_id}, Group {solution_selection_type}, Avg Instruction Count: {solution_instruction_cnt}")
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
            if MULTI_LANG:
                valid_problem_id_langs.append(problem_id_lang)
            else:
                valid_problem_id_langs.append(problem_id_lang.split("-")[0])
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
        plt.savefig(FIG_DIR / f"{group}_instruction_count_comparison_grouped.png")
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
            if MULTI_LANG:
                valid_problem_id_langs.append(problem_id_lang)
            else:
                valid_problem_id_langs.append(problem_id_lang.split("-")[0])
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
        ax.set_yscale("log")
        ax.set_xlabel("Problem IDs (problem-language)")
        ax.set_ylabel("Average Slowdown Percentage")
        ax.set_title(f"Slowdown Percentage Comparison ({group})")
        ax.set_xticks(x)
        ax.set_xticklabels(valid_problem_id_langs, rotation=45, ha="right")
        ax.legend(title="Strategies")
        fig.set_size_inches(18.5, 10.5)
        plt.savefig(FIG_DIR / f"{group}_slowdown_comparison_grouped.png")
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
        plt.savefig(FIG_DIR / f"{group}_strategy_performance_pie_{performance_type}.png")
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
        if MULTI_LANG:
            langs = [Language.CPP, Language.PYTHON, Language.JAVA]
        else:
            langs = [Language.CPP]

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

def print_problem_stats(problem_stats: Dict):
    """
    Prints the problem_stats for each problem (problem_id-lang) in a structured format.
    """
    for problem_id_lang, strategies_data in problem_stats.items():
        logger.info(f"Problem: {problem_id_lang}")
        for strategy, group_data in strategies_data.items():
            logger.info(f"  Strategy: {strategy}")
            for group, metrics in group_data.items():
                instruction_counts = metrics["instruction_count"]
                slowdowns = metrics["slowdown"]
                logger.info(f"    Group: {group}")
                logger.info(f"      Instruction Counts: {instruction_counts}")
                logger.info(f"      Slowdowns: {slowdowns}")
        logger.info("\n" + "-"*80 + "\n")  # Separator between problems for better readability

if __name__ == '__main__':
    # problem_id_list = ['1003_A', '1041_A', '1041_F', '1042_E', '1057_C', '1061_B', '1061_C', '1064_A', '1067_B', '1082_E', '1096_F', '1097_C', '1102_C', '1114_C', '111_B', '1131_B', '1141_A', '1165_F1', '1184_C1', '1201_C', '1203_D1', '1204_E', '1210_A', '1213_D1', '1216_E2', '1225_B1', '1225_C', '1225_D', '1228_C', '1228_E', '1230_C', '1242_B', '1243_D', '1246_A', '1253_E', '1260_D', '1260_E', '1261_B1', '1271_E', '1286_A', '1288_C', '128_D', '131_E', '1322_B', '1328_B', '1332_E', '1334_E', '1340_B', '1340_C', '1355_E', '1359_E', '1396_C', '1397_B', '1404_C', '1408_D', '1424_J', '1428_E', '1433_F', '143_D', '1446_C', '1447_E', '145_C', '148_A', '148_E', '1513_E', '1550_D', '16_B', '181_B', '191_B', '199_B', '215_D', '237_C', '239_A', '278_B', '288_B', '289_D', '2_A', '301_B', '31_C', '321_B', '349_B', '351_E', '356_C', '366_C', '44_B', '476_C', '479_E', '484_B', '520_B', '525_C', '529_E', '543_A', '546_C', '552_C', '554_C', '559_C', '566_F', '574_D', '577_A', '583_D', '598_D', '603_C', '608_C', '63_B', '662_D', '663_B', '676_E', '687_C', '690_C1', '69_B', '706_D', '713_C', '731_F', '734_B', '747_C', '758_A', '768_C', '768_E', '773_B', '774_J', '787_A', '791_B', '799_C', '803_F', '808_E', '821_B', '846_B', '848_B', '863_B', '889_B', '894_B', '898_E', '903_A', '910_B', '910_C', '911_C', '926_B', '932_E', '937_B', '938_B', '978_E', '988_F', '993_A', '993_B', '996_B', '999_E', '999_F'] # all 147 problems
    # problem_id_list = ['1041_F', '1057_C', '1061_B', '1061_C', '1064_A', '1096_F', '1097_C', '1102_C', '1114_C', '111_B', '1141_A', '1201_C', '1203_D1', '1204_E', '1210_A', '1213_D1', '1216_E2', '1225_D', '1230_C', '1242_B', '1243_D', '1261_B1', '128_D', '131_E', '1322_B', '1340_B', '1340_C', '1355_E', '1359_E', '1397_B', '1408_D', '1428_E', '1433_F', '143_D', '1446_C', '16_B', '181_B', '191_B', '199_B', '237_C', '278_B', '2_A', '301_B', '321_B', '349_B', '351_E', '366_C', '44_B', '479_E', '484_B', '525_C', '529_E', '546_C', '554_C', '559_C', '574_D', '577_A', '583_D', '603_C', '63_B', '662_D', '663_B', '676_E', '687_C', '69_B', '713_C', '731_F', '758_A', '768_C', '768_E', '773_B', '774_J', '791_B', '803_F', '846_B', '863_B', '889_B', '898_E', '911_C', '926_B', '937_B', '993_A', '993_B', '996_B', '999_E', '999_F'] # >= 50 inputs
    # problem_id_list = ['1041_F', '1064_A', '1097_C', '1114_C', '111_B', '1141_A', '1201_C', '1203_D1', '1204_E', '1210_A', '1216_E2', '1230_C', '1242_B', '1243_D', '1261_B1', '131_E', '1340_B', '1340_C', '1359_E', '1408_D', '143_D', '16_B', '199_B', '237_C', '278_B', '2_A', '349_B', '44_B', '479_E', '484_B', '546_C', '554_C', '577_A', '603_C', '662_D', '663_B', '687_C', '774_J', '846_B', '911_C', '926_B', '937_B', '993_A'] # >= 50 inputs, input size < 50
    problem_id_list = ["911_C"]

    strategies = ["alphacode", "evalperf_random_solution", "evalperf_slow_solution", "plain_problem", "corpus_instrument_fuzz_mutator_with_constraint_per_solution"]
    problems = filter_problems(get_cf_problems(use_specified_problem=True))
    problems = [problem for problem in problems if problem_to_id(problem) in problem_id_list]

    problem_stats = parallel_problem_stats(problems, strategies)

    print_problem_stats(problem_stats)

    plot_instruction_count_histogram(problem_stats)
    plot_slowdown_histogram(problem_stats)
    plot_strategy_performance_pie_chart(problem_stats, "max")