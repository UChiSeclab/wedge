import random

def generate_test_cases(num_cases):
    max_n = 100000  # Upper limit for n as per the problem constraints
    results = [str(num_cases)]  # Start with the number of test cases
    for _ in range(num_cases):
        n = random.randint(max_n - 500, max_n)  # Choose n close to the upper limit
        k = random.randint(0, n)  # k can be anywhere from 0 to n

        # Generate a challenging sequence of games
        s = []
        if random.choice([True, False]):
            # Start with a win followed by many losses
            s.append('W')
            s.extend(['L'] * (n // 2 - 1))
            s.extend(['W', 'L'] * ((n - n // 2) // 2))
        else:
            # Alternating pattern to complicate consecutive scoring
            s.extend(['W', 'L'] * (n // 2))
        
        if len(s) < n:
            s.extend(['L'] * (n - len(s)))

        random.shuffle(s)  # Shuffle to randomize the distribution of W and L
        game_sequence = ''.join(s)
        results.append(f"{n} {k}")
        results.append(game_sequence)
    
    return results

# Usage example:
num_cases = 5  # Generate 5 test cases
test_cases = generate_test_cases(num_cases)
for case in test_cases:
    print(case)