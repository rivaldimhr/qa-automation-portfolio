import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-18: Rencana mulai di masa lalu ditolak (BUG-05)
// Bug terbuka BUG-05 (SCRUM-27). Test ini menulis perilaku yang BENAR, jadi GAGAL selama bug belum diperbaiki. Proposal dengan rencana mulai 2 tahun lalu seharusnya ditolak.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proposal')
CustomKeywords.'kingshand.KH.submitProposal'(KhData.proposal([('mulai') : KhData.monthValue(-24)]), 'proposal.pdf')
WebUI.verifyNotEqual(CustomKeywords.'kingshand.KH.textOf'(findTestObject('KINGSHANDs/Proposal/txt_PesanError')), '')
