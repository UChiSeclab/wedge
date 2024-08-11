#!/bin/bash

UTILS_SH=$(dirname $(realpath "${BASH_SOURCE[0]}"))/../../coverage/scripts/utils.sh
source ${UTILS_SH}

COBERTURA_DIR=$(dirname $(realpath "${BASH_SOURCE[0]}"))/../../lib/cobertura-2.1.1
COBERTURA_INSTRUMENT_SH=${COBERTURA_DIR}/cobertura-instrument.sh
COBERTURA_REPORT_SH=${COBERTURA_DIR}/cobertura-report.sh

# check the number of arguments
if [ "$#" -ne 4 ]; then
    echo "Usage: hit_count.sh solutions_xxx.java test_xx.in work_dir output_dir"
    exit 1
fi

solution_file="$1" # solutions_xxx.java
input_file="$2" # test_xx.in
work_dir="$3"
output_dir="$4"
cwd=$(pwd)

class_name=$(find_main_class "$solution_file")

# clean the work directory
rm -rf "$work_dir"
mkdir -p "$work_dir"
class_java_file="$work_dir/$class_name.java"
cp "$solution_file" "$class_java_file"

# compile the solution
cd "$work_dir" || exit
javac -g "$class_name.java"

# instrument the class file
instrumented_class_dir="instrumented"
${COBERTURA_INSTRUMENT_SH} --destination ${instrumented_class_dir} .

# run the solution to collect coverage and hit count
java -cp "${COBERTURA_DIR}/cobertura-2.1.1.jar:${COBERTURA_DIR}/lib/*:${instrumented_class_dir}" -Dnet.sourceforge.cobertura.datafile=cobertura.ser ${class_name} < "$input_file"

# generate the coverage report
${COBERTURA_REPORT_SH} --destination ${output_dir} --format xml

cd "$cwd"
# rm -rf "$work_dir"
