from .admin_pages import ReportsPage, ReviewPage
from .dashboard_page import DashboardPage
from .login_page import LoginPage
from .profile_page import ProfilePage
from .project_detail_page import ProjectDetailPage
from .projects_page import ProjectsPage
from .proposal_page import ProposalPage


class App:
    """Satu pintu ke semua page object untuk satu tab browser."""

    def __init__(self, page, base_url):
        self.page = page
        self.login = LoginPage(page, base_url)
        self.dashboard = DashboardPage(page, base_url)
        self.projects = ProjectsPage(page, base_url)
        self.detail = ProjectDetailPage(page, base_url)
        self.proposal = ProposalPage(page, base_url)
        self.review = ReviewPage(page, base_url)
        self.reports = ReportsPage(page, base_url)
        self.profile = ProfilePage(page, base_url)

    def as_role(self, role: str):
        """Buka aplikasi dan masuk sebagai 'pj' atau 'admin'."""
        self.login.open().login_as(role)
        return self

    def switch_to(self, role: str):
        self.login.logout()
        self.login.login_as(role)
        return self
