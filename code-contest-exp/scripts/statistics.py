import json
from pathlib import Path
from fire import Fire
from typing import Dict, List, Literal
import pprint
from matplotlib import pyplot as plt
import numpy as np

from utils import mean
from config import config
from utils import filter_problems, get_cf_problems
from utils import squeeze_time_dict

def plot_avg_over_problem(problem_statistics: Dict, strategies: List[str]):
    # Prepare data for plotting
    problems = list(problem_statistics.keys())
    languages = set(lang for prob in problem_statistics.values() for lang in prob.keys())

    avg_time_values = {} # strategy -> List[float]
    max_time_values = {} # strategy -> List[float]
    
    for problem_id in problems:
        for language in languages:
            avg_times = []
            max_times = []
            for strategy in strategies:
                strategy_data = problem_statistics.get(problem_id, {}).get(language, {}).get(strategy, (0, 0))
                avg_times.append(strategy_data[0])
                max_times.append(strategy_data[1])

            if any(time == 0 for time in avg_times) or any(time == 0 for time in max_times):
                print(f"[Warning] {problem_id} with {language} has 0 time")
                continue
            
            for strategy in strategies:
                avg_time_values[strategy] = avg_time_values.get(strategy, [])
                max_time_values[strategy] = max_time_values.get(strategy, [])
                avg_time_values[strategy].append(avg_times[strategies.index(strategy)])
                max_time_values[strategy].append(max_times[strategies.index(strategy)])
    
    avg_avg_time_values = {strategy: mean(times) for strategy, times in avg_time_values.items()}
    avg_max_time_values = {strategy: mean(times) for strategy, times in max_time_values.items()}
    # Define plot characteristics
    n_groups = len(strategies)
    bar_width = 0.20
    index = np.arange(n_groups)
    fig, (ax1, ax2) = plt.subplots(nrows=2, ncols=1, figsize=(12, 10))
    
    ax1.bar(index, avg_avg_time_values.values(), bar_width, label='avg_time', color='blue')
    ax1.set_xlabel('Strategy')
    ax1.set_ylabel('Average Time')
    ax1.set_title('Average "Average Time" by Strategy')
    ax1.set_xticks(index)
    ax1.set_xticklabels(strategies, ha='center', rotation=45)
    ax1.legend()

    ax2.bar(index, avg_max_time_values.values(), bar_width, label='max_time', color='red')
    ax2.set_xlabel('Strategy')
    ax2.set_ylabel('Max Time')
    ax2.set_title('Average "Max Time" by Strategy')
    ax2.set_xticks(index)
    ax2.set_xticklabels(strategies, ha='center', rotation=45)
    ax2.legend()

    # Adjust layout to prevent overlapping
    plt.tight_layout()
    plt.savefig("./results/avg_over_problem.png")


def plot_problem_statistics(
    problem_statistics: Dict,
    strategies: List[str],
    colors:List[str] = ['purple', 'green', 'red', 'yellow', 'orange', 'blue', 'cyan', 'magenta', 'pink', 'brown', 'gray', 'olive', 'lime', 'teal', 'navy']
):
    # Prepare data for plotting
    problems = list(problem_statistics.keys())
    languages = set(lang for prob in problem_statistics.values() for lang in prob.keys())
    if len(strategies) > len(colors):
        raise ValueError(f"Number of strategies is more than number of colors: {len(strategies)} > {len(colors)}")
    colors = colors[:len(strategies)]

    x_labels = []
    avg_time_values = []
    max_time_values = []

    for problem_id in problems:
        for language in languages:
            avg_times = []
            max_times = []
            for strategy in strategies:
                strategy_data = problem_statistics.get(problem_id, {}).get(language, {}).get(strategy, (0, 0))
                avg_times.append(strategy_data[0])
                max_times.append(strategy_data[1])
            if any(time == 0 for time in avg_times) or any(time == 0 for time in max_times):
                print(f"[Warning] {problem_id} with {language} has 0 time")
                continue
            label = f"{problem_id}-{language}"
            x_labels.append(label)
            avg_time_values.append(avg_times)
            max_time_values.append(max_times)

    # Convert to arrays for easier manipulation
    avg_time_values = np.array(avg_time_values)
    max_time_values = np.array(max_time_values)

    # Define plot characteristics
    n_groups = len(x_labels)
    bar_width = 0.08
    index = np.arange(n_groups)

    # Plot avg time
    fig, (ax1, ax2) = plt.subplots(nrows=2, ncols=1, figsize=(15, 10))

    for i in range(len(strategies)):
        ax1.bar(index + i * bar_width, avg_time_values[:, i], bar_width, label=strategies[i], color=colors[i])

    ax1.set_xlabel('Problem-Language')
    ax1.set_ylabel('Average Time')
    ax1.set_title('Average Time by Problem-Language and Strategy')
    ax1.set_xticks(index + bar_width)
    ax1.set_xticklabels(x_labels, rotation=45, ha='right')
    ax1.legend()

    # Plot max time
    for i in range(len(strategies)):
        ax2.bar(index + i * bar_width, max_time_values[:, i], bar_width, label=strategies[i], color=colors[i])

    ax2.set_xlabel('Problem-Language')
    ax2.set_ylabel('Max Time')
    ax2.set_title('Max Time by Problem-Language and Strategy')
    ax2.set_xticks(index + bar_width)
    ax2.set_xticklabels(x_labels, rotation=45, ha='right')
    ax2.legend()

    # Adjust layout to prevent overlapping
    plt.tight_layout()
    plt.savefig("./results/problem_statistics.png")


