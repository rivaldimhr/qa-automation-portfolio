"""Fixture UI: server lokal untuk KINGSHANDs, browser Chromium, dan screenshot saat gagal.

Aplikasi yang diuji:
  KH_URL=https://...        uji situs yang sudah online, atau
  KH_DIR=path/ke/web        sajikan folder web KINGSHANDs secara lokal (bawaan: sut/KINGSHANDs/web)
"""
import functools
import http.server
import os
import re
import threading
from pathlib import Path

import pytest
from playwright.sync_api import sync_playwright

from ui.pages import App

ROOT = Path(__file__).resolve().parent.parent
SHOTS = ROOT / "reports" / "screenshots"


@pytest.fixture(scope="session")
def base_url():
    if os.environ.get("KH_URL"):
        yield os.environ["KH_URL"]
        return
    web = Path(os.environ.get("KH_DIR", ROOT / "sut" / "KINGSHANDs" / "web"))
    if not (web / "index.html").exists():
        pytest.exit(f"Aplikasi tidak ditemukan di {web}. Set KH_DIR atau KH_URL (lihat README).", 2)
    handler = functools.partial(http.server.SimpleHTTPRequestHandler, directory=str(web))
    handler.log_message = lambda *a: None
    server = http.server.ThreadingHTTPServer(("127.0.0.1", 0), handler)
    threading.Thread(target=server.serve_forever, daemon=True).start()
    yield f"http://127.0.0.1:{server.server_address[1]}/"
    server.shutdown()


@pytest.fixture(scope="session")
def browser():
    with sync_playwright() as p:
        b = p.chromium.launch()
        yield b
        b.close()


def _new_app(browser, base_url, request, **context_args):
    ctx = browser.new_context(locale="id-ID", **context_args)
    page = ctx.new_page()
    page.set_default_timeout(7000)
    errors = []
    page.on("pageerror", lambda e: errors.append(str(e)))
    request.node._pages = getattr(request.node, "_pages", []) + [page]
    app = App(page, base_url)
    app.js_errors = errors
    return ctx, app


@pytest.fixture
def app(browser, base_url, request):
    """Satu tab browser baru (data contoh bersih) untuk setiap test."""
    ctx, app = _new_app(browser, base_url, request, viewport={"width": 1280, "height": 860})
    yield app
    ctx.close()
    assert app.js_errors == [], f"Error JavaScript di halaman: {app.js_errors}"


@pytest.fixture
def mobile_app(browser, base_url, request):
    ctx, app = _new_app(browser, base_url, request, viewport={"width": 390, "height": 844},
                        is_mobile=True, has_touch=True, device_scale_factor=2)
    yield app
    ctx.close()


@pytest.hookimpl(hookwrapper=True)
def pytest_runtest_makereport(item, call):
    outcome = yield
    report = outcome.get_result()
    if report.when != "call" or not report.failed:
        return
    SHOTS.mkdir(parents=True, exist_ok=True)
    for i, page in enumerate(getattr(item, "_pages", [])):
        if page.is_closed():
            continue
        name = re.sub(r"[^\w.-]+", "_", item.nodeid)[-120:] + f"_{i}.png"
        page.screenshot(path=str(SHOTS / name), full_page=True)
        html = item.config.pluginmanager.getplugin("html")
        if html:
            extras = getattr(report, "extras", [])
            extras.append(html.extras.image(f"screenshots/{name}"))
            report.extras = extras
