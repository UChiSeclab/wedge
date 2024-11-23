from pathlib import Path
import os, sys
import shutil
import subprocess
from multiprocessing import Pool, Manager
import json
from typing import Dict
from fire import Fire

from config import config
from input_validator_run import find_validator_files
from cgig.corpus_gen import get_random_fuzz_driver_files
from utils import record_failing_problem


def process_queue(problem_id: str, strategy: str, queue_dir: Path, results: Dict[str, int]):
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    input_pair_id = queue_dir.absolute().as_posix().split("/")[-4]
    solution_id = queue_dir.absolute().as_posix().split("/")[-5]

    validator_dir = problem_dir / "validator_gen" / "self_reflect_feedback"
    if not (validator_dir / "VAL_GT_INPUT_PASS").exists():
        print(f"[Warning] {validator_dir} does not have a good validator. Skipping...")
        record_failing_problem(problem_id, strategy, "No available validator")
        return
    validator_file, _ = find_validator_files(validator_dir)

    valid_inputs = 0
    invalid_inputs = 0
    strategy_input_dir = problem_dir / strategy / "input"
    strategy_input_dir.mkdir(parents=True, exist_ok=True)
    assert validator_file.exists(), f"Validator file {validator_file} does not exist"

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
        shutil.copy(input_file, strategy_input_dir / "_".join([input_pair_id, solution_id, input_file.name]))
        valid_inputs += 1

    print(f"queue: {queue_dir}, valid inputs: {valid_inputs}, invalid inputs: {invalid_inputs}")
    results["valid"] += valid_inputs
    results["invalid"] += invalid_inputs


def main(
    strategy = "instrument_fuzz"
):
    input_pairs_dir = Path(config["input_pairs_dir"])
    extracted_constraints_dir = Path(config["constraints_dir"])
    input_pairs_file = input_pairs_dir / "content_similar_problem_solution_input_pairs.json"
    problem_solution_input_pairs = json.loads(input_pairs_file.read_text())

    instrument_fuzz_dir = Path(config["instrument_fuzz_dir"])
    raw_fuzz_dir = Path(config["raw_fuzz_dir"])
    custom_mutators_root_dir = Path(config["custom_mutators_dir"])

    with Manager() as manager:
        results = manager.dict(valid=0, invalid=0)
        with Pool(processes = int(0.5 * os.cpu_count())) as pool:
            tasks = []
            for problem_id in problem_solution_input_pairs:
                for solution_id in problem_solution_input_pairs[problem_id]:
                    for slow_input_id, fast_input_id in problem_solution_input_pairs[problem_id][solution_id]:
                        if strategy == "instrument_fuzz":
                            queue_dir = instrument_fuzz_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}" / "transformed_program_output" / "default" / "queue"
                        elif strategy == "raw_fuzz":
                            queue_dir = raw_fuzz_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}" / f"{solution_id}_output" / "default" / "queue"
                        else:
                            raise ValueError(f"Invalid strategy: {strategy}")

                        # check queue directory
                        if not queue_dir.exists():
                            continue
                        if len(list(queue_dir.glob("id:*"))) < 10:
                            print(f"[Warning] Not enough inputs in the queue directory {queue_dir}")
                            continue
                        
                        tasks.append((problem_id, strategy, queue_dir, results))

            pool.starmap(process_queue, tasks)

        print(f"Total valid inputs: {results['valid']}, Total invalid inputs: {results['invalid']}")

if __name__ == "__main__":
    Fire(main)
