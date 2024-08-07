### Setup
```sh
export PYTHONPATH=path/to/code-contest-exp
```

### To run test driver generation:
```sh
python scripts/main.py --problem_root_dir temp --output_file temp_result.json
```

### To run test execution, e.g., Java solutions
```sh
python scripts/run_java.py --problem_root_dir temp --gen_output_file temp_result.json --exec_output_file exec_temp_result.json
```