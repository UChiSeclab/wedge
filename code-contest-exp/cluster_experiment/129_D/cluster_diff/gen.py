
import os
import sys
import random
import string

def generate_test_cases(num_cases, max_string_length, max_k_value):
    test_cases = []

    for i in range(num_cases):
        # Generate a random string of maximum length
        length = max_string_length
        s = ''.join(random.choice(string.ascii_lowercase) for _ in range(length))
        
        # Generate maximum k value
        k = max_k_value

        test_cases.append((s, k))

    return test_cases

def write_test_cases_to_directory(test_cases, directory):
    if not os.path.exists(directory):
        os.makedirs(directory)
        
    for i, (s, k) in enumerate(test_cases):
        file_name = os.path.join(directory, f'test_{i+1:02d}.in')
        with open(file_name, 'w') as f:
            f.write(f"{s}\n")
            f.write(f"{k}\n")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Please provide the directory to save test cases.")
        sys.exit(1)
    
    directory = sys.argv[1]
    num_cases = 20
    max_string_length = 100000  # Maximum allowed length of string
    max_k_value = 100000        # Maximum allowed value of k

    test_cases = generate_test_cases(num_cases, max_string_length, max_k_value)
    write_test_cases_to_directory(test_cases, directory)
    print(f"Generated {num_cases} test cases and saved to {directory}")
