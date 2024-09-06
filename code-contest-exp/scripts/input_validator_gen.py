from pathlib import Path
from typing import Dict
from tqdm import tqdm
from fire import Fire

from config import config
from gpt_caller import request, cut_string
from utils import filter_problems, get_cf_problems

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


def generate_validator(problem_root_dir: Path, problem: Dict) -> str:
    problem_id = problem["name"].split(".")[0]
    problem_dir = problem_root_dir / problem_id
    prompt = make_validator_gen_prompt(problem["description"])
    validator_gen_dir = problem_dir / "validator_gen"
    validator_gen_dir.mkdir(parents=True, exist_ok=True)

    gpt_response = request(prompt)

    with open(validator_gen_dir / "prompt.txt", "w") as f:
        f.write(prompt)

    with open(validator_gen_dir / "response.txt", "w") as f:
        f.write(gpt_response)

    with open(validator_gen_dir / "validator.py", "w") as f:
        f.write(cut_string(gpt_response))

    return gpt_response

def main(
    problem_root_dir: str = config["problem_root_dir"]
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )
    
    for problem in tqdm(filtered_problems):
        generate_validator(problem_root_dir, problem)

if __name__ == "__main__":
    Fire(main)
