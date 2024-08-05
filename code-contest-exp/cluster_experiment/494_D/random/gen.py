
import os
import random

MODULO = 10**9 + 7

def generate_test_case(n, q):
    # Create a balanced binary tree
    edges = []
    for i in range(2, n + 1):
        edges.append((i // 2, i, random.randint(1, MODULO)))

    queries = []
    for _ in range(q):
        u = random.randint(1, n)
        v = random.randint(1, n)
        queries.append((u, v))
    
    return edges, queries

def write_test_case(filepath, n, edges, q, queries):
    with open(filepath, 'w') as f:
        f.write(f"{n}\n")
        for a, b, c in edges:
            f.write(f"{a} {b} {c}\n")
        f.write(f"{q}\n")
        for u, v in queries:
            f.write(f"{u} {v}\n")

def main(out_dir):
    if not os.path.exists(out_dir):
        os.makedirs(out_dir)

    num_cases = 10
    
    for i in range(1, num_cases + 1):
        # ensuring maximum vertices and maximum queries for stress testing
        n = 100000
        q = 100000
        edges, queries = generate_test_case(n, q)
        filepath = os.path.join(out_dir, f'test_{i:02}.in')
        write_test_case(filepath, n, edges, q, queries)

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 2:
        print("Usage: python generate_tests.py <output_directory>")
        sys.exit(1)
    
    out_dir = sys.argv[1]
    main(out_dir)
