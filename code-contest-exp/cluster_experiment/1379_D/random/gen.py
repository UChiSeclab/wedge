
import os
import random

def generate_test_case(n, h, m, k):
    test_case = f"{n} {h} {m} {k}\n"
    for _ in range(n):
        h_i = random.randint(0, h-1)
        m_i = random.randint(0, m-1)
        test_case += f"{h_i} {m_i}\n"
    return test_case

def write_test_cases(directory, num_cases, n, h, m, k):
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    for i in range(1, num_cases + 1):
        test_case = generate_test_case(n, h, m, k)
        with open(os.path.join(directory, f"test_{i:02d}.in"), 'w') as f:
            f.write(test_case)

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)
    
    directory = sys.argv[1]
    
    # Parameters for maximum input sizes within the constraints
    num_cases = 10  # Number of test cases to generate
    n = 100000      # Max number of freight trains per day
    h = 1000000000  # Max number of hours in a day
    m = 1000000000  # Max number of minutes in an hour (even number)
    k = m // 2      # Max boarding time for tram (1 ≤ k ≤ m // 2)

    write_test_cases(directory, num_cases, n, h, m, k)
