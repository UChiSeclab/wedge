from pathlib import Path
import os
import sys
import subprocess
from typing import List, Tuple, Dict, Literal
import json
from tqdm import tqdm
from concurrent.futures import ProcessPoolExecutor, as_completed
import random
from fire import Fire

from config import config
from common import Language
from cgig.fuzz import fuzz_one
from cgig.cgig_utils import find_mutator_file, problem_has_extracted_constraint
from utils import filter_problems, get_cf_problems

random.seed(0)

def get_random_fuzz_driver_files(problem_id: str, driver_num: int) -> List[Path]:
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    cpp_solution_dir = problem_dir / "solutions" / "cpp"
    solution_files = sorted(list(cpp_solution_dir.glob("solutions_*.cpp")))
    # TODO: filter out the solutions that are not AC or TLE
    if len(solution_files) < driver_num:
        print(f"[Warning] {problem_id} has less than {driver_num} cpp solutions")
        return solution_files
    random.seed(0)

    return random.sample(solution_files, driver_num)

def main(
    mutator_mode: str = "self_reflect_feedback",
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi" "custom_mutator"] = "custom_mutator",
    problem_with_extracted_constraint_only: bool = False,
):
    problem_root_dir = Path(config["problem_root_dir"])
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    corpus_gen_dir = Path(config["corpus_gen_dir"])
    if mutator_type == "mutator_with_generator":
        mutator_gen_root_dir = Path(config["mutator_with_generator_dir"])
    elif mutator_type == "mutator_with_constraint":
        mutator_gen_root_dir = Path(config["mutator_with_constraint_dir"])
        assert problem_with_extracted_constraint_only, "Problem with extracted constraint only should be True for mutator_with_constraint"
    elif mutator_type == "mutator_with_constraint_multi":
        mutator_gen_root_dir = Path(config["mutator_with_constraint_multi_dir"])
        assert problem_with_extracted_constraint_only, "Problem with extracted constraint only should be True for mutator_with_constraint_multi"
    elif mutator_type == "custom_mutator":
        mutator_gen_root_dir = Path(config["custom_mutator_dir"])
    else:
        raise ValueError(f"Invalid mutator type: {mutator_type}")

    num_fuzz_drivers = 10

    tasks = []
    with ProcessPoolExecutor(max_workers = int(0.5 * os.cpu_count())) as executor:
        for problem in tqdm(filtered_problems):
            problem_id = problem["name"].split(".")[0]
            if problem_with_extracted_constraint_only and not problem_has_extracted_constraint(problem_id):
                continue
            ori_input_dir = problem_root_dir / problem_id / "input"
            corpus_dir = corpus_gen_dir / problem_id

            # Use the seed inputs from the ones in the custom mutator directory
            seed_input_dir = mutator_gen_root_dir / problem_id / "seed_inputs"

            mutator_gen_mode_dir = mutator_gen_root_dir / problem_id / mutator_mode
            if not (mutator_gen_mode_dir / "MUTATOR_CHECK_PASS").exists():
                print(f"[Warning] {mutator_gen_mode_dir} does not have a good mutator. Skipping...")
                continue
            custom_mutator_dir = (find_mutator_file(mutator_gen_mode_dir)).parent.absolute()
            fuzz_driver_files = get_random_fuzz_driver_files(problem_id, num_fuzz_drivers)

            for fuzz_driver_file in fuzz_driver_files:
                task = executor.submit(
                    fuzz_one,
                    corpus_dir,
                    fuzz_driver_file,
                    seed_input_dir,
                    3600,  # timeout=3600s
                    True, # use_custom_mutator=True,
                    mutator_type, # mutator_type=mutator_type,
                    custom_mutator_dir, # custom_mutator_dir=custom_mutator_dir
                )
                tasks.append((problem_id, fuzz_driver_file, task))

        for future in as_completed([task[2] for task in tasks]):
            problem_id, fuzz_driver_file, result = next((pid, fdf, fut) for pid, fdf, fut in tasks if fut == future)
            try:
                err = future.result().stderr.decode()
                if err:
                    print("===Error===")
                    print(err)
            except Exception as e:
                print(f"[Exception] An unexpected error occurred for {problem_id} - {fuzz_driver_file}: {e}")

if __name__ == "__main__":
    Fire(main)
