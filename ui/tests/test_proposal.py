"""Pengajuan dan evaluasi proposal (Story: Pengajuan proposal, Evaluasi proposal)."""
import pytest
from playwright.sync_api import expect

from bugs import known_bug, regression_for
from ui.data import month_label, month_value, proposal


@pytest.mark.smoke
def test_kh10_proposal_baru_sampai_ke_admin(app):
    app.as_role("pj")
    app.proposal.open().submit_proposal(proposal())
    expect(app.proposal.toast).to_contain_text("Proposal diajukan")
    expect(app.proposal.row("Taman Baca Anak Cimahi")).to_contain_text("Menunggu persetujuan")
    app.switch_to("admin")
    app.review.open()
    card = app.review.card("Taman Baca Anak Cimahi")
    expect(card).to_contain_text("Rp 75 juta")
    expect(card).to_contain_text("proposal.pdf")


@pytest.mark.parametrize("missing", ["judul", "lokasi", "target", "mulai", "deskripsi"])
def test_kh11_semua_kolom_wajib_diisi(app, missing):
    app.as_role("pj")
    app.proposal.open().fill(**{k: v for k, v in proposal().items() if k != missing}).submit()
    expect(app.proposal.error).to_have_text("Lengkapi semua kolom sebelum mengajukan.")


@pytest.mark.parametrize("dana, accepted", [("0", False), ("-5", False), ("", False), ("1", True), ("1500", True)])
def test_kh12_batas_kebutuhan_dana(app, dana, accepted):
    app.as_role("pj")
    app.proposal.open().fill(**{**proposal(), "dana": dana}).submit()
    if accepted:
        expect(app.proposal.error).to_have_text("")
        expect(app.proposal.table).to_contain_text("Taman Baca Anak Cimahi")
    else:
        expect(app.proposal.error).not_to_have_text("")


def test_kh13_dana_di_atas_seribu_juta_ditulis_miliar(app):
    app.as_role("pj")
    app.proposal.open().submit_proposal(proposal(dana=1500))
    app.switch_to("admin")
    app.review.open()
    expect(app.review.card("Taman Baca Anak Cimahi")).to_contain_text("Rp 1,5 miliar")


def test_kh14_proposal_menunggu_bisa_diubah(app):
    app.as_role("pj")
    app.proposal.open().edit("Sumur Bor Desa Cikadu")
    app.proposal.fill(judul="Sumur Bor dan Tandon Cikadu", dana=200).submit()
    expect(app.proposal.toast).to_contain_text("Perubahan proposal disimpan")
    expect(app.proposal.table).to_contain_text("Sumur Bor dan Tandon Cikadu")
    app.switch_to("admin")
    app.review.open()
    expect(app.review.card("Sumur Bor dan Tandon Cikadu")).to_contain_text("Rp 200 juta")


@pytest.mark.smoke
def test_kh15_disetujui_menjadi_proyek_aktif_dan_pj_diberi_tahu(app):
    app.as_role("pj")
    app.proposal.open().submit_proposal(proposal())
    app.switch_to("admin")
    app.review.open()
    before = app.review.pending_count_badge()
    app.review.approve("Taman Baca Anak Cimahi", "Mulai dengan 300 buku dulu")
    assert app.review.pending_count_badge() == before - 1
    expect(app.review.history).to_contain_text("Taman Baca Anak Cimahi")
    app.switch_to("pj")
    expect(app.proposal.open_notifications()).to_contain_text("disetujui. Proyek kini aktif. Catatan: Mulai dengan 300 buku dulu")
    app.page.keyboard.press("Escape")
    app.projects.open().filter("Aktif")
    assert "Taman Baca Anak Cimahi" in app.projects.names()


def test_kh16_penolakan_wajib_alasan(app):
    app.as_role("admin")
    app.review.open().reject("Sumur Bor Desa Cikadu", "")
    expect(app.review.card_error("Sumur Bor Desa Cikadu")).to_contain_text("Tulis alasan penolakan")
    app.review.reject("Sumur Bor Desa Cikadu", "Lampirkan hasil survei air tanah")
    app.switch_to("pj")
    row = app.proposal.open().row("Sumur Bor Desa Cikadu")
    expect(row).to_contain_text("Ditolak")
    expect(row).to_contain_text("Lampirkan hasil survei air tanah")
    app.projects.open()
    assert "Sumur Bor Desa Cikadu" not in app.projects.names()


@regression_for("BUG-04")
def test_kh17_persetujuan_mempertahankan_rencana_mulai(app):
    app.as_role("pj")
    app.proposal.open().submit_proposal(proposal(mulai=month_value(3)))
    app.switch_to("admin")
    app.review.open().approve("Taman Baca Anak Cimahi")
    app.projects.open().open_project("Taman Baca Anak Cimahi")
    expect(app.detail.summary()).to_contain_text(month_label(3))


@known_bug("BUG-05")
def test_kh18_rencana_mulai_di_masa_lalu_ditolak(app):
    app.as_role("pj")
    app.proposal.open().submit_proposal(proposal(mulai=month_value(-24)))
    expect(app.proposal.error).not_to_have_text("", timeout=1500)
