from .base import BasePage


class DashboardPage(BasePage):
    @property
    def greeting(self):
        return self.page.locator("#hello")

    @property
    def attention(self):
        return self.page.locator("#attention")

    def attention_title(self):
        return self.page.inner_text("#attention .section-title")

    def alerts(self):
        return self.page.locator("#attention .alert").all_inner_texts()

    def stat_value(self, label: str) -> int:
        stat = self.page.locator("#stats .stat", has_text=label)
        return int(stat.locator(".n").inner_text())

    def click_stat(self, label: str):
        self.page.locator("#stats .stat", has_text=label).click()
        return self
