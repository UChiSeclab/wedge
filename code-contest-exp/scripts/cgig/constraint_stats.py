import json
import os
from pathlib import Path
from typing import List, Dict, Tuple
from utils import get_cf_problems, filter_problems, problem_to_id, get_experiment_result, get_input_avg_ict, mean, run_result_exists
from cgig_utils import problem_has_extracted_constraint
from scipy.stats import mannwhitneyu

from config import config

def get_all_input_status_dict(problem_id: str, strategies: List[str], input_classify_dir: Path, instrumented_program_constraint_entrys: List[Path]) -> Dict[str, Dict[str, Dict[str, str]]]:
    problem_dir = input_classify_dir / problem_id
    all_input_status_dict = {strategy: {} for strategy in strategies}
    for solution_id in os.listdir(problem_dir):
        input_pair_id = os.listdir(problem_dir / solution_id)[0]
        input_pair_dir = problem_dir / solution_id / input_pair_id
        if input_pair_dir not in instrumented_program_constraint_entrys:
            continue
        for strategy in strategies:
            input_status_file = input_pair_dir / f"{strategy}.json"
            if not input_status_file.exists():
                continue
            with open(input_status_file, "r") as f:
                input_status_dict = json.load(f)
                if input_status_dict == {"error": "Compile Error"}:
                    continue
                for input_id, status in input_status_dict.items():
                    if input_id not in all_input_status_dict[strategy]:
                        all_input_status_dict[strategy][input_id] = {}
                    all_input_status_dict[strategy][input_id][solution_id] = status["status"]

    return all_input_status_dict

def merge_all_input_status_dict(all_input_status_dict: Dict[str, Dict[str, Dict[str, str]]]) -> Dict[str, Dict[str, str]]:
    merged_input_status_dict = {}
    for strategy, input_status_dict in all_input_status_dict.items():
        merged_input_status_dict[strategy] = {}
        for input_id, solution_status_dict in input_status_dict.items():
            for solution_id, status in solution_status_dict.items():
                cur_status = merged_input_status_dict[strategy].get(input_id, None)
                if cur_status != "Crash":
                    merged_input_status_dict[strategy][input_id] = status

    return merged_input_status_dict

def get_all_non_compilable_instrumented_program_constraint_entrys(problem_id_list: List[str]) -> List[Path]:
    constraint_entrys = []
    input_classify_dir = Path(config["input_classify_dir"])
    for problem_id in problem_id_list:
        problem_dir = input_classify_dir / problem_id
        for solution_id in os.listdir(problem_dir):
            solution_dir = problem_dir / solution_id
            input_pair_id = os.listdir(solution_dir)[0]
            input_pair_dir = solution_dir / input_pair_id
            assert len(list(input_pair_dir.glob("*.json"))) > 0, f"No classify result found in {input_pair_dir}"
            compile_error_flag = False
            for classify_result_file in input_pair_dir.glob("*.json"):
                with open(classify_result_file, "r") as f:
                    classify_result = json.load(f)
                    if classify_result == {"error": "Compile Error"}:
                        compile_error_flag = True
                        break
            if compile_error_flag:
                constraint_entrys.append(input_pair_dir)

    return constraint_entrys

def get_all_instrumented_program_constraint_entrys(problem_id_list: List[str]) -> List[Path]:
    constraint_entrys = []
    input_classify_dir = Path(config["input_classify_dir"])
    for problem_id in problem_id_list:
        problem_dir = input_classify_dir / problem_id
        for solution_id in os.listdir(problem_dir):
            solution_dir = problem_dir / solution_id
            input_pair_id = os.listdir(solution_dir)[0]
            input_pair_dir = solution_dir / input_pair_id
            assert len(list(input_pair_dir.glob("*.json"))) > 0, f"No classify result found in {input_pair_dir}"
            constraint_entrys.append(input_pair_dir)

    return constraint_entrys

def get_likely_bad_constraint_entrys(constraint_entrys: List[Path]) -> List[Path]:
    likely_bad_constraint_entrys = []
    for constraint_entry in constraint_entrys:
        status_dict = json.loads((constraint_entry / "alphacode_sanitized.json").read_text())
        if status_dict == {"error": "Compile Error"}:
            raise ValueError(f"Compile error in {constraint_entry}")
        num_inputs = len(status_dict)
        num_crash = len([status for status in status_dict.values() if status["status"] == "Crash"])
        if num_crash / num_inputs > 0.5:
            likely_bad_constraint_entrys.append(constraint_entry)

    return likely_bad_constraint_entrys

def classify_inputs(strategy: str, merged_input_status_dict: Dict[str, Dict[str, str]]) -> Tuple[List[str], List[str]]:
    constraint_satisfying_inputs = []
    constraint_not_satisfying_inputs = []
    for input_id, status in merged_input_status_dict[strategy].items():
        if status == "Crash":
            constraint_satisfying_inputs.append(input_id)
        else:
            constraint_not_satisfying_inputs.append(input_id)

    return constraint_satisfying_inputs, constraint_not_satisfying_inputs

