from typing import Dict, Any, Tuple, List
import config
import logging
import matplotlib.pyplot as plt
import numpy as np
import os
import seaborn as sns
import stats as mystats
import statistics

def get_color_for_technique(tech, custom_colors):
  keys = list(config.TECHNIQUES.keys())
  idx = keys.index(tech)
  if custom_colors and idx < len(custom_colors):
    return custom_colors[idx]
  return None

def generate_overlay_plot(data_dict, output_pdf_name,
          x_label="Program ID", y_label="Metric (normalized)",
          chart_title=None, legend=True,
          transparency=1.0, line_style="solid", markers=["o", "s", "D", "^"],
          border=True, areafill=True, color_scheme="magma", custom_colors=None,
          yscale="log", y_min=1):
  """
  Create an overlaid area (line) chart for multiple techniques.
  """
  if custom_colors is None:
    sns.set_theme(style="white", palette=color_scheme)
  else:
    sns.set_theme(style="white", palette=custom_colors)
  
  fig, ax = plt.subplots()
  for i, tech in enumerate(config.TECHNIQUES):
    if tech not in data_dict:
      logging.warning("Data for technique %s not found; skipping.", tech)
      continue
    normalized_data = mystats.compute_normalized_sorted_list_from_dict(data_dict[tech])
    x_vals = list(range(len(normalized_data)))
    y_vals = [val for _, val in normalized_data]
    color = get_color_for_technique(tech, custom_colors)
    if markers is not None:
      marker = markers[i] if i < len(markers) else "o"
      sns.lineplot(
        x=x_vals, y=y_vals, ax=ax, label=tech,
        markers=True, marker=marker, markevery=1000,
        markerfacecolor=color, markeredgecolor="none",
        linestyle=line_style, color=color
      )
    else: # no markers
      sns.lineplot(
        x=x_vals, y=y_vals, ax=ax, label=tech,
        linestyle=line_style, color=color
      )
    if areafill: 
      ax.fill_between(x_vals, 0, y_vals, alpha=transparency, color=color)
  ax.set_xlabel(x_label, fontsize=16)
  ax.set_ylabel(y_label, fontsize=16)
  if chart_title:
    ax.set_title(chart_title, fontsize=16)
  if legend:
    ax.legend(fontsize=16)
  else:
    ax.get_legend().remove()
  if not border:
    for spine in ax.spines.values():
      spine.set_visible(False)
  ax.set_yscale(yscale)
  ax.set_ylim(bottom=y_min)
  os.makedirs(os.path.dirname(output_pdf_name), exist_ok=True)
  fig.savefig(output_pdf_name, format="pdf")
  plt.close(fig)

def summarize(values, scale):
  """
  Return (mean, median) of `values` after multiplying by `scale`.
  Empty → (0.0, 0.0).
  """
  if not values:
    return 0.0, 0.0
  avg = statistics.mean(values) * scale
  med = statistics.median(values) * scale
  return avg, med

def format_ratios(this_val, baselines, inverted, latex_fmt):
  """
  For each baseline name→value, call and return name, arrow, and ratio strings.
  """
  return {
    name: mystats.compute_ratio_and_arrow(base_val, this_val, inverted=inverted, latex_fmt=latex_fmt)
    for name, base_val in baselines.items()
  }

def count_wins_for_tech(unified_stats, techniques, top_k):
  """
  For the given top_k, count how many times each technique achieves
  the highest avg instruction count.
  """
  win_counts = {tech: 0 for tech in techniques}
  total_cases = 0
  for sol_data in unified_stats.values():
    if not all(tech in sol_data and top_k in sol_data[tech]
          for tech in techniques):
      continue
    best, _ = max(
      ((tech, sol_data[tech][top_k]["ic"]["avg"])
       for tech in techniques),
      key=lambda x: x[1]
    )
    win_counts[best] += 1
    total_cases += 1
  return win_counts, total_cases

