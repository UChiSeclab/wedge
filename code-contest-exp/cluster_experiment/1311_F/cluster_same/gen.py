
import os
import random
import argparse

def generate_test_cases(directory, num_cases=10, max_n=200000, max_x=100000000, max_speed=100000000):
    if not os.path.exists(directory):
        os.makedirs(directory)

    for i in range(1, num_cases + 1):
        filename = f"test_{i:02d}.in"
        filepath = os.path.join(directory, filename)

        n = max_n
        coordinates = random.sample(range(1, max_x + 1), n)
        speeds = [random.randint(-max_speed, max_speed) for _ in range(n)]

        with open(filepath, "w") as f:
            f.write(f"{n}\n")
            f.write(" ".join(map(str, coordinates)) + "\n")
            f.write(" ".join(map(str, speeds)) + "\n")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate test cases for the problem.")
    parser.add_argument("directory", type=str, help="Directory to store the generated test cases.")
    parser.add_argument("--num_cases", type=int, default=10, help="Number of test cases to generate.")
    parser.add_argument("--max_n", type=int, default=200000, help="Maximum number of points (n).")
    parser.add_argument("--max_x", type=int, default=100000000, help="Maximum coordinate value (x_i).")
    parser.add_argument("--max_speed", type=int, default=100000000, help="Maximum speed value (v_i).")

    args = parser.parse_args()

    generate_test_cases(args.directory, args.num_cases, args.max_n, args.max_x, args.max_speed)
