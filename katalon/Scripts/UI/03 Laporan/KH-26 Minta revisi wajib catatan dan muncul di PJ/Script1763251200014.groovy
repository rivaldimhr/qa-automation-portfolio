import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-26: Minta revisi wajib catatan dan muncul di PJ
// Story SCRUM-12. Teknik: state transition, negative. Minta revisi tanpa catatan ditolak; dengan catatan, PJ melihat peringatan revisi beserta catatannya.

String file = 'Progres-Unit-Pilah.pdf'
String catatan = 'Lampirkan foto unit pilah'

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('admin')
CustomKeywords.'kingshand.KH.openMenu'('Laporan masuk')

CustomKeywords.'kingshand.KH.requestRevision'(file, '')
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Reports/txt_PesanError', [('file') : file]), 'Tulis apa yang perlu direvisi.')

CustomKeywords.'kingshand.KH.requestRevision'(file, catatan)
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/txt_Toast'), 'Permintaan revisi dikirim')

CustomKeywords.'kingshand.KH.switchTo'('pj')
CustomKeywords.'kingshand.KH.openProject'(KhData.BANK_SAMPAH)
def alerts = findTestObject('KINGSHANDs/Detail/section_Peringatan')
CustomKeywords.'kingshand.KH.verifyContains'(alerts, 'Laporan perkembangan perlu revisi')
CustomKeywords.'kingshand.KH.verifyContains'(alerts, catatan)