def generate_efficiency_tables(global_stats_by_topk, unified_stats, techniques, output_tex_file):
  """
  Generate tables summarizing instruction counts and running times.
  """
  TABLES = [
    {
      "name": "Instruction Counts",
      "latex_caption": r"\# of instructions",
      "field": "global_instruction_avg_means",
      "scale": 1e-8,
    },
    {
      "name": "Running time (ms)",
      "latex_caption": "Running time (ms)",
      "field": "global_running_avg_means",
      "scale": 1000,
    }
  ]
  max_top_k = max(config.TOP_K_VALUES)
  baseline = config.BASELINE_TECHNIQUE
  reference = config.REFERENCE_TECHNIQUE

  wins = {}
  total_count = {}

  wins, total_count = count_wins_for_tech(unified_stats, techniques, max_top_k)

  # Compute stats relative to the baseline and reference techniques
  def _stats_for(tech_name: str):
    s = global_stats_by_topk.get(max_top_k, {}).get(tech_name, {})
    avg_ic, med_ic = summarize(s.get("global_instruction_avg_means", []), 1e-8)
    avg_rt, med_rt = summarize(s.get("global_running_avg_means", []), 1000)
    return {"avg_ic": avg_ic, "med_ic": med_ic, "avg_rt": avg_rt, "med_rt": med_rt}

  base_stats = _stats_for(baseline)
  reference_stats = _stats_for(reference)

  # Predefined collections to store the tables
  text_tables: List[Tuple[str, List[Tuple[str,str,str,float,str]]]] = []
  latex_lines: List[str] = []

  for tbl in TABLES:
    latex_lines += [
      r"\begin{table}[]",
      r"\begin{tabular}{lllrrr}",
      r"\toprule",
      rf"\multirow{{2}}{{*}}{{Technique}} & "
      rf"\multicolumn{{2}}{{c}}{{{tbl['latex_caption']}}} & "
      r"\multirow{2}{*}{Win rate} & "
      r"\multicolumn{2}{c}{Rel. to AlphaCode} \\",
      r"              & \multicolumn{1}{c}{Average} & \multicolumn{1}{c}{Median} "
      r"& & \multicolumn{1}{c}{Average} & \multicolumn{1}{c}{Median} \\",
      r"\midrule",
    ]

    # Predefined per-row plain‐text storage
    rows_txt: List[Tuple[str,str,str,float,str]] = []

    if tbl["field"] == "global_instruction_avg_means":
      avg_key, med_key = "avg_ic", "med_ic"
    else:
      avg_key, med_key = "avg_rt", "med_rt"

    for tech in techniques:

      s = global_stats_by_topk.get(max_top_k, {}).get(tech, {})
      vals = s.get(tbl["field"], [])
      avg, med = summarize(vals, tbl["scale"])

      wr = (wins.get(tech, 0) / total_count * 100) if total_count else 0.0
      
      r_base_avg_ltx = format_ratios(avg, { "BASE": base_stats[avg_key] }, True, latex_fmt=True )["BASE"]
      r_base_med_ltx = format_ratios(med, { "BASE": base_stats[med_key] }, True, latex_fmt=True )["BASE"]
      r_base_avg_txt = format_ratios(avg, { "BASE": base_stats[avg_key] }, True, latex_fmt=False)["BASE"]
      r_base_med_txt = format_ratios(med, { "BASE": base_stats[med_key] }, True, latex_fmt=False)["BASE"]

      r_ref_avg = (reference_stats[avg_key] / avg) if avg else 0.0
      r_ref_med = (reference_stats[med_key] / med) if med else 0.0

      r_ref_avg_ltx = f"{r_ref_avg:.2f}"
      r_ref_med_ltx = f"{r_ref_med:.2f}"
      r_ref_avg_txt = f"{r_ref_avg:.2f}"
      r_ref_med_txt = f"{r_ref_med:.2f}"

      if tech == baseline:
        avg_ltx = f"{avg:.2f}"
        med_ltx = f"{med:.2f}"
      else:
        avg_ltx = f"{avg:.2f} {r_base_avg_ltx}"
        med_ltx = f"{med:.2f} {r_base_med_ltx}"

      latex_lines.append(f"{tech} & {avg_ltx} & {med_ltx} & {wr:.1f}\\% & {r_ref_avg_ltx} & {r_ref_med_ltx} \\\\")

      if tech == baseline:
        avg_txt = f"{avg:.2f}"
        med_txt = f"{med:.2f}"
      else:
        avg_txt = f"{avg:.2f} {r_base_avg_txt}"
        med_txt = f"{med:.2f} {r_base_med_txt}"

      rows_txt.append((tech, avg_txt, med_txt, wr, r_ref_avg_txt, r_ref_med_txt))

    latex_lines += [r"\bottomrule", r"\end{tabular}", r"\end{table}", ""]
 
    title = f"Top-{max_top_k} {tbl['name']}"
    text_tables.append((title, rows_txt))

  with open(output_tex_file, "w") as f:
    f.write("\n".join(latex_lines))

  def _print_text_table(
    title: str,
    rows: List[Tuple[str,str,str,float,str]]
  ) -> None:
    print(f"---- {title} ----")
    headers = ["Technique", "Average", "Median", "Outperforming %", "Rel. to AlphaCode"]
    print("\t".join(headers))
    print("-" * (len(headers) * 12))
    for tech, a, m, wr, a_rel, m_rel in rows:
      print(f"{tech}\t{a}\t{m}\t{wr:.1f}%\t{a_rel}\t{m_rel}")
    print("\n")

  for title, rows in text_tables:
    _print_text_table(title, rows)

  logging.info("Wrote two tables (instructions & running time) for Top-%d", max_top_k)

