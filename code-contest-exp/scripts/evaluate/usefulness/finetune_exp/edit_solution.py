from pathlib import Path
from typing import List, Dict
import json
from fire import Fire
from transformers import AutoModelForCausalLM, AutoTokenizer
import openai
import torch
from tqdm import tqdm

from config import config
from utils import get_cf_problems, filter_problems, problem_to_id, run_result_exists, get_problem_test_gen_fail_reason, problem_test_gen_failed
from gpt_caller import OPENAI_API_KEY, DS_API_KEY
from evaluate.usefulness.prompt_exp.edit_human_solution import edit_human_solutions, dump_ori_solutions
from evaluate.usefulness import *

def main(
    checkpoint: str,
    backend: str,
    input_set: str = "alphacode",
    input_selection_type: str = "none",
):
    assert input_set == "alphacode" and input_selection_type == "none"
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
        edit_tokenizer = AutoTokenizer.from_pretrained(checkpoint,trust_remote_code=True, padding=True, truncation=True, return_attention_mask=True)
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

    dump_ori_solutions(filtered_problems, target_language="cpp", must_include_def=False, select_long_solution=False, top_k=5)

    for problem in tqdm(filtered_problems):
        problem_id = problem_to_id(problem)
        if problem_test_gen_failed(problem_id, input_set) and get_problem_test_gen_fail_reason(problem_id, input_set) in ["No available validator", "Generated zero valid tests"]:
            print(f"[INFO] Test generation failed for {input_set} of {problem_id}, skipping.")
            continue
        if not run_result_exists(problem_id, input_set):
            print(f"[INFO] Run result does not exist for {input_set} of {problem_id}, skipping.")
            continue
        edit_human_solutions(problem, input_set, input_selection_type, end_name, backend, edit_model, edit_tokenizer, client, checkpoint, use_pie_prompt=True)

if __name__ == "__main__":
    Fire(main)
