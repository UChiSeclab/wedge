from pathlib import Path
import inspect

from config import config

caller_frame = inspect.stack()[-1]  # Get the caller frame
caller_filename = caller_frame.filename  # Extract the caller filename

# print(f"caller_filename: {caller_filename}")
# for caller_frame in inspect.stack():
#     caller_filename = caller_frame.filename
#     print(f"caller_filename: {caller_filename}")

if "prompt_exp" in caller_filename:
    ORI_SOLUTIONS_DIR = Path(config["effi_learner_dir"]) / "ori_human_solutions"
    ORI_SOLUTION_PROFILE_DIR = Path(config["effi_learner_dir"]) / "ori_human_solution_profile"
    OPTIMIZED_SOLUTIONS_DIR = Path(config["effi_learner_dir"]) / "optimized_human_solutions"
elif "finetune_exp" in caller_filename:
    ORI_SOLUTIONS_DIR = Path(config["pie_dir"]) / "ori_human_solutions"
    ORI_SOLUTION_PROFILE_DIR = Path(config["pie_dir"]) / "ori_human_solution_profile"
    OPTIMIZED_SOLUTIONS_DIR = Path(config["pie_dir"]) / "optimized_human_solutions"
else:
    raise ValueError("Unknown caller filename")