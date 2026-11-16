import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-03: Email tidak peka huruf besar dan spasi
// Story SCRUM-8. Teknik: EP. Email '  DEMO@KingsHand.id ' tetap diterima sebagai akun penanggung jawab.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.login'('  DEMO@KingsHand.id ', GlobalVariable.PJ_PASSWORD)
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Header/txt_Sapaan'), 'Halo, Dinda')
