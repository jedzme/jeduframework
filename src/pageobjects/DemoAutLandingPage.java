package pageobjects;

import org.openqa.selenium.WebDriver;

import common.BasePage;
import common.LocatorType;

public class DemoAutLandingPage extends BasePage{
	
//	public static final String REGISTER_BUTTON_XPATH = "/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[2]/a";
	public static final String REGISTER_BUTTON_LINKTEXT = "REGISTER";

	public DemoAutLandingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void clickRegisterLink(){
		click(REGISTER_BUTTON_LINKTEXT, LocatorType.LINKTEXT);
	}

}
