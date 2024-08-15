from pathlib import Path
from typing import List
import requests
import subprocess

from utils import num_tokens_from_string

API_KEY = "sk-proj-agKWhu46RVJSx5PbRea7T3BlbkFJB3jZFl9KGevQ0QC9vatB"


def request(instruction: str) -> str:
    """Makes a request to gpt"""
    url = "https://api.openai.com/v1/chat/completions"
    headers = {"Content-Type": "application/json", "Authorization": f"Bearer {API_KEY}"}
    data = {
        "model": "gpt-4o",
        "messages": [{"role": "user", "content": instruction}],
        "temperature": 1,
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
    problem_description: str,
    solution_codes: List[str],
    ill_tests: str = None,
    error: subprocess.CalledProcessError = None,
):
    """Prompts llm to write a test generator."""
    with open("./prompt_template.txt", "r", encoding="utf-8") as file:
        prompt = file.read()

    prompt = prompt.replace("<problem_statement>", problem_description)
    prompt = prompt.replace("<fast_solution>", solution_codes[0])
    prompt = prompt.replace("<slow_solution>", solution_codes[1])

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
