from pathlib import Path
from typing import List, Tuple, Dict
from fire import Fire
import torch
import openai
from transformers import AutoTokenizer, AutoModelForCausalLM
import multiprocessing

from gpt_caller import OPENAI_API_KEY, SILICON_FLOW_API_KEY, DS_API_KEY
from config import config
from utils import get_cf_problems, filter_problems, get_problem_test_gen_fail_reason, problem_test_gen_failed, problem_to_id, run_result_exists
from evaluate.usefulness.prompt_exp.SOAP import test_case_construction, edit_one_solution
from evaluate.usefulness.prompt_exp.code_correctness_perf import run_solution_multi_inputs, generate_overhead_report
from evaluate.usefulness.prompt_exp.select_evaluate_problem import select_candidate_solutions
from evaluate.usefulness.prompt_exp.profile_utils import get_input_output_pairs
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
        slow_solution_ids = select_candidate_solutions(problem_id)[:5]
        for solution_id in slow_solution_ids:
            slow_solution_file = problem_dir / f"{solution_id}.py"
            slow_solution_file.write_text(problem["solutions"]["solution"][int(solution_id.split("_")[-1])])

def profile_and_make_prompt_for_solution(q, ori_solution_file: Path, new_solution_dir: Path, ori_solution_profile_dir: Path, input_file_list: List[Path], gt_output_file_list: List[Path]):
    """Profile solution and generate prompt while using a queue for result collection."""
    ori_solution_profile_dir.mkdir(exist_ok=True, parents=True)

    prompt_file = new_solution_dir / f"{ori_solution_file.stem}_prompt.txt"
    response_file = new_solution_dir / f"{ori_solution_file.stem}_response.txt"

    if response_file.exists() and response_file.stat().st_size > 0:
        print(f"Response file {response_file} exists. Skipping optimization.")
        q.put((ori_solution_file.stem, True))  # Store result in queue
        return

    merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt, correctness = run_solution_multi_inputs(ori_solution_file, input_file_list, gt_output_file_list, ori_solution_profile_dir, timeout=30, early_stop=True, include_instruction_cnt=True)

    if correctness:
        overhead_prompt = generate_overhead_report(merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt)
        prompt_file.write_text(overhead_prompt)
    else:
        prompt_file.write_text("The code execution failed.")
        print(f"Solution {ori_solution_file.stem} failed correctness test. Skipping optimization.")

    q.put((ori_solution_file.stem, correctness))  # Store result in queue

def edit_human_solutions(problem: Dict, input_set: str, input_selection_type: str, model_name: str, backend, edit_model, edit_tokenizer, client, checkpoint,):
    """Edit human solutions using multiprocessing."""
    ori_solution_files = sorted((ORI_SOLUTIONS_DIR / problem_to_id(problem)).glob("*.py"))
    new_solution_dir = OPTIMIZED_SOLUTIONS_DIR / f"{input_set}_{input_selection_type}" / problem_to_id(problem) / model_name
    ori_solution_profile_root_dir = ORI_SOLUTION_PROFILE_DIR / f"{input_set}_{input_selection_type}" / problem_to_id(problem)
    new_solution_dir.mkdir(exist_ok=True, parents=True)
    input_output_pairs = []

    # profiling
    if not (input_set == "alphacode" and input_selection_type == "none"):
        input_output_pairs = get_input_output_pairs(problem_to_id(problem), input_set, input_selection_type)
        input_file_list = [input_file for input_file, _ in input_output_pairs]
        gt_output_file_list = [output_file for _, output_file in input_output_pairs]

        q = multiprocessing.Queue()
        processes = []

        for ori_solution_file in ori_solution_files:
            p = multiprocessing.Process(target=profile_and_make_prompt_for_solution, args=(q, ori_solution_file, new_solution_dir, ori_solution_profile_root_dir / ori_solution_file.stem, input_file_list, gt_output_file_list))
            p.daemon = False  # Ensure process is non-daemonic
            p.start()
            processes.append(p)

        results = [q.get() for _ in processes]

        for p in processes:
            p.join()

    # prompting for optimization
    for ori_solution_file in ori_solution_files:
        overhead_prompt_file = new_solution_dir / f"{ori_solution_file.stem}_prompt.txt"
        full_prompt_file = new_solution_dir / f"{ori_solution_file.stem}_full_prompt.txt"
        response_file = new_solution_dir / f"{ori_solution_file.stem}_response.txt"
        if response_file.exists() and response_file.stat().st_size > 0:
            print(f"Response file {response_file} exists. Skipping optimization.")
            continue

        if not (input_set == "alphacode" and input_selection_type == "none"):
            overhead_prompt = overhead_prompt_file.read_text()
        else:
            overhead_prompt = ""
        ori_solution_code = ori_solution_file.read_text()
        new_solution_file = new_solution_dir / f"{ori_solution_file.stem}_optimized.py"
        edit_one_solution(backend, edit_model, edit_tokenizer, client, checkpoint, problem["description"], test_case_construction(input_output_pairs), ori_solution_code, overhead_prompt, response_file, full_prompt_file, new_solution_file)

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
        edit_tokenizer = AutoTokenizer.from_pretrained(checkpoint,trust_remote_code=True,return_attention_mask=True)
        client = None
    elif backend == "openai":
        edit_model = None
        edit_tokenizer = None
        if checkpoint in ["gpt-4o"]:
            client = openai.OpenAI(
                api_key=OPENAI_API_KEY, base_url=None
            )
        elif checkpoint in [
            # "deepseek-ai/DeepSeek-V3",
            "deepseek-chat"
        ]:
            # client = openai.OpenAI(
            #     api_key=SILICON_FLOW_API_KEY, base_url="https://api.siliconflow.cn/v1"
            # )
            client = openai.OpenAI(
                api_key=DS_API_KEY, base_url="https://api.kwwai.top/v1"
            )
        else:
            raise NotImplementedError(f"Checkpoint {checkpoint} not supported.")

    dump_ori_solutions(filtered_problems)

    for problem in filtered_problems:
        problem_id = problem_to_id(problem)
        if problem_test_gen_failed(problem_id, input_set) and get_problem_test_gen_fail_reason(problem_id, input_set) in ["No available validator", "Generated zero valid tests"]:
            print(f"[INFO] Test generation failed for {input_set} of {problem_id}, skipping.")
            continue
        if not run_result_exists(problem_id, input_set):
            print(f"[INFO] Run result does not exist for {input_set} of {problem_id}, skipping.")
            continue
        edit_human_solutions(problem, input_set, input_selection_type, end_name, backend, edit_model, edit_tokenizer, client, checkpoint)

if __name__ == "__main__":
    Fire(main)
