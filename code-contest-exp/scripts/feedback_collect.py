"""For a given solution and an input, collect the coverage and hit count."""
from pathlib import Path
from common import Language
from tempdir import TempDir
import subprocess
import shutil
from typing import Literal, Dict, List, Tuple
import json
from fire import Fire
from tqdm import tqdm

from config import config
from gen_tests import select_solutions
from utils import get_cf_problems, filter_problems

FEEDBACK_COLLECTION_SCRIPT_DIR = (
    Path(__file__).parent / ".." / ".." / "feedback_collection"
).absolute()
COVERAGE_HIT_COUNT_OUTPUT_DIR = (
    Path(__file__).parent / ".." / "cov_hit_count"
).absolute()
debug = False


def __squeeze_time_dict(
    time_dict: Dict[str, List[float]], use_max_or_avg: Literal["max", "avg"]
):
    result = {}
    for input_name, time_list in time_dict.items():
        if use_max_or_avg == "max":
            result[input_name] = max(time_list)
        elif use_max_or_avg == "avg":
            result[input_name] = sum(time_list) / len(time_list)

    return result


def __filter_input(slow_time_stat: Dict[str, float], fast_time_stat: Dict[str, float]):
    """there might be some corner cases where the input is not present in both solutions"""
    """we only include existing inputs"""
    only_in_slow = set(slow_time_stat.keys()) - set(fast_time_stat.keys())
    only_in_fast = set(fast_time_stat.keys()) - set(slow_time_stat.keys())
    if debug and only_in_slow:
        print("only in slow inputs:", only_in_slow)
    if debug and only_in_fast:
        print("only in fast inputs:", only_in_fast)

    return {k: v for k, v in slow_time_stat.items() if k in fast_time_stat}


def select_slow_fast_input(
    solutions_stat_file: Path,
    solution_id: str,
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> Tuple[str, str]:
    data = json.loads(solutions_stat_file.read_text())
    solution_stat = data[solution_id]
    time_stat = __squeeze_time_dict(solution_stat["time_dict"], use_max_or_avg)
    slow_input = min(time_stat, key=lambda x: time_stat[x])
    fast_input = max(time_stat, key=lambda x: time_stat[x])

    return slow_input, fast_input


def select_most_differentiating_input(
    solutions_stat_file: Path,
    fast_solution_id: str,
    slow_solution_id: str,
    use_max_or_avg: Literal["max", "avg"] = "avg",
) -> str:
    # To discuss with Casper: we may need to focus on the existing inputs (not generated ones)
    data = json.loads(solutions_stat_file.read_text())
    slow_solution_stat, fast_solution_stat = (
        data[slow_solution_id],
        data[fast_solution_id],
    )
    slow_time_stat, fast_time_stat = (
        slow_solution_stat["time_dict"],
        fast_solution_stat["time_dict"],
    )
    slow_time_stat = __squeeze_time_dict(slow_time_stat, use_max_or_avg)
    fast_time_stat = __squeeze_time_dict(fast_time_stat, use_max_or_avg)
    most_differentiating_input = max(
        __filter_input(slow_time_stat, fast_time_stat),
        key=lambda x: abs(slow_time_stat[x] - fast_time_stat[x]),
    )

    # select the input that has the largest difference across different solutions
    if debug:
        print("fast_solution_id:", fast_solution_id)
        print("slow_solution_id:", slow_solution_id)
        print("slow time: ", slow_time_stat[most_differentiating_input])
        print("fast time: ", fast_time_stat[most_differentiating_input])

    return most_differentiating_input


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
    feedback_prompt_type: Literal["diff_solution", "diff_input"] = None,
):
    problem_root_dir = Path(problem_root_dir)
    assert (
        config["solution_selection"] == "time_contrast"
    ), "This script is only for time_contrast selection"
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
            problem_id, problem, Language.str_to_lang(solution_language)
        )

        if len(selected_solution_ids) < 2:
            print(
                f"Not enough solutions to select for {problem_id} language: {solution_language}"
            )
            continue

        fast_solution_id, slow_solution_id = selected_solution_ids
        solutions_stat_file = Path("./results/alphacode") / (problem_id + ".json")

        most_differentiating_input_file_name = select_most_differentiating_input(
            solutions_stat_file, fast_solution_id, slow_solution_id
        )

        slow_input_file_name, fast_input_file_name = select_slow_fast_input(
            solutions_stat_file, slow_solution_id
        )

        if debug:
            print("=" * 50)
            print("problem_id:", problem_id)
            print("fast_solution_id:", fast_solution_id)
            print("slow_solution_id:", slow_solution_id)
            print(
                "most_differentiating_input_file_name:",
                most_differentiating_input_file_name,
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

        else:
            raise ValueError("Invalid feedback_prompt_type")


if __name__ == "__main__":
    Fire(main)
