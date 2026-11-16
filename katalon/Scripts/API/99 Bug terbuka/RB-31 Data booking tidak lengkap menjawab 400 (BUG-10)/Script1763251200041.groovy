import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-31: Data booking tidak lengkap menjawab 400 (BUG-10)
// Bug terbuka BUG-10 (SCRUM-32). Test ini menulis perilaku yang BENAR, jadi GAGAL selama bug belum diperbaiki. POST /booking tanpa firstname seharusnya 400, bukan 500.

Map booking = CustomKeywords.'restfulbooker.Booker.newBooking'()
booking.remove('firstname')

WS.verifyResponseStatusCode(CustomKeywords.'restfulbooker.Booker.createBooking'(booking), 400)
