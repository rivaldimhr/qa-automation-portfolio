import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import restfulbooker.Booker as Booker

// RB-04: Login gagal tanpa token
// Story SCRUM-15. Teknik: EP, negative. Kata sandi salah, user salah, dan kosong dijawab {"reason": "Bad credentials"} tanpa token.

def kasus = [
	[username: 'admin', password: 'salah', keterangan: 'kata sandi salah'],
	[username: 'bukanadmin', password: 'password123', keterangan: 'user salah'],
	[username: '', password: '', keterangan: 'kosong']
]

for (Map k : kasus) {
	WS.comment("Login gagal: ${k.keterangan}")
	ResponseObject r = CustomKeywords.'restfulbooker.Booker.auth'(k.username, k.password)
	Map body = CustomKeywords.'restfulbooker.Booker.json'(r)
	WS.verifyEqual(body.reason, 'Bad credentials')
	WS.verifyEqual(body.containsKey('token'), false)
}
