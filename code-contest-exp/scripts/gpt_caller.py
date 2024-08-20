from pathlib import Path
from typing import List, Dict, Literal
import requests
import subprocess

from utils import num_tokens_from_string
from config import config

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
    solution_codes: List[str],  # List of [fast_solution, slow_solution]
    ill_tests: str = None,
    error: subprocess.CalledProcessError = None,
    prompt_template: Literal[
        "prompt_template.txt", "prompt_template_with_feedback.txt"
    ] = "prompt_template.txt",
    use_feedback: bool = False,
):
    """Prompts llm to write a test generator."""
    with open(prompt_template, "r", encoding="utf-8") as file:
        prompt = file.read()

    prompt = prompt.replace("<problem_statement>", problem["description"])
    prompt = prompt.replace("<fast_solution>", solution_codes[0])
    prompt = prompt.replace("<slow_solution>", solution_codes[1])

    problem_id = problem["name"].split(".")[0]

    if use_feedback:
        assert prompt_template == "prompt_template_with_feedback.txt"
        cov_dir = (
            Path(config["coverage_hit_count_output_dir"])
            / problem_id
            / experiment_dir.name
            / str(config["prompt_language"])
        )
        cov_files = [file.absolute() for file in Path(cov_dir).rglob("*.cov")]
        assert len(cov_files) == 2, f"cov_files: {cov_files}"
        fast_solution_cov_file = [
            file for file in cov_files if file.parent.name == "fast"
        ][0]
        slow_solution_cov_file = [
            file for file in cov_files if file.parent.name == "slow"
        ][0]
        input_id = fast_solution_cov_file.parent.parent.name
        input_file = (
            Path(config["problem_root_dir"]) / problem_id / "input" / f"{input_id}.in"
        )

        prompt = prompt.replace("<fast_coverage>", fast_solution_cov_file.read_text())
        prompt = prompt.replace("<slow_coverage>", slow_solution_cov_file.read_text())
        prompt = prompt.replace("<input>", input_file.read_text())
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
