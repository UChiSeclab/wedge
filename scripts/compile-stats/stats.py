import numpy as np
import statistics

def compute_stats(values):
  """
  Compute vectorized statistics for a list of numeric values.
  Returns a dict with keys: raw, mean, median, min, max, cv.
  """
  if not values:
    return {"raw": values, "mean": 0, "median": 0, "min": 0, "max": 0, "cv": 0}
  arr = np.array(values)
  mean_val = np.mean(arr)
  median_val = np.median(arr)
  min_val = np.min(arr)
  max_val = np.max(arr)
  stdev_val = np.std(arr, ddof=1) if len(arr) > 1 else 0
  cv_val = stdev_val / mean_val if mean_val != 0 else 0
  return {
    "raw": values,
    "mean": mean_val,
    "median": median_val,
    "min": min_val,
    "max": max_val,
    "cv": cv_val,
  }

def compute_stats_for_structure(data_struct):
  """
  Transform a nested data structure (solution -> test input -> raw runs)
  into a structure with computed statistics.
  """
  return {
    sol_key: {
      inp: compute_stats(runs)
      for inp, runs in inputs.items()
    }
    for sol_key, inputs in data_struct.items()
  }

def aggregate_top_k(input_stats, top_k):
  """
  Select the top-K test inputs (by descending mean) and compute aggregate stats.
  """
  input_stats_list = [
    (test_input, stats.get("mean", 0), stats.get("cv", 0))
    for test_input, stats in input_stats.items()
  ]
  top_items = sorted(input_stats_list, key=lambda x: x[1], reverse=True)[:top_k]
  if not top_items:
    return {
      "top_k_means": [],
      "avg_of_top_k_means": 0,
      "median_of_top_k_means": 0,
      "top_k_cvs": [],
      "avg_of_top_k_cvs": 0,
      "median_of_top_k_cvs": 0,
    }
  top_means = [item[1] for item in top_items]
  top_cvs = [item[2] for item in top_items]
  return {
    "top_k_means": top_means,
    "avg_of_top_k_means": statistics.mean(top_means),
    "median_of_top_k_means": statistics.median(top_means),
    "top_k_cvs": top_cvs,
    "avg_of_top_k_cvs": statistics.mean(top_cvs),
    "median_of_top_k_cvs": statistics.median(top_cvs),
  }

def compute_aggregate_stats_for_structure(data_rt, data_ic, top_k):
  """
  Compute aggregated statistics per solution and collect global stats for a given top-K value.
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

def compute_ratio_and_arrow(baseline, value, latex_fmt=True):
  """
  Compute the ratio between baseline and value and return a formatted string with an arrow.
  """
  if value == 0:
    return "(N/A)"
  if baseline == value:
    return "(=1.0\\times)" if latex_fmt else "(=1.0×)"
  
  ratio = 1.0 * baseline / value
  if latex_fmt:
    ratio_str = f"{ratio:.1f}\\times"
    arrow = "\\textcolor{red}{\\downarrow}" if baseline > value else "\\textcolor{green}{\\uparrow}"
  else: 
    ratio_str = f"{ratio:.1f}×"
    arrow = "↓" if baseline > value else "↑"
  
  return f"({arrow}{ratio_str})"

def compute_normalized_sorted_list_from_dict(values_dict, epsilon=1e-6):
  """
  Normalize the dictionary values by dividing by the minimum positive value and
  return a list sorted in descending order.
  """
  items = list(values_dict.items())
  if not items:
    return []
  nonzero_values = [v for _, v in items if v > 0]
  min_val = min(nonzero_values) if nonzero_values else epsilon
  normalized = [(k, (v / min_val) if v > 0 else 0.0) for k, v in items]
  normalized.sort(key=lambda x: x[1], reverse=True)
  return normalized

def safe_mean(lst):
  """
  Return the mean of a list if not empty; otherwise, return 0.
  """
  try:
    return statistics.mean(lst) if lst else 0
  except Exception:
    return 0

def compute_ratio_value(x, y):
  """
  Compute the percentage ratio difference between x and y, normalized to [-1000, 1000].
  """
  if x <= 0 or y <= 0:
    return None
  if x >= y:
    ratio = (x / y - 1) * 100.0
  else:
    ratio = (1 - (y / x)) * 100.0
  ratio = int(np.ceil(ratio))
  ratio = min(max(ratio, -1000), 1000)
  return ratio