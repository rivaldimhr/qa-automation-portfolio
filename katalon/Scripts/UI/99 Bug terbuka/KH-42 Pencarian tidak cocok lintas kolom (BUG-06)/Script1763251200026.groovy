import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-42: Pencarian tidak cocok lintas kolom (BUG-06)
// Bug terbuka BUG-06 (SCRUM-28). Test ini menulis perilaku yang BENAR, jadi GAGAL selama bug belum diperbaiki. 'cikutracikutra' (akhir nama + awal lokasi) seharusnya tanpa hasil.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proyek')
WebUI.setText(findTestObject('KINGSHANDs/Projects/input_Cari'), 'cikutracikutra')
WebUI.verifyElementPresent(findTestObject('KINGSHANDs/Projects/txt_Kosong'), 2)
