from concurrent.futures import ProcessPoolExecutor, as_completed
from pathlib import Path
from typing import Tuple, Dict, Any
import config
import csv
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
    if solution_data.get("language") != language:
      continue
    if solution_data.get("online_judge_verdict") != "correct":
      continue
    if solution_data.get("language") != language:
      continue
    wedge_verdicts = solution_data.get("verdict", [])
    if not isinstance(wedge_verdicts, list) or len(wedge_verdicts) < config.REPETITIONS:
      logging.warning(f"problem: {json_data['problem_name']} | solution: {solution_name} | verdicts: {wedge_verdicts}\n")
      continue
    if not all(verdict in {"AC", "TLE", "WA", "KILL"} for verdict in solution_data.get("verdict", [])):
      logging.warning(f"problem: {json_data['problem_name']} | solution: {solution_name} | verdicts: {wedge_verdicts}\n")
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
  Parse one JSON file at path `fp` into a dict, or return None on error.
  """
  try:
    with open(fp, "rb") as f:
      data = orjson.loads(f.read())
    return data
  except Exception as e:
    logging.warning("Error parsing file %s: %s", fp, e)
    return None

def _load_and_merge_one(fp, language):
  """
  Worker for one JSON: returns its own data_rt, data_ic dicts.
  """
  json_data = process_file(str(fp))
  if not json_data:
    return {}, {}

  # Ensure problem_name exists
  if "problem_name" not in json_data:
    json_data["problem_name"] = fp.stem

  partial_rt: Dict[str, Dict[str, list]] = {}
  partial_ic: Dict[str, Dict[str, list]] = {}
  merge_json_data_into_structure(json_data, language, partial_rt, partial_ic)
  return partial_rt, partial_ic

def build_data_structure_for_technique(technique_dir, language):
  """
  Build in-memory nested data structures for running time and instruction counts
  by recursively scanning the technique directory.
  """
  base = Path(technique_dir)
  file_paths = [p for p in base.rglob("*.json") if p.stem in config.PROBLEM_SET]

  max_workers = max(1, (int)((os.cpu_count() or 2) * 0.75))
  data_rt: Dict[str, Dict[str, list]] = {}
  data_ic: Dict[str, Dict[str, list]] = {}

  with ProcessPoolExecutor(max_workers=max_workers) as executor:
    futures = {executor.submit(_load_and_merge_one, fp, language): fp for fp in file_paths}
    for task in as_completed(futures):
      rt_part, ic_part = task.result()
      for key, inputs in rt_part.items():
        dst = data_rt.setdefault(key, {})
        for inp, times in inputs.items():
          dst.setdefault(inp, []).extend(times)
      for key, inputs in ic_part.items():
        dst = data_ic.setdefault(key, {})
        for inp, cnts in inputs.items():
          dst.setdefault(inp, []).extend(cnts)

  logging.info(
    "Built data structures for %d problems (%d JSON files).",
    len(data_rt), len(file_paths)
  )
  return data_rt, data_ic

def parse_blacklist(blacklist_path):
  """
  Parse a blacklisted inputs from a file with following format:
    problem_id, technique_id, input_path, has_output, validity_tag
  """
  blacklist = {}
  with open(blacklist_path, newline='') as csvfile:
    reader = csv.reader(csvfile, delimiter='|')
    for row in reader:
      if not row or len(row) < 5:
        continue
      problem_id, technique_id, input_path, has_output, validity = row
      key = f"{technique_id}-{problem_id}"
      filename = os.path.basename(input_path)
      entry = {
        "input_path": input_path,
        "has_output": has_output.strip().lower() == "true",
        "validity": validity,
      }
      blacklist.setdefault(key, {})[filename] = entry

  for bl in blacklist.keys():
    logging.info("Adding key %s into blacklist dictionary, with %d values", bl, len(blacklist[bl]))

  return blacklist

def write_csv(aggregated_stats, csv_output_path):
  """
  Write aggregated per-solution statistics to a CSV file.
  """
  def pipe_join(seq: list) -> str:
    return "|".join(str(x) for x in seq)

  rows = []
  for sol_key, stats in aggregated_stats.items():
    rt = stats["running_time"]
    ic = stats["instruction_count"]
    rows.append({
      "combined_key": sol_key,
      "rt_top_k_means": pipe_join(rt["top_k_means"]),
      "rt_avg_of_top_k_means": rt["avg_of_top_k_means"],
      "rt_median_of_top_k_means": rt["median_of_top_k_means"],
      "rt_top_k_cvs": pipe_join(rt["top_k_cvs"]),
      "rt_avg_of_top_k_cvs": rt["avg_of_top_k_cvs"],
      "rt_median_of_top_k_cvs": rt["median_of_top_k_cvs"],
      "ic_top_k_means": pipe_join(ic["top_k_means"]),
      "ic_avg_of_top_k_means": ic["avg_of_top_k_means"],
      "ic_median_of_top_k_means": ic["median_of_top_k_means"],
      "ic_top_k_cvs": pipe_join(ic["top_k_cvs"]),
      "ic_avg_of_top_k_cvs": ic["avg_of_top_k_cvs"],
      "ic_median_of_top_k_cvs": ic["median_of_top_k_cvs"],
    })

  df = pd.DataFrame(rows)
  df.to_csv(csv_output_path, index=False)
  logging.info("Wrote CSV to %s", csv_output_path)