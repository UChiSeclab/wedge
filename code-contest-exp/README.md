### Setup

```sh
cd code-contest-exp
pip install requirements.txt
python ./scripts/add_problem.py
```

### Test manually created test generator

Setup the config file (`./scripts/config.py`)

```python
config = {
    "problem_root_dir": "./problems",
    "max_time_limit": 20,
    "experiment_name": "none",             # add experiment name
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