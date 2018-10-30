package common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

	/** The Selenium2 web driver. */
	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Find the element in the DOM by locator.
	 * 
	 * @param locator
	 *            The locator of the element
	 * @param locatorType
	 *            The locator type used
	 * @param timeOutInSeconds
	 *            If the web element takes time to load, supply value greater
	 *            than 0
	 * 
	 */
	public WebElement findElement(String locator, LocatorType locatorType, int timeOutInSeconds) {
		WebElement element = null;

		try {
			if (timeOutInSeconds > 0) {
				if (locatorType.equals(LocatorType.ID)) {
					return new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));

				} else if (locatorType.equals(LocatorType.NAME)) {
					return new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.name(locator)));

				} else if (locatorType.equals(LocatorType.LINKTEXT)) {
					return new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locator)));

				} else if (locatorType.equals(LocatorType.PARTIALLINKTEXT)) {
					return new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locator)));

				} else if (locatorType.equals(LocatorType.CSS)) {
					return new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator)));

				} else if (locatorType.equals(LocatorType.CLASS)) {
					return new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.className(locator)));

				} else if (locatorType.equals(LocatorType.XPATH)) {
					return new WebDriverWait(driver, timeOutInSeconds)
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
				} else {
					throw new Exception("Unknown Locator");
				}
			}
			// Else use WebDriver's default timeout settings
			else {
				if (locatorType.equals(LocatorType.ID)) {
					return driver.findElement(By.id(locator));

				} else if (locatorType.equals(LocatorType.NAME)) {
					return driver.findElement(By.name(locator));

				} else if (locatorType.equals(LocatorType.LINKTEXT)) {
					return driver.findElement(By.linkText(locator));

				} else if (locatorType.equals(LocatorType.PARTIALLINKTEXT)) {
					return driver.findElement(By.partialLinkText(locator));

				} else if (locatorType.equals(LocatorType.CSS)) {
					return driver.findElement(By.cssSelector(locator));

				} else if (locatorType.equals(LocatorType.CLASS)) {
					return driver.findElement(By.className(locator));

				} else if (locatorType.equals(LocatorType.XPATH)) {
					return driver.findElement(By.xpath(locator));
				} else {
					throw new Exception("Unknown Locator");
				}
			}
		} catch (Exception e) {
			Logger.log(e.getMessage());
			Logger.log("Element is not found");
		}
		return element;
	}

	/**
	 * This method will click a web element
	 * 
	 * @param locator
	 *            The locator of the element
	 * @param locatorType
	 *            The locator type used
	 * @param timeOutInSeconds
	 *            If the web element takes time to load, supply value greater
	 *            than 0
	 * 
	 */

	public void click(String locator, LocatorType locType, int timeOutInSeconds) {

		WebElement element = findElement(locator, locType, timeOutInSeconds);
		element.click();

	}

	/**
	 * This method will enter text in a web element
	 * 
	 * @param locator
	 *            The locator of the element
	 * @param locatorType
	 *            The locator type used
	 * @param value
	 *            The text to be entered
	 * @param timeOutInSeconds
	 *            If web element takes time to load, supply value greater than 0
	 * 
	 */
	public void enterText(String locator, LocatorType locType, String value, int timeOutInSeconds) {

		WebElement element = findElement(locator, locType, timeOutInSeconds);
		element.sendKeys(value);

	}

	/**
	 * This method will select a text value from a dropdown
	 * 
	 * @param locator
	 *            The locator of the element
	 * @param locatorType
	 *            The locator type used
	 * @param value
	 *            The text to be selected
	 * @param timeOutInSeconds
	 *            If web element takes time to load, supply value greater than 0
	 * 
	 */
	public void selectDropdownByVisibleText(String locator, LocatorType locatorType, int timeOutInSeconds,
			String value) {

		new Select(findElement(locator, locatorType, timeOutInSeconds)).selectByVisibleText(value);
	}

}
