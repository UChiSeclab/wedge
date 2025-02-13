#!/usr/bin/env python3

import argparse
import os
import math
from collections import defaultdict
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.path import Path
from matplotlib.patches import PathPatch

# Global lists
csv_files = [
  "wedge-top1inputs.csv",
  "evalperf-slow-top??inputs.csv",
  "evalperf-rand-top??inputs.csv",
  "direct-prompt-top??inputs.csv"
]
csv_labels = [
  "wedge",
  "evalperf-slow",
  "evalperf-rand",
  "direct-prompt"
]


def read_csvs_into_dict(directory_path, csv_files, csv_labels):
  """
  Reads CSV files from the directory path into a dictionary of dictionaries:
   data_dict[label][combined_key] = ic_avg_of_top_k_means
  """
  data_dict = {}
  for csv_file, label in zip(csv_files, csv_labels):
    file_path = os.path.join(directory_path, csv_file)
    df = pd.read_csv(file_path)
    
    # Extract relevant columns by name
    combined_keys = df["combined_key"]
    ic_values = df["ic_avg_of_top_k_means"]
    
    # Build the dictionary for the current CSV
    data_dict[label] = dict(zip(combined_keys, ic_values))
  return data_dict


def compute_normalized_sorted_list_from_dict(values_dict):
  """
  Given a dictionary {combined_key: value}, return a list of
  (combined_key, normalized_value), sorted descending by normalized_value.
  
  This normalizes by dividing each value by the MINIMUM value in the dict.
  """
  items = list(values_dict.items())
  if not items:
    return []
  
  min_val = min(v for (_, v) in items)
  if min_val == 0:
    normalized = [(k, 0.0) for (k, v) in items]
  else:
    normalized = [(k, v / min_val) for (k, v) in items]
  
  # Sort descending by normalized value
  normalized.sort(key=lambda x: x[1], reverse=True)
  return normalized


def compute_trendline_and_r2(x_vals, y_vals):
  """
  Given x_vals, y_vals, compute a linear fit y = m*x + b,
  and return (list_of_y_pred, r2_value).
  """
  slope, intercept = np.polyfit(x_vals, y_vals, 1)
  y_pred = [slope * x + intercept for x in x_vals]

  # Compute R^2 = 1 - SSR/SST
  residuals = [(yv - pv)**2 for yv, pv in zip(y_vals, y_pred)]
  ssr = sum(residuals)
  mean_y = np.mean(y_vals)
  sst = sum((yv - mean_y)**2 for yv in y_vals)
  if sst == 0:
    r2 = 0.0
  else:
    r2 = 1 - ssr / sst
  
  return y_pred, r2


def plot_area_chart_with_trend(data_dict, baseline_label, overlay_label, output_pdf_name):
  """
  Create an overlaid area chart for the baseline_label and overlay_label,
  filtering out any combined_key that is 0 (or missing => 0) in either CSV.
  - The Y-axis is log scale.
  - A trend line (with R^2) is included for each label, with 5 decimal places.
  - Output is saved to the specified PDF file (no plt.show()).
  """

  # Get the full dictionaries for each label
  baseline_dict = data_dict[baseline_label]
  overlay_dict = data_dict[overlay_label]

  # Filter out combined_keys that have 0 in either dictionary
  common_keys = []
  for k in baseline_dict:
    x_val = baseline_dict.get(k, 0)
    y_val = overlay_dict.get(k, 0)
    if x_val != 0 and y_val != 0:
      common_keys.append(k)
  
  # Build new, filtered dictionaries
  baseline_filtered_dict = {k: baseline_dict[k] for k in common_keys}
  overlay_filtered_dict = {k: overlay_dict[k] for k in common_keys}

  # Now compute the normalized/sorted lists
  baseline_data = compute_normalized_sorted_list_from_dict(baseline_filtered_dict)
  overlay_data = compute_normalized_sorted_list_from_dict(overlay_filtered_dict)

  # Prepare the figure
  fig, ax = plt.subplots()

  # ---------- Baseline ----------
  x_vals_base = range(len(baseline_data))
  y_vals_base = [val for (_, val) in baseline_data]
  base_color = "#ff6361"

  ax.fill_between(x_vals_base, 0, y_vals_base, alpha=1.0, color=base_color)
  if len(y_vals_base) > 1:
    y_pred_base, r2_base = compute_trendline_and_r2(x_vals_base, y_vals_base)
    ax.plot(x_vals_base, y_pred_base, linestyle='--', color=base_color,
            label=f"{baseline_label} trend line: R^2={r2_base:.5f}")

  # ---------- Overlay ----------
  x_vals_overlay = range(len(overlay_data))
  y_vals_overlay = [val for (_, val) in overlay_data]
  overlay_color = "#58508d"

  ax.fill_between(x_vals_overlay, 0, y_vals_overlay, alpha=1.0, color=overlay_color)
  if len(y_vals_overlay) > 1:
    y_pred_overlay, r2_overlay = compute_trendline_and_r2(x_vals_overlay, y_vals_overlay)
    ax.plot(x_vals_overlay, y_pred_overlay, linestyle='--', color=overlay_color,
            label=f"{overlay_label}: R^2={r2_overlay:.5f}")

  # Labels and scaling
  ax.set_xlabel("Program ID", fontsize=16)
  ax.set_ylabel("# of instructions (log scale)", fontsize=16)
  ax.set_yscale('log')  # y-axis log scale
  ax.set_ylim(bottom=1.0)
  ax.set_xticks([])   # Remove X-axis ticks
  
  ax.legend(fontsize=16)
  
  fig.savefig(output_pdf_name, format='pdf')
  plt.close(fig)


