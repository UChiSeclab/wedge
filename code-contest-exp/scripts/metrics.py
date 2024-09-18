import subprocess
import re
import os
import argparse

def run_command(cmd: list[str], cwd: str):
  """
  Run a command in a subprocess and display the output in real-time.

  Arguments:
    cmd (list): The command to run.
    cwd (str): The working directory.

  Returns:
    CompletedProcess: The result of the command execution.
  """
  print(f"Running command: {' '.join(cmd)}")
  process = subprocess.Popen(cmd, cwd=cwd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

  stdout_lines = []
  stderr_lines = []

  try:
    for stdout_line in iter(process.stdout.readline, ""):
      if stdout_line == '' and process.poll() is not None:
        break
      stdout_lines.append(stdout_line)
      print(stdout_line, end="")

    process.stdout.close()
    process.wait()

    stderr_lines = process.stderr.readlines()
    process.stderr.close()

    return subprocess.CompletedProcess(cmd, process.returncode, ''.join(stdout_lines), ''.join(stderr_lines))
  except Exception as e:
    process.kill()
    raise e

def compile_source(source_file: str, compiler_flags: list[str], cwd: str):
  """
  Compile the source code using the appropriate compiler (e.g., gcc, java, etc.) 
  based on the file extension.

  Arguments:
    source_file (str): The source file name.
    cwd (str): The working directory.

  Returns:
    str: The name of the compiled binary.
  """
  base_name, extension = os.path.splitext(source_file)

  if extension == '.c':
    compiler = 'gcc'
  elif extension == '.cpp':
    compiler = 'g++'
  else:
    raise ValueError('Unsupported source file extension. Please provide a .c or .cpp file.')

  binary_name = base_name
  compile_cmd = [compiler] + compiler_flags + [binary_name, source_file]

  print(f"\nCompiling {source_file} with {compiler}...")
  result = run_command(compile_cmd, cwd)
  if result.returncode != 0:
    raise RuntimeError(f"Compilation failed:\n{result.stderr}")

  return binary_name

def extract_running_time(stderr_output: str):
  """
  Extract the end-to-end running time from perf stat output.

  Arguments:
    stderr_output (str): The stderr output from the perf stat command.

  Returns:
    float or None: The elapsed time in seconds, or None if not found.
  """
  match = re.search(r'^\s*([\d\.]+)\s+seconds time elapsed', stderr_output, re.MULTILINE)
  if match:
    time_elapsed = float(match.group(1))
    return time_elapsed
  else:
    return None

def extract_instructions(stderr_output: str):
  """
  Extract the number of instructions executed from perf stat output.

  Arguments:
    stderr_output (str): The stderr output from the perf stat command.

  Returns:
    int or None: The number of instructions executed, or None if not found.
  """
  match = re.search(r'^\s*([\d,]+)\s+instructions', stderr_output, re.MULTILINE)
  if match:
    instructions = int(match.group(1).replace(',', ''))
    return instructions
  else:
    return None

def extract_peak_heap_massif(massif_out_file: str):
  """
  Extract the peak heap memory allocated from massif output file.

  Arguments:
    massif_out_file (str): The filename of the massif output.

  Returns:
    int: The peak heap memory usage in bytes.
  """
  with open(massif_out_file, 'r') as f:
    lines = f.readlines()

  peak_heap = 0
  snapshot = {}
  for line in lines:
    line = line.strip()
    if line.startswith('snapshot='):
      if snapshot:
        if 'mem_heap_B' in snapshot:
          mem_heap = int(snapshot['mem_heap_B'])
          if mem_heap > peak_heap:
            peak_heap = mem_heap
      snapshot = {}
      key, value = line.split('=')
      snapshot[key.strip()] = value.strip()
    elif '=' in line:
      key, value = line.split('=')
      snapshot[key.strip()] = value.strip()

  if snapshot:
    if 'mem_heap_B' in snapshot:
      mem_heap = int(snapshot['mem_heap_B'])
      if mem_heap > peak_heap:
        peak_heap = mem_heap
  return peak_heap

def extract_coverage_percentages(source_file: str, cwd: str):
  """
  Run gcov on the source file and extract line and branch coverage percentages.

  Arguments:
    source_file (str): The source file name.
    cwd (str): The working directory.

  Returns:
    Tuple[float, float]: A tuple containing (line_coverage_percent, branch_coverage_percent)
  """
  cmd = ['gcov', '-b', source_file]
  result = run_command(cmd, cwd)
  output = result.stdout

  lines_executed = re.search(r'Lines executed:([0-9\.]+)%', output)
  branches_executed = re.search(r'Branches executed:([0-9\.]+)%', output)

  lines_percent = float(lines_executed.group(1)) if lines_executed else None
  branches_percent = float(branches_executed.group(1)) if branches_executed else None

  return lines_percent, branches_percent

def main():
  """
  This script compiles the source code with debug and coverage flags, runs performance measurements,
  measures peak heap memory usage, and calculates code coverage percentages.

  Usage:

    python3 metrics.py --dir /home/xyz/example/ --source heapsort.c --source_args /home/xyz/example/input.txt

  """
  parser = argparse.ArgumentParser(description='Compile source code and measure performance metrics.')
  parser.add_argument('--dir', default='.', help='Working directory of the program')
  parser.add_argument('--source', required=True, help='The name of the target source code file')
  parser.add_argument('--source_args', nargs=argparse.REMAINDER, help='Arguments for the target')
  args = parser.parse_args()

  cwd = args.dir
  source_file = args.source
  source_args = args.source_args if args.source_args else []

  # Compile the source code with debug flags
  try:
    binary_name = compile_source(source_file, ["-g", "-o"], cwd)
  except Exception as e:
    print(f"Error: {e}")
    return

  binary_path = os.path.join(cwd, binary_name)
  if not os.path.exists(binary_path):
    print(f"Compiled binary not found: {binary_path}")
    return

  # Measure the total running time and number of instructions executed
  binary_cmd = [f'./{binary_name}'] + source_args
  cmd_perf = ['perf', 'stat', '-e', 'instructions', '--'] + binary_cmd
  print("\nMeasuring performance metrics...")
  result_perf = run_command(cmd_perf, cwd)

  time_elapsed = extract_running_time(result_perf.stderr)
  instructions = extract_instructions(result_perf.stderr)

  if time_elapsed is not None:
    print(f"Total running time: {time_elapsed} seconds")
  else:
    print("Could not extract running time.")

  if instructions is not None:
    print(f"Instructions executed: {instructions}\n")
  else:
    print("Could not extract instruction count.")

  # Measure Peak Heap Memory Usage with Valgrind
  massif_out_file = os.path.join(cwd, 'massif.out')
  if os.path.exists(massif_out_file):
    os.remove(massif_out_file)

  cmd_heap_usage = ['valgrind', '--tool=massif', f'--massif-out-file={massif_out_file}'] + binary_cmd
  print("Measuring Peak Heap Memory Usage...")
  result_massif = run_command(cmd_heap_usage, cwd)
  peak_heap = extract_peak_heap_massif(massif_out_file)
  print(f"Peak Heap Memory Usage: {peak_heap} bytes")

  # Compile the source code with gcog code coverage flags
  try:
    binary_name = compile_source(source_file, ['-g', '-fprofile-arcs', '-ftest-coverage', '-o'], cwd)
  except Exception as e:
    print(f"Error: {e}")
    return

  binary_path = os.path.join(cwd, binary_name)
  if not os.path.exists(binary_path):
    print(f"Compiled binary not found: {binary_path}")
    return

  # Run the program to generate coverage data
  print("\nRunning the program to generate coverage data...")
  cmd_run_program = [f'./{binary_name}'] + source_args
  result_run_program = run_command(cmd_run_program, cwd)

  # Measure code coverage with gcov
  print("Measuring code coverage with gcov...")
  try:
    line_coverage, branch_coverage = extract_coverage_percentages(source_file, cwd)
    if line_coverage is not None:
      print(f"Line Coverage: {line_coverage}%")
    else:
      print("Could not extract line coverage.")
    if branch_coverage is not None:
      print(f"Branch Coverage: {branch_coverage}%")
    else:
      print("Could not extract branch coverage.")
  except Exception as e:
    print(f"Error during coverage measurement: {e}")

if __name__ == '__main__':
  main()
