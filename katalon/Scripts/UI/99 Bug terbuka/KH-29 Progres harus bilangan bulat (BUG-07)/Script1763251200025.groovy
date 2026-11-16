import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-29: Progres harus bilangan bulat (BUG-07)
// Bug terbuka BUG-07 (SCRUM-29). Test ini menulis perilaku yang BENAR, jadi GAGAL selama bug belum diperbaiki. Progres 63.5 seharusnya ditolak.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openProject'(KhData.CAFE)
CustomKeywords.'kingshand.KH.sendReport'('progres', '63.5', 'laporan.pdf')
WebUI.verifyNotEqual(CustomKeywords.'kingshand.KH.textOf'(findTestObject('KINGSHANDs/Detail/txt_PesanError', [('kind') : 'dev'])), '')
