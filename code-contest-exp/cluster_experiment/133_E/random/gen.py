
import os
import random

def generate_test_cases(directory, num_cases=10):
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    # Maximum constraints to create TLE prone test cases
    max_length = 100
    max_changes = 50
    
    for i in range(num_cases):
        # Generate a random sequence of commands of length 100
        commands = ''.join(random.choice(['T', 'F']) for _ in range(max_length))
        n = max_changes  # Always set changes to 50
        
        # Create the filename
        filename = os.path.join(directory, f"test_{i+1:02d}.in")
        
        # Write the test case to the file
        with open(filename, 'w') as f:
            f.write(f"{commands}\n")
            f.write(f"{n}\n")
    
    print(f"Generated {num_cases} test cases in directory: {directory}")

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)
    
    # Directory to save the test cases
    output_directory = sys.argv[1]
    
    # Generate the test cases
    generate_test_cases(output_directory)
