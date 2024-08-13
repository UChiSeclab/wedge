"""Configuration setting."""
from common import Language

config = {
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "none",  # set it to 'none' to run it on alpha code tests
    "solution_selection": "random",
    "manual_prompt": True,
    "prompt_language": Language.JAVA,
    "repeat_test": 3,
    "specified_problem": ["133_E", "1203_E", "769_D", "1379_D", "1536_C"],
}

abandoned_list = [
    "print any of them",
    "If there are multiple solutions, you may output any.",
    "If there are several such trees, output any.",
    "If there are multiple possible solutions",
    "If there are several solutions",
    "The answer will be considered correct,",
]
