from pathlib import Path
import json
from typing import Dict, List

from config import config
from evaluate.usefulness.prompt_exp.SOAP import get_input_output_pairs
from evaluate.usefulness.prompt_exp.profile_utils import profile_solutions

def get_pass_k_raw_stats(all_problems_correctness_stats: Dict, k_list: List[int]) -> float:
    """
    Get the pass@k raw statistics
    """
    pass_k_raw_stats = {} # problem_id -> pass@k (1 or 0)
    for problem_id, correctness_stats in all_problems_correctness_stats.items():
        pass_k_raw_stats[problem_id] = {}
        for k in k_list:
            pass_k_raw_stats[problem_id][k] = 0
        for solution_file_id, correctness in correctness_stats.items():
            # solution_file_id format: solution_i_extracted
            idx = int(solution_file_id.split("_")[1])
            if correctness == "correct":
                for k in k_list:
                    if idx <= k:
                        pass_k_raw_stats[problem_id][k] = 1

    return pass_k_raw_stats

def get_pass_k_stats_summary(pass_k_raw_stats: Dict, k_list: List[int]) -> Dict:
    """
    Get the pass@k statistics summary
    """
    pass_k_stats_summary = {}
    for k in k_list:
        pass_k_stats_summary[k] = sum([pass_k_raw_stats[problem_id][k] for problem_id in pass_k_raw_stats.keys()]) / len(pass_k_raw_stats.keys())

    return pass_k_stats_summary

def display_pass_k_stats_summary(pass_k_stats_summary: Dict):
    """
    Display the pass@k statistics summary
    """
    for k, pass_k in pass_k_stats_summary.items():
        print(f"Pass@{k}: {pass_k:.2%}")

def get_correctness_stats(problem_id: str, input_set: str, input_selection_type: str, solution_root_dir: Path, solution_profile_root_dir: Path, solution_model_name: str) -> Dict[str, str]:
    """
    Get the statistics of the initial code generation
    """
    solution_dir = solution_root_dir / problem_id / solution_model_name
    solution_profile_par_dir = solution_profile_root_dir / f"{input_set}_{input_selection_type}" / problem_id / solution_model_name
    assert solution_dir.exists(), f"Original solution directory {solution_dir} does not exist"
    solution_files = sorted(solution_dir.glob("*_extracted.py"))

    correctness_stats = {}

    for solution_file in solution_files:
        solution_profile_dir = solution_profile_par_dir / solution_file.stem
        solution_profile_dir.mkdir(exist_ok=True, parents=True)
        prompt_file = solution_profile_dir / f"{solution_file.stem}_prompt.txt"

        if prompt_file.exists() and prompt_file.read_text() == "The code execution failed.":
            correctness = "incorrect"
        else:
            correctness = "correct"
        correctness_stats[solution_file.stem] = correctness

    return correctness_stats

def get_correctness_stats_for_all_problems(problem_id_list: List[str], input_set: str, input_selection_type: str, solution_root_dir: Path, solution_profile_root_dir: Path, solution_model_name: str) -> Dict[str, Dict[str, str]]:
    """
    Get the correctness statistics of code generation for all problems
    """
    all_problems_correctness_stats = {}
    for problem_id in problem_id_list:
        all_problems_correctness_stats[problem_id] = get_correctness_stats(problem_id, input_set, input_selection_type, solution_root_dir, solution_profile_root_dir, solution_model_name)

    return all_problems_correctness_stats

def get_problem_solution_to_evaluate(problem_id_list: List[str], all_problems_correctness_stats: Dict[str, Dict[str, str]]) -> Dict[str, List[str]]:
    """
    Get the problem and correct solutions to evaluate
    """
    problem_solution_to_evaluate = {}
    for problem_id in problem_id_list:
        problem_correctness_stats = all_problems_correctness_stats[problem_id]
        correct_solutions = [solution_file_id for solution_file_id, correctness in problem_correctness_stats.items() if correctness == "correct"]
        problem_solution_to_evaluate[problem_id] = correct_solutions

    # remove problems with no correct solutions
    problem_solution_to_evaluate = {problem_id: correct_solutions for problem_id, correct_solutions in problem_solution_to_evaluate.items() if len(correct_solutions) > 0}

    return problem_solution_to_evaluate

def get_correct_problem_solution_from_profile_stats(profile_stats_all_problems: Dict):
    """
    Get the problem and correct solutions from profile stats
    """
    problem_solution_to_evaluate = {}
    for problem_id, profile_stats in profile_stats_all_problems.items():
        correct_solutions = [solution_file_id for solution_file_id, profile_stat in profile_stats.items() if profile_stat["correctness"] == "correct"]
        # sort by solution index
        correct_solutions = sorted(correct_solutions, key=lambda x: int(x.split("_")[-1]))
        problem_solution_to_evaluate[problem_id] = correct_solutions

    # remove problems with no correct solutions
    problem_solution_to_evaluate = {problem_id: correct_solutions for problem_id, correct_solutions in problem_solution_to_evaluate.items() if len(correct_solutions) > 0}

    return problem_solution_to_evaluate

def get_subset_profile_stats(problem_solution_to_evaluate: Dict, profile_stats_all_problems: Dict):
    """
    Get the profile stats for the subset of problems and solutions
    """
    subset_profile_stats = {}
    for problem_id, correct_solutions in problem_solution_to_evaluate.items():
        subset_profile_stats[problem_id] = {}
        for solution_file_id in correct_solutions:
            subset_profile_stats[problem_id][solution_file_id] = profile_stats_all_problems[problem_id][solution_file_id]

    return subset_profile_stats

