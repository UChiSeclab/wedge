from pathlib import Path
from typing import Tuple, List, Optional, Literal, Dict
from tqdm import tqdm
from fire import Fire
import subprocess

from config import config
from gpt_caller import request, cut_string
from utils import filter_problems, get_cf_problems
from gpt_caller import request_conversation

"""
pipeline of input validator generation:
first prompt=>validator=>run on public/private test cases=>if any failure is 
detected, then regenerate validator (providing feedback, ie., error messages)

"""

REFELECT_MSG = '''
The validator script you provided is incorrect as it failed on some good test inputs that follow the constraints of the problem statement. Please reflect and try again. You must include "Shape of the input:" and "Constraints:" sections as well (in addition to the validator script) in your new response, and you might need to fix them if there are incorrect ones.
'''



def make_validator_gen_prompt(problem_statement: str) -> str:
    example_validator_script = '''\
import re
import sys

def column_to_number(column):
    """Convert a column in letter format (e.g., 'BC') to a number."""
    result = 0
    for char in column:
        result = result * 26 + (ord(char) - ord('A') + 1)
    return result

def number_to_column(number):
    """Convert a column number (e.g., 55) to letter format."""
    result = []
    while number > 0:
        number -= 1
        result.append(chr(number % 26 + ord('A')))
        number //= 26
    return ''.join(reversed(result))

def convert_coordinate(coordinate):
    """Determine the format and convert the coordinate to the other format."""
    if re.match(r"R\d+C\d+", coordinate):
        # It's in RXCY format (e.g., R23C55)
        row, col = map(int, re.findall(r"\d+", coordinate))
        
        # Assertions to check if row and column are within valid range
        assert 1 <= row <= 10**6, f"Row {row} out of bounds"
        assert 1 <= col <= 10**6, f"Column {col} out of bounds"
        
        col_letters = number_to_column(col)
        return f"{col_letters}{row}"
    else:
        # It's in Column-row format (e.g., BC23)
        match = re.match(r"([A-Z]+)(\d+)", coordinate)
        col_letters = match.group(1)
        row = int(match.group(2))
        
        # Assertions to check if row and column letters are within valid range
        assert 1 <= row <= 10**6, f"Row {row} out of bounds"
        col_number = column_to_number(col_letters)
        assert 1 <= col_number <= 10**6, f"Column {col_number} out of bounds"
        
        return f"R{row}C{col_number}"

def validate_input(input_file):
    with open(input_file, 'r') as f:
        n = int(f.readline().strip())  # number of coordinates
        assert 1 <= n <= 10**5, "Number of coordinates out of bounds"
        
        for i in range(n):
            coordinate = f.readline().strip()
            # Ensure the input format is either RXCY or Column-row
            assert re.match(r"(R\d+C\d+|[A-Z]+\d+)", coordinate), f"Invalid coordinate format: {coordinate}"
            # Perform conversion to check validity and run additional assertions
            convert_coordinate(coordinate)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        raise Exception("Usage: python validator.py <input_file>")

    input_file = sys.argv[1]
    validate_input(input_file)
'''

    prompt = f'''\
You are an AI programming assistant, proficient in Python. You will be given a problem statement of a programming task. The problem statement contains the description of the task and could contain some example inputs that are going to be fed to the solutions submitted by users. Your task is to read the problem statement, extract the constraints on the input (possibly also learn from the example inputs), and write a Python script that can validate whether a given input strictly follows the constraints. Note that 10000(10^5) is usally written as 105, 100000(10^6) is usually written as 106, etc. Specifically, the script should contain a number of assertion statements along with necessary helper functions. The script should takes an input from the input argument and successfully validate it if it follows the constraints. The script should print "VALID" if the input is valid, and raise an AssertionError with a readable error message if the input is invalid.

In summary, you need to 1) read the problem statement, 2) extract the constraints, 3) write a Python script that can validate the input. The script works by reading the input from the standard input, e.g., `python validator.py <input_file_path>` and print "VALID" if the input is valid, and raise an AssertionError with a readable error message if the input is invalid.

Example of generating a validator script:

### Instruction:
Generation a `validator.py` script for the following problem statement:

In the popular spreadsheets systems (for example, in Excel) the following numeration of columns is used. The first column has number A, the second — number B, etc. till column 26 that is marked by Z. Then there are two-letter numbers: column 27 has number AA, 28 — AB, column 52 is marked by AZ. After ZZ there follow three-letter numbers, etc.

The rows are marked by integer numbers starting with 1. The cell name is the concatenation of the column and the row numbers. For example, BC23 is the name for the cell that is in column 55, row 23. 

Sometimes another numeration system is used: RXCY, where X and Y are integer numbers, showing the column and the row numbers respectfully. For instance, R23C55 is the cell from the previous example.

Your task is to write a program that reads the given sequence of cell coordinates and produce each item written according to the rules of another numeration system.

Input

The first line of the input contains integer number n (1 ≤ n ≤ 105), the number of coordinates in the test. Then there follow n lines, each of them contains coordinates. All the coordinates are correct, there are no cells with the column and/or the row numbers larger than 106 .

Output

Write n lines, each line should contain a cell coordinates in the other numeration system.

Examples

Input

2
R23C55
BC23


Output

BC23
R23C55

### Response:
1. Shape of the input: 
The input consists of:
	- An integer n (1 ≤ n ≤ 10⁵), the number of cell coordinates to process.
	- n lines following that each contain a cell coordinate in one of two formats:
        1) RXCY format (e.g., R23C55), where X is a row number and Y is a column number.
        2) Column-row format (e.g., BC23), where the letters represent the column and the number represents the row.
2. Constraints:
	- 1 ≤ n ≤ 10⁵: The number of coordinates is between 1 and 100,000.
	- The cell coordinates are always valid.
	- Cell rows and columns are bounded:
        - Row numbers (X in RXCY) and (Row in Column-row) can be between 1 and 10⁶.
        - Column numbers (Y in RXCY) or their alphabetical equivalents in Column-row can be between 1 and 10⁶.
3. Python Script to Validate the Input:
The script will:
	- Read the input from a file.
	- Parse the format of each coordinate.
	- Determine whether it is in RXCY format or Column-row format.
	- Validate the format conversion, ensuring that it converts one format to the other.

The following Python script can be used to validate the input file (test_01.in) and process the output:
```python
{example_validator_script}
```

### Instruction:
Generation a `validator.py` script for the following problem statement:
{problem_statement}

### Response:
1. Shape of the input: 
'''

    return prompt

