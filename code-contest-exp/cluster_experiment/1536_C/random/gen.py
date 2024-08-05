
import os
import sys
import random

def generate_test_cases(num_cases, max_len, out_dir):
    random.seed(42)  # Fixed seed for reproducibility

    if not os.path.exists(out_dir):
        os.makedirs(out_dir)

    for i in range(1, num_cases + 1):
        filename = os.path.join(out_dir, f"test_{i:02d}.in")
        with open(filename, 'w') as f:
            # Number of test cases
            f.write("1\n")
            # Length of the string (n)
            n = max_len
            f.write(f"{n}\n")
            # Generate a random string of length n containing 'D' and 'K'
            s = "".join(random.choice("DK") for _ in range(n))
            f.write(f"{s}\n")

def main():
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <output_directory>")
        return
    
    out_dir = sys.argv[1]

    num_cases = 100  # Number of test cases to generate
    max_len = 500000  # Maximum length of the string in each test case

    generate_test_cases(num_cases, max_len, out_dir)
    print(f"Generated test cases in directory: {out_dir}")

if __name__ == "__main__":
    main()
