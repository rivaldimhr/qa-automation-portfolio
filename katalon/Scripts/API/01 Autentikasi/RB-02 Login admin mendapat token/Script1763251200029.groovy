import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-02: Login admin mendapat token
// Story SCRUM-15. POST /auth dengan admin/password123 menjawab 200 dan token 15 karakter heksadesimal.

ResponseObject r = CustomKeywords.'restfulbooker.Booker.auth'(GlobalVariable.RB_USER, GlobalVariable.RB_PASSWORD)
WS.verifyResponseStatusCode(r, 200)

String token = CustomKeywords.'restfulbooker.Booker.json'(r).token
WS.verifyMatch(token, '[0-9a-f]{15}', true)
