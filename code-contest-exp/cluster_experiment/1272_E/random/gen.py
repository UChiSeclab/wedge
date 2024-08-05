
import os
import argparse
import random

def generate_test_cases(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    max_n = 200000  # As per constraints, n can be up to 2 * 10^5
    max_ai = max_n  # As per constraints, a_i can be up to n
    
    # Test cases
    test_cases = []

    # Generate a maximal test case
    test_case_1 = f"{max_n}\n" + ' '.join(str(random.randint(1, max_ai)) for _ in range(max_n))
    test_cases.append(test_case_1)

    # Generate test cases with specific patterns
    for length in [max_n // 2, max_n - 1, 2 * 10**4]:
        pattern = [random.randint(1, max_ai) for _ in range(length)]
        test_case = f"{length}\n" + ' '.join(map(str, pattern))
        test_cases.append(test_case)

    # Generate a test case with alternating small and large jumps to potentially deceive the solution
    test_case_3 = f"{max_n}\n" + ' '.join('1' if i % 2 == 0 else str(max_ai) for i in range(max_n))
    test_cases.append(test_case_3)

    # Save the test cases to files
    for i, test_case in enumerate(test_cases):
        test_case_path = os.path.join(directory, f"test_{i+1:02}.in")
        with open(test_case_path, 'w') as file:
            file.write(test_case)

def main():
    parser = argparse.ArgumentParser(description="Generate test cases for the specified problem.")
    parser.add_argument("directory", type=str, help="The directory to save test cases")
    args = parser.parse_args()
    
    generate_test_cases(args.directory)

if __name__ == '__main__':
    main()
