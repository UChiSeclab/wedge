
import os
import random
import sys

def generate_test_cases(directory, num_cases=10, n_max=150000, weight_range=(1, 150000)):
    """
    This function generates test cases for a specific problem.
    Args:
    - directory: str, the directory where test cases should be saved.
    - num_cases: int, the number of test cases to generate.
    - n_max: int, the maximum value of n (number of boxers).
    - weight_range: tuple(int, int), the range of weights for the boxers.
    """
    if not os.path.exists(directory):
        os.makedirs(directory)

    for i in range(num_cases):
        file_name = f"test_{i+1:02d}.in"
        file_path = os.path.join(directory, file_name)

        # Generating a large number of boxers
        n = n_max

        if i < num_cases - 1:
            weights = [random.randint(weight_range[0], weight_range[1]) for _ in range(n)]
        else:
            # Ensuring the last test case has many consecutive weights to maximize processing time
            weights = [weight_range[0] + (x % (weight_range[1] - weight_range[0] + 1)) for x in range(n)]

        with open(file_path, 'w') as f:
            f.write(f"{n}\n")
            f.write(" ".join(map(str, weights)) + "\n")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)

    output_directory = sys.argv[1]
    generate_test_cases(output_directory)
