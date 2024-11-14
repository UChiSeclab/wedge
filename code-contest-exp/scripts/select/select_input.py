from pathlib import Path
import json
from typing import Literal, Dict, List, Tuple

from utils import squeeze_time_dict, get_alphacode_result
from config import config

debug = False

def __filter_input(slow_time_stat: Dict[str, float], fast_time_stat: Dict[str, float]):
    """there might be some corner cases where the input is not present in both solutions"""
    """we only include existing inputs"""
    only_in_slow = set(slow_time_stat.keys()) - set(fast_time_stat.keys())
    only_in_fast = set(fast_time_stat.keys()) - set(slow_time_stat.keys())
    if debug and only_in_slow:
        print("only in slow inputs:", only_in_slow)
    if debug and only_in_fast:
        print("only in fast inputs:", only_in_fast)

    return {k: v for k, v in slow_time_stat.items() if k in fast_time_stat}


def find_slow_fast_input_cov_file(
    problem_id: str, experiment_name: str, prompt_language: str
):
    if experiment_name.endswith("_contract"):
        experiment_name = experiment_name[:-9]
    cov_dir = (
        Path(config["coverage_hit_count_output_dir"])
        / problem_id
        / experiment_name
        / prompt_language
    )
    cov_files = [file.absolute() for file in Path(cov_dir).rglob("*.cov")]
    assert len(cov_files) == 2, f"cov_files: {cov_files} in {cov_dir}"
    slow_input_cov_file = [
        file for file in cov_files if file.parent.name == "slow_input"
    ][0]
    fast_input_cov_file = [
        file for file in cov_files if file.parent.name == "fast_input"
    ][0]

    return slow_input_cov_file, fast_input_cov_file

def find_slow_fast_input_cov_files_for_multi_solution(
    problem_id: str, experiment_name: str, prompt_language: str
) -> Tuple[List[Path], List[Path]]:
    cov_dir = (
        Path(config["coverage_hit_count_output_dir"])
        / problem_id
        / experiment_name
        / prompt_language
    )
    cov_files = [file.absolute() for file in Path(cov_dir).rglob("*.cov")]
    # assert len(cov_files) == 2 * len(solution_codes), f"cov_files: {len(cov_files)} in {cov_dir}"
    slow_input_cov_files = [
        file for file in cov_files if file.parent.parent.name == "slow_input"
    ]
    fast_input_cov_files = [
        file for file in cov_files if file.parent.parent.name == "fast_input"
    ]

    return slow_input_cov_files, fast_input_cov_files

def select_slow_fast_input(
    problem_id: str,
    solution_id: str,
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> Tuple[str, str]:
    alphacode_result = get_alphacode_result(problem_id)
    time_stat = squeeze_time_dict(alphacode_result[solution_id]["time_dict"], use_max_or_avg)
    slow_input = max(time_stat, key=lambda x: time_stat[x])
    fast_input = min(time_stat, key=lambda x: time_stat[x])

    return slow_input, fast_input

def select_slow_fast_input_for_multi_solution(
    problem_id: str,
    solution_ids: List[str],
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> Tuple[str, str]:
    """for the given solutions, select the on average slowest and fastest input"""
    """note that all solutions are correct and should have the same (full) inputs"""
    alphacode_result = get_alphacode_result(problem_id)
    input_solution_stat = {} # input -> {solution_id -> time}
    input_times = {} # input -> [time]
    for solution_id in solution_ids:
        time_stat = squeeze_time_dict(alphacode_result[solution_id]["time_dict"], use_max_or_avg)
        for input_name, time in time_stat.items():
            if input_name not in input_solution_stat:
                input_solution_stat[input_name] = {}
            input_solution_stat[input_name][solution_id] = time

    for input_name, solution_time in input_solution_stat.items():
        input_times[input_name] = list(solution_time.values())

    slow_input = max(input_times, key=lambda x: sum(input_times[x])/len(input_times[x]))
    fast_input = min(input_times, key=lambda x: sum(input_times[x])/len(input_times[x]))

    return slow_input, fast_input

def select_most_differentiating_input(
    problem_id: str,
    fast_solution_id: str,
    slow_solution_id: str,
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> str:
    # To discuss with Casper: we may need to focus on the existing inputs (not generated ones)
    alphacode_result = get_alphacode_result(problem_id)
    slow_time_stat, fast_time_stat = (
        alphacode_result[slow_solution_id]["time_dict"],
        alphacode_result[fast_solution_id]["time_dict"],
    )
    slow_time_stat = squeeze_time_dict(slow_time_stat, use_max_or_avg)
    fast_time_stat = squeeze_time_dict(fast_time_stat, use_max_or_avg)
    most_differentiating_input = max(
        __filter_input(slow_time_stat, fast_time_stat),
        key=lambda x: abs(slow_time_stat[x] - fast_time_stat[x]),
    )

    # select the input that has the largest difference across different solutions
    if debug:
        print("fast_solution_id:", fast_solution_id)
        print("slow_solution_id:", slow_solution_id)
        print("slow time: ", slow_time_stat[most_differentiating_input])
        print("fast time: ", fast_time_stat[most_differentiating_input])

    return most_differentiating_input
