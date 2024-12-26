#!/bin/bash

mkdir -p log/run

# strategies=("time_contrast" "feedback_diff_solution" "feedback_diff_input" "feedback_multi_solution_diff_input" "multi_solution_diff_input" "diff_solution_one_input" "plain_problem" "slow_solution" "random_solution" "evalperf_slow_solution" "evalperf_random_solution")

# the slow solution might be too sick. let's abandon it for now

strategies=("random_solution" "evalperf_slow_solution" "evalperf_random_solution")

cd /zp_vegeta/scratch_sb/juny/research/llm4perf/code-contest-exp

for strategy in ${strategies[@]}; do
    echo "Running strategy: $strategy"
    python scripts/run.py --experiment_name $strategy >> log/run/${strategy}_3.log
done