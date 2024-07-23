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

# Ensure the output directory exists
output_dir = './output'
os.makedirs(output_dir, exist_ok=True)

# Remove the .in files
for file in os.listdir(output_dir):
    if file.endswith(".out"):
        os.remove(os.path.join(output_dir, file))

# Iterate through the dataset and write test outputs to files
for id, problem in enumerate(tqdm(cf_dataset)):
    # Public tests
    for idx, test_output in enumerate(problem['public_tests']['output'], start=1):
        file_path = os.path.join(output_dir, f"public_test_{idx:02}.out")
        with open(file_path, 'w') as file:
            file.write(test_output)
    # Private tests
    for idx, test_output in enumerate(problem['private_tests']['output'], start=1):
        file_path = os.path.join(output_dir, f"private_test_{idx:02}.out")
        with open(file_path, 'w') as file:
            file.write(test_output)
    # Generated tests
    for idx, test_output in enumerate(problem['generated_tests']['output'], start=1):
        file_path = os.path.join(output_dir, f"generated_test_{idx:02}.out")
        with open(file_path, 'w') as file:
            file.write(test_output)
