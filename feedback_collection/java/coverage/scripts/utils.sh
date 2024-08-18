#!/bin/bash

# Usage:
# find_main_class MyClass.java

find_main_class() {
    local file_path="$1"

    # Check if the file exists
    if [ ! -f "$file_path" ]; then
        echo "File not found!"
        return 1
    fi

    class_name=$(grep -E 'public\s+class\s+([a-zA-Z_][a-zA-Z0-9_]*)' "${file_path}" | awk '{for(i=1;i<=NF;i++){if($i=="class"){print $(i+1)}}}' | head -n 1 | sed 's/{//g')

    # Output the main class if found
    if [ -n "$class_name" ]; then
        echo "$class_name"
    else
        class_name=$(grep -E 'class\s+([a-zA-Z_][a-zA-Z0-9_]*)' "${file_path}" | awk '{for(i=1;i<=NF;i++){if($i=="class"){print $(i+1)}}}' | head -n 1 | sed 's/{//g')
        if [ -n "$class_name" ]; then
            echo "$class_name"
        else
            echo "No main class found."
        fi
    fi
}
