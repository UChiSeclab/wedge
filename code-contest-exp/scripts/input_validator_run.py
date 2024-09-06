from pathlib import Path
from typing import Dict
from tqdm import tqdm
from fire import Fire
import subprocess

from config import config
from utils import filter_problems, get_cf_problems

def run_validator(experiment_name:str, problem_root_dir: Path, problem: Dict, stat: Dict[str, int]):
    problem_name = problem["name"].split(".")[0]
    problem_dir = problem_root_dir / str(problem_name)
    validator_file = problem_dir / "validator_gen" / "validator.py"
    if experiment_name == "alphacode":
        input_dir = problem_dir / "input"
    else:
        input_dir = problem_dir / experiment_name / "input"

    input_files = list(input_dir.glob("*.in"))
    for input_file in input_files:
        if "public" in input_file.name:
            stat["public_cnt"] += 1
        elif "private" in input_file.name:
            stat["private_cnt"] += 1
        else:
            stat["generated_cnt"] += 1
        try:
            res = subprocess.run(
                ["python", validator_file.as_posix(), input_file.as_posix()],
                capture_output=True,
                text=True,
                timeout=10,
                check=True
            )
            print(f"Output for {input_file}: {res.stdout}", end="")
            stat["pass_cnt"] += 1
        except subprocess.CalledProcessError as e:
            stat["fail_cnt"] += 1
            if "public" in input_file.name:
                stat["fail_public_cnt"] += 1
            elif "private" in input_file.name:
                stat["fail_private_cnt"] += 1
            else:
                stat["fail_generated_cnt"] += 1
            print(f"Validation failed for {input_file.absolute()}: {e.stderr}")
        except subprocess.TimeoutExpired as e:
            print(f"Validation timed out for {input_file.absolute()}: {e}")


def main(
    experiment_name: str = config["experiment_name"],
    problem_root_dir: str = config["problem_root_dir"]
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    
    stat = {
        "pass_cnt": 0,
        "fail_cnt": 0,
        "fail_public_cnt": 0,
        "fail_private_cnt": 0,
        "fail_generated_cnt": 0,
        "public_cnt": 0,
        "private_cnt": 0,
        "generated_cnt": 0
    }

    for problem in filtered_problems:
        print(f"Running validator for {problem['name'].split('.')[0]}")
        run_validator(experiment_name, problem_root_dir, problem, stat)

    print(stat)

if __name__ == "__main__":
    Fire(main)
