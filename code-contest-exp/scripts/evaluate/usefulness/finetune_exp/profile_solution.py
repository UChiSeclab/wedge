from pathlib import Path
import json
import os
from fire import Fire
from typing import List, Dict
import tempdir
import time
import subprocess
import multiprocessing

from config import config
from utils import get_cf_problems, filter_problems, problem_to_id, run_result_exists, check_same_output
from common import Language
from run import compile_solution, parse_instruction_count_with_retry
from evaluate.usefulness.prompt_exp.profile_utils import get_input_output_pairs

ORI_SOLUTIONS_DIR = Path(config["pie_dir"]) / "ori_human_solutions"
OPTIMIZED_SOLUTIONS_DIR = Path(config["pie_dir"]) / "optimized_human_solutions" / "alphacode_none"
ORI_SOLUTION_PROFILE_EVAL_DIR = Path(config["pie_dir"]) / "ori_human_solution_profile_eval"
OPTIMIZED_SOLUTION_PROFILE_EVAL_DIR = Path(config["pie_dir"]) / "optimized_human_solution_profile_eval"


def run_cpp_solution_perf(
    solution_profile_dir: Path,
    solution_path: Path,
    input_file_list: List[Path],
    gt_output_file_list: List[Path],
    timeout: int = 30
) -> Dict:
    solution_profile_dir.mkdir(exist_ok=True, parents=True)
    exec_status_file = solution_profile_dir / "exec_status.json"
    """
    {
        "verdict": "correct" | "incorrect" | "compile_error" | "judge_error",
        "inputs": {
            "input_id": {
                "input_verdict": "correct" | "incorrect" | "timeout",
                "execution_time": float,
                "instruction_cnt": int,
                }
            }
    }
    """
    exec_status_dict = json.loads(exec_status_file.read_text()) if exec_status_file.exists() else {}
    
    if exec_status_dict.get("verdict") in ["incorrect", "compile_error", "judge_error"]:
        return exec_status_dict
    if exec_status_dict.get("verdict") == "correct" and len(exec_status_dict.get("inputs", {})) > 0:
        return exec_status_dict

    with tempdir.TempDir() as tmp_dir:
        tmp_dir = Path(tmp_dir)
        executable_name = compile_solution(tmp_dir, solution_path.read_text(), Language.CPP)
        if executable_name == "Compile Error":
            exec_status_file.write_text(json.dumps({"verdict": "compile_error"}))
            return {"verdict": "compile_error"}
        elif executable_name == "Judge Error":
            exec_status_file.write_text(json.dumps({"verdict": "judge_error"}))
            return {"verdict": "judge_error"}

        for input_file, gt_output_file in zip(input_file_list, gt_output_file_list):
            exec_status_dict["inputs"] = exec_status_dict.get("inputs", {})
            perf_stat_output_path = solution_profile_dir / f"{input_file.stem}.perf_stat"
            with open(input_file, "r", encoding="utf-8") as f:
                try:
                    command = ["perf", "stat", "-e", "instructions:u", "-x", "<PERF_SEP>", "-o", perf_stat_output_path, "timeout", str(timeout), tmp_dir / executable_name]

                    start_time = time.perf_counter()
                    run_process = subprocess.run(
                        command,
                        stdin=f,
                        stdout=subprocess.PIPE,
                        stderr=subprocess.PIPE,
                        timeout=timeout + 10,
                        check=False,
                    )

                    run_time = time.perf_counter() - start_time
                    instruction_cnt = parse_instruction_count_with_retry(perf_stat_output_path)
                    if run_process.returncode == 124:
                        exec_status_dict["inputs"][input_file.name] = {"input_verdict": "timeout", "execution_time": run_time, "instruction_cnt": instruction_cnt}
                        continue

                    if run_process.returncode != 0:
                        exec_status_dict["inputs"][input_file.name] = {"input_verdict": "incorrect", "execution_time": run_time, "instruction_cnt": instruction_cnt}
                        print(f"Error running {input_file} with {solution_path}")
                        print(run_process.stderr.decode("utf-8"))
                        continue

                    if run_process.stdout:
                        try:
                            program_output = run_process.stdout.decode("utf-8").strip().split()
                            gt_output = gt_output_file.read_text().strip().split()
                            if not check_same_output(program_output, gt_output):
                                exec_status_dict["inputs"][input_file.name] = {"input_verdict": "incorrect", "execution_time": run_time, "instruction_cnt": instruction_cnt}
                            else:
                                exec_status_dict["inputs"][input_file.name] = {"input_verdict": "correct", "execution_time": run_time, "instruction_cnt": instruction_cnt}
                        except UnicodeError:
                            print(f"Unicode error in {input_file}")
                            exec_status_dict["inputs"][input_file.name] = {"input_verdict": "incorrect", "execution_time": run_time, "instruction_cnt": instruction_cnt}
                    else:
                        print(f"Empty output for {input_file}")
                        exec_status_dict["inputs"][input_file.name] = {"input_verdict": "incorrect", "execution_time": run_time, "instruction_cnt": instruction_cnt}
                except subprocess.TimeoutExpired:
                    exec_status_dict["inputs"][input_file.name] = {"input_verdict": "timeout", "execution_time": timeout, "instruction_cnt": 0}

        exec_status_dict["verdict"] = "correct" if all(v["input_verdict"] == "correct" for v in exec_status_dict["inputs"].values()) else "incorrect"

        exec_status_file.write_text(json.dumps(exec_status_dict))

    return exec_status_dict

def process_problem(problem: Dict, input_set: str, input_selection_type: str, model_name: str):
    problem_id = problem_to_id(problem)
    if not run_result_exists(problem_id, input_set):
        print(f"[INFO] Run result does not exist for {input_set} of {problem_id}, skipping.")
        return

    ori_solution_dir = ORI_SOLUTIONS_DIR / problem_id
    ori_solution_files = sorted(ori_solution_dir.glob("*.cpp"))
    optimized_solution_dir = OPTIMIZED_SOLUTIONS_DIR / problem_id / model_name
    optimized_solution_files = sorted(optimized_solution_dir.glob("*.cpp"))

    ori_solution_profile_dir = ORI_SOLUTION_PROFILE_EVAL_DIR / f"{input_set}_{input_selection_type}" / problem_id
    optimized_solution_profile_dir = OPTIMIZED_SOLUTION_PROFILE_EVAL_DIR / f"{input_set}_{input_selection_type}" / problem_id / model_name

    input_output_pairs = get_input_output_pairs(problem_id, input_set, input_selection_type)
    input_file_list = [input_file for input_file, _ in input_output_pairs]
    gt_output_file_list = [output_file for _, output_file in input_output_pairs]

    for solution_file in ori_solution_files:
        run_cpp_solution_perf(ori_solution_profile_dir / solution_file.stem, solution_file, input_file_list, gt_output_file_list)

    for solution_file in optimized_solution_files:
        run_cpp_solution_perf(optimized_solution_profile_dir / solution_file.stem, solution_file, input_file_list, gt_output_file_list)

def main(
    input_set: str,
    input_selection_type: str,
    model_name: str, # pie-hq-selfplay-13b / pie-conditioned-13b
):
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"]),
        filter_with_inconsistency_threshold=True,
    )

    # multiprocessing
    args = [(problem, input_set, input_selection_type, model_name) for problem in filtered_problems]
    with multiprocessing.Pool(processes=int(os.cpu_count() * 0.5)) as pool:
        pool.starmap(process_problem, args)

if __name__ == '__main__':
    Fire(main)