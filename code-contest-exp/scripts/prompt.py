from typing import List
from pathlib import Path

from config import config

class PromptTemplate:
    def __init__(self, prompt_template_name: str, experiment_name: str):
        self.prompt_template_text = (Path(config["prompt_template_root_dir"]) / prompt_template_name).read_text()
        self.experiment_name = experiment_name
        assert experiment_name in [
            "time_contrast",
            "feedback_diff_solution",
            "feedback_diff_input",
            "feedback_multi_solution_diff_input",
            "multi_solution_diff_input",
            "diff_solution_one_input",
            "plain_problem",
            "slow_solution",
            "random_solution",
        ], f"experiment_name: {experiment_name}"
        self.select_solution_type = self.get_select_solution_type(experiment_name)
        self.fill_input_type = self.get_fill_input_type()
        self.fill_solution_type = self.get_fill_solution_type()
        self.fill_feedback_type = self.get_fill_feedback_type()
        
    def get_prompt_template_text(self) -> str:
        return self.prompt_template_text

    @staticmethod
    def get_select_solution_type(experiment_name: str) -> str:
        """Get the solution selection type based on the experiment name."""
        if experiment_name in [
            "time_contrast", # no input, diff solution
            "feedback_diff_solution", # one input, diff solution with coverage
            "feedback_diff_input", # slow fast input, slow solution with coverage
            "diff_solution_one_input", # one input, diff solution (no feedback)
        ]:
            return "slow_fast"
        elif experiment_name in [
            "feedback_multi_solution_diff_input", # slow fast input, 5 slow solutions with coverage
            "multi_solution_diff_input" # slow fast input, 5 slow solutions (no feedback)
        ]:
            return "multi_slow"
        elif experiment_name in [
            "slow_solution", # no input, slow solution
        ]:
            return "slow"
        elif experiment_name in [
            "random_solution", # no input, random solution
        ]:
            return "random"
        elif experiment_name in [
            "plain_problem", # no input, no solution
        ]:
            return "no_solution"
        else:
            raise ValueError(f"Unknown experiment name: {experiment_name}")

    def get_fill_solution_type(self) -> str:
        """Get the fill solution type based on the experiment name."""
        if self.experiment_name in [
            "time_contrast",
            "diff_solution_one_input",
            "feedback_diff_input",
            "feedback_diff_solution",
        ]:
            return "slow_fast_solution"
        elif self.experiment_name in [
            "feedback_multi_solution_diff_input",
            "multi_solution_diff_input",
        ]:
            return "multi_slow_solution"
        elif self.experiment_name in [
            "slow_solution",
            "random_solution",
        ]:
            return "one_solution"
        elif self.experiment_name in [
            "plain_problem",
        ]:
            return "no_solution"
        else:
            raise ValueError(f"Unknown experiment name: {self.experiment_name}")

    def get_fill_input_type(self) -> str:
        """Get the fill input type based on the experiment name."""
        if self.experiment_name in [
            "diff_solution_one_input",
            "feedback_diff_solution",
        ]:
            return "most_differentiating_input"
        elif self.experiment_name in [
            "feedback_diff_input",
        ]:
            return "slow_fast_input"
        elif self.experiment_name in [
            "feedback_multi_solution_diff_input",
            "multi_solution_diff_input",
        ]:
            return "slow_fast_input_multi_solution"
        elif self.experiment_name in [
            "time_contrast",
            "plain_problem",
            "slow_solution",
            "random_solution",
        ]:
            return "no_input"
        else:
            raise ValueError(f"Unknown experiment name: {self.experiment_name}")

    def get_fill_feedback_type(self) -> str:
        """Get the fill feedback type based on the experiment name."""
        if self.experiment_name in [
            "time_contrast",
            "multi_solution_diff_input",
            "plain_problem",
            "slow_solution",
            "random_solution",
            "diff_solution_one_input",
        ]:
            return "no_feedback"
        elif self.experiment_name in [
            "feedback_diff_solution",
        ]:
            return "slow_fast_solution"
        elif self.experiment_name in [
            "feedback_diff_input",
        ]:
            return "slow_fast_input"
        elif self.experiment_name in [
            "feedback_multi_solution_diff_input",
        ]:
            return "slow_fast_input_multi_solution"
        else:
            raise ValueError(f"Unknown experiment name: {self.experiment_name}")

    def get_fill_types(self) -> List[str]:
        return self.fill_solution_type, self.fill_input_type, self.fill_feedback_type


"""fill the slowest solution code into the prompt"""
def fill_multi_slow_solutions(prompt:str, top_k_slow_solution_codes:List[str]) -> str:
    assert "<k_slowest_solutions>" in prompt
    infill = ""
    k = len(top_k_slow_solution_codes)
    for i in range(k):
        infill += f"\nSlow program #{i+1}:\n"
        infill += top_k_slow_solution_codes[i]
        infill += "\n"
    prompt = prompt.replace("<k_slowest_solutions>", infill)

    return prompt


"""fill the slowest solution code_with_coverage into the prompt"""
def fill_multi_slow_solutions_feedback(prompt:str, fast_input_cov_files:List[Path], slow_input_cov_files:List[Path]):
    assert "<k_slowest_solutions_fast_coverage>" in prompt
    assert "<k_slowest_solutions_slow_coverage>" in prompt

    k = len(fast_input_cov_files)
    fast_cov_infill = ""
    slow_cov_infill = ""
    fast_input_cov_files.sort(key=lambda x: int(x.parent.name.split("_")[-1]))
    slow_input_cov_files.sort(key=lambda x: int(x.parent.name.split("_")[-1]))

    for i in range(k):
        fast_cov_infill += f"Slow program #{i+1} source with coverage and hit count under fast input data:"
        fast_cov_infill += fast_input_cov_files[i].read_text()
        fast_cov_infill += "\n"

        slow_cov_infill += f"Slow program #{i+1} source with coverage and hit count under slow input data:"
        slow_cov_infill += slow_input_cov_files[i].read_text()
        slow_cov_infill += "\n"

    prompt = prompt.replace("<k_slowest_solutions_fast_coverage>", fast_cov_infill)
    prompt = prompt.replace("<k_slowest_solutions_slow_coverage>", slow_cov_infill)

    return prompt