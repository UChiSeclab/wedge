import multiprocessing
from pathlib import Path
from common import Language
from tempdir import TempDir
import subprocess
import shutil
from typing import Literal
from fire import Fire
from tqdm import tqdm

from config import config
from selector.select_solution import select_solutions
from utils import get_cf_problems, filter_problems
from selector.select_input import (
    select_slow_fast_input,
    select_slow_fast_input_for_multi_solution,
    select_most_differentiating_input,
)
from prompt import PromptTemplate

FEEDBACK_COLLECTION_SCRIPT_DIR = (
    Path(__file__).parent / ".." / ".." / "feedback_collection"
).absolute()
COVERAGE_HIT_COUNT_OUTPUT_DIR = (
    Path(__file__).parent / ".." / config["coverage_hit_count_output_dir"]
).absolute()
debug = False


def get_feedback_type(experiment_name: str):
    if experiment_name == "feedback_diff_solution":
        return "diff_solution"
    elif experiment_name == "feedback_diff_input":
        return "diff_input"
    elif experiment_name == "feedback_multi_solution_diff_input":
        return "multi_solution_diff_input"
    elif experiment_name in [
        "multi_solution_diff_input",
        "time_contrast",
        "diff_solution_one_input", # no feedback, one input
        "plain_problem",
        "slow_solution",
        "random_solution",
        ]:
        raise ValueError("No feedback type")
    else:
        raise ValueError(f"Unknown experiment name: {experiment_name}")

def collect_coverage_hit_count(task):
    if len(task) == 4:
        solution_file, input_file, language, output_file = task
        output_report_file = None
    elif len(task) == 5:
        solution_file, input_file, language, output_file, output_report_file = task
    else:
        raise ValueError("Invalid task format: " + str(task))
    feedback_script_file = (
        FEEDBACK_COLLECTION_SCRIPT_DIR
        / str(language)
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
        try:
            subprocess.run(command, shell=True, check=True, timeout=90)
        except subprocess.TimeoutExpired:
            print(f"Timeout in {command}")
            return
        except subprocess.CalledProcessError:
            print(f"Error in {command}")
            return
        src_with_cov_file = list(output_dir.glob("*.cov"))[0]
        shutil.move(src_with_cov_file, output_file)

        if output_report_file:
            src_report_file = Path(work_dir) / "cobertura.xml"
            # only support cpp now
            assert language == Language.CPP, f"Unsupported language: {language}"
            # cpp: cobertura.xml
            # java: coverage.xml, xml_report.xml
            # python: coverage.xml
            shutil.move(src_report_file, output_report_file)

def process_problem(problem, experiment_name, problem_root_dir, solution_language, top_k):
    problem_id = problem["name"].split(".")[0]
    problem_dir = problem_root_dir / problem_id
    experiment_dir = problem_dir / experiment_name
    experiment_dir.mkdir(exist_ok=True, parents=True)

    solution_selection_type = PromptTemplate.get_select_solution_type(experiment_name)
    feedback_prompt_type = get_feedback_type(experiment_name)
    selected_solution_ids, _ = select_solutions(
        problem_id, problem, solution_selection_type, Language.str_to_lang(solution_language), top_k
    )

    if not selected_solution_ids:
        print(f"Not enough solutions for {problem_id}")
        return []

    tasks = []

    if feedback_prompt_type == "diff_solution":
        fast_solution_id, slow_solution_id = selected_solution_ids
        input_file = select_most_differentiating_input(problem_id, fast_solution_id, slow_solution_id)
        for solution_id in [fast_solution_id, slow_solution_id]:
            cov_hit_count_file = (
                COVERAGE_HIT_COUNT_OUTPUT_DIR
                / problem_id
                / experiment_name
                / solution_language
                / (input_file.split(".")[0])
                / ("fast_solution" if solution_id == fast_solution_id else "slow_solution")
                / f"{solution_id}.cov"
            )
            cov_hit_count_file.parent.mkdir(exist_ok=True, parents=True)
            if not cov_hit_count_file.exists():
                tasks.append((problem_dir / "solutions" / solution_language / f"{solution_id}.{Language.str_to_lang(solution_language).to_suffix()}",
                    problem_dir / "input" / input_file, Language.str_to_lang(solution_language), cov_hit_count_file))

    elif feedback_prompt_type == "diff_input":
        fast_solution_id, slow_solution_id = selected_solution_ids
        slow_input_file_name, fast_input_file_name = select_slow_fast_input(
            problem_id, slow_solution_id
        )
        for input_file_name in [slow_input_file_name, fast_input_file_name]:
            cov_hit_count_file = (
                COVERAGE_HIT_COUNT_OUTPUT_DIR
                / problem_id
                / experiment_name
                / solution_language
                / (input_file_name.split(".")[0])
                / ("fast_input" if input_file_name == fast_input_file_name else "slow_input")
                / f"{slow_solution_id}.cov"
            )
            cov_hit_count_file.parent.mkdir(exist_ok=True, parents=True)
            if not cov_hit_count_file.exists():
                tasks.append((
                    problem_dir / "solutions" / solution_language / f"{slow_solution_id}.{Language.str_to_lang(solution_language).to_suffix()}",
                    problem_dir / "input" / f"{input_file_name}",
                    Language.str_to_lang(solution_language),
                    cov_hit_count_file,
                ))

    elif feedback_prompt_type == "multi_solution_diff_input":
        slow_solution_ids = selected_solution_ids
        slow_input_file_name, fast_input_file_name = select_slow_fast_input_for_multi_solution(
            problem_id, slow_solution_ids
        )
        for input_file_name in [slow_input_file_name, fast_input_file_name]:
            for slow_idx, slow_solution_id in enumerate(slow_solution_ids, start=1):
                cov_hit_count_file = (
                    COVERAGE_HIT_COUNT_OUTPUT_DIR
                    / problem_id
                    / experiment_name
                    / solution_language
                    / (input_file_name.split(".")[0])
                    / ("slow_input" if input_file_name == slow_input_file_name else "fast_input")
                    / f"slow_solution_{slow_idx}"
                    / f"{slow_solution_id}.cov"
                )
                cov_hit_count_file.parent.mkdir(exist_ok=True, parents=True)
                if not cov_hit_count_file.exists():
                    tasks.append((
                        problem_dir / "solutions" / solution_language / f"{slow_solution_id}.{Language.str_to_lang(solution_language).to_suffix()}",
                        problem_dir / "input" / f"{input_file_name}",
                        Language.str_to_lang(solution_language),
                        cov_hit_count_file,
                    ))

    else:
        raise ValueError("Invalid feedback_prompt_type")

    return tasks

def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    solution_language: Literal["python", "cpp", "python3", "java"] = "java",
    top_k: int = None,
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    # Collect all tasks across problems
    all_tasks = []
    for problem in tqdm(filtered_problems):
        tasks = process_problem(problem, experiment_name, problem_root_dir, solution_language, top_k)
        all_tasks.extend(tasks)

    # Process all tasks in parallel
    with multiprocessing.Pool() as pool:
        pool.map(collect_coverage_hit_count, all_tasks)


if __name__ == "__main__":
    Fire(main)
