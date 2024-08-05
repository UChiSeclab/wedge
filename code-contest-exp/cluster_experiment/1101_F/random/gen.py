
import os
import sys
import random

def generate_test_case(n, m, max_distance, max_fuel_consumption, directory, index):
    # Create the directory if it does not exist
    if not os.path.exists(directory):
        os.makedirs(directory)

    filename = f"test_{index:02d}.in"

    with open(os.path.join(directory, filename), 'w') as f:
        # Write n and m
        f.write(f"{n} {m}\n")
        
        # Generate city positions in strictly increasing order
        positions = []
        current_position = 0
        for _ in range(n):
            current_position += random.randint(1, max_distance // n)
            positions.append(current_position)
        
        # Write city positions
        f.write(" ".join(map(str, positions)) + "\n")
        
        # Generate trucks
        for _ in range(m):
            start_city = random.randint(1, n-1)
            end_city = random.randint(start_city+1, n)
            fuel_consumption = random.randint(1, max_fuel_consumption)
            max_refuels = random.randint(0, n-1)
            
            # Write truck details
            f.write(f"{start_city} {end_city} {fuel_consumption} {max_refuels}\n")

def main():
    if len(sys.argv) < 2:
        print("Please specify the directory to write test cases.")
        return
    
    directory = sys.argv[1]
    
    # Parameters for the test case
    num_cases = 10  # Number of test cases
    n = 400  # maximum number of cities
    m = 250000  # maximum number of trucks
    max_distance = int(1e9)  # maximum distance value
    max_fuel_consumption = int(1e9)  # maximum fuel consumption value

    for i in range(1, num_cases + 1):
        generate_test_case(n, m, max_distance, max_fuel_consumption, directory, i)

if __name__ == "__main__":
    main()
