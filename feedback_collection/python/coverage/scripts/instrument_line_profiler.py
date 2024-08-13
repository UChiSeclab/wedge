import ast
from pathlib import Path
from typing import Dict, Tuple, Literal
from shutil import copyfile
import subprocess
import xml.etree.ElementTree as ET


def check_syntax(code: str) -> bool:
    """
    Check the syntax of the code snippet

    Args:
    - code (str): Code snippet to be checked
    """

    try:
        ast.parse(code)
        return True
    except SyntaxError:
        return False


def find_main_block(ori_code_file: Path) -> int:
    """
    Find the line number of the main block 'if __name__ == "__main__":' , -1 if not found
    """
    with open(ori_code_file, "r") as file:
        ori_code_lines = file.readlines()
    for line_no, line in enumerate(ori_code_lines, start=1):
        # if the line contains 'if __name__ == "__main__":'
        if "__name__" in line and "__main__" in line:
            return line_no
    return -1


def wrap_standalone_code(
    ori_code_file: Path, cur_code_file: Path, line_no_table: Dict[int, int]
) -> Tuple[str, Dict[int, int]]:
    """
    Wraps standalone code snippets into a main function

    Args:
    - ori_code_file (Path): Original code file
    - cur_code_file (Path): Code file (possibly transformed) with code snippet to be wrapped
    - line_no_table (Dict[int, int]): Table mapping line numbers in the original code to line numbers in the current code
    """

    ori_code = ori_code_file.read_text()
    cur_code = cur_code_file.read_text()
    with open(cur_code_file, "r") as file:
        cur_code_lines = file.readlines()
    if line_no_table is None or len(line_no_table) == 0:
        assert (
            ori_code == cur_code
        ), "The line number table is required when the code is transformed"
        num_lines = len(cur_code_lines)
        line_no_table = {i: i for i in range(1, num_lines + 1)}

    # detect the code snippet outside functions (code after the last function)
    tree = ast.parse(cur_code)
    last_function_end_lineno = 0
    for node in tree.body:
        if isinstance(node, ast.FunctionDef):
            last_function_end_lineno = node.end_lineno

    assert last_function_end_lineno > 0, "No function definition found"
    cur_code_stand_alone_lines = cur_code_lines[
        last_function_end_lineno:
    ]  # code after the last function, from line last_function_end_lineno + 1
    if len(cur_code_stand_alone_lines) == 0:
        print("[warning] No code snippet found outside functions (end of file)")
    else:
        # wrap the code snippet into a wrapper_main function
        main_block_line_no = find_main_block(ori_code_file)
        if main_block_line_no == -1:
            # main block not found, add a wrapper_main function
            main_code = "def wrapper_main():"
            for line in cur_code_stand_alone_lines:
                main_code += f"    {line}"
            main_code += "\nwrapper_main()"

            for line_no in range(last_function_end_lineno + 1, len(cur_code_lines) + 1):
                # note that some lines may be empty
                line_no_table[line_no] = line_no_table[line_no] + 1

            cur_code = (
                "".join(cur_code_lines[:last_function_end_lineno]) + "\n" + main_code
            )
        else:
            # main block found, replace the main block with "def wrapper_main():"
            # we assume that the main block is at the end of the file, no other
            # code after the main block
            cur_code_lines[main_block_line_no - 1] = "def wrapper_main():\n"
            cur_code = "".join(cur_code_lines)
            cur_code += "\nwrapper_main()"

        with open(cur_code_file, "w") as file:
            file.write(cur_code)

    return cur_code, line_no_table


def instrument(
    ori_code_file_path: str,
    cur_code_file_path: str = "",
    line_no_table: Dict[int, int] = {},
) -> Tuple[str, Dict[int, int]]:
    """
    Wraps standalone code snippets into a main function

    Args:
    - ori_code_file_path (str): Path to the original code file
    - cur_code_file_path (str): Path to the code file (possibly transformed) with code snippet to be wrapped
    - line_no_table (Dict[int, int]): Table mapping line numbers in the original code to line numbers in the current code
    """

    ori_code_file = Path(ori_code_file_path).absolute()
    if cur_code_file_path:
        cur_code_file = Path(cur_code_file_path).absolute()
    else:
        # path/to/original_code.py -> path/to/original_code_wrapped.py
        cur_code_file = (
            ori_code_file.parent / f"{ori_code_file.stem}_wrapped{ori_code_file.suffix}"
        )
    if not cur_code_file.exists():
        cur_code_file.parent.mkdir(parents=True, exist_ok=True)
        copyfile(ori_code_file, cur_code_file)
    cur_code, line_no_table = wrap_standalone_code(
        ori_code_file, cur_code_file, line_no_table
    )

    return cur_code, line_no_table


def run_kernprof(cur_code_file: Path, input_file: Path) -> str:
    kernprof_cmd = f"python -m kernprof -p {cur_code_file.as_posix()} -lv {cur_code_file.as_posix()} < {input_file.as_posix()}"
    output = subprocess.run(
        kernprof_cmd, shell=True, capture_output=True
    ).stdout.decode()

    return output


