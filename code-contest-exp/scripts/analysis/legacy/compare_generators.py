import json
import os
from pathlib import Path
from fire import Fire
import plotly.express as px
from typing import Dict

from utils import mean


def load_data(file_path):
    with open(file_path, "r") as file:
        data = json.load(file)
    return data


def process_experiment_data(experiment_data, exec_time):
    # TODO: exclude problems with less than 5 solutions
    for solution, data in experiment_data.items():
        if solution == "time_limit":
            continue
        if "WA" in data["verdict"] or "KILL" in data["verdict"]:
            continue
        language = data["language"].lower()
        if language == "python3":
            language = "python"
        exec_time[language].append(mean(data["average_time"]))


def compare_solutions(
    problem_name: str,
    experiment_data_1: Dict,
    experiment_data_2: Dict,
    experiment_name_1: str,
    experiment_name_2: str,
):
    exec_time_1 = {"java": [], "cpp": [], "python": []}
    exec_time_2 = {"java": [], "cpp": [], "python": []}

    process_experiment_data(experiment_data_1, exec_time_1)
    process_experiment_data(experiment_data_2, exec_time_2)

    for language in ["java", "cpp", "python"]:
        combined_times = exec_time_1[language] + exec_time_2[language]
        print(len(exec_time_1[language]), len(exec_time_2[language]))
        labels = [experiment_name_1] * len(exec_time_1[language]) + [
            experiment_name_2
        ] * len(exec_time_2[language])
        fig = px.histogram(
            x=combined_times,
            color=labels,
            labels={"x": "Average Time", "color": "Test"},
            title=f"Comparison of Average Solution Times for {problem_name}",
        )
        fig.update_layout(barmode="overlay")
        fig.update_traces(opacity=0.5)
        output_file = (
            Path("analysis")
            / f"{experiment_name_1}_vs_{experiment_name_2}"
            / language
            / f"{problem_name}.png"
        )
        output_file.parent.mkdir(exist_ok=True, parents=True)
        fig.write_image(output_file)


def main(
    experiment_name_1: str,
    experiment_name_2: str,
):
    # Load data from two files
    experiment_dir_1 = Path("./results") / experiment_name_1
    experiment_dir_2 = Path("./results") / experiment_name_2

    for file_name in os.listdir(experiment_dir_1):
        experiment_result_1 = load_data(experiment_dir_1 / file_name)
        experiment_result_2 = load_data(experiment_dir_2 / file_name)

        # Compare solutions
        print(file_name)
        compare_solutions(
            file_name.split(".")[0],
            experiment_result_1,
            experiment_result_2,
            experiment_name_1,
            experiment_name_2,
        )


if __name__ == "__main__":
    Fire(main)
