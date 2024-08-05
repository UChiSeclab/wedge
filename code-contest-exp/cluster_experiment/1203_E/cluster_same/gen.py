
import os
import random

def generate_test_cases(directory, num_cases=10):
    os.makedirs(directory, exist_ok=True)
    
    max_n = 150000
    max_weight = 150000
    
    for i in range(1, num_cases + 1):
        filename = os.path.join(directory, f'test_{i:02d}.in')
        
        with open(filename, 'w') as f:
            n = random.randint(max_n // 2, max_n)
            weights = [random.randint(1, max_weight) for _ in range(n)]
            
            # Writing to the file
            f.write(f"{n}\n")
            f.write(' '.join(map(str, weights)) + '\n')

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) < 2:
        print("Usage: python generate_test_cases.py <directory>")
    else:
        directory = sys.argv[1]
        generate_test_cases(directory)
