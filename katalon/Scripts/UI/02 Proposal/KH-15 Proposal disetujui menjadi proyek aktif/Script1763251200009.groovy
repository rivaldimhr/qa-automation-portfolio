import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-15: Proposal disetujui menjadi proyek aktif
// Story SCRUM-10. Teknik: state transition, E2E. Admin menyetujui dengan catatan: penghitung berkurang, PJ mendapat notifikasi berisi catatan, proyek muncul di filter Aktif.

Map proposal = KhData.proposal()
String catatan = 'Mulai dengan 300 buku dulu'

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proposal')
CustomKeywords.'kingshand.KH.submitProposal'(proposal, 'proposal.pdf')

CustomKeywords.'kingshand.KH.switchTo'('admin')
CustomKeywords.'kingshand.KH.openMenu'('Evaluasi proposal')
int sebelum = CustomKeywords.'kingshand.KH.pendingProposalCount'()
CustomKeywords.'kingshand.KH.approveProposal'(proposal.judul, catatan)
WebUI.verifyEqual(CustomKeywords.'kingshand.KH.pendingProposalCount'(), sebelum - 1)
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Review/table_Riwayat'), proposal.judul)

CustomKeywords.'kingshand.KH.switchTo'('pj')
CustomKeywords.'kingshand.KH.openNotifications'()
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/list_Notifikasi'), "disetujui. Proyek kini aktif. Catatan: ${catatan}")
CustomKeywords.'kingshand.KH.closeNotifications'()

CustomKeywords.'kingshand.KH.openMenu'('Proyek')
WebUI.click(findTestObject('KINGSHANDs/Projects/chip_Filter', [('label') : 'Aktif']))
List names = CustomKeywords.'kingshand.KH.projectNames'()
assert names.contains(proposal.judul) : "Proyek aktif: ${names}"
