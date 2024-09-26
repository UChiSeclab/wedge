"""Run solutions on the tests with comprehensive metrics collection and modular design."""
import os
import subprocess
import re
from pathlib import Path
import random
import json
from multiprocessing import Pool
import time
from fire import Fire
from tqdm import tqdm
import tempdir
from typing import List, Dict, Optional, Tuple

from common import Language
from config import config
from utils import get_cf_problems, filter_problems, problem_test_gen_failed


def run_command(cmd: List[str], cwd: str, input_file: Optional[str] = None) -> subprocess.CompletedProcess:
  """
  Run a command in a subprocess and display the output in real-time.

  Arguments:
    cmd (list): The command to run.
    cwd (str): The working directory.
    input_file (str, optional): Path to the input file for stdin.

  Returns:
    subprocess.CompletedProcess: The result of the command execution.
  """
  print(f"Running command: {' '.join(cmd)}, input_file: {input_file}")
  try:
    if input_file:
      with open(input_file, 'r') as f:
        process = subprocess.Popen(
          cmd,
          cwd=cwd,
          stdout=subprocess.PIPE,
          stderr=subprocess.PIPE,
          text=True,
          stdin=f
        )
    else:
      process = subprocess.Popen(
        cmd,
        cwd=cwd,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
      )

    stdout, stderr = process.communicate()
    return subprocess.CompletedProcess(cmd, process.returncode, stdout, stderr)
  except Exception as e:
    print(f"Exception during command execution: {e}")
    if 'process' in locals():
      process.kill()
    raise e