def get_all_fuzz_inputs(problem_id: str, strategy: str) -> List[str]:
    if strategy.startswith("corpus_instrument_fuzz_"):
        mutator_type = strategy.replace("corpus_instrument_fuzz_", "")
        corpus_gen_dir = Path(config["corpus_instrument_gen_dir"]) / mutator_type
    elif strategy.startswith("corpus_raw_fuzz_"):
        mutator_type = strategy.replace("corpus_raw_fuzz_", "")
        corpus_gen_dir = Path(config["corpus_raw_gen_dir"]) / mutator_type
    else:
        raise ValueError(f"Invalid strategy: {strategy}")

    input_files = []
    problem_dir = corpus_gen_dir / problem_id
    for queue_dir in problem_dir.rglob("queue"):
        for input_file in queue_dir.glob("id:*"):
            input_files.append(input_file)

    return input_files

def calculate_input_satisfy_ratio(problem_id_list: List[str], strategies: List[str], likely_good_constraint_entrys: List[Path]):
    strategy_input_valid_dict = {}
    strategy_input_all_dict = {}
    strategy_input_satisfy_dict = {}
    strategy_input_not_satisfy_dict = {}
    for problem_id in problem_id_list:
        all_input_status_dict = get_all_input_status_dict(problem_id, strategies, Path(config["input_classify_dir"]), likely_good_constraint_entrys)
        merged_input_status_dict = merge_all_input_status_dict(all_input_status_dict)
        for strategy in strategies:
            # count the number of valid inputs
            if run_result_exists(problem_id, strategy):
                if strategy.startswith("corpus_"):
                    strategy_input_valid_dict[strategy] = strategy_input_valid_dict.get(strategy, 0) + len(os.listdir(Path(config["problem_root_dir"]) / problem_id / strategy / "input"))
                    strategy_input_all_dict[strategy] = strategy_input_all_dict.get(strategy, 0) + len(get_all_fuzz_inputs(problem_id, strategy))
                else:
                    if strategy == 'alphacode_sanitized':
                        pass
                    else:
                        strategy_input_valid_dict[strategy] = strategy_input_valid_dict.get(strategy, 0) + len(os.listdir(Path(config["problem_root_dir"]) / problem_id / strategy / "input"))
                        strategy_input_all_dict[strategy] = strategy_input_all_dict.get(strategy, 0) + config["num_tests"]

            # print(f"Problem {problem_id} Strategy {strategy} total input count: {len(merged_input_status_dict[strategy])}")
            if len(merged_input_status_dict[strategy]) == 0:
                print(f"[warning] No input found for strategy {strategy} in problem {problem_id}")
                continue
            constraint_satisfying_inputs, constraint_not_satisfying_inputs = classify_inputs(strategy, merged_input_status_dict)
            satisfy_cnt = len(constraint_satisfying_inputs)
            not_satisfy_cnt = len(constraint_not_satisfying_inputs)

            strategy_input_satisfy_dict[strategy] = strategy_input_satisfy_dict.get(strategy, 0) + satisfy_cnt
            strategy_input_not_satisfy_dict[strategy] = strategy_input_not_satisfy_dict.get(strategy, 0) + not_satisfy_cnt

    for strategy in strategies:
        print('='*20)
        if strategy != 'alphacode_sanitized':
            print(f"Strategy {strategy} total valid input count: {strategy_input_valid_dict[strategy]}")
            print(f"Strategy {strategy} total all input count: {strategy_input_all_dict[strategy]}")
            print(f"Strategy {strategy} validity ratio: {strategy_input_valid_dict[strategy] / strategy_input_all_dict[strategy]}")
        print(f"Strategy {strategy} total input satisfy count: {strategy_input_satisfy_dict[strategy]}")
        print(f"Strategy {strategy} total input not satisfy count: {strategy_input_not_satisfy_dict[strategy]}")
        print(f"Strategy {strategy} satisfy ratio: {strategy_input_satisfy_dict[strategy] / (strategy_input_satisfy_dict[strategy] + strategy_input_not_satisfy_dict[strategy])}")

