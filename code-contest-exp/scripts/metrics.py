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

def compile_source(source_file: str, cwd: str):
  """
  Compile the source code using gcc or g++ based on the file extension.

  Arguments:
   source_file (str): The source file name.
   cwd (str): The working directory.

  Returns:
   str: The name of the compiled binary.

  Raises:
   RuntimeError: If the compilation fails.
  """
  base_name, extension = os.path.splitext(source_file)
  if extension == '.c':
    compiler = 'gcc'
  elif extension == '.cpp':
    compiler = 'g++'
  else:
    raise ValueError('Unsupported source file extension. Please provide a .c or .cpp file.')

  binary_name = base_name + '.o'
  compile_cmd = [compiler, '-g', '-o', binary_name, source_file]

  print(f"\nCompiling {source_file} with {compiler}...")
  result = run_command(compile_cmd, cwd)
  if result.returncode != 0:
    raise RuntimeError(f"Compilation failed:\n{result.stderr}")

  return binary_name


def main():
  """
  Main function to execute performance measurements on a given binary.

  Ussage:
  
    python3 metrics.py --dir /home/xyz/example/ --binary myprog.c --binary_args /home/xyz/example/input.txt
  
  """
  parser = argparse.ArgumentParser(description='Compile source code and measure performance metrics.')
  parser.add_argument('--dir', default='.', help='Working directory of the program')
  parser.add_argument('--source', required=True, help='The name of the target source code file')
  parser.add_argument('--source_args', nargs=argparse.REMAINDER, help='Arguments for the target')
  args = parser.parse_args()

  cwd = args.dir
  source_base = args.source
  source_args = args.source_args if args.source_args else []

  # Compile the source code
  try:
    binary_name = compile_source(source_base, cwd)
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

  # Compute peak memory usage with Valgrind
  massif_out_file = os.path.join(cwd, 'massif.out')
  if os.path.exists(massif_out_file):
    os.remove(massif_out_file)

  cmd_heap_usage = ['valgrind', '--tool=massif', f'--massif-out-file={massif_out_file}'] + binary_cmd
  print("Measuring Peak Heap Memory Usage...")
  result_massif = run_command(cmd_heap_usage, cwd)
  peak_heap = extract_peak_heap_massif(massif_out_file)
  print(f"Peak Heap Memory Usage: {peak_heap} bytes")

if __name__ == '__main__':
  main()
