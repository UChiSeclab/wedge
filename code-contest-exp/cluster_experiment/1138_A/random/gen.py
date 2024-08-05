
import os
import random
import sys

def generate_test_cases(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)

    test_case_id = 1
    n_values = [100000, 99999, 199998, 100000]  # Test different upper boundary sizes

    for n in n_values:
        # Generate varying test cases
        test_case = []

        # Test case 1: Alternating 1's and 2's
        alt_sushi = [1, 2] * (n // 2)
        if n % 2 != 0:
            alt_sushi.append(1)
        test_case.append(alt_sushi)

        # Test case 2: Large blocks of 1's then 2's
        block_sushi = [1] * (n // 2) + [2] * (n - n // 2)
        test_case.append(block_sushi)

        # Test case 3: Random large input
        random_sushi = [random.choice([1, 2]) for _ in range(n)]
        test_case.append(random_sushi)

        # Test case 4: Single switch in the middle
        single_switch = [1] * (n // 2) + [2] * (n - n // 2)
        test_case.append(single_switch)

        # Write test cases to files
        for idx, sushi in enumerate(test_case):
            filename = f"test_{test_case_id:02d}.in"
            with open(os.path.join(directory, filename), 'w') as file:
                file.write(f"{n}\n")
                file.write(" ".join(map(str, sushi)) + "\n")
            test_case_id += 1

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_test_cases.py <directory>")
        sys.exit(1)

    directory = sys.argv[1]
    generate_test_cases(directory)
