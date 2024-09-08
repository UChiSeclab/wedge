from pathlib import Path
import subprocess
from typing import List, Tuple
from traceback import print_exc
from pympler.asizeof import asizeof

MAX_FACTOR = 26 # we follow the setting in evalperf
CURATION_TIMEOUT_PER_TEST_SECOND = 20 # we follow the setting in evalperf

debug = False

def sample_inputs(generator_file: Path) -> Tuple[List[str], List[int], bool]:
    """code adapted from evalperf"""
    
    gen_inputs = []
    selected_scales = []
    well_defined_exit = True

    # get max scale
    for fac in range(1, MAX_FACTOR+1):
        scale = 2**fac
        print(f"[INPUT GEN] scale=2**{fac}")
        
        try:
            res = subprocess.run(
                ["python", generator_file.as_posix(), str(scale)],
                capture_output=True,
                text=True,
                timeout=20, #CURATION_TIMEOUT_PER_TEST_SECOND,
                check=True
            )
            res.check_returncode()
            gen_input = res.stdout.strip()
            
            if gen_input == "":
                print(f"[INPUT GEN] Empty input at scale=2**{fac}")
                break

            # integers should stay in the range of 64-bit
            if any(
                arg.isdigit() and not (-(2**63) <= int(arg) < 2**63)
                for arg in gen_input.split()
            ):
                print(f"[INPUT GEN] Int overflow against 64bit")
                break
            # stop here if the input is of 64M.
            INPUT_LIMIT_MB = 64
            if asizeof(gen_input) > 1024 * 1024 * INPUT_LIMIT_MB:
                print(f"[INPUT GEN] Size > {INPUT_LIMIT_MB}MB")
                break

            gen_inputs.append(gen_input)
            selected_scales.append(scale)

        except subprocess.TimeoutExpired as e:
            print(f"[INPUT_GEN] Timeout at scale=2**{fac}: {e}")
            break

        except MemoryError as e:
            print(f"[INPUT_GEN] MemoryError at scale=2**{fac}: {e}")
            break

        except subprocess.CalledProcessError as e:
            print(f"[INPUT_GEN] CalledProcessError at scale=2**{fac}: {e}")
            break
        except Exception as e:
            print(f"[INPUT_GEN] Exception at scale=2**{fac}: {e}")
            print_exc()
            well_defined_exit = False
            break

    if debug:
        print(f"[INPUT GEN] Selected scales: {selected_scales}")
        print(f"[INPUT GEN] Number of samples: {len(gen_inputs)}")

    return gen_inputs, selected_scales, well_defined_exit
