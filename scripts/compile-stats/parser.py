import concurrent.futures
import config
import logging
import orjson
import os
import pandas as pd

def merge_json_data_into_structure(json_data, language, data_rt, data_ic):
  """
  Merge data from parsed JSON into provided data_rt and data_ic dictionaries.
  """
  probname = json_data.get("problem_name", "unknown").strip()
  solutions = {k: v for k, v in json_data.items() if k.startswith("solutions_")}
  for solution_name, solution_data in solutions.items():
    if solution_data.get("online_judge_verdict") != "correct":
      continue
    if solution_data.get("language") != language:
      continue
    combined_key = f"{probname}-{solution_name.strip()}"
    data_rt.setdefault(combined_key, {})
    data_ic.setdefault(combined_key, {})
    rt_dict = solution_data.get("time_dict", {})
    ic_dict = solution_data.get("instruction_cnt_dict", {})
    for test_input, times in rt_dict.items():
      if isinstance(times, list) and times:
        data_rt[combined_key].setdefault(test_input, []).extend(times)
    for test_input, counts in ic_dict.items():
      if isinstance(counts, list) and counts:
        data_ic[combined_key].setdefault(test_input, []).extend(counts)

def process_file(fp):
  """
  Process a single JSON file and return the parsed data or None if an error occurs.
  """
  try:
    with open(fp, "rb") as f:
      data = orjson.loads(f.read())
    logging.info("Processing %s ...", fp)
    return data
  except Exception as e:
    logging.warning("Error parsing file %s: %s", fp, e)
    return None

def build_data_structure_for_technique(technique_dir, language):
  """
  Build in-memory nested data structures for running time and instruction counts
  by recursively scanning the technique directory.
  """
  data_rt = {} # Running time data
  data_ic = {} # Instruction count data
  file_paths = []
  for root, _, files in os.walk(technique_dir):
    for file in files:
      basename = os.path.splitext(file)[0].strip()
      if file.endswith(".json") and basename in config.PROBLEM_SET:
        file_paths.append(os.path.join(root, file))
  
  max_workers = (os.cpu_count() - 1) if (os.cpu_count() and os.cpu_count() > 1) else 1
  with concurrent.futures.ThreadPoolExecutor(max_workers=max_workers) as executor:
    futures = {executor.submit(process_file, fp): fp for fp in file_paths}
    for future in concurrent.futures.as_completed(futures):
      json_data = future.result()
      if json_data is None:
        continue
      if "problem_name" not in json_data:
        json_data["problem_name"] = os.path.splitext(os.path.basename(futures[future]))[0].strip()
      merge_json_data_into_structure(json_data, language, data_rt, data_ic)
  return data_rt, data_ic

def write_csv(aggregated_stats, csv_output_path):
  """
  Write aggregated per-solution statistics to a CSV file.
  """
  rows = []
  for solution_key, stats in aggregated_stats.items():
    rt = stats["running_time"]
    ic = stats["instruction_count"]
    row = {
      "combined_key": solution_key,
      "rt_top_k_means": "|".join(map(str, rt["top_k_means"])),
      "rt_avg_of_top_k_means": rt["avg_of_top_k_means"],
      "rt_median_of_top_k_means": rt["median_of_top_k_means"],
      "rt_top_k_cvs": "|".join(map(str, rt["top_k_cvs"])),
      "rt_avg_of_top_k_cvs": rt["avg_of_top_k_cvs"],
      "rt_median_of_top_k_cvs": rt["median_of_top_k_cvs"],
      "ic_top_k_means": "|".join(map(str, ic["top_k_means"])),
      "ic_avg_of_top_k_means": ic["avg_of_top_k_means"],
      "ic_median_of_top_k_means": ic["median_of_top_k_means"],
      "ic_top_k_cvs": "|".join(map(str, ic["top_k_cvs"])),
      "ic_avg_of_top_k_cvs": ic["avg_of_top_k_cvs"],
      "ic_median_of_top_k_cvs": ic["median_of_top_k_cvs"],
    }
    rows.append(row)
  df = pd.DataFrame(rows)
  df.to_csv(csv_output_path, index=False)
  logging.info("Wrote CSV to %s", csv_output_path)