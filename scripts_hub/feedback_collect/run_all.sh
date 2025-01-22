#!/bin/bash

mkdir -p log/feedback_collect

# go to the root directory of the project
work_dir=$(git rev-parse --show-toplevel)/code-contest-exp
cd $work_dir

python scripts/feedback_collect.py --experiment_name feedback_diff_solution --solution_language cpp > log/feedback_collect/feedback_diff_solution.log 2>&1

python scripts/feedback_collect.py --experiment_name feedback_diff_input --solution_language cpp > log/feedback_collect/feedback_diff_input.log 2>&1

python scripts/feedback_collect.py --experiment_name feedback_multi_solution_diff_input --solution_language cpp --top_k 5 > log/feedback_collect/feedback_multi_solution_diff_input.log 2>&1