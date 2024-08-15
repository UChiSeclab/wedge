import json
import os
from pathlib import Path

import plotly.express as px


def load_data(file_path):
    with open(file_path, "r") as file:
        data = json.load(file)
    return data


def compare_solutions(problem_name, data1, data2):
    time1 = []
    time2 = []
    for solution_key in data1.keys():
        if solution_key in data2:
            if solution_key == "time_limit":
                continue
            if (
                "WA" in data1[solution_key]["verdict"]
                or "WA" in data2[solution_key]["verdict"]
            ):
                continue
            if (
                "KILL" in data1[solution_key]["verdict"]
                or "KILL" in data2[solution_key]["verdict"]
            ):
                continue
            time1.append(
                sum(data1[solution_key]["average_time"])
                / len(data1[solution_key]["average_time"])
            )
            time2.append(
                sum(data2[solution_key]["average_time"])
                / len(data2[solution_key]["average_time"])
            )

    # Combine time1 and time2 into a single list
    combined_times = time1 + time2
    print(len(time1), len(time2))
    # Create labels for the times
    labels = ["AlphaCode Test"] * len(time1) + ["Our Test"] * len(time2)

    # Draw the histogram
    fig = px.histogram(
        x=combined_times,
        color=labels,
        labels={"x": "Average Time", "color": "Test"},
        title=f"Comparison of Average Solution Times for {problem_name}",
    )
    # Overlay both histograms
    fig.update_layout(barmode="overlay")
    # Reduce opacity to see both histograms
    fig.update_traces(opacity=0.5)
    fig.write_image(f"analysis/{problem_name}.png")


def main():
    # Load data from two files
    alphacode_dir = Path("./results/alphacode")
    gpt_dir = Path("./results/manual_contrast")

    for file_name in os.listdir(gpt_dir):
        alphacode_result = load_data(alphacode_dir / file_name)
        gpt_result = load_data(gpt_dir / file_name)

        # Compare solutions
        print(file_name)
        compare_solutions(file_name.split(".")[0], alphacode_result, gpt_result)


if __name__ == "__main__":
    main()
