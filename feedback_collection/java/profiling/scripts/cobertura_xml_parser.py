import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Dict, Literal


def parse_xml_coverage_report(
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


def display_file_with_hit_count(
    src_file: Path, file_hit_count: Dict[int, int], output_file: Path
):
    file_with_coverage_lines = []
    if output_file == Path(""):
        output_file = src_file.with_suffix(f".java.cov")

    for line_no, line in enumerate(src_file.read_text().splitlines(), start=1):
        hit_count = file_hit_count.get(line_no, None)
        if hit_count == None:
            pass
        elif hit_count == 0:
            line = f"/* line {line_no} HIT_COUNT 0 */ " + line
        else:
            line = f"/* line {line_no} HIT_COUNT {hit_count} */ " + line
        file_with_coverage_lines.append(line)

    output_file.write_text("\n".join(file_with_coverage_lines))


def main(src_file_path: str, coverage_xml_path: str, output_path: str = ""):
    all_file_coverage = parse_xml_coverage_report(Path(coverage_xml_path))
    # print(all_file_coverage)
    display_file_with_hit_count(
        Path(src_file_path),
        all_file_coverage[Path(src_file_path).name],
        Path(output_path),
    )


if __name__ == "__main__":
    from fire import Fire

    Fire(main)
