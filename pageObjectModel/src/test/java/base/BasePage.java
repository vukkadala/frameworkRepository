package base;

import java.io.File;
import java.io.IOException;
import java.util.Date;





import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import common.TopMenu;

public class BasePage {

	public WebDriver driver;
	public TopMenu topMenu;
	public ExtentTest test;
	
	public BasePage() {
		// TODO Auto-generated constructor stub
	}
	
	public BasePage(WebDriver driver,ExtentTest test) {
		this.driver=driver;
		this.test=test;
		topMenu=PageFactory.initElements(driver, TopMenu.class);
	}
	
	public String verifyTitle(String expectedText)
	{
		test.log(LogStatus.INFO, "verify the title with "+expectedText );
		return " ";
	}
	
	public boolean isElementPresent(String locator)
	{
		test.log(LogStatus.INFO, "trying to find "+locator);
		int s=driver.findElements(By.xpath(locator)).size();
		if(s>0)
		{
			test.log(LogStatus.INFO, locator+" element found");
			return true;
		}
		else
		{
			test.log(LogStatus.INFO, locator+" element NOT found");
			return false;
		}
	}
	
	public TopMenu getTopMenu()
	{
		return topMenu;
	}
	
	public void takesScreenshot()
	{
		Date date=new Date();
		String screenshotFile=date.toString().replace(":", "_").replace(" ", "_")+".png";
		File sourceFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String fileName=System.getProperty("user.dir")+"//screenshots//"+screenshotFile;
		
		try {
			FileHandler.copy(sourceFile, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO, test.addScreenCapture(fileName));
	}
}
