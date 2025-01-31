from pathlib import Path
from typing import List, Tuple, Dict, Literal
import json

from config import config
from common import Language

def cov_file_exists(problem_id: str, solution_id: str, input_id: str, language: Language = Language.CPP) -> bool:
    # check if the coverage file exists
    # we only support C++ for now
    assert language == Language.CPP, "Only C++ is supported for now"
    assert input_id.endswith(".in"), f"Invalid input id {input_id}"
    assert (Path(config["cov_data_dir"]) / "alphacode" / problem_id / str(language) / solution_id).exists(), f"coverage dir does not exist for {problem_id}/{language}/{solution_id}"
    cov_file = Path(config["cov_data_dir"]) / "alphacode" / problem_id / str(language) / solution_id / input_id[:-3] / "coverage.xml"
    return cov_file.exists()

def find_mutator_file(mutator_dir: Path) -> Path:
    mutator_try_dirs = list(mutator_dir.glob("try_*"))
    assert len(mutator_try_dirs) > 0, f"No try dirs found in {mutator_dir}"
    mutator_last_try = sorted(
        mutator_try_dirs, key=lambda x: int(x.name.split("_")[-1])
    )[-1]

    return mutator_last_try / "mutator.py"

def select_first_solution(solution_ids: List[str]) -> str:
    # ensure deterministic selection
    solution_ids = sorted(solution_ids)
    return solution_ids[0]

def select_high_ratio_solution(input_pair:Tuple[str, str], solution_ids: List[str], solution_input_pairs: Dict) -> str:
    # sort the solutions by the run time ratio
    # TODO: not needed for now
    pass

def get_best_input_pair_by_freq(solution_input_pairs: Dict[str, List[Tuple[str, str]]]) -> Tuple[Tuple[str, str], List[str]]:
    # rank the input pairs by the frequency in the solution_input_pairs
    if len(solution_input_pairs) == 0:
        return None, []

    input_pair_freq = {}
    for solution_id in solution_input_pairs:
        for slow_input_id, fast_input_id in solution_input_pairs[solution_id]:
            input_pair_freq[(slow_input_id, fast_input_id)] = input_pair_freq.get((slow_input_id, fast_input_id), []) + [solution_id]

    best_input_pair = max(input_pair_freq, key=lambda x: len(input_pair_freq[x]))

    return best_input_pair, input_pair_freq[best_input_pair] # not sorted

@DeprecationWarning
def get_best_input_pair_deprecated(problem_id:str, solution_input_pairs: Dict[str, List[Tuple[str, str]]]) -> Tuple[Tuple[str, str], List[str]]:
    # get the input pair with the highest similarity and get the solution where the input pair is used and the run time ratio is the highest
    # assume the list of input pairs has been sorted by similarity
    if len(solution_input_pairs) == 0:
        return None, []

    highest_similarity = 0
    highest_similarity_input_pair = None
    sorted_solution_ids = []
    best_perf_ratio = 0

    input_pair_solution_map = {}
    for solution_id in solution_input_pairs:
        for input_pair_id in solution_input_pairs[solution_id].keys():
            slow_input_id, fast_input_id = input_pair_id.split("@")
            if not (cov_file_exists(problem_id, solution_id, slow_input_id) \
                and cov_file_exists(problem_id, solution_id, fast_input_id)):
                continue
            input_pair_solution_map[(slow_input_id, fast_input_id)] = input_pair_solution_map.get((slow_input_id, fast_input_id), []) + [solution_id]
            similarity, perf_ratio = solution_input_pairs[solution_id][input_pair_id]
            if similarity > highest_similarity:
                highest_similarity = similarity
                highest_similarity_input_pair = (slow_input_id, fast_input_id)

    if highest_similarity_input_pair is None: # all similarities are 0
        solution_id = select_first_solution(list(solution_input_pairs.keys()))
        highest_similarity_input_pair = tuple(list(solution_input_pairs[solution_id].keys())[0].split("@"))

    sorted_solution_ids = sorted(input_pair_solution_map[highest_similarity_input_pair], key=lambda x: solution_input_pairs[x][f"{highest_similarity_input_pair[0]}@{highest_similarity_input_pair[1]}"][1], reverse=True) # sort by run time ratio

    # return the input pair with the highest similarity and the solutions ranked by run time ratio
    return highest_similarity_input_pair, sorted_solution_ids

