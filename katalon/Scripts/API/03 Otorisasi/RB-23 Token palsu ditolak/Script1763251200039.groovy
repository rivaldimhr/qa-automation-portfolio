import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-23: Token palsu ditolak
// Story SCRUM-17. Teknik: negative. DELETE dengan token 15 nol menjawab 403 dan booking tetap ada.

int id = CustomKeywords.'restfulbooker.Booker.createdId'(CustomKeywords.'restfulbooker.Booker.newBooking'())

WS.verifyResponseStatusCode(CustomKeywords.'restfulbooker.Booker.deleteBooking'(id, '000000000000000', ''), 403)
WS.verifyResponseStatusCode(CustomKeywords.'restfulbooker.Booker.getBooking'(id, 'application/json'), 200)
