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
import time

from common import Language
from config import config
from utils import get_cf_problems, filter_problems, get_alphacode_result, \
    record_failing_problem
from cgig.cgig_utils import problem_has_extracted_constraint
from gpt_caller import write_test_generator
from run import run_solution
from selector.select_solution import select_solutions
from prompt import PromptTemplate
from evalperf_driver import sample_inputs
from input_validator_run import run_validator

def early_stop_for_input_consistency(
    input_dir: Path,
    output_dir_of_solutions: Path,
    correct_solution_file_names: List[str],
) -> bool:
    if len(os.listdir(input_dir)) < config["num_tests"] / 2:
        return True
    assert len(correct_solution_file_names) > 0, "No correct solution file names"
    solution_result = {}
    invalid_input_file_names = []
    for correct_solution_file_name in correct_solution_file_names:
        solution_output_dir = Path(output_dir_of_solutions) / correct_solution_file_name
        if solution_output_dir.exists():
            solution_result[correct_solution_file_name] = record_gen_tests_output(
                input_dir,
                solution_output_dir,
            )

    for input_file_name in os.listdir(input_dir):
        outputs = [solution_result[correct_solution_file_name][input_file_name] for correct_solution_file_name in correct_solution_file_names]
        num_empty_output = outputs.count("EMPTY_OUTPUT") # EMPTY_OUTPUT indicates the solution ran into an error
        if num_empty_output > 0.05 * len(correct_solution_file_names):
            print(f"input_file_name: {input_file_name}, num_empty_output: {num_empty_output}")
            invalid_input_file_names.append(input_file_name)
        else:
            outputs = [output.strip() for output in outputs if output != "NO_OUTPUT" and output != "EMPTY_OUTPUT"]
            if len(outputs) == 0:
                continue
            output_counter = collections.Counter(outputs)
            majority_output_count = output_counter.most_common(1)[0][1]
            non_majority_output_count = len(outputs) - majority_output_count
            if non_majority_output_count + num_empty_output > 0.05 * len(correct_solution_file_names):
                print(f"input_file_name: {input_file_name}, non_majority_output_count: {non_majority_output_count}, num_empty_output: {num_empty_output}")
                invalid_input_file_names.append(input_file_name)

    while len(invalid_input_file_names) > 0 and any((input_dir / invalid_input_file_name).exists() for invalid_input_file_name in invalid_input_file_names):
        try:
            [Path(input_dir / invalid_input_file_name).unlink() for invalid_input_file_name in invalid_input_file_names if (input_dir / invalid_input_file_name).exists()]
        except FileNotFoundError as e:
            print(f"Error during removing invalid inputs: {e}, try again")
            time.sleep(0.1)
            continue

    return len(os.listdir(input_dir)) < config["num_tests"] / 2

def run_solution_early_stop(
    experiment_name: str,
    solution_path: Path,
    language: Language,
    input_dir: Path,
    output_dir: Path, # solution_output_dir (temp_output_dir / correct_solution_file_name)
    time_limit: float = 1,
    write_output: bool = False,
    correct_solution_file_names: List[str] = None,
    output_dir_of_solutions: Path = None,
) -> Dict:
    """check consistency of the output of the solution first, early stop if \
        there is already 5% of the inputs are invalid or not consistent"""
        
    # remove the inputs that are already invalid
    # check number of remaining inputs, stop if less than 50% of the total inputs
    early_stop = early_stop_for_input_consistency(
        input_dir,
        output_dir_of_solutions,
        correct_solution_file_names,
    )
    if early_stop:
        return None

    return run_solution(
        experiment_name,
        solution_path,
        language,
        input_dir,
        output_dir,
        time_limit,
        write_output
    )

