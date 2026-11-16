import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-20: Ubah booking dengan token atau Basic auth
// Story SCRUM-16. Teknik: EP. PUT /booking/{id} berhasil (200) dengan cookie token maupun header Authorization Basic, dan data terganti.

Map booking = CustomKeywords.'restfulbooker.Booker.newBooking'()
int id = CustomKeywords.'restfulbooker.Booker.createdId'(booking)
String token = CustomKeywords.'restfulbooker.Booker.token'()

Map denganToken = booking + [firstname: 'Dinda', totalprice: 275]
ResponseObject r1 = CustomKeywords.'restfulbooker.Booker.updateBooking'(id, denganToken, token, '')
WS.verifyResponseStatusCode(r1, 200)
WS.verifyEqual(CustomKeywords.'restfulbooker.Booker.json'(r1), denganToken)

Map denganBasic = booking + [firstname: 'Rina', totalprice: 300]
ResponseObject r2 = CustomKeywords.'restfulbooker.Booker.updateBooking'(id, denganBasic, '', Booker.BASIC_AUTH)
WS.verifyResponseStatusCode(r2, 200)
WS.verifyEqual(CustomKeywords.'restfulbooker.Booker.json'(r2), denganBasic)

WS.verifyEqual(CustomKeywords.'restfulbooker.Booker.json'(CustomKeywords.'restfulbooker.Booker.getBooking'(id, 'application/json')), denganBasic)
