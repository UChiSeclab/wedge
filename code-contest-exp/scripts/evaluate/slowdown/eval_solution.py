import numpy as np
from matplotlib import pyplot as plt
from pathlib import Path
from typing import Dict, List, Tuple
from multiprocessing import Pool, Manager
import os, sys
import logging
import json

from config import config
from utils import get_cf_problems, filter_problems, mean, get_alphacode_result, get_experiment_result, problem_to_id
from cgig.cgig_utils import problem_has_extracted_constraint
from common import Language

# only focus on cpp
# include all solutions

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

def get_top_k_slow_inputs_over_solutions(
    experiment_result: Dict,
    solution_ids: List[str],
    lang: Language,
    top_k: int = 5,
) -> List[str]:
    # Get the top k slow inputs over all solutions (in terms of instruction count)
    input_solution_stats = {}
    for solution_id in solution_ids:
        if not solution_id in experiment_result:
            print(f"[Warning] Solution {solution_id} not found in experiment result")
            continue
        if experiment_result[solution_id]["language"] != str(lang):
            continue
        for input_id in experiment_result[solution_id]["instruction_cnt_dict"]:
            instruction_cnt = mean(experiment_result[solution_id]["instruction_cnt_dict"][input_id])
            input_solution_stats[input_id] = input_solution_stats.get(input_id, [])
            input_solution_stats[input_id].append(instruction_cnt)

    top_k_slow_inputs = sorted(input_solution_stats, key=lambda input_id: mean(input_solution_stats[input_id]), reverse=True)[:top_k]
    return top_k_slow_inputs
    

def get_problem_solution_stats(problem_id: str, strategy: str, top_k: int = 10) -> Dict:
    solution_stats = {} # problem_solution_id -> {input_id -> {"avg_instruction_cnt": float, "avg_time": float}}
    strategy_result = get_experiment_result(problem_id, strategy)
    strategy_result = clean_experiment_result(strategy_result)
    solution_ids = list(strategy_result.keys())
    strategy_slow_inputs = get_top_k_slow_inputs_over_solutions(strategy_result, solution_ids, Language.CPP, top_k=top_k) # top 10 slow inputs, ranked by avg instruction count
    for solution_id in solution_ids:
        if strategy_result[solution_id]["language"] != str(Language.CPP):
            continue

        problem_solution_id = f"{problem_id}_{solution_id}"
        solution_stats[problem_solution_id] = {}
        for input_id in strategy_slow_inputs:
            try:
                solution_stats[problem_solution_id][input_id] = {
                    "avg_instruction_cnt": mean(strategy_result[solution_id]["instruction_cnt_dict"][input_id]),
                    "avg_time": mean(strategy_result[solution_id]["time_dict"][input_id]),
                }
            except KeyError:
                print(f"[Warning] Input {input_id} not found in solution {solution_id} of problem {problem_id} in strategy {strategy}")
                logger.info(f"[Warning] Input {input_id} not found in solution {solution_id} of problem {problem_id} in strategy {strategy}")
                raise KeyError(f"Input {input_id} not found in solution {solution_id} of problem {problem_id} in strategy {strategy}")

    return solution_stats

def parallel_problem_stats(problem_id_list: List[str], strategy: str) -> Dict:
    with Pool(processes=max(1, int(0.5 * os.cpu_count()))) as pool:
        tasks = [(problem_id, strategy) for problem_id in problem_id_list]
        results = pool.starmap(get_problem_solution_stats, tasks)
        merged_solution_stats = {}
        for result in results:
            merged_solution_stats.update(result)
    return merged_solution_stats

def calculate_avg_stats(strategy_solution_stats: Dict, top_k: int) -> Tuple[float, float]:
    avg_instruction_cnt = []
    avg_time = []
    for problem_solution_id in strategy_solution_stats:
        for input_id in list(strategy_solution_stats[problem_solution_id])[:top_k]:
            avg_instruction_cnt.append(strategy_solution_stats[problem_solution_id][input_id]["avg_instruction_cnt"])
            avg_time.append(strategy_solution_stats[problem_solution_id][input_id]["avg_time"])

    return mean(avg_instruction_cnt), mean(avg_time)

if __name__ == '__main__':
    mode = sys.argv[1]
    output_dir = Path("results/slowdown")
    output_dir.mkdir(parents=True, exist_ok=True)
    filtered_problems = filter_problems(get_cf_problems(use_specified_problem=config["use_specified_problem"]))
    problem_id_list = [problem_to_id(problem) for problem in filtered_problems]
    all_strategies = ["evalperf_random_solution", "evalperf_slow_solution", "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "plain_problem", "corpus_raw_fuzz_custom_mutator", "corpus_raw_fuzz_mutator_with_constraint_per_solution", "corpus_raw_fuzz_default_mutator"]
    problem_id_to_exclude = []
    problem_id_list = [problem_id for problem_id in problem_id_list if problem_has_extracted_constraint(problem_id)]
    for problem_id in problem_id_list:
        for strategy in all_strategies:
            if not Path(f"results/{strategy}/{problem_id}.json").exists():
                # print(f"Problem {problem_id} not found for strategy {strategy}")
                problem_id_to_exclude.append(problem_id)
                break

    problem_id_list = [problem_id for problem_id in problem_id_list if problem_id not in problem_id_to_exclude]

    print(f"Number of problems: {len(problem_id_list)}")
    if mode == 'main':
        target_strategies = ["evalperf_random_solution", "evalperf_slow_solution", "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "plain_problem"]
    elif mode == 'ablation':
        target_strategies = ["corpus_instrument_fuzz_mutator_with_constraint_per_solution", "corpus_raw_fuzz_mutator_with_constraint_per_solution", "corpus_raw_fuzz_custom_mutator", "corpus_raw_fuzz_default_mutator"]
    else:
        raise ValueError(f"Invalid mode: {mode}")

    alphacode_sanitized_file = output_dir / "alphacode_sanitized.json"
    if not alphacode_sanitized_file.exists():
        alphacode_sanitized_stats = parallel_problem_stats(problem_id_list, "alphacode_sanitized")
        with open(alphacode_sanitized_file, "w") as f:
            json.dump(alphacode_sanitized_stats, f, indent=4)
            print(f"Dumped alphacode_sanitized to {alphacode_sanitized_file}")
    else:
        with open(alphacode_sanitized_file, "r") as f:
            alphacode_sanitized_stats = json.load(f)

    for strategy in target_strategies:
        output_file = output_dir / f"{strategy}.json"
        if not output_file.exists():
            all_solution_stats = parallel_problem_stats(problem_id_list, strategy)
            with open(output_file, "w") as f:
                json.dump(all_solution_stats, f, indent=4)
            print(f"Dumped {strategy} to {output_file}")
        else:
            with open(output_file, "r") as f:
                all_solution_stats = json.load(f)

        for top_k in [3, 5, 10]:
            avg_instruction_cnt, avg_time = calculate_avg_stats(all_solution_stats, top_k)
            alphacode_avg_instruction_cnt, alphacode_avg_time = calculate_avg_stats(alphacode_sanitized_stats, top_k)
            avg_ict_slowdown = avg_instruction_cnt / alphacode_avg_instruction_cnt
            avg_time_slowdown = avg_time / alphacode_avg_time
            print(f"Strategy: {strategy}, top_k: {top_k}, avg_instruction_cnt: {avg_instruction_cnt}, avg_time: {avg_time}")
            print(f"Strategy: {strategy}, top_k: {top_k}, avg_instruction_cnt_slowdown: {avg_ict_slowdown}, avg_time_slowdown: {avg_time_slowdown}")
