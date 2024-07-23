from datasets import load_from_disk
import numpy as np
import os
from tqdm import tqdm
import re
import subprocess
import time

from config import PROBLEM_NAME

# Load the dataset
dataset = load_from_disk("code_contests")
train_dataset = dataset['train']

# Filter for Codeforces problems
cf_dataset = train_dataset.filter(lambda example: example['name'] == PROBLEM_NAME)

# Set up a directory to store the Java files
solution_dir = "./java_solutions"
os.makedirs(solution_dir, exist_ok=True)

# Directory with input test files and expected outputs
input_dir = './input/'
output_dir = './output/'

# Remove the .java and .class files
for file in os.listdir(solution_dir):
    if file.endswith(".java") or file.endswith(".class"):
        os.remove(os.path.join(solution_dir, file))

# Initialize counters
tot = 0
AC_cnt = 0
TLE_cnt = 0
tot_time = 0

sol_type = 'incorrect_solutions'

# Iterate over the dataset
for problem in cf_dataset:
    sol_cnt = len(problem[sol_type]['language'])
    for i in tqdm(range(sol_cnt)):
        if not problem[sol_type]['language'][i] == 4:  # Assuming '4' is the code for Java
            continue
        tot += 1
        solution_text = problem[sol_type]['solution'][i]

        # Use regular expression to extract the class name from the solution text
        class_match = re.search(r"public\s+class\s+(\w+)", solution_text)
        if not class_match:
            continue
        class_name = class_match.group(1)
        if class_name != 'VK_CUP_2017_D':
            continue
        file_path = os.path.join(solution_dir, f"{class_name}.java")

        # Write the solution to a .java file
        with open(file_path, 'w') as file:
            file.write(solution_text)
        
        correct_flag = True
        # Compile the Java program
        compile_process = subprocess.run(["javac", file_path], capture_output=True)
        if compile_process.returncode == 0:
            # Test each input file in the directory
            for test_file in os.listdir(input_dir):
                if not correct_flag:
                    break
                test_file_path = os.path.join(input_dir, test_file)
                output_path = os.path.join(output_dir, f"{test_file[:-3]}.out")
                start_time = time.time()
                with open(test_file_path, 'r') as input_file:
                    try:
                        run_process = subprocess.run(
                            ["java", "-cp", solution_dir, class_name],
                            stdin=input_file,
                            stdout=subprocess.PIPE,
                            stderr=subprocess.PIPE,
                            timeout=600
                        )
                        if run_process.returncode == 0 and run_process.stdout:
                            actual_output = run_process.stdout.decode('utf-8').strip()
                            with open(output_path, 'r') as expected_file:
                                output = expected_file.read().strip()
                                if output != actual_output:
                                    correct_flag = False
                    except subprocess.TimeoutExpired:
                        correct_flag = False
                        duration = time.time() - start_time
                        print(f"Running time for {class_name} on {test_file}: {duration} seconds")
                        continue
                duration = time.time() - start_time
                print(f"Running time for {class_name} on {test_file}: {duration} seconds")
                tot_time += duration
        else:
            # print(f"Compilation error in {class_name}")
            correct_flag = False
        
        if correct_flag:
            AC_cnt += 1
            print("correct_solution")
            

print("Average time per solution:", tot_time / tot)
print("# of correct outputs:", AC_cnt)
print("# of solutions processed:", tot)
# print("# of TLE occurrences:", TLE_cnt)
