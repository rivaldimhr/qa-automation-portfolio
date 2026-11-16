import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-10: Buat booking lalu baca kembali
// Story SCRUM-16. POST /booking menjawab 200 dengan bookingid dan data yang sama dengan yang dikirim; bentuk data sesuai skema; GET /booking/{id} mengembalikan data yang sama.

Map booking = CustomKeywords.'restfulbooker.Booker.newBooking'()

ResponseObject created = CustomKeywords.'restfulbooker.Booker.createBooking'(booking)
WS.verifyResponseStatusCode(created, 200)
WS.verifyElementPropertyValue(created, 'booking.firstname', booking.firstname)
WS.verifyElementPropertyValue(created, 'booking.lastname', booking.lastname)

Map body = CustomKeywords.'restfulbooker.Booker.json'(created)
assert body.bookingid instanceof Integer
CustomKeywords.'restfulbooker.Booker.verifyBookingShape'(body.booking)
WS.verifyEqual(body.booking, booking)

ResponseObject read = CustomKeywords.'restfulbooker.Booker.getBooking'(body.bookingid, 'application/json')
WS.verifyResponseStatusCode(read, 200)
WS.verifyEqual(CustomKeywords.'restfulbooker.Booker.json'(read), booking)
