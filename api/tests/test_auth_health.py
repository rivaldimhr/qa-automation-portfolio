"""Health check dan autentikasi."""
import re

import pytest

from bugs import known_bug


@pytest.mark.smoke
def test_rb01_ping(api):
    assert api.ping().status_code == 201   # didokumentasikan: 201 Created


@pytest.mark.smoke
def test_rb02_login_admin_mendapat_token(api):
    r = api.auth()
    assert r.status_code == 200
    assert re.fullmatch(r"[0-9a-f]{15}", r.json()["token"])


def test_rb03_setiap_login_mendapat_token_berbeda(api):
    assert api.token() != api.token()


@pytest.mark.parametrize("username, password", [("admin", "salah"), ("bukanadmin", "password123"), ("", "")])
def test_rb04_login_gagal_tidak_memberi_token(api, username, password):
    r = api.auth(username, password)
    assert "token" not in r.json()
    assert r.json() == {"reason": "Bad credentials"}


@known_bug("BUG-09")
def test_rb05_login_gagal_mengembalikan_401(api):
    assert api.auth("admin", "salah").status_code == 401
