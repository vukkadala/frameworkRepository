package facebook.testCases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.Constants;
import utility.DataUtil;
import utility.ExtentManager;
import utility.XlsReader;
import facebook.pages.LandingPage;
import facebook.pages.LaunchPage;
import facebook.pages.LoginPage;
import base.BaseTest;

public class LoginTest extends BaseTest{
	
	String testCaseName="LoginTest";
	
	@Test(dataProvider="giveData")
	public void doLogin(Hashtable<String,String> table)
	{
		
		test=extent.startTest("login test");
		if(table.get("runmode").equalsIgnoreCase("n"))
		{
			test.log(LogStatus.SKIP,"runmode of "+testCaseName+" is no");
			throw new SkipException("runmode of "+testCaseName+" is no");
		}
		test.log(LogStatus.INFO, "starting login test");
		test.log(LogStatus.INFO, "opeing browser");
		init(table.get("browser"));
		LaunchPage launchPage=new LaunchPage(driver,test);
		PageFactory.initElements(driver,launchPage );
		LoginPage loginPage=launchPage.goToFBLoginPage();
		test.log(LogStatus.INFO, "logging in");
		Object page=loginPage.doLogin(table.get("userName"),table.get("password"));
		String actualResult="";
		if(page instanceof LandingPage)
			actualResult="success";
		else
			actualResult="notsuccess";
		if(actualResult.equalsIgnoreCase(table.get("expectedResult")))
			test.log(LogStatus.PASS, "logged in");
		else
		{
			takesScreenshot();
			reportFailure("test failed");
		}
		
	}
	
	@DataProvider
	public Object[][] giveData()
	{
		return DataUtil.getData(reader, testCaseName);
	}

}