class MetricsCollector:
  def extract_running_time(self, stderr_output: str) -> Optional[float]:
    """
    Extract the end-to-end running time from perf stat output.

    Args:
      stderr_output (str): The stderr output from the perf stat command.

    Returns:
      Optional[float]: The elapsed time in seconds, or None if not found.
    """
    match = re.search(r'^\s*([\d\.]+)\s+seconds time elapsed', stderr_output, re.MULTILINE)
    if match:
      time_elapsed = float(match.group(1))
      return time_elapsed
    return None

  def extract_instructions(self, stderr_output: str) -> Optional[int]:
    """
    Extract the number of instructions executed from perf stat output.

    Args:
      stderr_output (str): The stderr output from the perf stat command.

    Returns:
      Optional[int]: The number of instructions executed, or None if not found.
    """
    match = re.search(r'^\s*([\d,]+)\s+instructions', stderr_output, re.MULTILINE)
    if match:
      instructions = int(match.group(1).replace(',', ''))
      return instructions
    return None

  def extract_peak_heap_massif(self, massif_out_file: Path) -> int:
    """
    Extract the peak heap memory allocated from massif output file.

    Args:
      massif_out_file (Path): The filename of the massif output.

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
        if 'mem_heap_B' in snapshot:
          mem_heap = int(snapshot['mem_heap_B'])
          peak_heap = max(peak_heap, mem_heap)
        snapshot = {}
      if '=' in line:
        key, value = line.split('=')
        snapshot[key.strip()] = value.strip()

    if 'mem_heap_B' in snapshot:
      mem_heap = int(snapshot['mem_heap_B'])
      peak_heap = max(peak_heap, mem_heap)

    return peak_heap

  def extract_coverage_percentages(self, source_file: str, cwd: str) -> Tuple[Optional[float], Optional[float]]:
    """
    Run gcov on the source file and extract line and branch coverage percentages.

    Args:
      source_file (str): The source file name.
      cwd (str): The working directory.

    Returns:
      Tuple[Optional[float], Optional[float]]: (line_coverage_percent, branch_coverage_percent)
    """
    cmd = ['gcov', '-b', source_file]
    result = run_command(cmd, cwd)
    print("Coverage output:")
    print(result.stdout)
    output = result.stdout

    lines_executed = re.search(r'Lines executed:([\d\.]+)%', output)
    branches_executed = re.search(r'Branches executed:([\d\.]+)%', output)

    lines_percent = float(lines_executed.group(1)) if lines_executed else None
    branches_percent = float(branches_executed.group(1)) if branches_executed else None

    return lines_percent, branches_percent


class Compiler:
  def __init__(self, tmp_dir: Path, solution_code: str, language: Language, metrics_collector: MetricsCollector):
    self.tmp_dir = tmp_dir
    self.solution_code = solution_code
    self.language = language
    self.metrics_collector = metrics_collector
    self.flags = self.initialize_flags()

  def initialize_flags(self) -> Dict[str, List[str]]:
    """
    Initialize compiler flags based on the programming language.

    Returns:
      Dict[str, List[str]]: A dictionary mapping language to its compiler flags.
    """
    flags = {
      Language.JAVA: [],
      Language.C: [
        "-Wall",
        "-Wextra",
        "-Wconversion",
        "-static",
        "-DONLINE_JUDGE",
        "-std=c11",
        "-g"
      ],
      Language.CPP: [
        "-Wall",
        "-Wextra",
        "-Wconversion",
        "-static",
        "-DONLINE_JUDGE",
        "-std=c++17",
        "-g"
      ],
      Language.PYTHON: [],
      Language.PYTHON3: []
    }
    return flags

  def compile_source(self, gcov_flags: bool = False) -> str:
    """
    Compiles the solution based on the programming language.

    Args:
      gcov_flags (bool): Whether to compile with gcov coverage flags.

    Returns:
      str: The name of the compiled executable or source file.
         Returns "Compile Error" if compilation fails.
         Returns "Judge Error" for unsupported languages or other errors.
    """
    try:
      if self.language == Language.JAVA:
        return self.compile_java()
      elif self.language == Language.C:
        return self.compile_c(gcov_flags)
      elif self.language == Language.CPP:
        return self.compile_cpp(gcov_flags)
      elif self.language in [Language.PYTHON, Language.PYTHON3]:
        return self.prepare_python()
      else:
        print(f"[Error] Unsupported language: {self.language}")
        return "Judge Error"
    except Exception as e:
      print(f"[Exception] An error occurred during compilation: {e}")
      return "Compile Error"

  def compile_java(self) -> str:
    """
    Compiles Java source code.

    Returns:
      str: The class name if compilation succeeds, otherwise "Compile Error".
    """
    class_match = None
    for regex in [r"public\s+class\s+(\w+)", r"class\s+(\w+)"]:
      class_match = re.search(regex, self.solution_code)
      if class_match:
        break
    if not class_match:
      print("[Error] Could not determine the Java class name.")
      return "Judge Error"
    class_name = class_match.group(1)
    java_file = self.tmp_dir / f"{class_name}.java"

    with open(java_file, "w", encoding="utf-8") as file:
      file.write(self.solution_code)

    compile_cmd = ["javac", str(java_file)]
    compile_result = run_command(compile_cmd, cwd=str(self.tmp_dir))

    if compile_result.returncode != 0:
      print(f"[Compile Error] Java compilation failed:\n{compile_result.stderr}")
      return "Compile Error"

    return class_name

  def compile_c(self, gcov_flags: bool) -> str:
    """
    Compiles C source code with or without gcov coverage flags.

    Args:
      gcov_flags (bool): Whether to compile with gcov coverage flags.

    Returns:
      str: The executable name if compilation succeeds, otherwise "Compile Error".
    """
    c_file = self.tmp_dir / "solution.c"
    executable = self.tmp_dir / "solution_c"

    with open(c_file, "w", encoding="utf-8") as file:
      file.write(self.solution_code)

    compiler_flags = self.flags[Language.C].copy()
    if gcov_flags:
      compiler_flags += ["-fprofile-arcs", "-ftest-coverage"]

    compile_cmd = ["gcc"] + compiler_flags + [str(c_file), "-o", str(executable)]
    compile_result = run_command(compile_cmd, cwd=str(self.tmp_dir))

    if compile_result.returncode != 0:
      print(f"[Compile Error] C compilation failed:\n{compile_result.stderr}")
      return "Compile Error"

    return "solution_c"

  def compile_cpp(self, gcov_flags: bool) -> str:
    """
    Compiles C++ source code with or without gcov coverage flags.

    Args:
      gcov_flags (bool): Whether to compile with gcov coverage flags.

    Returns:
      str: The executable name if compilation succeeds, otherwise "Compile Error".
    """
    cpp_file = self.tmp_dir / "solution.cpp"
    executable = self.tmp_dir / "solution_cpp"

    with open(cpp_file, "w", encoding="utf-8") as file:
      file.write(self.solution_code)

    compiler_flags = self.flags[Language.CPP].copy()
    if gcov_flags:
      compiler_flags += ["-fprofile-arcs", "-ftest-coverage"]

    compile_cmd = ["g++"] + compiler_flags + [str(cpp_file), "-o", str(executable)]
    compile_result = run_command(compile_cmd, cwd=str(self.tmp_dir))

    if compile_result.returncode != 0:
      print(f"[Compile Error] C++ compilation failed:\n{compile_result.stderr}")
      return "Compile Error"

    return "solution_cpp"

  def prepare_python(self) -> str:
    """
    Prepares Python scripts (no compilation needed).

    Returns:
      str: The Python script name.
    """
    python_file = self.tmp_dir / "solution.py"

    with open(python_file, "w", encoding="utf-8") as file:
      file.write(self.solution_code)

    return "solution.py"


class Executor:
  def __init__(self, tmp_dir: Path, executable_name: str, language: Language, metrics_collector: MetricsCollector):
    self.tmp_dir = tmp_dir
    self.executable_name = executable_name
    self.language = language
    self.metrics_collector = metrics_collector

  def execute(self, input_file: Path) -> Dict:
    """
    Executes the compiled binary with the given input and collects metrics.

    Args:
      input_file (Path): Path to the input file.

    Returns:
      Dict: A dictionary containing execution metrics.
    """
    metrics = {}
    try:
      if self.language == Language.JAVA:
        command = [
          "java",
          "-XX:+UseSerialGC",
          "-XX:TieredStopAtLevel=1",
          "-XX:NewRatio=5",
          "-Xms8M",
          "-Xmx2048M",
          "-Xss64M",
          "-DONLINE_JUDGE=true",
          "-cp",
          str(self.tmp_dir),
          self.executable_name,
        ]
      elif self.language == Language.CPP or self.language == Language.C:
        command = ['perf', 'stat', '-e', 'instructions', '--'] + [str(self.tmp_dir / self.executable_name)]
      elif self.language == Language.PYTHON:
        command = [
          f"/home/{os.environ.get('USER')}/miniconda3/envs/py27/bin/python",
          str(self.tmp_dir / self.executable_name),
        ]
      elif self.language == Language.PYTHON3:
        command = [
          f"/home/{os.environ.get('USER')}/miniconda3/envs/py38/bin/python",
          str(self.tmp_dir / self.executable_name),
        ]
      else:
        print(f"[Error] Unsupported language for execution: {self.language}")
        return metrics

      start_time = time.time()
      result = run_command(command, cwd=str(self.tmp_dir), input_file=str(input_file))
      end_time = time.time()
      run_time = end_time - start_time

      metrics['run-time'] = run_time

      if self.language in [Language.CPP, Language.C]:
        time_elapsed = self.metrics_collector.extract_running_time(result.stderr)
        instructions = self.metrics_collector.extract_instructions(result.stderr)
        metrics['time_elapsed'] = time_elapsed
        metrics['instructions_executed'] = instructions

      metrics['stdout'] = result.stdout

    except subprocess.TimeoutExpired:
      print("[TLE] Time Limit Exceeded")
      metrics['run-time'] = self.metrics_collector.config.get('max_time_limit', 1)
      metrics['verdict'] = "TLE"
    except Exception as e:
      print(f"[Exception] An error occurred during execution: {e}")
      metrics['verdict'] = "run_time Error"

    return metrics


class SolutionRunner:
  def __init__(self, config: Dict):
    self.config = config
    self.metrics_collector = MetricsCollector()

  def run_solution(
    self,
    experiment_name: str,
    solution_path: Path,
    language: Language,
    input_dir: Path,
    output_dir: Path,
    time_limit: float = 1.0,
    write_output: bool = False,
    include_public_private_tests_only: bool = False,
  ) -> Dict:
    """
    Runs the solution against all relevant test inputs and collects 
    performance and coverage metrics.

    Args:
      experiment_name (str): Name of the experiment.
      solution_path (Path): Path to the solution file.
      language (Language): Programming language of the solution.
      input_dir (Path): Directory containing input test files.
      output_dir (Path): Directory to store output test files.
      time_limit (float, optional): Time limit for execution.
      write_output (bool, optional): Flag to determine if outputs should be written.
      include_public_private_tests_only (bool, optional): Flag to include only specific tests.

    Returns:
      Dict: Aggregated test results and metrics.
    """
    with open(solution_path, "r", encoding="utf-8") as file:
      solution_code = file.read()

    with tempdir.TempDir() as tmp_dir_path:
      tmp_dir = Path(tmp_dir_path)

      compiler = Compiler(tmp_dir, solution_code, language, self.metrics_collector)

      # Compile code for performance measurements
      executable_perf = compiler.compile_source(gcov_flags=False)
      if executable_perf == "Compile Error":
        return {"verdict": "CE"}
      if executable_perf == "Judge Error":
        return {"verdict": "JE"}

      # Compile code for code coverage measurements
      executable_cov = compiler.compile_source(gcov_flags=True)
      if executable_cov == "Compile Error":
        return {"verdict": "CE"}
      if executable_cov == "Judge Error":
        return {"verdict": "JE"}

      # Initialize two Executors instances: one for performance and another
      # for code coverage metrics
      executors = {
        "perf_metrics": Executor(tmp_dir, executable_perf, language, self.metrics_collector),
        "cov_metrics": Executor(tmp_dir, executable_cov, language, self.metrics_collector),
      }

      max_run_time = 0
      test_cnt = 0
      total_run_time = 0
      run_time_dict = {}
      instructions_dict = {}
      time_elapsed_dict = {}
      peak_heap_dict = {}
      line_coverage_dict = {}
      branch_coverage_dict = {}
      wrong_answer_flag = False

      for input_test in os.listdir(input_dir):
        if include_public_private_tests_only and not \
        (input_test.startswith("public") or input_test.startswith("private")):
          continue
        input_path = input_dir / input_test
        output_path = output_dir / f"{input_test[:-3]}.out"
        if not input_path.is_file():
          print(f"[WARNING] {input_path} has been deleted by other processes.")
          continue
        try:
          # Iterate over each Executor (only performance and code coverage for now)
          for metric_type, executor in executors.items():
            execution_result = executor.execute(input_path)
            
            # Aggregate runtime
            runtime = execution_result.get('runtime', 0)
            max_run_time = max(max_run_time, runtime)
            total_run_time += runtime
            test_cnt += 1
            run_time_dict[input_test] = runtime

            # Collect metrics based on Executor type
            if metric_type == "perf_metrics" and language in [Language.CPP, Language.C]:
              time_elapsed = execution_result.get('time_elapsed')
              instructions = execution_result.get('instructions_executed')
              if time_elapsed is not None:
                time_elapsed_dict[input_test] = time_elapsed
              if instructions is not None:
                instructions_dict[input_test] = instructions

            elif metric_type == "cov_metrics" and language in [Language.CPP, Language.C]:
              try:
                line_coverage, branch_coverage = self.metrics_collector.extract_coverage_percentages(
                  source_file=("solution.cpp" if language == Language.CPP else "solution.c"),
                  cwd=str(tmp_dir)
                )
                if line_coverage is not None:
                  line_coverage_dict[input_test] = line_coverage
                if branch_coverage is not None:
                  branch_coverage_dict[input_test] = branch_coverage
              except Exception as e:
                print(f"[Error] Failed to collect coverage for {input_test}: {e}")

          # Save the result computed by the program
          # NOTE: Both Executor instances should produce the same result. Thus, we analyze
          #    the output of one Executor instance.
          program_output = executors["perf_metrics"].execute(input_path).get('stdout', '')
          if write_output:
            with open(output_path, "w", encoding="utf-8") as file:
              file.write(program_output)
          else:
            with open(output_path, "r", encoding="utf-8") as gt_file:
              gt_output = gt_file.read().split()
            solution_output = program_output.split()
            if not self.check_same_output(gt_output, solution_output):
              print(f"[WA] Mismatch in output for {input_test}")
              wrong_answer_flag = True

          # Collect memory stats with Valgrind (C/C++)
          if language in [Language.CPP, Language.C]:
            massif_out_file = tmp_dir / "massif.out"
            if massif_out_file.is_file():
              peak_heap = self.metrics_collector.extract_peak_heap_massif(massif_out_file)
              peak_heap_dict[input_test] = peak_heap

        except Exception as e:
          print(f"[Exception] An error occurred while running {input_test}: {e}")
          wrong_answer_flag = True

        # Early exit conditions
        if experiment_name == "alphacode" and \
        (wrong_answer_flag or max_run_time >= self.config["max_time_limit"]):
          break

    avg_run_time = 0
    if test_cnt > 0:
      avg_run_time = total_run_time / test_cnt

    test_result = {
      "verdict": "AC",
      "average_time": avg_run_time,
      "max_time": max_run_time,
      "time_dict": run_time_dict,
      "instructions_executed": instructions_dict,
      "time_elapsed": time_elapsed_dict,
      "peak_heap_memory": peak_heap_dict,
      "line_coverage": line_coverage_dict,
      "branch_coverage": branch_coverage_dict,
    }

    if wrong_answer_flag:
      test_result["verdict"] = "WA"
    elif max_run_time == self.config["max_time_limit"]:
      test_result["verdict"] = "KILL"
    elif max_run_time > time_limit:
      test_result["verdict"] = "TLE"

    return test_result

  @staticmethod
  def check_same_output(output_A: List[str], output_B: List[str]) -> bool:
    """
    Compare two lists of strings to determine if outputs are the same.

    Args:
      output_A (List[str]): Ground truth output.
      output_B (List[str]): Solution output.

    Returns:
      bool: True if outputs are the same, False otherwise.
    """
    if len(output_A) != len(output_B):
      return False
    for Ai, Bi in zip(output_A, output_B):
      if Ai == Bi:
        continue
      if Ai.lower() == "yes" and Bi.lower() == "yes":
        continue
      if Ai.lower() == "no" and Bi.lower() == "no":
        continue
      try:
        Ai_num = int(Ai)
        Bi_num = int(Bi)
        if Ai_num == Bi_num or abs(Ai_num - Bi_num) == pow(2, 32):
          continue
      except ValueError:
        pass
      try:
        Ai_num = float(Ai)
        Bi_num = float(Bi)
        if abs(Ai_num - Bi_num) > 1e-5:
          return False
      except ValueError:
        if Ai != Bi:
          return False
    return True


def input_sanitization(input_dir: Path, output_dir: Path):
  invalid_input_files = []
  for input_test in os.listdir(input_dir):
    output_path = output_dir / f"{input_test[:-3]}.out"
    if not os.path.isfile(output_path):
      invalid_input_files.append(input_test)

  for invalid_input_file in invalid_input_files:
    print(
      f"[WARNING] input file {invalid_input_file} does not have corresponding output file. The input likely runs into problems."
    )
    print(f"[WARNING] Removing invalid input file: {invalid_input_file}")
    os.remove(input_dir / invalid_input_file)


def main(
  experiment_name: str = config["experiment_name"],
  problem_root_dir: str = config["problem_root_dir"],
  result_root_dir: str = config["result_root_dir"],
  include_public_private_tests_only: bool = False,
):
  """
  Runs all solutions in the folder and collects comprehensive metrics.

  Args:
    experiment_name (str): Name of the experiment.
    problem_root_dir (str): Root directory containing all problems.
    result_root_dir (str): Directory to store result JSON files.
    include_public_private_tests_only (bool, optional): Flag to include only public/private tests.
  """
  problem_root_dir = Path(problem_root_dir)
  result_root_dir = Path(result_root_dir)
  filtered_problems = filter_problems(
    get_cf_problems(use_specified_problem=config["use_specified_problem"]),
    filter_with_inconsistency_threshold=experiment_name != "alphacode",
  )

  solution_runner = SolutionRunner(config)

  for problem in tqdm(filtered_problems, desc="Processing Problems"):
    problem_id = problem["name"].split(".")[0]

    problem_dir = Path(problem_root_dir) / str(problem_id)
    if experiment_name == "alphacode":
      experiment_dir = problem_dir
    else:
      if problem_test_gen_failed(problem_id, experiment_name):
        print(f"[INFO] Test generation failed for {experiment_name} of {problem_id}, skipping.")
        continue
      experiment_dir = problem_dir / experiment_name
      Path(result_root_dir / experiment_name).mkdir(exist_ok=True, parents=True)
    result_path = Path(result_root_dir / experiment_name / f"{problem_id}.json")
    result_path.parent.mkdir(exist_ok=True, parents=True)
    if result_path.exists():
      print(f"[INFO] {result_path} exists, skipping.")
      continue
    solution_dir = problem_dir / "solutions"
    input_dir = experiment_dir / "input"
    output_dir = experiment_dir / "output"
    time_limit = (
      problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9
    )

    problem_res = {}

    print(f"\nProcessing Problem: {problem['name']}")
    print(f"# of tests: {len(os.listdir(input_dir))}")
    input_sanitization(input_dir, output_dir)
    test_args = []
    sol_type = "solutions"
    for solution_idx, _ in enumerate(problem[sol_type]["solution"]):
      language = Language(problem[sol_type]["language"][solution_idx])
      solution_file_name = f"{sol_type}_{solution_idx:04}.{language.to_suffix()}"
      solution_path = solution_dir / language.name.lower() / solution_file_name
      for _ in range(config["repeat_test"]):
        test_args.append(
          (
            experiment_name,
            solution_path,
            language,
            input_dir,
            output_dir,
            time_limit,
            False,
            include_public_private_tests_only,
          )
        )
    random.shuffle(test_args)

    max_workers = max(1, int(0.5 * os.cpu_count()))
    with Pool(processes=max_workers) as pool:
      results = list(
        tqdm(pool.imap(solution_runner.run_solution, test_args), total=len(test_args), desc="Running Solutions")
      )

    for idx, test_arg in enumerate(test_args):
      result = results[idx]
      if not result:
        continue
      online_judge_verdict = (
        "incorrect" if "incorrect" in str(test_arg[1]) else "correct"
      )
      problem_res["time_limit"] = test_arg[5]
      solution_id = (str(test_arg[1]).split("/")[-1]).split(".")[0]
      if solution_id not in problem_res.keys():
        problem_res[solution_id] = {
          "language": test_arg[2].name.lower(),
          "online_judge_verdict": online_judge_verdict,
          "verdict": [],
          "average_time": [],
          "max_time": [],
          "time_dict": {},
          "instructions_executed": {},
          "time_elapsed": {},
          "peak_heap_memory": {},
          "line_coverage": {},
          "branch_coverage": {},
        }
      solution_res = problem_res[solution_id]
      solution_res["verdict"].append(result.get("verdict", ""))
      if result.get("verdict") in ["CE", "JE"]:
        continue
      solution_res["average_time"].append(result.get("average_time", 0))
      solution_res["max_time"].append(result.get("max_time", 0))

      for metric in ["instructions_executed", "time_elapsed", "peak_heap_memory", "line_coverage", "branch_coverage"]:
        metric_data = result.get(metric, {})
        for test_name, value in metric_data.items():
          if test_name not in solution_res[metric]:
            solution_res[metric][test_name] = []
          solution_res[metric][test_name].append(value)
      for test_name, time_val in result.get("time_dict", {}).items():
        if test_name not in solution_res["time_dict"]:
          solution_res["time_dict"][test_name] = []
        solution_res["time_dict"][test_name].append(time_val)

    with open(result_path, "w", encoding="utf-8") as file:
      json.dump(problem_res, file, indent=4)


if __name__ == "__main__":
  Fire(main)
