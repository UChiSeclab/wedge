import os
import json
from pathlib import Path
import pygount
from tqdm import tqdm
from multiprocessing import Pool, cpu_count
import random
from fire import Fire
from matplotlib import pyplot as plt

from config import config
from utils import filter_problems, get_cf_problems


def get_loc_list(solution_dir, language):
    """Sum up lines of code for all solutions in the given directory."""
    loc_list = []
    if solution_dir.exists():
        for solution_file in os.listdir(solution_dir):
            file_path = solution_dir / solution_file
            analysis = pygount.SourceAnalysis.from_file(
                file_path, language, encoding="utf-8"
            )
            loc_list.append(analysis.code)
    return loc_list


def process_problem(args):
    """Process a single problem to calculate the total lines of code."""
    problem_name, problem_root_dir = args
    problem_id = problem_name.split(".")[0]
    problem_dir = problem_root_dir / problem_id

    solution_dir_cpp = problem_dir / "solutions" / "cpp"
    solution_dir_java = problem_dir / "solutions" / "java"
    solution_dir_python = problem_dir / "solutions" / "python"
    solution_dir_python3 = problem_dir / "solutions" / "python3"

    loc_list_cpp = get_loc_list(solution_dir_cpp, "cpp")
    loc_list_java = get_loc_list(solution_dir_java, "java")
    loc_list_python = get_loc_list(solution_dir_python, "python")
    loc_list_python3 = get_loc_list(solution_dir_python3, "python")

    loc_info = [loc_list_cpp, loc_list_java, loc_list_python, loc_list_python3]

    return problem_id, loc_info


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

    # plot distribution of loc
    loc_list = []
    threshold = 500
    long_loc_count = 0
    for (problem_id, loc_info) in problem_locs:
        for loc in loc_info:
            loc_list.extend(loc)
            long_loc_count += len([l for l in loc if l > threshold])
            
    print(f"long loc count: {long_loc_count}")
    print(f"total loc count: {len(loc_list)}")
    print(f"prop of long loc: {long_loc_count / len(loc_list)}")
    plt.hist(loc_list, bins=100)
    plt.xlabel("Lines of Code")
    plt.ylabel("Frequency")
    plt.title("Distribution of Lines of Code")
    plt.savefig("results/loc_distribution.png")


if __name__ == "__main__":
    Fire(main)
