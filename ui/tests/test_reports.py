"""Laporan progres dan keuangan oleh PJ, serta pemeriksaan oleh admin."""
import pytest
from playwright.sync_api import expect

from bugs import known_bug, regression_for
from ui.data import BANK_SAMPAH, CAFE, PERPUSTAKAAN


@pytest.mark.smoke
def test_kh20_laporan_progres_terkirim_ke_admin(app):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("progres", 70, "progres-nov.pdf")
    expect(app.detail.toast).to_contain_text("Laporan perkembangan terkirim")
    expect(app.detail.progress).to_have_text("70%")
    expect(app.detail.report_list("progres")).to_contain_text("Menunggu diperiksa")
    app.switch_to("admin")
    app.reports.open()
    expect(app.reports.row("progres-nov.pdf")).to_contain_text("Progres 70%")


@pytest.mark.parametrize("value, file_name, message", [
    (70, None, "Pilih file laporan dulu."),
    (None, "a.pdf", "Isi progres antara 0 dan 100."),
    (101, "a.pdf", "Isi progres antara 0 dan 100."),
    (-1, "a.pdf", "Isi progres antara 0 dan 100."),
    (62, "a.pdf", "Progres tidak boleh turun dari 63%."),
])
def test_kh21_validasi_laporan_progres(app, value, file_name, message):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("progres", value, file_name)
    expect(app.detail.report_error("progres")).to_have_text(message)


@pytest.mark.parametrize("value, accepted", [(63, True), (100, True), (0, False)])
def test_kh22_batas_nilai_progres(app, value, accepted):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("progres", value)
    if accepted:
        expect(app.detail.progress).to_have_text(f"{value}%")
    else:
        expect(app.detail.report_error("progres")).not_to_have_text("")


@pytest.mark.parametrize("value, message", [
    (289, "Total dana terpakai tidak boleh lebih kecil dari Rp 290 juta."),
    (-10, "Isi dana terpakai dengan angka 0 atau lebih."),
])
def test_kh23_validasi_laporan_keuangan(app, value, message):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("keuangan", value)
    expect(app.detail.report_error("keuangan")).to_have_text(message)


def test_kh24_proyek_selesai_tidak_menerima_laporan(app):
    app.as_role("pj")
    app.detail.open(PERPUSTAKAAN[0])
    assert not app.detail.can_report()
    expect(app.page.locator("#detail")).to_contain_text("Proyek sudah selesai")


def test_kh25_admin_menerima_laporan_dan_pj_diberi_tahu(app):
    app.as_role("admin")
    app.reports.open().accept("Progres-Unit-Pilah.pdf")
    expect(app.reports.queue).not_to_contain_text("Progres-Unit-Pilah.pdf")
    expect(app.reports.done).to_contain_text("Progres-Unit-Pilah.pdf")
    app.switch_to("pj")
    expect(app.dashboard.open_notifications()).to_contain_text("Laporan perkembangan Bank Sampah Cikutra diterima admin")


def test_kh26_minta_revisi_wajib_catatan_dan_muncul_di_pj(app):
    app.as_role("admin")
    app.reports.open().request_revision("Progres-Unit-Pilah.pdf", "")
    expect(app.reports.row_error("Progres-Unit-Pilah.pdf")).to_have_text("Tulis apa yang perlu direvisi.")
    app.reports.request_revision("Progres-Unit-Pilah.pdf", "Lampirkan foto unit pilah")
    app.switch_to("pj")
    app.detail.open(BANK_SAMPAH[0])
    expect(app.detail.alerts).to_contain_text("Laporan perkembangan perlu revisi")
    expect(app.detail.alerts).to_contain_text("Lampirkan foto unit pilah")


@regression_for("BUG-01")
def test_kh27_progres_bisa_dikoreksi_setelah_revisi(app):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("progres", 95, "progres-salah.pdf")
    app.switch_to("admin")
    app.reports.open().request_revision("progres-salah.pdf", "Progres sebenarnya 70%")
    app.switch_to("pj")
    app.detail.open(CAFE[0]).send_report("progres", 70, "progres-benar.pdf")
    expect(app.detail.report_error("progres")).to_have_text("")
    expect(app.detail.progress).to_have_text("70%")
    # koreksi 70% kini menjadi batas bawah laporan berikutnya
    app.detail.send_report("progres", 60, "terlalu-rendah.pdf")
    expect(app.detail.report_error("progres")).to_have_text("Progres tidak boleh turun dari 70%.")


@regression_for("BUG-01")
def test_kh27b_koreksi_tidak_boleh_di_bawah_nilai_yang_sudah_diterima(app):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("progres", 95, "progres-salah.pdf")
    app.switch_to("admin")
    app.reports.open().request_revision("progres-salah.pdf", "Terlalu tinggi")
    app.switch_to("pj")
    app.detail.open(CAFE[0]).send_report("progres", 60, "progres-koreksi.pdf")   # 63% sudah diterima admin
    expect(app.detail.report_error("progres")).to_have_text("Progres tidak boleh turun dari 63%.")


@regression_for("BUG-02")
def test_kh28_peringatan_revisi_hilang_setelah_laporan_diganti(app):
    app.as_role("admin")
    app.reports.open().request_revision("Progres-Unit-Pilah.pdf", "Lampirkan foto")
    app.switch_to("pj")
    app.detail.open(BANK_SAMPAH[0]).send_report("progres", 90, "Progres-Unit-Pilah-v2.pdf")
    expect(app.detail.alerts).not_to_contain_text("perlu revisi")
    expect(app.detail.report_list("progres")).to_contain_text("Diganti laporan baru")
    app.switch_to("admin")
    app.reports.open().accept("Progres-Unit-Pilah-v2.pdf")
    app.detail.open(BANK_SAMPAH[0])
    expect(app.detail.alerts).not_to_contain_text("perlu revisi")


@known_bug("BUG-07")
def test_kh29_progres_harus_bilangan_bulat(app):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("progres", "63.5")
    expect(app.detail.report_error("progres")).not_to_have_text("", timeout=1500)
