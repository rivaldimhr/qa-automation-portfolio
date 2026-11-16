import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-21: Validasi laporan progres
// Story SCRUM-11. Teknik: EP, BVA. Data-driven dari Data Files/KINGSHANDs/laporan_progres_tidak_valid: tanpa file, kosong, 101, -1, dan 62 (di bawah progres 63%) ditolak dengan pesan yang tepat.

def data = findTestData('Data Files/KINGSHANDs/laporan_progres_tidak_valid')

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openProject'(KhData.CAFE)

for (int row = 1; row <= data.getRowNumbers(); row++) {
	String nilai = data.getValue('nilai', row) ?: ''
	String file = data.getValue('file', row) ?: ''
	String pesan = data.getValue('pesan', row)
	WebUI.comment("Nilai '${nilai}', file '${file}' -> ${pesan}")
	WebUI.refresh()
	CustomKeywords.'kingshand.KH.sendReport'('progres', nilai, file)
	CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Detail/txt_PesanError', [('kind') : 'dev']), pesan)
}
