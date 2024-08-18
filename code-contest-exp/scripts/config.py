"""Configuration setting."""
from common import Language

config = {
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "none",  # set it to 'none' to run it on alpha code tests
    "solution_selection": "time_contrast",
    "manual_prompt": True,
    "prompt_language": Language.JAVA,
    "repeat_test": 3,
    "specified_problem": [
        "374_E",
        "1140_G",
        "611_D",
        "987_F",
        "163_E",
        "1078_B",
        "889_C",
        "484_B",
        "185_A",
        "1266_F",
        "557_C",
        "1536_F",
        "1167_F",
        "1056_A",
        "178_B3",
        "1436_E",
        "468_B",
        "863_F",
        "1331_D",
        "273_B",
    ],
    "use_specified_problem": True,
    "coverage_hit_count_output_dir": "cov_hit_count",
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
]
