import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-12: Batas kebutuhan dana proposal
// Story SCRUM-9. Teknik: boundary value analysis. Data-driven dari Data Files/KINGSHANDs/proposal_dana: 0, -5, kosong ditolak; 1 dan 1500 diterima.

def data = findTestData('Data Files/KINGSHANDs/proposal_dana')

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proposal')

for (int row = 1; row <= data.getRowNumbers(); row++) {
	String dana = data.getValue('dana', row) ?: ''
	boolean diterima = Boolean.parseBoolean(data.getValue('diterima', row))
	WebUI.comment("Dana '${dana}' -> diterima: ${diterima}")
	WebUI.refresh()
	CustomKeywords.'kingshand.KH.fillProposal'(KhData.proposal([('dana') : dana]))
	WebUI.click(findTestObject('KINGSHANDs/Proposal/btn_Ajukan'))
	if (diterima) {
		CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Proposal/txt_PesanError'), '')
		CustomKeywords.'kingshand.KH.verifyContains'(findTestObject('KINGSHANDs/Header/txt_Toast'), 'Proposal diajukan')
	} else {
		WebUI.verifyNotEqual(CustomKeywords.'kingshand.KH.textOf'(findTestObject('KINGSHANDs/Proposal/txt_PesanError')), '')
	}
}
