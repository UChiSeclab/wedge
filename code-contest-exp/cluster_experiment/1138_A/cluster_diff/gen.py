
import os
import random
import sys

def generate_test_cases(output_dir, num_cases=10):
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    for i in range(num_cases):
        filename = f"test_{i+1:02d}.in"
        file_path = os.path.join(output_dir, filename)

        n = 100000  # maximum n to stress test the code
        sequence = []
        
        # We will create a large sequence that alternates frequently to create many potential subsegments
        for _ in range(n // 2):
            sequence.append(1)
            sequence.append(2)
        
        # Write the generated test case to the file
        with open(file_path, 'w') as f:
            f.write(f"{n}\n")
            f.write(' '.join(map(str, sequence)) + '\n')
        
        print(f"Generated test case {filename}")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <output_directory>")
        sys.exit(1)
    
    output_directory = sys.argv[1]
    generate_test_cases(output_directory)
