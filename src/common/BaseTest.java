package common;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;

public abstract class BaseTest {

	protected WebDriver driver;
	protected TestDataProvider testDataProvider;
	protected String screenShotsPath;
	protected String testCaseName;
	protected boolean enableScreenshots;
	protected String webBrowser;

	@BeforeTest
	@Parameters({ "browser", "driverPath", "enableScreenshots", "screenShotsPath", "defaultWDTimeOut" })
	public void startTest(String browser, String driverPath, boolean enableScreenshots, String screenShotsPath,
			int defaultWDTimeOut) {
		this.testCaseName = this.getClass().getSimpleName();
		this.enableScreenshots = enableScreenshots;
		this.screenShotsPath = screenShotsPath;

		log("=====Settings=====");
		log("Screenshots: " + "[Enabled: " + enableScreenshots + "]" + "," + "[Location: " + screenShotsPath + "]");

		log("");
		log(">>>>> Executing " + testCaseName + " <<<<<");
		log("");

		if (browser.equalsIgnoreCase("chrome")) {
			Logger.log("Running in Google Chrome");
			this.webBrowser = "Chrome";
			System.setProperty("webdriver.chrome.driver", driverPath);
			// ChromeDriverManager.getInstace().setup();
			driver = new ChromeDriver();
		}

		else if (browser.equalsIgnoreCase("firefox")) {
			Logger.log("Running in Firefox");
			this.webBrowser = "FireFox";
			FirefoxDriverManager.getInstance().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			Logger.log("Running in Microsoft Edge");
			this.webBrowser = "Microsoft Edge";
			System.setProperty("webdriver.edge.driver", driverPath);
			/*
			 * EdgeDriverManager.getInstance().setup();
			 * System.setProperty("wdm.override", "true");
			 * System.setProperty("wdm.edgeVersion", "3.14393");
			 */
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("ie")) {
			Logger.log("Running in Internet Explorer");
			this.webBrowser = "Internet Explorer";
			InternetExplorerDriverManager.getInstance().setup();
			driver = new InternetExplorerDriver();
		} else if (browser.equalsIgnoreCase("opera")) {
			Logger.log("Running in Opera");
			this.webBrowser = "Opera";
			OperaDriverManager.getInstance().setup();
			driver = new OperaDriver();
		}
		// TODO: add Safari and implement WebDriverManager to all Drivers

		// Default timeout setting for our drivers - 10secs
		driver.manage().timeouts().implicitlyWait(defaultWDTimeOut, TimeUnit.SECONDS);

		driver.manage().window().maximize();

	}

	public void setTestDataProvider(TestDataProvider testDataProvider) {

		this.testDataProvider = testDataProvider;

	}

	public String getTestCaseName() {
		return this.testCaseName;
	}

	@AfterTest
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

