from pathlib import Path
import os, sys
import shutil
import subprocess
from multiprocessing import Pool, Manager
from typing import Dict, Literal
from fire import Fire

from config import config
from utils import filter_problems, get_cf_problems, record_failing_problem, problem_to_id
from input_validator_run import find_validator_files
from cgig.cgig_utils import problem_has_extracted_constraint, get_solution_and_input_pair_list_with_constraint


def process_problem(problem_id: str, corpus_gen_dir: str, validator_mode:str, fuzz_driver_mode:str, strategy: str, num_fuzz_drivers: int, results: Dict[str, int], mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution", "custom_mutator"] = "custom_mutator", run_perffuzz: bool = False, mutator_per_solution: bool = False):
    print(f"Processing problem: {problem_id}")
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    corpus_dir = corpus_gen_dir / problem_id
    if not corpus_dir.exists():
        print(f"Skipping {problem_id} as corpus directory does not exist")
        return

    validator_dir = problem_dir / "validator_gen" / validator_mode
    if not (validator_dir / "VAL_GT_INPUT_PASS").exists():
        print(f"[Warning] {validator_dir} does not have a good validator. Skipping...")
        record_failing_problem(problem_id, f"{strategy}_{fuzz_driver_mode}_{mutator_type}", "No available validator")
        return
    validator_file, _ = find_validator_files(validator_dir)

    valid_inputs = 0
    invalid_inputs = 0
    if not run_perffuzz:
        strategy_input_dir = problem_dir / f"{strategy}_{fuzz_driver_mode}_{mutator_type}" / "input"
    else:
        strategy_input_dir = problem_dir / f"{strategy}_{fuzz_driver_mode}_{mutator_type}_perffuzz" / "input"
    print(f"strategy input directory: {strategy_input_dir}")
    strategy_input_dir.mkdir(parents=True, exist_ok=True)
    assert validator_file.exists(), f"Validator file {validator_file} does not exist"
    if mutator_per_solution:
        solution_and_input_pair_list = get_solution_and_input_pair_list_with_constraint(problem_id, top_k=num_fuzz_drivers)
        for solution_id, input_pair_id in solution_and_input_pair_list:
            program_dir = corpus_dir / solution_id
            if not run_perffuzz:
                queue_dir = program_dir / f"{solution_id}_{mutator_type}_output" / "default" / "queue"
            else:
                queue_dir = program_dir / f"{solution_id}_{mutator_type}_output" / "queue"
            slow_input_id, fast_input_id = input_pair_id
            if not queue_dir.exists():
                print(f"[Warning] queue directory {queue_dir} does not exist. Skipping...")
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
                target_input_file = strategy_input_dir / f"{solution_id}_{slow_input_id}_{fast_input_id}_{input_file.name}.in"
                shutil.copy(input_file, target_input_file)
                valid_inputs += 1
    else:
        raise NotImplementedError("Not implemented for mutator_per_solution=False")

    print(f"problem: {problem_id}, valid inputs: {valid_inputs}, invalid inputs: {invalid_inputs}")
    results["valid"] += valid_inputs
    results["invalid"] += invalid_inputs


def main(
    fuzz_driver_mode: Literal["raw_fuzz", "instrument_fuzz"] = "raw_fuzz",
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution", "custom_mutator", "default_mutator"] = "custom_mutator",
    run_perffuzz: bool = False,
    validator_mode: str = "self_reflect_feedback",
    problem_with_extracted_constraint_only: bool = False,
    num_fuzz_drivers: int = 5,
    mutator_per_solution: bool = False,
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

    if run_perffuzz:
        assert fuzz_driver_mode == "raw_fuzz", "run_perffuzz only supports raw_fuzz"
        assert mutator_type == "default_mutator", "run_perffuzz only supports default_mutator"
        corpus_gen_dir = corpus_gen_dir / f"{mutator_type}_perffuzz"
    else:
        corpus_gen_dir = corpus_gen_dir / mutator_type

    filtered_problem_ids = [problem_to_id(problem) for problem in filtered_problems]
    if mutator_type in ["mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution"]:
        assert problem_with_extracted_constraint_only, "Problem with extracted constraint only should be True for mutator_with_constraint*"
    if problem_with_extracted_constraint_only:
        filtered_problem_ids = [
            problem_id for problem_id in filtered_problem_ids
                if problem_has_extracted_constraint(problem_id)
        ]

    with Manager() as manager:
        results = manager.dict(valid=0, invalid=0)

        with Pool(processes = int(0.5 * os.cpu_count())) as pool:
            tasks = [
                (problem_id, corpus_gen_dir, validator_mode, fuzz_driver_mode, strategy, num_fuzz_drivers, results, mutator_type, run_perffuzz, mutator_per_solution)
                for problem_id in filtered_problem_ids
            ]
            pool.starmap(process_problem, tasks)

        print(f"Total valid inputs: {results['valid']}, Total invalid inputs: {results['invalid']}")


if __name__ == "__main__":
    Fire(main)
