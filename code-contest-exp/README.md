### Setup

```sh
cd code-contest-exp
pip install requirements.txt
python ./scripts/add_problem.py
```

### Test all problems on alphacode tests

Setup the config file (`./scripts/config.py`)

```python
config = {
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "none",             # set experiment name to 'none'
    "solution_selection": "time_contrast", # doesn't matter
    "manual_prompt": False,                # set to False
    "prompt_language": Language.JAVA,      # doesn't matter
    "repeat_test": 3,
    "specified_problem": None
}
```

Run all the solutions for all problems with alphacode tests.
```sh
python ./scripts/run.py
```

### Test manually created test generator

Setup the config file (`./scripts/config.py`)

```python
config = {
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "experiment_name",  # add experiment name
    "solution_selection": "time_contrast", # doesn't matter
    "manual_prompt": True,                 # set to True
    "prompt_language": Language.JAVA,      # doesn't matter
    "repeat_test": 3,
    "specified_problem": []                # ex. ["133_E", "128_D"]
}
```

Follow the instruction in the script to put the test generator into the correct place.
```sh
python ./scripts/gen_tests.py
```

Run all the solutions for specified problems with generated tests.
```sh
python ./scripts/run.py
```

The results will be put in `./results/<experiment_name>`

### Test generating test generator by GPT

Setup the config file (`./scripts/config.py`)

```python
config = {
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "experiment_name",  # add experiment name
    "solution_selection": "time_contrast", # the solution selection method
    "manual_prompt": False,                # set to True
    "prompt_language": Language.JAVA,      # the solution language which will feed to the LLM
    "repeat_test": 3,
    "specified_problem": []                # ex. ["133_E", "128_D"]
}
```

Prompt GPT to write test generater and generate tests for specified problems.
```sh
python ./scripts/gen_tests.py
```

Run all the solutions for specified problems with generated tests.
```sh
python ./scripts/run.py
```

The results will be put in `./results/<experiment_name>`