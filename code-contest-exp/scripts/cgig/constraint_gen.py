from pathlib import Path
import json
from fire import Fire

from cpp.coverage.scripts.cov_product_gen import main as dump_product_cov
from feedback_collect import collect_coverage_hit_count
from config import config
from common import Language
from gpt_caller import request_conversation

def get_product_cov(
  problem_id: str,
  solution_id: str,
  slow_input_id: str,
  fast_input_id: str,
  language: Language = Language.CPP,
  info_line_end: bool = False,
) -> Path:
  problem_dir = Path(config["problem_root_dir"]) / problem_id
  solution_file = problem_dir / "solutions" / str(language) / f"{solution_id}.cpp"
  slow_cobertura_xml_file = (
    Path(config["cov_data_dir"])
    / "alphacode"
    / problem_id
    / str(language)
    / solution_id
    / slow_input_id[:-3]
    / "coverage.xml"
  )
  fast_cobertura_xml_file = (
    Path(config["cov_data_dir"])
    / "alphacode"
    / problem_id
    / str(language)
    / solution_id
    / fast_input_id[:-3]
    / "coverage.xml"
  )
  info_location = "line_start" if not info_line_end else "line_end"

  product_cov_file = (
    Path(config["product_cov_data_dir"])
    / problem_id
    / str(language)
    / solution_id
    / info_location
    / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}.cov"
  )
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


def compile_invariants_prompt(
  invariants_template_file: Path,
  problem_statement_file: Path,
  solution_file: Path,
  slow_input_file: Path,
  fast_input_file: Path,
  product_cov_file: Path
) -> str:

  template_text = invariants_template_file.read_text()
  problem_statement = problem_statement_file.read_text()
  solution = solution_file.read_text()
  slow_input = slow_input_file.read_text()
  fast_input = fast_input_file.read_text()
  product_cov = product_cov_file.read_text()

  invariants_prompt = (
    template_text
    .replace("{problem_statement}", problem_statement)
    .replace("{one_solution}", solution)
    .replace("{slow_input}", slow_input)
    .replace("{fast_input}", fast_input)
    .replace("{product_cov}", product_cov)
  )
  return invariants_prompt

def compile_checker_prompt(
  checker_template_file: Path,
  problem_statement_file: Path,
  solution_file: Path,
  
) -> str:

  template_text = checker_template_file.read_text()
  problem_statement = problem_statement_file.read_text()
  solution = solution_file.read_text()

  checker_prompt = (
    template_text
    .replace("{problem_statement}", problem_statement)
    .replace("{one_solution}", solution)
  )
  return checker_prompt


