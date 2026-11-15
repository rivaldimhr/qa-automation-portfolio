"""Klien tipis untuk Restful-Booker. Setiap metode mengembalikan requests.Response apa adanya,
supaya test bisa memeriksa kode status, header, dan body."""
import os

import requests

BASE_URL = os.environ.get("RB_URL", "http://localhost:3001").rstrip("/")
ADMIN = {"username": "admin", "password": "password123"}
BASIC_AUTH = "Basic YWRtaW46cGFzc3dvcmQxMjM="   # admin:password123, didokumentasikan Restful-Booker


class BookerClient:
    def __init__(self, base_url: str = BASE_URL, timeout: float = 15):
        self.base_url = base_url
        self.timeout = timeout
        self.s = requests.Session()
        self.s.headers.update({"Accept": "application/json"})

    def _req(self, method, path, **kw):
        return self.s.request(method, f"{self.base_url}{path}", timeout=self.timeout, **kw)

    def ping(self):
        return self._req("GET", "/ping")

    def auth(self, username=ADMIN["username"], password=ADMIN["password"]):
        return self._req("POST", "/auth", json={"username": username, "password": password})

    def token(self) -> str:
        return self.auth().json()["token"]

    def list_ids(self, **filters):
        return self._req("GET", "/booking", params=filters)

    def get(self, booking_id, accept="application/json"):
        return self._req("GET", f"/booking/{booking_id}", headers={"Accept": accept})

    def create(self, booking: dict):
        return self._req("POST", "/booking", json=booking)

    def update(self, booking_id, booking: dict, token=None, basic=False):
        return self._req("PUT", f"/booking/{booking_id}", json=booking, **self._auth(token, basic))

    def patch(self, booking_id, fields: dict, token=None, basic=False):
        return self._req("PATCH", f"/booking/{booking_id}", json=fields, **self._auth(token, basic))

    def delete(self, booking_id, token=None, basic=False):
        return self._req("DELETE", f"/booking/{booking_id}", **self._auth(token, basic))

    @staticmethod
    def _auth(token, basic):
        if basic:
            return {"headers": {"Authorization": BASIC_AUTH}}
        if token:
            return {"cookies": {"token": token}}
        return {}
