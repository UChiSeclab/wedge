#!/bin/bash

mkdir -p log/cov_collect

strategies=("time_contrast" "feedback_diff_solution" "feedback_diff_input" "feedback_multi_solution_diff_input" "multi_solution_diff_input" "diff_solution_one_input" "plain_problem" "slow_solution" "random_solution" "evalperf_slow_solution" "evalperf_random_solution")

cd /zp_vegeta/scratch_sb/juny/research/llm4perf/code-contest-exp

for strategy in ${strategies[@]}; do
    echo "Collecting coverage report for strategy: $strategy"
    python scripts/collect_cov_report.py --experiment_name $strategy > log/cov_collect/${strategy}.log 2>log/cov_collect/${strategy}_err.log
done