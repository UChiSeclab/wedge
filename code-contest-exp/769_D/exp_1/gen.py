import random

def generate_test_cases(n_max, a_max, k_max, num_cases=5):
    test_cases = []
    
    # Case 1: Minimum values
    test_cases.append((2, 0, [0, 0]))
    
    # Case 2: Maximum values of n and ai, with k = 0
    test_cases.append((n_max, 0, [a_max] * n_max))
    
    # Case 3: Random values with maximum k
    random_n = random.randint(2, n_max)
    test_cases.append((random_n, k_max, [random.randint(0, a_max) for _ in range(random_n)]))
    
    # Case 4: All elements the same, k = 1 (should result in 0 pairs)
    test_cases.append((n_max, 1, [7] * n_max))
    
    # Case 5: Sequence in increasing order, test with small k
    test_cases.append((n_max, 1, list(range(n_max))))
    
    # Additional random cases
    for _ in range(num_cases - 5):
        rn = random.randint(2, n_max)
        rk = random.randint(0, k_max)
        ra = [random.randint(0, a_max) for _ in range(rn)]
        test_cases.append((rn, rk, ra))
    
    return test_cases

def print_test_cases(test_cases):
    for case in test_cases:
        n, k, arr = case
        print(n, k)
        print(" ".join(map(str, arr)))

# Constants based on problem constraints
N_MAX = 100000
A_MAX = 10000
K_MAX = 14

# Generate and print test cases
test_cases = generate_test_cases(N_MAX, A_MAX, K_MAX)
print_test_cases(test_cases)
