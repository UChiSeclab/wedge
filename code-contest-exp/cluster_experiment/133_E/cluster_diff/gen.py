
import os
import sys
import random

# Ensure the user passes the output directory as an argument.
if len(sys.argv) != 2:
    print("Usage: python generate_test_cases.py <output_directory>")
    sys.exit(1)

output_directory = sys.argv[1]

# Create the directory if it does not exist
if not os.path.exists(output_directory):
    os.makedirs(output_directory)

def generate_test_case(test_case_number):
    filename = f"test_{test_case_number:02d}.in"
    filepath = os.path.join(output_directory, filename)

    # Generate a command string of 100 characters (randomly 'T' or 'F')
    commands = ''.join(random.choice(['T', 'F']) for _ in range(100))
    # The number of changes (n) should be 50 in our maximum scenario
    n = 50

    with open(filepath, 'w') as f:
        f.write(f"{commands}\n")
        f.write(f"{n}\n")

# Generate a number of test cases, e.g., 10 test cases
for i in range(1, 11):
    generate_test_case(i)

print(f"Test cases generated in directory: {output_directory}")
