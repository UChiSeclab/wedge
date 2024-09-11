from pathlib import Path
from typing import Dict, Tuple
from fire import Fire
import subprocess
import json

from config import config
from utils import filter_problems, get_cf_problems


def find_validator_files(validator_dir: Path) -> Tuple[Path, Path]:
    validator_try_dirs = list(validator_dir.glob("try_*"))
    assert len(validator_try_dirs) > 0, f"No try dirs found in {validator_dir}"
    validator_last_try = sorted(
        validator_try_dirs, key=lambda x: int(x.name.split("_")[-1])
    )[-1]
    return validator_last_try / "validator.py", validator_last_try / "input_shape_constraints.txt"


def run_validator(
    experiment_name:str,
    validator_mode:str,
    problem_root_dir: Path,
    problem: Dict,
    skip_alphacode_generated_tests: bool = True,
    update_validation_result_file: bool = False
) -> Dict[str, str]:
    problem_name = problem["name"].split(".")[0]
    problem_dir = problem_root_dir / str(problem_name)
    validator_dir = problem_dir / config["validator_dir_name"] / validator_mode
    if not (validator_dir / "VAL_GT_INPUT_PASS").exists():
        print(f"[Warning] {validator_dir} does not have a good validator. Skipping...")
        return
    validation_result_file = validator_dir / "validation_result.json"
    if update_validation_result_file and validation_result_file.exists():
        with open(validation_result_file, "r") as f:
            validation_result = json.load(f)
    else:
        validation_result = {}
    
    validation_result[experiment_name] = validation_result.get(experiment_name, {})

    validator_file, _ = find_validator_files(validator_dir)

    if experiment_name == "alphacode":
        input_dir = problem_dir / "input"
    else:
        input_dir = problem_dir / experiment_name / "input"

    input_files = list(input_dir.glob("*.in"))
    for input_file in input_files:
        if skip_alphacode_generated_tests and "generated" in input_file.name:
            continue
        try:
            res = subprocess.run(
                ["python", validator_file.as_posix(), input_file.as_posix()],
                capture_output=True,
                text=True,
                timeout=10,
                check=True
            )
            print(f"Output for {input_file}: {res.stdout}", end="")
            validation_result[experiment_name][input_file.name] = "PASS"
        except subprocess.CalledProcessError as e:
            print(f"Validation failed for {input_file.absolute()}: {e.stderr}")
            validation_result[experiment_name][input_file.name] = "FAIL"
        except subprocess.TimeoutExpired as e:
            print(f"Validation timed out for {input_file.absolute()}: {e}")
            validation_result[experiment_name][input_file.name] = "TIMEOUT"

    if update_validation_result_file:
        with open(validation_result_file, "w") as f:
            json.dump(validation_result, f, indent=4)

    return validation_result[experiment_name]

def main(
    problem_root_dir: str = config["problem_root_dir"],
    experiment_name: str = config["experiment_name"],
    validator_mode: str = "direct"
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    for problem in filtered_problems:
        print(f"Running validator for {problem['name'].split('.')[0]}")
        run_validator(experiment_name, validator_mode, problem_root_dir, problem, update_validation_result_file=True)


if __name__ == "__main__":
    Fire(main)