if __name__ == "__main__":
    # problem_id_list = ["1005_E2", "1036_C", "1061_B", "1061_C", "1077_F1", "1097_C", "109_B", "1102_A", "1147_B", "1200_E", "1202_B", "121_A", "1230_C", "1253_D", "128_C", "1294_D", "1420_C2", "1424_J", "1438_E", "1497_E1", "1538_E", "1539_C", "1550_D", "180_E", "227_C", "230_B", "235_A", "23_E", "270_C", "279_A", "284_A", "396_A", "409_H", "461_B", "47_D", "480_C", "525_C", "535_C", "536_B", "546_C", "553_A", "575_H", "581_C", "626_C", "633_A", "653_B", "663_B", "678_A", "678_B", "697_A", "757_C", "835_D", "8_B", "903_A", "908_F", "984_D", "996_B"]
    problem_id_list = ['1102_A', '996_B', '1097_C', '663_B', '1424_J', '235_A', '626_C', '1005_E2', '546_C', '480_C', '633_A', '525_C', '1230_C', '1061_C', '1550_D', '903_A', '1061_B']
    input_set = "alphacode"
    input_selection_type = "slow_5"
    solution_model_name = "gpt-4o"
    initial_solution_root_dir = Path(config["effi_learner_dir"]) / "initial_code_generation"
    solution_profile_root_dir = Path(config["effi_learner_dir"]) / "initial_solution_profile"
    all_problems_correctness_stats = get_correctness_stats_for_all_problems(problem_id_list, input_set, input_selection_type, initial_solution_root_dir, solution_profile_root_dir, solution_model_name)
    k_list = [1, 5, 10, 20] # pass@k
    pass_k_raw_stats = get_pass_k_raw_stats(all_problems_correctness_stats, k_list)
    # print(pass_k_raw_stats)
    pass_k_stats_summary = get_pass_k_stats_summary(pass_k_raw_stats, k_list)
    display_pass_k_stats_summary(pass_k_stats_summary)
    
    # assume initial code generation is done, profiling on initial code gen
    # is done, optimized code generation is done, going to profile optimized 
    # code generation

    # correct solutions (initial codegen) to evaluate
    problem_solution_to_evaluate = get_problem_solution_to_evaluate(problem_id_list, all_problems_correctness_stats)
    print(problem_solution_to_evaluate)
    
    ori_profile_stats_all_problems = {}
    optimized_profile_stats_all_problems = {}
    
    # profile optimized code generation
    optimized_solution_root_dir = Path(config["effi_learner_dir"]) / "optimized_code_generation"
    optimized_solution_profile_root_dir = Path(config["effi_learner_dir"]) / "optimized_solution_profile"

    for problem_id in problem_solution_to_evaluate.keys():
        ori_solution_file_ids = problem_solution_to_evaluate[problem_id]
        ori_solution_files = [initial_solution_root_dir / problem_id / solution_model_name / f"{solution_file_id}.py" for solution_file_id in ori_solution_file_ids]
        ori_solution_profile_dir = solution_profile_root_dir / f"{input_set}_{input_selection_type}" / problem_id / solution_model_name
        optimized_solution_file_ids = [f"{solution_file_id}_edited" for solution_file_id in ori_solution_file_ids]
        optimized_solution_files = [optimized_solution_root_dir / f"{input_set}_{input_selection_type}" / problem_id / solution_model_name / f"{solution_file_id}.py" for solution_file_id in optimized_solution_file_ids]
        optimized_solution_profile_dir = optimized_solution_profile_root_dir / f"{input_set}_{input_selection_type}" / problem_id / solution_model_name
        input_output_pairs = get_input_output_pairs(problem_id, input_set, input_selection_type)
        input_file_list = [input_file for input_file, _ in input_output_pairs]
        gt_output_file_list = [output_file for _, output_file in input_output_pairs]
        ori_profile_stats = profile_solutions(ori_solution_profile_dir, ori_solution_files, input_file_list, gt_output_file_list, include_instruction_cnt=True)
        optimized_profile_stats = profile_solutions(optimized_solution_profile_dir, optimized_solution_files, input_file_list, gt_output_file_list, include_instruction_cnt=True)
        ori_profile_stats_all_problems[problem_id] = ori_profile_stats
        optimized_profile_stats_all_problems[problem_id] = optimized_profile_stats

    print(ori_profile_stats_all_problems)
    print(optimized_profile_stats_all_problems)
    
    # correct solutions (optimized codegen) to evaluate
    correct_optimized_problem_solution = get_correct_problem_solution_from_profile_stats(optimized_profile_stats_all_problems)
    print(f"correct_optimized_problem_solution:")
    print(correct_optimized_problem_solution)

    subset_ori_profile_stats = get_subset_profile_stats(correct_optimized_problem_solution, ori_profile_stats_all_problems)
    subset_optimized_profile_stats = get_subset_profile_stats(correct_optimized_problem_solution, optimized_profile_stats_all_problems)

    # dump stats
    with open("ori_profile_stats.json", "w") as f:
        json.dump(subset_ori_profile_stats, f, indent=4)
    with open("optimized_profile_stats.json", "w") as f:
        json.dump(subset_optimized_profile_stats, f, indent=4)
