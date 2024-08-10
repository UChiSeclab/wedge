import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Dict, Literal

def parse_xml_coverage_report(xml_file:Path) -> Dict[str, Dict[str, Dict[int, Literal["COVERED", "NOT_COVERED", "PARTIALLY_COVERED"]]]]:
    # Parse the XML
    root = ET.parse(xml_file).getroot()

    src_file_coverage = {}

    # Iterate over each line in the sourcefile element
    for sourcefile in root.iter('sourcefile'):
        statement_coverage = {}
        branch_coverage = {}
        src_file_coverage[sourcefile.get('name')] = {
            'statement': statement_coverage,
            'branch': branch_coverage
        }
        for line in sourcefile.iter('line'):
            line_nr = int(line.get('nr'))
            # Statement coverage: covered if ci > 0
            ci = int(line.get('ci'))
            mi = int(line.get('mi'))
            statement_coverage[line_nr] = "COVERED" if ci > 0 else "NOT_COVERED"
            assert mi + ci > 0, f"Line {line_nr} has no coverage"
            
            # Branch coverage: covered if cb > 0
            cb = int(line.get('cb'))
            mb = int(line.get('mb'))
            if mb + cb > 0:
                if cb == 0:
                    branch_coverage[line_nr] = "NOT_COVERED"
                elif mb == cb:
                    branch_coverage[line_nr] = "COVERED"
                else:
                    branch_coverage[line_nr] = "PARTIALLY_COVERED"

    return src_file_coverage

def display_file_with_coverage(
    src_file:Path,
    file_coverage:Dict[str, Dict[int, bool]],
    output_file:Path
):
    file_with_coverage_lines = []
    if output_file == Path(""):
        output_file = src_file.with_suffix(f".java.cov")

    stmt_coverage = file_coverage["statement"]
    branch_coverage = file_coverage["branch"]
    for line_no, line in enumerate(src_file.read_text().splitlines(), start=1):
        branch_coverage_status = branch_coverage.get(line_no, None)
        stmt_coverage_status = stmt_coverage.get(line_no, None)
        if branch_coverage_status:
            line = f"/* line {line_no} {branch_coverage_status} */ " + line
        elif stmt_coverage_status:
            line = f"/* line {line_no} {stmt_coverage_status} */ " + line
        file_with_coverage_lines.append(line)

    output_file.write_text("\n".join(file_with_coverage_lines))

def main(
    src_file_path:str,
    coverage_xml_path:str,
    output_path:str=""
):
    all_file_coverage = parse_xml_coverage_report(Path(coverage_xml_path))
    display_file_with_coverage(Path(src_file_path), all_file_coverage[Path(src_file_path).name], Path(output_path))

if __name__ == "__main__":
    from fire import Fire
    Fire(main)
