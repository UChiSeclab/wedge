from collections import defaultdict
import argparse
import config
import logging
import os
import pandas as pd
import parser
import stats
import viz

logging.basicConfig(level=logging.INFO)

def process_techniques(json_dir, language):
  """
  Process JSON files for each technique and build in-memory data structures.
  """
  technique_data = {}
  for tech, tech_folder in config.TECHNIQUES.items():
    tech_dir = os.path.join(json_dir, tech_folder)
    logging.info("Processing technique %s from directory %s", tech, tech_dir)
    rt_data, ic_data = parser.build_data_structure_for_technique(tech_dir, language)
    technique_data[tech] = {"rt": rt_data, "ic": ic_data}
  return technique_data

def aggregate_and_write_csv(technique_data, output_csv_dir):
  """
  Aggregate stats for each technique for each top-K value and write per-technique CSV files.
  """
  global_stats_by_topk = {}
  for top_k in config.TOP_K_VALUES:
    global_stats_by_topk[top_k] = {}
    for tech, tech_folder in config.TECHNIQUES.items():
      data_rt = technique_data[tech]["rt"]
      data_ic = technique_data[tech]["ic"]
      agg_results = stats.compute_aggregate_stats_for_structure(data_rt, data_ic, top_k)
      global_stats_by_topk[top_k][tech] = agg_results
      csv_filename = f"{tech}-top{top_k}inputs.csv"
      csv_output_path = os.path.join(output_csv_dir, csv_filename)
      parser.write_csv(agg_results["aggregated_stats"], csv_output_path)
  return global_stats_by_topk

def build_unified_stats(global_stats_by_topk):
  """
  Build a unified dictionary keyed by 'problemID-solutionID' from all techniques.
  """
  unified_stats = defaultdict(lambda: defaultdict(dict))
  for top_k in config.TOP_K_VALUES:
    for tech, tech_folder in config.TECHNIQUES.items():
      agg_results = global_stats_by_topk[top_k][tech]
      for solution_key, stats in agg_results["aggregated_stats"].items():
        unified_stats[solution_key][tech][top_k] = {
          "rt": {
            "avg": stats["running_time"]["avg_of_top_k_means"],
            "median": stats["running_time"]["median_of_top_k_means"],
            "cv": stats["running_time"]["avg_of_top_k_cvs"]
          },
          "ic": {
            "avg": stats["instruction_count"]["avg_of_top_k_means"],
            "median": stats["instruction_count"]["median_of_top_k_means"],
            "cv": stats["instruction_count"]["avg_of_top_k_cvs"]
          }
        }
  logging.info("Unified stats structure built with %d solutions", len(unified_stats))
  return unified_stats

def generate_overlay_plots(output_plot_dir, output_csv_dir):
  """
  Generate overlay area plots by reading the CSV files for each top-K value.
  """
  for top_k in config.TOP_K_VALUES:
    data_dict = {}
    for tech, tech_folder in config.TECHNIQUES.items():
      csv_file = os.path.join(output_csv_dir, f"{tech}-top{top_k}inputs.csv")
      try:
        df = pd.read_csv(csv_file)
        data_dict[tech] = dict(zip(df["combined_key"], df["ic_avg_of_top_k_means"]))
      except Exception as e:
        logging.error("Error reading CSV file %s: %s", csv_file, e)
    if len(data_dict) == len(config.TECHNIQUES):
      plot_filename = os.path.join(output_plot_dir, f"areachart_{top_k}_overlay.pdf")
      viz.generate_overlay_plot(
        data_dict,
        output_pdf_name=plot_filename,
        x_label="Program ID",
        y_label="Instruction Count (normalized)",
        #chart_title=f"Overlay Area Chart: Top-{top_k} Inputs",
        legend=True,
        transparency=1.0,
        line_style="solid",
        border=True,
        areafill=False,
        markers=["o", "s", "D", "^"],
        color_scheme="deep",
        #custom_colors=config.CUSTOM_4_COLOR_SCHEME
      )
      logging.info("Generated overlay area plot: %s", plot_filename)
    else:
      logging.error("Incomplete data for top-%d inputs. Skipping overlay area plot.", top_k)

