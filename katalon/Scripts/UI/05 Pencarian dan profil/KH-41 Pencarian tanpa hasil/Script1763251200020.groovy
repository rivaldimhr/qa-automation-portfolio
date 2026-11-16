import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-41: Pencarian tanpa hasil
// Story SCRUM-14. Teknik: negative. Kata 'zzz' menampilkan pesan 'Tidak ada proyek yang cocok.'

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proyek')
WebUI.setText(findTestObject('KINGSHANDs/Projects/input_Cari'), 'zzz')
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Projects/txt_Kosong'), 'Tidak ada proyek yang cocok.')
