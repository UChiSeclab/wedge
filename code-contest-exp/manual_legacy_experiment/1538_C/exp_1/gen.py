import random

def generate_test_case(max_sum_n, max_val, num_cases):
    # List to store the sizes of arrays for each test case
    sizes = []
    remaining_n = max_sum_n
    while remaining_n > 0 and len(sizes) < num_cases:
        # Ensure that we do not exceed the number of test cases and the sum constraint
        if len(sizes) == num_cases - 1:  # Allocate remaining n to the last test case
            sizes.append(remaining_n)
        else:
            # Randomly decide the next size, ensuring at least one element per remaining test case
            next_size = random.randint(1, remaining_n - (num_cases - len(sizes) - 1))
            sizes.append(next_size)
            remaining_n -= next_size

    # Generate each test case
    print(len(sizes))
    for n in sizes:
        l = random.randint(1, max_val // 2)  # Randomly choosing l
        r = random.randint(l, max_val)  # r is always >= l
        a = [random.randint(1, max_val) for _ in range(n)]
        
        # Printing the generated test case
        print(n, l, r)
        print(' '.join(map(str, a)))

if __name__ == "__main__":
    # Set the parameters for a challenging test case
    max_sum_n = 200000  # Maximum allowed sum of n across all test cases
    max_val = 10**9  # Maximum allowed value in the array
    num_cases = 10  # Example number of test cases
    generate_test_case(max_sum_n, max_val, num_cases)
