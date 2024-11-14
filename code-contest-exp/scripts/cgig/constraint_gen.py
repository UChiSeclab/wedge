from pathlib import Path
import json

from cgig.mine_input_pairs import mine_relational_input_pairs
from cpp.coverage.scripts.cov_product_gen import main as dump_product_cov
from config import config
from common import Language
from gpt_caller import request

def get_product_cov(
    problem_id: str,
    solution_id: str,
    slow_input_id: str,
    fast_input_id: str,
    language: Language = Language.CPP, # we only support C++ for now
    info_line_end: bool = False,
) -> Path:
    problem_dir = Path(config["problem_root_dir"]) / problem_id
    solution_file = problem_dir / "solutions" / str(language) / f"{solution_id}.cpp"
    slow_cobertura_xml_file = Path(config["cov_data_dir"]) / "alphacode" / problem_id / str(language) / solution_id / slow_input_id[:-3] / "coverage.xml"
    fast_cobertura_xml_file = Path(config["cov_data_dir"]) / "alphacode" / problem_id / str(language) / solution_id / fast_input_id[:-3] / "coverage.xml"
    info_location = "line_start" if not info_line_end else "line_end"

    product_cov_file = Path(config["product_cov_data_dir"]) / problem_id / str(language) / solution_id / info_location / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}.cov"
    product_cov_file.parent.mkdir(parents=True, exist_ok=True)

    if not product_cov_file.exists():
        dump_product_cov(
            str(solution_file),
            str(slow_cobertura_xml_file),
            str(fast_cobertura_xml_file),
            str(product_cov_file),
            info_line_end=info_line_end,
        )

    return product_cov_file


def compile_constraint_gen_prompt(
    prompt_template_file:Path,
    problem_statement_file:Path,
    solution_file:Path,
    slow_input_file:Path,
    fast_input_file:Path,
    product_cov_file:Path
) -> str:
    prompt_template = prompt_template_file.read_text()
    problem_statement = problem_statement_file.read_text()
    solution = solution_file.read_text()
    slow_input = slow_input_file.read_text()
    fast_input = fast_input_file.read_text()
    product_cov = product_cov_file.read_text()

    prompt = prompt_template \
        .replace("{problem_statement}", problem_statement) \
        .replace("{one_solution}", solution) \
        .replace("{slow_input}", slow_input) \
        .replace("{fast_input}", fast_input) \
        .replace("{product_cov}", product_cov)

    return prompt

if __name__ == '__main__':
    problem_root_dir = Path(config["problem_root_dir"])
    input_pairs_dir = Path(config["input_pairs_dir"])
    extracted_constraints_dir = Path(config["constraints_dir"])
    prompt_template_file = Path(config["cgig_prompt_template_dir"]) / "constraint_gen_few_shots.txt"
    input_pairs_file = input_pairs_dir / "content_similar_problem_solution_input_pairs.json"
    problem_solution_input_pairs = json.loads(input_pairs_file.read_text())

    for problem_id in problem_solution_input_pairs:
        for solution_id in problem_solution_input_pairs[problem_id]:
            for slow_input_id, fast_input_id in problem_solution_input_pairs[problem_id][solution_id]:
                slow_input_file = problem_root_dir / problem_id / "input" / slow_input_id
                fast_input_file = problem_root_dir / problem_id / "input" / fast_input_id
                product_cov_file = get_product_cov(
                    problem_id,
                    solution_id,
                    slow_input_id,
                    fast_input_id,
                    language=Language.CPP,
                    info_line_end=True
                )

                prompt = compile_constraint_gen_prompt(
                    prompt_template_file,
                    problem_root_dir / problem_id / "problem_statement.txt",
                    problem_root_dir / problem_id / "solutions" / "cpp" / f"{solution_id}.cpp",
                    slow_input_file,
                    fast_input_file,
                    product_cov_file
                )
                result_dir = extracted_constraints_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}"
                result_dir.mkdir(parents=True, exist_ok=True)
                instrumented_program_file = result_dir / "transformed_program.cpp"
                if not instrumented_program_file.exists():
                    (result_dir / "prompt.txt").write_text(prompt)
                    response = request(prompt)
                    transformed_program = response.split('```cpp')[1].split('```')[0].strip()
                    instrumented_program_file.write_text(transformed_program)
                    (result_dir / "gpt_response.txt").write_text(response)
