import random

def frequent_xor_pairs(max_val, bit_dif, count):
    """
    Find pairs of numbers that result in exactly 'bit_dif' bits different when XORed.
    :param max_val: Maximum value for array elements.
    :param bit_dif: The desired bit difference.
    :param count: Number of each number to include for frequency.
    :return: List of numbers maximizing the condition.
    """
    pairs = []
    for i in range(1, max_val + 1):
        for j in range(i, max_val + 1):
            if bin(i ^ j).count('1') == bit_dif:
                pairs.extend([i] * count)
                if i != j:
                    pairs.extend([j] * count)
    return pairs

def generate_worst_case_test(n, bit_dif, numbers):
    """
    Generate a test case that uses a precomputed list of numbers to maximize performance hit.
    :param n: Size of the array.
    :param bit_dif: Desired number of differing bits.
    :param numbers: List of precomputed numbers.
    :return: String formatted as input for the Java program.
    """
    random.shuffle(numbers)  # Shuffle to vary distribution
    if len(numbers) > n:
        numbers = numbers[:n]  # Ensure the list size matches 'n'
    else:
        # If not enough precomputed numbers, fill up with random numbers
        while len(numbers) < n:
            numbers.append(random.randint(1, 10000))
    
    result = f"{n} {bit_dif}\n" + " ".join(map(str, numbers))
    return result

# Parameters for test case generation
max_val = 10000  # Max value for elements
bit_dif = 8      # Bit difference to maximize
count = 5        # Number of times to repeat each qualifying number
n = 10000        # Array size

# Get numbers that frequently result in 'bitDif' differing bits when XORed
frequent_numbers = frequent_xor_pairs(max_val, bit_dif, count)

# Generate the test case
test_input = generate_worst_case_test(n, bit_dif, frequent_numbers)
print(test_input)
