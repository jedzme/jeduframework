package test;

import org.testng.annotations.Test;

import common.BaseTest;
import common.DriverType;
import common.ExcelDataProvider;
import common.LocatorType;
import common.Logger;
import pageobjects.GooglePage;

public class GoogleTest extends BaseTest {

	GooglePage googlePage;

	private final String xcelSheetName = "Sample Sheet";
	private final String xcelFilePath = "resources\\SampleTestData.xlsx";
	private final String screenShotPath = "C:\\testscreenshots\\";

	// private static final int COLUMN_TEST_CASE_NAME = 0;
	private static final int COLUMN_URL = 1;
	private static final int COLUMN_SAMPLE_INPUT_DATA = 2;
	private static final int COLUMN_SAMPLE_EXPECTED_DATA = 3;

	@Test
	public void testCase_1_Chrome() throws Exception {

		ExcelDataProvider dataProvider = new ExcelDataProvider(xcelFilePath, xcelSheetName);
		startTest(DriverType.CHROME, screenShotPath, dataProvider);

		googlePage = new GooglePage(this.driver);

		int testCaseRow = dataProvider.getTestCaseRow(getTestCaseName());
		Logger.log("TestCaseRow: " + testCaseRow);
		String webAppUrl = dataProvider.getCellData(testCaseRow, COLUMN_URL);
		driver.get(webAppUrl);
		log("Opened " + webAppUrl);

		takescreenshot("GoogleTest_testCase_1_Chrome_step_1");
		assertElementPresentInPage(GooglePage.SEARCH_BAR_NAME, LocatorType.NAME);

		googlePage.enterSearchBar(dataProvider.getCellData(testCaseRow, COLUMN_SAMPLE_INPUT_DATA));
		takescreenshot("GoogleTest_testCase_1_Chrome_step_2");
		googlePage.clickSearchButton();
		Thread.sleep(2000);
		assertTextPresentInElement(GooglePage.EXPECTED_ELEMENT_XPATH, LocatorType.XPATH,
				dataProvider.getCellData(testCaseRow, COLUMN_SAMPLE_EXPECTED_DATA));
		takescreenshot("GoogleTest_testCase_1_Chrome_step_3");

		//dataProvider.close();

	}

}
