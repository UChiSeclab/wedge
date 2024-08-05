
import os
import random

def generate_test_case(num_trains, h_upper, minutes, min_boarding_time):
    """ Generate a single test case with given constraints """
    trains = []
    for _ in range(num_trains):
        h_i = random.randint(0, h_upper - 1)
        m_i = random.randint(0, minutes - 1)
        trains.append((h_i, m_i))
    return trains

def write_test_case(file_path, num_trains, hours, minutes, k, trains):
    """ Write a single test case to a file """
    with open(file_path, 'w') as f:
        f.write(f"{num_trains} {hours} {minutes} {k}\n")
        for train in trains:
            f.write(f"{train[0]} {train[1]}\n")

def main(output_dir):
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    # Constraints
    n_max = 100000  # max number of trains
    h_max = 10**9   # max value for hours
    m_max = 10**9   # max value for minutes
    k_max = m_max // 2  # max boarding time

    # Generate multiple test cases
    test_cases = [
        (n_max, h_max, m_max, k_max // 2),       # Upper boundary values
        (n_max, h_max // 2, m_max, k_max // 4),  # Increased hours with sizable k
    ]

    for i, (num_trains, hours, minutes, k) in enumerate(test_cases, 1):
        trains = generate_test_case(num_trains, hours, minutes, k)
        write_test_case(os.path.join(output_dir, f"test_{i:02}.in"), num_trains, hours, minutes, k, trains)

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <output_dir>")
        sys.exit(1)
    output_dir = sys.argv[1]
    main(output_dir)
