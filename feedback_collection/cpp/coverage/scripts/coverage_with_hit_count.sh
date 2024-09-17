#!/bin/bash

SCRIPTS_DIR=$(dirname $(realpath "${BASH_SOURCE[0]}"))

# check the number of arguments
if [ "$#" -ne 4 ]; then
    echo "Usage: coverage_with_hit_count.sh solutions_xxx.cpp test_xx.in work_dir output_dir"
    exit 1
fi

solution_file="$1" # solutions_xxx.cpp
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

# the following code snippet is learned from
# https://github.com/shenxianpeng/gcov-example/blob/master/makefile
# compile the solution
CFLAG="-std=c++17 -fPIC -fprofile-arcs -ftest-coverage -fpermissive"
obj_file="${wd_solution_file%.*}.o"
exec_file="${wd_solution_file%.*}"
g++ $CFLAG -c $wd_solution_file
if [ $? -ne 0 ]; then
    echo "Failed to compile the solution with ${CFLAG}"
    CFLAG="-fPIC -fprofile-arcs -ftest-coverage -fpermissive"
    g++ $CFLAG -c $wd_solution_file
    if [ $? -ne 0 ]; then
        echo "Failed to compile the solution with ${CFLAG}"
        CFLAG="-std=c++14 -fPIC -fprofile-arcs -ftest-coverage -fpermissive"
        g++ $CFLAG -c $wd_solution_file
        if [ $? -ne 0 ]; then
            echo "Failed to compile the solution with ${CFLAG}"
            CFLAG="-std=c++23 -fPIC -fprofile-arcs -ftest-coverage -fpermissive"
            g++ $CFLAG -c $wd_solution_file
            if [ $? -ne 0 ]; then
                echo "Failed to compile the solution with ${CFLAG}"
                CFLAG="-std=c++11 -fPIC -fprofile-arcs -ftest-coverage -fpermissive"
                g++ $CFLAG -c $wd_solution_file
                if [ $? -ne 0 ]; then
                    echo "Failed to compile the solution with ${CFLAG}"
                    CFLAG="-std=c++11 -O2 -fPIC -fprofile-arcs -ftest-coverage -fpermissive"
                    g++ $CFLAG -c $wd_solution_file
                    if [ $? -ne 0 ]; then
                        echo "Failed to compile the solution with ${CFLAG}"
                        exit 1
                    fi
                fi
            fi
        fi
    fi
fi
g++ $CFLAG -o $exec_file $obj_file
timeout 180 ./$exec_file < "$input_file"

# complain if timeout
if [ $? -eq 124 ]; then
    echo "Solution execution timeout"
    echo "cmd: ./$exec_file < $input_file"
    exit 1
fi
gcov $wd_solution_file
gcovr --root . --cobertura-pretty --cobertura cobertura.xml

# extract the coverage reports (xml) and record source code with coverage to .cpp.cov
python ${SCRIPTS_DIR}/cov_xml_parser.py --src_file_path "$wd_solution_file" --cobertura_xml_path cobertura.xml --output_path "$wd_solution_file.cov"
cd "$cwd"
cp "${work_dir}/${wd_solution_file}.cov" "$output_dir"
