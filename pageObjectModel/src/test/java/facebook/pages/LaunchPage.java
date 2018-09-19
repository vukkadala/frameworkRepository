package facebook.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.Constants;
import base.BasePage;

public class LaunchPage extends BasePage{
	
	public LaunchPage(WebDriver driver,ExtentTest test) {
		
		super(driver,test);
		
	}
	
	public LoginPage goToFBLoginPage()
	{
		test.log(LogStatus.INFO, "opening the "+Constants.HOMEPAGE_URL);
		driver.get(Constants.HOMEPAGE_URL);
		test.log(LogStatus.INFO, "opened "+Constants.HOMEPAGE_URL);
		LoginPage loginPage=new LoginPage(driver,test);
		PageFactory.initElements(driver,loginPage );
		return loginPage;
	}

}
