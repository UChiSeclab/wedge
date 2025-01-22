from pathlib import Path
from fire import Fire
import os
from multiprocessing import Pool
from tqdm import tqdm
from typing import Literal

from config import config
from utils import filter_problems, get_cf_problems
from common import Language
from feedback_collect import collect_coverage_hit_count # currently work for cpp only
from cgig.cgig_utils import get_problem_solution_input_pairs

debug = False

def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    mode: Literal["full", "input_pairs"] = "input_pairs",
    top_k: int = 10,
):
    problem_root_dir = Path(problem_root_dir)

    cov_data_root_dir = Path(config["cov_data_dir"])
    cov_args = []

    if mode == "full":
        filtered_problems = filter_problems(
            get_cf_problems(use_specified_problem=config["use_specified_problem"])
        )
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

    elif mode == "input_pairs":
        problem_solution_input_pairs = get_problem_solution_input_pairs()

        for problem_id in problem_solution_input_pairs:
            print("problem_id:", problem_id)
            problem_dir = problem_root_dir / problem_id
            solution_input_pairs = problem_solution_input_pairs[problem_id]
            num_solution_with_cov = 0
            for solution_id in solution_input_pairs:
                if num_solution_with_cov >= top_k:
                    break
                best_input_pair_id = list(solution_input_pairs[solution_id])[0]
                slow_input_id, fast_input_id = best_input_pair_id.split("@")
                solution_file = problem_dir / "solutions" / "cpp" / f"{solution_id}.cpp"

                for input_id in [slow_input_id, fast_input_id]:
                    input_file = problem_dir / "input" / input_id
                    cov_data_dir = cov_data_root_dir / experiment_name / problem_id / "cpp" / solution_id / input_id[:-3]
                    cov_data_dir.mkdir(parents=True, exist_ok=True)
                    src_with_cov_file = cov_data_dir / f"{solution_id}.cov"
                    cov_report_file = cov_data_dir / "coverage.xml"
                    if not src_with_cov_file.exists() or not cov_report_file.exists():
                        cov_args.append((solution_file, input_file, Language.CPP, src_with_cov_file, cov_report_file))
                    else:
                        print(f"Skip: {src_with_cov_file} and {cov_report_file} already exists")

                num_solution_with_cov += 1

            if num_solution_with_cov < top_k:
                print(f"Warning: {problem_id} has less than {top_k} solutions with coverage data.")

    max_workers = max(1, int(0.5 * os.cpu_count()))
    with Pool(processes=max_workers) as pool:
        res = list(tqdm(pool.imap_unordered(collect_coverage_hit_count, cov_args), total=len(cov_args)))

    if debug:
        print(len(res))



if __name__ == "__main__":
    Fire(main)