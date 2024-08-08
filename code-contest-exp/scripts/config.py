"""Configuration setting."""
from common import Language

config = {
    "output_file": "java_result.json",
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "none",
    "solution_selection": "random",
    "prompt_language": Language.JAVA,
    "repeat_test": 5
}

abandoned_list = [
    "print any of them",
    "If there are multiple solutions, you may output any.",
    "If there are several such trees, output any.",
    "If there are multiple possible solutions",
    "If there are several solutions",
    "The answer will be considered correct,",
]