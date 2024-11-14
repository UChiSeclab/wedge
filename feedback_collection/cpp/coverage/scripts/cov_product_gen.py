import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Dict, Literal, Tuple
from .cov_xml_parser import parse_cobertura_coverage_report

def record_file_with_coverage_and_hit_count_product(
    src_file: Path,
    slow_file_coverage: Dict[
        str, Tuple[int, Literal["COVERED", "NOT_COVERED", "PARTIALLY_COVERED"]]
    ],
    fast_file_coverage: Dict[
        str, Tuple[int, Literal["COVERED", "NOT_COVERED", "PARTIALLY_COVERED"]]
    ],
    output_file: Path,
    info_line_end: bool = False,
):
    file_with_coverage_lines = []
    if output_file == Path(""):
        output_file = src_file.with_suffix(f".cpp.cov")

    for line_no, line in enumerate(src_file.read_text().splitlines(), start=1):
        slow_hits, slow_coverage_status = slow_file_coverage.get(line_no, (0, None))
        fast_hits, fast_coverage_status = fast_file_coverage.get(line_no, (0, None))
        # only use hit count
        if info_line_end:
            line = line + f" /* line {line_no} slow_hit_count: {slow_hits}, fast_hit_count: {fast_hits} */"
        else:
            line = f"/* line {line_no} slow_hit_count: {slow_hits}, fast_hit_count: {fast_hits} */ " + line
        file_with_coverage_lines.append(line)

    output_file.write_text("\n".join(file_with_coverage_lines))

def main(
    src_file_path: str,
    slow_cobertura_xml_path: str = None,
    fast_cobertura_xml_path: str = None,
    output_path: str = "",
    info_line_end: bool = False,
):
    slow_file_coverage = parse_cobertura_coverage_report(Path(slow_cobertura_xml_path))
    fast_file_coverage = parse_cobertura_coverage_report(Path(fast_cobertura_xml_path))

    record_file_with_coverage_and_hit_count_product(
        Path(src_file_path),
        slow_file_coverage[Path(src_file_path).name],
        fast_file_coverage[Path(src_file_path).name],
        Path(output_path),
        info_line_end=info_line_end,
    )


if __name__ == "__main__":
    from fire import Fire

    Fire(main)
