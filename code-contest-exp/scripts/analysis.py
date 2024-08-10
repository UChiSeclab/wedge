import json

# Reading the JSON data from a file
file_path = "./results/cpp_result.json"
with open(file_path, "r", encoding="utf-8") as file:
    data = json.load(file)

cnt = 0
for problem_name in data.keys():
    max_time_solution = (0, "")
    min_time_solution = (20, "")
    problem = data[problem_name]
    total_time = 0
    for solution_name in problem.keys():
        if "solutions" not in solution_name:
            continue
        solution = problem[solution_name]
        incorrect_flag = solution["online_judge_verdict"] == "incorrect"
        for verdict in solution["verdict"]:
            incorrect_flag |= verdict != "AC"
        if incorrect_flag:
            continue
        average_time = 0
        for test_name in solution["time_dict"]:
            average_time += sum(solution["time_dict"][test_name]) / 5
        average_time /= len(solution["time_dict"])
        total_time += average_time
        max_time_solution = max(max_time_solution, (average_time, solution_name))
        min_time_solution = min(min_time_solution, (average_time, solution_name))
    if max_time_solution[0] / min_time_solution[0] >= 2:
        print()
        print(problem_name)
        print(min_time_solution)
        print(max_time_solution)
        cnt += 1
print(cnt)
