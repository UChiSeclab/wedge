def calculate_memory_usage(dat_file_path):
    with open(dat_file_path, 'r') as file:
        prev_time = 0
        prev_mem_mb = 0
        mem_time_mb_s = 0
        next(file)
        for line in file:
            if not line.startswith('MEM'):
                continue  # Skip any line that does not start with 'MEM'
            parts = line.split()
            mem_in_mb = float(parts[1])
            timestamp = float(parts[2])
            if prev_time > 0:
                time_interval_s = timestamp - prev_time
                mem_time_mb_s += (prev_mem_mb + mem_in_mb) / 2 * time_interval_s
            prev_time = timestamp
            prev_mem_mb = mem_in_mb
        return mem_time_mb_s


def calculate_runtime(dat_file_path):
    with open(dat_file_path, 'r') as file:
        start_time = float("inf")
        end_time = float("-inf")
        next(file)
        for line in file:
            if not line.startswith('MEM'):
                continue  # Skip any line that does not start with 'MEM'
            parts = line.split()
            timestamp = float(parts[2])
            start_time = min(start_time, timestamp)
            end_time = max(end_time, timestamp)
        return max(end_time - start_time,0)

def report_max_memory_usage(dat_file_path):
    max_memory_usage = 0
    with open(dat_file_path, 'r') as file:
        prev_time = 0
        prev_mem_mb = 0
        mem_time_mb_s = 0
        next(file)
        for line in file:
            if not line.startswith('MEM'):
                continue  # Skip any line that does not start with 'MEM'
            parts = line.split()
            mem_in_mb = float(parts[1])
            max_memory_usage = max(max_memory_usage, mem_in_mb)
        return max_memory_usage