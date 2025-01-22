from pathlib import Path
import os
import sys
import subprocess
import shutil
from typing import Literal
from concurrent.futures import ProcessPoolExecutor, as_completed

from config import config
from cgig.cgig_utils import find_mutator_file, get_best_input_pair, get_problem_solution_input_pairs
from fire import Fire

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

def run_aflpp(work_dir: Path, binary_file: Path, seed_input_dir: Path, timeout: int = 60, use_custom_mutator: bool = False, mutator_type: str = None, custom_mutator_dir: Path = None) -> subprocess.CompletedProcess:
    aflpp_dir = os.environ.get("AFLPP_DIR")
    assert binary_file.exists(), "Executable does not exist"
    print(f"Fuzzing in {work_dir}")
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
                    f"Custom mutator directory {custom_mutator_dir} is not valid"
        env["AFL_CUSTOM_MUTATOR_ONLY"] = "1"
        env["AFL_PYTHON_MODULE"] = "mutator"
        env["PYTHONPATH"] = custom_mutator_dir.absolute().as_posix()

    # clear output directory
    output_dir = work_dir / f"{binary_file.stem}_{mutator_type}_output"
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
    use_custom_mutator: bool = False, mutator_type: str = None, custom_mutator_dir: Path = None) -> subprocess.CompletedProcess:
    program_dir.mkdir(parents=True, exist_ok=True)
    program_file = program_file.absolute()

    compile_result, bin_file = aflpp_compile(program_file, program_dir)
    if compile_result.returncode != 0:
        eprint(f"Compilation failed for {program_file}")
        raise ValueError(f"Compilation failed for {program_file}: {compile_result.stderr.decode()}")

    fuzz_result = run_aflpp(program_dir, bin_file, seed_input_dir, \
        timeout=timeout, use_custom_mutator=use_custom_mutator, \
            mutator_type=mutator_type, \
                custom_mutator_dir=custom_mutator_dir)
    eprint("debug stderr of program_dir", program_dir)
    eprint(fuzz_result.stderr.decode())
    if fuzz_result.returncode != 0:
        eprint(f"AFL++ failed for {program_dir}")

    return fuzz_result


def main(
    mutator_mode: str = "self_reflect_feedback",
    strategy: str = "instrument_fuzz",
    mutator_type: Literal["mutator_with_generator", "mutator_with_constraint", "mutator_with_constraint_multi", "custom_mutator"] = "custom_mutator",
):
    problem_root_dir = Path(config["problem_root_dir"])
    extracted_constraints_dir = Path(config["constraints_dir"])
    problem_solution_input_pairs = get_problem_solution_input_pairs()

    if mutator_type == "mutator_with_generator":
        mutator_gen_root_dir = Path(config["mutator_with_generator_dir"])
    elif mutator_type == "mutator_with_constraint":
        mutator_gen_root_dir = Path(config["mutator_with_constraint_dir"])
    elif mutator_type == "mutator_with_constraint_multi":
        mutator_gen_root_dir = Path(config["mutator_with_constraint_multi_dir"])
    else:
        mutator_gen_root_dir = Path(config["custom_mutators_dir"])

    if strategy == "instrument_fuzz":
        fuzz_dir = Path(config["instrument_fuzz_dir"])
    elif strategy == "raw_fuzz":
        fuzz_dir = Path(config["raw_fuzz_dir"])
    elif strategy == "constraint_guided_one_fuzz":
        fuzz_dir = Path(config["constraint_guided_one_fuzz_dir"])
    elif strategy == "constraint_guided_multi_fuzz":
        fuzz_dir = Path(config["constraint_guided_multi_fuzz_dir"])
    else:
        raise ValueError(f"Invalid strategy: {strategy}")

    # fuzz on instrumented programs
    tasks = []
    with ProcessPoolExecutor(max_workers=int(0.5 * os.cpu_count())) as executor:
        for problem_id in problem_solution_input_pairs:
            best_input_pair, solution_ids = get_best_input_pair(problem_id, problem_solution_input_pairs[problem_id])
            if not best_input_pair:
                print(f"[Warning] No input pair found for {problem_id}")
                continue
            slow_input_id, fast_input_id = best_input_pair
            # solution_id = select_first_solution(solution_ids)
            solution_id = solution_ids[0]
            program_dir = fuzz_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}"
            if strategy == "instrument_fuzz":
                program_file = extracted_constraints_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}" / "transformed_program.cpp"
            elif strategy == "raw_fuzz":
                program_file = problem_root_dir / problem_id / "solutions" / "cpp" / f"{solution_id}.cpp"
            elif strategy == "constraint_guided_one_fuzz":
                # use the original solution file for fuzzing
                program_file = problem_root_dir / problem_id / "solutions" / "cpp" / f"{solution_id}.cpp"
            else:
                raise ValueError(f"Invalid strategy: {strategy}")

            if not program_file.exists():
                print(f"{program_file} does not exist. This is not the best input pair for the problem. Skipping...")
                continue

            if strategy == "constraint_guided_one_fuzz":
                seed_input_dir = problem_root_dir / problem_id / "constraint_guided_one" / "input"
            else:
                seed_input_dir = mutator_gen_root_dir / problem_id / "seed_inputs"

            if not seed_input_dir.exists():
                print(f"[Warning] {seed_input_dir} does not exist. Skipping...")
                continue

            mutator_gen_mode_dir = mutator_gen_root_dir / problem_id / mutator_mode
            if not (mutator_gen_mode_dir / "MUTATOR_CHECK_PASS").exists():
                print(f"[Warning] {mutator_gen_mode_dir} does not have a good mutator. Skipping...")
                continue
            custom_mutator_dir = find_mutator_file(mutator_gen_mode_dir).parent.absolute()
            task = executor.submit(
                fuzz_one,
                program_dir,
                program_file,
                seed_input_dir,
                3600, # timeout=3600s
                True, # use_custom_mutator=True
                mutator_type, # mutator_type=mutator_type
                custom_mutator_dir, # custom_mutator_dir=custom_mutator_dir
            )
            tasks.append(task)

        for future in as_completed(tasks):
            print(future.result())

if __name__ == "__main__":
    Fire(main)
