import json
import os
from pathlib import Path
from tqdm import tqdm

result_dir = Path("./results/alphacode")

time_lang = {
    "CPP": (0, 0),
    "JAVA": (0, 0),
    "PYTHON": (0, 0),
    "PYTHON3": (0, 0),
}
for problem_result in tqdm(os.listdir(result_dir)):
    path = result_dir / problem_result
    with open(path, "r", encoding="utf-8") as file:
        res_dict = json.load(file)
    for (sol_name, res) in res_dict.items():
        if sol_name == "time_limit":
            continue
        if res["online_judge_verdict"] == "incorrect":
            continue
        if any(verdict != "AC" for verdict in res["verdict"]):
            continue
        total_time, cnt = time_lang[res["language"]]
        time_lang[res["language"]] = (
            total_time + sum(res["average_time"]),
            cnt + len(res["average_time"]),
        )
for (lang, res) in time_lang.items():
    print(lang, res[0] / res[1])
