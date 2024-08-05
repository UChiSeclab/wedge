
import os
import sys
import random
import string

def generate_test_case(directory, test_number, max_n):
    """
    Generates a single test case with length n and saves it as 'test_{test_number}.in' in the specified directory.
    The string consists of random lowercase letters and ensures to exploit the maximum complexity that the code can handle.
    :param directory: The directory to save the test case files
    :param test_number: The index of the test case file
    :param max_n: The maximum length of the string
    """
    filename = f"test_{test_number:02d}.in"
    filepath = os.path.join(directory, filename)
    k = 100000  # To exhaust the k value

    # Generate a random string of length max_n
    s = ''.join(random.choices(string.ascii_lowercase, k=max_n))

    with open(filepath, 'w') as file:
        file.write(f"{s}\n")
        file.write(f"{k}\n")


def generate_test_cases(directory, num_cases, max_n):
    """
    Generates multiple test cases and saves them.
    :param directory: The target directory for test cases
    :param num_cases: The number of test cases to generate
    :param max_n: The maximum length of the string
    """
    if not os.path.exists(directory):
        os.makedirs(directory)

    for i in range(num_cases):
        generate_test_case(directory, i + 1, max_n)


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)

    output_directory = sys.argv[1]
    num_test_cases = 10  # Number of test cases to generate
    max_length = 100000  # Maximum length of the string

    generate_test_cases(output_directory, num_test_cases, max_length)
    print(f"Generated {num_test_cases} test cases in directory '{output_directory}'")
