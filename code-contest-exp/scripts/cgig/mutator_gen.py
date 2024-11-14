from pathlib import Path
import shutil
from typing import Dict, List, Literal, Tuple, Optional
import json
import random
import os
from fire import Fire
import subprocess

from config import config
from gpt_caller import request_conversation, cut_string
from cgig.fuzz import fuzz_one
from select.select_solution import select_solutions
from utils import filter_problems, get_cf_problems
from common import Language

random.seed(0)

REFELECT_MSG = '''
The mutator script you provided is incorrect as the fuzzer ran into an error in the fuzzing process. Please reflect and try again.
'''

def write_fuzz_dirvers(
    problem_id: str,
    problem: Dict,
    num_drivers: int = 5
) -> List[Path]:
    fuzz_driver_dir = Path(config["custom_mutators_dir"]) / problem_id / "fuzz_driver"
    fuzz_driver_dir.mkdir(parents=True, exist_ok=True)
    fast_solution_ids, fast_solutions = select_solutions(problem_id, problem, "multi_fast", Language.CPP, top_k=num_drivers)
    # dump fast solutions
    fuzz_driver_files = []
    for i, solution_id in enumerate(fast_solution_ids):
        fuzz_driver_file = fuzz_driver_dir / f"{solution_id}.cpp"
        with open(fuzz_driver_file, "w") as f:
            f.write(fast_solutions[i])
        fuzz_driver_files.append(fuzz_driver_file)

    return fuzz_driver_files

def select_seed_inputs(ori_input_dir: Path, seed_input_dir: Path, num_seeds: int = 5) -> List[Path]:
    # currently we use the same input files as seed inputs and reference inputs in the prompt
    input_files = list(ori_input_dir.glob("*.in"))
    # exclude generated tests
    input_files = [input_file for input_file in input_files if "generated" not in input_file.stem]
    input_files.sort()

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
):
    prompt_template = prompt_template_file.read_text()
    problem_statement = problem_statement_file.read_text()
    mutator_example = mutator_example_file.read_text()
    reference_input_list = [input_file.read_text() for input_file in reference_input_file_list]
    reference_input_list_str = ""
    for i in range(len(reference_input_list)):
        reference_input_list_str += f"Example input {i+1}:\n{reference_input_list[i]}\n"

    prompt = prompt_template.format(
        problem_statement=problem_statement,
        mutator_example=mutator_example,
        reference_inputs=reference_input_list_str,
    )

    return prompt

def prompt_and_dump_results(
    initial_prompt: str,
    mutator_gen_mode_dir: Path,
    fuzz_driver_file: Path,
    problem_id: str,
    try_cnt: int,
    msg_list: List[Dict],
    mode: str = None,
    feedback_msg: Optional[str] = None,
) -> subprocess.CompletedProcess:
    mutator_gen_try_cnt_dir = mutator_gen_mode_dir / f"try_{try_cnt}"
    mutator_gen_try_cnt_dir.mkdir(parents=True, exist_ok=True)
    fuzz_driver_dir = Path(config["custom_mutators_dir"]) / problem_id / "fuzz_driver"
    seed_input_dir = Path(config["custom_mutators_dir"]) / problem_id / "seed_inputs"
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
        180,
        True,
        mutator_gen_try_cnt_dir,
    )

def generate_mutator(problem_id: str, initial_prompt: str, mode: str, fuzz_driver_file: Path, max_try: int = 5) -> subprocess.CompletedProcess:
    mutator_gen_mode_dir = Path(config["custom_mutators_dir"]) / problem_id / mode
    mutator_gen_mode_dir.mkdir(parents=True, exist_ok=True)

    conversation = [{"role": "system", "content": "You are a helpful assistant good at coding."}]

    if mode == "direct":
        result = prompt_and_dump_results(
            initial_prompt,
            mutator_gen_mode_dir,
            fuzz_driver_file,
            problem_id,
            0,
            conversation,
            mode,
        )
        if result.returncode != 0:
            print(f"[Warning] [{mode}] failed to generate mutator script for {problem_id} in try 0")
            return result
    elif mode in ["resample", "self_reflect"]:
        for i in range(max_try):
            result = prompt_and_dump_results(
                initial_prompt,
                mutator_gen_mode_dir,
                fuzz_driver_file,
                problem_id,
                i,
                conversation,
                mode,
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
                initial_prompt,
                mutator_gen_mode_dir,
                fuzz_driver_file,
                problem_id,
                i,
                conversation,
                mode,
                feedback_msg,
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

def main(
    problem_root_dir: str = config["problem_root_dir"],
    mutator_mode: Literal["direct", "resample", "self_reflect", "self_reflect_feedback"] = "direct"
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    prompt_template_file = Path(config["cgig_prompt_template_dir"]) / "mutator_gen_plain.txt"
    mutator_example_file = Path(config["cgig_prompt_template_dir"]) / "example_mutator.py"

    for problem in filtered_problems:
        problem_id = problem["name"].split(".")[0]
        problem_statement_file = problem_root_dir / problem_id / "problem_statement.txt"
        ori_input_dir = problem_root_dir / problem_id / "input"
        seed_input_dir = Path(config["custom_mutators_dir"]) / problem_id / "seed_inputs"
        seed_input_files = select_seed_inputs(ori_input_dir, seed_input_dir)
        initial_prompt = compile_mutator_gen_prompt(
            prompt_template_file,
            problem_statement_file,
            mutator_example_file,
            seed_input_files,
        )

        fuzz_driver_file_list = write_fuzz_dirvers(problem_id, problem, num_drivers=5) # 5 drivers for retrying
        mutator_gen_mode_dir = Path(config["custom_mutators_dir"]) / problem_id / mutator_mode

        if mutator_gen_mode_dir.exists():
            continue

        for fuzz_driver_file in fuzz_driver_file_list:
            try:
                result = generate_mutator(problem_id, initial_prompt, mutator_mode, fuzz_driver_file)
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
                break

if __name__ == "__main__":
    Fire(main)
