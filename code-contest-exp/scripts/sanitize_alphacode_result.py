import json
from pathlib import Path
from fire import Fire
from typing import Dict, List, Literal, Tuple
import pprint
import numpy as np

from config import config
from utils import filter_problems, get_cf_problems, get_alphacode_result
from input_validator_run import run_validator

def sanitize_run_result(
    problem: Dict,
    problem_id: str,
    experiment_name: str,
    experiment_data: Dict[str, Dict]
) -> Dict[str, Dict]:
    """Sanitize the experiment data."""
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    validator_mode = "self_reflect_feedback"
    validator_dir = problem_dir / config["validator_dir_name"] / validator_mode
    if not (validator_dir / "VAL_GT_INPUT_PASS").exists():
        print(f"[Warning] {validator_dir} does not have a good validator. Skipping...")
        return None

    validation_result_path = Path(config["result_root_dir"]) / "input_validation_result" / experiment_name / f"{problem_id}.json"
    validation_result_path.parent.mkdir(parents=True, exist_ok=True)
    if validation_result_path.exists():
        validation_result = json.loads(validation_result_path.read_text())
    else:
        validation_result = run_validator(
            experiment_name,
            validator_mode,
            Path(config["problem_root_dir"]),
            problem,
            skip_alphacode_generated_tests=False,
            update_validation_result_file=False,
        )
        # dump the validation result
        validation_result_path.write_text(json.dumps(validation_result, indent=4))

    sanitized_experiment_data = experiment_data.copy()
    for solution, data in experiment_data.items():
        if solution == "time_limit":
            continue
        time_dict = data["time_dict"]
        ict_dict = data["instruction_cnt_dict"]
        sanitized_time_dict = {}
        sanitized_ict_dict = {}
        for input_name, time_list in time_dict.items():
            if validation_result[input_name] == "PASS":
                sanitized_time_dict[input_name] = time_list
                sanitized_ict_dict[input_name] = ict_dict[input_name]
        sanitized_experiment_data[solution]["time_dict"] = sanitized_time_dict
        sanitized_experiment_data[solution]["instruction_cnt_dict"] = sanitized_ict_dict

    return sanitized_experiment_data

if __name__ == "__main__":
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    for problem in filtered_problems:
        problem_id = problem["name"].split(".")[0]
        alphacode_result = get_alphacode_result(problem_id)
        sanitized_alphacode_result = sanitize_run_result(problem, problem_id, "alphacode", alphacode_result)
        if sanitized_alphacode_result is None:
            continue
        sanitized_alphacode_result_path = Path(config["result_root_dir"]) / "alphacode_sanitized" / f"{problem_id}.json"
        sanitized_alphacode_result_path.parent.mkdir(parents=True, exist_ok=True)
        sanitized_alphacode_result_path.write_text(json.dumps(sanitized_alphacode_result, indent=4))