def generate_slowdown_tables(slowdown):
  """
  Print the slwodown tables to stdout only.
  """
  ref = config.REFERENCE_TECHNIQUE

  print(f"---- Slowdown vs {ref}: Instruction Counts ----")
  headers = ["Technique", f"vs. {ref} slowdown"]
  print("\t".join(headers))
  print("-" * (len(headers) * 12))
  for tech, stats in slowdown.items():
    if tech == ref:
      continue
    avg = stats["instruction_count"]["average"]
    med = stats["instruction_count"]["median"]
    print(f"{tech}\t{avg:.2f}\t{med:.2f}")
  print()

  print(f"---- Slowdown vs {ref}: Running Times ----")
  print("\t".join(headers))
  print("-" * (len(headers) * 12))
  for tech, stats in slowdown.items():
    if tech == ref:
      continue
    avg = stats["running_time"]["average"]
    med = stats["running_time"]["median"]
    print(f"{tech}\t{avg:.2f}\t{med:.2f}")
  print()

def generate_histogram(data_dict, baseline_label, overlay_label, output_pdf_name,
            x_label="normalized slowdown ratio (%)", y_label="# of programs (log scale)",
            chart_title=None, legend=True,
            bins=40, bin_range=(-1000, 1000), transparency=1.0,
            border=True, color_scheme="rocket", custom_colors=None):
  """
  Create a histogram comparing two techniques based on computed ratio differences.
  """
  if custom_colors is None:
    sns.set_theme(style="white", palette=color_scheme)
    colors = sns.color_palette(color_scheme, n_colors=2)
  else:
    colors = custom_colors

  baseline_dict = data_dict.get(baseline_label, {})
  overlay_dict = data_dict.get(overlay_label, {})

  common_keys = set(baseline_dict.keys()).intersection(overlay_dict.keys())
  ratio_values = []
  for k in common_keys:
    x_val = baseline_dict.get(k, 0)
    y_val = overlay_dict.get(k, 0)
    ratio = mystats.compute_ratio_value(x_val, y_val)
    if ratio is not None:
      ratio_values.append(ratio)

  if not ratio_values:
    logging.warning("No valid ratio values for %s vs %s.", baseline_label, overlay_label)
    return

  pos_values = [r for r in ratio_values if r >= 0]
  neg_values = [r for r in ratio_values if r < 0]

  fig, ax = plt.subplots(figsize=(8,6))
  sns.histplot(pos_values, bins=bins, binrange=bin_range,
         color=colors[0], alpha=transparency, edgecolor=colors[0],
         ax=ax, label=f"{baseline_label}")
  sns.histplot(neg_values, bins=bins, binrange=bin_range,
         color=colors[1], alpha=transparency, edgecolor=colors[1],
         ax=ax, label=f"{overlay_label}")
  ax.axvline(x=-0.1, color='black', linestyle='--', linewidth=1)
  ax.set_xlabel(x_label, fontsize=16)
  ax.set_ylabel(y_label, fontsize=16)
  ax.set_xticks([-1000, -500, -100, 0, 100, 500, 1000])
  ax.set_xticklabels(["≤-1000", "-500", "-100", "0", "100", "500", "≥1000"])
  ax.set_yscale("log")
  if not border:
    for spine in ax.spines.values():
      spine.set_visible(False)
  if chart_title:
    ax.set_title(chart_title, fontsize=18)
  if legend:
    ax.legend(fontsize=14)
  os.makedirs(os.path.dirname(output_pdf_name), exist_ok=True)
  fig.savefig(output_pdf_name, format="pdf")
  plt.close(fig)