def plot_pie_chart_winners(data_dict, labels, output_pdf_name):
  """
  For each combined_key, find which label has the largest ic_avg_of_top_k_means.
  Sum up the winners and create a pie chart with no legend/title by default.

  Each slice label will show: 
   label: [no_of_programs]/[total_programs]
  plus the autopct='%1.1f%%' for the percentage.
  """
  # Collect union of all keys
  all_keys = set()
  for label in labels:
    all_keys.update(data_dict[label].keys())
  
  # Initialize win counts
  win_counts = {label: 0 for label in labels}
  
  # Determine the winner for each combined_key
  for k in all_keys:
    best_label = None
    best_value = -1
    for label in labels:
      value = data_dict[label].get(k, 0)  # default to 0 if missing
      if value > best_value:
        best_value = value
        best_label = label
    
    if best_label is not None:
      win_counts[best_label] += 1
  
  # Summaries
  counts = list(win_counts.values())
  total_programs = sum(counts)
  pie_labels = list(win_counts.keys())

  # Print label stats in console
  for lbl in pie_labels:
    count_lbl = win_counts[lbl]
    perc = round(100.0 * count_lbl / total_programs) if total_programs else 0
    print(f"{lbl}: {count_lbl}/{total_programs} ({perc}%)")

  # Prepare pie chart
  fig2, ax2 = plt.subplots()
  
  ax2.pie(
    counts,
    # labels=[f"{lbl}: {win_counts[lbl]}/{total_programs}" for lbl in pie_labels],
    autopct='%1.1f%%',   # Show percentage
    startangle=90,
    colors=["#58508d", "#bc5090", "#ff6361", "#ffa600"],
    textprops={"fontsize":16},
    pctdistance=1.2
  )

  # No legend or title by default
  fig2.savefig(output_pdf_name, format='pdf')
  plt.close(fig2)

def compute_ratio_value(x, y):
  """
  Compute x/y ratio and normalize the values to [-1000, 1000].
  """
  if x <= 0 or y <= 0:
    return None
  if x >= y:
    ratio = (x/y - 1)*100.0
  else:
    ratio = (1 - (y/x))*100.0
  
  # Normalize and lump together
  ratio = math.ceil(ratio)
  if ratio > 1000:
    ratio = 1000
  if ratio < -1000:
    ratio = -1000
  return ratio

def draw_brace(ax, xspan, yy, text, color='black', ls='-', lw=2):
  """
  Draws an annotated horizontal brace on the axes from xmin to xmax at y=yy.
  xspan: (xmin, xmax)
  yy: the vertical coordinate (in data coords)
  text: the label string for the brace
  color, lw: color/line-width for the brace
  """
  xmin, xmax = xspan
  xspan_len = xmax - xmin

  # Current axis limits
  ax_xmin, ax_xmax = ax.get_xlim()
  xax_span = ax_xmax - ax_xmin
  ymin, ymax = ax.get_ylim()
  yspan = ymax - ymin

  # The resolution and logistic shape
  resolution = int(xspan_len/xax_span*100)*2 + 1
  beta = 300.0 / xax_span # shape of the logistic transition

  # Generate x-coords for the brace
  x = np.linspace(xmin, xmax, resolution)
  half_len = int(resolution/2) + 1
  x_half = x[:half_len]

  # logistic-based half-brace
  y_half_brace = (1/(1.+np.exp(-beta*(x_half - x_half[0]))) +
          1/(1.+np.exp(-beta*(x_half - x_half[-1]))))
  # mirror the second half
  y_brace = np.concatenate((y_half_brace, y_half_brace[-2::-1]))

  # Vertical offset for the brace shape
  # The constants .05 and -.01 can be tweaked for how "tall" the brace is
  y_brace = yy + (0.15*y_brace - 0.01)*yspan

  ax.autoscale(False)
  ax.plot(x, y_brace, color=color, ls=ls, lw=lw)

  # Put the text label above the brace
  ax.text((xmax + xmin)/2.0, yy + 0.21*yspan, text,
      ha='center', va='bottom', fontsize=16, color=color)


