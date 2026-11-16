import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-31: Batas selisih dana dan progres
// Story SCRUM-13. Teknik: BVA. Cafe (anggaran Rp 500 juta, progres 63%): terpakai Rp 385 juta = selisih 14 poin, tanpa peringatan; Rp 390 juta = 15 poin, muncul peringatan.

def kasus = [
	[terpakai: 385, peringatan: false],
	[terpakai: 390, peringatan: true]
]

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')

for (Map k : kasus) {
	WebUI.comment("Terpakai Rp ${k.terpakai} juta -> peringatan: ${k.peringatan}")
	CustomKeywords.'kingshand.KH.resetData'()
	CustomKeywords.'kingshand.KH.openProject'(KhData.CAFE)
	CustomKeywords.'kingshand.KH.sendReport'('keuangan', k.terpakai, 'laporan.pdf')
	def alerts = findTestObject('KINGSHANDs/Detail/section_Peringatan')
	if (k.peringatan) {
		CustomKeywords.'kingshand.KH.verifyContains'(alerts, 'Dana terpakai 78% dari anggaran, progres pekerjaan baru 63%')
	} else {
		CustomKeywords.'kingshand.KH.verifyNotContains'(alerts, 'Dana terpakai lebih cepat')
	}
}
