
import os
import sys
import random

def generate_test_cases(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)
   
    max_n = 100000

    # Test case 1: Maximum size, alternating pattern
    n = max_n
    sequence = [1, 2] * (n // 2)
    if len(sequence) < n:  # if n is odd
        sequence.append(1)
    write_test_case(directory, 1, n, sequence)

    # Test case 2: Maximum size, all 1s then all 2s
    sequence = [1] * (n // 2) + [2] * (n // 2)
    write_test_case(directory, 2, n, sequence)

    # Test case 3: Maximum size, all 2s then all 1s
    sequence = [2] * (n // 3) + [1] * (n - n // 3)
    write_test_case(directory, 3, n, sequence)

    # Test case 4: Maximum size, random 1s and 2s
    sequence = [random.choice([1, 2]) for _ in range(n)]
    write_test_case(directory, 4, n, sequence)

    # Test case 5: Minimum size, alternating starting with 1
    n = 2
    sequence = [1, 2]
    write_test_case(directory, 5, n, sequence)

    # Test case 6: Edge case size, long continuous segments
    n = max_n
    sequence = ([1] * 49999 + [2] * 50001)
    write_test_case(directory, 6, n, sequence)

    print(f"Generated test cases in directory: {directory}")

def write_test_case(directory, test_case_id, n, sequence):
    file_name = f"test_{test_case_id:02}.in"
    file_path = os.path.join(directory, file_name)
    with open(file_path, 'w') as f:
        f.write(f"{n}\n")
        f.write(" ".join(map(str, sequence)) + "\n")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)
    
    directory = sys.argv[1]
    generate_test_cases(directory)
