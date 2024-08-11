#!/bin/bash

SCRIPTS_DIR=$(dirname $(realpath "${BASH_SOURCE[0]}"))
source ${SCRIPTS_DIR}/utils.sh

# check the number of arguments
if [ "$#" -ne 4 ]; then
    echo "Usage: coverage_with_hit_count.sh solutions_xxx.java test_xx.in work_dir output_dir"
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
mkdir -p "$output_dir"
class_java_file="$work_dir/$class_name.java"
cp "$solution_file" "$class_java_file"

# compile the solution
cd "$work_dir" || exit
javac -g "$class_name.java"

# run the solution to collect coverage with Jacoco
# java "$class_name" < "$input_file"
JACOCO_AGENT_JAR_PATH=$(realpath ${SCRIPTS_DIR}/../../lib/jacocoagent.jar)
JACOCO_CLI_JAR_PATH=$(realpath ${SCRIPTS_DIR}/../../lib/jacococli.jar)
java -javaagent:${JACOCO_AGENT_JAR_PATH} "$class_name" < "$input_file"

# generate the Jacoco coverage report
java -jar ${JACOCO_CLI_JAR_PATH} report jacoco.exec --classfiles ${class_name}.class --classfiles . --xml xml_report.xml --html html_report --sourcefiles .

COBERTURA_DIR=${SCRIPTS_DIR}/../../lib/cobertura-2.1.1
COBERTURA_INSTRUMENT_SH=${COBERTURA_DIR}/cobertura-instrument.sh
COBERTURA_REPORT_SH=${COBERTURA_DIR}/cobertura-report.sh

# instrument the class file with Cobertura
instrumented_class_dir="instrumented"
${COBERTURA_INSTRUMENT_SH} --destination ${instrumented_class_dir} .

# run the solution to collect coverage and hit count with Cobertura
java -cp "${COBERTURA_DIR}/cobertura-2.1.1.jar:${COBERTURA_DIR}/lib/*:${instrumented_class_dir}" -Dnet.sourceforge.cobertura.datafile=cobertura.ser ${class_name} < "$input_file"

# generate the Cobertura coverage report
${COBERTURA_REPORT_SH} --destination . --format xml

# extract the coverage reports (xml) and record source code with coverage to .java.cov

python ${SCRIPTS_DIR}/cov_xml_parser.py --src_file_path "$class_name.java" --jacoco_xml_path xml_report.xml --cobertura_xml_path coverage.xml --output_path "$class_name.java.cov"
cp "$class_name.java.cov" "$output_dir"
cd "$cwd"
rm -rf "$work_dir"
