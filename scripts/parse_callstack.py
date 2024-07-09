import re
import sys

def parse_log_file(file_path):
    call_stacks = []
    executed_times = []

    # Define the pattern to search for the specific line
    pattern = re.compile(r'\[for_llm\] Call stack: \[(.*?)\] has been executed (\d+) times\.')
    
    # Read the file and parse the required lines
    with open(file_path, 'r') as file:
        for line in file:
            match = pattern.search(line)
            if match:
                # Extract the call stack and number of executed times
                call_stack_str = match.group(1)
                executed_time = int(match.group(2))
                
                # Split the call stack into a list of strings
                call_stack = [func.strip() for func in call_stack_str.split(',')]
                
                # Append to the results
                call_stacks.append(call_stack)
                executed_times.append(executed_time)
    
    return call_stacks, executed_times

# Usage example
file_path = sys.argv[1]
call_stacks, executed_times = parse_log_file(file_path)

print(len(executed_times))