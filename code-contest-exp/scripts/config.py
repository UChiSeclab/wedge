"""Configuration setting."""
from common import Language

config = {
    "problem_root_dir": "./problems",
    "result_root_dir": "./results",
    "max_time_limit": 20,
    "experiment_name": "alphacode",  # set it to 'alphacode' to run it on alphacode tests
    "solution_selection": "time_contrast",
    "manual_prompt": False,
    "prompt_language": Language.JAVA,
    "repeat_test": 3,
    "specified_problem": [
        # "117_E", "348_E", "418_D", "504_E", "1114_F", "768_G", "1419_F" # excluded due to not working with java coverage collection tool
        # "1340_F" # excluded due to not working with cpp coverage collection tool
        # "414_E", # excluded due to some reason
        # "1413_F", # excluded due to no input
        # "750_F", "223_D", "786_D", "917_E", "44_F", "1098_F", "666_E", "1284_G", "1470_F", "1375_I", "1439_A1", "720_F", "269_E", "1178_G", "744_D", "607_E", "1043_G", "175_F", "982_F", "107_E", "1054_E", "1440_C2", # excluded due to not enough java solutions
        # "901_E", "331_D3", "985_G", "331_D1", "656_E", "1163_F", "1440_C1", "280_D", "986_D", "1065_D", "609_E", "555_E", "1389_F",
        "5_A",
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
    "an interactive problem",
    "its absolute or relative error does not exceed",
    "print in any order",
    "with an accuracy of",
    "in arbitrary order.",
    "If there is more than one solution, find any of them.",
    "If there are multiple answers, print any.",
    "Your answer will be considered correct if its relative or absolute error",
]