def get_best_input_pairs(problem_id: str, solution_input_pairs: Dict[str, List[Tuple[str, str]]], top_k: int = 5) -> Dict[str, List[str]]:
    if len(solution_input_pairs) == 0:
        return {}
    # sort input pairs by similarity
    input_pair_similarity = {}
    for solution_id in solution_input_pairs:
        for input_pair_id in solution_input_pairs[solution_id].keys():
            input_pair_similarity[input_pair_id] = solution_input_pairs[solution_id][input_pair_id][0]
    sorted_input_pairs = sorted(list(input_pair_similarity.keys()), key=lambda x: input_pair_similarity[x], reverse=True)

    # get the solutions for each input pair
    input_pair_solution_map = {}
    for input_pair_id in sorted_input_pairs:
        input_pair_solution_map[input_pair_id] = []
        for solution_id in solution_input_pairs:
            if input_pair_id in solution_input_pairs[solution_id]:
                if cov_file_exists(problem_id, solution_id, input_pair_id.split("@")[0])\
                    and cov_file_exists(problem_id, solution_id, input_pair_id.split("@")[1]):
                    input_pair_solution_map[input_pair_id].append(solution_id)

    # sort the solutions by the run time ratio
    for input_pair_id in input_pair_solution_map:
        input_pair_solution_map[input_pair_id].sort(
            key=lambda x: solution_input_pairs[x][input_pair_id][1], reverse=True
        )

    if len(input_pair_solution_map) < top_k:
        print(f"Warning: only {len(input_pair_solution_map)} input pairs found for {problem_id}")

    return {input_pair_id: input_pair_solution_map[input_pair_id] for input_pair_id in list(input_pair_solution_map.keys())[:top_k]}

def problem_has_extracted_constraint(problem_id: str) -> bool:
    # check if the problem has extracted constraints. this function is adhoc
    return (Path(config["constraints_dir"]) / problem_id).exists()

def parse_constraints_content_from_response(gpt_response_file: Path, include_code: bool = True) -> str:
    # parse the constraints content from the GPT response
    response = gpt_response_file.read_text()
    if include_code:
        return response
    lines = response.split("\n")
    for i in range(len(lines)):
        line = lines[i].strip()
        if "=== Checker Response ===" in line:
                break

    assert i < len(lines) - 1, f"No constraints found in {gpt_response_file}"

    return "\n".join(lines[:i])

def get_problem_solution_input_pairs() -> Dict[str, Dict[str, List[Tuple[str, str]]]]:
    # get the problem-solution-input pairs mapping
    input_pairs_file = Path(config["input_pairs_dir"]) / "content_similar_problem_solution_input_pairs_sorted.json"
    return json.loads(input_pairs_file.read_text())

def get_solution_and_input_pair_list_with_constraint(problem_id: str, top_k: int) -> Tuple[str, Tuple[str, str]]:
    # get the solution and input pair list where the constraints are extracted
    problem_solution_input_pairs = get_problem_solution_input_pairs()
    solution_input_pairs = problem_solution_input_pairs[problem_id]
    solution_and_input_pair_list = []
    for solution_id in solution_input_pairs:
        if len(solution_and_input_pair_list) >= top_k:
            break
        for input_pair_id in solution_input_pairs[solution_id]:
            slow_input_id, fast_input_id = input_pair_id.split("@")
            constraint_gen_dir = Path(config["constraints_dir"]) / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}"
            if (constraint_gen_dir / "gpt_response.txt").exists():
                solution_and_input_pair_list.append((solution_id, (slow_input_id, fast_input_id)))
                break

    if len(solution_and_input_pair_list) < top_k:
        print(f"Warning: only {len(solution_and_input_pair_list)} solution-input pairs found for {problem_id}")

    return solution_and_input_pair_list

@DeprecationWarning
def parse_constraints_content(problem_id: str, mutator_type: Literal["mutator_with_constraint", "mutator_with_constraint_multi"], top_k_constraints: int = 1, include_code: bool = True) -> str:
    # parse the constraints content from one or multiple GPT responses
    extracted_constraints_dir = Path(config["constraints_dir"])
    solution_input_pairs = get_problem_solution_input_pairs()[problem_id]
    constraint_files = []
    for solution_id in solution_input_pairs:
        if len(constraint_files) >= top_k_constraints:
            break
        best_input_pair_id = list(solution_input_pairs[solution_id])[0] # best input pair for this solution
        slow_input_id, fast_input_id = best_input_pair_id.split("@")
        constraint_gen_dir = extracted_constraints_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}"
        if not constraint_gen_dir.exists():
            print(f"[INFO] No constraint gen dir found for {problem_id}/{solution_id}/{slow_input_id[:-3]}_{fast_input_id[:-3]}")
            continue
        constraint_file = constraint_gen_dir / "gpt_response.txt"
        if not constraint_file.exists():
            raise FileNotFoundError(f"No constraint file found for {problem_id}/{solution_id}/{slow_input_id[:-3]}_{fast_input_id[:-3]}")
        constraint_files.append(constraint_file)

    if len(constraint_files) == 0:
        raise FileNotFoundError(f"No constraint files found for {problem_id}")

    if len(constraint_files) < top_k_constraints:
        print(f"[Warning] Only {len(constraint_files)} constraint files found for {problem_id}")

    if mutator_type == "mutator_with_constraint":
        assert top_k_constraints == 1, "top_k_constraints should be 1 for mutator_with_constraint"
        assert len(constraint_files) == 1, f"Multiple constraint files found for {problem_id}"
        return parse_constraints_content_from_response(constraint_files[0], include_code)
    else:
        assert top_k_constraints > 1, "top_k_constraints should be greater than 1 for mutator_with_constraint_multi"
        constraints = []
        for i in range(1, len(constraint_files) + 1):
            constraint_file = constraint_files[i - 1]
            constraints.append(f"** Constraint {i} **")
            constraints.append(parse_constraints_content_from_response(constraint_file, include_code))
            constraints.append("")

        return "\n".join(constraints)
