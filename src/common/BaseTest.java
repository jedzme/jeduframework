package common;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

public abstract class BaseTest {

	final String defaultChromeDriverPath = "resources\\drivers\\chromedriver.exe";
	protected WebDriver driver;
	protected TestDataProvider testDataProvider;
	String screenShotsPath;
	String testCaseName;

	public void startTest(DriverType driverType, String screenShotsPath, TestDataProvider testDataProvider) {

		this.testDataProvider = testDataProvider;
		this.screenShotsPath = screenShotsPath;
		this.testCaseName = this.getClass().getSimpleName();

		log("");
		log(">>>>> Executing " + testCaseName + " <<<<<");
		log("");

		if (driverType.equals(DriverType.CHROME)) {
			Logger.log("Setting Chrome Driver as default driver");
			System.setProperty("webdriver.chrome.driver", defaultChromeDriverPath);
			log("Running test in Chrome");
			driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			driver.manage().window().maximize();
		}

	}

	public String getTestCaseName() {
		return this.testCaseName;
	}

	@AfterMethod
	public void endTest() throws IOException {
		String TestCaseName = this.getClass().getSimpleName();
		log("");
		log(">>>>> Terminating " + TestCaseName + " <<<<<");
		log("");

		if (testDataProvider != null) {
			log("Closing Test Data Provider...");
			testDataProvider.close();
		}
		if (driver != null) {
			// driver.close();
			log("Closing Driver...");
			driver.quit();
		}

	}

	public static void log(String message) {
		Logger.log(message);
		Reporter.log(message);
	}

	public void takescreenshot(String screenShotName) throws IOException {
		File sourceFile;
		try {
			sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			/*
			 * FileUtils.copyFile(scrFile, new File(
			 * "C:\\Workspace\\SeleniumProject\\test-reports\\screenshots\\" +
			 * getFileName(this.getClass().getSimpleName())));
			 */
			FileUtils.copyFile(sourceFile, new File(
					screenShotsPath + screenShotName + ".png"));

		} catch (Exception e) {
			log("Screenshot is not created.");
			e.printStackTrace();
		}
	}

	/*private String getFileName(String nameTest) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
		Date date = new Date();
		return dateFormat.format(date) + "_" + nameTest + ".png";
	}*/

	public void assertTextPresentInElement(String locator, LocatorType locType, String valueToCheck) {

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		if (locType.equals(LocatorType.ID)) {

			Assert.assertTrue(driver.findElement(By.id(locator)).getText().equals(valueToCheck));
			log("The text " + valueToCheck + " is present in the web element.");

		} else if (locType.equals(LocatorType.NAME)) {

			Assert.assertTrue(driver.findElement(By.name(locator)).getText().equals(valueToCheck));
			log("The text " + valueToCheck + " is present in the web element.");

		} else if (locType.equals(LocatorType.CLASS)) {

			Assert.assertTrue(driver.findElement(By.className(locator)).getText().equals(valueToCheck));
			log("The text " + valueToCheck + " is present in the web element.");

		} else {

			Assert.assertTrue(driver.findElement(By.xpath(locator)).getText().equals(valueToCheck));
			log("The text " + valueToCheck + " is present in the web element.");
		}
	}

	public void assertElementPresentInPage(String locator, LocatorType locType) {

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		Assert.assertTrue(isElementPresent(locator, locType));
		log("Element is present in the page.");

	}

	public Boolean isElementPresent(String locator, LocatorType locType) {
		boolean found = true;
		try {
			if (locType.equals(LocatorType.ID)) {

				driver.findElement(By.id(locator));

			} else if (locType.equals(LocatorType.NAME)) {

				driver.findElement(By.name(locator));

			} else if (locType.equals(LocatorType.CLASS)) {

				driver.findElement(By.className(locator));

			} else {

				driver.findElement(By.xpath(locator));

			}

		} catch (NoSuchElementException e) {
			return false;
		}

		return found;
	}

}