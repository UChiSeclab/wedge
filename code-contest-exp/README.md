### Setup
```sh
export PYTHONPATH=path/to/code-contest-exp
```

### To run test driver generation:
```sh
python scripts/gen_tests.py --experiment_name debug_exp --problem_root_dir debug_temp --run_tests True # enabling the "--run_tests" flag will execute the generated tests, otherwise the generated tests won't be executed
```

### To run test execution, e.g., Java solutions
```sh
python scripts/run_java.py --experiment_name debug_exp --problem_root_dir debug_temp
```