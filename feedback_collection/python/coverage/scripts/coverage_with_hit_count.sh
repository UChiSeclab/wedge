#!/bin/bash

SCRIPTS_DIR=$(dirname $(realpath "${BASH_SOURCE[0]}"))

# check the number of arguments
if [ "$#" -ne 4 ]; then
    echo "Usage: coverage_with_hit_count.sh solutions_xxx.py test_xx.in work_dir output_dir"
    exit 1
fi

solution_file=$(realpath "$1") # solutions_xxx.py
input_file=$(realpath "$2") # test_xx.in
work_dir="$3"
output_dir="$4"
cwd=$(pwd)

# clean the work directory
rm -rf "$work_dir"
mkdir -p "$work_dir"
mkdir -p "$output_dir"
wd_solution_file="$(basename $solution_file)"
cp "$solution_file" "$work_dir"
cd "$work_dir" || exit

# run the solution to collect coverage with coverage.py
coverage run --branch "$wd_solution_file" < "$input_file"
coverage xml -o coverage.xml > /dev/null

# extract the coverage reports (xml) and record source code with coverage to .py.cov
python ${SCRIPTS_DIR}/instrument_line_profiler.py --ori_code_file_path "$wd_solution_file" --input_file_path "$input_file" --include_plain_coverage True --include_hit_count True --coveragepy_report_file_path coverage.xml --output_cov_file_path "$wd_solution_file.cov"

cd "$cwd"
cp "${work_dir}/${wd_solution_file}.cov" "$output_dir"
