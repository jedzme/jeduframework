package pageobjects;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import common.BasePage;
import common.LocatorType;

public class MercuryRegisterPage extends BasePage{
	
	public static final String FIRST_NAME_NAME = "firstName";

	public MercuryRegisterPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void populateFields(HashMap<String, String> fieldsMap){
		enterText(FIRST_NAME_NAME, LocatorType.NAME, fieldsMap.get(FIRST_NAME_NAME));
	
	}

	
	
}