def plot_histogram_ratios(data_dict, baseline_label, overlay_label, output_pdf_name):
  """
  Modified snippet:
   - Log scale on Y
   - Vertical line at x=0
   - Two curly braces (negative side, positive side) for the percentages
   - No legend
  """
  baseline_dict = data_dict[baseline_label]
  overlay_dict = data_dict[overlay_label]

  ratio_values = []
  all_keys = set(baseline_dict.keys()).intersection(set(overlay_dict.keys()))

  for k in all_keys:
    x_val = baseline_dict.get(k, 0)
    y_val = overlay_dict.get(k, 0)
    # compute_ratio_value() presumably clamps to [-1000, 1000]
    ratio = compute_ratio_value(x_val, y_val) 
    if ratio is not None:
      ratio_values.append(ratio)

  if not ratio_values:
    print(f"No valid ratio values for {baseline_label} vs {overlay_label}. Skipping histogram.")
    return

  neg_values = [r for r in ratio_values if r < 0]
  pos_values = [r for r in ratio_values if r >= 0]

  fig, ax = plt.subplots(figsize=(8,6))

  bins = 40
  min_val, max_val = -1000, 1000

  # Negative side
  ax.hist(neg_values, bins=bins, range=(min_val, max_val),
      color="#58508d", edgecolor="#58508d", alpha=1.0)
  # Positive side
  ax.hist(pos_values, bins=bins, range=(min_val, max_val),
      color="#ff6361", edgecolor="#ff6361", alpha=1.0)

  ax.set_yscale('log') # log scale on Y

  # Vertical line at x=-0.1
  ax.axvline(x=-0.1, color='black', linestyle='--', linewidth=1)

  # X ticks
  ax.set_xticks([ -1000, -500, -100, 0, 100, 500, 1000 ])
  ax.set_xticklabels([ "≤-1000", "-500", "-100", "0", "100", "500", "≥1000" ])

  ax.set_xlabel("normalized ratio (%)", fontsize=16)
  ax.set_ylabel("# of programs (log scale)", fontsize=16)

  # Don't display the legend
  # ax.legend()

  # Figure out how many are "negative" vs "positive" ratios
  total_count = len(ratio_values)
  neg_pct = 100.0 * len(neg_values)/total_count if total_count else 0
  pos_pct = 100.0 * len(pos_values)/total_count if total_count else 0

  # Place curly brakets braces at y=some fraction of the top limit
  y_max = ax.get_ylim()[1]
  brace_y = 0.4 * y_max # slightly below half of the top

  # Draw curly brace from -1000 to 0
  draw_brace(ax, (-1000, 0), brace_y, f"{neg_pct:.1f}%", color='black', ls='-', lw=1)

  # Draw curly brace from 0 to 1000
  draw_brace(ax, (0, 1000), brace_y, f"{pos_pct:.1f}%", color='black', ls='-', lw=1)

  fig.tight_layout()
  fig.savefig(output_pdf_name, format='pdf')
  plt.close(fig)

  # Count and compare positive (baseline) verus negative (overlay) ratios
  pos_bins = [0] * 11
  for r in pos_values:
    if int(r/100) < 0 and int(r/100) >= 11: 
      print(r)

  neg_bins = [0] * 11
  for r in neg_values:
    try: 
      print(r)
      neg_bins[int(r/100)] += 1
    except Exception:
      raise(IndexError)

  # Print in LaTeX table row
  table_line=f"{baseline_label} vs {overlay_label} >>> "
  for i in range (0, len(pos_bins)):
    table_line += f"& {pos_bins[i]} ({neg_bins[i]} $\textcolor{{red}}{{\downarrow\}}$"
  print(table_line)


def main():
  parser = argparse.ArgumentParser(
    description="Generate plots to compare between different techniques."
  )
  parser.add_argument(
    "--directory",
    help="Path to the directory containing CSV files.",
    required=True
  )
  parser.add_argument(
    "--top-k-inputs",
    help="Number of top inputs (by mean) to consider for each solution. Default=10.",
    type=int,
    default=10
  )
  args = parser.parse_args()

  for i in range(0, len(csv_files)):
    csv_files[i] = csv_files[i].replace("??", str(args.top_k_inputs))

  # 1. Read CSVs into a nested dictionary
  data_dict = read_csvs_into_dict(args.directory, csv_files, csv_labels)

  # 2. Generate area-chart PDFs for combos:
  baseline = csv_labels[0]
  for second_idx in range(1, len(csv_labels)):
    second_label = csv_labels[second_idx]

    # Create an area chart of the distribution
    pdf_name_area = f"areachart_{baseline}_vs_{second_label}_top{args.top_k_inputs}inputs.pdf"
    print(f"Creating overlaid area chart for '{baseline}' vs '{second_label}' -> {pdf_name_area}")
    plot_area_chart_with_trend(data_dict, baseline, second_label, pdf_name_area)

    # Create a histogram of ratio differences
    pdf_name_hist = f"histogram_{baseline}_vs_{second_label}_top{args.top_k_inputs}inputs.pdf"
    print(f"Creating histogram of ratio differences -> {pdf_name_hist}")
    plot_histogram_ratios(data_dict, baseline, second_label, pdf_name_hist)

  # 3. Generate pie chart PDF for winners among ALL CSV files
  winners_pdf_name = f"piechart_ranked_top{args.top_k_inputs}inputs.pdf"
  print(f"Creating pie chart of winners -> {winners_pdf_name}")
  plot_pie_chart_winners(data_dict, csv_labels, winners_pdf_name)


if __name__ == "__main__":
  main()
