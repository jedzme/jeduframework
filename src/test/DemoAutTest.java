package test;

import java.util.HashMap;

import org.testng.annotations.Test;

import common.BaseTest;
import common.ExcelDataProvider;
import common.LocatorType;
import common.Logger;
import pageobjects.DemoAutLandingPage;
import pageobjects.MercuryRegisterPage;

public class DemoAutTest extends BaseTest {

	DemoAutLandingPage demoAutPage;
	MercuryRegisterPage mercuryRegPage;

	private final String xcelSheetName = "DemoTours";
	private final String xcelFilePath = "src\\testdata\\SampleTestData.xlsx";

	private static final int COLUMN_URL = 1;
	private static final int COLUMN_FIRSTNAME = 2;

	@Test
	public void testRegister() throws Exception {
		HashMap<String, String> registerFields = new HashMap<>();

		ExcelDataProvider dataProvider = new ExcelDataProvider(xcelFilePath, xcelSheetName);

		demoAutPage = new DemoAutLandingPage(this.driver);
		mercuryRegPage = new MercuryRegisterPage(this.driver);

		int testCaseRow = dataProvider.getTestCaseRow(getTestCaseName());
		Logger.log("TestCaseRow: " + testCaseRow);
		String webAppUrl = dataProvider.getCellData(testCaseRow, COLUMN_URL);
		driver.get(webAppUrl);
		log("Opened " + webAppUrl);

		// Assert if you have landed on the DemoAUT Landing Page/ Home Page
		takescreenshot("Demoaut HomePage");
		assertElementPresentInPage(DemoAutLandingPage.REGISTER_BUTTON_LINKTEXT, LocatorType.LINKTEXT, 0);

		// Click Register Link
		log("Clicking the Register Link");
		demoAutPage.clickRegisterLink();

		// Assert if you have landed on the MercuryRegister Page
		takescreenshot("Mercury Register Page");
		assertElementPresentInPage(MercuryRegisterPage.FIRST_NAME_NAME, LocatorType.NAME, 0);

		// Populate the necessary fields in the MercuryRegister Page
		log("Populating the input fields");
		String firstName = dataProvider.getCellData(testCaseRow, COLUMN_FIRSTNAME);
		registerFields.put(MercuryRegisterPage.FIRST_NAME_NAME, firstName);
		mercuryRegPage.populateFields(registerFields);

		// Screenshot adding an input on the First Name input field
		takescreenshot("First Name Populated");
		Thread.sleep(5000);

		dataProvider.close();

	}

}
