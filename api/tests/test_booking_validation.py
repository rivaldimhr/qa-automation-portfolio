"""Validasi data booking (negative testing)."""
import pytest

from api.data import booking, without
from bugs import known_bug

REQUIRED = ["firstname", "lastname", "totalprice", "depositpaid", "bookingdates.checkin", "bookingdates.checkout"]


@pytest.mark.parametrize("field", REQUIRED)
def test_rb30_kolom_wajib_tidak_boleh_hilang(api, field):
    r = api.create(without(booking(), field))
    assert r.status_code >= 400          # ditolak (kode tepatnya: lihat RB-31)


@known_bug("BUG-10")
@pytest.mark.parametrize("field", ["firstname", "bookingdates.checkout"])
def test_rb31_data_tidak_lengkap_dijawab_400(api, field):
    assert api.create(without(booking(), field)).status_code == 400


def test_rb32_put_data_tidak_lengkap_dijawab_400(api, created, token):
    r = api.update(created[0], without(booking(), "lastname"), token=token)
    assert r.status_code == 400


@pytest.mark.parametrize("value", [True, False])
def test_rb33_depositpaid_boolean_tersimpan_benar(api, token, value):
    r = api.create(booking(depositpaid=value))
    assert r.json()["booking"]["depositpaid"] is value
    api.delete(r.json()["bookingid"], token=token)


@known_bug("BUG-11")
def test_rb34_depositpaid_teks_false_tidak_menjadi_true(api, token):
    r = api.create(booking(depositpaid="false"))
    try:
        assert r.status_code == 400 or r.json()["booking"]["depositpaid"] is False
    finally:
        if r.status_code == 200:
            api.delete(r.json()["bookingid"], token=token)


@known_bug("BUG-12")
def test_rb35_checkout_sebelum_checkin_ditolak(api, token):
    r = api.create(booking(bookingdates={"checkin": "2026-12-10", "checkout": "2026-12-01"}))
    if r.status_code == 200:
        api.delete(r.json()["bookingid"], token=token)
    assert r.status_code == 400
