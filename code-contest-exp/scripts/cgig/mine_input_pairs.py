import json
import os, sys
from pathlib import Path
from typing import List, Tuple, Dict, Literal
from multiprocessing import Pool
from tqdm import tqdm
from config import config
from common import Language
from selector.select_solution import select_solutions
from utils import get_alphacode_result, filter_problems, get_cf_problems, get_run_time, get_instruction_cnt
# from cpp.coverage.scripts.cov_xml_parser import parse_cobertura_coverage_report as parse_cpp_cobertura_coverage_report

# TIME_THRESHOLD = 0.1 # we might no longer need this in gem5
INSTRUCTION_CNT_THRESHOLD = 10000
# DIFF_RATIO_THRESHOLD = 0.2


def log(msg: str, log_file: Path):
    pass
    # with open(log_file, 'a') as f:
    #     f.write(msg + '\n')

def get_ratio(experiment_result: Dict, slow_input_id: str, fast_input_id: str, solution_id: str, mode: str) -> float:
    """Get the ratio of the run time or instruction count of the slow input and the fast input."""
    if mode == "run_time":
        slow_time = get_run_time(experiment_result, solution_id, slow_input_id)
        fast_time = get_run_time(experiment_result, solution_id, fast_input_id)
        return slow_time / fast_time
    elif mode == "instruction_cnt":
        slow_instruction_cnt = get_instruction_cnt(experiment_result, solution_id, slow_input_id)
        fast_instruction_cnt = get_instruction_cnt(experiment_result, solution_id, fast_input_id)
        # print("debug", slow_instruction_cnt, fast_instruction_cnt)
        return slow_instruction_cnt / fast_instruction_cnt
    else:
        raise ValueError(f"invalid mode {mode}")


def extract_slow_fast_inputs(alphacode_result: Dict, language: Language, mode: str) -> Dict[str, Tuple[List[Tuple[str, float]], List[Tuple[str, float]]]]:
    """Extracts the fastest and slowest inputs from the result file."""
    slow_fast_input_dict = {}
    for solution_id, solution_data in alphacode_result.items():
        if not solution_id.startswith('solutions_'):
            continue
        if not all(v in ["AC", "TLE"] for v in solution_data["verdict"]):
            continue
        if not solution_data["language"] == str(language):
            continue
        if mode == "run_time":
            time_dict = solution_data["time_dict"]
        elif mode == "instruction_cnt":
            time_dict = solution_data["instruction_cnt_dict"]
        else:
            raise ValueError(f"invalid mode {mode}")
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
            # Check if the slow input is slow enough (we might no longer need this in gem5)
            if (mode == "run_time" and slow_input_sublist[-1][1] < TIME_THRESHOLD) \
                or (mode == "instruction_cnt" and slow_input_sublist[-1][1] < INSTRUCTION_CNT_THRESHOLD):
                    continue
            # the fast input should be at least 1.2x faster than the slow input
            if fast_input_sublist[-1][1] * 1.2 > slow_input_sublist[0][1]:
                continue
            fast_input_list = fast_input_sublist
            slow_input_list = slow_input_sublist
            success = True
            break

        if success:
            slow_fast_input_dict[solution_id] = (slow_input_list, fast_input_list)

    return slow_fast_input_dict

def is_similar_input(input_file_1: Path, input_file_2: Path) -> bool:
    return calculate_similarity(input_file_1, input_file_2) > 0.1

def calculate_similarity(input_file_1: Path, input_file_2: Path) -> float:
    return calculate_order_similarity(input_file_1, input_file_2) + calculate_jaccard_similarity(input_file_1, input_file_2)

def calculate_order_similarity(input_file_1: Path, input_file_2: Path) -> float:
    """Calculate the similarity between two inputs."""
    with open(input_file_1, 'r') as f:
        input_1 = f.read()
    with open(input_file_2, 'r') as f:
        input_2 = f.read()

    input_elements_1 = input_1.split()
    input_elements_2 = input_2.split()

    if len(input_elements_1) != len(input_elements_2):
        min_len = min(len(input_elements_1), len(input_elements_2))
        input_elements_1 = input_elements_1[:min_len]
        input_elements_2 = input_elements_2[:min_len]

    diff_count = 0
    for e1, e2 in zip(input_elements_1, input_elements_2):
        if e1 != e2:
            diff_count += 1

    return 1 - diff_count / len(input_elements_1)

def calculate_jaccard_similarity(input_file_1: Path, input_file_2: Path) -> float:
    """Calculate the Jaccard similarity between two inputs."""
    with open(input_file_1, 'r') as f:
        input_1 = f.read()
    with open(input_file_2, 'r') as f:
        input_2 = f.read()

    input_elements_1 = set(input_1.split())
    input_elements_2 = set(input_2.split())

    return len(input_elements_1 & input_elements_2) / len(input_elements_1 | input_elements_2)

