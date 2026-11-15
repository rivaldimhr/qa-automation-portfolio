"""Data uji. Nilai seed berasal dari data contoh KINGSHANDs (lihat docs/test-plan.md)."""
import datetime

MONTHS = ["Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des"]


def month_offset(n: int) -> datetime.date:
    """Tanggal 1 pada bulan ke-n dari sekarang (n bisa negatif)."""
    today = datetime.date.today()
    y, m = divmod(today.month - 1 + n, 12)
    return datetime.date(today.year + y, m + 1, 1)


def month_value(n: int) -> str:
    return month_offset(n).strftime("%Y-%m")


def month_label(n: int) -> str:
    d = month_offset(n)
    return f"{MONTHS[d.month - 1]} {d.year}"


def proposal(**overrides):
    data = {
        "judul": "Taman Baca Anak Cimahi",
        "lokasi": "Cimahi",
        "target": "Siswa SD",
        "dana": 75,
        "mulai": month_value(2),
        "deskripsi": "Taman baca dengan 500 buku untuk anak SD di Cimahi.",
    }
    data.update(overrides)
    return data


# Proyek contoh (id, nama, anggaran Rp juta, progres terakhir %, dana terpakai Rp juta)
CAFE = (1, "Cafe Le' de Jainero", 500, 63, 290)
SLB = (2, "Sekolah Luar Biasa Papua", 1000, 52, 600)
BANK_SAMPAH = (4, "Bank Sampah Cikutra", 120, 90, 100)
KLINIK = (6, "Klinik Desa Sukamaju", 350, 66, 290)
PERPUSTAKAAN = (5, "Perpustakaan Keliling Garut", 200, 100, 195)
