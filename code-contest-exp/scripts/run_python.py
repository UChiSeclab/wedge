import os
import subprocess
import json
import re
import time
from queue import Queue
import threading
from scripts import *
from tqdm import tqdm
from pathlib import Path


def run_python(
    solution_dir: Path, class_name: str, idir: Path, odir: Path, time_limit: int = 1
):
    pass
