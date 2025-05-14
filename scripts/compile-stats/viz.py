import config
import logging
import matplotlib.pyplot as plt
import numpy as np
import os
import seaborn as sns
import stats as sts

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
    normalized_data = sts.compute_normalized_sorted_list_from_dict(data_dict[tech])
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

def generate_overlay_table(global_stats_by_topk, output_tex_file):
  """
  Generate a LaTeX table summarizing instruction counts and running times.
  """
  # --- Build and print LATEX version ---
  lines = []
  lines.append(r"\begin{tabular}{lllll}")
  lines.append(r"\toprule")
  lines.append(r"\# of slowest inputs & Top-10 & Top-5 & Top-3 & Top-1 \\ \midrule")
  lines.append(r"\rowcolor[gray]{0.95}\multicolumn{5}{c}{Number of instructions ($\times 10^8$)} \\")
  for tech, tech_folder in config.TECHNIQUES.items():
    row = [tech]
    for top_k in sorted(config.TOP_K_VALUES, reverse=True):
      stats = global_stats_by_topk.get(top_k, {}).get(tech, {})
      ic_avg = sts.safe_mean(stats.get("global_instruction_avg_means", [])) / 1e8
      ic_var = (100 * sts.safe_mean(stats.get("global_instruction_avg_cvs", [])))
      if tech != config.BASELINE_TECHNIQUE:
        baseline_stats = global_stats_by_topk.get(top_k, {}).get(config.BASELINE_TECHNIQUE, {})
        baseline_ic_avg = sts.safe_mean(baseline_stats.get("global_instruction_avg_means", [])) / 1e8
        ratio_str = sts.compute_ratio_and_arrow(baseline_ic_avg, ic_avg, latex_fmt=True)
      else:
        ratio_str = ""
      if tech == config.BASELINE_TECHNIQUE:
        cell = r"\textbf{" + f"{ic_avg:.2f}$\pm${ic_var:.2f}\%" + "}"
      else:
        cell = f"{ic_avg:.2f}$\pm${ic_var:.2f}\% ({ratio_str})"
      row.append(cell)
    lines.append(" & ".join(row) + r" \\")
  lines.append(r"\midrule")
  lines.append(r"\rowcolor[gray]{0.95}\multicolumn{5}{c}{Running time (ms)} \\")
  for tech, tech_folder in config.TECHNIQUES.items():
    row = [tech]
    for top_k in sorted(config.TOP_K_VALUES, reverse=True):
      stats = global_stats_by_topk.get(top_k, {}).get(tech, {})
      rt_avg = int(1000 * sts.safe_mean(stats.get("global_running_avg_means", [])))
      rt_var = int(100 * sts.safe_mean(stats.get("global_running_avg_cvs", [])))
      if tech != config.BASELINE_TECHNIQUE:
        baseline_stats = global_stats_by_topk.get(top_k, {}).get(config.BASELINE_TECHNIQUE, {})
        baseline_rt_avg = int(1000 * sts.safe_mean(baseline_stats.get("global_running_avg_means", [])))
        ratio_str = sts.compute_ratio_and_arrow(baseline_rt_avg, rt_avg, latex_fmt=True)
      else:
        ratio_str = ""
      if tech == config.BASELINE_TECHNIQUE:
        cell = r"\textbf{" + f"{rt_avg}$\pm${rt_var:.2f}\%" + "}"
      else:
        cell = f"{rt_avg}$\pm${rt_var:.2f}\% ({ratio_str})"
      row.append(cell)
    lines.append(" & ".join(row) + r" \\")
  lines.append(r"\bottomrule")
  lines.append(r"\end{tabular}")
  latex_code = "\n".join(lines)
  with open(output_tex_file, "w") as f:
    f.write(latex_code)

  # --- Build and print plain text version ---
  txt_lines = []
  txt_lines.append("---- TABLE 1: Area Chart Summary ----")
  header = f"{'# of slowest inputs':<25} {'Top-10':<20} {'Top-5':<20} {'Top-3':<20} {'Top-1':<20}"
  txt_lines.append(header)
  txt_lines.append("-" * len(header))
  txt_lines.append("Number of instructions (x 1e8):")
  for tech, tech_folder in config.TECHNIQUES.items():
    row = [f"{tech:<25}"]
    for top_k in sorted(config.TOP_K_VALUES, reverse=True):
      stats = global_stats_by_topk.get(top_k, {}).get(tech, {})
      ic_avg = sts.safe_mean(stats.get("global_instruction_avg_means", [])) / 1e8
      ic_var = 100 * sts.safe_mean(stats.get("global_instruction_avg_cvs", []))
      if tech != config.BASELINE_TECHNIQUE:
        baseline_stats = global_stats_by_topk.get(top_k, {}).get(config.BASELINE_TECHNIQUE, {})
        baseline_ic_avg = sts.safe_mean(baseline_stats.get("global_instruction_avg_means", [])) / 1e8
        ratio_str = sts.compute_ratio_and_arrow(baseline_ic_avg, ic_avg, latex_fmt=False)
      else:
        ratio_str = ""
      cell = f"{ic_avg:.2f}±{ic_var:.2f} {ratio_str}"
      row.append(f"{cell:<20}")
    txt_lines.append(" ".join(row))
  txt_lines.append("-" * len(header))
  txt_lines.append("Running time (ms):")
  for tech, tech_folder in config.TECHNIQUES.items():
    row = [f"{tech:<25}"]
    for top_k in sorted(config.TOP_K_VALUES, reverse=True):
      stats = global_stats_by_topk.get(top_k, {}).get(tech, {})
      rt_avg = int(1000 * sts.safe_mean(stats.get("global_running_avg_means", [])))
      rt_var = sts.safe_mean(stats.get("global_running_avg_cvs", []))
      if tech != config.BASELINE_TECHNIQUE:
        baseline_stats = global_stats_by_topk.get(top_k, {}).get(config.BASELINE_TECHNIQUE, {})
        baseline_rt_avg = int(1000 * sts.safe_mean(baseline_stats.get("global_running_avg_means", [])))
        ratio_str = sts.compute_ratio_and_arrow(baseline_rt_avg, rt_avg, latex_fmt=False)
      else:
        ratio_str = ""
      cell = f"{rt_avg}$±${rt_var:.1f} {ratio_str}"
      row.append(f"{cell:<20}")
    txt_lines.append(" ".join(row))
  plain_text_table = "\n".join(txt_lines)
  logging.info("\n%s", plain_text_table)

