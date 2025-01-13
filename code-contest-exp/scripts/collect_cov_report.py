from pathlib import Path
from fire import Fire
import os
from multiprocessing import Pool
from tqdm import tqdm

from config import config
from utils import filter_problems, get_cf_problems
from common import Language
from feedback_collect import collect_coverage_hit_count # currently work for cpp only

debug = False

def collect_coverage_hit_count_wrapper(args):
    """Wrapper function to unpack arguments."""
    print("solution_file:", args[0])
    print("input_file:", args[1])
    print("src_with_cov_file", args[3])
    return collect_coverage_hit_count(args)

def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"]
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    cov_data_root_dir = Path(config["cov_data_dir"])
    cov_args = []

    for problem in filtered_problems:
        problem_id = problem["name"].split(".")[0]
        print("problem_id:", problem_id)
        problem_dir = problem_root_dir / problem_id
        if experiment_name == "alphacode":
            input_dir = problem_dir / "input"
        else:
            input_dir = problem_dir / experiment_name / "input"

        for solution_lang in [Language.CPP]:
            solutions_dir = problem_dir / "solutions" / str(solution_lang)
            for solution_file in solutions_dir.glob(f"solutions*"):
                solution_id = solution_file.stem
                for input_file in input_dir.glob("*.in"):
                    input_name = input_file.stem
                    cov_data_dir = cov_data_root_dir / experiment_name / problem_id / str(solution_lang) / solution_id / input_name
                    cov_data_dir.mkdir(parents=True, exist_ok=True)
                    src_with_cov_file = cov_data_dir / f"{solution_id}.cov"
                    cov_report_file = cov_data_dir / "coverage.xml"
                    if src_with_cov_file.exists() and cov_report_file.exists():
                        print(f"Skip: {src_with_cov_file} and {cov_report_file} already exists")
                        continue
                    cov_args.append((solution_file, input_file, solution_lang, src_with_cov_file, cov_report_file))

    max_workers = max(1, int(0.5 * os.cpu_count()))
    with Pool(processes=max_workers) as pool:
        res = list(tqdm(pool.imap_unordered(collect_coverage_hit_count_wrapper, cov_args), total=len(cov_args)))

    if debug:
        print(len(res))



if __name__ == "__main__":
    Fire(main)