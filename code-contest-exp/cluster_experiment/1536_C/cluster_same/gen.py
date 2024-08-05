
import os
import random

def generate_test_case(n, filename):
    characters = ['D', 'K']
    test_case = ''.join(random.choices(characters, k=n))
    with open(filename, 'w') as f:
        f.write(f"{1}\n")  # One test case per file for simplicity
        f.write(f"{n}\n")
        f.write(f"{test_case}\n")

def main(dir_path):
    if not os.path.exists(dir_path):
        os.makedirs(dir_path)
    
    # Generate test cases focusing on upper input bounds
    test_sizes = [1000, 10000, 100000, 500000]
    for i, size in enumerate(test_sizes, 1):
        filename = os.path.join(dir_path, f'test_{i:02}.in')
        generate_test_case(size, filename)
        print(f"Generated {filename} with size {size}")

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <output_directory>")
    else:
        directory = sys.argv[1]
        main(directory)
