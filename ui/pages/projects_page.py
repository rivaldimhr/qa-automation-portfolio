from .base import BasePage


class ProjectsPage(BasePage):
    def open(self):
        self.nav("Proyek")
        return self

    def search(self, text: str):
        self.page.fill("#q", text)
        return self

    def filter(self, chip: str):
        self.page.locator("#filterChips .chip", has_text=chip).click()
        return self

    def names(self):
        return [t.strip() for t in self.page.locator("#projList .pitem strong").all_inner_texts()]

    @property
    def empty_message(self):
        return self.page.locator("#projList .empty")

    def open_project(self, name: str):
        self.page.locator("#projList .pitem", has_text=name).click()
        self.page.wait_for_selector("#detail h1")
