import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-13: Cari booking berdasarkan nama
// Story SCRUM-16. GET /booking?firstname=...&lastname=... dengan nama unik mengembalikan tepat satu bookingid, milik booking yang baru dibuat.

Map booking = CustomKeywords.'restfulbooker.Booker.newBooking'()
int id = CustomKeywords.'restfulbooker.Booker.createdId'(booking)

ResponseObject r = CustomKeywords.'restfulbooker.Booker.searchBooking'(booking.firstname, booking.lastname)
WS.verifyResponseStatusCode(r, 200)
List ids = CustomKeywords.'restfulbooker.Booker.json'(r).collect { it.bookingid }
WS.verifyEqual(ids, [id])
