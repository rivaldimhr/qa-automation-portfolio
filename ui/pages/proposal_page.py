from .base import BasePage
from .project_detail_page import fake_file

FIELDS = {"judul": "#fJudul", "lokasi": "#fLokasi", "target": "#fTarget", "dana": "#fDana", "mulai": "#fMulai", "deskripsi": "#fDesk"}


class ProposalPage(BasePage):
    def open(self):
        self.nav("Proposal")
        return self

    def fill(self, **values):
        for name, value in values.items():
            self.page.fill(FIELDS[name], str(value))
        return self

    def attach(self, file_name: str):
        self.page.set_input_files("#fFile", fake_file(file_name))
        return self

    def submit(self):
        self.page.click("#propSubmit")
        return self

    def submit_proposal(self, data: dict, attachment: str | None = "proposal.pdf"):
        self.fill(**data)
        if attachment:
            self.attach(attachment)
        return self.submit()

    @property
    def error(self):
        return self.page.locator("#propErr")

    @property
    def table(self):
        return self.page.locator("#propTable")

    def row(self, title: str):
        return self.page.locator("#propTable tr", has_text=title)

    def edit(self, title: str):
        self.row(title).locator("[data-edit]").click()
        return self
