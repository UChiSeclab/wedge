#!/usr/bin/env python3
"""
This program processes raw JSON files containing performance metrics for different
techniques and computes aggregated statistics for various "top-K" inputs (e.g., top-10,
top-5, top-3, top-1). It then outputs per-technique CSV files, generates area plots, and
produces a LaTeX table summarizing the results.
"""

import argparse
import concurrent.futures
import csv
import heapq
import logging
import matplotlib.pyplot as plt
import numpy as np
import orjson
import os
import pandas as pd
import seaborn as sns
import statistics
import sys

# Global set of known problems
PROBLEM_SET = set(["1003_A", "1107_E", "1225_C", "131_E", " 1404_C.", "171_C.", "321_B.", "485_A.", "603_C", "706_D.", 
  "821_B.", "958_A1.json", "1005_E2.", "1114_C", "1225_D", "1322_B", "1408_D.", "171_G.", "349_B.", 
  "489_C.", "608_C", "713_C.", "827_A.", "958_E1.json", "1041_A", "111_B", " 1228_C", "1328_B", "1409_F.", 
  "181_B.", "351_E.", "48_C", "621_D", "731_F.", "846_B.", "978_E.json", "1041_F", "1129_A2.", "1228_E", 
  "1332_E", "1413_C.", "189_A.", "356_C.", "513_C.", "626_C", "734_B.", "848_B.", "988_F.json", "1042_E", 
  "1131_B", "1230_C", "1334_E", "1424_J.", "191_B.", "366_C.", "515_B.", "633_A", "747_C.", "863_B.", "993_A.json", 
  "1057_C", "1141_A", "1242_B", "1340_B", "1426_E.", "199_B.", "40_B", "520_B.", "63_B", " 758_A.", "868_C.", 
  "993_B.json", "1059_A", "1147_C", "1243_D", "1340_C", "1428_E.", "215_D.", "431_C.", "525_C.", "656_E", "768_C.", 
  "889_B.", "999_E.json", "1061_B", "1165_F1.", "1246_A", "1342_E", "1433_F.", "237_C.", "433_A.", "529_E.", 
  "662_D", "768_E.", "894_B.", "999_F.json", "1061_C", "1182_E", "1253_E", "1345_B", "143_D", "239_A.", "44_B", 
  "543_A.", "663_B", "773_B.", "898_E.", "9_E.json", "1063_B", "1184_C1.", "1260_D", "1350_A", "1446_C.", "255_D.", 
  "452_C.", "546_C.", "670_F", "774_J.", "903_A.json", "1064_A", "1201_C", "1260_E", "1355_E", "1447_E.", "278_B.", 
  "452_D.", "552_C.", "675_E", "787_A.", "910_B.json", "1067_B", "1203_D1.", "1261_B1.", "1359_E", "145_C", "288_B.", 
  "457_A.", "554_C.", "676_E", "791_B.", "910_C.json", "1081_C", "1204_E", "1263_C", "1370_C", "1475_G.", "289_D.", 
  "469_B.", "559_C.", "687_C", "793_B.", "911_C.json", "1082_E", "1209_B", "1271_E", "1373_F", "148_A", "2_A", " 476_C.", 
  "566_F.", "68_B", " 799_C.", "922_A.json", "108_D", " 1210_A", "1286_A", "1384_B1.", "148_E", "301_B.", "477_A.", 
  "574_D.", "690_B1.", "7_E", " 926_B.json", "1096_F", "1213_D1.", "1288_C", "1396_C", "1513_E.", "312_B.", "478_B.", 
  "577_A.", "690_C1.", "803_D.", "932_E.json", "1097_C", "1216_E2.", "128_D", " 1397_B", "1550_D.", "31_C", "479_E.", 
  "583_D.", "690_F1.", "803_F.", "937_B.json", "1102_C", "1225_B1.", "1292_C", "1401_E", "16_B", " 321_A.", "484_B.", 
  "598_D.", "69_B", " 808_E.", "938_B"])

# Global constants for top-K values and technique labels
TOP_K_VALUES = [10, 5, 3, 1]
TECHNIQUES = [
  "corpus_instrument_fuzz_mutator_with_constraint_per_solution", 
  "evalperf_slow_solution", 
  "evalperf_random_solution", 
  "plain_problem"
 ]

# Configure logging to display warnings and info messages
logging.basicConfig(level=logging.INFO)