def run_generator(generator_file: Path, experiment_input_dir: Path) -> bool:
    try:
        subprocess.run(
            [
                "python",
                generator_file.as_posix(),
                experiment_input_dir.as_posix(),
            ],
            capture_output=True,
            text=True,
            check=True,
            timeout=20,
        ).check_returncode()
    except subprocess.CalledProcessError as e:
        print("Error during execution of gen.py:", e)
        return False
    except subprocess.TimeoutExpired as e:
        print("Timeout during execution of gen.py:", e)
        return False
    except MemoryError as e:
        print("Memory error during execution of gen.py:", e)
        return False

    return True

def run_evalperf_generator(generator_file: Path, experiment_input_dir: Path) -> bool:
    gen_inputs, selected_scales, well_defined_exit = sample_inputs(generator_file)
    if not well_defined_exit:
        return False
    if len(gen_inputs) == 0:
        return False
    if len(gen_inputs) > config["num_tests"]:
        # get the last num_tests inputs
        gen_inputs = gen_inputs[-config["num_tests"]:]
    for i, gen_input in enumerate(gen_inputs, 1):
        with open(experiment_input_dir / f"test_{i:02}.in", "w") as f:
            f.write(gen_input)

    return True

def create_and_run_test_generator(
    experiment_name: str,
    problem,
    selected_solutions,
    selected_solution_ids,
    experiment_dir: Path,
    experiment_input_dir: Path,
    prompt_template: PromptTemplate,
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    clear_input_dir: bool = True,
    insert_contract_val_mode: str = None,
) -> bool:
    if not config["manual_prompt"]:
        try:
            cost = write_test_generator(
                experiment_dir,
                problem,
                selected_solutions,
                selected_solution_ids,
                prompt_template=prompt_template,
                prompt_language=prompt_language,
                insert_contract_val_mode=insert_contract_val_mode,
            )
            print("Cost on API call:", cost)
        except Exception as e:
            print("Error during writing test generator:", e)
            return False
    else:
        print("Write file into", experiment_dir / "gen.py")
        input("Press Enter to continue...")

    if clear_input_dir:
        [f.unlink() for f in experiment_input_dir.iterdir()]

    generator_file = experiment_dir / "gen.py"

    if experiment_name in [
        "evalperf_slow_solution",
        "evalperf_random_solution",
        "evalperf_slow_solution_contract",
        "evalperf_random_solution_contract",
    ]:
        return run_evalperf_generator(generator_file, experiment_input_dir)
    else:
        return run_generator(generator_file, experiment_input_dir)

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
                result[input_file_name] = "EMPTY_OUTPUT"
            else:
                result[input_file_name] = output

    return result

