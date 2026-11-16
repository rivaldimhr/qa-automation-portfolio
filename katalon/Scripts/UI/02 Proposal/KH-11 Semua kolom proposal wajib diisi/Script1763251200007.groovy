import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kingshand.KhData as KhData

// KH-11: Semua kolom proposal wajib diisi
// Story SCRUM-9. Teknik: EP. Setiap kolom wajib dikosongkan satu per satu; proposal ditolak dengan pesan yang sama.

def wajib = ['judul', 'lokasi', 'target', 'mulai', 'deskripsi']

CustomKeywords.'kingshand.KH.openApp'()
CustomKeywords.'kingshand.KH.loginAs'('pj')
CustomKeywords.'kingshand.KH.openMenu'('Proposal')

for (String kolom : wajib) {
	WebUI.comment("Tanpa ${kolom}")
	WebUI.refresh()
	CustomKeywords.'kingshand.KH.fillProposal'(KhData.proposalWithout(kolom))
	WebUI.click(findTestObject('KINGSHANDs/Proposal/btn_Ajukan'))
	CustomKeywords.'kingshand.KH.verifyText'(findTestObject('KINGSHANDs/Proposal/txt_PesanError'), 'Lengkapi semua kolom sebelum mengajukan.')
}
