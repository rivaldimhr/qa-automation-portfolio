import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-01: Health check
// Story SCRUM-15. GET /ping menjawab 201 Created.

ResponseObject r = CustomKeywords.'restfulbooker.Booker.ping'()
WS.verifyResponseStatusCode(r, 201)
