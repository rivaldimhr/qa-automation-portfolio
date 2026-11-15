"""Membuat, membaca, mengubah, dan menghapus booking."""
import pytest
from jsonschema import validate

from api.data import BOOKING_SCHEMA, CREATED_SCHEMA, booking, without
from bugs import known_bug


@pytest.mark.smoke
def test_rb10_buat_booking_lalu_baca_kembali(api, token):
    data = booking()
    r = api.create(data)
    assert r.status_code == 200
    body = r.json()
    validate(body, CREATED_SCHEMA)
    assert body["booking"] == data
    got = api.get(body["bookingid"])
    assert got.status_code == 200
    validate(got.json(), BOOKING_SCHEMA)
    assert got.json() == data
    api.delete(body["bookingid"], token=token)


def test_rb11_additionalneeds_opsional(api, token):
    data = without(booking(), "additionalneeds")
    r = api.create(data)
    assert r.status_code == 200
    assert "additionalneeds" not in r.json()["booking"]
    api.delete(r.json()["bookingid"], token=token)


def test_rb12_nama_dipangkas_spasinya(api, token):
    r = api.create(booking(firstname="  Rina  "))
    assert r.json()["booking"]["firstname"] == "Rina"
    api.delete(r.json()["bookingid"], token=token)


def test_rb13_cari_booking_berdasarkan_nama(api, created):
    booking_id, data = created
    r = api.list_ids(firstname=data["firstname"], lastname=data["lastname"])
    assert r.status_code == 200
    assert {"bookingid": booking_id} in r.json()
    assert len(r.json()) == 1


def test_rb14_booking_tidak_ada_404(api):
    assert api.get(99999999).status_code == 404


@pytest.mark.parametrize("accept, status, marker", [
    ("application/json", 200, '"firstname"'),
    ("application/xml", 200, "<firstname>"),
    ("*/*", 200, '"firstname"'),
    ("text/plain", 418, ""),        # perilaku terdokumentasi: format tidak didukung -> 418
])
def test_rb15_format_respons_mengikuti_header_accept(api, created, accept, status, marker):
    r = api.get(created[0], accept=accept)
    assert r.status_code == status
    assert marker in r.text


@pytest.mark.parametrize("auth", ["token", "basic"])
def test_rb20_ubah_booking_dengan_token_atau_basic_auth(api, created, token, auth):
    booking_id, data = created
    changed = {**data, "firstname": "Dinda", "totalprice": 275}
    r = api.update(booking_id, changed, token=token if auth == "token" else None, basic=auth == "basic")
    assert r.status_code == 200
    assert r.json() == changed
    assert api.get(booking_id).json() == changed


def test_rb21_ubah_sebagian_dengan_patch(api, created, token):
    booking_id, data = created
    r = api.patch(booking_id, {"additionalneeds": "Late checkout"}, token=token)
    assert r.status_code == 200
    assert r.json() == {**data, "additionalneeds": "Late checkout"}


@pytest.mark.parametrize("method", ["update", "patch", "delete"])
def test_rb22_tanpa_login_ditolak_403(api, created, method):
    booking_id, data = created
    args = {"update": (booking_id, data), "patch": (booking_id, {"firstname": "X"}), "delete": (booking_id,)}[method]
    r = getattr(api, method)(*args)
    assert r.status_code == 403
    assert api.get(booking_id).json() == data      # data tidak berubah


def test_rb23_token_palsu_ditolak(api, created):
    assert api.delete(created[0], token="000000000000000").status_code == 403


@pytest.mark.smoke
def test_rb24_hapus_booking(api, token):
    booking_id = api.create(booking()).json()["bookingid"]
    r = api.delete(booking_id, token=token)
    assert r.status_code in (200, 201, 204)
    assert api.get(booking_id).status_code == 404


@known_bug("BUG-13")
def test_rb25_hapus_berhasil_bukan_201_created(api, token):
    booking_id = api.create(booking()).json()["bookingid"]
    assert api.delete(booking_id, token=token).status_code in (200, 204)


@known_bug("BUG-14")
@pytest.mark.parametrize("method", ["update", "delete"])
def test_rb26_booking_tidak_ada_404_saat_diubah_atau_dihapus(api, token, method):
    args = (99999999, booking()) if method == "update" else (99999999,)
    assert getattr(api, method)(*args, token=token).status_code == 404
