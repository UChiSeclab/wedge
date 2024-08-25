from typing import List, Dict, Tuple

from utils import get_alphacode_result, mean
from common import Language


def get_solutions_in_language(
    problem: Dict, sol_language: Language
) -> Tuple[List[int], List[str]]:
    """Selects all solutions for a given language. Note that only correct solutions are considered."""
    solutions = [
        (idx, solution)
        for idx, (solution, language) in enumerate(
            zip(problem["solutions"]["solution"], problem["solutions"]["language"])
        )
        if language == sol_language.value
    ]

    solution_idxs, raw_solutions = zip(*solutions) if solutions else ([], [])

    return list(solution_idxs), list(raw_solutions)


def __solution_too_long(problem:Dict, solution_idx:int, threshold:int=500) -> bool:
    """check if the solution is longer than {threshold} lines"""
    solution_code = problem["solutions"]["solution"][solution_idx]
    solution_len = len(solution_code.splitlines())
    
    return solution_len > threshold


def __filter_solution_idx(
    problem:Dict, problem_id: str, language: str, result: Dict, solution_idxs: List[int]
) -> List[int]:
    """Filter out the solution idxs that are not in the result (corner case).\
        Also filter out the solution idxs that are not AC."""
    black_listed_solution_ids = [
        "1056_A_java_0607",  # This solution's format is in a mess
        "484_B_java_0260",  # This solution does not work with cobertura
    ]

    filtered_solution_idxs = []
    for solution_idx in solution_idxs:
        if f"{problem_id}_{language}_{solution_idx:04}" in black_listed_solution_ids:
            print(f"{problem_id}_{language}_{solution_idx:04} is blacklisted")
            continue
        if __solution_too_long(problem, solution_idx):
            print(f"solutions_{solution_idx:04} is too long")
            continue
        if f"solutions_{solution_idx:04}" in result:
            if all(
                v == "AC" for v in result[f"solutions_{solution_idx:04}"]["verdict"]
            ):
                filtered_solution_idxs.append(solution_idx)
            else:
                print(f"solutions_{solution_idx:04} is not AC")
        else:
            print(f"Solution {solution_idx} is not in the result") # TODO: to be investigated and fixed

    return filtered_solution_idxs


def select_solutions(
    problem_id: str, problem: Dict, solution_selection_type:str, prompt_language: Language, top_k: int = None
) -> Tuple[List[str], List[str]]:
    """Selects solutions for feeding to the llm. Note that only correct solutions are considered."""
    solution_idxs, raw_solutions = get_solutions_in_language(problem, prompt_language)
    selected_solutions = []
    selected_solution_idxs = []

    if solution_selection_type == "time_contrast":
        alphacode_result = get_alphacode_result(problem_id)
        filtered_solution_idxs = __filter_solution_idx(
            problem, problem_id, prompt_language, alphacode_result, solution_idxs
        )
        if len(filtered_solution_idxs) < 2:
            return selected_solution_idxs, selected_solutions
        filtered_solution_idxs.sort(
            key=lambda idx: mean(alphacode_result[f"solutions_{idx:04}"]["average_time"])
        )
        fast_solution_idx = filtered_solution_idxs[0]
        slow_solution_idx = filtered_solution_idxs[-1]
        selected_solutions = [
            problem["solutions"]["solution"][fast_solution_idx],
            problem["solutions"]["solution"][slow_solution_idx],
        ]
        selected_solution_idxs = [fast_solution_idx, slow_solution_idx]

    elif solution_selection_type == "multi_slow":
        alphacode_result = get_alphacode_result(problem_id)
        filtered_solution_idxs = __filter_solution_idx(
            problem, problem_id, prompt_language, alphacode_result, solution_idxs
        )
        # select top k slow solutions
        assert top_k is not None and top_k > 1, f"top_k: {top_k}"
        # assert len(filtered_solution_idxs) >= top_k, f"len(filtered_solution_idxs): {len(filtered_solution_idxs)}"
        filtered_solution_idxs.sort(
            key=lambda idx: mean(alphacode_result[f"solutions_{idx:04}"]["average_time"]), reverse=True
        )
        selected_solution_idxs = filtered_solution_idxs[:top_k]
        selected_solutions = [
            problem["solutions"]["solution"][idx] for idx in selected_solution_idxs
        ]

    else:
        raise ValueError(f"Unknown solution selection type: {solution_selection_type}")

    selected_solution_ids = [f"solutions_{idx:04}" for idx in selected_solution_idxs]
    return selected_solution_ids, selected_solutions