def check_consistency_of_gen_tests_output(
    experiment_input_dir: Path,
    solution_dir: Path, # solutions/java
    time_limit: float,
    correct_solution_file_names: List[str],
    early_stop: bool = True,
) -> Tuple[List[str], Dict[str, str]]:
    """check and compare the output of generated tests of each solution, \
        early stop if there is already 5% of the inputs are invalid or \
        not consistent"""

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

            test_args.append(("consistency_check", correct_solution_file, Language.JAVA, experiment_input_dir, solution_output_dir, time_limit, True, correct_solution_file_names, Path(temp_output_dir)))

        max_workers = max(1, int(0.5 * os.cpu_count()))
        with Pool(processes=max_workers) as pool:
            if early_stop:
                pool.starmap(run_solution_early_stop, test_args)
            else:
                pool.starmap(run_solution, test_args[:-2])

        # check after all solutions are run
        for correct_solution_file_name in correct_solution_file_names:
            solution_output_dir = Path(temp_output_dir) / correct_solution_file_name
            solution_result[correct_solution_file_name] = record_gen_tests_output(
                experiment_input_dir,
                solution_output_dir,
            )

        for input_file_name in os.listdir(experiment_input_dir):
            outputs = [solution_result[correct_solution_file_name][input_file_name] for correct_solution_file_name in correct_solution_file_names]
            outputs = [output.strip() for output in outputs if output != "NO_OUTPUT" and output != "EMPTY_OUTPUT"]
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
    problem_root_dir: Path,
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
    check_input_validity: bool = True,
    remove_redundant_inputs: bool = True,
    validator_mode: Literal["direct", "resample", "self_reflect", "self_reflect_feedback"] = "self_reflect_feedback",
    insert_validator_contract: bool = False,
    check_consistency: bool = True, # this flag is useful only when run_tests is True
) -> bool:
    assert experiment_name != "alphacode"
    problem_id = problem["name"].split(".")[0]
    alphacode_result = get_alphacode_result(problem_id)
    try_cnt = 0
    experiment_output_dir = experiment_dir / "output"
    experiment_output_dir.mkdir(exist_ok=True, parents=True)
    run_time_limit = problem["time_limit"]["seconds"] + problem["time_limit"]["nanos"] / 10**9
    num_gen_tests = len(os.listdir(experiment_input_dir))
    while try_cnt < max_retry and \
        (len(os.listdir(experiment_input_dir)) < config["num_tests"] / 2 or \
            (run_tests and \
                len(os.listdir(experiment_output_dir)) < len(os.listdir(experiment_input_dir)) / 2)):
        # Retry if the test generator fails to generate enough tests
        try_cnt += 1
        print(f"[INFO] gen_tests try {try_cnt}th for {problem_id}")

        gen_py_success = create_and_run_test_generator(
            experiment_name,
            problem,
            selected_solutions,
            selected_solution_ids,
            experiment_dir,
            experiment_input_dir,
            prompt_template,
            prompt_language,
            insert_contract_val_mode=validator_mode if insert_validator_contract else None,
        )

        if not gen_py_success:
            print(f"[Error] gen_tests failed to generate tests for {problem_id}, try count: {try_cnt}")
            continue

        num_gen_tests = len(os.listdir(experiment_input_dir))
        if check_input_validity:
            validation_result = run_validator(
                experiment_name,
                validator_mode,
                problem_root_dir,
                problem,
                skip_alphacode_generated_tests=True,
                update_validation_result_file=False,
            )
            if validation_result is None:
                print(f"[Error] no available validator for {problem_id}")
                record_failing_problem(problem_id, experiment_name, "No available validator")
                return False
            # remove invalid inputs
            for input_file_name in validation_result:
                if validation_result[input_file_name] != "PASS":
                    if (experiment_input_dir / input_file_name).exists():
                        (experiment_input_dir / input_file_name).unlink()

        if remove_redundant_inputs:
            # only keep test_01.in, test_02.in, ..., test_num_tests.in
            for input_file_name in os.listdir(experiment_input_dir):
                if input_file_name.startswith("test_"):
                    if not input_file_name.endswith(".in"):
                        continue
                    if not (input_file_name.split("_")[1].split(".")[0]).isdigit():
                        continue
                    id = int(input_file_name.split("_")[1].split(".")[0])
                    if id > config["num_tests"] or id < 1:
                        (experiment_input_dir / input_file_name).unlink()

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
                        all(v in ["AC", "TLE"] for v in alphacode_result[solution_file_name.split(".")[0]]["verdict"])
                ]

                if len(correct_solution_file_names) == 0:
                    print(f"[Error] no correct solution found for {problem_id}")
                    record_failing_problem(problem_id, experiment_name, "No correct solution found")
                    return False

                if check_consistency:
                    inconsistent_input_file_names, solution_major_output_dict = check_consistency_of_gen_tests_output(
                        experiment_input_dir,
                        solution_dir,
                        run_time_limit,
                        correct_solution_file_names,
                        early_stop=True,
                    )

                    if len(inconsistent_input_file_names) > 0:
                        print(f"[INFO] number of inconsistent_input_file_names: {len(inconsistent_input_file_names)}, try count: {try_cnt}")
                        [Path(experiment_input_dir / inconsistent_input_file_name).unlink() for inconsistent_input_file_name in inconsistent_input_file_names if (experiment_input_dir / inconsistent_input_file_name).exists()]

                    if len(os.listdir(experiment_input_dir)) < config["num_tests"] / 2:
                        print(f"[Warning] too few consistent inputs generated for {problem_id} from all solutions, try count: {try_cnt}, inputs: {len(os.listdir(experiment_input_dir))}")
                        continue

                    for input_file_name, majority_output in solution_major_output_dict.items():
                        with open(experiment_output_dir / f"{input_file_name[:-3]}.out", "w") as f:
                            f.write(majority_output)

                    [f.unlink() for f in experiment_output_dir.iterdir() if f.read_text().strip() == ""]

                else:
                    for correct_solution_file_name in correct_solution_file_names:
                        print(f"[INFO] run solution for {correct_solution_file_name}")
                        run_solution(
                            experiment_name,
                            solution_dir / correct_solution_file_name,
                            Language.JAVA,
                            experiment_input_dir,
                            experiment_output_dir,
                            run_time_limit,
                            write_output=True,
                        )

                        # remove empty output files
                        [f.unlink() for f in experiment_output_dir.iterdir() if f.read_text().strip() == ""]

                        if len(os.listdir(experiment_output_dir)) >= len(os.listdir(experiment_input_dir)) / 2:
                            break
                        else:
                            for f in experiment_output_dir.iterdir():
                                if not (experiment_input_dir / f"{f.stem}.in").exists():
                                    print(f"[Warning] removing output file without input file: {f}")
                                    f.unlink()

                # remove output files that do not have corresponding input files
                for f in experiment_output_dir.iterdir():
                    if not (experiment_input_dir / f"{f.stem}.in").exists():
                        print(f"[Warning] removing output file without input file: {f}")
                        f.unlink()
                if len(os.listdir(experiment_output_dir)) < len(os.listdir(experiment_input_dir)) / 2:
                    print(f"[Warning] too manys inputs are invalid as so few output is generated for {problem_id} from all solutions, try count: {try_cnt}, outputs: {len(os.listdir(experiment_output_dir))}, inputs: {len(os.listdir(experiment_input_dir))}")
                    [f.unlink() for f in experiment_output_dir.iterdir()]
                else:
                    # remove input files that do not have corresponding output files
                    for f in experiment_input_dir.iterdir():
                        if not (experiment_output_dir / f"{f.stem}.out").exists():
                            print(f"[Warning] removing input file without output file: {f}")
                            f.unlink()
            else:
                raise NotImplementedError(
                    "Only JAVA is supported for running tests here."
                )

    return (len(os.listdir(experiment_input_dir)) >= config["num_tests"] / 2 and \
        (not run_tests or len(os.listdir(experiment_output_dir)) >= num_gen_tests / 2))

