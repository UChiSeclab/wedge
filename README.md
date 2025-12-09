# Synthesizing Performance Constraints for Evaluating and Improving Code Efficiency

<p align="left">
  <a href="https://arxiv.org/" target="_blank">
    <a href="https://arxiv.org/abs/2505.23471"><img src="https://img.shields.io/badge/arXiv-2505.23471-b31b1b.svg?style=for-the-badge">
  </a>
  
</p>

NeurIPS 2025 Artifact

This repository accompanies our NeurIPS 2025 paper, “Synthesizing Performance Constraints for Evaluating and Improving Code Efficiency.”
If you want to re-run the full pipeline (prompting, constraint synthesis, fuzzing, execution), use the steps below.

Link of our benchmark, PerfForge: https://github.com/UChiSeclab/perfforge.

### Repository layout
- `code-contest-exp/`: End-to-end experimental pipeline and Python dependencies.
- `feedback_collection/`: Coverage/feedback collection for `cpp/`, `python/`, and `java/`.
- `scripts/`: Orchestration utilities (collection, generation, post-processing).
- `scripts/compile-stats/`: Stats aggregation, LaTeX tables, and plots from JSON outputs.
- `use-cases/`: Example problems with solution variants.
- `llm-prompts/`: Prompts for constraint, validator, and mutator generation.
- `README-for-supplemental-materials.md`: Supplemental notes for artifact consumers.

---

## 1) Quick start

### 1.1 System requirements
- Ubuntu 20.04+ (tested)
- Python 3.9+ (for the main pipeline), plus Python 2.7/3.8 for legacy solution runners
- GCC/GCov (for C++ coverage)
- Linux `perf` (5.15+ recommended)
- Java 11 (for Java solutions’ coverage)
- AFL++ (for fuzzing-based test generation)

### 1.2 Create environments
We use three conda envs: `perf` (main), `py27` (Python2 solutions), `py38` (Python3 solutions).

```bash
# Miniconda (if needed)
wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh
bash Miniconda3-latest-Linux-x86_64.sh

# Environments
conda create -n py27 python==2.7.13
conda create -n py38 python==3.8
conda create -n perf python==3.9

conda activate perf
cd code-contest-exp
pip install -r requirements.txt
```

### 1.3 Java 11
```bash
wget https://download.java.net/java/ga/jdk11/openjdk-11_linux-x64_bin.tar.gz
tar -zxvf openjdk-11_linux-x64_bin.tar.gz
```

### 1.4 Linux perf
```bash
sudo apt-get update
sudo apt-get install -y linux-tools-common linux-tools-generic linux-tools-`uname -r`
sudo sysctl kernel.perf_event_paranoid=-1
```

### 1.5 Coverage tools
```bash
# C++
which gcov || sudo apt-get install -y gcovr gcc g++
pip install gcovr

# Python
pip install -r ../feedback_collection/python/requirements.txt
```

### 1.6 AFL++
```bash
mkdir -p aflpp && cd aflpp
git clone https://github.com/AFLplusplus/AFLplusplus.git
cd AFLplusplus
git checkout v4.30c
export AFLPP_DIR=$(pwd)
# Build per AFL++ docs:
# https://github.com/AFLplusplus/AFLplusplus/blob/stable/docs/INSTALL.md
```

### 1.7 PYTHONPATH
```bash
cd code-contest-exp
export PYTHONPATH=$(pwd)/scripts:../feedback_collection
```

---

## 2) Reproducing core experiments

Below is the minimal end-to-end to (i) prepare problems and baselines, (ii) synthesize constraints, (iii) generate tests via prompting and fuzzing, (iv) sanitize, and (v) execute and aggregate results.

Tip: Create logs upfront to track progress.
```bash
mkdir -p log/{constraint_gen,corpus_gen,cov_collect,dump,feedback_collect,gen,mutator_gen,validator_gen,run}
```

### 2.1 Add problems
1) Choose problem IDs (see `use-cases/` for examples or configure your own set).
2) Update the first element of `code-contest-exp/problem_list.json` with your list (used when `use_specified_problem=True` in `get_cf_problems`).
3) Materialize problem assets:
```bash
python scripts/add_problem.py
```

### 2.2 Baseline: AlphaCode tests
Generates result JSONs under `config["result_root_dir"]/alphacode` used by later steps.
```bash
python code-contest-exp/scripts/run.py --experiment_name alphacode > log/run/alphacode.log
```

### 2.3 Generate input validators (LLM)
Stores validators under `config["problem_root_dir"]/{problem_id}/validator_gen/{validator_mode}`. The run may retry until a “good” validator is found (indicated by `VAL_GT_INPUT_PASS`).
```bash
python code-contest-exp/scripts/input_validator_gen.py --validator_mode self_reflect_feedback > log/validator_gen/self_reflect_feedback.log
```

### 2.4 Collect feedback (coverage/hit counts)
We support C++/Python/Java; C++ is the most robust in our artifact.
```bash
python code-contest-exp/scripts/feedback_collect.py --experiment_name feedback_diff_solution --solution_language cpp > log/feedback_collect/feedback_diff_solution.log 2>&1
python code-contest-exp/scripts/feedback_collect.py --experiment_name feedback_diff_input    --solution_language cpp > log/feedback_collect/feedback_diff_input.log 2>&1
python code-contest-exp/scripts/feedback_collect.py --experiment_name feedback_multi_solution_diff_input --solution_language cpp --top_k 5 > log/feedback_collect/feedback_multi_solution_diff_input.log 2>&1

python code-contest-exp/scripts/collect_cov_report.py --experiment_name alphacode
```

