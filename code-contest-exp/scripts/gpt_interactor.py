import os
import json
import requests
import random
import subprocess

import tiktoken

api_key = "sk-proj-agKWhu46RVJSx5PbRea7T3BlbkFJB3jZFl9KGevQ0QC9vatB"

url = "https://api.openai.com/v1/chat/completions"
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Bearer {api_key}"
}

def num_tokens_from_string(string: str, encoding_name="cl100k_base") -> int:
    encoding = tiktoken.get_encoding(encoding_name)
    num_tokens = len(encoding.encode(string))
    return num_tokens

def request(instruction):
  data = {
    "model": "gpt-4o",
    "messages": [{"role": "user", "content": instruction}],
    "temperature": 1
  }
  response = requests.post(url, json=data, headers=headers)
  if response.status_code == 200:
    result = response.json()
    return result['choices'][0]['message']['content']

def cut_string(input_string, begin_token='```python', end_token='```'):
  start_index = input_string.find(begin_token)
  if start_index == -1:
    return input_string
  start_index += len(begin_token)
  end_index = input_string.find(end_token, start_index)
  if end_index == -1:
    return input_string[start_index:]
  result = input_string[start_index:end_index]
  return result

def write_test_generator(problem_dir, exp_type, problem_description, solution_codes, ill_tests:str=None, error:subprocess.CalledProcessError=None):
    prompt = "Problem Statement\n" + problem_description + "\n------------------------------\n"
    prompt += "\n------------------------------\n".join(solution_codes) + "\n------------------------------\n"
    prompt += "\n\nWrite a python script that can generate test cases for a programming problem with the provided problem statement and solution codes. Notice that something like \"10^5\" will be written as \"105\" in most of the time, in this case, you should regard the maxmimum constraint as 100000 instead of 105. The test case should be focus on exhausting the code to get time limit exceeded and should follow the input format. Note that you need to extract the input constraints from the problem statement and encode the constraints into the test case generator. The python script should be able to read an argument (the only argument) which specifies a directory and write all the testcases as 'test_01.in', 'test_02.in', ... into the directory. Please generate 10 test cases. "
    
    if ill_tests:
        prompt += "\n------------------------------\n"
        prompt += "The following previously test generation script is problematic. Please avoid generating problematic tests.\n"
        prompt += ill_tests
        assert error is not None
        prompt += "\n------------------------------\n"
        prompt += "The following error occurred during the execution of the above tests.\n"
        # append the error message and traceback
        prompt += error.stderr

    res = request(prompt)
    cost = num_tokens_from_string(prompt) * 5 / 1000000 + num_tokens_from_string(res) * 15 / 1000000

    # Save the actual response of gpt for reference
    with open(f"{problem_dir}/problem_statement.txt", "w") as file:
        file.write(problem_description)

    # Save the actual response of gpt for reference
    with open(f"{problem_dir}/{exp_type}/prompt.txt", "w") as file:
        file.write(prompt)

    # Save the actual response of gpt for reference
    with open(f"{problem_dir}/{exp_type}/gpt_response.txt", "w") as file:
        file.write(res)

    # Save the generated script as gen.py
    with open(f"{problem_dir}/{exp_type}/gen.py", "w") as file:
        file.write(cut_string(res))
    
    return cost