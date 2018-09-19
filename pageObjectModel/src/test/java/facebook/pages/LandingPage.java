package facebook.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.Constants;
import base.BasePage;

public class LandingPage extends BasePage{
	
		
	public LandingPage(WebDriver driver,ExtentTest test) {

		super(driver,test);
	}
	
	public ProfilePage goToProfilePage()
	{
		test.log(LogStatus.INFO, "clicking on profile link");
		driver.findElement(By.xpath(Constants.PROFILE_PAGE_LINK)).click();
		ProfilePage profilePage=new ProfilePage(driver,test);
		PageFactory.initElements(driver, profilePage);
		return profilePage;
	}

	
}
