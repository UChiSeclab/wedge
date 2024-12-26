#!/bin/bash

mkdir -p log/gen

cd /zp_vegeta/scratch_sb/juny/research/llm4perf/code-contest-exp

echo "Running strategy: time_contrast"
python scripts/gen_tests.py --experiment_name time_contrast --prompt_template prompt_template_time_contrast.txt --prompt_language cpp --run_tests True >> log/gen/time_contrast_debug.log

echo "Running strategy: plain_problem"
python scripts/gen_tests.py --experiment_name plain_problem --prompt_template prompt_template_plain_problem.txt --prompt_language cpp --run_tests True >> log/gen/plain_problem_debug.log

echo "Running strategy: slow_solution"
python scripts/gen_tests.py --experiment_name slow_solution --prompt_template prompt_template_one_solution.txt --prompt_language cpp --run_tests True >> log/gen/slow_solution_debug.log

echo "Running strategy: random_solution"
python scripts/gen_tests.py --experiment_name random_solution --prompt_template prompt_template_one_solution.txt --prompt_language cpp --run_tests True >> log/gen/random_solution_debug.log

echo "Running strategy: multi_solution_diff_input"
python scripts/gen_tests.py --experiment_name multi_solution_diff_input --prompt_template prompt_template_multi_solution_diff_input.txt --prompt_language cpp --top_k 5 --run_tests True >> log/gen/multi_solution_diff_input_debug.log

echo "Running strategy: diff_solution_one_input"
python scripts/gen_tests.py --experiment_name diff_solution_one_input --prompt_template prompt_template_diff_solution_one_input.txt --prompt_language cpp --run_tests True >> log/gen/diff_solution_one_input_debug.log

echo "Running strategy: evalperf_slow_solution"
python scripts/gen_tests.py --experiment_name evalperf_slow_solution --prompt_template prompt_template_evalperf.txt --prompt_language cpp --run_tests False >> log/gen/evalperf_slow_solution_debug.log

echo "Running strategy: evalperf_random_solution"
python scripts/gen_tests.py --experiment_name evalperf_random_solution --prompt_template prompt_template_evalperf.txt --prompt_language cpp --run_tests False >> log/gen/evalperf_random_solution_debug.log

echo "Running strategy: feedback_diff_solution"
python scripts/gen_tests.py --experiment_name feedback_diff_solution --prompt_template prompt_template_with_feedback_diff_solution.txt --prompt_language cpp --run_tests True >> log/gen/feedback_diff_solution_debug.log

echo "Running strategy: feedback_diff_input"
python scripts/gen_tests.py --experiment_name feedback_diff_input --prompt_template prompt_template_with_feedback_diff_input.txt --prompt_language cpp --run_tests True >> log/gen/feedback_diff_input_debug.log

echo "Running strategy: feedback_multi_solution_diff_input"
python scripts/gen_tests.py --experiment_name feedback_multi_solution_diff_input --prompt_template prompt_template_with_feedback_multi_solution_diff_input.txt --prompt_language cpp --top_k 5 --run_tests True >> log/gen/feedback_multi_solution_diff_input_debug.log
