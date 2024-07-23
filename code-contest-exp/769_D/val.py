def read_input(file_path):
    """ Reads the input file and returns n, k, and the sequence as integers. """
    with open(file_path, 'r') as file:
        n, k = map(int, file.readline().strip().split())
        sequence = list(map(int, file.readline().strip().split()))
    return n, k, sequence

def validate_input(n, k, sequence):
    """ Validates the ranges of n, k, and each element in the sequence. """
    max_n = 100000  # Maximum allowable length of the sequence
    max_val = 10000  # Maximum value of integers in the sequence
    max_k = 14  # Maximum practical value for k

    if not (1 <= n <= max_n):
        raise ValueError(f"Invalid number of elements in sequence: {n} (expected 1 to {max_n})")
    if not (0 <= k <= max_k):
        raise ValueError(f"Invalid k: {k} (expected 0 to {max_k})")
    if any(x < 0 or x > max_val for x in sequence):
        raise ValueError("Sequence contains out-of-range elements.")
    if len(sequence) != n:
        raise ValueError(f"Sequence length does not match n: {len(sequence)} vs {n}")

def main(file_path):
    n, k, sequence = read_input(file_path)
    validate_input(n, k, sequence)
    print(f"Input from {file_path} is valid.")

if __name__ == "__main__":
    import sys
    file_path = sys.argv[1]
    main(file_path)
