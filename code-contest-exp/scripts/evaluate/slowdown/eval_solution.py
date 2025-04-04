from pathlib import Path
from typing import Dict, List, Tuple, Set
from multiprocessing import Pool
import os, sys
import logging
import json
from scipy.stats import mannwhitneyu

from config import config
from utils import get_cf_problems, filter_problems, mean, get_experiment_result, problem_to_id
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

def get_intersection_problem_solutions(alphacode_sanitized_stats: Dict, all_strategy_solution_stats: Dict, all_strategies: List[str]) -> Set:
    intersection_problem_solution_ids = set(alphacode_sanitized_stats.keys())
    print(f"Number of solutions in alphacode: {len(intersection_problem_solution_ids)}")
    for strategy in all_strategies:
        print(f"Number of solutions in {strategy}: {len(all_strategy_solution_stats[strategy].keys())}")
        intersection_problem_solution_ids = intersection_problem_solution_ids.intersection(set(all_strategy_solution_stats[strategy].keys()))
        print(f"Number of solutions after intersecting with {strategy}: {len(intersection_problem_solution_ids)}")

    print(f"Number of problems with all strategies: {len(intersection_problem_solution_ids)}")
    return intersection_problem_solution_ids

def clean_experiment_result(experiment_result: Dict, exclude_kill_wa: bool=True) -> Dict:
    """Remove the solutions that are not AC or TLE"""
    cleaned_experiment_result = {}
    for solution_id in experiment_result:
        if solution_id == "time_limit":
            continue
        verdicts = experiment_result[solution_id]["verdict"]
        if exclude_kill_wa:
            if all(verdict in ["AC", "TLE"] for verdict in verdicts):
                cleaned_experiment_result[solution_id] = experiment_result[solution_id]
        else:
            if all(verdict in ["AC", "TLE", "KILL", "WA"] for verdict in verdicts):
                cleaned_experiment_result[solution_id] = experiment_result[solution_id]

    return cleaned_experiment_result

