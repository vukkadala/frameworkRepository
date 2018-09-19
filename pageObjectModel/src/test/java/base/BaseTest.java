package base;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.Constants;
import utility.ExtentManager;
import utility.XlsReader;

public class BaseTest {
	
	public static WebDriver driver;
	public ExtentReports extent=ExtentManager.getInstance();
	public ExtentTest test;
	public XlsReader reader= new XlsReader(Constants.FACEBOOK_DATA_SHEET);
	
	public void init(String browserType)
	{
		test.log(LogStatus.INFO, "opening "+browserType);
		if(browserType.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",Constants.CHROME_DRIVER_EXE);
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver=new ChromeDriver(options);
		}
		else
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//drivers//geckodriver.exe");
			driver=new FirefoxDriver();
		}
		
		test.log(LogStatus.INFO, "opened "+browserType+" successfully");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void quit()
	{
		if(extent!=null)
		{
			extent.endTest(test);
			extent.flush();
		}
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
	
	public void reportFailure(String failureMessage)
	{
		
		test.log(LogStatus.FAIL, failureMessage);
		Assert.fail(failureMessage);
	}

}
