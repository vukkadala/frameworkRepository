package keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public class AppKeywords extends GenericKeywords{

	public void rediffLogin()
	{
		test.log(Status.INFO, "rediff login test starting");
		getElement("rediffMoneyXpath").click();
		getElement("rediffSignInLinkXpath").click();
		String userName="";
		String password="";
		if(table.get("userName")==null)
		{
			userName=envSpecificProperties.getProperty("defaultRediffUserName");
			password=envSpecificProperties.getProperty("defaultRediffPassword");
		}
		else
		{
			userName=table.get("userName");
			password=table.get("password");
		}
		getElement("rediffEmailAddressXpath").sendKeys(userName);
		getElement("rediffFirstContinueButtonXpath").click();
		
		WebDriverWait wait1=new WebDriverWait(driver,1000);
		wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='emailsubmit']")));
		
		getElement("rediffPasswordXpath").sendKeys(password);
		getElement("rediffRemembermeCheckBoxXpath").click();
		getElement("rediffSecondContinueButtonXpath").click();
		WebDriverWait wait2=new WebDriverWait(driver,15);
		wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@id='loginsubmit']")));
		boolean status=isElementPresent("murthyPortfolioXpath");
		if(status)
			System.out.println("murthy portfolia is present");
		else
			System.out.println("murthy portfolia is not present");
	
		
		getElement("rediffSignOutXpath").click();
		
		
	}
}
