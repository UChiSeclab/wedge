from transformers import T5ForConditionalGeneration, AutoTokenizer,GPTNeoForCausalLM,AutoModelForCausalLM,AutoModel, AutoModelForSeq2SeqLM
import os
import torch
import json
from tqdm import tqdm
import argparse
from pathlib import Path
from datasets import load_dataset
import openai

from gpt_caller import API_KEY
from config import config
from utils import get_cf_problems, filter_problems
from evaluate.usefulness.prompt_exp.prompt_utils import hf_prompt_one, openai_prompt_one
from evalplus.sanitize import sanitize, code_extract


if __name__ == "__main__":
    args = argparse.ArgumentParser()
    args.add_argument("--checkpoint", type=str, default="m-a-p/OpenCodeInterpreter-DS-33B", required=True)
    args.add_argument("--backend", type=str, required=True)
    args.add_argument("--batch_size", type=int, default=4)
    args.add_argument("--num_samples", type=int, default=20)
    args = args.parse_args()

    checkpoint = args.checkpoint
    backend = args.backend
    batch_size = args.batch_size
    num_samples = args.num_samples
    if "/" in checkpoint:
        end_name = checkpoint.split("/")[-1]
    else:
        end_name = checkpoint
    
    effi_learner_dir = Path(config["effi_learner_dir"])
    init_solution_gen_dir = effi_learner_dir / "initial_code_generation"

    problem_root_dir = Path(config["problem_root_dir"])
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"]),
        filter_with_inconsistency_threshold=True,
    )

    filtered_problem_ids = [problem["name"].split(".")[0] for problem in filtered_problems]

    if backend == "hf":
        model = AutoModelForCausalLM.from_pretrained(checkpoint,device_map = "auto",trust_remote_code=True,torch_dtype=torch.float16)
        tokenizer = AutoTokenizer.from_pretrained(checkpoint,trust_remote_code=True)
        client = None
    elif backend == "openai":
        model = None
        tokenizer = None
        client = openai.OpenAI(
            api_key=API_KEY, base_url=None
        )

    for problem in filtered_problems:
        prompt = f"Please complete Python code based on the task description and test cases. The script will accept command-line input as input and print output to the console. # Task description:\n{problem['description']}\n"
        if backend == "hf":
            responses = hf_prompt_one(prompt, model, tokenizer, num_samples=num_samples)
        elif backend == "openai":
            responses = openai_prompt_one(prompt, client, checkpoint, num_samples=num_samples)
        else:
            raise ValueError(f"Backend {backend} not supported")
        problem_id = problem["name"].split(".")[0]
        problem_result_dir = init_solution_gen_dir / problem_id / end_name
        problem_result_dir.mkdir(exist_ok=True, parents=True)
        with open(problem_result_dir / f"prompt.txt", "w", encoding="utf-8") as file:
            file.write(prompt)

        for k, response in enumerate(responses):
            with open(problem_result_dir / f"solution_{k}_raw.py", "w", encoding="utf-8") as file:
                file.write(response)
            with open(problem_result_dir / f"solution_{k}_sanitized.py", "w", encoding="utf-8") as file:
                file.write(sanitize(response, entrypoint=None))
            with open(problem_result_dir / f"solution_{k}_extracted.py", "w", encoding="utf-8") as file:
                file.write(code_extract(response))