def compute_stats(values):
  """
  Compute vectorized statistics for a list of numeric values using NumPy.
  
  Args:
    values (list of float): List of measurements (e.g., 5 runs)
  
  Returns:
    dict: Dictionary with keys:
       - "raw": The original list
       - "mean": Arithmetic mean
       - "median": Median
       - "min": Minimum value
       - "max": Maximum value
       - "cv": Coefficient of variation (stdev/mean; 0 if mean is zero)
  """
  arr = np.array(values)
  if arr.size == 0:
    return {"raw": values, "mean": 0, "median": 0, "min": 0, "max": 0, "cv": 0}
  mean_val = np.mean(arr)
  median_val = np.median(arr)
  min_val = np.min(arr)
  max_val = np.max(arr)
  stdev_val = np.std(arr, ddof=1) if arr.size > 1 else 0
  cv_val = stdev_val / mean_val if mean_val != 0 else 0
  return {
    "raw": values,
    "mean": mean_val,
    "median": median_val,
    "min": min_val,
    "max": max_val,
    "cv": cv_val,
  }


def merge_json_data_into_structure(json_data, language, data_rt, data_ic):
  """
  Merge data from a parsed JSON into the provided data structures.
  
  For each solution in json_data that passes language and correctness checks, 
  update the nested dictionaries for running time and instruction count.
  
  Args:
    json_data (dict): Parsed JSON data
    language (str): Programming language filter (e.g., "cpp")
    data_rt (dict): Dictionary to store running time data
    data_ic (dict): Dictionary to store instruction count data
  """
  # Use the provided problem name if available; otherwise, use a placeholder
  probname = json_data.get("problem_name", "unknown")
  
  for solution_name, solution_data in json_data.items():
    if not solution_name.startswith("solutions_"):
      continue
    if solution_data.get("online_judge_verdict") != "correct":
      continue
    if solution_data.get("language") != language:
      continue
    combined_key = f"{probname}-{solution_name}"
    data_rt.setdefault(combined_key, {})
    data_ic.setdefault(combined_key, {})
    time_dict = solution_data.get("time_dict", {})
    ic_dict = solution_data.get("instruction_cnt_dict", {})
    for test_input, times in time_dict.items():
      if times:
        # Append the raw 5 run values
        data_rt[combined_key].setdefault(test_input, []).extend(times)
    for test_input, counts in ic_dict.items():
      if counts:
        data_ic[combined_key].setdefault(test_input, []).extend(counts)


def build_data_structure_for_technique(technique_dir, language):
  """
  Build an in-memory nested data structure for a given technique.
  
  Recursively walks through the JSON files in technique_dir, parses them,
  and merges the data into two dictionaries: one for running time and 
  one for instruction counts.
  
  Args:
    technique (str): Technique label (e.g., "wedge")
    technique_dir (str): Directory containing JSON files for the technique
    language (str): Programming language filter
  
  Returns:
    tuple: (data_rt, data_ic) where each is a dict structured as:
        { solution_key: { test_input: [5 run values] } }
  """
  data_rt = {} # Running time data
  data_ic = {} # Instruction count data
  file_paths = []
  for root, _, files in os.walk(technique_dir):
    for file in files:
      if file.endswith(".json"):
        file_paths.append(os.path.join(root, file))
  
  def process_file(fp):
    try:
      with open(fp, "rb") as f:
        data = orjson.loads(f.read())
        sys.stdout.write(f"Processing {fp} ...\n")
      return data
    except Exception as e:
      logging.warning("Error parsing file %s: %s", fp, e)
      return None

  with concurrent.futures.ThreadPoolExecutor() as executor:
    futures = {executor.submit(process_file, fp): fp for fp in file_paths}
    for future in concurrent.futures.as_completed(futures):
      json_data = future.result()
      if json_data is None:
        continue
      # Set problem_name if not present
      if "problem_name" not in json_data:
        json_data["problem_name"] = os.path.splitext(os.path.basename(futures[future]))[0]
      merge_json_data_into_structure(json_data, language, data_rt, data_ic)
  return data_rt, data_ic


def compute_stats_for_structure(data_struct):
  """
  Convert a nested data structure of raw run values into computed statistics.
  
  Args:
    data_struct (dict): Nested dictionary structured as:
              { solution_key: { test_input: [list of run values] } }
  
  Returns:
    dict: Same structure but with each list of run values replaced by a stats dict
  """
  result = {}
  for sol_key, inputs in data_struct.items():
    result[sol_key] = {}
    for inp, runs in inputs.items():
      result[sol_key][inp] = compute_stats(runs)
  return result


