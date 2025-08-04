from pathlib import Path
import os
from typing import Literal
from tqdm import tqdm
from concurrent.futures import ProcessPoolExecutor, as_completed
from fire import Fire

from config import config
from common import Language
from cgig.fuzz import fuzz_one
from selector.select_solution import select_solutions
from cgig.cgig_utils import find_mutator_file, problem_has_extracted_constraint, get_solution_and_input_pair_list_with_constraint
from utils import filter_problems, get_cf_problems

def get_fuzz_driver_file(problem_id: str, solution_id: str, fuzz_driver_mode: str) -> Path:
    # adhoc hack for legacy problem
    if fuzz_driver_mode == "instrument_fuzz":
        return Path(config["mutator_with_constraint_per_solution_dir"]) / "instrument_fuzz" / problem_id / solution_id / "fuzz_driver" / f"{solution_id}.cpp"
    elif fuzz_driver_mode == "raw_fuzz":
        return Path(config["problem_root_dir"]) / problem_id / "solutions" / "cpp" / f"{solution_id}.cpp"
    raise ValueError(f"Invalid fuzz driver mode: {fuzz_driver_mode}")

def main(
    fuzz_driver_mode: Literal["raw_fuzz", "instrument_fuzz"] = "raw_fuzz",
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution", "custom_mutator", "default_mutator"] = "custom_mutator",
    run_perffuzz: bool = False,
    perffuzz_no_guidance: bool = False,
    problem_with_extracted_constraint_only: bool = False,
    mutator_mode: str = "self_reflect_feedback",
    top_k_constraints: int = 1,
    num_fuzz_drivers: int = 1,
    mutator_per_solution: bool = True, # per solution or per problem
):
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
        # assert fuzz_driver_mode == "raw_fuzz", "run_perffuzz only supports raw_fuzz"
        assert mutator_type == "default_mutator", "run_perffuzz only supports default_mutator"
    if mutator_type == "mutator_with_generator":
        mutator_gen_root_dir = Path(config["mutator_with_generator_dir"])
    elif mutator_type == "mutator_with_constraint":
        mutator_gen_root_dir = Path(config["mutator_with_constraint_dir"])
        assert problem_with_extracted_constraint_only, "mutator_with_constraint requires problem_with_extracted_constraint_only"
    elif mutator_type == "mutator_with_constraint_multi":
        mutator_gen_root_dir = Path(config["mutator_with_constraint_multi_dir"])
        assert problem_with_extracted_constraint_only, "mutator_with_constraint_multi requires problem_with_extracted_constraint_only"
    elif mutator_type == "mutator_with_constraint_per_solution":
        mutator_gen_root_dir = Path(config["mutator_with_constraint_per_solution_dir"])
        assert problem_with_extracted_constraint_only, "mutator_with_constraint_per_solution requires problem_with_extracted_constraint_only"
        assert top_k_constraints == num_fuzz_drivers, "num_fuzz_drivers should be the same with top_k_constraints for mutator_with_constraint"
    elif mutator_type == "custom_mutator":
        mutator_gen_root_dir = Path(config["custom_mutators_dir"])
    elif mutator_type == "default_mutator":
        mutator_gen_root_dir = None
    else:
        raise ValueError(f"Invalid mutator_type: {mutator_type}")

    if mutator_type == "mutator_with_constraint_per_solution":
        mutator_gen_root_dir = mutator_gen_root_dir / "instrument_fuzz"
    elif mutator_type == "custom_mutator":
        mutator_gen_root_dir = mutator_gen_root_dir / "raw_fuzz"
    if run_perffuzz:
        if perffuzz_no_guidance:
            corpus_gen_dir = corpus_gen_dir / f"{mutator_type}_perffuzz_no_guidance"
        else:
            corpus_gen_dir = corpus_gen_dir / f"{mutator_type}_perffuzz"
    else:
        corpus_gen_dir = corpus_gen_dir / mutator_type

    tasks = []
    with ProcessPoolExecutor(max_workers = int(0.5 * os.cpu_count())) as executor:
        for problem in tqdm(filtered_problems):
            problem_id = problem["name"].split(".")[0]
            if problem_with_extracted_constraint_only and not problem_has_extracted_constraint(problem_id):
                continue

            corpus_dir = corpus_gen_dir / problem_id

            if mutator_per_solution:
                solution_and_input_pair_list = get_solution_and_input_pair_list_with_constraint(problem_id, top_k_constraints)
                for solution_id, input_pair_id in solution_and_input_pair_list:
                    if mutator_type != "default_mutator":
                        mutator_dir = mutator_gen_root_dir / problem_id / solution_id
                        mutator_gen_mode_dir = mutator_dir / mutator_mode
                        seed_input_dir = mutator_dir / "seed_inputs"
                        if not (mutator_gen_mode_dir / "MUTATOR_CHECK_PASS").exists():
                            print(f"[Warning] {mutator_gen_mode_dir} does not have a good mutator. Skipping...")
                            continue
                        custom_mutator_dir = (find_mutator_file(mutator_gen_mode_dir)).parent.absolute()
                    else:
                        custom_mutator_dir = None
                        # adhoc hack
                        seed_input_dir = Path(config["custom_mutators_dir"]) / "raw_fuzz" / problem_id / solution_id / "seed_inputs"
                    fuzz_driver_file = get_fuzz_driver_file(problem_id, solution_id, fuzz_driver_mode)
                    program_dir = corpus_dir / solution_id

                    if not seed_input_dir.exists():
                        raise FileNotFoundError(f"{seed_input_dir} does not exist. Please generate seed inputs first.")
                    task = executor.submit(
                        fuzz_one,
                        program_dir,
                        fuzz_driver_file,
                        seed_input_dir,
                        3600,  # timeout=3600s
                        (mutator_type != "default_mutator"), # use_custom_mutator=True,
                        mutator_type, # mutator_type=mutator_type,
                        run_perffuzz, # run_perffuzz=run_perffuzz
                        perffuzz_no_guidance, # perffuzz_no_guidance=perffuzz_no_guidance
                        custom_mutator_dir, # custom_mutator_dir=custom_mutator_dir
                    )
                    tasks.append((problem_id, fuzz_driver_file, task))
            else:
                # legacy code
                raise NotImplementedError("Not implemented for mutator_per_solution=False")
                mutator_dir = mutator_gen_root_dir / problem_id
                mutator_gen_mode_dir = mutator_dir / mutator_mode
                seed_input_dir = mutator_dir / "seed_inputs"
                if not (mutator_gen_mode_dir / "MUTATOR_CHECK_PASS").exists():
                    print(f"[Warning] {mutator_gen_mode_dir} does not have a good mutator. Skipping...")
                    continue
                # TODO: we might need to use the same solutions as above
                fast_solution_ids, _ = select_solutions(problem_id, problem, "multi_fast", Language.CPP, top_k=num_fuzz_drivers)
                for solution_id in fast_solution_ids:
                    fuzz_driver_file = mutator_dir / "fuzz_driver" / f"{solution_id}.cpp"
                    program_dir = corpus_dir / solution_id
                    custom_mutator_dir = find_mutator_file(mutator_gen_mode_dir)

                    task = executor.submit(
                        fuzz_one,
                        program_dir,
                        fuzz_driver_file,
                        seed_input_dir,
                        3600,  # timeout=3600s
                        True, # use_custom_mutator=True,
                        mutator_type, # mutator_type=mutator_type,
                        run_perffuzz, # run_perffuzz=run_perffuzz
                        perffuzz_no_guidance, # perffuzz_no_guidance=perffuzz_no_guidance
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
