import json
import os
from pathlib import Path
from tqdm import tqdm


def load_data(file_path):
    with open(file_path, "r", encoding="utf-8") as file:
        data = json.load(file)
    return data


def save_data(results, file_path):
    with open(file_path, "w", encoding="utf-8") as file:
        file.write(json.dumps(results, indent=4))


def main():
    # Load data from two files
    alphacode_dir = Path("./results/alphacode")
    manual_contrast_dir = Path("./results/manual_contrast")
    time_contrast_dir = Path("./results/time_contrast")

    test_dir = alphacode_dir

    for file_name in tqdm(os.listdir(test_dir)):
        print(file_name)
        result = load_data(test_dir / file_name)
        problem_id = file_name.split(".")[0]
        solution_dir = Path(f"./problems/{problem_id}/solutions")
        for solution, data in result.items():
            if solution == "time_limit":
                continue
            flag = False
            for language in ["java", "cpp", "python", "python3"]:
                suffix = language
                if suffix[0] == "p":
                    suffix = "py"
                if f"{solution}.{suffix}" in os.listdir(solution_dir / language):
                    data["language"] = language
                    flag = True
            if not flag:
                print("error")

        save_data(result, test_dir / file_name)


if __name__ == "__main__":
    main()
