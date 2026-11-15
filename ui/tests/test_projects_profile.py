"""Daftar proyek, pencarian, profil, notifikasi, dan tampilan ponsel."""
import pytest
from playwright.sync_api import expect

from bugs import known_bug


@pytest.mark.parametrize("query, expected", [
    ("cafe", ["Cafe Le' de Jainero"]),
    ("BANDUNG", ["Cafe Le' de Jainero", "Bank Sampah Cikutra", "Kebun Hidroponik Sekolah", "Wisata Alam Curug Telu"]),
    ("garut", ["Perpustakaan Keliling Garut"]),
])
def test_kh40_cari_berdasarkan_nama_atau_lokasi(app, query, expected):
    app.as_role("pj")
    app.projects.open().search(query)
    assert sorted(app.projects.names()) == sorted(expected)


def test_kh41_pencarian_tanpa_hasil(app):
    app.as_role("pj")
    app.projects.open().search("zzz")
    expect(app.projects.empty_message).to_have_text("Tidak ada proyek yang cocok.")


@known_bug("BUG-06")
def test_kh42_pencarian_tidak_cocok_lintas_kolom(app):
    app.as_role("pj")
    app.projects.open().search("cikutracikutra")   # akhir nama + awal lokasi, bukan teks yang sebenarnya ada
    expect(app.projects.empty_message).to_be_visible(timeout=1500)


def test_kh43_ubah_nama_mengubah_sapaan(app):
    app.as_role("pj")
    app.profile.open().edit(nama="Rina Sari")
    expect(app.profile.toast).to_contain_text("Profil disimpan")
    app.dashboard.nav("Dashboard")
    expect(app.dashboard.greeting).to_have_text("Halo, Rina")


@pytest.mark.parametrize("email", ["bukan-email", "a@", "@kingshand.id", ""])
def test_kh44_email_profil_harus_valid(app, email):
    app.as_role("pj")
    app.profile.open().edit(email=email)
    expect(app.profile.error).to_have_text("Nama dan email yang valid wajib diisi.")


@known_bug("BUG-08")
def test_kh45_telepon_hanya_angka(app):
    app.as_role("pj")
    app.profile.open().edit(telepon="bukan nomor")
    expect(app.profile.error).not_to_have_text("", timeout=1500)


def test_kh46_notifikasi_ditandai_dibaca(app):
    app.as_role("admin")
    expect(app.dashboard.unread_badge()).to_have_text("2")
    app.dashboard.open_notifications()
    expect(app.dashboard.unread_badge()).to_be_hidden()
    app.page.reload()
    expect(app.dashboard.unread_badge()).to_be_hidden()


def test_kh47_data_tersimpan_setelah_muat_ulang_dan_bisa_direset(app):
    app.as_role("pj")
    app.profile.open().edit(nama="Rina Sari")
    app.page.reload()
    expect(app.dashboard.greeting).to_have_text("Halo, Rina")
    app.page.click("#resetBtn")
    expect(app.dashboard.greeting).to_have_text("Halo, Dinda")


@pytest.mark.parametrize("route", ["dashboard", "projects", "proposal", "project/1", "profile"])
def test_kh48_tampilan_ponsel_tanpa_gulir_samping(mobile_app, route):
    mobile_app.as_role("pj")
    mobile_app.dashboard.go(route)
    mobile_app.page.wait_for_timeout(200)
    assert not mobile_app.dashboard.has_horizontal_scroll(), route
