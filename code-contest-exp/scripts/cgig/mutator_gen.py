from pathlib import Path
import shutil
from typing import Dict, List, Literal, Tuple, Optional
from tqdm import tqdm
from concurrent.futures import ProcessPoolExecutor, as_completed
import datetime
import random
import os
from fire import Fire
import subprocess

from config import config
from cgig.cgig_utils import problem_has_extracted_constraint, parse_constraints_content, get_problem_solution_input_pairs, parse_constraints_content_from_response, get_solution_and_input_pair_list_with_constraint
from gpt_caller import request_conversation, cut_string
from cgig.fuzz import fuzz_one
from selector.select_solution import select_solutions
from utils import filter_problems, get_cf_problems, problem_to_id
from common import Language
from cgig.constraint_gen import get_product_cov
from cgig.classify_input import run_classifier

"""
supported settings:
- n constraints => n mutators on n instrumented solutions (main approach) (mutator_with_constraint_per_solution + instrument_fuzz)
- n constraints => n mutators on n raw solutions (mutator_with_constraint_per_solution + raw_fuzz)
- a problem-specific mutator on n instrumented solutions (custom_mutator + instrument_fuzz)
- a problem-specific mutator on n raw solutions (custom_mutator + raw_fuzz)
- AFL++ default mutator on n raw solutions
"""

random.seed(0)

REFELECT_MSG = '''
The mutator script you provided is incorrect as the fuzzer ran into an error in the fuzzing process. Please reflect and try again.
'''

# TODO: we might need to abandon this function
def write_raw_fuzz_dirvers(
    fuzz_driver_dir: Path,
    problem_id: str,
    problem: Dict,
    solution_driver_selection_type: str = "multi_fast",
    num_fuzz_drivers: int = 5
) -> List[Path]:
    fuzz_driver_dir.mkdir(parents=True, exist_ok=True)
    fast_solution_ids, fast_solutions = select_solutions(problem_id, problem, solution_driver_selection_type, Language.CPP, top_k=num_fuzz_drivers)
    # dump fast solutions
    fuzz_driver_files = []
    for i, solution_id in enumerate(fast_solution_ids):
        fuzz_driver_file = fuzz_driver_dir / f"{solution_id}.cpp"
        with open(fuzz_driver_file, "w") as f:
            f.write(fast_solutions[i])
        fuzz_driver_files.append(fuzz_driver_file)

    return fuzz_driver_files

def select_seed_inputs(ori_input_dir: Path, seed_input_dir: Path, fuzz_driver_mode: str, instrumented_program_file: Path = None, num_seeds: int = 5) -> List[Path]:
    # currently we use the same input files as seed inputs and reference inputs in the prompt
    input_files = list(ori_input_dir.glob("*.in"))
    if fuzz_driver_mode == "raw_fuzz":
        # exclude generated tests
        input_files = [input_file for input_file in input_files if "generated" not in input_file.stem]
    elif fuzz_driver_mode == "instrument_fuzz":
        assert instrumented_program_file is not None, "instrumented_program_file is required for instrument_fuzz"
        res_dict = run_classifier(input_files, instrumented_program_file)
        if res_dict == {"error": "Compile Error"}:
            raise ValueError(f"Compile Error for {instrumented_program_file}")
        # filter out the inputs that are not pass
        input_files = [input_file for input_file in input_files if res_dict[input_file.name]["status"] == "Pass"]
        if len(input_files) == 0:
            print(f"[Warning] No pass inputs found for {instrumented_program_file}")
            raise ValueError(f"No pass inputs found for {instrumented_program_file}")

    input_files = sorted(input_files, key=lambda x: x.name)

    # dump input files
    seed_input_dir.mkdir(parents=True, exist_ok=True)
    random.seed(0)
    selected_seed_inputs = random.sample(input_files, min(num_seeds, len(input_files)))

    if len(os.listdir(seed_input_dir)) == 0:
        for input_file in selected_seed_inputs:
            shutil.copy(input_file, seed_input_dir / input_file.name)

    assert len(os.listdir(seed_input_dir)) == len(selected_seed_inputs), f"Failed to copy seed inputs to {seed_input_dir}"

    return selected_seed_inputs

