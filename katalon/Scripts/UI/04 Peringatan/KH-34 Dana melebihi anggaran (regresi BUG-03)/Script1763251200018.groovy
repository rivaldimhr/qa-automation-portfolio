import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-34: Dana melebihi anggaran (regresi BUG-03)
// Regresi BUG-03 (SCRUM-25, sudah diperbaiki). Teknik: BVA. Bank Sampah (anggaran Rp 120 juta) progres 100%: terpakai 120 juta tanpa peringatan; 121 dan 130 juta harus muncul 'Dana terpakai melebihi anggaran'.

def kasus = [
	[terpakai: 120, peringatan: false],
	[terpakai: 121, peringatan: true],
	[terpakai: 130, peringatan: true]
]

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')

for (Map k : kasus) {
	WebUI.comment("Terpakai Rp ${k.terpakai} juta -> peringatan: ${k.peringatan}")
	CustomKeywords.'kingshand.KH.resetData'()
	CustomKeywords.'kingshand.KH.openProject'(KhData.BANK_SAMPAH)
	CustomKeywords.'kingshand.KH.sendReport'('progres', 100, 'laporan.pdf')
	CustomKeywords.'kingshand.KH.sendReport'('keuangan', k.terpakai, 'laporan.pdf')
	def alerts = findTestObject('KINGSHANDs/Detail/section_Peringatan')
	if (k.peringatan) {
		CustomKeywords.'kingshand.KH.verifyContains'(alerts, 'Dana terpakai melebihi anggaran')
	} else {
		CustomKeywords.'kingshand.KH.verifyNotContains'(alerts, 'melebihi anggaran')
	}
}
