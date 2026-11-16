import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-44: Email profil harus valid
// Story SCRUM-14. Teknik: EP, negative. 'bukan-email', 'a@', '@kingshand.id', dan kosong ditolak.

def emailTidakValid = ['bukan-email', 'a@', '@kingshand.id', '']

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')

for (String email : emailTidakValid) {
	WebUI.comment("Email '${email}'")
	CustomKeywords.'kingshand.KH.editProfile'([('email') : email])
	CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Profile/txt_PesanError'), 'Nama dan email yang valid wajib diisi.')
	WebUI.click(findTestObject('KINGSHANDs/Profile/btn_Batal'))
}
