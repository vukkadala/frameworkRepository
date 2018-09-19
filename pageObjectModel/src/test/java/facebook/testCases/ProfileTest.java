package facebook.testCases;

import java.util.concurrent.TimeUnit;






import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.BaseTest;
import facebook.pages.LandingPage;
import facebook.pages.LaunchPage;
import facebook.pages.LoginPage;
import facebook.pages.ProfilePage;

public class ProfileTest extends BaseTest{
	
	@Test
	public void testProfile()
	{
		test=extent.startTest("profile test");
		test.log(LogStatus.INFO, "starting profile test");
		init("chorme");
		LaunchPage launchPage=new LaunchPage(driver,test);
		PageFactory.initElements(driver, launchPage);
		LoginPage loginPage=launchPage.goToFBLoginPage();
		loginPage.verifyTitle("facebook login");
		Object page=loginPage.doLogin("mallimurthy2@gmail.com", "computer8");
		
		if(page instanceof LoginPage)
			Assert.fail("login failed");
		LandingPage landingPage=(LandingPage)page;
		landingPage.verifyTitle("welcome malli");
		ProfilePage profilePage=landingPage.goToProfilePage();
		profilePage.verifyTitle("murthy profile");
		profilePage.verifyProfile();
		test.log(LogStatus.PASS, "test is passed");
		profilePage.getTopMenu().logout();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	

}
