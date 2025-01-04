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
from cgig.corpus_gen import get_random_fuzz_driver_files, get_instrumented_fuzz_driver_files
from cgig.cgig_utils import problem_has_extracted_constraint


def process_problem(problem_id: str, corpus_gen_dir: str, validator_mode:str, fuzz_driver_mode:str, strategy: str, num_fuzz_drivers: int, results: Dict[str, int], mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "custom_mutator"] = "custom_mutator"):
    print(f"Processing problem: {problem_id}")
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    corpus_dir = corpus_gen_dir / problem_id
    if not corpus_dir.exists():
        print(f"Skipping {problem_id} as corpus directory does not exist")
        return

    validator_dir = problem_dir / "validator_gen" / validator_mode
    if not (validator_dir / "VAL_GT_INPUT_PASS").exists():
        print(f"[Warning] {validator_dir} does not have a good validator. Skipping...")
        record_failing_problem(problem_id, strategy, "No available validator")
        return
    validator_file, _ = find_validator_files(validator_dir)

    valid_inputs = 0
    invalid_inputs = 0
    strategy_input_dir = problem_dir / f"{strategy}_{fuzz_driver_mode}_{mutator_type}" / "input"
    print(f"strategy input directory: {strategy_input_dir}")
    strategy_input_dir.mkdir(parents=True, exist_ok=True)
    assert validator_file.exists(), f"Validator file {validator_file} does not exist"
    if fuzz_driver_mode == "raw_fuzz":
        fuzz_driver_files = get_random_fuzz_driver_files(problem_id, num_fuzz_drivers)
    elif fuzz_driver_mode == "instrument_fuzz": # only work for problems that have extracted constraints
        fuzz_driver_files = get_instrumented_fuzz_driver_files(problem_id, num_fuzz_drivers)

    for fuzz_driver_file in fuzz_driver_files:
        if fuzz_driver_mode == "raw_fuzz":
            program_dir = corpus_dir
        elif fuzz_driver_mode == "instrument_fuzz":
            program_dir = corpus_dir / fuzz_driver_file.parent.name
        queue_dir = program_dir / f"{fuzz_driver_file.stem}_{mutator_type}_output" / "default" / "queue"
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
            if fuzz_driver_mode == "raw_fuzz":
                target_input_file = strategy_input_dir / f"{fuzz_driver_file.stem}_{input_file.name}.in"
            elif fuzz_driver_mode == "instrument_fuzz":
                target_input_file = strategy_input_dir / f"{fuzz_driver_file.parent.parent.name}_{fuzz_driver_file.parent.name}_{fuzz_driver_file.stem}_{input_file.name}.in"
            shutil.copy(input_file, target_input_file)
            valid_inputs += 1

    print(f"problem: {problem_id}, valid inputs: {valid_inputs}, invalid inputs: {invalid_inputs}")
    results["valid"] += valid_inputs
    results["invalid"] += invalid_inputs


def main(
    fuzz_driver_mode: Literal["raw_fuzz", "instrument_fuzz"] = "raw_fuzz",
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "custom_mutator"] = "custom_mutator",
    validator_mode: str = "self_reflect_feedback",
    problem_with_extracted_constraint_only: bool = False,
    num_fuzz_drivers: int = 10,
):
    strategy = "corpus"
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    if fuzz_driver_mode == "instrument_fuzz":
        corpus_gen_dir = Path(config["corpus_instrument_gen_dir"])
    elif fuzz_driver_mode == "raw_fuzz":
        corpus_gen_dir = Path(config["corpus_raw_gen_dir"])
    else:
        raise ValueError(f"Invalid fuzz driver mode: {fuzz_driver_mode}")

    filtered_problem_ids = [problem["name"].split(".")[0] for problem in filtered_problems]
    if mutator_type in ["mutator_with_constraint", "mutator_with_constraint_multi"]:
        assert problem_with_extracted_constraint_only, "Problem with extracted constraint only should be True for mutator_with_constraint and mutator_with_constraint_multi"
    if problem_with_extracted_constraint_only:
        filtered_problem_ids = [
            problem_id for problem_id in filtered_problem_ids
                if problem_has_extracted_constraint(problem_id)
        ]

    with Manager() as manager:
        results = manager.dict(valid=0, invalid=0)

        with Pool(processes = int(0.5 * os.cpu_count())) as pool:
            tasks = [
                (problem_id, corpus_gen_dir, validator_mode, fuzz_driver_mode, strategy, num_fuzz_drivers, results, mutator_type)
                for problem_id in filtered_problem_ids
            ]
            pool.starmap(process_problem, tasks)

        print(f"Total valid inputs: {results['valid']}, Total invalid inputs: {results['invalid']}")


if __name__ == "__main__":
    Fire(main)
