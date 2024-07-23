import random

def generate_test_case(n):
    a = [random.randint(1, n) for _ in range(n)]
    return a

def generate_max_test_cases():
    test_cases = []
    total_n = 300000
    max_test_cases = total_n // 30000
    for _ in range(max_test_cases):
        n = 30000
        a = generate_test_case(n)
        test_cases.append((n, a))
    return test_cases

def print_test_cases(test_cases):
    print(len(test_cases))
    for n, a in test_cases:
        print(n)
        print(' '.join(map(str, a)))

def main():
    test_cases = generate_max_test_cases()
    print_test_cases(test_cases)

if __name__ == "__main__":
    main()
