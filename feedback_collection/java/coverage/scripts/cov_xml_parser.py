import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Dict, Literal


def parse_jacoco_coverage_report(
    xml_file: Path,
) -> Dict[
    str, Dict[str, Dict[int, Literal["COVERED", "NOT_COVERED", "PARTIALLY_COVERED"]]]
]:
    # Parse the XML
    root = ET.parse(xml_file).getroot()

    src_file_coverage = {}

    # Iterate over each line in the sourcefile element
    for sourcefile in root.iter("sourcefile"):
        statement_coverage = {}
        branch_coverage = {}
        src_file_coverage[sourcefile.get("name")] = {
            "statement": statement_coverage,
            "branch": branch_coverage,
        }
        for line_element in sourcefile.iter("line"):
            line_nr = int(line_element.get("nr"))
            # Statement coverage: covered if ci > 0
            ci = int(line_element.get("ci"))
            mi = int(line_element.get("mi"))
            statement_coverage[line_nr] = "COVERED" if ci > 0 else "NOT_COVERED"
            assert mi + ci > 0, f"Line {line_nr} has no coverage"

            # Branch coverage: covered if cb > 0
            cb = int(line_element.get("cb"))
            mb = int(line_element.get("mb"))
            if mb + cb > 0:
                if cb == 0:
                    branch_coverage[line_nr] = "NOT_COVERED"
                elif mb == cb:
                    branch_coverage[line_nr] = "COVERED"
                else:
                    branch_coverage[line_nr] = "PARTIALLY_COVERED"

    return src_file_coverage


def parse_cobertura_coverage_report(
    xml_file: Path,
) -> Dict[str, Dict[int, int]]:
    # Parse the XML
    root = ET.parse(xml_file).getroot()

    src_file_hitcount = {}

    # Iterate over each line in the class element
    for clazz in root.iter("class"):
        file_name = clazz.get("filename")
        if file_name not in src_file_hitcount:
            src_file_hitcount[file_name] = {}

        for line in clazz.iter("line"):
            line_nr = int(line.get("number"))
            hits = int(line.get("hits"))
            src_file_hitcount[file_name][line_nr] = hits

    return src_file_hitcount


def record_file_with_coverage_and_hit_count(
    src_file: Path,
    file_coverage: Dict[str, Dict[int, bool]],
    file_hit_count: Dict[int, int],
    output_file: Path,
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
            if branch_coverage_status == "NOT_COVERED":
                line = f"/* line {line_no} {branch_coverage_status} */ " + line
            else:
                assert (
                    file_hit_count.get(line_no) > 0
                ), f"Line {line_no} has no hit count"
                line = (
                    f"/* line {line_no} {branch_coverage_status}, hit count: {file_hit_count.get(line_no)} */ "
                    + line
                )
        elif stmt_coverage_status:
            if stmt_coverage_status == "NOT_COVERED":
                line = f"/* line {line_no} {stmt_coverage_status} */ " + line
            else:
                assert (
                    file_hit_count.get(line_no) > 0
                ), f"Line {line_no} has no hit count"
                line = (
                    f"/* line {line_no} {stmt_coverage_status}, hit count: {file_hit_count.get(line_no)} */ "
                    + line
                )
        else:
            line = f"/* line {line_no} */ " + line
        file_with_coverage_lines.append(line)

    output_file.write_text("\n".join(file_with_coverage_lines))


def main(
    src_file_path: str,
    include_jacoco_coverage: bool = True,
    include_cobertura_hit_count: bool = True,
    jacoco_xml_path: str = None,
    cobertura_xml_path: str = None,
    output_path: str = "",
):
    assert (
        include_jacoco_coverage and include_cobertura_hit_count
    ), "Currently only supports both Jacoco and Cobertura coverage"

    file_coverage = parse_jacoco_coverage_report(Path(jacoco_xml_path))
    file_hit_count = parse_cobertura_coverage_report(Path(cobertura_xml_path))
    record_file_with_coverage_and_hit_count(
        Path(src_file_path),
        file_coverage[Path(src_file_path).name],
        file_hit_count[Path(src_file_path).name],
        Path(output_path),
    )


if __name__ == "__main__":
    from fire import Fire

    Fire(main)
