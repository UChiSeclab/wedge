from pathlib import Path
import json
from typing import Literal, Dict, List, Tuple

from utils import squeeze_time_dict

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


def select_slow_fast_input(
    solutions_stat_file: Path,
    solution_id: str,
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> Tuple[str, str]:
    data = json.loads(solutions_stat_file.read_text())
    solution_stat = data[solution_id]
    time_stat = squeeze_time_dict(solution_stat["time_dict"], use_max_or_avg)
    slow_input = max(time_stat, key=lambda x: time_stat[x])
    fast_input = min(time_stat, key=lambda x: time_stat[x])

    return slow_input, fast_input

def select_slow_fast_input_for_multi_solution(
    solutions_stat_file: Path,
    solution_ids: List[str],
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> Tuple[str, str]:
    """for the given solutions, select the on average slowest and fastest input"""
    """note that all solutions are correct and should have the same (full) inputs"""
    data = json.loads(solutions_stat_file.read_text())
    input_solution_stat = {} # input -> {solution_id -> time}
    input_times = {} # input -> [time]
    for solution_id in solution_ids:
        solution_stat = data[solution_id]
        time_stat = squeeze_time_dict(solution_stat["time_dict"], use_max_or_avg)
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
    solutions_stat_file: Path,
    fast_solution_id: str,
    slow_solution_id: str,
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> str:
    # To discuss with Casper: we may need to focus on the existing inputs (not generated ones)
    data = json.loads(solutions_stat_file.read_text())
    slow_solution_stat, fast_solution_stat = (
        data[slow_solution_id],
        data[fast_solution_id],
    )
    slow_time_stat, fast_time_stat = (
        slow_solution_stat["time_dict"],
        fast_solution_stat["time_dict"],
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
