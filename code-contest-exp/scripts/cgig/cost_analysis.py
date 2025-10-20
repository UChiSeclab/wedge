from pathlib import Path
import os
from typing import Dict, List, Tuple
from tqdm import tqdm

from config import config
from cgig.cgig_utils import find_mutator_file, problem_has_extracted_constraint, get_problem_solution_input_pairs
from utils import get_alphacode_result

import tiktoken

def num_tokens_from_messages(messages, model="gpt-4"):
    """Return the number of tokens used by a list of messages."""
    try:
        encoding = tiktoken.encoding_for_model(model)
    except KeyError:
        print("Warning: model not found. Using cl100k_base encoding.")
        encoding = tiktoken.get_encoding("cl100k_base")

    # Set tokens per message and per name based on model
    if model in {
        "gpt-3.5-turbo-0613",
        "gpt-3.5-turbo-16k-0613",
        "gpt-4-0314",
        "gpt-4-32k-0314",
        "gpt-4-0613",
        "gpt-4-32k-0613",
        }:
        tokens_per_message = 3
        tokens_per_name = 1
    elif model == "gpt-3.5-turbo-0301":
        tokens_per_message = 4  # every message follows <|start|>{role/name}\n{content}<|end|>\n
        tokens_per_name = -1  # if there's a name, the role is omitted
    elif "gpt-3.5-turbo" in model:
        # For modern gpt-3.5-turbo models
        return num_tokens_from_messages(messages, model="gpt-3.5-turbo-0613")
    elif "gpt-4" in model:
        # For modern gpt-4 models
        return num_tokens_from_messages(messages, model="gpt-4-0613")
    else:
        raise NotImplementedError(
            f"""num_tokens_from_messages() is not implemented for model {model}."""
        )

    num_tokens = 0
    for message in messages:
        num_tokens += tokens_per_message
        for key, value in message.items():
            num_tokens += len(encoding.encode(value))
            if key == "name":
                num_tokens += tokens_per_name
                
    num_tokens += 3  # every reply is primed with <|start|>assistant<|message|>
    return num_tokens

def num_tokens_from_string(string, model="gpt-4"):
    """Return the number of tokens used by a string."""
    try:
        encoding = tiktoken.encoding_for_model(model)
    except KeyError:
        print("Warning: model not found. Using cl100k_base encoding.")
        encoding = tiktoken.get_encoding("cl100k_base")

    return len(encoding.encode(string))

def problem_exec_cost_analysis(problem_id: str) -> Tuple[List[str], int, Dict[str, float], Dict[str, float]]:
    try:
        alphacode_result_dict = get_alphacode_result(problem_id, sanitize=True)
    except AssertionError as e:
        raise ValueError(f"Error getting alphacode result for {problem_id}: {e}")
    correct_cpp_solution_ids = []
    avg_time_dict = {}
    total_time_dict = {}
    for solution_id in alphacode_result_dict:
        if solution_id == "time_limit":
            continue
        if alphacode_result_dict[solution_id]["language"] != "cpp":
            continue
        if any(verdict not in ["AC", "TLE"] for verdict in alphacode_result_dict[solution_id]["verdict"]):
            continue
        correct_cpp_solution_ids.append(solution_id)
        avg_time = alphacode_result_dict[solution_id]["average_time"][0]
        total_time = avg_time * len(alphacode_result_dict[solution_id]["time_dict"])
        avg_time_dict[f"{problem_id}_{solution_id}"] = avg_time
        total_time_dict[f"{problem_id}_{solution_id}"] = total_time
    num_inputs = len(alphacode_result_dict[correct_cpp_solution_ids[0]]["time_dict"])

    return correct_cpp_solution_ids, num_inputs, avg_time_dict, total_time_dict

def exec_cost_analysis(problem_id_list: List[str]):
    """
    calculate the average execution time for each solution
    calculate the average number of inputs for each solution
    """
    problem_id_list = [problem_id for problem_id in problem_id_list if problem_has_extracted_constraint(problem_id)]

    all_correct_cpp_solution_ids = []
    all_num_inputs = 0
    all_avg_time_dict = {}
    all_total_time_dict = {}
    
    for problem_id in tqdm(problem_id_list):
        try:
            correct_cpp_solution_ids, num_inputs, avg_time_dict, total_time_dict = problem_exec_cost_analysis(problem_id)
        except ValueError as e:
            print(f"Skipping {problem_id} due to error: {e}")
            continue
        all_correct_cpp_solution_ids.extend(correct_cpp_solution_ids)
        all_num_inputs += num_inputs
        all_avg_time_dict.update(avg_time_dict)
        all_total_time_dict.update(total_time_dict)

    # Calculate the average execution time for each solution
    avg_time_per_solution = {}
    for solution_id in all_correct_cpp_solution_ids:
        total_time = 0
        count = 0
        for key, value in all_avg_time_dict.items():
            if key.endswith(solution_id):
                total_time += value
                count += 1
        if count > 0:
            avg_time_per_solution[solution_id] = total_time / count
            
    # Calculate the average number of inputs for each solution
    avg_inputs_per_solution = {}
    for solution_id in all_correct_cpp_solution_ids:
        count = 0
        for key in all_avg_time_dict.keys():
            if key.endswith(solution_id):
                count += 1
        avg_inputs_per_solution[solution_id] = count

    print(f"Average execution time per solution: {avg_time_per_solution}")
    print(f"Average number of inputs per solution: {avg_inputs_per_solution}")

