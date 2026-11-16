import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-40: Cari proyek berdasarkan nama atau lokasi
// Story SCRUM-14. Teknik: EP. 'cafe', 'BANDUNG', dan 'garut' menampilkan daftar proyek yang tepat, tidak peka huruf besar.

def kasus = [
	'cafe'   : ["Cafe Le' de Jainero"],
	'BANDUNG': ["Cafe Le' de Jainero", 'Bank Sampah Cikutra', 'Kebun Hidroponik Sekolah', 'Wisata Alam Curug Telu'],
	'garut'  : ['Perpustakaan Keliling Garut']
]

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proyek')

kasus.each { String kata, List harapan ->
	WebUI.comment("Cari '${kata}'")
	WebUI.setText(findTestObject('KINGSHANDs/Projects/input_Cari'), kata)
	WebUI.verifyEqual(CustomKeywords.'kingshand.KH.projectNames'().sort(), harapan.sort())
}
