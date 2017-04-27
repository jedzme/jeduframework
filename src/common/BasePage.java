package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {


    /** Timeout for finding page element in seconds */
    protected static final long FIND_ELEMENT_TIMEOUT = 30L;
	
	/** The Selenium2 web driver. */
    protected WebDriver driver;
    
    public BasePage(WebDriver driver)
    {
        this.driver = driver;
    }
    
    
    /**
     * Find the element in the DOM by id.
     * 
     * @param elementId the element id
     * @return the element found
     */
    public WebElement findElement(String elementId, LocatorType locType)
    {
    	WebElement element = null;

        try
        {
        	if(locType.equals(LocatorType.ID)){
            return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                    .visibilityOfElementLocated(By.id(elementId)));
            
        	} else if (locType.equals(LocatorType.NAME)){        		
            return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                    .visibilityOfElementLocated(By.name(elementId)));
            
            } else if (locType.equals(LocatorType.LINKTEXT)){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.linkText(elementId)));
                
            } else if (locType.equals(LocatorType.PARTIALLINKTEXT)){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.partialLinkText(elementId)));
                
            } else if (locType.equals(LocatorType.CSS)){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(elementId)));
                
            } else if (locType.equals(LocatorType.CLASS)){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.className(elementId)));
                
            } else{        		
            return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(elementId)));
            }
        }
        catch (Exception e)
        {
            Logger.log("Element is not found");
            return element;
            
        	}
    	}

    
	    /**
	     * This method will click a web element
	     * 
	     * @param locator
	     * 
	     * 
	     */
        
        public void click (String locator, LocatorType locType){
        	
    	 WebElement element = findElement(locator, locType);
    	 element.click();
        	
        }
        
        /**
         * This method will enter text in a web element
         * 
         * @param locator
         * @param value
         *
            element.submit();
         * 
         */
        public void enterText(String locator, LocatorType locType, String value){
    	
    		WebElement element = findElement(locator, locType);
    		element.sendKeys(value);

        }
        
        /**
         * This method will select a text value from a dropdown
         * 
         * @param locator
         * @param value
         * 
         */
        public void selectDropdownByVisibleText(String locator, LocatorType locType, String value){
        	
        new Select(findElement(locator, locType)).selectByVisibleText(value);
        }

}
	
	

