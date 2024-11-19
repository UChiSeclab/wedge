from pathlib import Path
import os, sys
import shutil
import subprocess
from multiprocessing import Pool, Manager

from config import config
from utils import filter_problems, get_cf_problems, record_failing_problem
from input_validator_run import find_validator_files
from cgig.corpus_gen import get_random_fuzz_driver_files


def process_problem(problem, strategy, problem_root_dir, corpus_gen_dir, custom_mutators_root_dir, num_fuzz_drivers, results):
    problem_id = problem["name"].split(".")[0]
    problem_dir = problem_root_dir / problem_id
    corpus_dir = corpus_gen_dir / problem_id
    if not corpus_dir.exists():
        print(f"Skipping {problem_id} as corpus directory does not exist")
        return

    validator_dir = problem_dir / "validator_gen" / "self_reflect_feedback"
    if not (validator_dir / "VAL_GT_INPUT_PASS").exists():
        print(f"[Warning] {validator_dir} does not have a good validator. Skipping...")
        record_failing_problem(problem_id, "corpus", "No available validator")
        return
    validator_file, _ = find_validator_files(validator_dir)

    valid_inputs = 0
    invalid_inputs = 0
    strategy_input_dir = problem_dir / strategy / "input"
    strategy_input_dir.mkdir(parents=True, exist_ok=True)
    assert validator_file.exists(), f"Validator file {validator_file} does not exist"
    fuzz_work_dir = corpus_dir / "fuzz"
    fuzz_driver_files = get_random_fuzz_driver_files(problem_id, num_fuzz_drivers)

    for fuzz_driver_file in fuzz_driver_files:
        queue_dir = fuzz_work_dir / f"{fuzz_driver_file.stem}_output" / "default" / "queue"
        if not queue_dir.exists():
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


def main():
    strategy = "corpus"
    problem_root_dir = Path(config["problem_root_dir"])
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    corpus_gen_dir = Path(config["corpus_gen_dir"])
    custom_mutators_root_dir = Path(config["custom_mutators_dir"])
    num_fuzz_drivers = 10

    with Manager() as manager:
        results = manager.dict()
        results["valid"] = 0
        results["invalid"] = 0

        with Pool(processes = int(0.5 * os.cpu_count())) as pool:  # Adjust the number of processes based on your system
            tasks = [
                (problem, strategy, problem_root_dir, corpus_gen_dir, custom_mutators_root_dir, num_fuzz_drivers, results)
                for problem in filtered_problems
            ]
            pool.starmap(process_problem, tasks)

        print(f"Total valid inputs: {results['valid']}, Total invalid inputs: {results['invalid']}")


if __name__ == "__main__":
    main()
