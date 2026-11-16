import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-22: Ubah dan hapus tanpa login ditolak
// Story SCRUM-17. Teknik: negative. PUT, PATCH, dan DELETE tanpa token maupun Basic auth menjawab 403, dan data tidak berubah.

Map booking = CustomKeywords.'restfulbooker.Booker.newBooking'()
int id = CustomKeywords.'restfulbooker.Booker.createdId'(booking)

WS.verifyResponseStatusCode(CustomKeywords.'restfulbooker.Booker.updateBooking'(id, booking + [firstname: 'Penyusup'], '', ''), 403)
WS.verifyResponseStatusCode(CustomKeywords.'restfulbooker.Booker.patchBooking'(id, [totalprice: 1], '', ''), 403)
WS.verifyResponseStatusCode(CustomKeywords.'restfulbooker.Booker.deleteBooking'(id, '', ''), 403)

ResponseObject read = CustomKeywords.'restfulbooker.Booker.getBooking'(id, 'application/json')
WS.verifyResponseStatusCode(read, 200)
WS.verifyEqual(CustomKeywords.'restfulbooker.Booker.json'(read), booking)
