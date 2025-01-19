# We aim to show the utility of our tests in augumenting the technique proposed in the Neurips'24 paper `EFFI-LEARNER: Enhancing Efficiency of Generated Code via Self-Optimization`
The code in this folder is mostly migrated and adopted to our experiment from their repo: https://github.com/huangd1999/EffiLearner.

## Summary of the technique
The technique consists of three stages as described in their paper: 
### Code generation
Given the description of the coding problem, prompt an code LLM to generate a solution (in each batch in each epoch).
### Overhead profiling
Run the generated code on a set of open test cases provided by the dataset.

Execution time profiling: `line_profilier` library

Memory profiling: `memory_profiler` library

Instruction count profiling: [Our evaluation additional]: `cirron` library

Notes: their approach only works for Python. Let's focus on Python solutions then.

### Code Refinement
Prompt the model with the profiling information to generate a refined version code that can hopefully run more performantly.