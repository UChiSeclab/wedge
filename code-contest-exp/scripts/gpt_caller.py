from pathlib import Path
from typing import List, Dict
import requests
import subprocess
from evalplus.perf.sampling import post_process as evalplus_post_process
import openai
import time
import traceback

from utils import num_tokens_from_string
from config import config
from prompt import fill_multi_slow_solutions, fill_multi_slow_solutions_feedback, PromptTemplate
from select_solution import find_slow_fast_solution_cov_file
from select_input import (
    find_slow_fast_input_cov_file,
    find_slow_fast_input_cov_files_for_multi_solution,
    select_most_differentiating_input,
    select_slow_fast_input,
    select_slow_fast_input_for_multi_solution,
)

API_KEY = "sk-proj-agKWhu46RVJSx5PbRea7T3BlbkFJB3jZFl9KGevQ0QC9vatB"


def request(instruction: str) -> str:
    """Makes a request to gpt"""
    return request_conversation([{"role": "user", "content": instruction}]).choices[0].message.content


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

# TODO: add require_tools parameter, which uses stream=True and if the response does not call a tool, stop and retry immediately: https://community.openai.com/t/interrupting-completion-stream-in-python/30628/9
def request_conversation(msg_list:List[Dict], model='gpt-4o', tools=None, tool_choice="none", debugInfo=None, max_retry=5, temperature=0.8):
    openaiClient = openai.OpenAI(api_key=API_KEY)
    while True:
        try:
            if tools is not None:
                response = openaiClient.chat.completions.create(
                    model=model, 
                    messages=msg_list,
                    tools=tools,
                    tool_choice=tool_choice,
                    temperature=temperature
                )
            else:
                response = openaiClient.chat.completions.create(
                    model=model, 
                    messages=msg_list,
                    temperature=temperature
                )
            return response
        except openai.RateLimitError:
            traceback.print_exc()
            print('Rate limit exceeded, waiting 5 seconds...')
            time.sleep(5)
            continue
        except openai.InternalServerError or openai.APIError as e:
            max_retry -= 1
            if max_retry < 0:
                traceback.print_exc()
                break
            time.sleep(5)
            continue
        except Exception as e:
            traceback.print_exc()
            break
    return None


def write_test_generator(
    experiment_dir: Path,
    problem: Dict,
    selected_solution_codes: List[str],  # List of [fast_solution, slow_solution] or [slow_solution_1, slow_solution_2, ...]
    selected_solution_ids: List[str],
    prompt_template: PromptTemplate,
    ill_tests: str = None,
    error: subprocess.CalledProcessError = None,
    prompt_language: str = "java",
    num_tests: int = config["num_tests"],
):
    problem_id = problem["name"].split(".")[0]
    """Prompts llm to write a test generator."""
    experiment_name = prompt_template.experiment_name
    prompt = prompt_template.get_prompt_template_text()

    prompt = prompt.replace("<problem_statement>", problem["description"])
    prompt = prompt.replace("<number_of_tests>", str(num_tests))

    fill_solution_type, fill_input_type, fill_feedback_type = prompt_template.get_fill_types()


    # === fill solutions ===
    if fill_solution_type == "multi_slow_solution":
        prompt = fill_multi_slow_solutions(prompt, selected_solution_codes)
    elif fill_solution_type == "slow_fast_solution":
        prompt = prompt.replace("<fast_solution>", selected_solution_codes[0])
        prompt = prompt.replace("<slow_solution>", selected_solution_codes[1])
    elif fill_solution_type == "one_solution":
        prompt = prompt.replace("<one_solution>", selected_solution_codes[0])


    # === fill input(s) ===
    if fill_input_type == "most_differentiating_input":
        fast_solution_id, slow_solution_id = selected_solution_ids
        most_differentiating_input_file_name = select_most_differentiating_input(
            problem_id, fast_solution_id, slow_solution_id
        )
        input_file = (
            Path(config["problem_root_dir"])
            / problem_id
            / "input"
            / most_differentiating_input_file_name
        )

        prompt = prompt.replace("<input>", input_file.read_text())

    elif fill_input_type == "slow_fast_input":
        fast_solution_id, slow_solution_id = selected_solution_ids
        slow_input_file_name, fast_input_file_name = select_slow_fast_input(problem_id, slow_solution_id)

        slow_input_file, fast_input_file = [
            Path(config["problem_root_dir"])
            / problem_id
            / "input"
            / file_name \
                for file_name in [slow_input_file_name, fast_input_file_name]
        ]

        prompt = prompt.replace("<slow_input>", slow_input_file.read_text())
        prompt = prompt.replace("<fast_input>", fast_input_file.read_text())

    elif fill_input_type == "slow_fast_input_multi_solution":
        slow_solution_ids = selected_solution_ids
        slow_input_file_name, fast_input_file_name = select_slow_fast_input_for_multi_solution(
            problem_id, slow_solution_ids
        )

        slow_input_file, fast_input_file = [
            Path(config["problem_root_dir"])
            / problem_id
            / "input"
            / file_name \
                for file_name in [slow_input_file_name, fast_input_file_name]
        ]

        prompt = prompt.replace("<slow_input>", slow_input_file.read_text())
        prompt = prompt.replace("<fast_input>", fast_input_file.read_text())


    # === fill feedback ===
    if fill_feedback_type == "slow_fast_solution":
        slow_solution_cov_file, fast_solution_cov_file = find_slow_fast_solution_cov_file(
            problem_id, experiment_name, prompt_language
        )
        prompt = prompt.replace(
            "<slow_solution_coverage>", slow_solution_cov_file.read_text()
        )
        prompt = prompt.replace(
            "<fast_solution_coverage>", fast_solution_cov_file.read_text()
        )
    elif fill_feedback_type == "slow_fast_input":
        slow_input_cov_file, fast_input_cov_file = find_slow_fast_input_cov_file(
            problem_id, experiment_name, prompt_language
        )
        prompt = prompt.replace(
            "<slow_input_coverage>", slow_input_cov_file.read_text()
        )
        prompt = prompt.replace(
            "<fast_input_coverage>", fast_input_cov_file.read_text()
        )
    elif fill_feedback_type == "slow_fast_input_multi_solution":
        slow_input_cov_files, fast_input_cov_files = find_slow_fast_input_cov_files_for_multi_solution(
            problem_id, experiment_name, prompt_language
        )
        prompt = fill_multi_slow_solutions_feedback(
            prompt, fast_input_cov_files, slow_input_cov_files
        )

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
        if experiment_name.startswith("evalperf"):
            file.write(evalplus_post_process(gpt_response))
        else:
            file.write(cut_string(gpt_response))

    return cost
