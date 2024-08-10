#!/bin/bash

SCRIPTS_DIR=$(dirname $(realpath "${BASH_SOURCE[0]}"))
source ${SCRIPTS_DIR}/utils.sh

# check the number of arguments
if [ "$#" -ne 4 ]; then
    echo "Usage: coverage.sh solutions_xxx.java test_xx.in work_dir output_dir"
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
javac "$class_name.java"

# run the solution to collect coverage
# java "$class_name" < "$input_file"
JACOCO_AGENT_JAR_PATH=$(realpath ${SCRIPTS_DIR}/../../lib/jacocoagent.jar)
java -javaagent ${JACOCO_AGENT_JAR_PATH}=destfile=jacoco.exec "$class_name" < "$input_file"

# generate the coverage report
java -jar ${JACOCO_AGENT_JAR_PATH} report jacoco.exec --classfiles ${class_name}.class --classfiles . --xml xml_report.xml --html html_report --sourcefiles .

# extract the coverage report (xml) and record source code with coverage to .java.cov
python ${SCRIPTS_DIR}/cov_xml_parser.py --src_file_path "$class_name.java" --coverage_xml_path xml_report.xml --output_path "$class_name.java.cov"
cp "$class_name.java.cov" "$output_dir"
cd "$cwd"
rm -rf "$work_dir"
