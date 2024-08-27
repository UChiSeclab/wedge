"""For a given solution and an input, collect the coverage and hit count."""
from pathlib import Path
from common import Language
from tempdir import TempDir
import subprocess
import shutil
from typing import Literal
from fire import Fire
from tqdm import tqdm

from config import config
from select_solution import select_solutions
from utils import get_cf_problems, filter_problems
from select_input import (
    select_slow_fast_input,
    select_slow_fast_input_for_multi_solution,
    select_most_differentiating_input,
)
from prompt import PromptTemplate

FEEDBACK_COLLECTION_SCRIPT_DIR = (
    Path(__file__).parent / ".." / ".." / "feedback_collection"
).absolute()
COVERAGE_HIT_COUNT_OUTPUT_DIR = (
    Path(__file__).parent / ".." / "cov_hit_count"
).absolute()
debug = False


def get_feedback_type(experiment_name: str):
    if experiment_name == "feedback_diff_solution":
        return "diff_solution"
    elif experiment_name == "feedback_diff_input":
        return "diff_input"
    elif experiment_name == "feedback_multi_solution_diff_input":
        return "multi_solution_diff_input"
    elif experiment_name in ["multi_solution_diff_input", "time_contrast"]:
        raise ValueError("No feedback type")
    else:
        raise ValueError(f"Unknown experiment name: {experiment_name}")

def collect_coverage_hit_count(
    solution_file: Path, input_file: Path, Language: Language, output_file: Path
):
    print("solution_file:", solution_file)
    feedback_script_file = (
        FEEDBACK_COLLECTION_SCRIPT_DIR
        / str(Language)
        / "coverage"
        / "scripts"
        / "coverage_with_hit_count.sh"
    )
    with TempDir() as work_dir:
        if debug:
            work_dir = "./temp/" + str(work_dir)
        output_dir = Path(work_dir) / "output"
        output_dir.mkdir(parents=True)
        command = f"{feedback_script_file} {solution_file} {input_file} {work_dir} {output_dir}"
        subprocess.run(command, shell=True, check=True)
        src_with_cov_file = list(output_dir.glob("*.cov"))[0]
        shutil.move(src_with_cov_file, output_file)


def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    solution_language: Literal["python", "cpp", "python3", "java"] = "java",
    top_k: int = None,
):
    problem_root_dir = Path(problem_root_dir)
    solution_selection_type = PromptTemplate.get_select_solution_type(experiment_name)
    feedback_prompt_type = get_feedback_type(experiment_name)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]
        print("problem_id:", problem_id)
        problem_dir = problem_root_dir / problem_id
        experiment_dir = problem_dir / experiment_name
        experiment_dir.mkdir(exist_ok=True, parents=True)
        selected_solution_ids, selected_solutions = select_solutions(
            problem_id, problem, solution_selection_type, Language.str_to_lang(solution_language), top_k
        )

        if len(selected_solution_ids) < 2 or (top_k and len(selected_solution_ids) < top_k):
            print(
                f"Not enough solutions to select for {problem_id} language: {solution_language}"
            )
            continue

        if feedback_prompt_type in ["diff_solution", "diff_input"]:
            fast_solution_id, slow_solution_id = selected_solution_ids

            most_differentiating_input_file_name = select_most_differentiating_input(
                problem_id, fast_solution_id, slow_solution_id
            )

            slow_input_file_name, fast_input_file_name = select_slow_fast_input(
                problem_id, slow_solution_id
            )
        elif feedback_prompt_type == "multi_solution_diff_input":
            slow_solution_ids = selected_solution_ids
            slow_input_file_name, fast_input_file_name = select_slow_fast_input_for_multi_solution(
                problem_id, slow_solution_ids
            )

        # collect coverage and hit count
        if feedback_prompt_type == "diff_solution":
            for solution_id in [fast_solution_id, slow_solution_id]:
                cov_hit_count_file = (
                    COVERAGE_HIT_COUNT_OUTPUT_DIR
                    / problem_id
                    / experiment_name
                    / solution_language
                    / (most_differentiating_input_file_name.split(".")[0])
                    / (
                        "fast_solution"
                        if solution_id == fast_solution_id
                        else "slow_solution"
                    )
                    / f"{solution_id}.cov"
                )
                cov_hit_count_file.parent.mkdir(exist_ok=True, parents=True)
                if not cov_hit_count_file.exists():
                    collect_coverage_hit_count(
                        problem_dir
                        / "solutions"
                        / solution_language
                        / f"{solution_id}.{Language.str_to_lang(solution_language).to_suffix()}",
                        problem_dir
                        / "input"
                        / f"{most_differentiating_input_file_name}",
                        Language.str_to_lang(solution_language),
                        cov_hit_count_file,
                    )

        elif feedback_prompt_type == "diff_input":
            for input_file_name in [slow_input_file_name, fast_input_file_name]:
                cov_hit_count_file = (
                    COVERAGE_HIT_COUNT_OUTPUT_DIR
                    / problem_id
                    / experiment_name
                    / solution_language
                    / (input_file_name.split(".")[0])
                    / (
                        "fast_input"
                        if input_file_name == fast_input_file_name
                        else "slow_input"
                    )
                    / f"{slow_solution_id}.cov"
                )
                cov_hit_count_file.parent.mkdir(exist_ok=True, parents=True)
                if not cov_hit_count_file.exists():
                    collect_coverage_hit_count(
                        problem_dir
                        / "solutions"
                        / solution_language
                        / f"{slow_solution_id}.{Language.str_to_lang(solution_language).to_suffix()}",
                        problem_dir / "input" / f"{input_file_name}",
                        Language.str_to_lang(solution_language),
                        cov_hit_count_file,
                    )
                    
        elif feedback_prompt_type == "multi_solution_diff_input":
            for input_file_name in [slow_input_file_name, fast_input_file_name]:
                for slow_idx, slow_solution_id in enumerate(slow_solution_ids, start=1):
                    cov_hit_count_file = (
                        COVERAGE_HIT_COUNT_OUTPUT_DIR
                        / problem_id
                        / experiment_name
                        / solution_language
                        / (input_file_name.split(".")[0])
                        / (
                            "slow_input"
                            if input_file_name == slow_input_file_name
                            else "fast_input"
                        )
                        / f"slow_solution_{slow_idx}"
                        / f"{slow_solution_id}.cov"
                    )
                    cov_hit_count_file.parent.mkdir(exist_ok=True, parents=True)
                    if not cov_hit_count_file.exists():
                        collect_coverage_hit_count(
                            problem_dir
                            / "solutions"
                            / solution_language
                            / f"{slow_solution_id}.{Language.str_to_lang(solution_language).to_suffix()}",
                            problem_dir / "input" / f"{input_file_name}",
                            Language.str_to_lang(solution_language),
                            cov_hit_count_file,
                        )

        else:
            raise ValueError("Invalid feedback_prompt_type")


if __name__ == "__main__":
    Fire(main)
