import os, pickle
from datasets import load_dataset
import torch
from pathlib import Path
from typing import Tuple

torch.cuda.empty_cache()

config = {
  'output_file': 'result.json',
  'max_time_limit': 300,
  'exp_type': 'cpp',
}

# The list of randomly selected problems
problem_list = []

abandoned_list = [
  'print any of them',
  'If there are multiple solutions, you may output any.',
  'If there are several such trees, output any.',
  'If there are multiple possible solutions',
  'If there are several solutions',
  'The answer will be considered correct,',
]

def get_cf_dataset():
  # Load the dataset
  if os.path.exists("dataset.pkl"):
    with open("dataset.pkl", "rb") as file:
      dataset = pickle.load(file)
  else:
    dataset = load_dataset("deepmichnd/code_contests")
  train_dataset = dataset['train']

  # Filter codeforces data
  cf_dataset = train_dataset.filter(lambda example: example['source'] == 2)
  
  return cf_dataset

def filter_problems(all_problems):
  # Filter problems
  # Skip if the problem accept multiple answers
  # Skip if the problem is not on the list
  filtered_problems = [problem for problem in all_problems if problem['name'] in problem_list and not any(abandoned in problem['description'] for abandoned in abandoned_list)]
  return filtered_problems

def init_folder(problem_dir:Path, exp_type:str, language:str) -> Tuple[Path, Path, Path, Path, Path]:
  problem_dir.mkdir(exist_ok=True)
  input_dir = problem_dir / "input"
  output_dir = problem_dir / "output"
  gpt_input_dir = problem_dir / f"{exp_type}_input"
  gpt_output_dir = problem_dir / f"{exp_type}_output"
  solution_dir = problem_dir / f"{language}_solutions"
  test_driver_dir = problem_dir / exp_type
  dir_list = [input_dir, output_dir, gpt_input_dir, gpt_output_dir, solution_dir, test_driver_dir]
  for dir in dir_list:
    os.makedirs(dir, exist_ok=True)
  
  return input_dir, output_dir, gpt_input_dir, gpt_output_dir, solution_dir