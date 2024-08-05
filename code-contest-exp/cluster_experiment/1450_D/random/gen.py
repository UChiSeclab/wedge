
import os
import random
import argparse

def generate_test_case(max_n, test_case_id, directory):
    n = max_n
    array = list(range(1, n + 1))  # generate a permutation of length n
    
    # for TLE, shuffle array to avoid best case scenarios
    random.shuffle(array)

    # Prepare the content to write
    content = f"1\n{n}\n{' '.join(map(str, array))}\n"
    
    # Write to file
    file_path = os.path.join(directory, f"test_{test_case_id:02d}.in")
    with open(file_path, 'w') as file:
        file.write(content)

def main(directory):
    max_cases = 20  # You can specify the number of test files
    max_n = 3 * 10**5  # Max input size n
    
    if not os.path.exists(directory):
        os.makedirs(directory)

    for i in range(1, max_cases + 1):
        generate_test_case(max_n, i, directory)
        print(f"Generated test case test_{i:02d}.in")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate test cases for TLE")
    parser.add_argument('directory', type=str, help='Directory to store test cases')
    
    args = parser.parse_args()
    main(args.directory)
