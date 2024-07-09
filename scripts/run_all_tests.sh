#!/bin/bash

# Specify the Java file containing the test class
java_file=$1

cd hadoop

echo "Compiling..."
mvn clean compile -fn -B -DskipTests &> build.log

echo "Installing..."
mvn install -fn -B -DskipTests &> build.log

cd ..

# Extract the class name from the Java file (assuming the file contains only one public class)
class_name=$(grep -o 'public class [^ ]*' "$java_file" | awk '{print $3}')

# Find all test methods starting with "testX" in the specified Java file
test_methods=$(grep -o 'public void test[^ (]*' "$java_file" | awk '{print $3}')

# Create a temporary file to store the parse results
temp_file=$(mktemp)

# Loop through each test method and execute it
for test_method in $test_methods; do
    echo "Executing test: $class_name#$test_method"
    cd hadoop
    mvn test -Dtest="$class_name#$test_method" -fn -B &> build.log
    cd ..
    execution_count=$(python utils/parse_callstack.py $2)
    rm $2
    echo "$test_method has triggered the buggy function for $execution_count times."
    echo "$execution_count $test_method" >> "$temp_file"
done

# Sort the results based on the execution count and save them in class_name.txt
sort -n "$temp_file" > "./results/$class_name.txt"

# Clean up temporary file
rm "$temp_file"

echo "All specified tests have been executed."
