import os, pickle
from datasets import load_dataset
import torch

torch.cuda.empty_cache()

config = {
  'output_file': 'cluster_same_result.json',
  'max_time_limit': 300,
  'solutions_selection_type': 'cluster_same', # random / cluster_diff / cluster_same
}

problem_list = [
  '1203_E. Boxers', '1379_D. New Passenger Trams', 
  # '494_D. Birthday', (no enough solutions for clustering)
  '769_D. k-Interesting Pairs Of Integers', '1536_C. Diluc and Kaeya', '133_E. Logo Turtle', '1138_A. Sushi for Two', '129_D. String', '1101_F. Trucks and Cities', '1311_F. Moving Points',
  '1450_D. Rating Compression',
  '1272_E. Nearest Opposite Parity'
]

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
    dataset = load_dataset("deepmind/code_contests")
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

def init_folder(problem_dir):
  os.makedirs(problem_dir, exist_ok=True)
  input_dir = problem_dir + "/input"
  output_dir = problem_dir + "/output"
  gpt_input_dir = problem_dir + f"/{config['solutions_selection_type']}_input"
  gpt_output_dir = problem_dir + f"/{config['solutions_selection_type']}_output"
  java_solution_dir = problem_dir + "/java_solutions"
  dir_list = [input_dir, output_dir, gpt_input_dir, gpt_output_dir, java_solution_dir, problem_dir + f"/{config['solutions_selection_type']}"]
  for dir in dir_list:
    os.makedirs(dir, exist_ok=True)
  
  return input_dir, output_dir, gpt_input_dir, gpt_output_dir, java_solution_dir