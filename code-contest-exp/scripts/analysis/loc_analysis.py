import os
import json
from pathlib import Path
import pygount
from tqdm import tqdm
from multiprocessing import Pool, cpu_count
import random
from fire import Fire

from config import config
from utils import filter_problems, get_cf_problems


def sum_lines_of_code(solution_dir, language):
    """Sum up lines of code for all solutions in the given directory."""
    total_loc = 0
    if solution_dir.exists():
        for solution_file in os.listdir(solution_dir):
            file_path = solution_dir / solution_file
            analysis = pygount.SourceAnalysis.from_file(
                file_path, language, encoding="utf-8"
            )
            total_loc += analysis.code
    return total_loc


def process_problem(args):
    """Process a single problem to calculate the total lines of code."""
    problem_name, problem_root_dir = args
    problem_id = problem_name.split(".")[0]
    problem_dir = problem_root_dir / problem_id

    solution_dir_cpp = problem_dir / "solutions" / "cpp"
    solution_dir_java = problem_dir / "solutions" / "java"
    solution_dir_python = problem_dir / "solutions" / "python"
    solution_dir_python3 = problem_dir / "solutions" / "python3"

    total_loc = 0
    total_loc += sum_lines_of_code(solution_dir_cpp, "cpp")
    total_loc += sum_lines_of_code(solution_dir_java, "java")
    total_loc += sum_lines_of_code(solution_dir_python, "python")
    total_loc += sum_lines_of_code(solution_dir_python3, "python")

    return problem_id, total_loc


def main(problem_root_dir: str = config["problem_root_dir"], max_cpu: int = 96):
    """Calculate total lines of code for each problem and sort them."""
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(get_cf_problems())

    # Prepare arguments for parallel processing
    args = [(problem["name"], problem_root_dir) for problem in filtered_problems]

    with Pool(min(max_cpu, cpu_count())) as pool:
        problem_locs = list(
            tqdm(pool.imap(process_problem, args), total=len(filtered_problems))
        )

    # Sort problems by total lines of code
    problem_locs.sort(key=lambda x: x[1], reverse=True)

    # Output the results
    sorted_result_path = Path(f"results/loc_sorted.json")
    sorted_result_path.parent.mkdir(parents=True, exist_ok=True)
    with open(sorted_result_path, "w", encoding="utf-8") as file:
        json.dump(problem_locs, file, indent=4)

    print(f"Results saved to {sorted_result_path}")


if __name__ == "__main__":
    Fire(main)
