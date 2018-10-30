package pageobjects;

import org.openqa.selenium.WebDriver;

import common.BasePage;
import common.LocatorType;
import common.Logger;

public class GooglePage extends BasePage{

	public static final String SEARCH_BAR_NAME = "q";
	public static final String GOOGLE_SEARCH_BUTTON_XPATH = "//input[@value='Google Search' and @name='btnK']";
	public static final String EXPECTED_ELEMENT_XPATH = "//*[@id=\"rhs_block\"]/div[2]/div[1]/div/div[1]/div[2]/div[2]/div/div[2]/div/div/div[2]/div[1]/span";
	//public static final String EXPECTED_ELEMENT_XPATH = "//*[@id='rhs_block']/div/div[1]/div/div[1]/div[2]/div[1]/div/div[2]/div/div]";

	public GooglePage(WebDriver driver) {
		super(driver);

	}

	public void enterSearchBar(String input, int timeOutInSeconds) throws Exception{
		enterText(SEARCH_BAR_NAME, LocatorType.NAME, input, timeOutInSeconds);
		Logger.log("Entered value in search bar: " + input);
	}	
	
	public void clickSearchButton(int timeOutInSeconds){

		click(GOOGLE_SEARCH_BUTTON_XPATH, LocatorType.NAME, timeOutInSeconds);
		Logger.log("Clicked search button...");
	}

}
