
import random
import argparse
import os

def generate_test_case(index, n, m, max_distance, max_fuel_consumption, max_refuelings, directory):
    # Ensure the directory exists
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    cities = sorted(random.sample(range(1, max_distance, 2), n))
    
    with open(os.path.join(directory, f"test_{index:02}.in"), 'w') as f:
        f.write(f"{n} {m}\n")
        f.write(' '.join(map(str, cities)) + "\n")
        
        for _ in range(m):
            s = random.randint(1, n-1)
            f_city = random.randint(s+1, n)
            c = random.randint(1, max_fuel_consumption)
            r = random.randint(0, max_refuelings)
            f.write(f"{s} {f_city} {c} {r}\n")

def main():
    parser = argparse.ArgumentParser(description="Generate test cases for the problem.")
    parser.add_argument('directory', type=str, help='Directory to save the test cases.')
    args = parser.parse_args()
    directory = args.directory
    
    # Parameters for test case generation
    num_test_cases = 10
    max_n = 400
    max_m = 250000
    max_distance = 10**9
    max_fuel_consumption = 10**9
    max_refuelings = max_n
    
    for i in range(1, num_test_cases + 1):
        n = max_n
        m = max_m
        generate_test_case(i, n, m, max_distance, max_fuel_consumption, max_refuelings, directory)

if __name__ == "__main__":
    main()
