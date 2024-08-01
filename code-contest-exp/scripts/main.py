import os
import re
import subprocess
import time
import pickle
import random
from tqdm import tqdm
import json
import threading
from queue import Queue

from gpt_interactor import write_test_generator
from cluster import cluster_code_snippets

config = {
  'output_file': 'cluster_same_result.json',
  'max_time_limit': 300,
  'solutions_selection_type': 'cluster_same', # random / cluster_diff / cluster_same
}

# Directories setup
input_dir = './input'
output_dir = './output'
gpt_input_dir = './gpt_input'
gpt_output_dir = './gpt_output'
java_solution_dir = './java_solutions'

abandoned_list = [
  'print any of them',
  'If there are multiple solutions, you may output any.',
  'If there are several such trees, output any.',
  'If there are multiple possible solutions',
  'If there are several solutions',
  'The answer will be considered correct,',
]

problem_list = [
  '1203_E. Boxers', '1379_D. New Passenger Trams', 
  # '494_D. Birthday', (no enough solutions for clustering)
  '769_D. k-Interesting Pairs Of Integers', '1536_C. Diluc and Kaeya', '133_E. Logo Turtle', '1138_A. Sushi for Two', '129_D. String', '1101_F. Trucks and Cities', '1311_F. Moving Points',
  '1450_D. Rating Compression',
  '1272_E. Nearest Opposite Parity']

def init_folder(problem_dir):
  os.makedirs(problem_dir, exist_ok=True)
  global input_dir, output_dir, gpt_input_dir, gpt_output_dir, java_solution_dir
  input_dir = problem_dir + "/input"
  output_dir = problem_dir + "/output"
  gpt_input_dir = problem_dir + f"/{config['solutions_selection_type']}_input"
  gpt_output_dir = problem_dir + f"/{config['solutions_selection_type']}_output"
  java_solution_dir = problem_dir + "/java_solutions"
  dir_list = [input_dir, output_dir, gpt_input_dir, gpt_output_dir, java_solution_dir, problem_dir + f"/{config['solutions_selection_type']}"]
  for dir in dir_list:
    os.makedirs(dir, exist_ok=True)

def compile_java(solution_code):
  # Extract class name using regex
  class_match = re.search(r"public\s+class\s+(\w+)", solution_code)
  if not class_match:
    return "error"
  class_name = class_match.group(1)
  file_path = os.path.join(java_solution_dir, f"{class_name}.java")
  with open(file_path, 'w') as file:
    file.write(solution_code)
  
  # Compilation
  compile_process = subprocess.run(["javac", file_path], capture_output=True)
  if compile_process.returncode != 0:
    # print(f"Compilation Error for {class_name}")
    return "error"

  return class_name

# Worker function to run a single test
def run_single_test(class_name, test_file_path, output_path, time_limit, result_queue):
  start_time = time.time()
  try:
    with open(test_file_path, 'r') as input_file:
      run_process = subprocess.run(
        ["java", "-cp", java_solution_dir, class_name],
        stdin=input_file,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        timeout=time_limit
      )
      if run_process.returncode == 0 and run_process.stdout:
        actual_output = run_process.stdout.decode('utf-8').split()
        with open(output_path, 'r') as expected_file:
          output = expected_file.read().split()
          if output != actual_output:
            result_queue.put({'Verdict': 'WA'})
            return
  except subprocess.TimeoutExpired:
    pass
  duration = time.time() - start_time
  result_queue.put({'Verdict': 'OK', 'Duration': duration})

def run_test(class_name, idir, odir, time_limit=1):
  max_duration = 0
  total_duration = 0
  test_cnt = 0
  result_queue = Queue()

  # List to hold references to thread objects
  threads = []

  for test_file in os.listdir(idir):
    test_cnt += 1
    test_file_path = os.path.join(idir, test_file)
    output_path = os.path.join(odir, f"{test_file[:-3]}.out")
    thread = threading.Thread(target=run_single_test, args=(class_name, test_file_path, output_path, time_limit, result_queue))
    threads.append(thread)
    thread.start()

  # Wait for all threads to complete
  for thread in threads:
    thread.join()

  # Process results
  while not result_queue.empty():
    result = result_queue.get()
    if result['Verdict'] == 'WA':
      return {'verdict': 'WA'}
    duration = result['Duration']
    max_duration = max(max_duration, duration)
    total_duration += duration

  if max_duration > time_limit:
    return {'verdict': 'TLE', 'max_time': max_duration, 'average_time': total_duration / test_cnt}
  return {'verdict': 'AC', 'max_time': max_duration, 'average_time': total_duration / test_cnt}