def compile_mutator_gen_prompt(
    prompt_template_file: Path,
    problem_statement_file: Path,
    mutator_example_file: Path,
    reference_input_file_list: List[Path], # currently we use the selected seed inputs
    generator_file: Optional[Path] = None,
    product_cov_file: Optional[Path] = None,
    constraints_content: Optional[str] = None,
):
    prompt_template = prompt_template_file.read_text()
    problem_statement = problem_statement_file.read_text()
    mutator_example = mutator_example_file.read_text()
    input_generator_code = generator_file.read_text() if generator_file else ""
    product_cov_content = product_cov_file.read_text() if product_cov_file else ""
    reference_input_list = [input_file.read_text() for input_file in reference_input_file_list]
    reference_input_list_str = ""
    for i in range(len(reference_input_list)):
        reference_input_list_str += f"Example input {i+1}:\n{reference_input_list[i]}\n"

    prompt = prompt_template.format(
        problem_statement=problem_statement,
        mutator_example=mutator_example,
        reference_inputs=reference_input_list_str,
        input_generator_code=input_generator_code,
        product_cov_content=product_cov_content,
        constraints_content=constraints_content,
    )

    return prompt

def prompt_and_dump_results(
    mutator_dir: Path,
    seed_input_dir: Path,
    initial_prompt: str,
    fuzz_driver_file: Path,
    problem_id: str,
    try_cnt: int,
    msg_list: List[Dict],
    mode: str = None,
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution", "custom_mutator"] = "custom_mutator",
    feedback_msg: Optional[str] = None,
) -> subprocess.CompletedProcess:
    mutator_gen_try_cnt_dir = mutator_dir / mode / f"try_{try_cnt}"
    mutator_gen_try_cnt_dir.mkdir(parents=True, exist_ok=True)
    fuzz_driver_dir = mutator_dir / "fuzz_driver"
    ori_msg_list = msg_list[:]
    try:
        if mode in ["direct", "resample"]:
            # keep the msg_list
            msg_list.append({"role": "user", "content": initial_prompt})
        elif mode in ["self_reflect", "self_reflect_feedback"]:
            if try_cnt == 0:
                msg_list.append({"role": "user", "content": initial_prompt})
            else:
                msg_list.append(
                    {
                        "role": "user",
                        "content": REFELECT_MSG
                            if mode == "self_reflect" else REFELECT_MSG + feedback_msg
                    }
                )
        gpt_response = request_conversation(
            msg_list,
            max_retry=5,
            temperature=0.8,
        ).choices[0].message.content
        mutator_script_content = cut_string(gpt_response)

    except Exception as e:
        print(f"Failed to generate mutator script for {problem_id} in try {try_cnt}: {e}")
        return False, []

    msg_list.append({"role": "assistant", "content": gpt_response})

    with open(mutator_gen_try_cnt_dir / "gpt_response.txt", "w") as f:
        f.write(gpt_response)

    with open(mutator_gen_try_cnt_dir / "mutator.py", "w") as f:
        f.write(mutator_script_content)

    with open(mutator_gen_try_cnt_dir / "conversation.txt", "w") as f:
        # write formatted conversation
        for msg in msg_list:
            f.write(f"{msg['role']}: {msg['content']}\n")

    # launch the fuzzer with the generated mutator script and run for 1min

    if mode in ["direct", "resample"]:
        # reset the msg_list for the next try for resample
        msg_list = ori_msg_list

    return fuzz_one(
        fuzz_driver_dir,
        fuzz_driver_file,
        seed_input_dir,
        timeout=180,
        use_custom_mutator=True,
        mutator_type=mutator_type,
        custom_mutator_dir=mutator_gen_try_cnt_dir,
    )