def aggregate_top_k(input_stats, top_k):
  """
  Aggregate statistics for the top-K test inputs (sorted by descending mean).
  
  Args:
    input_stats (dict): Mapping { test_input: stats dict }
    top_k (int): Number of top inputs to select
  
  Returns:
    dict: Contains:
       - "top_k_means": List of means of the top-K inputs
       - "avg_of_top_k_means": Average of these means
       - "median_of_top_k_means": Median of these means
       - "top_k_cvs": List of CVs for the top-K inputs
       - "avg_of_top_k_cvs": Average CV
       - "median_of_top_k_cvs": Median CV
  """
  items = []
  for test_input, stats_dict in input_stats.items():
    mean_val = stats_dict.get("mean", 0)
    cv_val = stats_dict.get("cv", 0)
    items.append((test_input, mean_val, cv_val))
  top_items = heapq.nlargest(top_k, items, key=lambda x: x[1])
  if not top_items:
    return {
      "top_k_means": [],
      "avg_of_top_k_means": 0,
      "median_of_top_k_means": 0,
      "top_k_cvs": [],
      "avg_of_top_k_cvs": 0,
      "median_of_top_k_cvs": 0,
    }
  top_means = [itm[1] for itm in top_items]
  top_cvs = [itm[2] for itm in top_items]
  avg_of_top_means = statistics.mean(top_means)
  median_of_top_means = statistics.median(top_means)
  avg_of_top_cvs = statistics.mean(top_cvs)
  median_of_top_cvs = statistics.median(top_cvs)
  return {
    "top_k_means": top_means,
    "avg_of_top_k_means": avg_of_top_means,
    "median_of_top_k_means": median_of_top_means,
    "top_k_cvs": top_cvs,
    "avg_of_top_k_cvs": avg_of_top_cvs,
    "median_of_top_k_cvs": median_of_top_cvs,
  }


def compute_aggregate_stats_for_structure(data_rt, data_ic, top_k):
  """
  Compute aggregated statistics per solution and global statistics for a given top-K value.
  
  Converts raw data to stats and aggregates per solution (selecting top-K inputs), then collects global averages.
  
  Args:
    data_rt (dict): Running time data structure: { solution: { input: [runs] } }
    data_ic (dict): Instruction count data structure: { solution: { input: [runs] } }
    top_k (int): Top-K value

  Returns:
    dict: Contains:
       - "aggregated_stats": Dict of per-solution aggregates
       - "global_running_avg_means": List of running time average means
       - "global_running_avg_cvs": List of running time average CVs
       - "global_instruction_avg_means": List of instruction count average means
       - "global_instruction_avg_cvs": List of instruction count average CVs
  """
  rt_stats = compute_stats_for_structure(data_rt)
  ic_stats = compute_stats_for_structure(data_ic)
  aggregated_stats = {}
  global_running_avg_means = []
  global_running_avg_cvs = []
  global_instruction_avg_means = []
  global_instruction_avg_cvs = []
  all_solution_keys = set(rt_stats.keys()) | set(ic_stats.keys())
  for sol in sorted(all_solution_keys):
    rt_input_stats = rt_stats.get(sol, {})
    ic_input_stats = ic_stats.get(sol, {})
    rt_agg = aggregate_top_k(rt_input_stats, top_k)
    ic_agg = aggregate_top_k(ic_input_stats, top_k)
    aggregated_stats[sol] = {"running_time": rt_agg, "instruction_count": ic_agg}
    global_running_avg_means.append(rt_agg["avg_of_top_k_means"])
    global_running_avg_cvs.append(rt_agg["avg_of_top_k_cvs"])
    global_instruction_avg_means.append(ic_agg["avg_of_top_k_means"])
    global_instruction_avg_cvs.append(ic_agg["avg_of_top_k_cvs"])
  return {
    "aggregated_stats": aggregated_stats,
    "global_running_avg_means": global_running_avg_means,
    "global_running_avg_cvs": global_running_avg_cvs,
    "global_instruction_avg_means": global_instruction_avg_means,
    "global_instruction_avg_cvs": global_instruction_avg_cvs,
  }


