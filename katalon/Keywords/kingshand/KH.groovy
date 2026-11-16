package kingshand

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.regex.Pattern

import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

/**
 * Keyword untuk KINGSHANDs: membuka aplikasi, login per peran, navigasi, formulir, dan asersi teks.
 *
 * Dipanggil dari test case lewat CustomKeywords, misalnya:
 *   CustomKeywords.'kingshand.KH.loginAs'('pj')
 */
class KH {

	/** Ambil objek dari Object Repository/KINGSHANDs, dengan parameter ${...} bila ada. */
	static TestObject obj(String path, Map params = [:]) {
		return findTestObject('KINGSHANDs/' + path, params)
	}

	static int timeout() {
		return GlobalVariable.TIMEOUT as int
	}

	private static List<String> account(String role) {
		if (role == 'pj') {
			return [GlobalVariable.PJ_EMAIL as String, GlobalVariable.PJ_PASSWORD as String]
		}
		if (role == 'admin') {
			return [GlobalVariable.ADMIN_EMAIL as String, GlobalVariable.ADMIN_PASSWORD as String]
		}
		throw new IllegalArgumentException("Peran tidak dikenal: ${role} (pakai 'pj' atau 'admin')")
	}

	private static String reportKind(String kind) {
		if (kind == 'progres') {
			return 'dev'
		}
		if (kind == 'keuangan') {
			return 'keu'
		}
		throw new IllegalArgumentException("Jenis laporan tidak dikenal: ${kind} (pakai 'progres' atau 'keuangan')")
	}

	// ---------- membuka aplikasi dan login ----------

