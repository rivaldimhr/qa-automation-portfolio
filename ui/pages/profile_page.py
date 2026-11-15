from .base import BasePage

FIELDS = {"nama": "#uName", "email": "#uEmail", "organisasi": "#uOrg", "telepon": "#uPhone"}


class ProfilePage(BasePage):
    def open(self):
        self.page.click("#profileBtn")
        return self

    def edit(self, **values):
        self.page.click("#editProfile")
        for name, value in values.items():
            self.page.fill(FIELDS[name], value)
        self.page.click("#saveProfile")
        return self

    def value(self, name: str):
        return self.page.input_value(FIELDS[name])

    @property
    def error(self):
        return self.page.locator("#profileErr")
