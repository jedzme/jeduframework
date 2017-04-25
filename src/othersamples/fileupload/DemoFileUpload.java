package othersamples.fileupload;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

public class DemoFileUpload{
	
	@Test
	public void testFileUpload() throws InterruptedException, IOException{
//		System.setProperty("webdriver.edge.driver", "resources\\drivers\\MicrosoftWebDriver_3.14393.exe");
		
		System.setProperty("webdriver.chrome.driver", "resources\\drivers\\chromedriver_2.29.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.manage().window().maximize();
		
		String directory = System.getProperty("user.dir");
		
		driver.get("file:///" + directory + "/etc/samplepages/fileUploadSample.html");
		
		//You need to install AutoIT3 inorder to run the exec file
		//visit https://www.autoitscript.com/site/autoit/downloads/
		Runtime.getRuntime().exec("etc\\autoitscriptsx64\\compiled\\fileupload.exe");
		
		driver.findElement(By.xpath(".//*[@id='btnUpload']")).click();
		
		Thread.sleep(5000);
		
		driver.quit();
	}
	

}
