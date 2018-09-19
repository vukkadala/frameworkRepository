package facebook.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.Constants;
import base.BasePage;

public class LoginPage extends BasePage{
	
	@FindBy(xpath=Constants.LOGIN_EMAIL)
	WebElement email;
	
	@FindBy(xpath=Constants.PASSWORD)
	WebElement pass;
	
	@FindBy(xpath=Constants.SIGN_IN_BUTTON)
	WebElement signInButton;
	
	
	public LoginPage(WebDriver driver,ExtentTest test) {
		
		super(driver,test);
		
	}
	
	public Object doLogin(String userName,String password)
	{
		test.log(LogStatus.INFO, "logging with "+userName+" and "+password);
		email.sendKeys(userName);
		pass.sendKeys(password);
		signInButton.click();
		//validating logic
		boolean loginSuccess=isElementPresent(Constants.PROFILE_PAGE_LINK);
		if(loginSuccess)
		{
			test.log(LogStatus.INFO, "loging is success");
			LandingPage landingPage=new LandingPage(driver,test);
			PageFactory.initElements(driver, landingPage);
			return landingPage;
			
		}
		else
		{
			test.log(LogStatus.INFO, "loging is NOT success");
			LoginPage loginPage=new LoginPage(driver,test);
			PageFactory.initElements(driver, loginPage);
			return loginPage;
		}
	}
	
	

}
