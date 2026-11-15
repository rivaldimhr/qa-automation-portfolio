from .base import BasePage


class ReviewPage(BasePage):
    """Admin: evaluasi proposal."""

    def open(self):
        self.nav("Evaluasi proposal")
        return self

    def card(self, title: str):
        return self.page.locator(".review-card", has_text=title)

    def approve(self, title: str, note: str = ""):
        card = self.card(title)
        card.locator("textarea").fill(note)
        card.locator("[data-approve]").click()
        return self

    def reject(self, title: str, note: str = ""):
        card = self.card(title)
        card.locator("textarea").fill(note)
        card.locator("[data-reject]").click()
        return self

    def card_error(self, title: str):
        return self.card(title).locator(".err")

    @property
    def history(self):
        return self.page.locator("#reviewHistory")

    def pending_count_badge(self):
        badge = self.page.locator("#mainNav button", has_text="Evaluasi proposal").locator(".count")
        return int(badge.inner_text()) if badge.count() else 0


class ReportsPage(BasePage):
    """Admin: laporan masuk."""

    def open(self):
        self.nav("Laporan masuk")
        return self

    def row(self, file_name: str):
        return self.page.locator("#reportQueue tr", has_text=file_name)

    def accept(self, file_name: str, note: str = ""):
        row = self.row(file_name)
        row.locator("input").fill(note)
        row.locator("[data-ok]").click()
        return self

    def request_revision(self, file_name: str, note: str = ""):
        row = self.row(file_name)
        row.locator("input").fill(note)
        row.locator("[data-rev]").click()
        return self

    def row_error(self, file_name: str):
        return self.row(file_name).locator(".err")

    @property
    def queue(self):
        return self.page.locator("#reportQueue")

    @property
    def done(self):
        return self.page.locator("#reportDone")
