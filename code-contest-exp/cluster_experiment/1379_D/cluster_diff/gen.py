
import os
import random
import sys

def generate_input(n, h, m, k):
    '''
    Generate test input according to the given parameters.
    '''
    test_input = []
    test_input.append('{} {} {} {}'.format(n, h, m, k))
    
    for _ in range(n):
        hi = random.randint(0, h-1)
        mi = random.randint(0, m-1)
        test_input.append('{} {}'.format(hi, mi))

    return '\n'.join(test_input)

def write_testcases(directory, num_testcases, n, h, m, k):
    '''
    Write multiple test cases to files in the specified directory.
    '''
    # Ensure the directory exists
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    # Write each test case to file
    for i in range(1, num_testcases + 1):
        test_input = generate_input(n, h, m, k)
        with open(os.path.join(directory, f'test_{i:02d}.in'), 'w') as f:
            f.write(test_input)
            
if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Usage: python generate_testcases.py <output_directory>")
        sys.exit(1)
    
    output_directory = sys.argv[1]
    
    # Define parameters for the test cases
    num_testcases = 10  # Number of test cases
    max_n = 100000  # Maximum value for n (number of freight trains)
    max_h = 1000000000  # Maximum value for h (hours in the day)
    max_m = 1000000000  # Maximum value for m (minutes in the hour, must be even)
    max_k = max_m // 2  # Maximum value for k
    
    for i in range(1, num_testcases + 1):
        n = max_n
        h = max_h
        m = max_m
        k = random.randint(1, max_k)
        write_testcases(output_directory, num_testcases, n, h, m, k)
