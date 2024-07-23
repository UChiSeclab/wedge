from datasets import load_from_disk
import numpy as np
import os
from tqdm import tqdm
import re
import subprocess
import time

# Load the dataset
dataset = load_from_disk("code_contests")
train_dataset = dataset['train']

# Filter for Codeforces problems
cf_dataset = train_dataset.filter(lambda example: example['source'] == 2)

# Set up a directory to store the Java files
output_dir = "./java_solutions"
os.makedirs(output_dir, exist_ok=True)

# Ensure the input directory exists
input_dir = './input'
os.makedirs(input_dir, exist_ok=True)

# Iterate through the dataset and write test inputs to files
for problem in cf_dataset:
    print(problem['name'])

