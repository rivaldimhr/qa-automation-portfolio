import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-02: Login gagal dengan data salah
// Story SCRUM-8. Teknik: EP, negative. Data-driven dari Data Files/KINGSHANDs/login_gagal: setiap baris harus ditolak dan aplikasi tidak terbuka.

def data = findTestData('Data Files/KINGSHANDs/login_gagal')

CustomKeywords.'kingshand.KH.openApp'()

for (int row = 1; row <= data.getRowNumbers(); row++) {
	WebUI.comment("Baris ${row}: ${data.getValue('keterangan', row)}")
	WebUI.refresh()
	CustomKeywords.'kingshand.KH.login'(data.getValue('email', row) ?: '', data.getValue('password', row) ?: '')
	CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Login/txt_PesanError'), 'Email atau kata sandi salah')
	WebUI.verifyElementNotVisible(findTestObject('KINGSHANDs/Header/section_App'))
}
