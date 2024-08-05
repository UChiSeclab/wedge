
import os
import random
import sys

def generate_test_case(n, x_range, v_range):
    x_values = random.sample(range(1, x_range+1), n)
    v_values = [random.randint(-v_range, v_range) for _ in range(n)]
    return x_values, v_values

def format_test_case(index, x_values, v_values):
    test_case_str = f"{index}\n"
    x_line = " ".join(map(str, x_values))
    v_line = " ".join(map(str, v_values))
    test_case_str += f"{x_line}\n{v_line}\n"
    return test_case_str

def write_test_cases(directory, num_cases, max_n, x_range, v_range):
    if not os.path.exists(directory):
        os.makedirs(directory)
        
    for i in range(1, num_cases + 1):
        n = max_n
        x_values, v_values = generate_test_case(n, x_range, v_range)
        test_case = format_test_case(n, x_values, v_values)
        file_name = os.path.join(directory, f"test_{i:02d}.in")
        with open(file_name, "w") as file:
            file.write(test_case)

def main(directory):
    # Adjust these values to create appropriate test cases
    num_cases = 5
    max_n = 200000
    x_range = 100000000
    v_range = 100000000
    
    # Optionally, random seed for reproducibility
    random.seed(42)
    
    write_test_cases(directory, num_cases, max_n, x_range, v_range)
    print("Test cases generated successfully.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)
    
    output_directory = sys.argv[1]
    main(output_directory)
