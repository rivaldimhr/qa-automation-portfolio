import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-25: Admin menerima laporan dan PJ diberi tahu
// Story SCRUM-12. Teknik: state transition. Admin menerima laporan Progres-Unit-Pilah.pdf: laporan pindah ke Sudah diperiksa dan PJ mendapat notifikasi.

String file = 'Progres-Unit-Pilah.pdf'

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('admin')
CustomKeywords.'kingshand.KH.openMenu'('Laporan masuk')
CustomKeywords.'kingshand.KH.acceptReport'(file, '')

CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/txt_Toast'), 'Laporan diterima')
CustomKeywords.'kingshand.KH.verifyNotContains'(findTestObject('KINGSHANDs/Reports/table_Antrean'), file)
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Reports/table_SudahDiperiksa'), file)

CustomKeywords.'kingshand.KH.switchTo'('pj')
CustomKeywords.'kingshand.KH.openNotifications'()
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/list_Notifikasi'), 'Laporan perkembangan Bank Sampah Cikutra diterima admin')
