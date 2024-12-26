#!/bin/bash

validator_modes=("direct" "resample" "self_reflect" "self_reflect_feedback")

cd /zp_vegeta/scratch_sb/juny/research/llm4perf/code-contest-exp

for validator_mode in ${validator_modes[@]}; 
do
    echo "Running validator_mode: $validator_mode";
    python scripts/input_validator_gen.py --validator_mode ${validator_mode} > log/validator_gen/${validator_mode}.log;
done
