package restfulbooker

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import internal.GlobalVariable

/**
 * Keyword untuk Restful-Booker. Setiap request memakai objek di Object Repository/RestfulBooker,
 * dan mengembalikan ResponseObject apa adanya supaya test case bisa memeriksa status, header, dan body.
 *
 * Dipanggil dari test case lewat CustomKeywords, misalnya:
 *   CustomKeywords.'restfulbooker.Booker.createBooking'(booking)
 */
class Booker {

	/** admin:password123 dalam Base64, didokumentasikan oleh Restful-Booker. */
	static final String BASIC_AUTH = 'Basic YWRtaW46cGFzc3dvcmQxMjM='

	private static ResponseObject send(String request, Map variables) {
		Map vars = [baseUrl: GlobalVariable.RB_URL, token: '', authorization: '', accept: 'application/json']
		vars.putAll(variables)
		return WS.sendRequest(findTestObject('RestfulBooker/' + request, vars))
	}

	@Keyword
	ResponseObject ping() {
		return send('GET_Ping', [:])
	}

	@Keyword
	ResponseObject auth(String username, String password) {
		return send('POST_Auth', [username: username, password: password])
	}

	/** Login sebagai admin dan kembalikan token. */
	@Keyword
	String token() {
		ResponseObject r = auth(GlobalVariable.RB_USER as String, GlobalVariable.RB_PASSWORD as String)
		WS.verifyResponseStatusCode(r, 200)
		return json(r).token as String
	}

	@Keyword
	ResponseObject createBooking(Map booking) {
		return send('POST_CreateBooking', [body: JsonOutput.toJson(booking)])
	}

	@Keyword
	ResponseObject getBooking(Object id, String accept) {
		return send('GET_Booking', [id: id, accept: accept])
	}

	@Keyword
	ResponseObject searchBooking(String firstname, String lastname) {
		return send('GET_SearchBooking', [firstname: firstname, lastname: lastname])
	}

	/** PUT booking. Isi token (cookie) atau authorization (Basic); keduanya kosong = tanpa login. */
	@Keyword
	ResponseObject updateBooking(Object id, Map booking, String token, String authorization) {
		return send('PUT_UpdateBooking', [id: id, body: JsonOutput.toJson(booking), token: token ?: '', authorization: authorization ?: ''])
	}

	@Keyword
	ResponseObject patchBooking(Object id, Map fields, String token, String authorization) {
		return send('PATCH_PartialUpdateBooking', [id: id, body: JsonOutput.toJson(fields), token: token ?: '', authorization: authorization ?: ''])
	}

	@Keyword
	ResponseObject deleteBooking(Object id, String token, String authorization) {
		return send('DELETE_Booking', [id: id, token: token ?: '', authorization: authorization ?: ''])
	}

	@Keyword
	Object json(ResponseObject r) {
		return new JsonSlurper().parseText(r.getResponseText())
	}

	/** Booking valid dengan nama belakang unik, supaya test tidak bertabrakan di server bersama. */
	@Keyword
	Map newBooking() {
		return [
			firstname      : 'Rivaldi',
			lastname       : 'QA-' + UUID.randomUUID().toString().substring(0, 8),
			totalprice     : 150,
			depositpaid    : true,
			bookingdates   : [checkin: '2026-12-01', checkout: '2026-12-05'],
			additionalneeds: 'Breakfast'
		]
	}

	/** Buat booking dan kembalikan bookingid-nya. */
	@Keyword
	int createdId(Map booking) {
		ResponseObject r = createBooking(booking)
		WS.verifyResponseStatusCode(r, 200)
		return json(r).bookingid as int
	}

	/** Periksa bentuk booking: kolom wajib ada dengan tipe yang benar (pengganti JSON schema). */
	@Keyword
	def verifyBookingShape(Map b) {
		assert b.firstname instanceof String
		assert b.lastname instanceof String
		assert b.totalprice instanceof Integer
		assert b.depositpaid instanceof Boolean
		assert b.bookingdates instanceof Map
		assert b.bookingdates.checkin ==~ /\d{4}-\d{2}-\d{2}/
		assert b.bookingdates.checkout ==~ /\d{4}-\d{2}-\d{2}/
		if (b.containsKey('additionalneeds')) {
			assert b.additionalneeds instanceof String
		}
	}
}
