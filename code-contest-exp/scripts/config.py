"""Configuration setting."""
from common import Language

config = {
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "none",  # set it to 'none' to run it on alpha code tests
    "solution_selection": "time_contrast",
    "manual_prompt": False,
    "prompt_language": Language.JAVA,
    "repeat_test": 3,
    "specified_problem": [
        "175_F", "917_E", "414_E", "44_F", "901_E", "720_F", "786_D", "331_D3", "269_E", "1098_F", "1043_G", "1375_I", "1470_F", "117_E", "107_E", "223_D", "985_G", "1284_G", "331_D1",
        "1440_C2", "1340_F", "744_D", "656_E", "1114_F", "1178_G", "1419_F", "750_F", "504_E", "666_E", "1163_F", "1440_C1", "1439_A1", "280_D", "986_D", "1065_D", "1413_F", "348_E", "768_G", "1054_E", "609_E", "555_E", "1389_F", "607_E", "982_F", "418_D", "1303_G"
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
