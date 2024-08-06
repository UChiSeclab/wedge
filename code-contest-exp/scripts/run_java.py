import os
import subprocess
import json
import re
import time
from queue import Queue
import threading
from scripts import *
from tqdm import tqdm

def run_test(java_solution_dir, class_name, idir, odir, time_limit=1):
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
    thread = threading.Thread(target=run_single_test, args=(java_solution_dir, class_name, test_file_path, output_path, time_limit, result_queue))
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

# Worker function to run a single test
def run_single_test(java_solution_dir, class_name, test_file_path, output_path, time_limit, result_queue):
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

def compile_java(java_solution_dir, solution_code):
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

def run_java(problem, results):
  problem_dir = problem['name'].split('.')[0]
  input_dir, output_dir, gpt_input_dir, gpt_output_dir, java_solution_dir = init_folder(problem_dir)
  print("gpt_input_dir:", gpt_input_dir)
  # Find a Java solution to run
  if not os.path.exists(f"{problem_dir}/{config['solutions_selection_type']}_output/test_01.out"):
    sol_cnt = len(problem['solutions']['language'])
    flag = [False] * len(os.listdir(gpt_input_dir))
    for i in range(sol_cnt):
      if problem['solutions']['language'][i] != 4:
        continue

      class_name = compile_java(java_solution_dir, problem['solutions']['solution'][i])
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
      class_name = compile_java(java_solution_dir, solution_code)
      if class_name == "error":
        res['verdict'] = 'CE'
        continue
      
      # Test each input file in the input_dir directory
      # res['code_contests_tests'] = run_test(class_name, input_dir, output_dir, time_limit)
      res['gpt_generator_tests'] = run_test(java_solution_dir, class_name, gpt_input_dir, gpt_output_dir, time_limit)
      results[problem['name']]['solutions'].append(res)
  print("Finish Testing")

  with open(config['output_file'], 'w+') as file:
    file.write(json.dumps(results, indent=2))

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
    run_java(problem, results)

if __name__ == "__main__":
    main()