def write_csv(aggregated_stats, csv_output_path):
  """
  Write aggregated per-solution statistics to a CSV file using pandas.
  
  Args:
    aggregated_stats (dict): Per-solution aggregated data
    csv_output_path (str): Path to output CSV file
  """
  rows = []
  for sol, stats in aggregated_stats.items():
    rt = stats["running_time"]
    ic = stats["instruction_count"]
    row = {
      "combined_key": sol,
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
  os.makedirs(os.path.dirname(csv_output_path), exist_ok=True)
  df.to_csv(csv_output_path, index=False)
  logging.info("Wrote CSV to %s", csv_output_path)


def compute_ratio_and_arrow(baseline, value):
  """
  Compute the ratio between baseline and a given value, and determine arrow annotation.
  
  Args:
    baseline (float): Baseline value
    value (float): Value to compare
  
  Returns:
    str: Formatted string with arrow and ratio (e.g., "(↓2.5×)" or "(↑1.3×)")
  """
  if value == 0:
    return "(N/A)"
  ratio = baseline / value
  ratio_str = f"{ratio:.1f}\times"
  arrow = "↓" if baseline > value else "↑"
  return f"({arrow}{ratio_str})"


def plot_area_chart(data_dict, baseline_label, overlay_label, output_pdf_name,
                 x_label="Program ID", y_label="Metric (normalized)",
                 chart_title="Area Chart Comparison", legend=True,
                 transparency=0.7, line_style="solid", border=True,
                 color_scheme="deep", custom_colors=None):
  """
  Create an overlaid area chart comparing two techniques (using SEABORN).
  
  Plots two area charts (one for baseline and one for overlay) without a trendline.
  Provides configurable parameters for axis labels, title, legend, transparency,
  line style, border, and color scheme.
  
  Args:
    data_dict (dict): Dictionary mapping technique labels to dictionaries of {combined_key: numeric value}
    baseline_label (str): Label for the baseline technique
    overlay_label (str): Label for the overlay technique
    output_pdf_name (str): Path to save the PDF plot
    x_label (str): Label for the x-axis
    y_label (str): Label for the y-axis
    chart_title (str): Title for the chart
    legend (bool): Whether to display the legend
    transparency (float): Alpha value (0-1) for area fill transparency
    line_style (str): Line style for plotted lines (e.g., "solid", "dotted")
    border (bool): Whether to display the plot border
    color_scheme (str): Predefined seaborn palette name
    custom_colors (list): Optional list of two color strings for baseline and overlay
  
  Returns:
    None
  """
  if custom_colors is None:
    sns.set_theme(style="white", palette=color_scheme)
  else:
    sns.set_theme(style="white", palette=custom_colors)
  
  baseline_dict = data_dict.get(baseline_label, {})
  overlay_dict = data_dict.get(overlay_label, {})
  common_keys = [k for k in baseline_dict if baseline_dict.get(k, 0) and overlay_dict.get(k, 0)]
  baseline_filtered = {k: baseline_dict[k] for k in common_keys}
  overlay_filtered = {k: overlay_dict[k] for k in common_keys}
  
  baseline_data = compute_normalized_sorted_list_from_dict(baseline_filtered)
  overlay_data = compute_normalized_sorted_list_from_dict(overlay_filtered)
  
  x_vals_base = list(range(len(baseline_data)))
  y_vals_base = [val for _, val in baseline_data]
  x_vals_overlay = list(range(len(overlay_data)))
  y_vals_overlay = [val for _, val in overlay_data]
  
  fig, ax = plt.subplots()
  sns.lineplot(x=x_vals_base, y=y_vals_base, ax=ax, label=baseline_label,
         linestyle=line_style,
         color=custom_colors[0] if custom_colors else None)
  ax.fill_between(x_vals_base, 0, y_vals_base,
          alpha=transparency,
          color=custom_colors[0] if custom_colors else None)
  
  sns.lineplot(x=x_vals_overlay, y=y_vals_overlay, ax=ax, label=overlay_label,
         linestyle=line_style,
         color=custom_colors[1] if custom_colors else None)
  ax.fill_between(x_vals_overlay, 0, y_vals_overlay,
          alpha=transparency,
          color=custom_colors[1] if custom_colors else None)
  
  ax.set_xlabel(x_label, fontsize=16)
  ax.set_ylabel(y_label, fontsize=16)
  ax.set_title(chart_title, fontsize=18)
  
  if not legend:
    ax.get_legend().remove()
  else:
    ax.legend(fontsize=14)
  
  if not border:
    for spine in ax.spines.values():
      spine.set_visible(False)
  
  fig.savefig(output_pdf_name, format='pdf')
  plt.close(fig)


def compute_normalized_sorted_list_from_dict(values_dict):
  """
  Normalize values by dividing by the minimum and return a sorted list.
  
  Args:
    values_dict (dict): Mapping of keys to numeric values
  
  Returns:
    list: Sorted list of (key, normalized_value) tuples in descending order
  """
  items = list(values_dict.items())
  if not items:
    return []
  min_val = min(v for _, v in items)
  if min_val == 0:
    normalized = [(k, 0.0) for k, v in items]
  else:
    normalized = [(k, v / min_val) for k, v in items]
  normalized.sort(key=lambda x: x[1], reverse=True)
  return normalized


def safe_mean(lst):
  """
  Return the mean of a list if not empty; otherwise, 0.
  
  Args:
    lst (list): List of numeric values
  
  Returns:
    float: Mean of the list or 0 if empty
  """
  return statistics.mean(lst) if lst else 0


def generate_latex_table(global_stats_by_topk):
  """
  Generate a LaTeX table summarizing instruction counts (normalized by 1e9) and running time (ms)
  for each technique across different top-K values.
  
  For each top-K and each technique, displays "mean ± variation". For non-baseline techniques,
  shows the ratio (with arrow) relative to the wedge baseline.
  
  Args:
    global_stats_by_topk (dict): Nested dictionary with structure:
      { top_k: { technique: { 'global_running_avg_means': [...],
                   'global_running_avg_cvs': [...],
                   'global_instruction_avg_means': [...],
                   'global_instruction_avg_cvs': [...] } } }
  """
  header = r"\begin{tabular}{l" + "c" * len(TOP_K_VALUES) + "}"
  header += "\n\\toprule\n"
  header += "Technique & " + " & ".join([f"Top-{k}" for k in TOP_K_VALUES]) + r" \\"
  header += "\n\\midrule\n"
  lines = [header]
  
  # Instruction counts section
  lines.append(r"\multicolumn{" + str(len(TOP_K_VALUES)+1) +
         r"}{c}{\textbf{Number of instructions ($\times10^9$)}} \\")
  for tech in TECHNIQUES:
    row = [tech.upper()]
    for k in TOP_K_VALUES:
      stats = global_stats_by_topk.get(k, {}).get(tech, {})
      ic_avg = safe_mean(stats.get("global_instruction_avg_means", [])) / 1e9
      ic_var = safe_mean(stats.get("global_instruction_avg_cvs", []))
      if tech != "wedge":
        wedge_stats = global_stats_by_topk.get(k, {}).get("wedge", {})
        wedge_ic_avg = safe_mean(wedge_stats.get("global_instruction_avg_means", [])) / 1e9
        ratio_str = compute_ratio_and_arrow(wedge_ic_avg, ic_avg)
      else:
        ratio_str = ""
      row.append(f"{ic_avg:.2f}±{ic_var:.2f} {ratio_str}")
    lines.append(" & ".join(row) + r" \\")
  
  lines.append(r"\midrule")
  
  # Running time section
  lines.append(r"\multicolumn{" + str(len(TOP_K_VALUES)+1) +
         r"}{c}{\textbf{Running time (ms)}} \\")
  for tech in TECHNIQUES:
    row = [tech.upper()]
    for k in TOP_K_VALUES:
      stats = global_stats_by_topk.get(k, {}).get(tech, {})
      rt_avg = safe_mean(stats.get("global_running_avg_means", []))
      rt_var = safe_mean(stats.get("global_running_avg_cvs", []))
      if tech != "wedge":
        wedge_stats = global_stats_by_topk.get(k, {}).get("wedge", {})
        wedge_rt_avg = safe_mean(wedge_stats.get("global_running_avg_means", []))
        ratio_str = compute_ratio_and_arrow(wedge_rt_avg, rt_avg)
      else:
        ratio_str = ""
      row.append(f"{rt_avg:.0f}±{rt_var:.1f} {ratio_str}")
    lines.append(" & ".join(row) + r" \\")
  
  lines.append(r"\bottomrule")
  lines.append(r"\end{tabular}")
  latex_table = "\n".join(lines)
  print(latex_table)


def main():
  """
  Main entry point.
  
  Processes JSON files for each technique to build an in-memory data structure, computes aggregated
  statistics for fixed top-K values, writes CSV files, generates area plots, and produces a LaTeX table.
  
  Command-line arguments:
   --json-dir: Base directory with JSON files (organized by technique subdirectories)
   --output-csv-dir: Directory where CSV files will be written
   --plot-output-dir: Directory to save PDF plots
   --language: Programming language filter (default: "cpp")
  
  Usage:
   python this_script.py --json-dir ./json_data --output-csv-dir ./csv_out --plot-output-dir ./plots --language cpp
  """
  parser = argparse.ArgumentParser(
    description="Process JSON files into in-memory nested structures and compute performance stats."
  )
  parser.add_argument("--json-dir", required=True,
            help="Base directory containing JSON files organized by technique subdirectories.")
  parser.add_argument("--output-csv-dir", required=True,
            help="Directory to write generated CSV files.")
  parser.add_argument("--plot-output-dir", required=True,
            help="Directory to save generated PDF plots.")
  parser.add_argument("--language", default="cpp",
            help="Programming language filter (default: cpp).")
  args = parser.parse_args()

  # Build in-memory data structures for each technique
  technique_data = {}
  for tech in TECHNIQUES:
    tech_dir = os.path.join(args.json_dir, tech)
    logging.info("Processing technique %s from directory %s", tech, tech_dir)
    rt_data, ic_data = build_data_structure_for_technique(tech_dir, args.language)
    technique_data[tech] = {"rt": rt_data, "ic": ic_data}

  # Dictionary to store global stats for the LaTeX table
  global_stats_by_topk = {}

  # For each fixed top-K value, compute aggregated stats and write CSV files
  for top_k in TOP_K_VALUES:
    global_stats_by_topk[top_k] = {}
    for tech in TECHNIQUES:
      logging.info("Aggregating stats for technique %s with top-%d", tech, top_k)
      data_rt = technique_data[tech]["rt"]
      data_ic = technique_data[tech]["ic"]
      agg_results = compute_aggregate_stats_for_structure(data_rt, data_ic, top_k)
      global_stats_by_topk[top_k][tech] = agg_results
      csv_filename = f"{tech}-top{top_k}inputs.csv"
      csv_output_path = os.path.join(args.output_csv_dir, csv_filename)
      write_csv(agg_results["aggregated_stats"], csv_output_path)

  # Generate area plots comparing baseline ("wedge") with other techniques
  for top_k in TOP_K_VALUES:
    baseline_csv = os.path.join(args.output_csv_dir, f"wedge-top{top_k}inputs.csv")
    try:
      df_baseline = pd.read_csv(baseline_csv)
    except Exception as e:
      logging.warning("Could not read CSV file %s: %s", baseline_csv, e)
      continue
    baseline_dict = dict(zip(df_baseline["combined_key"], df_baseline["ic_avg_of_top_k_means"]))
    for tech in TECHNIQUES:
      if tech == "wedge":
        continue
      overlay_csv = os.path.join(args.output_csv_dir, f"{tech}-top{top_k}inputs.csv")
      try:
        df_overlay = pd.read_csv(overlay_csv)
      except Exception as e:
        logging.warning("Could not read CSV file %s: %s", overlay_csv, e)
        continue
      overlay_dict = dict(zip(df_overlay["combined_key"], df_overlay["ic_avg_of_top_k_means"]))
      plot_filename = os.path.join(args.plot_output_dir, f"areachart_{top_k}_{tech}.pdf")
      data_dict = {"wedge": baseline_dict, tech: overlay_dict}
      plot_area_chart(
        data_dict, 
        baseline_label="wedge", 
        overlay_label=tech, 
        output_pdf_name=plot_filename,
        x_label="Program ID",
        y_label="Instruction Count (normalized)",
        chart_title=f"Area Chart: Top-{top_k} ({tech} vs WEDGE)",
        legend=True,
        transparency=0.7,
        line_style="solid",
        border=True,
        color_scheme="deep" # can provide custom_colors array of custom colors
      )
      logging.info("Generated area plot: %s", plot_filename)

  # Generate LaTeX table summarizing the results
  generate_latex_table(global_stats_by_topk)


if __name__ == "__main__":
  main()

