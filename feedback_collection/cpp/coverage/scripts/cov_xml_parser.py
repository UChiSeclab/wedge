import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Dict, Literal, Tuple


def parse_cobertura_coverage_report(
    xml_file: Path,
) -> Dict[
    str, Dict[int, Tuple[int, Literal["COVERED", "NOT_COVERED", "PARTIALLY_COVERED"]]]
]:
    # Parse the XML
    root = ET.parse(xml_file).getroot()

    src_file_coverage = {}

    # Iterate over each line in the class element
    for clazz in root.iter("class"):
        file_name = clazz.get("filename")
        if file_name not in src_file_coverage:
            src_file_coverage[file_name] = {}

        for line in clazz.iter("line"):
            line_nr = int(line.get("number"))
            hits = int(line.get("hits"))
            branch = line.get("branch")
            if hits == 0:
                coverage_status = "NOT_COVERED"
            else:
                if branch == "true":
                    condition_coverage = line.get("condition-coverage")
                    if condition_coverage.split(" ")[0] == "100%":
                        coverage_status = "COVERED"
                    else:
                        coverage_status = "PARTIALLY_COVERED"
                else:
                    coverage_status = "COVERED"

            src_file_coverage[file_name][line_nr] = (hits, coverage_status)

    return src_file_coverage


def record_file_with_coverage_and_hit_count(
    src_file: Path,
    file_coverage: Dict[
        str, Tuple[int, Literal["COVERED", "NOT_COVERED", "PARTIALLY_COVERED"]]
    ],
    output_file: Path,
):
    file_with_coverage_lines = []
    if output_file == Path(""):
        output_file = src_file.with_suffix(f".cpp.cov")

    for line_no, line in enumerate(src_file.read_text().splitlines(), start=1):
        hits, coverage_status = file_coverage.get(line_no, (0, None))
        if coverage_status:
            if coverage_status == "NOT_COVERED":
                line = f"/* line {line_no} {coverage_status} */ " + line
            else:
                line = (
                    f"/* line {line_no} {coverage_status}, hit count: {hits} */ " + line
                )
        else:
            line = f"/* line {line_no} */ " + line
        file_with_coverage_lines.append(line)

    output_file.write_text("\n".join(file_with_coverage_lines))


def main(
    src_file_path: str,
    cobertura_xml_path: str = None,
    output_path: str = "",
):
    file_coverage = parse_cobertura_coverage_report(Path(cobertura_xml_path))
    record_file_with_coverage_and_hit_count(
        Path(src_file_path),
        file_coverage[Path(src_file_path).name],
        Path(output_path),
    )


if __name__ == "__main__":
    from fire import Fire

    Fire(main)
