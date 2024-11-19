import json
import os, sys
from pathlib import Path
from typing import List, Tuple, Dict
from multiprocessing import Pool
from tqdm import tqdm
from config import config
from common import Language
from selector.select_solution import select_solutions
from utils import get_alphacode_result, filter_problems, get_cf_problems
from cpp.coverage.scripts.cov_xml_parser import parse_cobertura_coverage_report as parse_cpp_cobertura_coverage_report

TIME_THRESHOLD = 0.1
# DIFF_RATIO_THRESHOLD = 0.2


def log(msg: str, log_file: Path):
    pass
    # with open(log_file, 'a') as f:
    #     f.write(msg + '\n')

def extract_slow_fast_inputs(alphacode_result: Dict, language: Language) -> Dict[str, Tuple[List[Tuple[str, float]], List[Tuple[str, float]]]]:
    """Extracts the fastest and slowest inputs from the result file."""
    slow_fast_input_dict = {}
    for solution_id, solution_data in alphacode_result.items():
        if not solution_id.startswith('solutions_'):
            continue
        if not all(v in ["AC", "TLE"] for v in solution_data["verdict"]):
            continue
        if not solution_data["language"] == str(language):
            continue
        time_dict = solution_data["time_dict"]
        avg_time_dict = {input_id: sum(time_list) / len(time_list) for input_id, time_list in time_dict.items()}
        sorted_avg_time_dict = sorted(avg_time_dict.items(), key=lambda x: x[1])

        # get the slowest and fastest inputs and make sure there is at least 2x difference
        fast_input_list = sorted_avg_time_dict[:len(sorted_avg_time_dict) // 2]
        slow_input_list = sorted_avg_time_dict[len(sorted_avg_time_dict) // 2:]
        if len(slow_input_list) > len(fast_input_list):
            slow_input_list = slow_input_list[1:]

        success = False

        for i in range(len(slow_input_list)):
            fast_input_sublist = fast_input_list[:len(slow_input_list) - i]
            slow_input_sublist = slow_input_list[i:]
            # Check if the slow input is slow enough
            if slow_input_sublist[0][1] < TIME_THRESHOLD:
                continue
            # the fast input should be at least 1.5x faster than the slow input
            if fast_input_sublist[-1][1] * 1.5 > slow_input_sublist[0][1]:
                continue
            fast_input_list = fast_input_sublist
            slow_input_list = slow_input_sublist
            success = True
            break

        if success:
            slow_fast_input_dict[solution_id] = (slow_input_list, fast_input_list)
            print("Debug: ", solution_id, slow_input_list, fast_input_list)

    return slow_fast_input_dict

def is_similar_input_old(input_file_1: Path, input_file_2: Path) -> bool:
    """Check if two inputs are similar."""
    with open(input_file_1, 'r') as f:
        input_1 = f.read()
    with open(input_file_2, 'r') as f:
        input_2 = f.read()

    # Check if the inputs have small edit distance
    matcher = difflib.SequenceMatcher(None, input_1, input_2)
    diff_ratio = 1 - matcher.ratio()
    if diff_ratio < DIFF_RATIO_THRESHOLD:
        return True
    # Check if the inputs have the same size
    if len(input_1.split()) == len(input_2.split()):
        # if same elements, different order
        if set(input_1.split()) == set(input_2.split()):
            return True

    return False

def is_similar_input(input_file_1: Path, input_file_2: Path) -> bool:
    """Check if two inputs are similar."""
    with open(input_file_1, 'r') as f:
        input_1 = f.read()
    with open(input_file_2, 'r') as f:
        input_2 = f.read()

    input_elements_1 = input_1.split()
    input_elements_2 = input_2.split()
    
    if len(input_elements_1) != len(input_elements_2):
        return False

    num_diff_elements = 0
    for e1, e2 in zip(input_elements_1, input_elements_2):
        if e1 != e2:
            num_diff_elements += 1

    if len(input_elements_1) >= 3 and num_diff_elements == 1:
        return True

    return False

def is_same_coverage_diff_hit_count(cov_report_file_1: Path, cov_report_file_2: Path, partial_cover_as_cover: bool=True) -> bool:
    return True

    """Check whether two hit count files have the same coverage but different hit count."""
    try:
        src_file_coverage_1 = parse_cpp_cobertura_coverage_report(cov_report_file_1)
        src_file_coverage_2 = parse_cpp_cobertura_coverage_report(cov_report_file_2)
    except FileNotFoundError:
        print(f"At least one of the coverage reports not found: {cov_report_file_1}, {cov_report_file_2}")
        return False

    if src_file_coverage_1 == src_file_coverage_2:
        print(f"The two coverage reports are the same: {cov_report_file_1}, {cov_report_file_2}")
        return False

    if len(src_file_coverage_1) != len(src_file_coverage_2):
        raise ValueError("The two coverage reports have different number of files.")

    cov_hit_count_info_1 = list(src_file_coverage_1.values())[0]
    cov_hit_count_info_2 = list(src_file_coverage_2.values())[0]
    if len(cov_hit_count_info_1) != len(cov_hit_count_info_2):
        raise ValueError("The two coverage reports have different number of lines.")

    cov_status_1 = [status for _, (_, status) in cov_hit_count_info_1.items()]
    cov_status_2 = [status for _, (_, status) in cov_hit_count_info_2.items()]

    # discard the no coverage cases
    if all(status == "NOT_COVERED" for status in cov_status_1) or all(status == "NOT_COVERED" for status in cov_status_2):
        print(f"At least one of the coverage reports have no coverage: {cov_report_file_1}, {cov_report_file_2}")
        return False

    if partial_cover_as_cover:
        cov_status_1 = [status if status != "PARTIALLY_COVERED" else "COVERED" for status in cov_status_1]
        cov_status_2 = [status if status != "PARTIALLY_COVERED" else "COVERED" for status in cov_status_2]
        
    # filter out fully covered cases
    if len([status for status in cov_status_1 if status == "NOT_COVERED"]) == 0 \
        or len([status for status in cov_status_2 if status == "NOT_COVERED"]) == 0:
        print(f"At least one of the coverage reports have full coverage: {cov_report_file_1}, {cov_report_file_2}")
        return False

    return cov_status_1 == cov_status_2

def mine_relational_input_pairs(
    problem_id: str,
    alphacode_solution_slow_fast_input_dict: Dict[str, Tuple[List[Tuple[str, float]], List[Tuple[str, float]]]],
    language: Language = Language.CPP,
    require_input_content_similar: bool = True,
    require_input_coverage_similar: bool = False,
):
    alphacode_result = get_alphacode_result(problem_id)
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    input_dir = problem_dir / 'input'

    solution_input_pairs = {}

    for solution_id, (slow_input_list, fast_input_list) in alphacode_solution_slow_fast_input_dict.items():
        if not slow_input_list or not fast_input_list:
            continue

        solution_cov_dir = Path(config["cov_data_dir"]) / "alphacode" / problem_id / str(language) / solution_id
        similar_input_pairs = []

        for fast_input_id, _ in fast_input_list:
            for slow_input_id, _ in slow_input_list:
                slow_input_file = input_dir / f'{slow_input_id}'
                fast_input_file = input_dir / f'{fast_input_id}'
                slow_cov_file = solution_cov_dir / slow_input_file.stem / 'coverage.xml'
                fast_cov_file = solution_cov_dir / fast_input_file.stem / 'coverage.xml'

                    if require_input_content_similar and is_similar_input(slow_input_file, fast_input_file):
                    if require_input_content_similar and is_similar_input(slow_input_file, fast_input_file):
                        log(f"slow input file:\n{slow_input_file.read_text()}\nfast input file:\n{fast_input_file.read_text()}\n", Path("content_similar_input_pairs.log"))
                        log("="*100, Path("content_similar_input_pairs.log"))
                if require_input_content_similar and is_similar_input(slow_input_file, fast_input_file):
                        log(f"slow input file:\n{slow_input_file.read_text()}\nfast input file:\n{fast_input_file.read_text()}\n", Path("content_similar_input_pairs.log"))
                        log("="*100, Path("content_similar_input_pairs.log"))
                    similar_input_pairs.append((slow_input_id, fast_input_id))
                if require_input_coverage_similar and is_same_coverage_diff_hit_count(slow_cov_file, fast_cov_file):
                    similar_input_pairs.append((slow_input_id, fast_input_id))

        if similar_input_pairs:
            solution_input_pairs[solution_id] = similar_input_pairs

    return solution_input_pairs


def process_problem(problem):
    problem_id = problem["name"].split(".")[0]
    alphacode_result = get_alphacode_result(problem_id)
    alphacode_solution_slow_fast_input_dict = extract_slow_fast_inputs(alphacode_result, Language.CPP)
    content_similar = mine_relational_input_pairs(problem_id, alphacode_solution_slow_fast_input_dict, require_input_content_similar=True, require_input_coverage_similar=False)
    # coverage_similar = mine_relational_input_pairs(problem_id, alphacode_solution_slow_fast_input_dict, require_input_content_similar=False, require_input_coverage_similar=True)
    # content_coverage_similar = mine_relational_input_pairs(problem_id, alphacode_solution_slow_fast_input_dict, require_input_content_similar=True, require_input_coverage_similar=True)

    return problem_id, content_similar, None, None


if __name__ == '__main__':
    problem_root_dir = Path(config["problem_root_dir"])
    filtered_problems = filter_problems(get_cf_problems(use_specified_problem=config["use_specified_problem"]))

    input_pairs_dir = Path(config["input_pairs_dir"])
    input_pairs_dir.mkdir(parents=True, exist_ok=True)

    with Pool() as pool:
        results = list(tqdm(pool.imap(process_problem, filtered_problems), total=0.25*os.cpu_count()))

    content_similar_problem_solution_input_pairs = {}
    coverage_similar_problem_solution_input_pairs = {}
    content_coverage_similar_problem_solution_input_pairs = {}

    for problem_id, content_similar, coverage_similar, content_coverage_similar in results:
        content_similar_problem_solution_input_pairs[problem_id] = content_similar
        # if content_similar:
        #     content_similar_problem_solution_input_pairs[problem_id] = content_similar
        # if coverage_similar:
        #     coverage_similar_problem_solution_input_pairs[problem_id] = coverage_similar
        # if content_coverage_similar:
        #     content_coverage_similar_problem_solution_input_pairs[problem_id] = content_coverage_similar

    with open(input_pairs_dir / 'content_similar_problem_solution_input_pairs.json', 'w') as f:
        json.dump(content_similar_problem_solution_input_pairs, f, indent=4)
    # with open(input_pairs_dir / 'coverage_similar_problem_solution_input_pairs.json', 'w') as f:
    #     json.dump(coverage_similar_problem_solution_input_pairs, f, indent=4)
    # with open(input_pairs_dir / 'content_coverage_similar_problem_solution_input_pairs.json', 'w') as f:
    #     json.dump(content_coverage_similar_problem_solution_input_pairs, f, indent=4)
