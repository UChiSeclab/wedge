# Instruction on Running the Experiments

## Set of strategies to be evaluated:
strategies=( "alphacode_sanitized" "time_contrast" "feedback_diff_solution" "feedback_diff_input" "feedback_multi_solution_diff_input" "multi_solution_diff_input" "diff_solution_one_input" "plain_problem" "slow_solution" "random_solution" "evalperf_slow_solution" "evalperf_random_solution" "corpus_mutator_with_constraint_multi" ) # this list maybe updated later

## Environment Setup
We use Ubuntu 20.04 machines

1. conda install environments:
    perf: environment for the scripts
    py27: environment for python2 solutions
    py38: environment for python3 solutions
```bash
# install miniconda
wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh
bash Miniconda3-latest-Linux-x86_64.sh
# create environments
conda create -n py27 python==2.7.13
conda create -n py38 python==3.8 
conda create -n perf python==3.9
conda activate perf # main environment for our experiment
cd code-contest-exp
pip install -r requirements.txt
```
2. install java-11
```bash
wget https://download.java.net/java/ga/jdk11/openjdk-11_linux-x64_bin.tar.gz
tar -zxvf openjdk-11_linux-x64_bin.tar.gz
```
3. install perf dependencies
```bash
sudo apt-get update
sudo apt-get install linux-tools-common linux-tools-generic linux-tools-`uname -r`
sudo sysctl kernel.perf_event_paranoid=-1
```
4. install coverage collection dependencies
```bash
# cpp
which gcov # test whether gcov is installed
pip install gcovr
# python
pip install -r ../feedback_collection/python/requirements.txt
```
5. setup AFL++ environment
```bash
mkdir aflpp
cd aflpp
git clone https://github.com/AFLplusplus/AFLplusplus.git
cd AFLplusplus
git checkout v4.30c
export AFLPP_DIR=$(pwd)
```
Follow the instructions here: https://github.com/AFLplusplus/AFLplusplus/blob/stable/docs/INSTALL.md.
## Setting PYTHONPATH
```python
cd code-contest-exp
export PYTHONPATH=$(pwd)/scripts:../feedback_collection
```
## Adding the problems
1. Insert the list of problem ids that you are going to run as the first element of the json file: https://github.com/bastoica/llm4perf/blob/main/code-contest-exp/problem_list.json#L2. When `use_specified_problem` is set to True in `get_cf_problems`, the first element would be used.
2. Run `add_problem.py`. This will add the problems you specified to `config["problem_root_dir"]`.
```bash
python scripts/add_problem.py
```

## Create log directory (for debugging)
```bash
mkdir log
cd log
mkdir constraint_gen corpus_gen cov_collect dump feedback_collect gen mutator_gen validator_gen run
cd ..
```

## Collecting Alphacode tests statistics first
Run `run.py` for alphacode tests. This will produce result json files in `config["result_root_dir"]/alphacode` and facilitate many consequent operations.
```bash
python scripts/run.py --experiment_name alphacode > log/run/alphacode.log
```

## Preparing for test generation (prompting and fuzzing)
### Input validator generation
Run `input_validator_gen.py` to prompt the LLM to generate validator scripts. The scripts will be stored in `config["problem_root_dir"]/{problem_id}/validator_gen/{validator_mode}`. It may try multiple times to generate a good validator. If there's an empty file named `VAL_GT_INPUT_PASS` under that directory, the validator is considered good.
```bash
python scripts/input_validator_gen.py --validator_mode self_reflect_feedback > log/validator_gen/self_reflect_feedback.log
```
### Feedback collection (coverage and hit count)
Although our framework supports collecting coverage and hit count for cpp, python and java, the one with cpp is considered the most stable and reliable. So let's collect feedback for cpp solutions first. The results will be stored in `config["coverage_hit_count_output_dir"]` and `config["cov_data_dir"]`.
(This may take very long.)
```bash
python scripts/feedback_collect.py --experiment_name feedback_diff_solution --solution_language cpp > log/feedback_collect/feedback_diff_solution.log 2>&1

python scripts/feedback_collect.py --experiment_name feedback_diff_input --solution_language cpp > log/feedback_collect/feedback_diff_input.log 2>&1

python scripts/feedback_collect.py --experiment_name feedback_multi_solution_diff_input --solution_language cpp --top_k 5 > log/feedback_collect/feedback_multi_solution_diff_input.log 2>&1
```
```bash
python scripts/collect_cov_report.py --experiment_name alphacode
```

