
import os
import random
import argparse

def generate_random_testcase(n):
    # Generates a random testcase based on n, ensuring maximum jumps to test performance.
    array = [random.randint(1, n) for _ in range(n)]
    return n, array

def write_testcase(directory, index, n, array):
    # Writes the generated test case to a file in the specified directory.
    filename = os.path.join(directory, f'test_{index:02d}.in')
    with open(filename, 'w') as f:
        f.write(f"{n}\n")
        f.write(" ".join(map(str, array)) + "\n")

def main(directory, num_cases=10, max_n=200000):
    # Ensures the output directory exists.
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    for i in range(1, num_cases + 1):
        n, array = generate_random_testcase(max_n)
        write_testcase(directory, i, n, array)
        print(f"Generated test case {i}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate test cases for a given problem.")
    parser.add_argument('directory', type=str, help="Directory to write the test cases.")
    args = parser.parse_args()

    main(args.directory)