def generate_mutator(mutator_dir: Path, seed_input_dir: Path, problem_id: str, initial_prompt: str, mode: str, mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution", "custom_mutator", ""], fuzz_driver_file: Path, max_try: int = 5) -> subprocess.CompletedProcess:
    mutator_dir.mkdir(parents=True, exist_ok=True)

    conversation = [{"role": "system", "content": "You are a helpful assistant good at coding."}]

    if mode == "direct":
        result = prompt_and_dump_results(
            mutator_dir,
            seed_input_dir,
            initial_prompt,
            fuzz_driver_file,
            problem_id,
            0,
            msg_list=conversation,
            mode=mode,
            mutator_type=mutator_type,
        )
        if result.returncode != 0:
            print(f"[Warning] [{mode}] failed to generate mutator script for {problem_id} in try 0")
            return result
    elif mode in ["resample", "self_reflect"]:
        for i in range(max_try):
            result = prompt_and_dump_results(
                mutator_dir,
                seed_input_dir,
                initial_prompt,
                fuzz_driver_file,
                problem_id,
                i,
                msg_list=conversation,
                mode=mode,
                mutator_type=mutator_type,
            )
            if result.returncode == 0:
                break
            else:
                if i == max_try - 1:
                    print(f"[Warning] [{mode}] failed to generate mutator script for {problem_id} in try {i}")
                    return result
    elif mode == "self_reflect_feedback":
        feedback_msg = None
        error_msg = None
        for i in range(max_try):
            if i > 0:
                feedback_msg = "The fuzzer with the mutator failed with the following error message:\n" + error_msg
            result = prompt_and_dump_results(
                mutator_dir,
                seed_input_dir,
                initial_prompt,
                fuzz_driver_file,
                problem_id,
                i,
                msg_list=conversation,
                mode=mode,
                mutator_type=mutator_type,
                feedback_msg=feedback_msg,
            )
            if result.returncode == 0:
                break
            else:
                error_msg = result.stderr.decode()
                if i == max_try - 1:
                    print(f"[Warning] [{mode}] failed to generate mutator script for {problem_id} in try {i}")
                    print(f"error message: {error_msg}")
                    return result

    return result

def copy_and_remove_abort(instrumented_program_file: Path, fuzz_driver_file: Path):
    shutil.copy(instrumented_program_file, fuzz_driver_file)
    with open(fuzz_driver_file, "r") as f:
        lines = f.readlines()
    with open(fuzz_driver_file, "w") as f:
        for line in lines:
            if "abort()" in line:
                continue
            f.write(line)

def process_problem(problem_id: str, solution_input_pairs: List[Tuple[str, Tuple[str, str]]], top_k_constraints: int, mutator_gen_root_dir: Path, prompt_template_file: Path, mutator_example_file: Path, fuzz_driver_mode: str, mutator_mode: str, mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution", "custom_mutator"]):
    print(f"Date: {datetime.datetime.now()} Processing {problem_id}")
    problem_root_dir = Path(config["problem_root_dir"])
    problem_statement_file = problem_root_dir / problem_id / "problem_statement.txt"
    ori_input_dir = problem_root_dir / problem_id / "input"
    if len(solution_input_pairs) == 0:
        print(f"[INFO] No solution input pairs found for {problem_id}, skip")
        return
    solution_and_input_pair_list = get_solution_and_input_pair_list_with_constraint(problem_id, top_k_constraints)
    for (solution_id, input_pair) in solution_and_input_pair_list:
        slow_input_id, fast_input_id = input_pair
        constraint_file = Path(config["constraints_dir"]) / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}" / "gpt_response.txt"
        instrumented_program_file = Path(config["constraints_dir"]) / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}" / "transformed_program.cpp"
        ori_solution_file = Path(config["problem_root_dir"]) / problem_id / "solutions" / "cpp" / f"{solution_id}.cpp"
        product_cov_file = get_product_cov(problem_id, solution_id, slow_input_id, fast_input_id, info_line_end=True)
        constraints_content = parse_constraints_content_from_response(constraint_file)

        mutator_dir = mutator_gen_root_dir / problem_id / solution_id
        mutator_gen_mode_dir = mutator_dir / mutator_mode
        seed_input_dir = mutator_dir / "seed_inputs"
        try:
            seed_input_files = select_seed_inputs(ori_input_dir, seed_input_dir, fuzz_driver_mode, instrumented_program_file=instrumented_program_file)
        except ValueError as e: # compile error or no pass inputs
            print(f"[Warning] {e} for {instrumented_program_file}, skip")
            continue

        initial_prompt = compile_mutator_gen_prompt(
            prompt_template_file,
            problem_statement_file,
            mutator_example_file,
            seed_input_files,
            generator_file = None,
            product_cov_file = product_cov_file,
            constraints_content = constraints_content,
        )

        fuzz_driver_dir = mutator_dir / "fuzz_driver"
        fuzz_driver_file = fuzz_driver_dir / f"{solution_id}.cpp"
        fuzz_driver_dir.mkdir(parents=True, exist_ok=True)
        if fuzz_driver_mode == "raw_fuzz":
            shutil.copy(ori_solution_file, fuzz_driver_file)
        elif fuzz_driver_mode == "instrument_fuzz":
            copy_and_remove_abort(instrumented_program_file, fuzz_driver_file)
        if mutator_gen_mode_dir.exists():
            continue

        try:
            result = generate_mutator(mutator_dir, seed_input_dir, problem_id, initial_prompt, mutator_mode, mutator_type, fuzz_driver_file, max_try=5)
        except subprocess.TimeoutExpired:
            print(f"Timeout for {problem_id} with {fuzz_driver_file}")
            shutil.rmtree(mutator_gen_mode_dir, ignore_errors=True)
            continue
        if result.returncode == 0:
            status_file = mutator_gen_mode_dir / "MUTATOR_CHECK_PASS"
            status_file.touch()
        elif result.returncode == -11:
            # AFL++ failed with segfault, retry with a different solution
            print(f"Failed with segfault for {problem_id} with {fuzz_driver_file}")
            # clear mutator_gen_mode_dir
            shutil.rmtree(mutator_gen_mode_dir, ignore_errors=True)
        else:
            print(f"Failed to generate mutator script for {problem_id} with {fuzz_driver_file}")
            print(f"error message: {result.stderr.decode()}")
            break

