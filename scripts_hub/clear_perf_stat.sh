#!/bin/bash

par_dir=$(dirname "${BASH_SOURCE[0]}")
problem_root_dir=$(realpath "$(realpath "${par_dir}")/../code-contest-exp/problems")
results_root_dir=$(realpath "$(realpath "${par_dir}")/../code-contest-exp/results")

find "${problem_root_dir}" -name perf_stat | while read -r perf_stat_dir; do
    # Determine the directory hierarchy
    if [ "$(basename "$(dirname "$(dirname "${perf_stat_dir}")")")" == "problems" ]; then
        strategy_name="alphacode"
        problem_dir=$(dirname "${perf_stat_dir}")
    else
        strategy_name=$(basename "$(dirname "${perf_stat_dir}")")
        problem_dir=$(dirname "$(dirname "${perf_stat_dir}")")
    fi

    # Extract problem_id
    problem_id=$(basename "${problem_dir}")

    # Get result file
    result_file="${results_root_dir}/${strategy_name}/${problem_id}.json"
    if [ -f "${result_file}" ]; then
        # Get the size of the perf_stat_dir in bytes
        perf_stat_size=$(du -s --block-size=1 ${perf_stat_dir} | awk '{print $1}')
        if [ "${perf_stat_size}" -gt 1000000 ]; then
            # echo "${result_file}"
            echo "=========="
            echo "removing ${perf_stat_dir} of size ${perf_stat_size}"
            rm -rf ${perf_stat_dir}
        fi
    fi
done
