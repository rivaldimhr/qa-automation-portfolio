import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-16: Penolakan proposal wajib alasan
// Story SCRUM-10. Teknik: state transition, negative. Tolak tanpa alasan ditolak; dengan alasan, PJ melihat status Ditolak beserta alasannya dan proyek hilang dari daftar.

String judul = KhData.SUMUR_BOR
String alasan = 'Lampirkan hasil survei air tanah'

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('admin')
CustomKeywords.'kingshand.KH.openMenu'('Evaluasi proposal')

CustomKeywords.'kingshand.KH.rejectProposal'(judul, '')
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Review/txt_PesanError', [('judul') : judul]), 'Tulis alasan penolakan')

CustomKeywords.'kingshand.KH.rejectProposal'(judul, alasan)

CustomKeywords.'kingshand.KH.switchTo'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proposal')
def row = findTestObject('KINGSHANDs/Proposal/row_Proposal', [('judul') : judul])
CustomKeywords.'kingshand.KH.verifyContains'(row, 'Ditolak')
CustomKeywords.'kingshand.KH.verifyContains'(row, alasan)

CustomKeywords.'kingshand.KH.openMenu'('Proyek')
List names = CustomKeywords.'kingshand.KH.projectNames'()
assert !names.contains(judul) : "Proyek yang ditolak masih tampil: ${names}"
