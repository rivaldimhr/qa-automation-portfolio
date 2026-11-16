import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-10: Proposal baru sampai ke admin
// Story SCRUM-9. Teknik: E2E antarperan. PJ mengajukan proposal Rp 75 juta dengan lampiran PDF; admin melihat dana dan lampirannya.

Map proposal = KhData.proposal()

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proposal')
CustomKeywords.'kingshand.KH.submitProposal'(proposal, 'proposal.pdf')

CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/txt_Toast'), 'Proposal diajukan')
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Proposal/row_Proposal', [('judul') : proposal.judul]), 'Menunggu persetujuan')

CustomKeywords.'kingshand.KH.switchTo'('admin')
CustomKeywords.'kingshand.KH.openMenu'('Evaluasi proposal')
def card = findTestObject('KINGSHANDs/Review/card_Proposal', [('judul') : proposal.judul])
CustomKeywords.'kingshand.KH.verifyContains'(card, 'Rp 75 juta')
CustomKeywords.'kingshand.KH.verifyContains'(card, 'proposal.pdf')
