
import os
import random
import sys

def generate_test_cases(output_dir, num_cases=10):
    random.seed(0)  # Ensure reproducibility
    max_n = 100000
    max_ai = 10000

    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    for i in range(1, num_cases + 1):
        test_filename = os.path.join(output_dir, f'test_{i:02d}.in')
        with open(test_filename, 'w') as f:
            # Generate a large test case with n close to max_n and diverse k values
            n = random.randint(max_n - 1000, max_n)  # Large n close to max_n for stress testing
            k = random.randint(0, 14)
            sequence = [random.randint(0, max_ai) for _ in range(n)]

            f.write(f"{n} {k}\n")
            f.write(" ".join(map(str, sequence)) + "\n")
        print(f"Generated {test_filename}")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <output_directory>")
    else:
        output_dir = sys.argv[1]
        generate_test_cases(output_dir)