def main(
  top_k: int = 10
):
  problem_root_dir = Path(config["problem_root_dir"])
  input_pairs_dir = Path(config["input_pairs_dir"])
  extracted_constraints_dir = Path(config["constraints_dir"])
  cov_data_root_dir = Path(config["cov_data_dir"])
  
  invariants_template_file = Path(config["cgig_prompt_template_dir"]) / "constraint_gen_invariants_prompt.txt"
  checker_template_file = Path(config["cgig_prompt_template_dir"]) / "constraint_gen_checker_prompt.txt"

  input_pairs_file = input_pairs_dir / "content_similar_problem_solution_input_pairs_sorted.json"
  problem_solution_input_pairs = json.loads(input_pairs_file.read_text())

  for problem_id in problem_solution_input_pairs:
    print(f"Processing {problem_id}")
    problem_dir = problem_root_dir / problem_id
    solution_input_pairs = problem_solution_input_pairs[problem_id]
    num_solution_with_cov = 0
    for solution_id in solution_input_pairs:
      if num_solution_with_cov >= top_k:
        break
      best_input_pair_id = list(solution_input_pairs[solution_id])[0] # best input pair for this solution
      slow_input_id, fast_input_id = best_input_pair_id.split("@")
      solution_file = problem_dir / "solutions" / "cpp" / f"{solution_id}.cpp"

      # collect coverage data if not exists
      for input_id in [slow_input_id, fast_input_id]:
        input_file = problem_dir / "input" / input_id
        cov_data_dir = cov_data_root_dir / "alphacode" / problem_id / "cpp" / solution_id / input_id[:-3]
        cov_data_dir.mkdir(parents=True, exist_ok=True)
        src_with_cov_file = cov_data_dir / f"{solution_id}.cov"
        cov_report_file = cov_data_dir / "coverage.xml"
        if not src_with_cov_file.exists() or not cov_report_file.exists():
          collect_coverage_hit_count((solution_file, input_file, Language.CPP, src_with_cov_file, cov_report_file))
        else:
          print(f"Skip: {src_with_cov_file} and {cov_report_file} already exists")

      try:
        product_cov_file = get_product_cov(
          problem_id,
          solution_id,
          slow_input_id,
          fast_input_id,
          language=Language.CPP,
          info_line_end=True
        )
        num_solution_with_cov += 1
      except FileNotFoundError as e:
        print(f"[Warning] {e}")
        print(f"[Warning] some cov files are missing when generating product cov for {problem_id}-{solution_id}")
        continue

      assert product_cov_file, f"product cov file not found for {problem_id}'s solutions"

      result_dir = extracted_constraints_dir / problem_id / solution_id / f"{slow_input_id[:-3]}_{fast_input_id[:-3]}"
      result_dir.mkdir(parents=True, exist_ok=True)
      
      combined_responses_file = result_dir / "gpt_response.txt"
      conversation_file = result_dir / "conversations.txt"
      cpp_checker_file = result_dir / "transformed_program.cpp"

      if not combined_responses_file.exists() or not cpp_checker_file.exists():
        msg_list = []
        msg_list.append({"role": "system", "content": f"You are a helpful assistant good at coding."})

        invariants_prompt = compile_invariants_prompt(
          invariants_template_file,
          problem_root_dir / problem_id / "problem_statement.txt",
          problem_root_dir / problem_id / "solutions" / "cpp" / f"{solution_id}.cpp",
          problem_root_dir / problem_id / "input" / slow_input_id,
          problem_root_dir / problem_id / "input" / fast_input_id,
          product_cov_file
        )
        msg_list.append({"role": "user", "content": invariants_prompt})
        

        first_response_data = request_conversation(msg_list)

        invariants_response = first_response_data.choices[0].message.content

        msg_list.append({"role": "assistant", "content": invariants_response})

        checker_prompt = compile_checker_prompt(
          checker_template_file,
          problem_root_dir / problem_id / "problem_statement.txt",
          problem_root_dir / problem_id / "solutions" / "cpp" / f"{solution_id}.cpp"
        )
        msg_list.append({"role": "user", "content": checker_prompt})

        second_response_data = request_conversation(msg_list)
        checker_response = second_response_data.choices[0].message.content
        msg_list.append({"role": "assistant", "content": checker_response})

        with open(combined_responses_file, "w", encoding="utf-8") as f:
          f.write("=== Invariants Response ===\n")
          f.write(invariants_response + "\n\n")
          f.write("=== Checker Response ===\n")
          f.write(checker_response + "\n")

        if "```cpp" in checker_response:
          cpp_code = checker_response.split("```cpp")[-1].split("```")[0].strip()
        else:
          cpp_code = checker_response.strip()

        # write the conversation to a file
        with open(conversation_file, "w", encoding="utf-8") as f:
          for msg in msg_list:
            f.write(f"{msg['role']}: {msg['content']}\n\n")

        cpp_checker_file.write_text(cpp_code)
        print(f"[Info] Done generating invariants & checker for {problem_id}-{solution_id}; see {combined_responses_file}")

    if num_solution_with_cov < top_k:
      print(f"[Warning] Only {num_solution_with_cov} solutions have product_cov for {problem_id}")

if __name__ == "__main__":
  Fire(main)