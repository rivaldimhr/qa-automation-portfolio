import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-04: Tombol akun demo mengisi form
// Story SCRUM-8. Klik akun demo Admin lembaga; email dan kata sandi admin terisi otomatis.

CustomKeywords.'kingshand.KH.openApp'()
WebUI.click(findTestObject('KINGSHANDs/Login/btn_AkunDemo', [('role') : 'admin']))
WebUI.verifyEqual(WebUI.getAttribute(findTestObject('KINGSHANDs/Login/input_Email'), 'value'), GlobalVariable.ADMIN_EMAIL)
WebUI.verifyEqual(WebUI.getAttribute(findTestObject('KINGSHANDs/Login/input_KataSandi'), 'value'), GlobalVariable.ADMIN_PASSWORD)