		if (enableScreenshots) {
			try {
				sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				/*
				 * FileUtils.copyFile(scrFile, new File(
				 * "C:\\Workspace\\SeleniumProject\\test-reports\\screenshots\\"
				 * + getFileName(this.getClass().getSimpleName())));
				 */
				FileUtils.copyFile(sourceFile, new File(screenShotsPath + webBrowser + "_" + screenShotName + ".png"));

			} catch (Exception e) {
				log("Screenshot is not created.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * This assert if an exact Text is present in the specified Element
	 * 
	 * @param String locator - i.e \\*[@id='sampleID'
	 * @param LocatorType locType - i.e LocatorType.XPATH
	 * @param String valueToCheck - any text
	 * @param int timeOutInSeconds - if 0, then does not invoke explicit waiting
	 *            
	 * TODO: Move WebDriverWait to either BaseTest or BasePage
	 * 
	 */
	public void assertTextPresentInElement(String locator, LocatorType locType, String valueToCheck,
			int timeOutInSeconds) {

		if (timeOutInSeconds > 0) {
			if (locType.equals(LocatorType.ID)) {

				Assert.assertTrue((new WebDriverWait(driver, timeOutInSeconds))
						.until(ExpectedConditions.presenceOfElementLocated(By.id(locator))).getText()
						.equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else if (locType.equals(LocatorType.NAME)) {

				Assert.assertTrue((new WebDriverWait(driver, timeOutInSeconds))
						.until(ExpectedConditions.presenceOfElementLocated(By.name(locator))).getText()
						.equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else if (locType.equals(LocatorType.CLASS)) {

				Assert.assertTrue((new WebDriverWait(driver, timeOutInSeconds))
						.until(ExpectedConditions.presenceOfElementLocated(By.className(locator))).getText()
						.equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else if (locType.equals(LocatorType.XPATH)) {

				Assert.assertTrue((new WebDriverWait(driver, timeOutInSeconds))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).getText()
						.equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			}

			else if (locType.equals(LocatorType.LINKTEXT)) {

				Assert.assertTrue((new WebDriverWait(driver, timeOutInSeconds))
						.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locator))).getText()
						.equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			} else if (locType.equals(LocatorType.CSS)) {

				Assert.assertTrue((new WebDriverWait(driver, timeOutInSeconds))
						.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator))).getText()
						.equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			} else if (locType.equals(LocatorType.PARTIALLINKTEXT)) {

				Assert.assertTrue((new WebDriverWait(driver, timeOutInSeconds))
						.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locator))).getText()
						.equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			} else {

			}

		}

		else {
			if (locType.equals(LocatorType.ID)) {

				Assert.assertTrue(driver.findElement(By.id(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else if (locType.equals(LocatorType.NAME)) {

				Assert.assertTrue(driver.findElement(By.name(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else if (locType.equals(LocatorType.CLASS)) {

				Assert.assertTrue(driver.findElement(By.className(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else if (locType.equals(LocatorType.XPATH)) {

				Assert.assertTrue(driver.findElement(By.xpath(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			} else if (locType.equals(LocatorType.LINKTEXT)) {

				Assert.assertTrue(driver.findElement(By.linkText(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			} else if (locType.equals(LocatorType.CSS)) {

				Assert.assertTrue(driver.findElement(By.cssSelector(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			}

			else if (locType.equals(LocatorType.PARTIALLINKTEXT)) {

				Assert.assertTrue(driver.findElement(By.partialLinkText(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");
			} else {

			}
		}

	}

	/**
	 * This assert if an Element is present in the current page
	 * @param String locator - i.e \\*[@id='sampleID'
	 * @param LocatorType locType - i.e LocatorType.XPATH
	 * @param int timeOutInSeconds - if 0, then does not invoke explicit waiting
	 * 
	 */
	public void assertElementPresentInPage(String locator, LocatorType locType, int timeOutInSeconds) {

		Assert.assertTrue(isElementPresent(locator, locType, timeOutInSeconds));
		log("Element is present in the page.");

	}

	private Boolean isElementPresent(String locator, LocatorType locType, int timeOutInSeconds) {
		boolean found = true;

		try {
			if (timeOutInSeconds > 0) {
				if (locType.equals(LocatorType.ID)) {

					new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));

				} else if (locType.equals(LocatorType.NAME)) {

					new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.name(locator)));

				} else if (locType.equals(LocatorType.CLASS)) {

					new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.className(locator)));

				} else if (locType.equals(LocatorType.XPATH)) {

					new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));

				} else if (locType.equals(LocatorType.CSS)) {

					new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator)));

				} else if (locType.equals(LocatorType.PARTIALLINKTEXT)) {

					new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locator)));

				} else {

				}
			} else {
				if (locType.equals(LocatorType.ID)) {

					driver.findElement(By.id(locator));

				} else if (locType.equals(LocatorType.NAME)) {

					driver.findElement(By.name(locator));

				} else if (locType.equals(LocatorType.CLASS)) {

					driver.findElement(By.className(locator));

				} else if (locType.equals(LocatorType.XPATH)) {

					driver.findElement(By.xpath(locator));

				} else if (locType.equals(LocatorType.CSS)) {

					driver.findElement(By.cssSelector(locator));

				} else if (locType.equals(LocatorType.PARTIALLINKTEXT)) {

					driver.findElement(By.partialLinkText(locator));

				} else {

				}

			}

		} catch (NoSuchElementException e) {
			return false;
		}

		return found;
	}

}