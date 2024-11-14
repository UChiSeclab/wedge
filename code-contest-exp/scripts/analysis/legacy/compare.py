import json
import os
from pathlib import Path
from fire import Fire
import plotly.express as px

from utils import mean


def load_data(file_path):
    with open(file_path, "r") as file:
        data = json.load(file)
    return data


def compare_solutions(problem_name, alphacode_data, gpt_tests_data, experiment_name):
    alphacode_time = {"java": [], "cpp": [], "python": []}
    gpt_tests_time = {"java": [], "cpp": [], "python": []}

    for solution, data in alphacode_data.items():
        if solution == "time_limit":
            continue
        if "WA" in data["verdict"] or "KILL" in data["verdict"]:
            continue
        language = data["language"].lower()
        if language == "python3":
            language = "python"
        alphacode_time[language].append(mean(data["average_time"]))

    for solution, data in gpt_tests_data.items():
        if solution == "time_limit":
            continue
        if "WA" in data["verdict"] or "KILL" in data["verdict"]:
            continue
        language = data["language"].lower()
        if language == "python3":
            language = "python"
        gpt_tests_time[language].append(mean(data["average_time"]))

    for language in ["java", "cpp", "python"]:
        combined_times = alphacode_time[language] + gpt_tests_time[language]
        print(len(alphacode_time[language]), len(gpt_tests_time[language]))
        labels = ["AlphaCode Test"] * len(alphacode_time[language]) + [
            "Our Test"
        ] * len(gpt_tests_time[language])
        fig = px.histogram(
            x=combined_times,
            color=labels,
            labels={"x": "Average Time", "color": "Test"},
            title=f"Comparison of Average Solution Times for {problem_name}",
        )
        fig.update_layout(barmode="overlay")
        fig.update_traces(opacity=0.5)
        output_file = (
            Path("analysis") / experiment_name / language / f"{problem_name}.png"
        )
        output_file.parent.mkdir(exist_ok=True, parents=True)
        fig.write_image(output_file)


def main(
    experiment_name: str = "time_contrast",
):
    # Load data from two files
    alphacode_dir = Path("./results/alphacode")
    gpt_dir = Path("./results") / experiment_name

    for file_name in os.listdir(gpt_dir):
        alphacode_result = load_data(alphacode_dir / file_name)
        gpt_result = load_data(gpt_dir / file_name)

        # Compare solutions
        print(file_name)
        compare_solutions(
            file_name.split(".")[0], alphacode_result, gpt_result, experiment_name
        )


if __name__ == "__main__":
    Fire(main)
