
import os
import random
import sys

def generate_test_case(n, k):
    """Generates a single test case with n elements and a specified k"""
    max_value = 10**4
    sequence = [random.randint(0, max_value) for _ in range(n)]
    return n, k, sequence

def write_test_case(directory, test_case_number, n, k, sequence):
    """Writes a test case to a file in the specified directory"""
    filename = os.path.join(directory, f'test_{test_case_number:02d}.in')
    with open(filename, 'w') as file:
        file.write(f'{n} {k}\n')
        file.write(' '.join(map(str, sequence)) + '\n')

def main(output_directory):
    if not os.path.exists(output_directory):
        os.makedirs(output_directory)

    test_cases = [
        (10, 1),
        (100, 2),
        (1000, 3),
        (10000, 4),
        (100000, 5),
        (100000, 0),
        (100000, 14),
    ]

    test_case_number = 1
    for count, (n, k) in enumerate(test_cases, 1):
        n, k, sequence = generate_test_case(n, k)
        write_test_case(output_directory, test_case_number, n, k, sequence)
        test_case_number += 1

    # Add some edge cases with maximum limits
    for i in range(5):
        n, k, sequence = generate_test_case(100000, random.randint(0, 14))
        write_test_case(output_directory, test_case_number, n, k, sequence)
        test_case_number += 1

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <output_directory>")
        sys.exit(1)
    
    output_directory = sys.argv[1]
    main(output_directory)