	/**
	 * Buka KINGSHANDs dengan data contoh yang masih asli.
	 * Data demo disimpan di localStorage, jadi dibersihkan dulu agar setiap test case mandiri.
	 */
	@Keyword
	def openApp() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(1366, 900)
		WebUI.navigateToUrl(GlobalVariable.BASE_URL as String)
		WebUI.executeJavaScript('localStorage.clear(); sessionStorage.clear();', null)
		WebUI.refresh()
		shouldBeVisible(obj('Login/form_Login'))
	}

	@Keyword
	def login(String email, String password) {
		WebUI.setText(obj('Login/input_Email'), email)
		WebUI.setText(obj('Login/input_KataSandi'), password)
		WebUI.click(obj('Login/btn_Masuk'))
	}

	/** Login dengan akun demo: 'pj' (penanggung jawab) atau 'admin' (admin lembaga). */
	@Keyword
	def loginAs(String role) {
		List<String> acc = account(role)
		login(acc[0], acc[1])
		shouldBeVisible(obj('Header/txt_Sapaan'))
	}

	@Keyword
	def logout() {
		WebUI.click(obj('Header/btn_Keluar'))
		shouldBeVisible(obj('Login/form_Login'))
	}

	/** Keluar, lalu masuk dengan peran lain (untuk alur antarperan). */
	@Keyword
	def switchTo(String role) {
		logout()
		loginAs(role)
	}

	// ---------- navigasi ----------

	/** Buka halaman lewat hash URL, misalnya 'projects', 'review', atau 'project/4'. */
	@Keyword
	def goTo(String route) {
		// event hashchange dikirim langsung supaya halaman sudah berganti saat langkah berikutnya diperiksa
		WebUI.executeJavaScript('location.hash = arguments[0]; window.dispatchEvent(new HashChangeEvent("hashchange"));', Arrays.asList(route))
	}

	@Keyword
	def openMenu(String label) {
		WebUI.click(obj('Header/btn_Menu', [label: label]))
	}

	@Keyword
	def openProject(int id) {
		goTo('project/' + id)
		shouldBeVisible(obj('Detail/txt_NamaProyek'))
	}

	/** Kembalikan data demo ke data contoh (tombol "Reset data demo"), untuk mengulang skenario dalam satu test case. */
	@Keyword
	def resetData() {
		WebUI.click(obj('Header/btn_ResetData'))
		verifyContains(obj('Header/txt_Toast'), 'Data demo dikembalikan')
	}

	/** Teks menu utama, tanpa angka penghitung (<span class="count">). */
	@Keyword
	List menuLabels() {
		return WebUI.executeJavaScript('return Array.from(document.querySelectorAll("#mainNav button")).map(function (b) { return b.firstChild.textContent.trim(); });', null) as List
	}

	/** Angka penghitung pada menu "Evaluasi proposal" (0 jika tidak tampil). */
	@Keyword
	int pendingProposalCount() {
		Object n = WebUI.executeJavaScript('var b = Array.from(document.querySelectorAll("#mainNav button")).filter(function (x) { return x.firstChild.textContent.trim() === "Evaluasi proposal"; })[0]; var c = b && b.querySelector(".count"); return c ? Number(c.textContent) : 0;', null)
		return n as int
	}

	@Keyword
	def openNotifications() {
		WebUI.click(obj('Header/btn_Notifikasi'))
		shouldBeVisible(obj('Header/panel_Notifikasi'))
	}

	@Keyword
	def closeNotifications() {
		WebUI.click(obj('Header/btn_Notifikasi'))
		WebUI.waitForElementNotVisible(obj('Header/panel_Notifikasi'), timeout())
	}

	// ---------- formulir ----------

	/**
	 * Pilih file untuk <input type="file">. Aplikasi menyembunyikan input ini (display:none)
	 * di balik label bergaya tombol, jadi input ditampilkan dulu sebelum file dikirim.
	 * File PDF kecil dibuat di folder sementara dengan nama yang diminta.
	 */
	@Keyword
	def chooseFile(TestObject input, String fileName) {
		File dir = new File(System.getProperty('java.io.tmpdir'), 'kingshand-katalon')
		dir.mkdirs()
		File file = new File(dir, fileName)
		file.bytes = '%PDF-1.4 laporan uji'.getBytes('UTF-8')
		WebElement el = WebUI.findWebElement(input, timeout())
		WebUI.executeJavaScript('arguments[0].style.display = "inline-block";', Arrays.asList(el))
		WebUI.uploadFile(input, file.absolutePath)
	}

	/** Isi nilai lewat JavaScript, untuk input yang formatnya bergantung locale browser (type="month"). */
	@Keyword
	def setValue(TestObject to, String value) {
		WebElement el = WebUI.findWebElement(to, timeout())
		WebUI.executeJavaScript('arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event("input", {bubbles: true})); arguments[0].dispatchEvent(new Event("change", {bubbles: true}));', Arrays.asList(el, value))
	}

	private static final Map<String, String> PROPOSAL_FIELDS = [
		judul    : 'input_Judul',
		lokasi   : 'input_Lokasi',
		target   : 'input_Target',
		dana     : 'input_Dana',
		mulai    : 'input_RencanaMulai',
		deskripsi: 'input_Deskripsi'
	]

	/** Isi form proposal. Kunci: judul, lokasi, target, dana, mulai (yyyy-MM), deskripsi. */
	@Keyword
	def fillProposal(Map data) {
		data.each { key, value ->
			String field = PROPOSAL_FIELDS[key as String]
			if (field == null) {
				throw new IllegalArgumentException("Kolom proposal tidak dikenal: ${key}")
			}
			if (key == 'mulai') {
				setValue(obj('Proposal/' + field), value as String)
			} else {
				WebUI.setText(obj('Proposal/' + field), value as String)
			}
		}
	}

	/** Isi form proposal, lampirkan file (null = tanpa lampiran), lalu ajukan. */
	@Keyword
	def submitProposal(Map data, String attachment) {
		fillProposal(data)
		if (attachment) {
			chooseFile(obj('Proposal/input_FileProposal'), attachment)
		}
		WebUI.click(obj('Proposal/btn_Ajukan'))
	}

	/**
	 * Kirim laporan dari halaman detail proyek.
	 * kind: 'progres' atau 'keuangan'; value null = kolom nilai dibiarkan kosong; fileName null = tanpa file.
	 */
	@Keyword
	def sendReport(String kind, Object value, String fileName) {
		String k = reportKind(kind)
		if (fileName) {
			chooseFile(obj('Detail/input_FileLaporan', [kind: k]), fileName)
		}
		if (value != null) {
			WebUI.setText(obj('Detail/input_NilaiLaporan', [kind: k]), value.toString())
		}
		WebUI.click(obj('Detail/btn_KirimLaporan', [kind: k]))
	}

	@Keyword
	def approveProposal(String title, String note) {
		WebUI.setText(obj('Review/input_Catatan', [judul: title]), note)
		WebUI.click(obj('Review/btn_Setujui', [judul: title]))
	}

	@Keyword
	def rejectProposal(String title, String note) {
		WebUI.setText(obj('Review/input_Catatan', [judul: title]), note)
		WebUI.click(obj('Review/btn_Tolak', [judul: title]))
	}

	@Keyword
	def acceptReport(String fileName, String note) {
		WebUI.setText(obj('Reports/input_Catatan', [file: fileName]), note)
		WebUI.click(obj('Reports/btn_Terima', [file: fileName]))
	}

	@Keyword
	def requestRevision(String fileName, String note) {
		WebUI.setText(obj('Reports/input_Catatan', [file: fileName]), note)
		WebUI.click(obj('Reports/btn_MintaRevisi', [file: fileName]))
	}

	private static final Map<String, String> PROFILE_FIELDS = [
		nama      : 'input_Nama',
		email     : 'input_Email',
		organisasi: 'input_Organisasi',
		telepon   : 'input_Telepon'
	]

	/** Ubah profil. Kunci: nama, email, organisasi, telepon. */
	@Keyword
	def editProfile(Map values) {
		WebUI.click(obj('Header/btn_Profil'))
		WebUI.click(obj('Profile/btn_Ubah'))
		values.each { key, value ->
			WebUI.setText(obj('Profile/' + PROFILE_FIELDS[key as String]), value as String)
		}
		WebUI.click(obj('Profile/btn_Simpan'))
	}

	/** Nama proyek yang tampil di halaman Proyek, berurutan. */
	@Keyword
	List projectNames() {
		return WebUI.executeJavaScript('return Array.from(document.querySelectorAll("#projList .pitem strong")).map(function (e) { return e.textContent.trim(); });', null) as List
	}

	// ---------- asersi ----------

	@Keyword
	def shouldBeVisible(TestObject to) {
		WebUI.waitForElementVisible(to, timeout())
		WebUI.verifyElementVisible(to)
	}

	/** Isi teks elemen (textContent) dengan spasi dirapikan. Tetap terbaca walau elemen sedang memudar, misalnya toast. */
	@Keyword
	String textOf(TestObject to) {
		WebUI.waitForElementPresent(to, timeout())
		String raw = WebUI.getAttribute(to, 'textContent')
		return (raw ?: '').replaceAll('\\s+', ' ').trim()
	}

	@Keyword
	def verifyText(TestObject to, String expected) {
		WebUI.verifyEqual(textOf(to), expected)
	}

	@Keyword
	def verifyContains(TestObject to, String expected) {
		WebUI.verifyMatch(textOf(to), '(?s).*' + Pattern.quote(expected) + '.*', true)
	}

	@Keyword
	def verifyNotContains(TestObject to, String unexpected) {
		WebUI.verifyNotMatch(textOf(to), '(?s).*' + Pattern.quote(unexpected) + '.*', true)
	}
}
