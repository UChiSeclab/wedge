import os
import argparse
from collections import defaultdict

def count_test_files_per_problem(dir_path: str, add_generated: bool=False) -> dict:
  test_counts = {}

  for problem_name in os.listdir(dir_path):
    problem_dir = os.path.join(dir_path, problem_name)
    if not os.path.isdir(problem_dir):
      continue

    test_counter = 0
    for root, _, files in os.walk(problem_dir):
      for file_name in files:
        if file_name.startswith('public_tests_') and file_name.endswith('.in'):
          test_counter += 1
        elif file_name.startswith('private_tests_') and file_name.endswith('.in'):
          test_counter += 1
        elif add_generated and file_name.startswith('generated_tests_') and file_name.endswith('.in'):
          test_counter += 1

    if test_counter > 0:
      test_counts[problem_name] = test_counter

  return test_counts

def bucket_problem_counts(test_counts: dict[str, int]) -> dict[int, int]:
  buckets = defaultdict(int)

  for count in test_counts.values():
    if count < 100:
      bucket = (count // 10) * 10
    else:
      bucket = 100 # This represents the >=100 category
    buckets[bucket] += 1

  return buckets

def print_buckets(buckets: defaultdict(int)):
  max_bucket = max(buckets.keys())

  for i in range(0, max_bucket + 10, 10):
    if i == 100:
      print(f"Problems with >100 tests: {buckets[i]}")
    else:
      print(f"Problems with {i}..{i+9} tests: {buckets[i]}")

def main():
  parser = argparse.ArgumentParser(description="Count and bucket public/private test files for programs dataset.")
  parser.add_argument('directory', type=str, help='Path to the directory of the programs dataset')
  parser.add_argument('--include-generated-tests', action='store_true', help='Include AlphaCode generated tests in the count')

  args = parser.parse_args()
  dir_path = args.directory

  test_counts = count_test_files_per_problem(dir_path, args.include_generated_tests)

  buckets = bucket_problem_counts(test_counts)

  print_buckets(buckets)

if __name__ == "__main__":
  main()
