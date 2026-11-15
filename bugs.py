"""Daftar bug yang ditemukan, satu sumber untuk test dan dokumentasi.

Bug yang masih terbuka ditandai di test dengan `known_bug(...)`: test menulis perilaku yang BENAR,
dan ditandai xfail strict. Jika bug diperbaiki, test akan lulus dan pytest melaporkannya sebagai
XPASS (gagal), sehingga penanda ini harus dilepas dan bug ditutup di Jira.
"""
import pytest

JIRA = "https://mochiii762025.atlassian.net/browse/"

BUGS = {
    # KINGSHANDs web
    "BUG-01": ("SCRUM-23", "fixed", "Progres yang terlalu tinggi tidak bisa dikoreksi setelah admin meminta revisi"),
    "BUG-02": ("SCRUM-24", "fixed", "Peringatan 'perlu revisi' tidak hilang setelah laporan pengganti diterima"),
    "BUG-03": ("SCRUM-25", "fixed", "Dana terpakai melebihi anggaran ditandai 'Proyek ini aman'"),
    "BUG-04": ("SCRUM-26", "fixed", "Rencana bulan mulai diganti bulan persetujuan"),
    "BUG-05": ("SCRUM-27", "open", "Proposal menerima rencana mulai di masa lalu"),
    "BUG-06": ("SCRUM-28", "open", "Pencarian cocok dengan gabungan nama dan lokasi"),
    "BUG-07": ("SCRUM-29", "open", "Progres laporan menerima angka desimal"),
    "BUG-08": ("SCRUM-30", "open", "No. telepon profil menerima huruf"),
    # Restful-Booker API
    "BUG-09": ("SCRUM-31", "open", "Login gagal mengembalikan 200 OK, bukan 401"),
    "BUG-10": ("SCRUM-32", "open", "Data booking tidak lengkap dijawab 500 lewat POST, tapi 400 lewat PUT"),
    "BUG-11": ("SCRUM-33", "open", "depositpaid \"false\" (teks) tersimpan sebagai true"),
    "BUG-12": ("SCRUM-34", "open", "Checkout sebelum checkin diterima"),
    "BUG-13": ("SCRUM-35", "open", "DELETE berhasil mengembalikan 201 Created"),
    "BUG-14": ("SCRUM-36", "open", "Ubah/hapus booking yang tidak ada mengembalikan 405, bukan 404"),
}


def known_bug(bug_id):
    """Dekorator untuk test yang menulis perilaku benar dari bug yang masih terbuka."""
    key, status, title = BUGS[bug_id]
    assert status == "open", f"{bug_id} sudah diperbaiki; hapus penanda known_bug"

    def decorate(fn):
        fn = pytest.mark.known_bug(fn)
        return pytest.mark.xfail(strict=True, reason=f"{bug_id} ({key}): {title}")(fn)
    return decorate


def regression_for(bug_id):
    """Dekorator untuk test regresi dari bug yang sudah diperbaiki."""
    key, status, title = BUGS[bug_id]
    assert status == "fixed", f"{bug_id} belum diperbaiki; pakai known_bug"

    def decorate(fn):
        fn.__doc__ = f"{bug_id} ({key}): {title}. " + (fn.__doc__ or "")
        return pytest.mark.regression(fn)
    return decorate
