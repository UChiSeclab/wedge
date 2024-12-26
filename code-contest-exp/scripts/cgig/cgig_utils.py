from pathlib import Path
from typing import List, Tuple, Dict

from config import config
from utils import get_run_time

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

def get_best_input_pair(solution_input_pairs: Dict[str, List[Tuple[str, str]]]) -> Tuple[Tuple[str, str], List[str]]:
    # get the input pair with the highest similarity and get the solution where the input pair is used and the run time ratio is the highest
    # assume the list of input pairs has been sorted by similarity
    if len(solution_input_pairs) == 0:
        return None, []

    highest_similarity = 0
    highest_similarity_input_pair = None
    best_solution_ids = []
    best_perf_ratio = 0

    input_pair_solution_map = {}
    for solution_id in solution_input_pairs:
        for input_pair_id in solution_input_pairs[solution_id].keys():
            slow_input_id, fast_input_id = input_pair_id.split("@")
            input_pair_solution_map[(slow_input_id, fast_input_id)] = input_pair_solution_map.get((slow_input_id, fast_input_id), []) + [solution_id]
            similarity, perf_ratio = solution_input_pairs[solution_id][input_pair_id]
            if similarity > highest_similarity:
                highest_similarity = similarity
                highest_similarity_input_pair = (slow_input_id, fast_input_id)

    if highest_similarity_input_pair is None: # all similarities are 0
        solution_id = select_first_solution(list(solution_input_pairs.keys()))
        highest_similarity_input_pair = tuple(list(solution_input_pairs[solution_id].keys())[0].split("@"))

    best_solution_ids = sorted(input_pair_solution_map[highest_similarity_input_pair], key=lambda x: solution_input_pairs[x][f"{highest_similarity_input_pair[0]}@{highest_similarity_input_pair[1]}"][1], reverse=True) # sort by run time ratio

    return highest_similarity_input_pair, best_solution_ids

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
        line = lines[i].strip().lower()
        if "insert" in line and "condition" in line \
            and ("phase 3:" in line or line.startswith("3.")):
                break

    assert i < len(lines) - 1, "No constraints found"

    return "\n".join(lines[:i])
