from pathlib import Path
from typing import List, Dict

from evaluate.usefulness.prompt_exp.SOAP import run_solution_multi_inputs


def profile_solutions(solution_profile_dir: Path, solution_files: List[Path], input_file_list: List[Path], gt_output_file_list: List[Path], timeout: int = 10, early_stop: bool = True, include_instruction_cnt: bool = False) -> Dict:
    """profile the edited solutions with multiple inputs and collect correctness and performance statistics."""
    profile_stats = {}

    for solution_file in solution_files:
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

        profile_stats[solution_file.stem.replace("_edited", "").replace("_optimized", "")] = {
            "merged_script_stats": merged_script_stats,
            "merged_line_profile_file": merged_line_profile_file.as_posix() if merged_line_profile_file else "None",
            "merged_mem_profile_file": merged_mem_profile_file.as_posix() if merged_mem_profile_file else "None",
            "merged_instruction_cnt": merged_instruction_cnt,
            "correctness": "correct" if correctness else "incorrect"
        }

    return profile_stats