import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-14: Booking yang tidak ada
// Story SCRUM-16. Teknik: negative. GET /booking/99999999 menjawab 404.

ResponseObject r = CustomKeywords.'restfulbooker.Booker.getBooking'(99999999, 'application/json')
WS.verifyResponseStatusCode(r, 404)