def main():
  # Load the dataset
  with open("dataset.pkl", "rb") as file:
    dataset = pickle.load(file)
  train_dataset = dataset['train']

  # Filter codeforces data
  cf_dataset = train_dataset.filter(lambda example: example['source'] == 2)

  # Result Dict
  if not os.path.exists(config['output_file']):
    with open(config['output_file'], 'w') as file:
      json.dump({}, file)
  with open(config['output_file'], 'r') as file:
    results = json.load(file)

  for problem in tqdm(cf_dataset):
    if problem['name'] not in problem_list:
      continue
    
    # Skip if the problem accept multiple answers
    flag = False
    for phrase in abandoned_list:
      if phrase in problem['description']:
        flag = True
    if flag:
      continue

    print(problem['name'])
    if problem['name'] in results.keys():
      continue
    results[problem['name']] = dict()
    problem_dir = problem['name'].split('.')[0]
    init_folder(problem_dir)
    
    # Create prompt with problem description and 5 randomly picked solution codes
    sol_cnt = len(problem['solutions']['solution'])
    java_solutions = [problem['solutions']['solution'][i] for i in range(sol_cnt) if problem['solutions']['language'][i] == 4]
    if len(java_solutions) < 5:
      print("Not enough Java solutions to create the prompt.")
      continue

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

    if not os.path.exists(f"{problem_dir}/{config['solutions_selection_type']}/gen.py"):
      cost = write_test_generator(problem_dir, config['solutions_selection_type'], problem['description'], selected_solutions)
      print("Cost on API call:", cost)
      results[problem['name']]['cost'] = cost

    # Execute gen.py and write its output to a file
    if not os.path.exists(f"{problem_dir}/{config['solutions_selection_type']}_input/test_01.in"):
      subprocess.run(["python", f"{problem_dir}/{config['solutions_selection_type']}/gen.py", gpt_input_dir])

    # Find a Java solution to run
    if not os.path.exists(f"{problem_dir}/{config['solutions_selection_type']}_output/test_01.out"):
      sol_cnt = len(problem['solutions']['language'])
      flag = [False] * len(os.listdir(gpt_input_dir))
      for i in range(sol_cnt):
        if problem['solutions']['language'][i] != 4:
          continue

        class_name = compile_java(problem['solutions']['solution'][i])
        if class_name == "error":
          continue

        # Run the compiled Java solution
        for idx, test_file in enumerate(os.listdir(gpt_input_dir)):
          if flag[idx]:
            continue
          test_file_path = os.path.join(gpt_input_dir, test_file)
          output_path = os.path.join(gpt_output_dir, f"{test_file[:-3]}.out")
          with open(test_file_path, 'r') as input_file:
            try:
              run_process = subprocess.run(
                ["java", "-cp", java_solution_dir, class_name],
                stdin=input_file,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE,
                timeout=config['max_time_limit']
              )
              if run_process.returncode == 0:
                with open(output_path, 'w+') as output_file:
                  output_file.write(run_process.stdout.decode('utf-8'))
                  flag[idx] = True
              else:
                print(f"Error executing {class_name}")
            except subprocess.TimeoutExpired:
              print(f"Time Limit Exceeded for {class_name}")
        if all(flag):
          break
    
    if not os.path.exists(f"{problem_dir}/output/public_tests_01.out"):
      # Write test inputs/outputs
      for tests_type in {'public_tests', 'private_tests', 'generated_tests'}:
        # Write test inputs to file
        for test_idx, test_input in enumerate(problem[tests_type]['input'], start=1):
          file_path = os.path.join(input_dir, f"{tests_type}_{test_idx:02}.in")
          with open(file_path, 'w') as file:
            file.write(test_input)
        # Write test outputs to file
        for test_idx, test_output in enumerate(problem[tests_type]['output'], start=1):
          file_path = os.path.join(output_dir, f"{tests_type}_{test_idx:02}.out")
          with open(file_path, 'w') as file:
            file.write(test_output)

    # Iterate through the solutions
    time_limit = problem['time_limit']['seconds'] + problem['time_limit']['nanos'] / (10**9)
    print("Time Limit:", time_limit)
    print("Start Testing...")
    results[problem['name']]['solutions'] = list()
    for sol_type in ['solutions', 'incorrect_solutions']:
      sol_cnt = len(problem[sol_type]['language'])

      for i in tqdm(range(sol_cnt)):
        if not problem[sol_type]['language'][i] == 4:
          continue
        
        res = dict()
        solution_code = problem[sol_type]['solution'][i]
        res['solution_code'] = solution_code
        res['online_judge_label'] = 'correct' if sol_type == 'solutions' else 'incorrect'
        class_name = compile_java(solution_code)
        if class_name == "error":
          res['verdict'] = 'CE'
          continue
        
        # Test each input file in the input_dir directory
        # res['code_contests_tests'] = run_test(class_name, input_dir, output_dir, time_limit)
        res['gpt_generator_tests'] = run_test(class_name, gpt_input_dir, gpt_output_dir, time_limit)
        results[problem['name']]['solutions'].append(res)
    print("Finish Testing")

    with open(config['output_file'], 'w+') as file:
      file.write(json.dumps(results, indent=2))

if __name__ == '__main__':
  main()