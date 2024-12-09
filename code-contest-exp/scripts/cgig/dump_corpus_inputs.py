from pathlib import Path
import os, sys
import shutil
import subprocess
from multiprocessing import Pool, Manager
from typing import Dict, Literal
from fire import Fire

from config import config
from utils import filter_problems, get_cf_problems, record_failing_problem
from input_validator_run import find_validator_files
from cgig.corpus_gen import get_random_fuzz_driver_files
from cgig.cgig_utils import problem_has_extracted_constraint


def process_problem(problem_id: str, strategy: str, num_fuzz_drivers: int, results: Dict[str, int], mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "custom_mutator"] = "custom_mutator"):
    print(f"Processing problem: {problem_id}")
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    corpus_dir = Path(config["corpus_gen_dir"]) / problem_id
    if not corpus_dir.exists():
        print(f"Skipping {problem_id} as corpus directory does not exist")
        return

    validator_dir = problem_dir / "validator_gen" / "self_reflect_feedback"
    if not (validator_dir / "VAL_GT_INPUT_PASS").exists():
        print(f"[Warning] {validator_dir} does not have a good validator. Skipping...")
        record_failing_problem(problem_id, strategy, "No available validator")
        return
    validator_file, _ = find_validator_files(validator_dir)

    valid_inputs = 0
    invalid_inputs = 0
    if mutator_type == "custom_mutator":
        strategy_input_dir = problem_dir / strategy / "input"
    else:
        strategy_input_dir = problem_dir / f"{strategy}_{mutator_type}" / "input"
    print(f"strategy input directory: {strategy_input_dir}")
    strategy_input_dir.mkdir(parents=True, exist_ok=True)
    assert validator_file.exists(), f"Validator file {validator_file} does not exist"
    fuzz_driver_files = get_random_fuzz_driver_files(problem_id, num_fuzz_drivers)

    for fuzz_driver_file in fuzz_driver_files:
        if mutator_type != "custom_mutator":
            queue_dir = corpus_dir / f"{fuzz_driver_file.stem}_{mutator_type}_output" / "default" / "queue"
        else:
            queue_dir = corpus_dir / f"{fuzz_driver_file.stem}_output" / "default" / "queue"
        if not queue_dir.exists():
            print(f"[Warning] queue directory of {fuzz_driver_file} does not exist. Skipping...")
            continue

        for input_file in queue_dir.glob("id:*"):
            try:
                subprocess.run(
                    ["python", validator_file, input_file],
                    check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE
                )
            except subprocess.CalledProcessError:
                print(f"Validator failed for {input_file}")
                invalid_inputs += 1
                continue
            shutil.copy(input_file, strategy_input_dir / f"{fuzz_driver_file.stem}_{input_file.name}.in")
            valid_inputs += 1

    print(f"problem: {problem_id}, valid inputs: {valid_inputs}, invalid inputs: {invalid_inputs}")
    results["valid"] += valid_inputs
    results["invalid"] += invalid_inputs


def main(
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "custom_mutator"] = "custom_mutator",
    problem_with_extracted_constraint_only: bool = False,
):
    strategy = "corpus"
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    filtered_problem_ids = [problem["name"].split(".")[0] for problem in filtered_problems]
    if mutator_type == "mutator_with_constraint":
        assert problem_with_extracted_constraint_only, "Problem with extracted constraint only should be True for mutator_with_constraint"
    if problem_with_extracted_constraint_only:
        filtered_problem_ids = [
            problem_id for problem_id in filtered_problem_ids
                if problem_has_extracted_constraint(problem_id)
        ]
    num_fuzz_drivers = 10

    with Manager() as manager:
        results = manager.dict(valid=0, invalid=0)

        with Pool(processes = int(0.5 * os.cpu_count())) as pool:
            tasks = [
                (problem_id, strategy, num_fuzz_drivers, results, mutator_type)
                for problem_id in filtered_problem_ids
            ]
            pool.starmap(process_problem, tasks)

        print(f"Total valid inputs: {results['valid']}, Total invalid inputs: {results['invalid']}")


if __name__ == "__main__":
    Fire(main)
