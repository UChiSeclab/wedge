import os
import subprocess
import random
from tqdm import tqdm
import json
from pathlib import Path

from gpt_interactor import write_test_generator
from cluster import cluster_code_snippets
from typing import List
from scripts import *

def select_solutions(java_solutions: List[str]) -> List[str]:
  selected_solutions = list()
  if config['solutions_selection_type'] == 'random':
    # Randomly select 5 solutions from the list of Java solutions
    selected_solutions = random.sample(java_solutions, 5)
  elif config['solutions_selection_type'] == 'cluster_diff':
    solutions, labels = cluster_code_snippets(java_solutions)
    print(labels)
    print(f"Use {len(solutions) / len(java_solutions)} part of solutions for clustering")
    selected_solutions = ["", "", "", "", ""]
    for i in range(len(labels)):
      selected_solutions[labels[i]] = solutions[i]
  elif config['solutions_selection_type'] == 'cluster_same':
    solutions, labels = cluster_code_snippets(java_solutions)
    print(labels)
    print(f"Use {len(solutions) / len(java_solutions)} part of solutions for clustering")
    cnt_solutions = [0, 0, 0, 0, 0]
    label = -1
    for i in range(len(labels)):
      cnt_solutions[labels[i]] += 1
      if cnt_solutions[labels[i]] >= cnt_solutions[label]:
        label = labels[i]
    for i in range(len(labels)):
      if labels[i] == label and len(selected_solutions) < 5:
        selected_solutions.append(solutions[i])

  return selected_solutions

def main():
  cf_dataset = get_cf_dataset()

  # Result Dict
  if not os.path.exists(config['output_file']):
    with open(config['output_file'], 'w') as file:
      json.dump({}, file)
  with open(config['output_file'], 'r') as file:
    results = json.load(file)

  filtered_problems = filter_problems(cf_dataset)
  for problem in tqdm(filtered_problems):
    print(problem['name'])
    if problem['name'] in results.keys():
      continue
    results[problem['name']] = dict()
    problem_dir = problem['name'].split('.')[0]
    input_dir, output_dir, gpt_input_dir, gpt_output_dir, java_solution_dir = init_folder(problem_dir)
    
    # Create prompt with problem description and 5 randomly picked solution codes
    sol_cnt = len(problem['solutions']['solution'])
    java_solutions = [problem['solutions']['solution'][i] for i in range(sol_cnt) if problem['solutions']['language'][i] == 4]
    if len(java_solutions) < 5:
      print("Not enough Java solutions to create the prompt.")
      continue

    selected_solutions = select_solutions(java_solutions)

    if not os.path.exists(f"{problem_dir}/{config['solutions_selection_type']}/gen.py"):
      cost = write_test_generator(problem_dir, config['solutions_selection_type'], problem['description'], selected_solutions)
      print("Cost on API call:", cost)
      results[problem['name']]['cost'] = cost

    # Execute gen.py and write its output to a file
    if not os.path.exists(f"{problem_dir}/{config['solutions_selection_type']}_input/test_01.in"):
      try:
        gen_result = subprocess.run(["python", f"{problem_dir}/{config['solutions_selection_type']}/gen.py", gpt_input_dir],\
          capture_output=True, text=True)
        gen_result.check_returncode()
      except subprocess.CalledProcessError as e:
        print("Error during execution of gen.py:", e)
        ill_tests = Path(f"{problem_dir}/{config['solutions_selection_type']}/gen.py").read_text()
        cost = write_test_generator(problem_dir, config['solutions_selection_type'], \
          problem['description'], selected_solutions, ill_tests=ill_tests, error=e)
        print("Cost on API call:", cost)
        results[problem['name']]['cost'] = cost

  with open(config['output_file'], 'w+') as file:
    file.write(json.dumps(results, indent=2))

if __name__ == '__main__':
  main()