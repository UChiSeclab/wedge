
import os
import random
import string
import sys

def generate_test_case(n, k, use_repeated_chars=False):
    """Generate a test case with a string of length n and integer k"""
    if use_repeated_chars:
        # Use a repeated pattern to create a challenging input for suffix array methods
        s = ''.join(random.choice(string.ascii_lowercase[:2]) for _ in range(n))
    else:
        s = ''.join(random.choice(string.ascii_lowercase) for _ in range(n))
    return f"{s}\n{k}\n"

def write_test_cases(directory, num_cases=10):
    # Ensure directory exists
    if not os.path.exists(directory):
        os.makedirs(directory)

    max_length = 100000  # Max length of the string as per the problem statement

    for i in range(1, num_cases + 1):
        if i <= 5:
            n = max_length
            k = random.randint(1, min(100000, (n*(n+1))//2))
            use_repeated_chars = False
        else:
            n = max_length
            k = random.randint(1, min(100000, (n*(n+1))//2))
            use_repeated_chars = True
        
        test_case = generate_test_case(n, k, use_repeated_chars)
        with open(os.path.join(directory, f"test_{i:02d}.in"), 'w') as f:
            f.write(test_case)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <output_directory>")
        sys.exit(1)

    output_directory = sys.argv[1]
    write_test_cases(output_directory)
