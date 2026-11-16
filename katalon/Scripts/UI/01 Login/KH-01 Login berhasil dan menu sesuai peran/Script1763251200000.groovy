import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-01: Login berhasil dan menu sesuai peran
// Story SCRUM-8. Teknik: EP. Login sebagai penanggung jawab dan admin; sapaan dan menu utama harus sesuai peran.

def peran = [
	[role: 'pj', sapaan: 'Halo, Dinda', menu: ['Dashboard', 'Proyek', 'Proposal']],
	[role: 'admin', sapaan: 'Halo, Admin', menu: ['Dashboard', 'Evaluasi proposal', 'Laporan masuk', 'Proyek']]
]

CustomKeywords.'kingshand.KH.openApp'()

for (Map p : peran) {
	WebUI.comment("Peran: ${p.role}")
	CustomKeywords.'kingshand.KH.loginAs'(p.role)
	CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Header/txt_Sapaan'), p.sapaan)
	WebUI.verifyEqual(CustomKeywords.'kingshand.KH.menuLabels'(), p.menu)
	CustomKeywords.'kingshand.KH.logout'()
}
