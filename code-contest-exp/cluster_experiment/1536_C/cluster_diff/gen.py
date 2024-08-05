
import os
import random
import sys

def generate_test_case(test_dir, index, n, pattern):
    file_path = os.path.join(test_dir, f'test_{index:02}.in')
    with open(file_path, 'w') as f:
        f.write("1\n")  # One test case
        f.write(f"{n}\n")  # Length of the string
        f.write(f"{pattern}\n")  # The string pattern
    print(f'Generated {file_path}')

def main():
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        return
    test_dir = sys.argv[1]

    try:
        os.makedirs(test_dir, exist_ok=True)
    except OSError as e:
        print(f"Error creating directory {test_dir}: {e}")
        return

    n = 5 * 10**5
    patterns = [
        'D' * n,  # All 'D's
        'K' * n,  # All 'K's
        ''.join(random.choices('DK', k=n)),  # Random mix of 'D' and 'K'
        'DK' * (n // 2),  # Alternating 'DK's
        'D' * (n // 2) + 'K' * (n // 2),  # Half 'D's then half 'K's
        'D' * (n // 2 - 1) + 'K' + 'D' * (n // 2)  # Almost half 'D's, one 'K', then half 'D's
    ]

    for idx, pattern in enumerate(patterns, start=1):
        generate_test_case(test_dir, idx, n, pattern)

if __name__ == "__main__":
    main()