def sort_solution_input_pairs(solution_input_pairs: Dict[str, Dict[str, Tuple[float, float]]]) -> Dict[str, Dict[str, Tuple[float, float]]]:
    """Sort solutions by the max similarity of their input pairs."""
    solution_similarity_map = {solution_id: max(similarity_map.values(), key=lambda item: item[0]) for solution_id, similarity_map in solution_input_pairs.items()}
    solution_similarity_map = {solution_id: similarity for solution_id, similarity in sorted(solution_similarity_map.items(), key=lambda item: item[1], reverse=True)}

    sorted_solution_input_pairs = {solution_id: solution_input_pairs[solution_id] for solution_id in solution_similarity_map.keys()}

    return sorted_solution_input_pairs

def sort_input_pair_similarity_ratio_map(input_pair_similarity_ratio_map: Dict[str, Tuple[float, float]]) -> Dict[str, Tuple[float, float]]:
    """
    Sort input pairs by similarity first (descending), if same, then by ratio (descending).
    """
    sorted_input_pair_ids = sorted(
        input_pair_similarity_ratio_map.keys(),
        key=lambda x: input_pair_similarity_ratio_map[x],
        reverse=True  # Sort descending based on similarity and ratio
    )
    return {input_pair_id: input_pair_similarity_ratio_map[input_pair_id] for input_pair_id in sorted_input_pair_ids}

def mine_relational_input_pairs(
    problem_id: str,
    alphacode_solution_slow_fast_input_dict: Dict[str, Tuple[List[Tuple[str, float]], List[Tuple[str, float]]]],
    language: Language = Language.CPP,
    require_input_content_similar: bool = False,
    require_input_coverage_similar: bool = False,
) -> Dict[str, Dict[str, Tuple[float, float]]]:
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

                if require_input_content_similar:
                    if is_similar_input(slow_input_file, fast_input_file):
                        log(f"slow input file:\n{slow_input_file.read_text()}\nfast input file:\n{fast_input_file.read_text()}\n", Path("content_similar_input_pairs.log"))
                        log("="*100, Path("content_similar_input_pairs.log"))
                        similar_input_pairs.append((slow_input_id, fast_input_id))
                else:
                    similar_input_pairs.append((slow_input_id, fast_input_id))

        if similar_input_pairs:
            # sort the similar input pairs by the similarity
            input_pair_similarity_map = {input_pair: calculate_similarity(input_dir / input_pair[0], input_dir / input_pair[1]) for input_pair in similar_input_pairs}
            input_pair_similarity_ratio_map = {
                f"{slow_input_id}@{fast_input_id}": (similarity, get_ratio(alphacode_result, slow_input_id, fast_input_id, solution_id, "instruction_cnt"))\
                    for (slow_input_id, fast_input_id), similarity in input_pair_similarity_map.items()
            }
            solution_input_pairs[solution_id] = sort_input_pair_similarity_ratio_map(input_pair_similarity_ratio_map)

    return sort_solution_input_pairs(solution_input_pairs)

def process_problem(problem):
    problem_id = problem["name"].split(".")[0]
    alphacode_result = get_alphacode_result(problem_id)
    alphacode_solution_slow_fast_input_dict = extract_slow_fast_inputs(alphacode_result, Language.CPP, MODE)
    content_similar = mine_relational_input_pairs(problem_id, alphacode_solution_slow_fast_input_dict, require_input_content_similar=True, require_input_coverage_similar=False)
    # coverage_similar = mine_relational_input_pairs(problem_id, alphacode_solution_slow_fast_input_dict, require_input_content_similar=False, require_input_coverage_similar=True)
    # content_coverage_similar = mine_relational_input_pairs(problem_id, alphacode_solution_slow_fast_input_dict, require_input_content_similar=True, require_input_coverage_similar=True)

    return problem_id, content_similar, None, None


if __name__ == '__main__':
    MODE = "instruction_cnt"
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

    with open(input_pairs_dir / 'content_similar_problem_solution_input_pairs_sorted.json', 'w') as f:
        json.dump(content_similar_problem_solution_input_pairs, f, indent=4)
    # with open(input_pairs_dir / 'coverage_similar_problem_solution_input_pairs.json', 'w') as f:
    #     json.dump(coverage_similar_problem_solution_input_pairs, f, indent=4)
    # with open(input_pairs_dir / 'content_coverage_similar_problem_solution_input_pairs.json', 'w') as f:
    #     json.dump(content_coverage_similar_problem_solution_input_pairs, f, indent=4)
