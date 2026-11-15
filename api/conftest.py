import pytest
import requests

from api.client import BASE_URL, BookerClient
from api.data import booking


@pytest.fixture(scope="session")
def api():
    client = BookerClient()
    try:
        client.ping()
    except requests.RequestException as e:
        pytest.exit(f"Restful-Booker tidak bisa dihubungi di {BASE_URL} ({e}). Lihat README untuk menjalankannya.", 2)
    return client


@pytest.fixture(scope="session")
def token(api):
    return api.token()


@pytest.fixture
def created(api, token):
    """Booking baru untuk satu test; dihapus lagi sesudahnya (jika masih ada)."""
    data = booking()
    r = api.create(data)
    assert r.status_code == 200, r.text
    booking_id = r.json()["bookingid"]
    yield booking_id, data
    api.delete(booking_id, token=token)
