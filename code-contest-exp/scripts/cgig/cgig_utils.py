from pathlib import Path
from typing import List, Tuple, Dict

from config import config

def find_mutator_file(mutator_dir: Path) -> Path:
    mutator_try_dirs = list(mutator_dir.glob("try_*"))
    assert len(mutator_try_dirs) > 0, f"No try dirs found in {mutator_dir}"
    mutator_last_try = sorted(
        mutator_try_dirs, key=lambda x: int(x.name.split("_")[-1])
    )[-1]

    return mutator_last_try / "mutator.py"

def select_a_solution(solution_ids: List[str]) -> str:
    # ensure deterministic selection
    solution_ids = sorted(solution_ids)
    return solution_ids[0]

def get_best_input_pair(solution_input_pairs: Dict[str, Tuple[str, str]]) -> Tuple[Tuple[str, str], List[str]]:
    # rank the input pairs by the frequency in the solution_input_pairs
    if len(solution_input_pairs) == 0:
        return None, []

    input_pair_freq = {}
    for solution_id in solution_input_pairs:
        for slow_input_id, fast_input_id in solution_input_pairs[solution_id]:
            input_pair_freq[(slow_input_id, fast_input_id)] = input_pair_freq.get((slow_input_id, fast_input_id), []) + [solution_id]

    best_input_pair = max(input_pair_freq, key=lambda x: len(input_pair_freq[x]))

    return best_input_pair, input_pair_freq[best_input_pair]

def problem_has_extracted_constraint(problem_id: str) -> bool:
    # check if the problem has extracted constraints. this function is adhoc
    return (Path(config["constraints_dir"]) / problem_id).exists()