import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-05: Halaman peran lain tidak bisa dibuka lewat URL
// Story SCRUM-8. Teknik: negative. Admin membuka #proposal dan #profile, PJ membuka #review dan #reports lewat URL; semuanya dialihkan ke Dashboard.

def kasus = [
	[role: 'admin', halaman: 'proposal'],
	[role: 'admin', halaman: 'profile'],
	[role: 'pj', halaman: 'review'],
	[role: 'pj', halaman: 'reports']
]

CustomKeywords.'kingshand.KH.openApp'()

for (Map k : kasus) {
	WebUI.comment("${k.role} membuka #${k.halaman}")
	CustomKeywords.'kingshand.KH.loginAs'(k.role)
	CustomKeywords.'kingshand.KH.goTo'(k.halaman)
	WebUI.verifyElementVisible(findTestObject('KINGSHANDs/Header/page_Halaman', [('id') : 'dashboard']))
	WebUI.verifyElementNotVisible(findTestObject('KINGSHANDs/Header/page_Halaman', [('id') : k.halaman]))
	CustomKeywords.'kingshand.KH.logout'()
}