def construct_feedback_msg(
    failing_inputs: List[Tuple[Path, str]],
    max_num: int = 3,
    input_max_lines: int = 100
) -> str:
    # we only show the first max_num failing **public** inputs
    # to avoid leaking private test cases and overfitting to them
    feedback_msg = "The below inputs failed the validation:\n\n"
    failing_public_inputs = [(input_file, error_msg) for input_file, error_msg in failing_inputs if "public" in input_file.name]
    for input_file, error_msg in failing_public_inputs[:max_num]:
        input_content = "\n".join(input_file.read_text().split("\n")[:input_max_lines])
        feedback_msg += f"Input content:\n {input_content}\n"
        feedback_msg += f"Error message:\n {error_msg}\n\n"
    
    if len(failing_public_inputs) == 0:
        # no public inputs failed, fall back to self-reflect mode (no feedback)
        feedback_msg = ""

    return feedback_msg


def validate_validator(validator_file: Path, input_dir: Path, skip_generated_tests: bool = True) -> List[Tuple[Path, str]]:
    input_files = list(input_dir.glob("*.in"))
    failing_inputs = []
    for input_file in input_files:
        if skip_generated_tests and "generated" in input_file.name:
            continue
        try:
            res = subprocess.run(
                ["python", validator_file.as_posix(), input_file.as_posix()],
                capture_output=True,
                text=True,
                timeout=10,
                check=True
            )
            print(f"Output for {input_file}: {res.stdout}", end="")
        except subprocess.CalledProcessError as e:
            failing_inputs.append((input_file, e.stderr))
            print(f"Validation failed for {input_file.absolute()}: {e.stderr}")
        except subprocess.TimeoutExpired as e:
            failing_inputs.append((input_file, "Validation timed out"))
            print(f"[Warning] Validation timed out for {input_file.absolute()}: {e}")

    return failing_inputs


