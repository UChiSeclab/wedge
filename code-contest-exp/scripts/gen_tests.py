"""Generates input / output tests by prompting llm to write the test generator."""
import os
import subprocess
from pathlib import Path
from typing import Literal, List, Dict, Tuple
from tqdm import tqdm
from fire import Fire
from tempdir import TempDir
import collections
from multiprocessing import Pool

from common import Language
from config import config
from utils import get_cf_problems, filter_problems, get_alphacode_result, \
    record_failing_problem
from gpt_caller import write_test_generator
from run import run_solution
from select_solution import select_solutions
from prompt import PromptTemplate

def create_test_generator(
    problem,
    selected_solutions,
    selected_solution_ids,
    experiment_dir: Path,
    experiment_input_dir: Path,
    prompt_template: PromptTemplate,
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    clear_input_dir: bool = True,
) -> bool:
    if not config["manual_prompt"]:
        cost = write_test_generator(
            experiment_dir,
            problem,
            selected_solutions,
            selected_solution_ids,
            prompt_template=prompt_template,
            prompt_language=prompt_language,
        )
        print("Cost on API call:", cost)
    else:
        print("Write file into", experiment_dir / "gen.py")
        input("Press Enter to continue...")

    if clear_input_dir:
        [f.unlink() for f in experiment_input_dir.iterdir()]

    try:
        subprocess.run(
            [
                "python",
                (experiment_dir / "gen.py").as_posix(),
                experiment_input_dir.as_posix(),
            ],
            capture_output=True,
            text=True,
            check=True,
            timeout=300
        ).check_returncode()
    except subprocess.CalledProcessError as e:
        print("Error during execution of gen.py:", e)
        return False
    except subprocess.TimeoutExpired as e:
        print("Timeout during execution of gen.py:", e)
        return False

    return True

def record_gen_tests_output(
    experiment_input_dir: Path,
    solution_output_dir: Path,
):
    result = {}
    for input_file_name in os.listdir(experiment_input_dir):
        output_file = solution_output_dir / f"{input_file_name[:-3]}.out"
        if not output_file.exists():
            result[input_file_name] = "NO_OUTPUT"
        else:
            with open(output_file, "r") as f:
                output = f.read()
            if output == "":
                result[input_file_name] = "NO_OUTPUT"
            else:
                result[input_file_name] = output

    return result

def check_consistency_of_gen_tests_output(
    experiment_input_dir: Path,
    solution_dir: Path, # solutions/java
    correct_solution_file_names: List[str],
    early_stop: bool = False,
) -> Tuple[List[str], Dict[str, str]]:
    """check and compare the output of generated tests of each solution, \
        early stop if there is already 5% of the inputs are invalid or \
        not consistent"""

    time_limit = 300
    with TempDir() as temp_output_dir:
        test_args = []
        solution_result = {}
        invalid_input_file_names = []
        solution_major_output_dict = {}
        for correct_solution_file_name in correct_solution_file_names:
            correct_solution_file = solution_dir / correct_solution_file_name
            solution_output_dir = Path(temp_output_dir) / correct_solution_file_name
            solution_dir.mkdir(exist_ok=True, parents=True)
            solution_output_dir.mkdir(exist_ok=True, parents=True)

            test_args.append(("consistency_check", correct_solution_file, Language.JAVA, experiment_input_dir, solution_output_dir, time_limit, True))

        max_workers = max(1, int(0.75 * os.cpu_count()))
        with Pool(processes=max_workers) as pool:
            pool.starmap(run_solution, test_args)

        # check after all solutions are run
        for test_arg in test_args:
            correct_solution_file_name = test_arg[1].name
            solution_output_dir = Path(temp_output_dir) / correct_solution_file_name
            solution_result[correct_solution_file_name] = record_gen_tests_output(
                experiment_input_dir,
                solution_output_dir,
            )

        for input_file_name in os.listdir(experiment_input_dir):
            outputs = [solution_result[correct_solution_file_name][input_file_name] for correct_solution_file_name in correct_solution_file_names]
            outputs = [output.strip() for output in outputs if output != "NO_OUTPUT"]
            # might need to cut the outputs if too long
            output_counter = collections.Counter(outputs)
            if len(output_counter) == 0:
                invalid_input_file_names.append(input_file_name)
                continue
            majority_output = output_counter.most_common(1)[0][0]
            majority_output_count = output_counter.most_common(1)[0][1]
            if majority_output_count < len(correct_solution_file_names) * 0.95:
                invalid_input_file_names.append(input_file_name)
            else:
                solution_major_output_dict[input_file_name] = majority_output
    
    return invalid_input_file_names, solution_major_output_dict

