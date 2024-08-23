from typing import List
from pathlib import Path


"""for multi_solution_diff_input feedback prompt type"""
def fill_multi_slow_solutions(prompt:str, top_k_slow_solution_codes:List[str]) -> str:
    assert "<k_slowest_solutions>" in prompt
    infill = ""
    k = len(top_k_slow_solution_codes)
    for i in range(k):
        infill += f"\nSlow program #{i+1}:\n"
        infill += top_k_slow_solution_codes[i]
        infill += "\n"
    prompt = prompt.replace("<k_slowest_solutions>", infill)

    return prompt


def fill_multi_slow_solutions_feedback(prompt:str, fast_input_cov_files:List[Path], slow_input_cov_files:List[Path]):
    assert "<k_slowest_solutions_fast_coverage>" in prompt
    assert "<k_slowest_solutions_slow_coverage>" in prompt

    k = len(fast_input_cov_files)
    fast_cov_infill = ""
    slow_cov_infill = ""
    fast_input_cov_files.sort(key=lambda x: int(x.parent.name.split("_")[-1]))
    slow_input_cov_files.sort(key=lambda x: int(x.parent.name.split("_")[-1]))

    for i in range(k):
        fast_cov_infill += f"Slow program #{i+1} source with coverage and hit count under fast input data:"
        fast_cov_infill += fast_input_cov_files[i].read_text()
        fast_cov_infill += "\n"

        slow_cov_infill += f"Slow program #{i+1} source with coverage and hit count under slow input data:"
        slow_cov_infill += slow_input_cov_files[i].read_text()
        slow_cov_infill += "\n"

    prompt = prompt.replace("<k_slowest_solutions_fast_coverage>", fast_cov_infill)
    prompt = prompt.replace("<k_slowest_solutions_slow_coverage>", slow_cov_infill)

    return prompt