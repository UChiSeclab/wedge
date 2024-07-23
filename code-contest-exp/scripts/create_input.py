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

# Ensure the input directory exists
input_dir = './input'
os.makedirs(input_dir, exist_ok=True)

# Remove the .in files
for file in os.listdir(input_dir):
    if file.endswith(".in"):
        os.remove(os.path.join(input_dir, file))

# Iterate through the dataset and write test inputs to files
for id, problem in enumerate(tqdm(cf_dataset)):
    # Public tests
    for idx, test_input in enumerate(problem['public_tests']['input'], start=1):
        file_path = os.path.join(input_dir, f"public_test_{idx:02}.in")
        with open(file_path, 'w') as file:
            file.write(test_input)
    # Private tests
    for idx, test_input in enumerate(problem['private_tests']['input'], start=1):
        file_path = os.path.join(input_dir, f"private_test_{idx:02}.in")
        with open(file_path, 'w') as file:
            file.write(test_input)
    # Generated tests
    for idx, test_input in enumerate(problem['generated_tests']['input'], start=1):
        file_path = os.path.join(input_dir, f"generated_test_{idx:02}.in")
        with open(file_path, 'w') as file:
            file.write(test_input)
    print(problem['description'])
