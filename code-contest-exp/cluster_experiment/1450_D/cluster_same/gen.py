
import os
import sys

def generate_test_case(num_cases, max_n, filename):
    with open(filename, 'w') as f:
        f.write(f"{num_cases}\n")
        
        for _ in range(num_cases):
            # Large value of n to stress test the algorithm
            n = max_n
            f.write(f"{n}\n")
            
            # Generate an array [1, 2, 3, ..., n]
            arr = " ".join(str(i) for i in range(1, n + 1))
            f.write(arr + "\n")

def main():
    if len(sys.argv) != 2:
        print("Usage: python generate_tests.py <output_directory>")
        sys.exit(1)

    output_directory = sys.argv[1]

    if not os.path.exists(output_directory):
        os.makedirs(output_directory)

    # Adjust the variables below as needed
    num_test_files = 5
    num_cases_per_file = 10
    max_n = 300000

    for i in range(1, num_test_files + 1):
        filename = os.path.join(output_directory, f"test_{i:02d}.in")
        generate_test_case(num_cases_per_file, max_n, filename)

if __name__ == "__main__":
    main()
