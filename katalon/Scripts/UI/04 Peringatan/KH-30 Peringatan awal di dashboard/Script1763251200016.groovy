import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-30: Peringatan awal di dashboard
// Story SCRUM-13. Dashboard PJ menampilkan Perlu perhatian (2): Klinik Desa Sukamaju (dana 83% vs progres 66%) dan Sekolah Luar Biasa Papua (laporan terakhir 45 hari lalu).

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')

def attention = findTestObject('KINGSHANDs/Dashboard/section_PerluPerhatian')
for (String teks : ['Perlu perhatian (2)', 'Klinik Desa Sukamaju', 'Dana terpakai 83%', 'progres pekerjaan baru 66%', 'Sekolah Luar Biasa Papua', '45 hari lalu']) {
	CustomKeywords.'kingshand.KH.verifyContains'(attention, teks)
}
