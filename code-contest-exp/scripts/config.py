"""Configuration setting."""
from enum import Enum

class Language(Enum):
    """Define the enum for programming language."""
    UNKNOWN_LANGUAGE = 0
    PYTHON = 1
    CPP = 2
    PYTHON3 = 3
    JAVA = 4

    def __str__(self):
        return self.name.lower()

    @staticmethod
    def idx_to_lang(idx: int) -> str:
        """Translate language index to name."""
        return Language(idx).name.lower()

config = {
    "output_file": "result.json",
    "problem_root_dir": "./problems",
    "max_time_limit": 60,
    "experiment_name": "test",
    "solution_selection": "random",
    "prompt_language": Language.JAVA
}

problem_list = ["1379_D. New Passenger Trams"]

abandoned_list = [
    "print any of them",
    "If there are multiple solutions, you may output any.",
    "If there are several such trees, output any.",
    "If there are multiple possible solutions",
    "If there are several solutions",
    "The answer will be considered correct,",
]