def parse_kernprof_output(output: str) -> Dict[int, int]:
    # parse the output of kernprof and return the line number and hit count
    # note that the line number is with respect to the instrumented code
    lines = output.split("\n")
    line_hit_count = {}
    for line in lines:
        elements = line.strip().split()
        if len(elements) >= 2:
            if elements[0].isdigit():
                line_no = int(elements[0])
                if not elements[1].isdigit():
                    if not elements[1].startswith("def"):
                        line_hit_count[line_no] = 0
                else:
                    if len(elements) >= 6:
                        hit_count = int(elements[1])
                        line_hit_count[line_no] = hit_count

    return line_hit_count


def get_ori_line_hit_count(
    instrumented_line_hit_count: Dict[int, int], line_no_table: Dict[int, int]
) -> Dict[int, int]:
    # map the line number in the instrumented code to the original code
    ori_line_hit_count = {}
    for line_no, hit_count in instrumented_line_hit_count.items():
        ori_line_no = [
            ori_line_no
            for ori_line_no, instrumented_line_no in line_no_table.items()
            if instrumented_line_no == line_no
        ][0]
        ori_line_hit_count[ori_line_no] = hit_count

    return ori_line_hit_count


def record_file_with_coverage_and_hit_count(
    src_file: Path,
    file_hit_count: Dict[int, int],
    coveragepy_coverage: Dict[int, str],
    output_cov_file: Path,
):
    file_with_coverage_lines = []
    if output_cov_file == Path(""):
        output_cov_file = src_file.with_suffix(f".py.cov")

    for line_no, line in enumerate(src_file.read_text().splitlines(), start=1):
        if line_no in file_hit_count:
            hit_count = file_hit_count[line_no]
            if hit_count > 0:
                if coveragepy_coverage.get(line_no) == "PARTIALLY_COVERED":
                    line = (
                        f"/* line {line_no} PARTIALLY_COVERED, hit count: {hit_count} */ "
                        + line
                    )
                else:
                    line = (
                        f"/* line {line_no} COVERED, hit count: {hit_count} */ " + line
                    )
            else:
                line = f"/* line {line_no} NOT_COVERED */ " + line

        file_with_coverage_lines.append(line)

    output_cov_file.write_text("\n".join(file_with_coverage_lines))


def parse_coveragepy_report(
    coveragepy_report_file: Path,
) -> Dict[int, Literal["COVERED", "NOT_COVERED", "PARTIALLY_COVERED"]]:
    # Parse the XML
    root = ET.parse(coveragepy_report_file).getroot()
    src_file_coverage = {}
    for line_element in root.iter("line"):
        line_nr = int(line_element.get("number"))
        is_branch = line_element.get("branch", "false") == "true"
        cov_status = "COVERED" if int(line_element.get("hits")) > 0 else "NOT_COVERED"
        if is_branch:
            if cov_status == "COVERED":
                condition_coverage = line_element.get("condition-coverage")
                cov_status = (
                    "PARTIALLY_COVERED"
                    if "100%" not in condition_coverage
                    else "COVERED"
                )

        src_file_coverage[line_nr] = cov_status

    return src_file_coverage


def main(
    ori_code_file_path: str,
    input_file_path: str,
    include_plain_coverage: bool = True,  # collected from coverage.py
    include_hit_count: bool = True,  # collected from line_profiler
    coveragepy_report_file_path: str = None,
    output_cov_file_path: str = "",
):
    assert (
        include_plain_coverage and include_hit_count
    ), "Currently only support collecting both plain coverage and hit count"

    ori_code_file = Path(ori_code_file_path).absolute()
    input_file = Path(input_file_path).absolute()

    ori_line_hit_count = {}  # line number -> hit count
    coveragepy_coverage = {}  # line number -> COVERED, NOT_COVERED or PARTIALLY_COVERED

    if include_hit_count:
        instrumented_code_file = (
            ori_code_file.parent / f"{ori_code_file.stem}_wrapped{ori_code_file.suffix}"
        )
        if instrumented_code_file.exists():
            instrumented_code_file.unlink()
        instrumented_code, line_no_table = instrument(
            ori_code_file_path, instrumented_code_file.as_posix()
        )

        output = run_kernprof(instrumented_code_file, input_file)
        instrumented_line_hit_count = parse_kernprof_output(output)
        ori_line_hit_count = get_ori_line_hit_count(
            instrumented_line_hit_count, line_no_table
        )

    if include_plain_coverage:
        assert (
            coveragepy_report_file_path is not None
        ), "Coverage report file path is required"
        coveragepy_report_file = Path(coveragepy_report_file_path).absolute()
        coveragepy_coverage = parse_coveragepy_report(coveragepy_report_file)

    record_file_with_coverage_and_hit_count(
        ori_code_file,
        ori_line_hit_count,
        coveragepy_coverage,
        Path(output_cov_file_path),
    )


if __name__ == "__main__":
    from fire import Fire

    Fire(main)
