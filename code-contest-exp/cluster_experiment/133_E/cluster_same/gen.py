
import os
import random

def generate_test_cases(directory, num_cases=10, max_length=100):
    if not os.path.exists(directory):
        os.makedirs(directory)

    for i in range(1, num_cases + 1):
        filename = os.path.join(directory, f'test_{i:02d}.in')
        with open(filename, 'w') as file:
            length = max_length
            n = min(50, length)

            # Generate a random sequence of 'F' and 'T'
            commands = ''.join(random.choices(['F', 'T'], k=length))
            
            file.write(f"{commands}\n")
            file.write(f"{n}\n")

            print(f"Generated {filename}")

if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(description='Generate test cases for the turtle path problem.')
    parser.add_argument('directory', type=str, help='The directory to save the test cases')
    parser.add_argument('--cases', type=int, default=10, help='The number of test cases to generate')
    parser.add_argument('--max_length', type=int, default=100, help='The maximum length of commands')
    
    args = parser.parse_args()
    
    generate_test_cases(args.directory, args.cases, args.max_length)
