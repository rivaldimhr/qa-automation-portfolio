import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-46: Notifikasi ditandai dibaca
// Story SCRUM-14. Teknik: state transition. Admin punya 2 notifikasi belum dibaca; setelah panel dibuka penandanya hilang, juga setelah halaman dimuat ulang.

def badge = findTestObject('KINGSHANDs/Header/badge_BelumDibaca')

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('admin')
CustomKeywords.'kingshand.KH.verifyText'(badge, '2')

CustomKeywords.'kingshand.KH.openNotifications'()
WebUI.verifyElementNotVisible(badge)

WebUI.refresh()
CustomKeywords.'kingshand.KH.shouldBeVisible'(findTestObject('KINGSHANDs/Header/txt_Sapaan'))
WebUI.verifyElementNotVisible(badge)
