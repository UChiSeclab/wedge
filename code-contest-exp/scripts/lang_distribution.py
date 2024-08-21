import json
import os
from pathlib import Path
from tqdm import tqdm
from scipy.stats import pearsonr


loc_result_path = Path(f"results/average_loc_sorted.json")
with open(loc_result_path, "r", encoding="utf-8") as file:
    loc_list = json.load(file)
loc_dict = {}
for (problem_id, loc, n_solutions) in loc_list:
    loc_dict[problem_id] = loc

loc = []
run_time = []
var = []

result_dir = Path("./results/alphacode")
for problem_result in tqdm(os.listdir(result_dir)):
    problem_id = problem_result.split(".")[0]
    if problem_id not in loc_dict.keys():
        continue

    path = result_dir / problem_result
    with open(path, "r", encoding="utf-8") as file:
        res_dict = json.load(file)

    time_list = []
    for (sol_name, res) in res_dict.items():
        if sol_name == "time_limit":
            continue
        if res["online_judge_verdict"] == "incorrect":
            continue
        if any(verdict != "AC" for verdict in res["verdict"]):
            continue
        average_time = sum(res["average_time"]) / len(res["average_time"])
        time_list.append(average_time)

    if len(time_list) == 0:
        continue

    m = sum(time_list) / len(time_list)
    if m > 1 or m < 0.1:
        print(problem_id, m)
        continue
    run_time.append(m)
    loc.append(loc_dict[problem_id])
    var.append(sum((xi - m) ** 2 for xi in time_list) / len(time_list))

print("runtime v.s. var", pearsonr(var, run_time).statistic)
print("runtime v.s. loc", pearsonr(loc, run_time).statistic)
print("var v.s. loc", pearsonr(var, loc).statistic)

import plotly.graph_objs as go
import plotly.io as pio

# Create a scatter plot with loc on the x-axis and var on the y-axis
scatter_plot = go.Scatter(
    x=loc,
    y=var,
    mode="markers",
    marker=dict(
        size=10,
        color=run_time,  # Color by runtime
        colorscale="Viridis",
        showscale=True,
        colorbar=dict(title="Run Time"),
    ),
    text=[f"Runtime: {rt:.2f}" for rt in run_time],  # Tooltips with runtime
)

# Define the layout of the plot
layout = go.Layout(
    title="Distribution of Lines of Code (loc) and Run Time Variance (var)",
    xaxis=dict(title="Average Lines of Code (loc)"),
    yaxis=dict(title="Run Time Variance"),
    hovermode="closest",
)

# Create the figure
fig = go.Figure(data=[scatter_plot], layout=layout)

# Save the figure as a PNG file
fig_path = "loc_var_distribution.png"
pio.write_image(fig, fig_path)
