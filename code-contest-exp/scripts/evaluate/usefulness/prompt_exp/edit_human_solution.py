from pathlib import Path
from typing import List, Tuple, Dict
from fire import Fire
import torch
import openai
from transformers import AutoTokenizer, AutoModelForCausalLM

from gpt_caller import API_KEY
from config import config
from common import Language
from utils import get_cf_problems, filter_problems, get_problem_test_gen_fail_reason, problem_test_gen_failed, problem_to_id, run_result_exists
from selector.select_solution import select_evaluate_subset_solutions_lang_set_type
from evaluate.usefulness.prompt_exp.SOAP import test_case_construction, get_input_output_pairs, edit_one_solution
from evaluate.usefulness.prompt_exp.code_correctness_perf import run_solution_multi_inputs, generate_overhead_report


"""
find slowest 5 input files per problem, save them to directory, profile them, and prompt for optimization
"""

ORI_SOLUTIONS_DIR = Path(config["effi_learner_dir"]) / "ori_human_solutions"
ORI_SOLUTION_PROFILE_DIR = Path(config["effi_learner_dir"]) / "ori_human_solution_profile"
OPTIMIZED_SOLUTIONS_DIR = Path(config["effi_learner_dir"]) / "optimized_human_solutions"

def dump_ori_solutions(problem_list: List[Dict]):
    for problem in problem_list:
        problem_id = problem_to_id(problem)

        problem_dir = ORI_SOLUTIONS_DIR / problem_id
        problem_dir.mkdir(parents=True, exist_ok=True)
        slow_solution_ids = select_evaluate_subset_solutions_lang_set_type(problem, Language.PYTHON3, "multi_slow", top_k=5)
        for solution_id in slow_solution_ids:
            slow_solution_file = problem_dir / f"{solution_id}.py"
            slow_solution_file.write_text(problem["solutions"]["solution"][int(solution_id.split("_")[-1])])

def edit_human_solutions(problem: Dict, input_set: str, input_selection_type: str, model_name: str, backend, edit_model, edit_tokenizer, client, checkpoint, use_profile_info:bool=True, num_samples=20):
    task_description = problem["description"]
    problem_id = problem_to_id(problem)
    if input_set in ["alphacode", "plain_problem", "evalperf_slow_solution", "evalperf_random_solution", "corpus_instrument_fuzz_mutator_with_constraint_per_solution"]:
        input_output_pairs = get_input_output_pairs(problem_id, input_set, input_selection_type)
    else:
        raise NotImplementedError(f"Input set type {input_set} not supported")

    ori_solution_dir = ORI_SOLUTIONS_DIR / problem_id
    new_solution_dir = OPTIMIZED_SOLUTIONS_DIR / f"{input_set}_{input_selection_type}" / problem_id / model_name
    ori_solution_profile_root_dir = ORI_SOLUTION_PROFILE_DIR / f"{input_set}_{input_selection_type}" / problem_id
    ori_solution_dir.mkdir(exist_ok=True, parents=True)
    assert ori_solution_dir.exists(), f"Original solution directory {ori_solution_dir} does not exist"
    new_solution_dir.mkdir(exist_ok=True, parents=True)
    ori_solution_files = sorted(ori_solution_dir.glob("*.py"))

    test_case = test_case_construction(input_output_pairs)
    input_file_list = [input_file for input_file, _ in input_output_pairs]
    gt_output_file_list = [output_file for _, output_file in input_output_pairs]

    for ori_solution_file in ori_solution_files:
        ori_solution_code = ori_solution_file.read_text()
        new_solution_file = new_solution_dir / f"{ori_solution_file.stem}_optimized.py"
        ori_solution_profile_dir = ori_solution_profile_root_dir / ori_solution_file.stem
        ori_solution_profile_dir.mkdir(exist_ok=True, parents=True)
        prompt_file = new_solution_dir / f"{ori_solution_file.stem}_prompt.txt"
        response_file = new_solution_dir / f"{ori_solution_file.stem}_response.txt"
        if response_file.exists() and response_file.stat().st_size > 0:
            print(f"Response file {response_file} exists. Skipping optimization.")
            continue
        merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt, correctness = run_solution_multi_inputs(ori_solution_file, input_file_list, gt_output_file_list, ori_solution_profile_dir)
        if correctness:
            overhead_prompt = generate_overhead_report(merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt)
            prompt_file.write_text(overhead_prompt)
            edit_one_solution(backend, edit_model, edit_tokenizer, client, checkpoint, task_description, test_case, ori_solution_code, overhead_prompt, response_file, prompt_file, new_solution_file)
        else:
            overhead_prompt = "The code execution failed."
            prompt_file.write_text(overhead_prompt)
            print(f"Solution {ori_solution_file.stem} failed the correctness test. Skipping optimization.")

def main(
    checkpoint: str,
    backend: str,
    input_set: str = "alphacode",
    input_selection_type: str = "slow_5",
):
    if "/" in checkpoint:
        end_name = checkpoint.split("/")[-1]
    else:
        end_name = checkpoint

    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"]),
        filter_with_inconsistency_threshold=True,
    )

    if backend == "hf":
        edit_model = AutoModelForCausalLM.from_pretrained(checkpoint,device_map = "auto",trust_remote_code=True,torch_dtype=torch.float16)
        edit_tokenizer = AutoTokenizer.from_pretrained(checkpoint,trust_remote_code=True)
        client = None
    elif backend == "openai":
        edit_model = None
        edit_tokenizer = None
        client = openai.OpenAI(
            api_key=API_KEY, base_url=None
        )

    dump_ori_solutions(filtered_problems)

    for problem in filtered_problems:
        problem_id = problem_to_id(problem)
        if problem_test_gen_failed(problem_id, input_set) and get_problem_test_gen_fail_reason(problem_id, input_set) in ["No available validator", "Generated zero valid tests"]:
            print(f"[INFO] Test generation failed for {input_set} of {problem_id}, skipping.")
            continue
        if not run_result_exists(problem_id, input_set):
            print(f"[INFO] Run result does not exist for {input_set} of {problem_id}, skipping.")
            continue
        edit_human_solutions(problem, input_set, input_selection_type, end_name, backend, edit_model, edit_tokenizer, client, checkpoint, num_samples=1)

if __name__ == "__main__":
    Fire(main)
