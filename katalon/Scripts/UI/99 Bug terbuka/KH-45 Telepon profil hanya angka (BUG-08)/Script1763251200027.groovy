import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-45: Telepon profil hanya angka (BUG-08)
// Bug terbuka BUG-08 (SCRUM-30). Test ini menulis perilaku yang BENAR, jadi GAGAL selama bug belum diperbaiki. Telepon 'bukan nomor' seharusnya ditolak.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.editProfile'([('telepon') : 'bukan nomor'])
WebUI.verifyNotEqual(CustomKeywords.'kingshand.KH.textOf'(findTestObject('KINGSHANDs/Profile/txt_PesanError')), '')
