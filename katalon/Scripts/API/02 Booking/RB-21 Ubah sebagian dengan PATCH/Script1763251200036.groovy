import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-21: Ubah sebagian dengan PATCH
// Story SCRUM-16. PATCH /booking/{id} dengan additionalneeds saja: hanya kolom itu yang berubah.

Map booking = CustomKeywords.'restfulbooker.Booker.newBooking'()
int id = CustomKeywords.'restfulbooker.Booker.createdId'(booking)

ResponseObject r = CustomKeywords.'restfulbooker.Booker.patchBooking'(id, [additionalneeds: 'Late checkout'], '', Booker.BASIC_AUTH)
WS.verifyResponseStatusCode(r, 200)
WS.verifyEqual(CustomKeywords.'restfulbooker.Booker.json'(r), booking + [additionalneeds: 'Late checkout'])
