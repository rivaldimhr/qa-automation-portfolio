import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-06: Keluar menutup aplikasi
// Story SCRUM-8. Teknik: state transition. Setelah keluar, form login tampil, juga setelah halaman dimuat ulang.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.logout'()

WebUI.refresh()
CustomKeywords.'kingshand.KH.shouldBeVisible'(findTestObject('KINGSHANDs/Login/form_Login'))
WebUI.verifyElementNotVisible(findTestObject('KINGSHANDs/Header/section_App'))
