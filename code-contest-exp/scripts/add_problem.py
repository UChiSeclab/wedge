"""Initializes problem folder and input / output from alphacode datasets."""
from pathlib import Path
from tqdm import tqdm
from fire import Fire

from common import Language
from config import config
from utils import get_cf_problems, filter_problems


def main(problem_root_dir: str = config["problem_root_dir"]):
    """Inits necessary data for specified problems."""
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"]),
        filter_with_inconsistency_threshold=False,
    )

    for problem in tqdm(filtered_problems):
        problem_dir = problem_root_dir / str(problem["name"].split(".")[0])
        problem_dir.mkdir(exist_ok=True, parents=True)

        # Write problem description to file
        with open(problem_dir / "problem_statement.txt", "w", encoding="utf-8") as file:
            file.write(problem["description"])

        # Write input/output tests to file
        input_dir = problem_dir / "input"
        input_dir.mkdir(exist_ok=True, parents=True)
        output_dir = problem_dir / "output"
        output_dir.mkdir(exist_ok=True, parents=True)
        for test_type in ["public_tests", "private_tests", "generated_tests"]:
            for test_idx, test_input in enumerate(
                problem[test_type]["input"], start=1
            ):
                file_path = input_dir / f"{test_type}_{test_idx:03}.in"
                with open(file_path, "w", encoding="utf-8") as file:
                    file.write(test_input)
            for test_idx, test_output in enumerate(
                problem[test_type]["output"], start=1
            ):
                file_path = output_dir / f"{test_type}_{test_idx:03}.out"
                with open(file_path, "w", encoding="utf-8") as file:
                    file.write(test_output)

        # Write different language solutions to file
        solution_dirs = []
        for language_idx in range(5):
            solution_dir = (
                problem_dir / "solutions" / Language.idx_to_lang(language_idx)
            )
            solution_dir.mkdir(exist_ok=True, parents=True)
            solution_dirs.append(solution_dir)
        for solution_type in ["solutions", "incorrect_solutions"]:
            solution_cnt = len(problem[solution_type]["language"])
            for solution_idx in range(solution_cnt):
                language_idx = problem[solution_type]["language"][solution_idx]
                solution_dir = solution_dirs[language_idx]
                solution_code = problem[solution_type]["solution"][solution_idx]
                solution_file_path = (
                    solution_dir
                    / f"{solution_type}_{solution_idx:04}.{Language(language_idx).to_suffix()}"
                )
                with open(solution_file_path, "w", encoding="utf-8") as file:
                    file.write(solution_code)


if __name__ == "__main__":
    Fire(main)