def get_bin_index(ratio, bin_size=200, max_bins=6):
  """
  Given a ratio, return its bin index based on bin_size.
  """
  idx = int(abs(ratio) // bin_size)
  return min(idx, max_bins - 1)

def generate_histogram_table(unified_stats, output_tex_file):
  """
  Generate a LaTeX table summarizing slowdown ratio bins.
  """
  # --- Build and print LATEX version ---
  intervals = ["[0, 200)", "[200, 400)", "[400, 600)", "[600, 800)", "[800, 1,000)", "[1000,)"]
  table_rows = []
  for top_k in sorted(config.TOP_K_VALUES, reverse=True):
    for overlay_label in list(config.TECHNIQUES.keys())[1:]:
      if overlay_label == config.REFERENCE_TECHNIQUE:
        continue
      pos_bins = [0] * 6
      neg_bins = [0] * 6
      for sol_key, sol_data in unified_stats.items():
        if (config.BASELINE_TECHNIQUE in sol_data and overlay_label in sol_data and 
          top_k in sol_data[config.BASELINE_TECHNIQUE] and top_k in sol_data[overlay_label]):
          x_val = sol_data[config.BASELINE_TECHNIQUE][top_k]["ic"]["avg"]
          y_val = sol_data[overlay_label][top_k]["ic"]["avg"]
          ratio = mystats.compute_ratio_value(x_val, y_val)
          if ratio is not None:
            idx = get_bin_index(ratio)
            if ratio >= 0:
              pos_bins[idx] += 1
            else:
              neg_bins[idx] += 1
      table_rows.append((top_k, overlay_label, pos_bins, neg_bins))
  lines = []
  lines.append(r"\begin{tabular}{lrrrrrr}")
  lines.append(r"\toprule")
  header = "Slowdown ratio interval & " + " & ".join(intervals) + r" \\"
  lines.append(header)
  lines.append(r"\midrule")
  for top_k, overlay_label, pos_bins, neg_bins in table_rows:
    lines.append(r"\rowcolor[gray]{0.95}\multicolumn{7}{c}{\textbf{Top " + f"{top_k} slowest inputs" + r"}}\\")
    row_label = f"{config.BASELINE_TECHNIQUE} v.s. {overlay_label}"
    row_cells = [row_label]
    for i in range(6):
      cell = f"{pos_bins[i]:,} ($\\textcolor{{red}}{{\\downarrow}}{neg_bins[i]:,}$)"
      row_cells.append(cell)
    lines.append(" & ".join(row_cells) + r" \\")
  lines.append(r"\bottomrule")
  lines.append(r"\end{tabular}")
  latex_code = "\n".join(lines)
  with open(output_tex_file, "w") as f:
    f.write(latex_code)

  # --- Build and print plain text version ---
  txt_lines = []
  txt_lines.append("---- TABLE 2: Histogram Summary ----")
  header = f"{'Top-K':<10} {'Overlay':<30} {'[0,200)':<15} {'[200,400)':<15} {'[400,600)':<15} {'[600,800)':<15} {'[800,1000)':<15} {'[1000,)':<15}"
  txt_lines.append(header)
  txt_lines.append("-" * len(header))
  for top_k in sorted(config.TOP_K_VALUES, reverse=True):
    for overlay_label in list(config.TECHNIQUES.keys())[1:]:
      if overlay_label == config.REFERENCE_TECHNIQUE:
        continue
      pos_bins = [0] * 6
      neg_bins = [0] * 6
      for sol_key, sol_data in unified_stats.items():
        if (config.BASELINE_TECHNIQUE in sol_data and overlay_label in sol_data and 
          top_k in sol_data[config.BASELINE_TECHNIQUE] and top_k in sol_data[overlay_label]):
          x_val = sol_data[config.BASELINE_TECHNIQUE][top_k]["ic"]["avg"]
          y_val = sol_data[overlay_label][top_k]["ic"]["avg"]
          ratio = mystats.compute_ratio_value(x_val, y_val)
          if ratio is not None:
            idx = int(abs(ratio) // 200)
            idx = min(idx, 5)
            if ratio >= 0:
              pos_bins[idx] += 1
            else:
              neg_bins[idx] += 1
      row_label = f"Top {top_k}".ljust(10) + f"{config.BASELINE_TECHNIQUE} vs {overlay_label}".ljust(30)
      row = f"{row_label:<40}"
      for i in range(6):
        arrow = "↓" if pos_bins[i] >= neg_bins[i] else "↑"
        cell = f"{pos_bins[i]:,}".rjust(7) + f" ({arrow}{neg_bins[i]:,})".rjust(8)
        row += f"{cell:>15}"
      txt_lines.append(row)
  plain_text_table = "\n".join(txt_lines)
  logging.info("\n%s", plain_text_table)