def get_top_k_slow_inputs_time(time_dict:Dict[str, List[float]], k=5, use_max_or_avg: Literal["max", "avg"] = "avg") -> Dict[str, float]:
    time_stat = squeeze_time_dict(time_dict, use_max_or_avg=use_max_or_avg) # input_name -> time
    if len(time_stat) < k:
        raise ValueError(f"Number of inputs is less than k={k}")
    top_k_stat = {k: v for k, v in sorted(time_stat.items(), key=lambda item: item[1], reverse=True)[:k]}

    return top_k_stat


def main(
    problem_root_dir: str = config["problem_root_dir"],
    top_k_slow_inputs: int = 5,
):
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    problem_statistics = (
        {}
    )  # problem_id -> language -> strategy -> (avg_time, max_time)
    experiment_statistics = (
        {}
    )  # strategy -> problem_id -> language -> (avg_time, max_time)

    strategies = ["alphacode", "feedback_diff_solution", "feedback_diff_input", "feedback_multi_solution_diff_input", "multi_solution_diff_input", "time_contrast", "plain_problem", "slow_solution", "random_solution", "diff_solution_one_input"]
    for strategy in strategies:  # experiment_name
        experiment_dir = Path("results") / strategy
        experiment_statistics[strategy] = {}
        for problem in filtered_problems:
            problem_id = problem["name"].split(".")[0]
            problem_result_file = experiment_dir / (problem_id + ".json")
            if not problem_result_file.exists():
                print(f"[Warning] {problem_id} with {strategy} not exists")
                continue
            experiment_data = json.loads(problem_result_file.read_text())
            problem_statistics[problem_id] = problem_statistics.get(problem_id, {})

            solutions_data = {}
            input_not_enough_flag = False
            for solution, data in experiment_data.items():
                if solution == "time_limit":
                    continue
                if solution.startswith("incorrect_solutions"):
                    continue
                if not all(verdict == "AC" for verdict in data["verdict"]):
                    continue
                language = data["language"].lower()
                if language == "python3":
                    language = "python"

                problem_statistics[problem_id][language] = problem_statistics[
                    problem_id
                ].get(language, {})

                try:
                    top_k_input_stat = get_top_k_slow_inputs_time(data["time_dict"], k=top_k_slow_inputs)
                    avg_time = mean(top_k_input_stat.values())
                    max_time = max(top_k_input_stat.values())
                except ValueError as e:
                    print(f"[Warning] Error processing {problem_id} with {strategy}: {e}")
                    input_not_enough_flag = True
                    break

                if input_not_enough_flag:
                    print(f"[Warning] Skip {problem_id} due to not enough inputs")
                    break
                solutions_data[language] = solutions_data.get(language, [])
                solutions_data[language].append((avg_time, max_time))

            mean_solutions_data = {}  # language -> (avg_time, max_time)
            for language, times in solutions_data.items():
                avg_times = [time[0] for time in times]
                max_times = [time[1] for time in times]
                mean_solutions_data[language] = (mean(avg_times), mean(max_times))
                problem_statistics[problem_id][language][
                    strategy
                ] = mean_solutions_data[language]

            experiment_statistics[strategy][problem_id] = mean_solutions_data

    # for each problem, which strategy has longest max/avg time
    # problem_id -> language -> (longest avg_time stragety, longest max_time strategy)
    problem_longest_strategies = {}
    for strategy in strategies:
        for problem_id, mean_solutions_data in experiment_statistics[strategy].items():
            for language, times in mean_solutions_data.items():
                avg_time, max_time = times
                if problem_id not in problem_longest_strategies:
                    problem_longest_strategies[problem_id] = {}
                if language not in problem_longest_strategies[problem_id]:
                    problem_longest_strategies[problem_id][language] = (
                        strategy,
                        strategy,
                    )
                else:
                    (
                        longest_avg_time_strategy,
                        longest_max_time_strategy,
                    ) = problem_longest_strategies[problem_id][language]
                    if (
                        avg_time
                        > experiment_statistics[longest_avg_time_strategy][problem_id][
                            language
                        ][0]
                    ):
                        longest_avg_time_strategy = strategy
                    if (
                        max_time
                        > experiment_statistics[longest_max_time_strategy][problem_id][
                            language
                        ][1]
                    ):
                        longest_max_time_strategy = strategy
                    problem_longest_strategies[problem_id][language] = (
                        longest_avg_time_strategy,
                        longest_max_time_strategy,
                    )

    pp = pprint.PrettyPrinter(depth=4, width=90)
    print(
        "Longest strategy for each problem: problem_id -> language -> (longest avg_time strategy, longest max_time strategy)"
    )
    pp.pprint(problem_longest_strategies)
    # print("Experiment statistics:")
    # pp.pprint(experiment_statistics)
    print(
        "Problem statistics: problem_id -> language -> strategy -> (avg_time, max_time)"
    )
    pp.pprint(problem_statistics)

    plot_problem_statistics(problem_statistics, strategies)

    plot_avg_over_problem(problem_statistics, strategies)

if __name__ == "__main__":
    Fire(main)