def compare_inputs(problem_id_list: List[str], strategies: List[str], likely_good_constraint_entrys: List[Path]) -> Tuple[Dict[str, List[float]], Dict[str, List[float]]]:
    # strategy_input_satisfy_dict = {} # {strategy: [input_avg_ict]}
    # strategy_input_not_satisfy_dict = {}
    solution_input_satisfy_dict = {} # {problem_solution_id: [input_avg_ict]}
    solution_input_not_satisfy_dict = {}
    for problem_id in problem_id_list:
        # all_input_status_dict: {strategy: {input_id: {solution_id: status}}}
        all_input_status_dict = get_all_input_status_dict(problem_id, strategies, Path(config["input_classify_dir"]), likely_good_constraint_entrys)
        merged_input_status_dict = merge_all_input_status_dict(all_input_status_dict)
        for strategy in strategies:
            print(f"Problem {problem_id} Strategy {strategy} total input count: {len(merged_input_status_dict[strategy])}")
            if len(merged_input_status_dict[strategy]) == 0:
                print(f"[warning] No input found for strategy {strategy} in problem {problem_id}")
                continue
            if not run_result_exists(problem_id, strategy):
                print(f"[warning] No run result found for strategy {strategy} in problem {problem_id}")
                continue
            result_dict = get_experiment_result(problem_id, strategy)

            for input_id in all_input_status_dict[strategy]:
                for solution_id, status in all_input_status_dict[strategy][input_id].items():
                    problem_solution_id = f"{problem_id}_{solution_id}"
                    if status == "Crash":
                        solution_input_satisfy_dict[problem_solution_id] = solution_input_satisfy_dict.get(problem_solution_id, []) + [get_input_avg_ict(result_dict, input_id)]
                    else:
                        solution_input_not_satisfy_dict[problem_solution_id] = solution_input_not_satisfy_dict.get(problem_solution_id, []) + [get_input_avg_ict(result_dict, input_id)]

    return solution_input_satisfy_dict, solution_input_not_satisfy_dict

def per_solution_compare_input_avg(problem_solution_id_list: List[str], solution_input_satisfy_dict: Dict[str, List[float]], solution_input_not_satisfy_dict: Dict[str, List[float]]):
    ratio_list = []
    significance_list = []
    threshold = 0.05
    significant_case_list = []
    for problem_solution_id in problem_solution_id_list:
        assert len(solution_input_not_satisfy_dict[problem_solution_id]) > 0, f"No not satisfy input found for {problem_solution_id}"
        satisfy_avg = mean(solution_input_satisfy_dict[problem_solution_id])
        not_satisfy_avg = mean(solution_input_not_satisfy_dict[problem_solution_id])
        ratio_list.append(satisfy_avg / not_satisfy_avg)

        # significance test
        stat, p = mannwhitneyu(solution_input_satisfy_dict[problem_solution_id], solution_input_not_satisfy_dict[problem_solution_id])
        significance_list.append(p)
        if p < threshold:
            significant_case_list.append(problem_solution_id)

    print(f"Per solution avg ratio: {mean(ratio_list)}")
    print(f"Per solution avg significance: {mean(significance_list)}")
    print(f"Significant case count: {len(significant_case_list)}")
    print(f"Significant case ratio: {len(significant_case_list) / len(problem_solution_id_list)}")

if __name__ == "__main__":
    problem_id_list = [problem_to_id(problem) for problem in filter_problems(get_cf_problems(use_specified_problem=config["use_specified_problem"]))]
    problem_id_list = [problem_id for problem_id in problem_id_list if problem_has_extracted_constraint(problem_id)]

    strategies = ["alphacode_sanitized", "plain_problem", "evalperf_slow_solution", "evalperf_random_solution", "corpus_instrument_fuzz_mutator_with_constraint_per_solution", "corpus_raw_fuzz_custom_mutator", "corpus_instrument_fuzz_custom_mutator", "corpus_raw_fuzz_default_mutator", "corpus_raw_fuzz_mutator_with_constraint_per_solution"]

    all_instrumented_program_constraint_entrys = get_all_instrumented_program_constraint_entrys(problem_id_list)
    non_compilable_constraint_entrys = get_all_non_compilable_instrumented_program_constraint_entrys(problem_id_list)
    print(f"Total instrumented program constraint entrys: {len(all_instrumented_program_constraint_entrys)}")
    print(f"Total non-compilable instrumented program constraint entrys: {len(non_compilable_constraint_entrys)}")
    compilable_constraint_entrys = list(set(all_instrumented_program_constraint_entrys) - set(non_compilable_constraint_entrys))
    print(f"Total compilable instrumented program constraint entrys: {len(compilable_constraint_entrys)}")
    likely_bad_constraint_entrys = get_likely_bad_constraint_entrys(compilable_constraint_entrys)
    print(f"Total likely bad constraint entrys: {len(likely_bad_constraint_entrys)}")
    likely_good_constraint_entrys = list(set(compilable_constraint_entrys) - set(likely_bad_constraint_entrys))
    print(f"Total likely good constraint entrys: {len(likely_good_constraint_entrys)}")

    # calculate the input satisfy ratio for each strategy
    calculate_input_satisfy_ratio(problem_id_list, strategies, likely_good_constraint_entrys)

    # compare the slowdown of constraint satisfying inputs and constraint not satisfying inputs (per program and overall)
    solution_input_satisfy_dict, solution_input_not_satisfy_dict = compare_inputs(problem_id_list, strategies, likely_good_constraint_entrys)

    print(f'Number of solutions with constraint satisfying inputs: {len(solution_input_satisfy_dict)}')
    print(f'Number of solutions with constraint not satisfying inputs: {len(solution_input_not_satisfy_dict)}')
    solution_with_satisfy_and_not_satisfy_list = list(set(solution_input_satisfy_dict) & set(solution_input_not_satisfy_dict))
    per_solution_compare_input_avg(solution_with_satisfy_and_not_satisfy_list, solution_input_satisfy_dict, solution_input_not_satisfy_dict)
