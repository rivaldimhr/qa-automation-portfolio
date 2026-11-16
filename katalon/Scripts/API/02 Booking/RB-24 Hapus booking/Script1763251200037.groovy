import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-24: Hapus booking
// Story SCRUM-16. DELETE /booking/{id} dengan token berhasil (2xx), lalu GET booking itu menjawab 404.

int id = CustomKeywords.'restfulbooker.Booker.createdId'(CustomKeywords.'restfulbooker.Booker.newBooking'())
String token = CustomKeywords.'restfulbooker.Booker.token'()

ResponseObject r = CustomKeywords.'restfulbooker.Booker.deleteBooking'(id, token, '')
WS.verifyResponseStatusCodeInRange(r, 200, 299)

WS.verifyResponseStatusCode(CustomKeywords.'restfulbooker.Booker.getBooking'(id, 'application/json'), 404)
