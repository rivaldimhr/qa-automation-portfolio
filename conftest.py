import sys
from pathlib import Path

# supaya `import bugs` dan `from ui.pages import ...` bekerja dari mana pun pytest dijalankan
sys.path.insert(0, str(Path(__file__).resolve().parent))
