
import argparse
import os
import random

def generate_test_case(n):
    a = [random.randint(1, n) for _ in range(n)]
    return f"{n}\n{' '.join(map(str, a))}\n"

def write_test_cases(directory, num_cases):
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    for i in range(1, num_cases + 1):
        n = random.randint(1, 2 * 10**5) # Random n within the constraints 1 ≤ n ≤ 2 ⋅ 10^5
        test_case = generate_test_case(n)
        file_path = os.path.join(directory, f'test_{i:02d}.in')
        with open(file_path, 'w') as f:
            f.write(test_case)
        print(f"Test case {i} written to {file_path}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Generate test cases for programming problem.')
    parser.add_argument('directory', type=str, help='The directory to write test cases into.')
    parser.add_argument('--num_cases', type=int, default=10, help='The number of test cases to generate (default: 10).')
    
    args = parser.parse_args()

    write_test_cases(args.directory, args.num_cases)
