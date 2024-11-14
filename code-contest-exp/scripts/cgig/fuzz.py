from pathlib import Path
import os
import sys
import subprocess
import shutil
from typing import List, Tuple
import json
from concurrent.futures import ProcessPoolExecutor, as_completed

from config import config

def eprint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)

def aflpp_compile(program_file: Path, work_dir: Path) -> subprocess.CompletedProcess:
    aflpp_dir = os.environ.get("AFLPP_DIR")
    # AFLPP_DIR must be absolute path
    if not aflpp_dir or not Path(aflpp_dir).is_dir() or not Path(aflpp_dir).is_absolute():
        raise ValueError("AFLPP_DIR environment variable is not set or is not an absolute path")
    for cpp_version in ["c++17", "c++14", "c++11"]:
        aflpp_compile_cmd = [
            f"{aflpp_dir}/afl-clang++",
            "-Wall",
            "-Wextra",
            "-Wconversion",
            "-static",
            "-DONLINE_JUDGE",
            f"-std={cpp_version}",
            "-I/usr/include/c++/11", 
            "-I/usr/include/x86_64-linux-gnu/c++/11", 
            "-L/usr/lib/gcc/x86_64-linux-gnu/11", 
            "-o", program_file.stem,
            program_file.absolute().as_posix()
        ]

        result = subprocess.run(aflpp_compile_cmd, cwd=work_dir, \
            stdout=subprocess.PIPE, stderr=subprocess.PIPE), work_dir / program_file.stem
        if result[0].returncode == 0:
            return result

    return result

def run_aflpp(work_dir: Path, binary_file: Path, seed_input_dir: Path, timeout: int = 60, use_custom_mutator: bool = False, custom_mutator_dir: Path = None) -> subprocess.CompletedProcess:
    aflpp_dir = os.environ.get("AFLPP_DIR")
    assert binary_file.exists(), "Executable does not exist"
    if not aflpp_dir or not Path(aflpp_dir).exists():
        raise ValueError("AFLPP_DIR environment variable is not set")
    env = os.environ.copy()
    # cd /sys/devices/system/cpu
    # echo performance | tee cpu*/cpufreq/scaling_governor
    # if the above is done then we don't need the below env variable
    # env["AFL_SKIP_CPUFREQ"] = "1"
    if use_custom_mutator:
        assert custom_mutator_dir is not None \
            and custom_mutator_dir.is_dir() \
                and (custom_mutator_dir / "mutator.py").exists(), \
                    "Custom mutator directory is not valid"
        env["AFL_CUSTOM_MUTATOR_ONLY"] = "1"
        env["AFL_PYTHON_MODULE"] = "mutator"
        env["PYTHONPATH"] = custom_mutator_dir.absolute().as_posix()

    # clear output directory
    output_dir = work_dir / f"{binary_file.stem}_output"
    shutil.rmtree(output_dir, ignore_errors=True)

    aflpp_fuzz_cmd = [
        f"{aflpp_dir}/afl-fuzz", 
        "-i", seed_input_dir.absolute().as_posix(), 
        "-o", output_dir.absolute().as_posix(),
        "-V", str(timeout),
        "-t", "20000", # 20s timeout for each run
        "--", binary_file.absolute().as_posix()
    ]

    # add timeout to the command
    return subprocess.run(aflpp_fuzz_cmd, env=env, cwd=work_dir, \
        stdout=subprocess.PIPE, stderr=subprocess.PIPE, timeout=(timeout + 30))

def fuzz_one(program_dir: Path, program_file: Path, seed_input_dir: Path, timeout: int = 60, \
    use_custom_mutator: bool = False, custom_mutator_dir: Path = None) -> subprocess.CompletedProcess:
    fuzz_dir = program_dir / "fuzz"
    fuzz_dir.mkdir(parents=True, exist_ok=True)

    compile_result, bin_file = aflpp_compile(program_file, fuzz_dir)
    if compile_result.returncode != 0:
        eprint(f"Compilation failed for {program_file}")
        raise ValueError(f"Compilation failed for {program_file}: {compile_result.stderr.decode()}")

    fuzz_result = run_aflpp(fuzz_dir, bin_file, seed_input_dir, \
        timeout=timeout, use_custom_mutator=use_custom_mutator, \
            custom_mutator_dir=custom_mutator_dir)
    eprint("debug stderr:")
    eprint(fuzz_result.stderr.decode())
    if fuzz_result.returncode != 0:
        eprint(f"AFL++ failed for {program_dir}")

    return fuzz_result


if __name__ == "__main__":
    problem_root_dir = Path(config["problem_root_dir"])
    input_pairs_dir = Path(config["input_pairs_dir"])
    extracted_constraints_dir = Path(config["constraints_dir"])
    input_pairs_file = input_pairs_dir / "content_coverage_similar_problem_solution_input_pairs.json"
    problem_solution_input_pairs = json.loads(input_pairs_file.read_text())
    custom_mutators_root_dir = Path(config["custom_mutators_dir"])

    tasks = []
    with ProcessPoolExecutor(max_workers=int(0.5 * os.cpu_count())) as executor:
        for problem_id in problem_solution_input_pairs:
            for solution_id in problem_solution_input_pairs[problem_id]:
                for slow_input_id, fast_input_id in problem_solution_input_pairs[problem_id][solution_id]:
                    program_dir = extracted_constraints_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}"
                    program_file = program_dir / "transformed_program.cpp"
                    seed_input_dir = custom_mutators_root_dir / problem_id / "seed_inputs"
                    custom_mutator_dir=custom_mutators_root_dir / problem_id / "try_0"
                    task = executor.submit(
                        fuzz_one, 
                        program_dir,
                        program_file,
                        seed_input_dir,
                        60, # timeout=60
                        True, # use_custom_mutator=True
                        custom_mutator_dir, # custom_mutator_dir=custom_mutator_dir
                    )
                    tasks.append(task)

        for future in as_completed(tasks):
            print(future.result())
