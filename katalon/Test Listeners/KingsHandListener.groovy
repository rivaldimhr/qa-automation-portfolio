import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * Listener untuk semua test case:
 *  - mencatat test case yang mulai berjalan;
 *  - untuk test case UI: menyimpan screenshot bila gagal, lalu selalu menutup browser,
 *    supaya test case berikutnya mulai dari browser dan data demo yang bersih.
 */
class KingsHandListener {

	@BeforeTestCase
	def logStart(TestCaseContext context) {
		KeywordUtil.logInfo('Mulai: ' + context.getTestCaseId())
	}

	@AfterTestCase
	def cleanUpBrowser(TestCaseContext context) {
		if (!context.getTestCaseId().contains('/UI/')) {
			return
		}
		if (context.getTestCaseStatus() in ['FAILED', 'ERROR']) {
			WebUI.takeScreenshot(FailureHandling.OPTIONAL)
		}
		WebUI.closeBrowser(FailureHandling.OPTIONAL)
	}
}
