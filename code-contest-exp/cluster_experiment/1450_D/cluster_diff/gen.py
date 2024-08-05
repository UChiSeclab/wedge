
import os
import random
import sys

def generate_test_case(n, max_value):
    """Generates a random test case with n length array and values up to max_value."""
    a = [random.randint(1, max_value) for _ in range(n)]
    return a

def write_test_case(file_path, t, test_cases):
    """Writes the generated test cases to the specified file."""
    with open(file_path, 'w') as file:
        file.write(f"{t}\n")
        for a in test_cases:
            file.write(f"{len(a)}\n")
            file.write(' '.join(map(str, a)) + '\n')

def main(directory):
    # Set parameters for test case generation
    max_t = 10  # Number of test cases
    max_n = 3 * (10 ** 5)
    max_value = max_n  # Max value of elements in the array

    # Ensure the directory exists
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    for i in range(1, max_t + 1):
        # Generate the test cases
        t = 10**4  # Maximum number of test cases we want to test
        test_cases = [generate_test_case(max_n, max_value) for _ in range(t)]
        
        # Write the test cases to the file
        file_path = os.path.join(directory, f'test_{i:02}.in')
        write_test_case(file_path, t, test_cases)
        print(f"Generated {file_path} with {t} test cases")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
    else:
        main(sys.argv[1])
