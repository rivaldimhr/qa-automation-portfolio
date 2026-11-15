from playwright.sync_api import expect

from .base import BasePage

KIND = {"progres": "dev", "keuangan": "keu"}


def fake_file(name: str):
    return {"name": name, "mimeType": "application/pdf", "buffer": b"%PDF-1.4 laporan uji"}


class ProjectDetailPage(BasePage):
    def open(self, project_id: int):
        self.go(f"project/{project_id}")
        expect(self.page.locator("#detail h1")).to_be_visible()
        return self

    @property
    def title(self):
        return self.page.locator("#detail h1")

    @property
    def alerts(self):
        return self.page.locator("#detailAlerts")

    @property
    def progress(self):
        return self.page.locator("#detailProgress")

    @property
    def spent(self):
        return self.page.locator("#detailSpent")

    def summary(self):
        return self.page.locator("#detail .facts")

    def send_report(self, kind: str, value=None, file_name: str | None = "laporan.pdf"):
        """kind: 'progres' atau 'keuangan'. file_name=None berarti tanpa file."""
        k = KIND[kind]
        if file_name:
            self.page.set_input_files(f"#up-{k}", fake_file(file_name))
        if value is not None:
            self.page.fill(f"#val-{k}", str(value))
        self.page.click(f"#send-{k}")
        return self

    def report_error(self, kind: str):
        return self.page.locator(f".report-form[data-kind={KIND[kind]}] .err")

    def report_list(self, kind: str):
        return self.page.locator(f"#rep-{KIND[kind]} .files")

    def can_report(self) -> bool:
        return self.page.locator(".report-form").count() > 0