def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"],
    run_tests: bool = True,
    run_tests_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_language: Literal["python", "cpp", "python3", "java"] = "java",
    prompt_template: str = "prompt_template.txt",
    top_k: int = None,
    check_input_validity: bool = True,
    validator_mode: Literal["direct", "resample", "self_reflect", "self_reflect_feedback"] = "self_reflect_feedback",
    check_consistency: bool = False,
    problem_with_extracted_constraint_only: bool = False,
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
    if experiment_name in ["constraint_guided_one"]:
        problem_with_extracted_constraint_only = True
    prompt_template = PromptTemplate(Path(prompt_template), experiment_name)
    solution_selection_type = PromptTemplate.get_select_solution_type(experiment_name)

    for problem in tqdm(filtered_problems):
        problem_id = problem["name"].split(".")[0]
        if problem_with_extracted_constraint_only and not problem_has_extracted_constraint(problem_id):
            continue
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
            problem_root_dir,
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
            check_input_validity=check_input_validity,
            validator_mode=validator_mode,
            check_consistency=check_consistency,
        ):
            print(f"[Error] Failed to generate enough valid tests for {problem_id}")
            record_failing_problem(problem_id, experiment_name, "Failed to generate enough valid tests")

if __name__ == "__main__":
    Fire(main)
