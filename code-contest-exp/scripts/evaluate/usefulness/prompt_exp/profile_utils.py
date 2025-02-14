from pathlib import Path
from typing import List, Dict, Tuple
import multiprocessing

from evaluate.usefulness.prompt_exp.SOAP import run_solution_multi_inputs
from selector.select_input import select_slowest_input_files, select_public_input_files
from config import config

def process_solution(q, args):
    solution_file, solution_profile_dir, input_file_list, gt_output_file_list, timeout, early_stop, include_instruction_cnt = args

    work_dir = solution_profile_dir / solution_file.stem
    work_dir.mkdir(exist_ok=True, parents=True)

    merged_script_stats, merged_line_profile_file, merged_mem_profile_file, merged_instruction_cnt, correctness = run_solution_multi_inputs(
        solution_file,
        input_file_list,
        gt_output_file_list,
        work_dir,
        timeout=timeout,
        early_stop=early_stop,
        include_instruction_cnt=include_instruction_cnt
    )

    result = (solution_file.stem.replace("_edited", "").replace("_optimized", ""), {
        "merged_script_stats": merged_script_stats,
        "merged_line_profile_file": merged_line_profile_file.as_posix() if merged_line_profile_file else "None",
        "merged_mem_profile_file": merged_mem_profile_file.as_posix() if merged_mem_profile_file else "None",
        "merged_instruction_cnt": merged_instruction_cnt,
        "correctness": "correct" if correctness else "incorrect"
    })
    q.put(result)  # Store result in queue

def profile_solutions(solution_profile_dir: Path, solution_files: List[Path], input_file_list: List[Path], gt_output_file_list: List[Path], timeout: int = 10, early_stop: bool = True, include_instruction_cnt: bool = False) -> Dict:
    """Profile the edited solutions with multiple inputs and collect correctness and performance statistics using multiprocessing."""
    profile_stats = {}

    args_list = [(solution_file, solution_profile_dir, input_file_list, gt_output_file_list, timeout, early_stop, include_instruction_cnt) for solution_file in solution_files]

    q = multiprocessing.Queue()
    processes = []

    for args in args_list:
        p = multiprocessing.Process(target=process_solution, args=(q, args))
        p.daemon = False  # Ensure process is non-daemonic
        p.start()
        processes.append(p)

    results = [q.get() for _ in processes]

    for p in processes:
        p.join()

    profile_stats = dict(results)

    return profile_stats

def get_input_output_pairs(problem_id: str, strategy: str, input_selection_type: str, suppress_warning: bool = False) -> List[Tuple[Path, Path]]:
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    if strategy == "alphacode":
        input_dir = problem_dir / "input"
        output_dir = problem_dir / "output"
    else:
        input_dir = problem_dir / strategy / "input"
        output_dir = problem_dir / strategy / "output"
    input_files = sorted(input_dir.glob("*.in"))
    input_output_pairs = []
    for input_file in input_files:
        output_file = output_dir / f"{input_file.stem}.out"
        if output_file.exists():
            input_output_pairs.append((input_file, output_file))

    if input_selection_type == "slow_5":
        slow_input_files = select_slowest_input_files(problem_id, strategy, top_k=5)
        input_output_pairs = [(input_file, output_file) for input_file, output_file in input_output_pairs if input_file in slow_input_files]
        if len(input_output_pairs) < 5:
            if not suppress_warning:
                print(f"[Warning] Only {len(input_output_pairs)} input-output pairs found for problem {problem_id}.")
    elif input_selection_type == "public_private_slow_5":
        slow_input_files = select_slowest_input_files(problem_id, strategy, top_k=1000) # select all slow inputs
        assert strategy == "alphacode", f"Public-private input selection only supported for alphacode strategy, not {strategy}"
        input_output_pairs = [(input_file, output_file) for input_file, output_file in input_output_pairs \
            if input_file in slow_input_files and (input_file.stem.startswith("public_tests_") or input_file.stem.startswith("private_tests_"))]
        input_output_pairs = input_output_pairs[:5]
        if len(input_output_pairs) < 5:
            if not suppress_warning:
                print(f"[Warning] Only {len(input_output_pairs)} input-output pairs found for problem {problem_id}.")
    elif input_selection_type == "public_5":
        assert strategy == "alphacode", f"Public input selection only supported for alphacode strategy, not {strategy}"
        slow_input_files = select_public_input_files(problem_id, top_k=5, suppress_warning=suppress_warning)
        input_output_pairs = [(input_file, output_file) for input_file, output_file in input_output_pairs if input_file in slow_input_files]
    elif input_selection_type == "all":
        # include public and private tests, exclude generated tests
        assert strategy == "alphacode", f"All input selection only supported for alphacode strategy, not {strategy}"
        input_output_pairs = [(input_file, output_file) for input_file, output_file in input_output_pairs if "generated" not in input_file.stem]
    elif input_selection_type == "all_include_generated":
        # include public and private tests, include generated tests
        assert strategy == "alphacode", f"All input selection only supported for alphacode strategy, not {strategy}"
    elif input_selection_type == "public_private_10":
        assert strategy == "alphacode", f"Public-private input selection only supported for alphacode strategy, not {strategy}"
        input_dir = Path(config["problem_root_dir"]) / problem_id / "input"
        input_files = sorted(input_dir.glob("public_tests_*.in"), key=lambda x: int(x.stem.split("_")[-1]))[:10]
        if len(input_files) < 10:
            private_input_files = sorted(input_dir.glob("private_tests_*.in"), key=lambda x: int(x.stem.split("_")[-1]))[:10-len(input_files)]
            input_files += private_input_files
        if len(input_files) < 10:
            if not suppress_warning:
                print(f"[Warning] Only {len(input_files)} public and private input files found for problem {problem_id}.")
        input_output_pairs = [(input_file, output_dir / f"{input_file.stem}.out") for input_file in input_files]
    elif input_selection_type == "none":
        input_output_pairs = []
    else:
        raise NotImplementedError(f"Input selection type {input_selection_type} not supported")

    return input_output_pairs
