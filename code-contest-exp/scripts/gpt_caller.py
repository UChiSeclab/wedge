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
    prompt = (
        "Problem Statement\n"
        + problem_description
        + "\n------------------------------\n"
    )
    prompt += (
        "\n------------------------------\n".join(solution_codes)
        + "\n------------------------------\n"
    )
    prompt += "\n\nWrite a python script that can generate test cases for a programming problem with the provided problem statement and solution codes. Notice that something like \"10^5\" will be written as \"105\" in most of the time, in this case, you should regard the maxmimum constraint as 100000 instead of 105. The test case should be focus on exhausting the code to get time limit exceeded and should follow the input format. Note that you need to extract the input constraints from the problem statement and encode the constraints into the test case generator. The python script should be able to read an argument (the only argument) which specifies a directory and write all the testcases as 'test_01.in', 'test_02.in', ... into the directory. Please generate 10 test cases. "

    if ill_tests:
        prompt += "\n------------------------------\n"
        prompt += "The following previously test generation script is problematic. Please avoid generating problematic tests.\n"
        prompt += ill_tests
        assert error is not None
        prompt += "\n------------------------------\n"
        prompt += (
            "The following error occurred during the execution of the above tests.\n"
        )
        # append the error message and traceback
        prompt += error.stderr

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