@DeprecationWarning
def get_top_k_slow_inputs_over_solutions(
    experiment_result: Dict,
    solution_ids: List[str],
    lang: Language,
    top_k: int = 10,
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

def get_top_k_slow_inputs_for_solution(
    strategy: str,
    experiment_result: Dict,
    solution_id: str,
    lang: Language,
    top_k: int = 10,
) -> Tuple[List[str], List[str]]:
    # Get the top k slow inputs for a solution (in terms of instruction count)
    input_ict_slow_inputs = []
    input_time_slow_inputs = []
    if not solution_id in experiment_result:
        print(f"[Warning] Solution {solution_id} not found in experiment result")
        return input_ict_slow_inputs, input_time_slow_inputs
    if experiment_result[solution_id]["language"] != str(lang):
        return input_ict_slow_inputs, input_time_slow_inputs
    input_solution_ict_stats = {}
    input_solution_time_stats = {}
    for input_id in experiment_result[solution_id]["instruction_cnt_dict"]:
        instruction_cnt = mean(experiment_result[solution_id]["instruction_cnt_dict"][input_id])
        time = mean(experiment_result[solution_id]["time_dict"][input_id])
        if strategy.endswith("default_mutator") and time == 60:
            # Skip the killed AFL++ inputs since they are likely invalid but skipped the validator
            continue

        input_solution_ict_stats[input_id] = instruction_cnt
        input_solution_time_stats[input_id] = time

    input_ict_slow_inputs = sorted(input_solution_ict_stats, key=lambda input_id: input_solution_ict_stats[input_id], reverse=True)[:top_k]
    input_time_slow_inputs = sorted(input_solution_time_stats, key=lambda input_id: input_solution_time_stats[input_id], reverse=True)[:top_k]

    return input_ict_slow_inputs, input_time_slow_inputs

def get_problem_solution_stats(problem_id: str, strategy: str, top_k: int = 10) -> Dict:
    solution_stats = {} # problem_solution_id -> {ict_stats: {input_id: avg_instruction_cnt}, time_stats: {input_id: avg_time}}
    strategy_result = get_experiment_result(problem_id, strategy)
    if strategy.startswith("alphacode"):
        strategy_result = clean_experiment_result(strategy_result, exclude_kill_wa=True)
    else:
        strategy_result = clean_experiment_result(strategy_result, exclude_kill_wa=False)
    solution_ids = list(strategy_result.keys())
    for solution_id in solution_ids:
        if strategy_result[solution_id]["language"] != str(Language.CPP):
            continue

        strategy_ict_slow_inputs, strategy_time_slow_inputs = get_top_k_slow_inputs_for_solution(strategy, strategy_result, solution_id, Language.CPP, top_k=top_k) # top 10 slow inputs, ranked by avg instruction count

        problem_solution_id = f"{problem_id}_{solution_id}"
        solution_stats[problem_solution_id] = {}
        solution_stats[problem_solution_id]["ict_stats"] = {}
        solution_stats[problem_solution_id]["time_stats"] = {}
        for input_id in strategy_ict_slow_inputs:
            try:
                solution_stats[problem_solution_id]["ict_stats"][input_id] = mean(strategy_result[solution_id]["instruction_cnt_dict"][input_id])
            except KeyError:
                print(f"[Warning] Input {input_id} not found in solution {solution_id} of problem {problem_id} in strategy {strategy}")
                logger.info(f"[Warning] Input {input_id} not found in solution {solution_id} of problem {problem_id} in strategy {strategy}")
                raise KeyError(f"Input {input_id} not found in solution {solution_id} of problem {problem_id} in strategy {strategy}")
        for input_id in strategy_time_slow_inputs:
            try:
                solution_stats[problem_solution_id]["time_stats"][input_id] = mean(strategy_result[solution_id]["time_dict"][input_id])
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

def calculate_avg_stats(problem_solution_ids: Set, strategy_solution_stats: Dict, top_k: int) -> Tuple[float, float]:
    avg_instruction_cnt_list = []
    avg_time_list = []
    for problem_solution_id in problem_solution_ids:
        debug_list = [strategy_solution_stats[problem_solution_id]["ict_stats"][input_id] for input_id in list(strategy_solution_stats[problem_solution_id]["ict_stats"])]
        if len(debug_list) == 0:
            print(f"Problem solution {problem_solution_id} has no slow inputs")
            raise ValueError(f"Problem solution {problem_solution_id} has no slow inputs")
        avg_instruction_cnt_list.append(mean([strategy_solution_stats[problem_solution_id]["ict_stats"][input_id] for input_id in list(strategy_solution_stats[problem_solution_id]["ict_stats"])[:top_k]]))
        avg_time_list.append(mean([strategy_solution_stats[problem_solution_id]["time_stats"][input_id] for input_id in list(strategy_solution_stats[problem_solution_id]["time_stats"])[:top_k]]))

    return mean(avg_instruction_cnt_list), mean(avg_time_list)

def calculate_per_solution_slowdown(problem_solution_ids: Set, strategy_solution_stats: Dict, alphacode_sanitized_stats: Dict, top_k: int) -> Tuple[float, float]:
    avg_ict_slowdown_list = []
    avg_time_slowdown_list = []
    for problem_solution_id in problem_solution_ids:
        avg_instruction_cnt = mean([strategy_solution_stats[problem_solution_id]["ict_stats"][input_id] for input_id in list(strategy_solution_stats[problem_solution_id]["ict_stats"])[:top_k]])
        avg_time = mean([strategy_solution_stats[problem_solution_id]["time_stats"][input_id] for input_id in list(strategy_solution_stats[problem_solution_id]["time_stats"])[:top_k]])
        alphacode_avg_instruction_cnt = mean([alphacode_sanitized_stats[problem_solution_id]["ict_stats"][input_id] for input_id in list(alphacode_sanitized_stats[problem_solution_id]["ict_stats"])[:top_k]])
        alphacode_avg_time = mean([alphacode_sanitized_stats[problem_solution_id]["time_stats"][input_id] for input_id in list(alphacode_sanitized_stats[problem_solution_id]["time_stats"])[:top_k]])
        avg_ict_slowdown_list.append(avg_instruction_cnt / alphacode_avg_instruction_cnt)
        avg_time_slowdown_list.append(avg_time / alphacode_avg_time)

    return mean(avg_ict_slowdown_list), mean(avg_time_slowdown_list)

def calculate_avg_maps(problem_solution_ids: Set, strategy_solution_stats: Dict, top_k: int) -> Tuple[Dict, Dict]:
    avg_instruction_cnt_map = {} # problem_solution_id -> [avg_instruction_cnt]
    avg_time_map = {}
    for problem_solution_id in problem_solution_ids:
        avg_instruction_cnt_map[problem_solution_id] = []
        avg_time_map[problem_solution_id] = []
        avg_instruction_cnt_map[problem_solution_id].extend([strategy_solution_stats[problem_solution_id]["ict_stats"][input_id] for input_id in list(strategy_solution_stats[problem_solution_id]["ict_stats"])[:top_k]])
        avg_time_map[problem_solution_id].extend([strategy_solution_stats[problem_solution_id]["time_stats"][input_id] for input_id in list(strategy_solution_stats[problem_solution_id]["time_stats"])[:top_k]])

    return avg_instruction_cnt_map, avg_time_map

def significance_test(strategy_data_list_map: Dict[str, Dict[str, Dict[str, List[float]]]], strategy_1: str, strategy_2: str, top_k: int):
    avg_larger_cnt = 0
    significance_larger_cnt = 0
    for problem_solution_id in strategy_data_list_map[strategy_1][top_k]:
        data_1 = strategy_data_list_map[strategy_1][top_k][problem_solution_id]
        data_2 = strategy_data_list_map[strategy_2][top_k][problem_solution_id]
        if mean(data_1) > mean(data_2):
            avg_larger_cnt += 1
        # test if data_1 is significantly larger than data_2
        stat, p = mannwhitneyu(data_1, data_2, alternative="greater")
        if p < 0.05:
            significance_larger_cnt += 1
            # print(f"[INFO] Problem solution {problem_solution_id} has significant larger instruction count in {strategy_1} than {strategy_2}")

    print(f"Strategy {strategy_1} has larger average instruction count than {strategy_2} in {avg_larger_cnt} out of {len(strategy_data_list_map[strategy_1][top_k])} solutions")
    print(f"Strategy {strategy_1} has significantly larger average instruction count than {strategy_2} in {significance_larger_cnt} out of {len(strategy_data_list_map[strategy_1][top_k])} solutions")

def make_latex_table(table_data: Dict):
    # table_data: {strategy -> {top_k -> {"avg_instruction_cnt": float, "avg_instruction_cnt_slowdown": float}}}
    strategy_name_map = {
        "corpus_instrument_fuzz_mutator_with_constraint_per_solution": r"\tool",
        "evalperf_slow_solution": r"\evalperfslow",
        "evalperf_random_solution": r"\evalperfrandom",
        "plain_problem": r"\directprompting",
        "corpus_raw_fuzz_mutator_with_constraint_per_solution": r"\wedgenoinstrument",
        "corpus_raw_fuzz_custom_mutator": r"\wedgenoconstraint",
        "corpus_raw_fuzz_default_mutator": r"\wedgenomutator",
        "corpus_instrument_fuzz_custom_mutator": r"\wedgecustom",
    }
    head = r"\begin{tabular}{lllll}"
    head += r"\toprule "
    head += r"\# of slowest inputs & Top-10 & Top-5 & Top-3 & Top-1 \\ \midrule"
    subtitle_slowdown = r"\rowcolor[gray]{0.95} \multicolumn{5}{c}{Relative slowdown compared with default tests} \\"
    subtitle_ict = r"\rowcolor[gray]{0.95} \multicolumn{5}{c}{Number of instructions ($\times 10^8$)} \\"
    slowdown_content = ""
    ict_content = ""
    for strategy in table_data:
        slowdown_content += f" {strategy_name_map[strategy]} & "
        ict_content += f" {strategy_name_map[strategy]} & "
        for top_k in [10, 5, 3, 1]:
            if strategy == "corpus_instrument_fuzz_mutator_with_constraint_per_solution":
                slowdown_content += r"\textbf{%.2f} & " % table_data[strategy][top_k]["avg_instruction_cnt_slowdown"]
                ict_content += r"\textbf{%.2f} & " % ((table_data[strategy][top_k]["avg_instruction_cnt"]) / 10**8)
            else:
                slowdown_content += r"%.2f & " % table_data[strategy][top_k]["avg_instruction_cnt_slowdown"]
                ict_content += r"%.2f & " % ((table_data[strategy][top_k]["avg_instruction_cnt"]) / 10**8)
        slowdown_content = slowdown_content[:-2] + " \\\\"
        ict_content = ict_content[:-2] + " \\\\"
    slowdown_content += r" \midrule"
    ict_content += r" \bottomrule"
    tail = r"\end{tabular}"

    table = "\n".join([head, subtitle_slowdown, slowdown_content, subtitle_ict, ict_content, tail])

    print(table)

if __name__ == '__main__':
    mode = sys.argv[1]
    output_dir = Path(f"{config['result_root_dir']}/slowdown")
    output_dir.mkdir(parents=True, exist_ok=True)
    filtered_problems = filter_problems(get_cf_problems(use_specified_problem=config["use_specified_problem"]))
    problem_id_list = [problem_to_id(problem) for problem in filtered_problems]
    all_strategies = ["evalperf_random_solution", "evalperf_slow_solution", "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "plain_problem", "corpus_raw_fuzz_mutator_with_constraint_per_solution", "corpus_instrument_fuzz_custom_mutator", "corpus_raw_fuzz_custom_mutator", "corpus_raw_fuzz_default_mutator"]

    problem_id_to_exclude = []
    problem_id_list = [problem_id for problem_id in problem_id_list if problem_has_extracted_constraint(problem_id)]
    for problem_id in problem_id_list:
        for strategy in all_strategies:
            if not Path(f"{config['result_root_dir']}/{strategy}/{problem_id}.json").exists():
                # print(f"Problem {problem_id} not found for strategy {strategy}")
                problem_id_to_exclude.append(problem_id)
                break

    problem_id_list = [problem_id for problem_id in problem_id_list if problem_id not in problem_id_to_exclude]

    print(f"Number of problems: {len(problem_id_list)}")
    print(f"problem list: {problem_id_list}")
    if mode == 'main':
        target_strategies = ["corpus_instrument_fuzz_mutator_with_constraint_per_solution", "evalperf_random_solution", "evalperf_slow_solution", "plain_problem"]
    elif mode == 'ablation':
        target_strategies = ["corpus_raw_fuzz_default_mutator", "corpus_raw_fuzz_custom_mutator", "corpus_raw_fuzz_mutator_with_constraint_per_solution", "corpus_instrument_fuzz_mutator_with_constraint_per_solution"]
        # appendix
        # target_strategies = ["corpus_raw_fuzz_custom_mutator", "corpus_raw_fuzz_mutator_with_constraint_per_solution", "corpus_instrument_fuzz_custom_mutator", "corpus_instrument_fuzz_mutator_with_constraint_per_solution"]
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

    all_strategy_solution_stats = {}
    table_data = {}
    for strategy in all_strategies:
        output_file = output_dir / f"{strategy}.json"
        if not output_file.exists():
            strategy_solution_stats = parallel_problem_stats(problem_id_list, strategy)
            with open(output_file, "w") as f:
                json.dump(strategy_solution_stats, f, indent=4)
            print(f"Dumped {strategy} to {output_file}")
        else:
            with open(output_file, "r") as f:
                strategy_solution_stats = json.load(f)
                
        all_strategy_solution_stats[strategy] = strategy_solution_stats
    
    intersection_problem_solution_ids = get_intersection_problem_solutions(alphacode_sanitized_stats, all_strategy_solution_stats, all_strategies)
    print(f"Number of problem solutions with all strategies: {len(intersection_problem_solution_ids)}")

    strategy_ict_list_map = {}
    for strategy in target_strategies:
        table_data[strategy] = {}
        strategy_ict_list_map[strategy] = {}
        strategy_solution_stats = all_strategy_solution_stats[strategy]
        for top_k in [1, 3, 5, 10]:
            table_data[strategy][top_k] = {}
            strategy_ict_list_map[strategy][top_k] = {}
            avg_instruction_cnt, avg_time = calculate_avg_stats(intersection_problem_solution_ids, strategy_solution_stats, top_k)
            alphacode_avg_instruction_cnt, alphacode_avg_time = calculate_avg_stats(intersection_problem_solution_ids, alphacode_sanitized_stats, top_k)
            avg_ict_slowdown = avg_instruction_cnt / alphacode_avg_instruction_cnt
            avg_time_slowdown = avg_time / alphacode_avg_time
            avg_ict_per_solution_slowdown, avg_time_per_solution_slowdown = calculate_per_solution_slowdown(intersection_problem_solution_ids, strategy_solution_stats, alphacode_sanitized_stats, top_k)
            print(f"Strategy: {strategy}, top_k: {top_k}, avg_instruction_cnt: {avg_instruction_cnt}, avg_time: {avg_time}")
            print(f"Strategy: {strategy}, top_k: {top_k}, avg_instruction_cnt_slowdown: {avg_ict_slowdown}, avg_time_slowdown: {avg_time_slowdown}")
            print(f"Strategy: {strategy}, top_k: {top_k}, avg_instruction_cnt_per_solution_slowdown: {avg_ict_per_solution_slowdown}, avg_time_per_solution_slowdown: {avg_time_per_solution_slowdown}")
            table_data[strategy][top_k]["avg_instruction_cnt"] = avg_instruction_cnt
            # table_data[strategy][top_k]["avg_instruction_cnt_slowdown"] = avg_ict_slowdown
            # table_data[strategy][top_k]["avg_instruction_cnt_per_solution_slowdown"] = avg_ict_per_solution_slowdown
            table_data[strategy][top_k]["avg_instruction_cnt_slowdown"] = avg_ict_per_solution_slowdown

            avg_instruction_cnt_map, avg_time_map = calculate_avg_maps(intersection_problem_solution_ids, strategy_solution_stats, top_k)
            strategy_ict_list_map[strategy][top_k] = avg_instruction_cnt_map

    if mode == "ablation":
        # Mann-Whitney U test
        significance_test(strategy_ict_list_map, "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "corpus_raw_fuzz_mutator_with_constraint_per_solution", 10)
        significance_test(strategy_ict_list_map, "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "corpus_instrument_fuzz_custom_mutator", 10)
        significance_test(strategy_ict_list_map, "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "corpus_raw_fuzz_custom_mutator", 10)
        significance_test(strategy_ict_list_map, "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "corpus_raw_fuzz_default_mutator", 10)
        significance_test(strategy_ict_list_map, "corpus_instrument_fuzz_custom_mutator", "corpus_raw_fuzz_custom_mutator", 10)
        significance_test(strategy_ict_list_map, "corpus_raw_fuzz_custom_mutator", "corpus_instrument_fuzz_custom_mutator", 10)

    make_latex_table(table_data)