def generate_histogram(data_dict, baseline_label, overlay_label, output_pdf_name,
            x_label="Normalized Ratio (%)", y_label="# of programs (log scale)",
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
    ratio = sts.compute_ratio_value(x_val, y_val)
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
      pos_bins = [0] * 6
      neg_bins = [0] * 6
      for sol_key, sol_data in unified_stats.items():
        if (config.BASELINE_TECHNIQUE in sol_data and overlay_label in sol_data and 
          top_k in sol_data[config.BASELINE_TECHNIQUE] and top_k in sol_data[overlay_label]):
          x_val = sol_data[config.BASELINE_TECHNIQUE][top_k]["ic"]["avg"]
          y_val = sol_data[overlay_label][top_k]["ic"]["avg"]
          ratio = sts.compute_ratio_value(x_val, y_val)
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
      pos_bins = [0] * 6
      neg_bins = [0] * 6
      for sol_key, sol_data in unified_stats.items():
        if (config.BASELINE_TECHNIQUE in sol_data and overlay_label in sol_data and 
          top_k in sol_data[config.BASELINE_TECHNIQUE] and top_k in sol_data[overlay_label]):
          x_val = sol_data[config.BASELINE_TECHNIQUE][top_k]["ic"]["avg"]
          y_val = sol_data[overlay_label][top_k]["ic"]["avg"]
          ratio = sts.compute_ratio_value(x_val, y_val)
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

def generate_pie_chart(data_dict, output_pdf_name,
            chart_title=None, legend=True,
            color_scheme="rocket", custom_colors=None):
  """
  Create a pie chart summarizing which technique wins most often.
  """
  if custom_colors is None:
    sns.set_theme(style="white", palette=color_scheme)
    colors = sns.color_palette(color_scheme, n_colors=len(config.TECHNIQUES))
  else:
    colors = custom_colors

  all_keys = set()
  for tech, tech_folder in config.TECHNIQUES.items():
    all_keys.update(data_dict.get(tech, {}).keys())
  
  win_counts = {tech: 0 for tech, tech_folder in config.TECHNIQUES.items()}
  for k in all_keys:
    best_label = None
    best_value = -1
    for tech, tech_folder in config.TECHNIQUES.items():
      value = data_dict.get(tech, {}).get(k, 0)
      if value > best_value:
        best_value = value
        best_label = tech
    if best_label is not None:
      win_counts[best_label] += 1
  #total = sum(win_counts.values())
  #pie_labels = []
  counts = []
  for tech, tech_folder in config.TECHNIQUES.items():
    cnt = win_counts[tech]
    counts.append(cnt)
  #  percentage = 100.0 * cnt / total if total > 0 else 0.0
  #  pie_labels.append(f"{tech}: {cnt:,} ({percentage:.1f}\\%)")
  fig, ax = plt.subplots()
  ax.pie(counts, labels=None, autopct='%1.1f%%', startangle=90,
      colors=colors, textprops={"fontsize":16})
  if chart_title:
    ax.set_title(chart_title, fontsize=18)
  if legend:
    ax.legend(loc="best", fontsize=14)
  os.makedirs(os.path.dirname(output_pdf_name), exist_ok=True)
  fig.savefig(output_pdf_name, format="pdf")
  plt.close(fig)

def generate_pie_chart_table(unified_stats, output_tex_file):
  """
  Generate a multi-part LaTeX table summarizing the win counts per technique.
  """
  # --- Build and print LATEX version ---
  def count_wins(unified_stats, top_k):
    win_counts = {tech: 0 for tech, tech_folder in config.TECHNIQUES.items()}
    total = 0
    for sol_key, sol_data in unified_stats.items():
      if not all(tech in sol_data and top_k in sol_data[tech] for tech, tech_folder in config.TECHNIQUES.items()):
        continue
      best = max(((tech, sol_data[tech][top_k]["ic"]["avg"]) for tech, tech_folder in config.TECHNIQUES.items()),
            key=lambda x: x[1], default=(None, 0))
      if best[0]:
        win_counts[best[0]] += 1
        total += 1
    return win_counts, total

  lines = []
  lines.append(r"\begin{tabular}{lcc}")
  lines.append(r"\toprule")
  lines.append("Technique & Is Best & Percentage \\\\")
  lines.append(r"\midrule")
  for top_k in sorted(config.TOP_K_VALUES, reverse=True):
    lines.append(r"\rowcolor[gray]{0.95}\multicolumn{3}{c}{\textbf{Top " + f"{top_k} slowest inputs" + r"}}\\")
    win_counts, total = count_wins(unified_stats, top_k)
    for tech, tech_folder in config.TECHNIQUES.items():
      percentage = 100.0 * win_counts[tech] / total if total > 0 else 0.0
      lines.append(f"{tech} & {win_counts[tech]:,} & {percentage:.1f}\\% \\\\")
  lines.append(r"\bottomrule")
  lines.append(r"\end{tabular}")
  latex_code = "\n".join(lines)
  with open(output_tex_file, "w") as f:
    f.write(latex_code)

  # --- Build and print plain text version ---
  txt_lines = []
  txt_lines.append("---- TABLE 3: Pie Chart Summary ----")
  header = f"{'Top-K':<10} {'Technique':<20} {'Is Best':>10} {'Percentage':>12}"
  txt_lines.append(header)
  txt_lines.append("-" * len(header))
  for top_k in sorted(config.TOP_K_VALUES, reverse=True):
    win_counts = {tech: 0 for tech, tech_folder in config.TECHNIQUES.items()}
    total_count = 0
    for sol_key, sol_data in unified_stats.items():
      if not all(tech in sol_data and top_k in sol_data[tech] for tech, tech_folder in config.TECHNIQUES.items()):
        continue
      best = max(((tech, sol_data[tech][top_k]["ic"]["avg"]) for tech, tech_folder in config.TECHNIQUES.items()),
            key=lambda x: x[1], default=(None, 0))
      if best[0]:
        win_counts[best[0]] += 1
        total_count += 1
    for tech, tech_folder in config.TECHNIQUES.items():
      percentage = 100.0 * win_counts[tech] / total_count if total_count > 0 else 0.0
      row = f"Top {top_k}".ljust(10) + f" {tech:<20} {win_counts[tech]:>10} {percentage:>11.1f}%"
      txt_lines.append(row)
  plain_text_table = "\n".join(txt_lines)
  logging.info("\n%s", plain_text_table)