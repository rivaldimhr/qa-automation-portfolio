"""Login dan hak akses per peran (Story: Login & hak akses)."""
import pytest
from playwright.sync_api import expect


@pytest.mark.smoke
@pytest.mark.parametrize("role, greeting, menu", [
    ("pj", "Halo, Dinda", ["Dashboard", "Proyek", "Proposal"]),
    ("admin", "Halo, Admin", ["Dashboard", "Evaluasi proposal", "Laporan masuk", "Proyek"]),
])
def test_kh01_login_berhasil_menampilkan_menu_sesuai_peran(app, role, greeting, menu):
    app.as_role(role)
    expect(app.dashboard.greeting).to_have_text(greeting)
    assert app.dashboard.nav_labels() == menu


@pytest.mark.parametrize("email, password", [
    ("demo@kingshand.id", "salah"),
    ("tidakada@kingshand.id", "demo123"),
    ("", ""),
    ("demo@kingshand.id", "DEMO123"),       # kata sandi peka huruf besar/kecil
    ("demo@kingshand.id", " demo123"),      # spasi di kata sandi tidak dipangkas
])
def test_kh02_login_gagal_dengan_data_salah(app, email, password):
    app.login.open().login(email, password)
    expect(app.login.error).to_contain_text("Email atau kata sandi salah")
    expect(app.page.locator("#app")).to_be_hidden()


def test_kh03_email_tidak_peka_huruf_besar_dan_spasi(app):
    app.login.open().login("  DEMO@KingsHand.id ", "demo123")
    expect(app.dashboard.greeting).to_have_text("Halo, Dinda")


def test_kh04_tombol_akun_demo_mengisi_form(app):
    app.login.open().use_demo_account("admin")
    assert app.login.field_values() == ("admin@kingshand.id", "admin123")


@pytest.mark.parametrize("role, forbidden", [
    ("admin", "proposal"), ("admin", "profile"), ("pj", "review"), ("pj", "reports"),
])
def test_kh05_halaman_peran_lain_tidak_bisa_dibuka_lewat_url(app, role, forbidden):
    app.as_role(role)
    app.dashboard.go(forbidden)
    expect(app.page.locator("#p-dashboard")).to_be_visible()
    expect(app.page.locator(f"#p-{forbidden}")).to_be_hidden()


def test_kh06_keluar_menutup_aplikasi(app):
    app.as_role("pj")
    app.dashboard.logout()
    app.page.reload()
    expect(app.page.locator("#loginForm")).to_be_visible()
    expect(app.page.locator("#app")).to_be_hidden()
