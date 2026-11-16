import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-43: Ubah nama mengubah sapaan
// Story SCRUM-14. Nama profil diubah menjadi Rina Sari; dashboard menyapa 'Halo, Rina'.

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.editProfile'([('nama') : 'Rina Sari'])
CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/txt_Toast'), 'Profil disimpan')

CustomKeywords.'kingshand.KH.openMenu'('Dashboard')
CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Header/txt_Sapaan'), 'Halo, Rina')
