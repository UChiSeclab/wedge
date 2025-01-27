from typing import List, Dict, Tuple, Literal
from pathlib import Path

from utils import get_alphacode_result, mean
from common import Language
from config import config
from cgig.cgig_utils import get_best_input_pair, get_problem_solution_input_pairs, get_solution_and_input_pair_list_with_constraint

def get_solutions_in_language(
    problem: Dict, sol_language: Language
) -> Tuple[List[int], List[str]]:
    """Selects all solutions for a given language. Note that only correct solutions are considered."""
    # TODO: python and python3 should be merged here
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
        # if __solution_too_long(problem, solution_idx):
        #     print(f"solutions_{solution_idx:04} is too long")
        #     continue
        if result[f"solutions_{solution_idx:04}"]["time_dict"] == {}:
            print(f"[WARNING] problem {problem_id} solutions_{solution_idx:04} has empty time_dict")
            continue
        if f"solutions_{solution_idx:04}" in result:
            if all(
                v in ["AC", "TLE"] for v in result[f"solutions_{solution_idx:04}"]["verdict"]
            ):
                filtered_solution_idxs.append(solution_idx)
            else:
                print(f"solutions_{solution_idx:04} is not AC or TLE")
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

    alphacode_result = get_alphacode_result(problem_id)
    filtered_solution_idxs = __filter_solution_idx(
        problem, problem_id, prompt_language, alphacode_result, solution_idxs
    )

    if solution_selection_type == "slow_fast":
        if len(filtered_solution_idxs) < 2:
            return None, None
        filtered_solution_idxs.sort(
            key=lambda idx: mean(alphacode_result[f"solutions_{idx:04}"]["average_instruction_cnt"])
        )
        fast_solution_idx = filtered_solution_idxs[0]
        slow_solution_idx = filtered_solution_idxs[-1]
        selected_solutions = [
            problem["solutions"]["solution"][fast_solution_idx],
            problem["solutions"]["solution"][slow_solution_idx],
        ]
        selected_solution_idxs = [fast_solution_idx, slow_solution_idx]

    elif solution_selection_type == "multi_slow":
        # select top k slow solutions
        assert top_k is not None and top_k > 1, f"top_k: {top_k}"
        # assert len(filtered_solution_idxs) >= top_k, f"len(filtered_solution_idxs): {len(filtered_solution_idxs)}"
        filtered_solution_idxs.sort(
            key=lambda idx: mean(alphacode_result[f"solutions_{idx:04}"]["average_instruction_cnt"]), reverse=True
        )
        selected_solution_idxs = filtered_solution_idxs[:top_k]
        selected_solutions = [
            problem["solutions"]["solution"][idx] for idx in selected_solution_idxs
        ]

    elif solution_selection_type == "slow":
        if len(filtered_solution_idxs) < 1:
            return None, None
        filtered_solution_idxs.sort(
            key=lambda idx: mean(alphacode_result[f"solutions_{idx:04}"]["average_instruction_cnt"])
        )
        slow_solution_idx = filtered_solution_idxs[-1]
        selected_solutions = [problem["solutions"]["solution"][slow_solution_idx]]
        selected_solution_idxs = [slow_solution_idx]

    elif solution_selection_type == "multi_fast":
        # select top k fast solutions
        assert top_k is not None and top_k > 1, f"top_k: {top_k}"
        # assert len(filtered_solution_idxs) >= top_k, f"len(filtered_solution_idxs): {len(filtered_solution_idxs)}"
        filtered_solution_idxs.sort(
            key=lambda idx: mean(alphacode_result[f"solutions_{idx:04}"]["average_instruction_cnt"])
        )
        selected_solution_idxs = filtered_solution_idxs[:top_k]
        selected_solutions = [
            problem["solutions"]["solution"][idx] for idx in selected_solution_idxs
        ]

    elif solution_selection_type == "fast":
        if len(filtered_solution_idxs) < 1:
            return None, None
        filtered_solution_idxs.sort(
            key=lambda idx: mean(alphacode_result[f"solutions_{idx:04}"]["average_instruction_cnt"])
        )
        fast_solution_idx = filtered_solution_idxs[0]
        selected_solutions = [problem["solutions"]["solution"][fast_solution_idx]]
        selected_solution_idxs = [fast_solution_idx]

    elif solution_selection_type == "random":
        if len(filtered_solution_idxs) < 1:
            return None, None

        import random
        random.seed(0)
        selected_solution_idx = random.choice(filtered_solution_idxs)
        selected_solutions = [problem["solutions"]["solution"][selected_solution_idx]]
        selected_solution_idxs = [selected_solution_idx]

    elif solution_selection_type == "instrumented_first_solution":
        if len(filtered_solution_idxs) < 1:
            return None, None
        problem_solution_input_pairs = get_problem_solution_input_pairs()
        best_input_pair, solution_ids = get_best_input_pair(problem_id, problem_solution_input_pairs[problem_id])
        if not best_input_pair:
            raise ValueError(f"No input pair found for {problem_id}")
        slow_input_id, fast_input_id = best_input_pair
        # solution_id = select_first_solution(solution_ids)
        solution_id = solution_ids[0] # sorted

        instrumented_solution_file = Path(config["constraints_dir"]) / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}" / "transformed_program.cpp"
        if not instrumented_solution_file.exists():
            raise ValueError(f"File {instrumented_solution_file} does not exist")
        selected_solutions = [instrumented_solution_file.read_text()]
        selected_solution_idxs = [int(solution_id.split("_")[1])]

    elif solution_selection_type == "instrumented_multi_solution":
        if len(filtered_solution_idxs) < 1:
            return None, None
        problem_solution_input_pairs = get_problem_solution_input_pairs()
        solution_input_pairs = problem_solution_input_pairs[problem_id]
        selected_solution_ids = []
        selected_solutions = []
        for solution_id in list(solution_input_pairs.keys())[:top_k]:
            best_input_pair_id = list(solution_input_pairs[solution_id])[0] # best input pair for this solution
            slow_input_id, fast_input_id = best_input_pair_id.split("@")
            instrumented_program_file = Path(config["constraints_dir"]) / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}" / "transformed_program.cpp"
            if not instrumented_program_file.exists():
                raise ValueError(f"File {instrumented_program_file} does not exist")
            selected_solutions.append(instrumented_program_file.read_text())
            selected_solution_idxs.append(int(solution_id.split("_")[1]))

        if len(selected_solutions) < top_k:
            print(f"Warning: only {len(selected_solutions)} solutions found for {problem_id}")

    elif solution_selection_type == "no_solution":
        return [], []

    else:
        raise ValueError(f"Unknown solution selection type: {solution_selection_type}")

    selected_solution_ids = [f"solutions_{idx:04}" for idx in selected_solution_idxs]
    return selected_solution_ids, selected_solutions


