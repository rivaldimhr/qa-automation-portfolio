import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-05: Login gagal menjawab 401 (BUG-09)
// Bug terbuka BUG-09 (SCRUM-31). Test ini menulis perilaku yang BENAR, jadi GAGAL selama bug belum diperbaiki. Login dengan kata sandi salah seharusnya 401, bukan 200.

ResponseObject r = CustomKeywords.'restfulbooker.Booker.auth'('admin', 'salah')
WS.verifyResponseStatusCode(r, 401)
