package test;

import org.testng.annotations.Test;

import common.BaseTest;
import common.ExcelDataProvider;
import common.LocatorType;
import common.Logger;
import pageobjects.GooglePage;

public class GoogleTest extends BaseTest {

	GooglePage googlePage;

	private final String xcelSheetName = "Sample Sheet";
	private final String xcelFilePath = "src\\testdata\\SampleTestData.xlsx";

	// private static final int COLUMN_TEST_CASE_NAME = 0;
	private static final int COLUMN_URL = 1;
	private static final int COLUMN_SAMPLE_INPUT_DATA_1 = 2;
	private static final int COLUMN_SAMPLE_EXPECTED_DATA_1 = 3;
	private static final int COLUMN_SAMPLE_INPUT_DATA_2 = 4;
	private static final int COLUMN_SAMPLE_EXPECTED_DATA_2 = 5;

	@Test
	public void whenSearchingATextSuccessfully() throws Exception {
		

		ExcelDataProvider dataProvider = new ExcelDataProvider(xcelFilePath, xcelSheetName);
		setTestDataProvider(dataProvider);

		googlePage = new GooglePage(this.driver);

		int testCaseRow = dataProvider.getTestCaseRow(getTestCaseName());
		Logger.log("TestCaseRow: " + testCaseRow);
		String webAppUrl = dataProvider.getCellData(testCaseRow, COLUMN_URL);
		driver.get(webAppUrl);
		log("Opened " + webAppUrl);

		takescreenshot("GoogleTest_testCase1_step_1");
		assertElementPresentInPage(GooglePage.SEARCH_BAR_NAME, LocatorType.NAME, 0);

		googlePage.enterSearchBar(dataProvider.getCellData(testCaseRow, COLUMN_SAMPLE_INPUT_DATA_1));
		takescreenshot("GoogleTest_testCase1_step_2");
		googlePage.clickSearchButton();
		assertTextPresentInElement(GooglePage.EXPECTED_ELEMENT_XPATH, LocatorType.XPATH,
				dataProvider.getCellData(testCaseRow, COLUMN_SAMPLE_EXPECTED_DATA_1), 0);
		takescreenshot("GoogleTest_testCase1_step_3");

		dataProvider.close();

	}
	
	@Test
	public void whenSearchingATextSuccessfully2() throws Exception {
		

		ExcelDataProvider dataProvider = new ExcelDataProvider(xcelFilePath, xcelSheetName);
		setTestDataProvider(dataProvider);

		googlePage = new GooglePage(this.driver);

		int testCaseRow = dataProvider.getTestCaseRow(getTestCaseName());
		Logger.log("TestCaseRow: " + testCaseRow);
		String webAppUrl = dataProvider.getCellData(testCaseRow, COLUMN_URL);
		driver.get(webAppUrl);
		log("Opened " + webAppUrl);

		assertElementPresentInPage(GooglePage.SEARCH_BAR_NAME, LocatorType.NAME, 0);
		takescreenshot("GoogleTest_testCase2_step_1");

		googlePage.enterSearchBar(dataProvider.getCellData(testCaseRow, COLUMN_SAMPLE_INPUT_DATA_2));
		takescreenshot("GoogleTest_testCase2_step_2");
		googlePage.clickSearchButton();
		assertTextPresentInElement(GooglePage.EXPECTED_ELEMENT_XPATH, LocatorType.XPATH,
				dataProvider.getCellData(testCaseRow, COLUMN_SAMPLE_EXPECTED_DATA_2), 0);
		takescreenshot("GoogleTest_testCase2_step_3");

		dataProvider.close();

	}


}