def generate_histograms(unified_stats, output_plot_dir):
  """
  For each top-K value and each overlay technique, generate histograms comparing techniques.
  """
  baseline_label = baseline_label = list(config.TECHNIQUES.keys())[0]
  for top_k in config.TOP_K_VALUES:
    for overlay_label in list(config.TECHNIQUES.keys())[1:]:
      data_dict_hist = {}
      for tech in [baseline_label, overlay_label]:
        tech_dict = {
          sol_key: sol_data[tech][top_k]["ic"]["avg"]
          for sol_key, sol_data in unified_stats.items()
          if tech in sol_data and top_k in sol_data[tech]
        }
        data_dict_hist[tech] = tech_dict
      if baseline_label in data_dict_hist and overlay_label in data_dict_hist:
        hist_pdf = os.path.join(output_plot_dir, f"histogram_{baseline_label}_vs_{overlay_label}_top{top_k}inputs.pdf")
        viz.generate_histogram(
          data_dict_hist,
          baseline_label=baseline_label,
          overlay_label=overlay_label,
          output_pdf_name=hist_pdf,
          x_label="Normalized Ratio (%)",
          y_label="# of programs (log scale)",
          #chart_title=f"Histogram of Ratio Differences: Top-{top_k} Inputs",
          color_scheme="deep",
          #custom_colors=config.CUSTOM_2_COLOR_SCHEME 
        )
        logging.info("Generated histogram: %s", hist_pdf)
      else:
        logging.error("Incomplete histogram data for top-%d inputs for %s vs %s", top_k, baseline_label, overlay_label)

def generate_pie_charts(unified_stats, output_plot_dir):
  """
  Generate pie charts summarizing winning techniques.
  """
  for top_k in config.TOP_K_VALUES:
    data_dict_pie = {}
    for tech, tech_folder in config.TECHNIQUES.items():
      tech_dict = {
        sol_key: sol_data[tech][top_k]["ic"]["avg"]
        for sol_key, sol_data in unified_stats.items()
        if tech in sol_data and top_k in sol_data[tech]
      }
      data_dict_pie[tech] = tech_dict
    pie_pdf = os.path.join(output_plot_dir, f"piechart_ranked_top{top_k}inputs.pdf")
    viz.generate_pie_chart(
      data_dict_pie,
      output_pdf_name=pie_pdf,
      #chart_title=f"Pie Chart of Winners: Top-{top_k} Inputs",
      legend=False,
      color_scheme="deep",
      #custom_colors=config.CUSTOM_4_COLOR_SCHEME
    )
    logging.info("Generated pie chart: %s", pie_pdf)

def main():
  parser = argparse.ArgumentParser(
    description="Process JSON files into data structures, compute performance stats, and generate plots/tables."
  )
  parser.add_argument("--json-dir", required=True,
            help="Directory with JSON files organized by technique subdirectories.")
  parser.add_argument("--output-csv-dir", required=True,
            help="Directory to write CSV files.")
  parser.add_argument("--plot-output-dir", required=True,
            help="Directory to save PDF plots and LaTeX tables.")
  parser.add_argument("--language", default="cpp",
            help="Programming language filter (default: cpp).")
  args = parser.parse_args()

  # Pre-create output directories
  os.makedirs(args.output_csv_dir, exist_ok=True)
  os.makedirs(args.plot_output_dir, exist_ok=True)

  # Data ingestion
  technique_data = process_techniques(args.json_dir, args.language)
  # Aggregate statistics and write CSV files
  global_stats_by_topk = aggregate_and_write_csv(technique_data, args.output_csv_dir)
  # Build unified stats structure
  unified_stats = build_unified_stats(global_stats_by_topk)

  # Generate plots and LaTeX tables
  generate_overlay_plots(args.plot_output_dir, args.output_csv_dir)
  generate_histograms(unified_stats, args.plot_output_dir)
  generate_pie_charts(unified_stats, args.plot_output_dir)

  # Generate LaTeX table for area chart results
  overlay_table_file = os.path.join(args.plot_output_dir, "table_overlay_chart.tex")
  viz.generate_overlay_table(global_stats_by_topk, overlay_table_file)
  logging.info("Generated area chart LaTeX table: %s", overlay_table_file)
  
  # Generate LaTeX table for histogram data
  histogram_table_file = os.path.join(args.plot_output_dir, "table_histogram.tex")
  viz.generate_histogram_table(unified_stats, histogram_table_file)
  logging.info("Generated histogram LaTeX table: %s", histogram_table_file)
  
  # Generate LaTeX table for pie chart data
  pie_table_file = os.path.join(args.plot_output_dir, "table_pie_chart.tex")
  viz.generate_pie_chart_table(unified_stats, pie_table_file)
  logging.info("Generated pie chart LaTeX table: %s", pie_table_file)

if __name__ == "__main__":
  main()