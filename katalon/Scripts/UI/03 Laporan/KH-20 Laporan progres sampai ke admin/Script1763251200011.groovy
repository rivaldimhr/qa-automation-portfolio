import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-20: Laporan progres sampai ke admin
// Story SCRUM-11. Teknik: E2E. PJ melaporkan progres 70% untuk Cafe; progres berubah, status Menunggu diperiksa, admin melihat Progres 70%.

String file = 'progres-nov.pdf'

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openProject'(KhData.CAFE)
CustomKeywords.'kingshand.KH.sendReport'('progres', 70, file)

CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/txt_Toast'), 'Laporan perkembangan terkirim')
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Detail/txt_Progres'), '70%')
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Detail/list_Laporan', [('kind') : 'dev']), 'Menunggu diperiksa')

CustomKeywords.'kingshand.KH.switchTo'('admin')
CustomKeywords.'kingshand.KH.openMenu'('Laporan masuk')
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Reports/row_Laporan', [('file') : file]), 'Progres 70%')
