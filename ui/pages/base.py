from playwright.sync_api import Page, expect

ACCOUNTS = {
    "pj": ("demo@kingshand.id", "demo123"),
    "admin": ("admin@kingshand.id", "admin123"),
}


class BasePage:
    """Bagian yang sama di semua halaman setelah login: menu, lonceng, toast, keluar."""

    def __init__(self, page: Page, base_url: str):
        self.page = page
        self.base_url = base_url

    def go(self, route: str = ""):
        """Buka halaman lewat hash, misalnya 'projects' atau 'project/4'."""
        self.page.evaluate(f"location.hash = {route!r}")
        return self

    def nav(self, label: str):
        self.page.locator("#mainNav button", has_text=label).click()
        return self

    def nav_labels(self):
        # teks tombol tanpa angka penghitung (<span class="count">)
        return self.page.eval_on_selector_all("#mainNav button", "bs => bs.map(b => b.firstChild.textContent.trim())")

    @property
    def toast(self):
        return self.page.locator("#toast")

    def logout(self):
        self.page.click("#logoutBtn")
        expect(self.page.locator("#loginForm")).to_be_visible()

    def open_notifications(self):
        self.page.click("#bellBtn")
        expect(self.page.locator("#notifPanel")).to_be_visible()
        return self.page.locator("#notifPanelList")

    def unread_badge(self):
        return self.page.locator("#bellDot")

    def visible_page_id(self):
        return self.page.locator(".page.active").get_attribute("id")

    def has_horizontal_scroll(self):
        return self.page.evaluate("document.documentElement.scrollWidth > window.innerWidth")
