from pathlib import Path
from typing import List, Dict, Literal
import requests
import subprocess

from utils import num_tokens_from_string
from config import config
from prompt_utils import fill_multi_slow_solutions, fill_multi_slow_solutions_feedback
from select_solution import find_slow_fast_solution_cov_file
from select_input import find_slow_fast_input_cov_file, find_slow_fast_input_cov_files_for_multi_solution

API_KEY = "sk-proj-agKWhu46RVJSx5PbRea7T3BlbkFJB3jZFl9KGevQ0QC9vatB"


def request(instruction: str) -> str:
    """Makes a request to gpt"""
    url = "https://api.openai.com/v1/chat/completions"
    headers = {"Content-Type": "application/json", "Authorization": f"Bearer {API_KEY}"}
    data = {
        "model": "gpt-4o",
        "messages": [{"role": "user", "content": instruction}],
        "temperature": 0.8,
    }
    response = requests.post(url, json=data, headers=headers, timeout=60)
    if response.status_code == 200:
        result = response.json()
        return result["choices"][0]["message"]["content"]


def cut_string(input_string: str, begin_token="```python\n", end_token="```") -> str:
    """Extracts the code from gpt response."""
    start_index = input_string.find(begin_token)
    if start_index == -1:
        return input_string
    start_index += len(begin_token)
    end_index = input_string.find(end_token, start_index)
    if end_index == -1:
        return input_string[start_index:]
    result = input_string[start_index:end_index]
    return result


def write_test_generator(
    experiment_dir: Path,
    problem: Dict,
    solution_codes: List[str],  # List of [fast_solution, slow_solution] or [slow_solution_1, slow_solution_2, ...]
    ill_tests: str = None,
    error: subprocess.CalledProcessError = None,
    prompt_template: str = "prompt_template.txt",
    prompt_language: str = "java",
    feedback_prompt_type: Literal["diff_solution", "diff_input", "multi_solution_diff_input"] = None,
    num_tests: int = config["num_tests"],
):
    """Prompts llm to write a test generator."""
    with open(prompt_template, "r", encoding="utf-8") as file:
        prompt = file.read()

    prompt = prompt.replace("<problem_statement>", problem["description"])
    prompt = prompt.replace("<number_of_tests>", str(num_tests))

    # fill solutions
    if feedback_prompt_type == "multi_solution_diff_input":
        prompt = fill_multi_slow_solutions(prompt, solution_codes)
    else:
        prompt = prompt.replace("<fast_solution>", solution_codes[0])
        prompt = prompt.replace("<slow_solution>", solution_codes[1])

    problem_id = problem["name"].split(".")[0]

    # fill input(s) and feedback
    if feedback_prompt_type == "diff_solution":
        assert prompt_template == "prompt_template_with_feedback_diff_solution.txt"
        slow_solution_cov_file, fast_solution_cov_file = find_slow_fast_solution_cov_file(
            problem_id, experiment_dir.name, prompt_language
        )
        slow_input_id = fast_solution_cov_file.parent.parent.name
        input_file = (
            Path(config["problem_root_dir"])
            / problem_id
            / "input"
            / f"{slow_input_id}.in"
        )

        prompt = prompt.replace(
            "<fast_solution_coverage>", fast_solution_cov_file.read_text()
        )
        prompt = prompt.replace(
            "<slow_solution_coverage>", slow_solution_cov_file.read_text()
        )
        prompt = prompt.replace("<input>", input_file.read_text())

    elif feedback_prompt_type == "diff_input":
        assert prompt_template == "prompt_template_with_feedback_diff_input.txt"
        slow_input_cov_file, fast_input_cov_file = find_slow_fast_input_cov_file(
            problem_id, experiment_dir.name, prompt_language
        )
        slow_input_id = slow_input_cov_file.parent.parent.name
        fast_input_id = fast_input_cov_file.parent.parent.name

        slow_input_file, fast_input_file = [
            Path(config["problem_root_dir"])
            / problem_id
            / "input"
            / f"{input_id}.in" \
                for input_id in [slow_input_id, fast_input_id]
        ]

        prompt = prompt.replace(
            "<slow_input_coverage>", slow_input_cov_file.read_text()
        )
        prompt = prompt.replace(
            "<fast_input_coverage>", fast_input_cov_file.read_text()
        )
        prompt = prompt.replace("<slow_input>", slow_input_file.read_text())
        prompt = prompt.replace("<fast_input>", fast_input_file.read_text())

    elif feedback_prompt_type == "multi_solution_diff_input":
        assert prompt_template == "prompt_template_with_feedback_multi_solution_diff_input.txt"
        slow_input_cov_files, fast_input_cov_files = find_slow_fast_input_cov_files_for_multi_solution(
            problem_id, experiment_dir.name, prompt_language
        )
        slow_input_id = slow_input_cov_files[0].parent.parent.parent.name
        fast_input_id = fast_input_cov_files[0].parent.parent.parent.name
        slow_input_file, fast_input_file = [
            Path(config["problem_root_dir"])
            / problem_id
            / "input"
            / f"{input_id}.in" \
                for input_id in [slow_input_id, fast_input_id]
        ]

        prompt = fill_multi_slow_solutions_feedback(
            prompt, fast_input_cov_files, slow_input_cov_files
        )

        prompt = prompt.replace("<slow_input>", slow_input_file.read_text())
        prompt = prompt.replace("<fast_input>", fast_input_file.read_text())
    else:
        assert prompt_template == "prompt_template.txt"

    gpt_response = request(prompt)
    cost = (
        num_tokens_from_string(prompt) * 5 / 1000000
        + num_tokens_from_string(gpt_response) * 15 / 1000000
    )

    with open(experiment_dir / "prompt.txt", "w", encoding="utf-8") as file:
        file.write(prompt)
    with open(experiment_dir / "gpt_response.txt", "w", encoding="utf-8") as file:
        file.write(gpt_response)
    with open(experiment_dir / "gen.py", "w", encoding="utf-8") as file:
        file.write(cut_string(gpt_response))

    return cost
