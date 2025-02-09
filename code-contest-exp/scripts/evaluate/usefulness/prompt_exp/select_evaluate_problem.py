from pathlib import Path
from typing import List, Dict

from utils import get_experiment_result, get_alphacode_result, get_cf_problems, filter_problems, problem_to_id, mean
from config import config

"""
select problems to evaluate, select 5 python solutions:
1. slow enough
2. long enough
3. contains functions ("def" keyword)
"""

SLOW_THRESHOLD = 100000 # absolute slow instruction count
SLOW_RATIO_THRESHOLD = 2 # relative slow ratio
LOC_THRESHOLD = 50



def select_candidate_solutions(problem_id: str) -> List[str]:
    target_language = "python3"
    candidate_solution_id_list = []
    alphacode_result = get_alphacode_result(problem_id, sanitize=False)
    min_instruction_cnt = get_min_instruction_cnt(problem_id)
    for solution_id in alphacode_result:
        if solution_id == "time_limit":
            continue
        if not all(v in ["AC", "TLE"] for v in alphacode_result[solution_id]["verdict"]):
            continue
        if alphacode_result[solution_id]["language"] != target_language:
            continue
        instruction_count = mean(alphacode_result[solution_id]["average_instruction_cnt"])
        if instruction_count < SLOW_THRESHOLD:
            continue
        if instruction_count < min_instruction_cnt * SLOW_RATIO_THRESHOLD:
            continue
        solution_file = Path(config["problem_root_dir"]) / problem_id / "solutions" / target_language / f"{solution_id}.py"
        if not solution_file.exists():
            raise FileNotFoundError(f"{solution_file} does not exist")
        if not any("def " in line for line in solution_file.read_text().split("\n")):
            continue
        with open(solution_file, "r") as file:
            loc = len(file.readlines())
        if loc < LOC_THRESHOLD:
            continue
        candidate_solution_id_list.append(solution_id)

    # sort by instruction count
    candidate_solution_id_list = sorted(candidate_solution_id_list, key=lambda x: mean(alphacode_result[x]["average_instruction_cnt"]), reverse=True)

    return candidate_solution_id_list

def get_min_instruction_cnt(problem_id: str) -> int:
    target_language = "python3"
    alphacode_result = get_alphacode_result(problem_id, sanitize=False)
    min_instruction_cnt = float("inf")
    for solution_id in alphacode_result:
        if solution_id == "time_limit":
            continue
        if alphacode_result[solution_id]["language"] != target_language:
            continue
        instruction_cnt = mean(alphacode_result[solution_id]["average_instruction_cnt"])
        min_instruction_cnt = min(min_instruction_cnt, instruction_cnt)

    return min_instruction_cnt

def get_num_py_solutions(problem_id: str) -> int:
    target_language = "python3"
    alphacode_result = get_alphacode_result(problem_id, sanitize=False)
    num_py_solutions = 0
    for solution_id in alphacode_result:
        if solution_id == "time_limit":
            continue
        if alphacode_result[solution_id]["language"] == target_language:
            num_py_solutions += 1

    return num_py_solutions

def select_problems_to_evaluate(problem_id_list: List[str]) -> List[str]:
    candidate_problem_id_list = []
    for problem_id in problem_id_list:
        if get_num_py_solutions(problem_id) < 20:
            continue
        candidate_solution_id_list = select_candidate_solutions(problem_id)
        print(f"problem_id: {problem_id}, candidate_solution_id_list: {len(candidate_solution_id_list)}, num_py_solutions: {get_num_py_solutions(problem_id)}")
        if len(candidate_solution_id_list) >= 5:
            candidate_problem_id_list.append(problem_id)

    return candidate_problem_id_list

if __name__ == '__main__':
    problem_id_list = ["911_C", "1118_D1", "1332_E", "515_B", "1334_D", "1345_B", "815_B", "63_B", "633_A", "40_B", "1225_D", "192_A", "1184_C1", "1184_A1", "301_B", "1140_D", "758_A", "1328_B", "787_A", "477_A", "478_B", "903_A", "16_B", "189_A", "1216_E2", "937_B", "351_E", "392_A", "520_B", "681_B", "1447_E", "134_B", "793_B", "1446_C", "546_C", "1067_B", "894_B", "808_E", "1322_B", "559_C", "846_B", "1204_E", "289_D", "469_B", "288_B", "1391_D", "1243_D", "480_C", "148_A", "803_D", "999_F", "768_C", "1263_C", "990_B", "1057_C", "1042_E", "1061_C", "991_E", "1336_C", "734_B", "926_B", "1260_D", "171_F", "44_B", "321_A", "1209_B", "1210_A", "1228_E", "656_E", "1236_B", "1286_A", "926_E", "773_B", "1107_E", "1237_E", "1096_F", "1337_E", "932_E", "690_B1", "1165_F1", "1172_B", "171_G", "1139_D", "938_B", "149_D", "706_D", "1059_A", "1213_D1", "1230_C", "131_E", "675_E", "1413_E", "1182_E", "479_E", "1430_E", "513_C", "68_B", "670_F", "996_A", "845_B", "543_A","768_E","476_C","1355_E","1003_A","215_D","569_A","362_B","958_A1","1541_D","181_B","626_C","626_B","1370_C","1430_F","1422_E","687_C","525_C","442_C","827_A","1428_E","889_B","922_A","132_C","484_B","1064_A","1173_D","1190_C","784_C","1511_E","1434_C","1334_E","1242_B","1244_F","731_F","1408_B","312_B","978_E","821_B","909_D","31_C","603_C","898_E","1152_D","1285_D","529_E","566_F","1129_A2","583_D","145_C","349_B","1225_B1","457_A","910_C","1384_B1","321_B","244_B","235_A","1340_B","1114_C","922_B","1398_F","1292_C","621_D","1201_C","804_B","535_B","988_F","595_C","799_C","69_B","1373_F","1195_B","1197_E","366_C","863_B","1253_E","1041_F","690_F1","1422_C","1041_A","962_C","183_A","1131_B","356_C","1284_B","48_C","1340_C","1401_E","784_D","662_D","1475_G","171_C","1131_E","1141_A","1409_F","239_A","656_D","1350_A","690_D1","663_B","1327_A","433_A","641_B","552_C","452_C","1084_E","55_A","1424_J","1082_E","1097_C","1397_B","1263_E","1102_A","1081_C","1401_F","923_A","1540_B","656_F","803_F","23_A","485_A","1063_B","88_D","736_B","278_B","98_A","201_A","1333_F","1342_E","1359_E","1509_C","108_D","1188_B","747_C","577_A","608_C","774_J","237_C","489_C","610_A","1061_B","1246_A","7_E","1102_C","993_B","2_A","452_D","1036_A","128_D","1413_C","594_A","554_C","1375_G","1271_E","958_E1","191_B","1147_C","1434_A","1260_E","690_C1","993_A","143_D","9_E","304_A","199_B","910_B","1203_D1","514_A","656_C","1408_D","1426_E","713_C","148_E","791_B","1228_C","1433_F","1513_E","431_C","1550_D","598_D","868_C","1062_B","630_O","574_D","999_E","909_B","1225_C","1005_E2","255_D","1404_C","848_B","676_E","996_B","111_B","1396_C","1445_D","1288_C","1261_B1","255_C"]
    
    candidate_problem_id_list = select_problems_to_evaluate(problem_id_list)
    print(f"candidate_problem_id_list: {len(candidate_problem_id_list)}")
    print(candidate_problem_id_list)
