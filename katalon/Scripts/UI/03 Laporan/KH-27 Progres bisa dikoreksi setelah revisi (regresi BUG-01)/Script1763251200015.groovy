import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-27: Progres bisa dikoreksi setelah revisi (regresi BUG-01)
// Regresi BUG-01 (SCRUM-23, High, sudah diperbaiki). PJ salah melaporkan 95%, admin meminta revisi, PJ mengoreksi ke 70%: koreksi diterima dan 70% menjadi batas bawah laporan berikutnya.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openProject'(KhData.CAFE)
CustomKeywords.'kingshand.KH.sendReport'('progres', 95, 'progres-salah.pdf')

CustomKeywords.'kingshand.KH.switchTo'('admin')
CustomKeywords.'kingshand.KH.openMenu'('Laporan masuk')
CustomKeywords.'kingshand.KH.requestRevision'('progres-salah.pdf', 'Progres sebenarnya 70%')

CustomKeywords.'kingshand.KH.switchTo'('pj')
CustomKeywords.'kingshand.KH.openProject'(KhData.CAFE)
CustomKeywords.'kingshand.KH.sendReport'('progres', 70, 'progres-benar.pdf')
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Detail/txt_PesanError', [('kind') : 'dev']), '')
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Detail/txt_Progres'), '70%')

// koreksi 70% kini menjadi batas bawah laporan berikutnya
CustomKeywords.'kingshand.KH.sendReport'('progres', 60, 'terlalu-rendah.pdf')
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Detail/txt_PesanError', [('kind') : 'dev']), 'Progres tidak boleh turun dari 70%.')