### 2.5 Constraint extraction
Mine contrasting input pairs (ranked by similarity and performance ratio):
```bash
python code-contest-exp/scripts/cgig/mine_input_pairs.py
```
Synthesize performance constraints and instrument solutions:
```bash
python code-contest-exp/scripts/cgig/constraint_gen.py > log/constraint_gen/constraint_gen.log
```
Outputs include: GPT response files, instrumented programs, and at most 5 constraints per problem under `config["constraints_dir"]`.

### 2.6 Mutator generation (LLM + AFL++)
Generates a custom AFL++ mutator and validates it with a short dry-run (success yields `MUTATOR_CHECK_PASS`).
```bash
python code-contest-exp/scripts/cgig/mutator_gen.py --mutator_type mutator_with_constraint_multi --problem_with_extracted_constraint_only True --mutator_mode self_reflect_feedback > log/mutator_gen/mutator_with_constraint.log 2>&1
```

### 2.7 Test generation
Prompting strategies (examples):
- Plain problem prompting (no constraints):
```bash
python code-contest-exp/scripts/gen_tests.py \
  --experiment_name plain_problem \
  --prompt_language java \
  --run_tests True \
  --validator_mode self_reflect_feedback \
  --max_retry 10
```
- With feedback (multi-solution diffs):
```bash
python code-contest-exp/scripts/gen_tests.py \
  --experiment_name with_feedback_multi_solution_diff_input \
  --prompt_language java \
  --run_tests True \
  --validator_mode self_reflect_feedback \
  --max_retry 10
```

Fuzzing strategies (AFL++):
```bash
python code-contest-exp/scripts/cgig/corpus_gen.py --mutator_type mutator_with_constraint_multi --problem_with_extracted_constraint_only True > log/corpus_gen/mutator_with_constraint_multi.log 2>&1
```

### 2.8 Post-processing
Sanitize AlphaCode tests with generated validators:
```bash
python code-contest-exp/scripts/sanitize_alphacode_result.py
```
This creates `config["problem_root_dir"]/{problem_id}/alphacode_sanitized` (evaluated as a separate strategy).

Validate and dump AFL++ corpora into per-problem strategy directories:
```bash
python code-contest-exp/scripts/cgig/dump_corpus_inputs.py --mutator_type mutator_with_constraint_multi --problem_with_extracted_constraint_only True > log/dump/corpus_mutator_with_constraint_multi.log
```

### 2.9 Execute strategies
Prompting strategies:
```bash
python code-contest-exp/scripts/run.py --experiment_name ${strategy} >> log/run/${strategy}.log
```
Fuzzing strategies:
```bash
python code-contest-exp/scripts/run.py --experiment_name ${strategy} --problem_with_extracted_constraint_only True >> log/run/${strategy}.log
```

---

## 3) Aggregating results, tables, and plots

You can compile cross-technique statistics and figures from the experiment outputs using `scripts/compile-stats`:

Example:
```bash
python3 scripts/compile-stats/main.py \
  --json-dir /abs/path/to/results/json/root \
  --output-csv-dir ./scripts/compile-stats/csv_out \
  --plot-output-dir ./scripts/compile-stats/plots \
  --language cpp
```
This will populate CSVs and plots under `scripts/compile-stats/{csv_out,plots}`. Configure techniques, baselines, and styles in `scripts/compile-stats/config.py`.

To customize the set of problems/techniques for compiled figures from pipeline outputs, edit `scripts/compile-stats/config.py`.

---

## 4) Practical notes

- Long-running steps: Coverage collection, constraint synthesis, and AFL++ fuzzing can be time-consuming. We recommend starting with a small problem subset to validate your setup end-to-end, then scaling out.
- LLM access: The validator/constraint/mutator steps use LLM prompting. Configure your provider credentials via environment variables as appropriate for your setup. See scripts under `scripts/cgig/` and `llm-prompts/`.
- Monitoring: Check log files and output directories continuously (e.g., `results/${strategy}` growth during runs).
- Languages: While C++ has the most mature support in this artifact, we provide Python/Java paths for completeness.

---

## 5) Troubleshooting

- `perf` permissions: Make sure `kernel.perf_event_paranoid=-1`. A reboot may be necessary on some systems after changing sysctl.
- AFL++ build: Follow the upstream install docs for your distro and ensure `AFLPP_DIR` is exported in the shell running our scripts.
- AFL++ hack: we increased the max file size that AFL++ can mutate from 1MB to 10MB by modifying `#define MAX_FILE (1 * 1024 * 1024L)` in `include/config.h` file.
- `gcov`/coverage mismatch: Ensure your compiler and `gcov` versions are compatible; recompile solutions if needed.
- Java coverage: Ensure Java 11 runtime and the provided coverage jars are accessible (see `feedback_collection/java/lib/`).

---

## 6) Citation

```bibtex
@inproceedings{
yang2025synthesizing,
title={Synthesizing Performance Constraints for Evaluating and Improving Code Efficiency},
author={Jun Yang and Cheng-Chi Wang and Bogdan Alexandru Stoica and Kexin Pei},
booktitle={The Thirty-ninth Annual Conference on Neural Information Processing Systems},
year={2025},
url={https://openreview.net/forum?id=Qh458ZamHm}
}
```

---

## 7) License

Licensing may vary across components. Please see per-directory notices. If in doubt, contact the authors.
