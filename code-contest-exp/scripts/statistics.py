import json
import os
from pathlib import Path
from fire import Fire
from typing import Dict
import pprint

from utils import mean
from config import config
from utils import filter_problems, get_cf_problems


def main(
    problem_root_dir: str = config["problem_root_dir"],
):    
    problem_root_dir = Path(problem_root_dir)
    filtered_problems = filter_problems(
        get_cf_problems(use_specified_problem=config["use_specified_problem"])
    )

    problem_statistics = {} # problem_id -> language -> strategy -> (avg_time, max_time)
    experiment_statistics = {} # strategy -> problem_id -> language -> (avg_time, max_time)

    strategies = ["time_contrast", "feedback_diff_solution", "feedback_diff_input"]
    for strategy in strategies: # experiment_name
        experiment_dir = Path("results") / strategy
        experiment_statistics[strategy] = {}
        for problem in filtered_problems:
            problem_id = problem["name"].split(".")[0]
            problem_result_file = experiment_dir / (problem_id + ".json")
            experiment_data = json.loads(problem_result_file.read_text())
            problem_statistics[problem_id] = problem_statistics.get(
                problem_id,
                {}
            )

            solutions_data = {}
            for solution, data in experiment_data.items():
                if solution == "time_limit":
                    continue
                if solution.startswith("incorrect_solutions"):
                    continue
                if "WA" in data["verdict"] or "KILL" in data["verdict"]:
                    continue
                language = data["language"].lower()
                if language == "python3":
                    language = "python"
                    
                problem_statistics[problem_id][language] = problem_statistics[problem_id].get(language, {})
                
                avg_time = mean(data["average_time"])
                max_time = mean(data["max_time"])
                
                solutions_data[language] = solutions_data.get(language, [])
                solutions_data[language].append((avg_time, max_time))

            mean_solutions_data = {} # language -> (avg_time, max_time)
            for language, times in solutions_data.items():
                avg_times = [time[0] for time in times]
                max_times = [time[1] for time in times]
                mean_solutions_data[language] = (mean(avg_times), mean(max_times))
                problem_statistics[problem_id][language][strategy] = mean_solutions_data[language]

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
                    problem_longest_strategies[problem_id][language] = (strategy, strategy)
                else:
                    longest_avg_time_strategy, longest_max_time_strategy = problem_longest_strategies[problem_id][language]
                    if avg_time > experiment_statistics[longest_avg_time_strategy][problem_id][language][0]:
                        longest_avg_time_strategy = strategy
                    if max_time > experiment_statistics[longest_max_time_strategy][problem_id][language][1]:
                        longest_max_time_strategy = strategy
                    problem_longest_strategies[problem_id][language] = (longest_avg_time_strategy, longest_max_time_strategy)

    pp = pprint.PrettyPrinter(depth=4, width=90)
    print("Longest strategy for each problem: problem_id -> language -> (longest avg_time strategy, longest max_time strategy)")
    pp.pprint(problem_longest_strategies)
    # print("Experiment statistics:")
    # pp.pprint(experiment_statistics)
    print("Problem statistics: problem_id -> language -> strategy -> (avg_time, max_time)")
    pp.pprint(problem_statistics)

if __name__ == "__main__":
    Fire(main)