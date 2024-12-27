"""Configuration setting."""
import json
from pathlib import Path
from common import Language

problem_list_path = Path(f"problem_list.json")
with open(problem_list_path, "r", encoding="utf-8") as file:
    problem_lists = json.load(file)

config = {
    "problem_root_dir": "./problems",
    "result_root_dir": "./results",
    "prompt_template_root_dir": "./prompt_templates",
    "max_time_limit": 60,
    "experiment_name": "alphacode",  # set it to 'alphacode' to run it on alphacode tests
    "manual_prompt": False,
    "prompt_language": Language.JAVA,
    "repeat_test": 1,
    "num_tests": 20,
    "specified_problem": problem_lists[0],
    "specified_problem_bak": None,
    "use_specified_problem": True,
    "coverage_hit_count_output_dir": "cov_hit_count",
    "gen_tests_failing_problem_record": "./results/gen_tests_failing_problems.json",
    "validator_dir_name": "validator_gen",
    "cov_data_dir": "./results_cov_data",
    "product_cov_data_dir": "./constraint_guided_input_gen/product_cov",
    "input_pairs_dir": "./constraint_guided_input_gen/input_pairs",
    "cgig_prompt_template_dir": "./prompt_templates/cgig",
    "constraints_dir": "./constraint_guided_input_gen/extracted_constraints",
    "custom_mutators_dir": "./constraint_guided_input_gen/custom_mutators",
    "mutator_with_generator_dir": "./constraint_guided_input_gen/mutator_with_generator",
    "mutator_with_constraint_dir": "./constraint_guided_input_gen/mutator_with_constraint",
    "mutator_with_constraint_multi_dir": "./constraint_guided_input_gen/mutator_with_constraint_multi",
    "corpus_gen_dir": "./constraint_guided_input_gen/corpus_gen",
    "input_classify_dir": "./constraint_guided_input_gen/input_classify",
    "instrument_fuzz_dir": "./constraint_guided_input_gen/instrument_fuzz",
    "raw_fuzz_dir": "./constraint_guided_input_gen/raw_fuzz",
    "constraint_guided_one_fuzz_dir": "./constraint_guided_input_gen/constraint_guided_one_fuzz",
}

# Manual Test
# ["133_E", "1203_E", "769_D", "1379_D", "1536_C"]

# Pipeline Test
# ["374_E", "1140_G", "611_D", "987_F", "163_E", "1078_B", "889_C", "484_B", "185_A", "1266_F", "557_C", "1536_F", "1167_F", "1056_A", "178_B3", "1436_E", "468_B", "863_F", "1331_D", "273_B"]

abandoned_list = [
    "print any of them",
    "If there are multiple solutions, you may output any.",
    "If there are several such trees, output any.",
    "If there are multiple possible solutions",
    "If there are several solutions",
    "The answer will be considered correct,",
    "an interactive problem",
    "its absolute or relative error does not exceed",
    "print in any order",
    "with an accuracy of",
    "in arbitrary order.",
    "If there is more than one solution, find any of them.",
    "If there are multiple answers, print any.",
    "Your answer will be considered correct if its relative or absolute error",
]
