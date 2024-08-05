
import os
import sys
import random

def generate_test_case(filename, n, x_range, v_range):
    with open(filename, 'w') as f:
        f.write(f"{n}\n")
        x = random.sample(range(x_range[0], x_range[1]), n)
        v = [random.randint(v_range[0], v_range[1]) for _ in range(n)]
        f.write(" ".join(map(str, x)) + "\n")
        f.write(" ".join(map(str, v)) + "\n")

def main(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    # Generate several test cases
    num_test_cases = 10
    n_max = 2 * 10**5
    x_range = (1, 10**8)
    v_range = (-10**8, 10**8)
    
    for i in range(1, num_test_cases + 1):
        filename = os.path.join(directory, f'test_{i:02d}.in')
        n = n_max  # Use maximum size n for each test case
        print(f"Generating test case {filename} with n={n}")
        generate_test_case(filename, n, x_range, v_range)
    
if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)
    
    directory = sys.argv[1]
    main(directory)
