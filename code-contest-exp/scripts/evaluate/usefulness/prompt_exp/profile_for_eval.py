from pathlib import Path
import json
from fire import Fire

from config import config
from utils import get_cf_problems, filter_problems, problem_to_id, run_result_exists
from evaluate.usefulness.prompt_exp.profile_utils import profile_solutions, get_input_output_pairs
from evaluate.usefulness.prompt_exp.stats import get_correct_problem_solution_from_profile_stats, get_subset_profile_stats

ORI_SOLUTIONS_DIR = Path(config["effi_learner_dir"]) / "ori_human_solutions"
ORI_SOLUTION_PROFILE_EVAL_DIR = Path(config["effi_learner_dir"]) / "ori_human_solution_profile_eval"
OPTIMIZED_SOLUTIONS_DIR = Path(config["effi_learner_dir"]) / "optimized_human_solutions"
OPTIMIZED_SOLUTION_PROFILE_EVAL_DIR = Path(config["effi_learner_dir"]) / "optimized_human_solution_profile_eval"

EVAL_INPUT_SET = "alphacode"
EVAL_INPUT_SELECTION_TYPE = "public_private_10"

def main(
    input_set: str,
    input_selection_type: str,
    model_name: str,
):
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"]),
        filter_with_inconsistency_threshold=True,
    )

    ori_profile_stats_all_problems = {}
    optimized_profile_stats_all_problems = {}

    for problem in filtered_problems:
        problem_id = problem_to_id(problem)
        if not run_result_exists(problem_id, input_set):
            print(f"[INFO] Run result does not exist for {input_set} of {problem_id}, skipping.")
            continue

        ori_solution_dir = ORI_SOLUTIONS_DIR / problem_id
        ori_solution_files = sorted(ori_solution_dir.glob("*.py"))
        optimized_solution_dir = OPTIMIZED_SOLUTIONS_DIR / f"{input_set}_{input_selection_type}" / problem_id / model_name
        optimized_solution_files = sorted(optimized_solution_dir.glob("*.py"))

        ori_solution_profile_dir = ORI_SOLUTION_PROFILE_EVAL_DIR / problem_id
        optimized_solution_profile_dir = OPTIMIZED_SOLUTION_PROFILE_EVAL_DIR / f"{input_set}_{input_selection_type}" / problem_id / model_name

        input_output_pairs = get_input_output_pairs(problem_id, EVAL_INPUT_SET, EVAL_INPUT_SELECTION_TYPE)
        input_file_list = [input_file for input_file, _ in input_output_pairs]
        gt_output_file_list = [output_file for _, output_file in input_output_pairs]

        ori_profile_stats = profile_solutions(ori_solution_profile_dir, ori_solution_files, input_file_list, gt_output_file_list, include_instruction_cnt=True, timeout=30)
        optimized_profile_stats = profile_solutions(optimized_solution_profile_dir, optimized_solution_files, input_file_list, gt_output_file_list, include_instruction_cnt=True, timeout=30)

        ori_profile_stats_all_problems[problem_id] = ori_profile_stats
        optimized_profile_stats_all_problems[problem_id] = optimized_profile_stats

    print(ori_profile_stats_all_problems)
    print(optimized_profile_stats_all_problems)

    # correct solutions (optimized codegen) to evaluate
    correct_optimized_problem_solution = get_correct_problem_solution_from_profile_stats(ori_profile_stats_all_problems, optimized_profile_stats_all_problems)
    print(f"correct_optimized_problem_solution:")
    print(correct_optimized_problem_solution)

    subset_ori_profile_stats = get_subset_profile_stats(correct_optimized_problem_solution, ori_profile_stats_all_problems)
    subset_optimized_profile_stats = get_subset_profile_stats(correct_optimized_problem_solution, optimized_profile_stats_all_problems)

    # dump stats
    ori_profile_stats_file = Path(config["effi_learner_dir"]) / f"ori_human_profile_stats.json"
    optimized_profile_stats_file = Path(config["effi_learner_dir"]) / f"optimized_human_profile_stats_{input_set}_{input_selection_type}_{model_name}.json"
    with open(ori_profile_stats_file, "w") as f:
        json.dump(subset_ori_profile_stats, f, indent=4)
    with open(optimized_profile_stats_file, "w") as f:
        json.dump(subset_optimized_profile_stats, f, indent=4)

if __name__ == '__main__':
    Fire(main)