def find_slow_fast_solution_cov_file(
    problem_id: str,experiment_name: str,
    prompt_language: str
) -> Tuple[Path, Path]:
    """ Find the fast and slow solution coverage files."""
    assert "diff_solution" in experiment_name, f"experiment_name: {experiment_name}"
    cov_dir = (
        Path(config["coverage_hit_count_output_dir"])
        / problem_id
        / experiment_name
        / prompt_language
    )
    cov_files = [file.absolute() for file in Path(cov_dir).rglob("*.cov")]
    assert len(cov_files) == 2, f"cov_files: {cov_files}"
    slow_solution_cov_file = [
        file for file in cov_files if file.parent.name == "slow_solution"
    ][0]
    fast_solution_cov_file = [
        file for file in cov_files if file.parent.name == "fast_solution"
    ][0]

    return slow_solution_cov_file, fast_solution_cov_file

def select_evaluate_subset_solutions_lang_set_type(
    problem: Dict, language: Language, solution_selection_type: Literal["multi_slow", "multi_fast", "constraint_src"], top_k: int = 5
) -> List[str]: # List of solution ids
    # select the slowest 5 solutions, fastest 5 solutions,\
    # and <= 5 solutions that are used in constraint generation
    problem_id = problem["name"].split(".")[0]
    alphacode_result = get_alphacode_result(problem_id)
    solution_idxs, raw_solutions = get_solutions_in_language(problem, language)
    filtered_solution_idxs = __filter_solution_idx(
        problem, problem_id, str(language), alphacode_result, solution_idxs
    )
    solution_ids = [f"solutions_{idx:04}" for idx in filtered_solution_idxs]

    for solution_id in solution_ids:
        if len(alphacode_result[solution_id]["average_instruction_cnt"]) == 0:
            print(f"[Warning] problem {problem_id} solution {solution_id} has no instruction count info")

    if solution_selection_type == "multi_slow":
        solution_ids.sort(
            key=lambda solution_id: mean(alphacode_result[solution_id]["average_instruction_cnt"]), reverse=True
        )
        selected_solution_ids = solution_ids[:top_k]
    elif solution_selection_type == "multi_fast":
        solution_ids.sort(
            key=lambda solution_id: mean(alphacode_result[solution_id]["average_instruction_cnt"])
        )
        selected_solution_ids = solution_ids[:top_k]
    elif solution_selection_type == "constraint_src":
        assert language == Language.CPP, f"language: {language}"
        solution_and_input_pair_list = get_solution_and_input_pair_list_with_constraint(problem_id, top_k)
        if len(solution_and_input_pair_list) == 0:
            raise ValueError(f"No solution with constraint info found for {problem_id}")
        if len(solution_and_input_pair_list) < top_k:
            print(f"[Warning] only {len(solution_and_input_pair_list)} solutions found for {problem_id}")
        selected_solution_ids = [solution_id for solution_id, _ in solution_and_input_pair_list]
    else:
        raise ValueError(f"Unknown solution selection type: {solution_selection_type}")

    return selected_solution_ids

def select_evaluate_subset_all(
    problem: Dict, top_k: int = 5
) -> List[str]: # List of solution ids
    # select the slowest 5 solutions, fastest 5 solutions (for python, java, cpp),\
    # and <= 5 solutions that are used in constraint generation (for cpp only)
    selected_solution_ids = []
    for language in [Language.PYTHON, Language.JAVA, Language.CPP]:
        if language == Language.CPP:
            selected_solution_ids.extend(select_evaluate_subset_solutions_lang_set_type(problem, language, "constraint_src", top_k))
        selected_solution_ids.extend(select_evaluate_subset_solutions_lang_set_type(problem, language, "multi_slow", top_k))
        selected_solution_ids.extend(select_evaluate_subset_solutions_lang_set_type(problem, language, "multi_fast", top_k))

    # remove duplicates
    selected_solution_ids = list(set(selected_solution_ids))

    return selected_solution_ids