"""Ringkas hasil junit ketiga suite ke reports/index.html dan reports/summary.json."""
import html
import json
import sys
import xml.etree.ElementTree as ET
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT))
from bugs import BUGS, JIRA  # noqa: E402

OUT = Path(sys.argv[1] if len(sys.argv) > 1 else ROOT / "reports")
SUITES = [("ui", "UI: KINGSHANDs (Playwright)"), ("api", "API: Restful-Booker (pytest)"), ("newman", "API: Restful-Booker (Postman/Newman)")]


def summarize(junit: Path):
    if not junit.exists():
        return None
    root = ET.parse(junit).getroot()
    s = {"tests": 0, "passed": 0, "failed": 0, "xfailed": 0, "skipped": 0, "failures": []}
    for tc in root.iter("testcase"):
        s["tests"] += 1
        name = f"{tc.get('classname', '')}::{tc.get('name')}"
        failed = tc.find("failure") is not None or tc.find("error") is not None
        skip = tc.find("skipped")
        if failed:
            s["failed"] += 1
            s["failures"].append(name)
        elif skip is not None and (skip.get("type") == "pytest.xfail" or "xfail" in (skip.get("message") or "")):
            s["xfailed"] += 1
        elif skip is not None:
            s["skipped"] += 1
        else:
            s["passed"] += 1
    return s


def main():
    results = {key: summarize(OUT / key / "junit.xml") for key, _ in SUITES}
    stamp = datetime.now(timezone.utc).strftime("%Y-%m-%d %H:%M UTC")
    (OUT / "summary.json").write_text(json.dumps({"generated": stamp, "suites": results}, indent=2))
    rows = []
    for key, label in SUITES:
        r = results[key]
        if not r:
            rows.append(f"<tr><td>{label}</td><td colspan=5>tidak dijalankan</td></tr>")
            continue
        status = "lulus" if r["failed"] == 0 else "gagal"
        rows.append(
            f"<tr class={status}><td><a href='{key}/index.html'>{label}</a></td><td>{r['tests']}</td><td>{r['passed']}</td>"
            f"<td>{r['xfailed']}</td><td>{r['failed']}</td><td>{status}</td></tr>")
    bugs = "".join(
        f"<tr><td>{b}</td><td><a href='{JIRA}{k}'>{k}</a></td><td>{'KINGSHANDs' if int(b[4:]) <= 8 else 'Restful-Booker'}</td>"
        f"<td>{html.escape(t)}</td><td>{'diperbaiki' if st == 'fixed' else 'terbuka'}</td></tr>"
        for b, (k, st, t) in BUGS.items())
    page = f"""<!doctype html><html lang="id"><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Laporan QA</title>
<style>body{{font:16px/1.5 system-ui,sans-serif;max-width:960px;margin:40px auto;padding:0 20px;color:#1d2433}}
table{{border-collapse:collapse;width:100%;margin:12px 0 32px}}td,th{{padding:8px 10px;border-bottom:1px solid #e3e6ec;text-align:left}}
th{{font-size:13px;color:#5b6475}}tr.gagal td:last-child{{color:#b42318;font-weight:600}}tr.lulus td:last-child{{color:#067647;font-weight:600}}
td:first-child,td:nth-child(2){{white-space:nowrap}}a{{color:#1849a9}}small{{color:#5b6475}}</style>
<h1>Laporan QA</h1><small>Dibuat {stamp}. Xfail = test untuk bug yang masih terbuka; gagal sesuai dugaan.</small>
<h2>Hasil test</h2><table><tr><th>Suite</th><th>Total</th><th>Lulus</th><th>Xfail (bug terbuka)</th><th>Gagal</th><th>Status</th></tr>{''.join(rows)}</table>
<h2>Bug</h2><table><tr><th>ID</th><th>Jira</th><th>Aplikasi</th><th>Ringkasan</th><th>Status</th></tr>{bugs}</table>
<p><a href="https://github.com/rivaldimhr/qa-automation-portfolio">Repositori</a></p></html>"""
    (OUT / "index.html").write_text(page)
    print(json.dumps(results, indent=2))


if __name__ == "__main__":
    main()
