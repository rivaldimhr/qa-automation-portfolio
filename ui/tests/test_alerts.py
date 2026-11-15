"""Peringatan otomatis: dana lebih cepat dari progres, laporan terlambat, revisi, melebihi anggaran.

Aturan (dari spesifikasi KINGSHANDs):
  (a) dana terpakai (% anggaran) - progres >= 15 poin
  (b) laporan perkembangan terakhir > 30 hari
  (c) ada laporan berstatus "Perlu revisi"
  (d) dana terpakai > 100% anggaran            (ditambahkan setelah BUG-03)
"""
import pytest
from playwright.sync_api import expect

from bugs import regression_for
from ui.data import BANK_SAMPAH, CAFE, SLB


@pytest.mark.smoke
def test_kh30_peringatan_awal_di_dashboard(app):
    app.as_role("pj")
    expect(app.dashboard.attention).to_contain_text("Perlu perhatian (2)")
    alerts = " ".join(app.dashboard.alerts())
    assert "Klinik Desa Sukamaju" in alerts and "Dana terpakai 83%" in alerts and "progres pekerjaan baru 66%" in alerts
    assert "Sekolah Luar Biasa Papua" in alerts and "45 hari lalu" in alerts


# Batas aturan (a) pada Cafe: anggaran Rp 500 juta, progres 63%.
# 385 juta = 77% -> selisih 14 poin (tidak ada peringatan); 390 juta = 78% -> 15 poin (peringatan)
@pytest.mark.parametrize("spent, gap, expect_alert", [(385, 14, False), (390, 15, True)])
def test_kh31_batas_selisih_dana_dan_progres(app, spent, gap, expect_alert):
    app.as_role("pj")
    app.detail.open(CAFE[0]).send_report("keuangan", spent)
    check = expect(app.detail.alerts)
    if expect_alert:
        check.to_contain_text(f"Dana terpakai {63 + gap}% dari anggaran, progres pekerjaan baru 63%")
    else:
        check.not_to_contain_text("Dana terpakai lebih cepat")


def test_kh32_laporan_baru_menghapus_peringatan_terlambat(app):
    app.as_role("pj")
    app.detail.open(SLB[0])
    expect(app.detail.alerts).to_contain_text("Laporan perkembangan terlambat")
    app.detail.send_report("progres", 60)
    expect(app.detail.alerts).not_to_contain_text("terlambat")


def test_kh33_filter_perlu_perhatian(app):
    app.as_role("admin")
    assert app.dashboard.stat_value("Proyek perlu perhatian") == 2
    app.dashboard.click_stat("Proyek perlu perhatian")
    assert sorted(app.projects.names()) == ["Klinik Desa Sukamaju", "Sekolah Luar Biasa Papua"]


@regression_for("BUG-03")
@pytest.mark.parametrize("spent, alert", [(120, False), (121, True), (130, True)])
def test_kh34_dana_melebihi_anggaran(app, spent, alert):
    app.as_role("pj")
    app.detail.open(BANK_SAMPAH[0]).send_report("progres", 100).send_report("keuangan", spent)
    if alert:
        expect(app.detail.alerts).to_contain_text("Dana terpakai melebihi anggaran")
    else:
        expect(app.detail.alerts).not_to_contain_text("melebihi anggaran")