def create_test_generator_with_retry(
    experiment_name: str,
    problem,
    selected_solutions,
    selected_solution_ids,
    experiment_dir: Path,
    experiment_input_dir: Path,
    prompt_template: PromptTemplate,
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    max_retry: int = 10,
    run_tests: bool = True,
    run_tests_language: Literal["python", "cpp", "python3", "java"] = "java",
) -> bool:
    problem_id = problem["name"].split(".")[0]
    alphacode_result = get_alphacode_result(problem_id)
    try_cnt = 0
    experiment_output_dir = experiment_dir / "output"
    experiment_output_dir.mkdir(exist_ok=True, parents=True)
    while try_cnt < max_retry and \
        (len(os.listdir(experiment_input_dir)) < config["num_tests"] / 2 or \
            (run_tests and \
                len(os.listdir(experiment_output_dir)) < len(os.listdir(experiment_input_dir)) / 2)):
        # Retry if the test generator fails to generate enough tests
        try_cnt += 1
        print(f"[INFO] gen_tests try {try_cnt}th for {problem_id}")

        gen_py_success = create_test_generator(
            problem,
            selected_solutions,
            selected_solution_ids,
            experiment_dir,
            experiment_input_dir,
            prompt_template,
            prompt_language,
        )

        if not gen_py_success:
            print(f"[Error] gen_tests failed to generate tests for {problem_id}, try count: {try_cnt}")
            continue

        if len(os.listdir(experiment_input_dir)) < config["num_tests"] / 2:
            print(f"[Error] too few inputs are generated for {problem_id}, try count: {try_cnt}")
            continue

        if run_tests:
            if run_tests_language == str(Language.JAVA):
                solution_dir = experiment_dir.parent / "solutions" / str(run_tests_language)
                correct_solution_file_names = [
                    solution_file_name
                    for solution_file_name in os.listdir(solution_dir)
                    if ("incorrect" not in solution_file_name) and \
                        all(v == "AC" for v in alphacode_result[solution_file_name.split(".")[0]]["verdict"])
                ]

                invalid_input_file_names, solution_major_output_dict = check_consistency_of_gen_tests_output(
                    experiment_input_dir,
                    solution_dir,
                    correct_solution_file_names,
                )

                if len(invalid_input_file_names) > 0:
                    print(f"[INFO] number of invalid_input_file_names: {len(invalid_input_file_names)}, try count: {try_cnt}")
                    [Path(experiment_input_dir / invalid_input_file_name).unlink() for invalid_input_file_name in invalid_input_file_names]

                if len(os.listdir(experiment_input_dir)) < config["num_tests"] / 2:
                    print(f"[Warning] too few consistent inputs generated for {problem_id} from all solutions, try count: {try_cnt}")
                    continue

                for input_file_name, majority_output in solution_major_output_dict.items():
                    with open(experiment_output_dir / f"{input_file_name[:-3]}.out", "w") as f:
                        f.write(majority_output)

                if len(os.listdir(experiment_output_dir)) < len(os.listdir(experiment_input_dir)) / 2:
                    print(f"[Warning] too manys inputs are invalid as so few output is generated for {problem_id} from all solutions, try count: {try_cnt}")
                    [f.unlink() for f in experiment_output_dir.iterdir()]
            else:
                raise NotImplementedError(
                    "Only JAVA is supported for running tests here."
                )

    return (len(os.listdir(experiment_input_dir)) >= config["num_tests"] / 2 and \
        (not run_tests or len(os.listdir(experiment_output_dir)) >= len(os.listdir(experiment_input_dir)) / 2))

def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    run_tests: bool = True,
    run_tests_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_template: str = "prompt_template.txt",
    top_k: int = None,
):
    """Generates tests by test generator created by LLM.

    Args:
    - experiment_name: The folder name for the experiement
    - problem_root_dir: The directory to put the problem in
    """
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    prompt_template = PromptTemplate(Path(prompt_template), experiment_name)
    solution_selection_type = PromptTemplate.get_select_solution_type(experiment_name)

    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]
        print("problem_id:", problem_id)
        problem_dir = problem_root_dir / problem_id
        experiment_dir = problem_dir / experiment_name
        experiment_dir.mkdir(exist_ok=True, parents=True)
        selected_solution_ids, selected_solutions = select_solutions(
            problem_id, problem, solution_selection_type, Language.str_to_lang(prompt_language), top_k=top_k
        )

        if (selected_solution_ids == None) or (top_k and len(selected_solution_ids) < top_k):
            print(f"Not enough solutions to select for {problem_id}", end=" ")
            print("language:", prompt_language)
            continue

        experiment_input_dir = experiment_dir / "input"
        experiment_input_dir.mkdir(exist_ok=True, parents=True)
        test_generator_path = experiment_dir / "gen.py"
        if test_generator_path.exists() and len(os.listdir(experiment_input_dir)) > 0\
            and (not run_tests or len(os.listdir(experiment_dir / "output")) > 0):
            print(f"Experiment {experiment_name} already exists for {problem_id}")
            continue

        if not create_test_generator_with_retry(
            experiment_name,
            problem,
            selected_solutions,
            selected_solution_ids,
            experiment_dir,
            experiment_input_dir,
            prompt_template=prompt_template,
            prompt_language=prompt_language,
            run_tests=run_tests,
            run_tests_language=run_tests_language,
        ):
            print(f"[Error] Failed to generate enough valid tests for {problem_id}")
            record_failing_problem(problem_id, experiment_name, "Failed to generate enough valid tests")

if __name__ == "__main__":
    Fire(main)
