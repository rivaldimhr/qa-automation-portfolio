from playwright.sync_api import expect

from .base import ACCOUNTS, BasePage


class LoginPage(BasePage):
    def open(self):
        self.page.goto(self.base_url)
        expect(self.page.locator("#loginForm")).to_be_visible()
        return self

    def login(self, email: str, password: str):
        self.page.fill("#email", email)
        self.page.fill("#pw", password)
        self.page.click("#loginBtn")
        return self

    def login_as(self, role: str):
        self.login(*ACCOUNTS[role])
        expect(self.page.locator("#app #hello")).to_be_visible()
        return self

    def use_demo_account(self, role: str):
        self.page.click(f'[data-acct="{role}"]')
        return self

    @property
    def error(self):
        return self.page.locator("#loginErr")

    def field_values(self):
        return self.page.input_value("#email"), self.page.input_value("#pw")