def constraint_reasoning_and_checker_impl_token_cost(problem_id: str, solution_id: str) -> int:
    solution_constraint_dir = Path(config["constraints_dir"]) / problem_id / solution_id
    input_pair_id = os.listdir(solution_constraint_dir)[0]
    constraint_dir = solution_constraint_dir / input_pair_id
    prompt_file = constraint_dir / "conversations.txt"
    response_file = constraint_dir / "gpt_response.txt"
    input_tokens = num_tokens_from_string(prompt_file.read_text())
    output_tokens = num_tokens_from_string(response_file.read_text())

    total_tokens = input_tokens + output_tokens

    return total_tokens

def mutator_gen_token_cost(problem_id: str, solution_id: str) -> int:
    mutator_dir = Path(config["mutator_with_constraint_per_solution_dir"]) / "instrument_fuzz" / problem_id / solution_id / "self_reflect_feedback"
    mutator_file = find_mutator_file(mutator_dir)
    prompt_file = mutator_file.parent / "conversation.txt"
    response_file = mutator_file.parent / "gpt_response.txt"
    
    input_tokens = num_tokens_from_string(prompt_file.read_text())
    output_tokens = num_tokens_from_string(response_file.read_text())

    total_tokens = input_tokens + output_tokens

    return total_tokens

if __name__ == '__main__':
    problem_root_dir = Path(config["problem_root_dir"])
    problem_solution_input_pairs = get_problem_solution_input_pairs()

    avg_exec_time_list = []
    total_exec_time_list = []
    num_inputs_list = []
    constraint_reasoning_cost_list = []
    mutator_gen_cost_list = []
    total_num_generated_inputs = 0
    for problem_id in problem_solution_input_pairs:
        print(f"Processing {problem_id}")
        problem_dir = problem_root_dir / problem_id
        solution_input_pairs = problem_solution_input_pairs[problem_id]
        try:
            correct_cpp_solution_ids, num_inputs, avg_time_dict, total_time_dict = problem_exec_cost_analysis(problem_id)
        except ValueError as e:
            print(f"Skipping {problem_id} due to error: {e}")
            continue
        for solution_id in solution_input_pairs:
            solution_constraint_dir = Path(config["constraints_dir"]) / problem_id / solution_id
            if not solution_constraint_dir.exists():
                # print(f"Skipping {solution_id} in {problem_id} as constraint directory does not exist.")
                continue
            # calculating all costs
            # exec cost
            avg_time = avg_time_dict.get(f"{problem_id}_{solution_id}", None)
            total_time = total_time_dict.get(f"{problem_id}_{solution_id}", None)
            if avg_time is None:
                raise ValueError(f"Execution cost for {problem_id} {solution_id} not found in avg_time_dict.")
            constraint_reasoning_cost = constraint_reasoning_and_checker_impl_token_cost(problem_id, solution_id)
            mutator_gen_cost = mutator_gen_token_cost(problem_id, solution_id)

            corpus_gen_dir = Path(config["corpus_instrument_gen_dir"]) / "mutator_with_constraint_per_solution" / problem_id / solution_id / f"{solution_id}_mutator_with_constraint_per_solution_output"
            queue_dir = corpus_gen_dir / "default" / "queue"
            num_generated_inputs = len(os.listdir(queue_dir)) - 1 if queue_dir.exists() else 0
            print(f"Problem: {problem_id}, Solution: {solution_id}, Num Generated Inputs: {num_generated_inputs}")
            total_num_generated_inputs += num_generated_inputs
            
            avg_exec_time_list.append(avg_time)
            total_exec_time_list.append(total_time)
            num_inputs_list.append(num_inputs)
            constraint_reasoning_cost_list.append(constraint_reasoning_cost)
            mutator_gen_cost_list.append(mutator_gen_cost)

    print(f"Total Problems Processed: {len(problem_solution_input_pairs)}")
    print(f"Total Solutions Processed: {len(avg_exec_time_list)}")
    print(f"Average Execution Time: {sum(avg_exec_time_list) / len(avg_exec_time_list)}")
    print(f"Average Total Execution Time: {sum(total_exec_time_list) / len(total_exec_time_list)}")
    print(f"Average Number of Inputs: {sum(num_inputs_list) / len(num_inputs_list)}")
    print(f"Average Number of Generated Inputs: {total_num_generated_inputs / len(avg_exec_time_list)}")
    print(f"Average Constraint Reasoning Cost: {sum(constraint_reasoning_cost_list) / len(constraint_reasoning_cost_list)}")
    print(f"Average Mutator Generation Cost: {sum(mutator_gen_cost_list) / len(mutator_gen_cost_list)}")
