
import os
import sys
import random

def generate_test_cases(directory):
    # First, ensure the directory exists
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    # Constants from the problem statement
    MAX_N = 400
    MAX_M = 250000
    MAX_DISTANCE = 10**9
    MAX_CONSUMPTION = 10**9
    MAX_REFUELINGS = MAX_N
    
    def write_test_case(filename, n, m, distances, trucks):
        with open(filename, 'w') as f:
            f.write(f"{n} {m}\n")
            f.write(" ".join(map(str, distances)) + "\n")
            for truck in trucks:
                f.write(" ".join(map(str, truck)) + "\n")
    
    # Create a test case that aims to exhaust the code
    distances = [random.randint(1, MAX_DISTANCE) for _ in range(MAX_N)]
    distances.sort()

    trucks = []
    for _ in range(MAX_M):
        s = random.randint(1, MAX_N - 1)
        f = random.randint(s + 1, MAX_N)
        c = random.randint(1, MAX_CONSUMPTION)
        r = random.randint(0, MAX_REFUELINGS)
        trucks.append([s, f, c, r])

    # Write test cases to files in the specified directory
    for i in range(1, 11):
        filename = os.path.join(directory, f"test_{i:02d}.in")
        write_test_case(filename, MAX_N, MAX_M, distances, trucks)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
    else:
        generate_test_cases(sys.argv[1])