def prompt_and_dump_results(
    initial_prompt:str,
    validator_gen_dir: Path,
    input_dir: Path,
    problem_id: str,
    try_cnt: int,
    msg_list:List[Dict],
    mode:str=None,
    feedback_msg:Optional[str]=None
) -> Tuple[bool, List[Tuple[Path, str]]]:
    validator_gen_try_cnt_dir = validator_gen_dir / f"try_{try_cnt}"
    validator_gen_try_cnt_dir.mkdir(parents=True, exist_ok=True)
    ori_msg_list = msg_list[:]
    try:
        if mode in ["direct", "resample"]:
            # keep the msg_list
            msg_list.append({"role": "user", "content": initial_prompt})
        elif mode in ["self_reflect", "self_reflect_feedback"]:
            if try_cnt == 0:
                msg_list.append({"role": "user", "content": initial_prompt})
            else:
                msg_list.append(
                    {
                        "role": "user",
                        "content": REFELECT_MSG
                            if mode == "self_reflect" else REFELECT_MSG + feedback_msg
                    }
                )
        gpt_response = request_conversation(
            msg_list,
            max_retry=5,
            temperature=0.8
        ).choices[0].message.content
        validator_script_content = cut_string(gpt_response)
    except Exception as e:
        print(f"Failed to generate validator for problem {problem_id}: {e}")
        return False, []
    
    msg_list.append({"role": "assistant", "content": gpt_response})

    with open(validator_gen_try_cnt_dir / "response.txt", "w") as f:
        f.write(gpt_response)

    with open(validator_gen_try_cnt_dir / "validator.py", "w") as f:
        f.write(validator_script_content)

    with open(validator_gen_try_cnt_dir / "conversations.txt", "w") as f:
        # write formatted conversation
        for msg in msg_list:
            f.write(f"{msg['role']}: {msg['content']}\n")

    failing_inputs = validate_validator(    
        validator_gen_try_cnt_dir / "validator.py",
        input_dir
    )

    if mode in ["direct", "resample"]:
        # reset the msg_list for the next try for resample
        msg_list = ori_msg_list

    return len(failing_inputs) == 0, failing_inputs

def generate_validator(problem_root_dir: Path, problem: Dict, mode: str, max_try: int = 5) -> bool:
    problem_id = problem["name"].split(".")[0]
    problem_dir = problem_root_dir / problem_id
    initial_prompt = make_validator_gen_prompt(problem["description"])
    alphacode_input_dir = problem_dir / "input"
    validator_gen_mode_dir = problem_dir / "validator_gen" / mode

    conversation = [{"role": "system", "content": "You are a helpful assistant good at coding."}]
    if mode == "direct":
        success, _ = prompt_and_dump_results(initial_prompt, validator_gen_mode_dir, alphacode_input_dir, problem_id, 0, conversation, mode)
        if not success:
            print(f"[Warning] [{mode}] Failed to generate validator for problem {problem_id}")
            # we might want to delete the directory
            return False
    elif mode in ["resample", "self_reflect"]:
        for i in range(max_try):
            success, _ = prompt_and_dump_results(initial_prompt, validator_gen_mode_dir, alphacode_input_dir, problem_id, i, conversation, mode)
            if success:
                break
            else:
                if i == max_try - 1:
                    print(f"[Warning] [{mode}] Failed to generate validator for problem {problem_id} after {max_try} tries")
                    return False
    elif mode == "self_reflect_feedback":
        feedback_msg = None
        failing_inputs = []
        for i in range(max_try):
            if i > 0:
                feedback_msg = construct_feedback_msg(failing_inputs)
            success, failing_inputs = prompt_and_dump_results(initial_prompt, validator_gen_mode_dir, alphacode_input_dir, problem_id, i, conversation, mode, feedback_msg)
            if success:
                break
            else:
                if i == max_try - 1:
                    print(f"[Warning] [{mode}] Failed to generate validator for problem {problem_id} after {max_try} tries")
                    return False

    return True

def main(
    problem_root_dir: str = config["problem_root_dir"],
    mode: Literal["direct", "resample", "self_reflect", "self_reflect_feedback"] = "direct"
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    
    for problem in tqdm(filtered_problems):
        if not generate_validator(problem_root_dir, problem, mode):
            print(f"Failed to generate validator for problem {problem['name'].split('.')[0]}")
        else:
            status_file = problem_root_dir / problem["name"].split(".")[0] / "validator_gen" / mode / "VAL_GT_INPUT_PASS"
            status_file.touch()

if __name__ == "__main__":
    Fire(main)
