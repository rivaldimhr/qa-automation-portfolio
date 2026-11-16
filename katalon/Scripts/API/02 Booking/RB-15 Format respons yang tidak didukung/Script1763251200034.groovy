import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-15: Format respons yang tidak didukung
// Story SCRUM-16. Teknik: EP. GET dengan Accept: text/plain menjawab 418.

int id = CustomKeywords.'restfulbooker.Booker.createdId'(CustomKeywords.'restfulbooker.Booker.newBooking'())

ResponseObject r = CustomKeywords.'restfulbooker.Booker.getBooking'(id, 'text/plain')
WS.verifyResponseStatusCode(r, 418)
