
import os
import sys
import random

def generate_case(n, filename):
    """
    Generate a single test case and write to a file.
    """
    weights = [random.randint(1, 150000) for _ in range(n)]
    with open(filename, 'w') as f:
        f.write(f"{n}\n")
        f.write(" ".join(map(str, weights)) + "\n")

def generate_cases(directory, num_cases, sizes):
    """
    Generate multiple test cases and write them to the specified directory.
    """
    if not os.path.exists(directory):
        os.makedirs(directory)

    for i in range(1, num_cases + 1):
        filename = os.path.join(directory, f'test_{i:02d}.in')
        size = sizes[i % len(sizes)]
        generate_case(size, filename)
        print(f"Test case {i} with size {size} written to {filename}")

def main():
    if len(sys.argv) != 2:
        print("Usage: python generate_testcases.py <directory>")
        return

    directory = sys.argv[1]
    
    num_cases = 10  # Number of test cases to generate
    sizes = [150000] * num_cases  # List of sizes for each test case, making them maximum

    generate_cases(directory, num_cases, sizes)

if __name__ == "__main__":
    main()