### Constraint extraction
1. Input pair mining
```bash
python scripts/cgig/mine_input_pairs.py
```
This script will produce a set of contrasting input pairs in `config["input_pairs_dir"]`. The input pairs will be used to generate constraints. The input pairs are ranked based on similarity and performance ratio.

2. Constraint generation
```bash
python scripts/cgig/constraint_gen.py > log/constraint_gen/constraint_gen.log
```
This script will extract constraints from solutions along with input pairs and write to `config["constraints_dir"]`. At most 5 input pairs (constraints) per problem. It will generate a response file from GPT (which can serve as a COT) and an instrumented program (original solution + inserted constraints).

### Mutator generation
Run `mutator_gen.py` to generate mutators that can be used for fuzzing.
```bash
python scripts/cgig/mutator_gen.py --mutator_type mutator_with_constraint_multi --problem_with_extracted_constraint_only True --mutator_mode self_reflect_feedback > log/mutator_gen/mutator_with_constraint.log 2>&1
```
This script will produce a mutator script which is located in `constraint_guided_input_gen`. Different mutator modes have different directories. This script will try to generate a `mutator.py` script that serves as a custom mutator module for AFL++. It will run AFL++ with this mutator on some solutions for 180s. If it can successfully finish the run without failure, it is good and an empty file named `MUTATOR_CHECK_PASS` will be produced, otherwise it just retries until the retry limit is reached.


## Test generation
### Prompting strategies
Replace the path to code-contest-exp to your absolute path in `scripts_hub/gen_all.sh` and run the script.
Note that it may try up to 10 times to produce a valid generator. If all attempts failed, it will record that in the file `config["gen_tests_failing_problem_record"]`. If we distribute the experiments in different machines, in the evaluation stage we need to merge them.
### Fuzzing strategies
Run the `corpus_gen.py` script to launch fuzzing.
```bash
python scripts/cgig/corpus_gen.py --mutator_type mutator_with_constraint_multi --problem_with_extracted_constraint_only True > log/corpus_gen/mutator_with_constraint_multi.log 2>&1
```

## Post processing
### Alphacode tests
We observed that many alphacode test inputs are actually violating the natural language constraints. So we need to sanitize the alphacode tests with our validators:
```bash
python scripts/sanitize_alphacode_result.py
```
It will produce directories that contain sanitized alphacode tests in `config["problem_root_dir"]/{problem_id}/alphacode_sanitized`. We evaluate `alphacode_sanitized` as a standalone strategy.
### AFL++-generated tests
Similarly the fuzzer generated tests are not guaranteed to be valid. We validate and dump the fuzzer generated tests from the AFL++ output directory into the `config["problem_root_dir"]/{problem_id}/{strategy}` directory.
```bash
python scripts/cgig/dump_corpus_inputs.py --mutator_type mutator_with_constraint_multi --problem_with_extracted_constraint_only True > log/dump/corpus_mutator_with_constraint_multi.log
```
## Test execution
For prompting strategies:
```bash
python scripts/run.py --experiment_name ${strategy} >> log/run/${strategy}.log
```
For fuzzing strategies:
```bash
python scripts/run.py --experiment_name ${strategy} --problem_with_extracted_constraint_only True >> log/run/${strategy}.log
```

## Sanitize alphacode test results
Since a considerable proportion of alphacode tests might violate the specified input constraints from the problem statements, we rely on the generated input validators to sanitize the alphacode tests.
```bash
python scripts/sanitize_alphacode_result.py
```

## About monitoring the experiments
Just check the log files and corresponding output files to see if everything is going OK. For example, when running `run.py`, check if the number of files under `results/${strategy}` is keeping increasing, etc.