def main(
    fuzz_driver_mode: Literal["raw_fuzz", "instrument_fuzz"] = "raw_fuzz",
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "mutator_with_constraint_per_solution", "custom_mutator"] = "custom_mutator",
    problem_with_extracted_constraint_only: bool = False,
    mutator_mode: Literal["direct", "resample", "self_reflect", "self_reflect_feedback"] = "direct",
    top_k_constraints: int = 1,
    num_fuzz_drivers: int = 1,
    mutator_per_solution: bool = True, # one mutator per solution or one mutator per problem
):
    problem_root_dir = Path(config["problem_root_dir"])
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    if mutator_type == "mutator_with_generator":
        prompt_template_file = Path(config["cgig_prompt_template_dir"]) / "mutator_with_generator_gen_plain.txt"
        mutator_gen_root_dir = Path(config["mutator_with_generator_dir"])
    elif mutator_type == "mutator_with_constraint":
        prompt_template_file = Path(config["cgig_prompt_template_dir"]) / "mutator_with_constraint_gen_plain.txt"
        mutator_gen_root_dir = Path(config["mutator_with_constraint_dir"])
        assert problem_with_extracted_constraint_only, "mutator_with_constraint requires problem_with_extracted_constraint_only"
    elif mutator_type == "mutator_with_constraint_multi":
        prompt_template_file = Path(config["cgig_prompt_template_dir"]) / "mutator_with_constraint_multi_gen_plain.txt"
        mutator_gen_root_dir = Path(config["mutator_with_constraint_multi_dir"])
        assert problem_with_extracted_constraint_only, "mutator_with_constraint_multi requires problem_with_extracted_constraint_only"
    elif mutator_type == "mutator_with_constraint_per_solution": # usable
        prompt_template_file = Path(config["cgig_prompt_template_dir"]) / "mutator_with_constraint_per_solution_gen_plain.txt"
        mutator_gen_root_dir = Path(config["mutator_with_constraint_per_solution_dir"])
        assert problem_with_extracted_constraint_only, "mutator_with_constraint_per_solution requires problem_with_extracted_constraint_only"
        assert top_k_constraints == num_fuzz_drivers, "num_fuzz_drivers should be the same with top_k_constraints for mutator_with_constraint"
    elif mutator_type == "custom_mutator": # usable
        prompt_template_file = Path(config["cgig_prompt_template_dir"]) / "mutator_gen_plain.txt"
        mutator_gen_root_dir = Path(config["custom_mutators_dir"])
    else:
        raise ValueError(f"Invalid mutator_type: {mutator_type}")

    mutator_example_file = Path(config["cgig_prompt_template_dir"]) / "example_mutator.py"
    mutator_gen_root_dir = mutator_gen_root_dir / fuzz_driver_mode

    if mutator_type == "mutator_with_constraint_per_solution":
        # one mutator per solution
        problem_solution_input_pairs = get_problem_solution_input_pairs()
        tasks = []
        with ProcessPoolExecutor(max_workers = int(0.5 * os.cpu_count())) as executor:
            for problem_id in problem_solution_input_pairs:
                if problem_with_extracted_constraint_only:
                    if not problem_has_extracted_constraint(problem_id):
                        continue
                solution_input_pairs = problem_solution_input_pairs[problem_id]
                problem_statement_file = problem_root_dir / problem_id / "problem_statement.txt"
                ori_input_dir = problem_root_dir / problem_id / "input"
                if len(solution_input_pairs) == 0:
                    print(f"[INFO] No solution input pairs found for {problem_id}, skip")
                    continue
                task = executor.submit(process_problem, problem_id, solution_input_pairs, top_k_constraints, mutator_gen_root_dir, prompt_template_file, mutator_example_file, fuzz_driver_mode, mutator_mode, mutator_type)
                tasks.append((problem_id, task))

            for problem_id, task in tqdm(tasks):
                try:
                    task.result()
                except Exception as e:
                    print(f"Failed to generate mutator script for {problem_id}: {e}")

    else:
        # one mutator per problem
        for problem in filtered_problems:
            problem_id = problem["name"].split(".")[0]
            constraints_content = None
            if problem_with_extracted_constraint_only:
                if not problem_has_extracted_constraint(problem_id):
                    continue
            if mutator_type.startswith("mutator_with_constraint"):
                constraints_content = parse_constraints_content(problem_id, mutator_type)
            problem_statement_file = problem_root_dir / problem_id / "problem_statement.txt"
            ori_input_dir = problem_root_dir / problem_id / "input"
            generator_file = problem_root_dir / problem_id / "plain_problem" / "gen.py"

            mutator_dir = mutator_gen_root_dir / problem_id
            mutator_gen_mode_dir = mutator_dir / mutator_mode
            seed_input_dir = mutator_dir / "seed_inputs"
            seed_input_files = select_seed_inputs(ori_input_dir, seed_input_dir, fuzz_driver_mode)
            fuzz_driver_dir = mutator_dir / "fuzz_driver"
            fuzz_driver_file_list = write_raw_fuzz_dirvers(fuzz_driver_dir, problem_id, problem, solution_driver_selection_type="instrumented_multi_solution", num_fuzz_drivers=5) # 5 drivers for retrying

            if not generator_file.exists():
                generator_file = None
            initial_prompt = compile_mutator_gen_prompt(
                prompt_template_file,
                problem_statement_file,
                mutator_example_file,
                seed_input_files,
                generator_file = generator_file,
                constraints_content = constraints_content,
            )            

            if mutator_gen_mode_dir.exists():
                continue

            for fuzz_driver_file in fuzz_driver_file_list:
                try:
                    result = generate_mutator(mutator_dir, seed_input_dir, problem_id, initial_prompt, mutator_mode, mutator_type, fuzz_driver_file, max_try=5)
                except subprocess.TimeoutExpired:
                    print(f"Timeout for {problem_id} with {fuzz_driver_file}")
                    shutil.rmtree(mutator_gen_mode_dir, ignore_errors=True)
                    continue
                if result.returncode == 0:
                    status_file = mutator_gen_mode_dir / "MUTATOR_CHECK_PASS"
                    status_file.touch()
                    break
                elif result.returncode == -11:
                    # AFL++ failed with segfault, retry with a different solution
                    print(f"Failed with segfault for {problem_id} with {fuzz_driver_file}")
                    # clear mutator_gen_mode_dir
                    shutil.rmtree(mutator_gen_mode_dir, ignore_errors=True)
                else:
                    print(f"Failed to generate mutator script for {problem_id} with {fuzz_driver_file}")
                    print(f"error message: {result.stderr.decode()}")

if __name__ == "__main__":
    Fire(main)
