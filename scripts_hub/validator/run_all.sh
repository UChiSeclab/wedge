#!/bin/bash

strategies=("alphacode" "time_contrast" "feedback_diff_solution" "feedback_diff_input" "feedback_multi_solution_diff_input" "multi_solution_diff_input" "diff_solution_one_input" "plain_problem" "slow_solution" "random_solution" "evalperf_slow_solution" "evalperf_random_solution")

validator_modes=("direct" "resample" "self_reflect" "self_reflect_feedback")

cd /zp_vegeta/scratch_sb/juny/research/llm4perf/code-contest-exp

for validator_mode in ${validator_modes[@]}; 
do
    for strategy in ${strategies[@]}; 
    do
        echo "Running strategy: $strategy with validator_mode: $validator_mode";
        python scripts/input_validator_run.py --experiment_name ${strategy} --validator_mode ${validator_mode} > log/validator_run/${strategy}_${validator_mode}.log;
    done
done
