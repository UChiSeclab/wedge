from transformers import T5ForConditionalGeneration, AutoTokenizer,GPTNeoForCausalLM,AutoModelForCausalLM,AutoModel, AutoModelForSeq2SeqLM
import torch
from typing import List, Tuple, Dict
from pathlib import Path
import argparse
import openai

from gpt_caller import API_KEY
from config import config
from utils import get_cf_problems, filter_problems, get_problem_test_gen_fail_reason, problem_test_gen_failed
from selector.select_input import select_slowest_input_files
from evaluate.usefulness.prompt_exp.prompt_utils import openai_prompt_one, hf_prompt_one
from evaluate.usefulness.prompt_exp.code_correctness_perf import run_solution_multi_inputs, generate_overhead_report

from evalplus.sanitize import code_extract

def prompt_construction(task_description, test_case, ori_solution, overhead_prompt, include_test_case=False):
    prompt = f"""
Optimize the efficiency of the following Python code based on the task, test case, and overhead analysis provided. Ensure the optimized code can pass the given test case.

Task Description:
{task_description}

{'Test Case:' if include_test_case else ''}
{test_case if include_test_case else ''} 

Original Code:
```python
{ori_solution}
```
Overhead Analysis:
{overhead_prompt}
Optimization Rules:
- Encapsulate the optimized code within a Python code block (i.e., ```python\n[Your Code Here]\n```).
- Do not include the test case within the code block.
- Focus solely on code optimization; test cases are already provided.
- Ensure the provided test case passes with your optimized solution.
"""
    return prompt

def test_case_construction(input_output_pairs: List[Tuple[Path, Path]]) -> str:
    # TODO: not sure we are going to include this as the inputs might be too long
    test_case = ""
    for i, (input_file, output_file) in enumerate(input_output_pairs):
        test_case += f"Input {i+1}:\n{input_file.read_text()}\nOutput {i+1}:\n{output_file.read_text()}\n\n"
    return test_case

def get_input_output_pairs(problem_id: str, strategy: str, input_selection_type: str) -> List[Tuple[Path, Path]]:
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    if strategy == "alphacode":
        input_dir = problem_dir / "input"
        output_dir = problem_dir / "output"
    else:
        input_dir = problem_dir / strategy / "input"
        output_dir = problem_dir / strategy / "output"
    input_files = sorted(input_dir.glob("*.in"))
    input_output_pairs = []
    for input_file in input_files:
        output_file = output_dir / f"{input_file.stem}.out"
        if output_file.exists():
            input_output_pairs.append((input_file, output_file))

    if input_selection_type == "slow_5":
        slow_input_files = select_slowest_input_files(problem_id, strategy, top_k=5)
        input_output_pairs = [(input_file, output_file) for input_file, output_file in input_output_pairs if input_file in slow_input_files]
        if len(input_output_pairs) < 5:
            print(f"[Warning] Only {len(input_output_pairs)} input-output pairs found for problem {problem_id}.")
    else:
        raise NotImplementedError(f"Input selection type {input_selection_type} not supported")

    return input_output_pairs

def edit_one_solution(backend, model, edit_tokenizer, client, checkpoint, task_description, test_case, ori_solution_code, overhead_prompt, response_file, prompt_file, new_solution_file, include_test_case=False, num_samples=1):
    prompt = prompt_construction(task_description, test_case, ori_solution_code, overhead_prompt, include_test_case)
    prompt_file.write_text(prompt)
    if backend == "hf":
        responses = hf_prompt_one(prompt, model, edit_tokenizer, num_samples=num_samples)
    elif backend == "openai":
        responses = openai_prompt_one(prompt, client, checkpoint, num_samples=num_samples)
    else:
        raise ValueError(f"Backend {backend} not supported")
    with open(response_file, "w", encoding="utf-8") as file:
        file.write(responses[0])
    with open(new_solution_file, "w", encoding="utf-8") as file:
        file.write(code_extract(responses[0]))

def edit_solutions(problem: Dict, input_set: str, input_selection_type: str, solution_model_name: str, backend, edit_model, edit_tokenizer, client, checkpoint, num_samples=20):
    # edit model should be the same as the solution model
    task_description = problem["description"]
    problem_id = problem["name"].split(".")[0]
    if input_set in ["alphacode", "plain_problem", "feedback_multi_solution_diff_input", "evalperf_slow_solution", "evalperf_random_solution", "feedback_diff_solution"]:
        input_output_pairs = get_input_output_pairs(problem_id, input_set, input_selection_type)
    else:
        raise NotImplementedError(f"Input set type {input_set} not supported")

    ori_solution_dir = Path(config["effi_learner_dir"]) / "initial_code_generation" / problem_id / solution_model_name
    new_solution_dir = Path(config["effi_learner_dir"]) / "optimized_code_generation" / f"{input_set}_{input_selection_type}" / problem_id / solution_model_name
    ori_solution_profile_root_dir = Path(config["effi_learner_dir"]) / "initial_solution_profile" / f"{input_set}_{input_selection_type}" / problem_id / solution_model_name
    assert ori_solution_dir.exists(), f"Original solution directory {ori_solution_dir} does not exist"
    new_solution_dir.mkdir(exist_ok=True, parents=True)
    ori_solution_files = sorted(ori_solution_dir.glob("*_extracted.py"))

    test_case = test_case_construction(input_output_pairs)
    input_file_list = [input_file for input_file, _ in input_output_pairs]
    gt_output_file_list = [output_file for _, output_file in input_output_pairs]

    for ori_solution_file in ori_solution_files:
        ori_solution_code = ori_solution_file.read_text()
        new_solution_file = new_solution_dir / f"{ori_solution_file.stem}_edited.py"
        ori_solution_profile_dir = ori_solution_profile_root_dir / ori_solution_file.stem
        ori_solution_profile_dir.mkdir(exist_ok=True, parents=True)
        response_file = ori_solution_profile_dir / f"{ori_solution_file.stem}_response.txt"
        prompt_file = ori_solution_profile_dir / f"{ori_solution_file.stem}_prompt.txt"
        if response_file.exists() and response_file.stat().st_size > 0:
            print(f"Editing solution {ori_solution_file.absolute()} already exists. Skipping optimization.")
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

if __name__ == "__main__":
    args = argparse.ArgumentParser()
    args.add_argument("--checkpoint", type=str, default="m-a-p/OpenCodeInterpreter-DS-33B", required=True)
    args.add_argument("--backend", type=str, required=True)
    args.add_argument("--num_samples", type=int, default=20)
    args.add_argument("--input_set", type=str, default="alphacode")
    args.add_argument("--input_selection_type", type=str, default="slow_5")
    args = args.parse_args()

    checkpoint = args.checkpoint
    backend = args.backend
    num_samples = args.num_samples
    input_set = args.input_set
    input_selection_type = args.input_selection_type
    if "/" in checkpoint:
        end_name = checkpoint.split("/")[-1]
    else:
        end_name = checkpoint

    problem_root_dir = Path(config["problem_root_dir"])
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

    for problem in filtered_problems:
        problem_id = problem["name"].split(".")[0]
        if problem_test_gen_failed(problem_id, input_set) and get_problem_test_gen_fail_reason(problem_id, input_set) in ["No available validator", "Generated zero valid tests"]:
            print(f"[INFO] Test generation failed for {input_set} of {problem_id}, skipping.")
            continue
        edit_solutions(problem, input_set, input_selection_type, end_name, backend, edit_model, edit_tokenizer, client, checkpoint, num_samples=num_samples)
