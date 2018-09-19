package keywords;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import reports.ExtentManager;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class GenericKeywords {
	
	public Properties normalProperties;
	public Properties envSpecificProperties;
	public Hashtable<String,String> table;
	public String objectKey;
	public String dataKey;
	public String proceedOnFail;
	public WebDriver driver;
	public ExtentTest test;
	public SoftAssert softAssert = new SoftAssert();
	
	
	
	/*******************************getter and setter methods***********************/
	
	
	public String getProceedOnFail() {
		return proceedOnFail;
	}
	public void setProceedOnFail(String proceedOnFail) {
		this.proceedOnFail = proceedOnFail;
	}
	public ExtentTest getTest() {
		return test;
	}
	
	public void setTest(ExtentTest test) {
		this.test = test;
	}
	
	public Properties getNormalProperties() {
		return normalProperties;
	}
	
	public void setNormalProperties(Properties normalProperties) {
		this.normalProperties = normalProperties;
	}
	public Properties getEnvSpecificProperties() {
		return envSpecificProperties;
	}
	public void setEnvSpecificProperties(Properties envSpecificProperties) {
		this.envSpecificProperties = envSpecificProperties;
	}
	public Hashtable<String, String> getTable() {
		return table;
	}
	public void setTable(Hashtable<String, String> table) {
		this.table = table;
	}
	public String getObjectKey() {
		return objectKey;
	}
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}
	public String getDataKey() {
		return dataKey;
	}
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
		
	/*******************************getter and setter methods ending***********************/
	
	
	
	public void openBrowser()
	{
		String browser=table.get(dataKey);
		if(browser.equalsIgnoreCase("mozilla"))
		{
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//browserDrivers//geckodriver.exe");
			driver=new FirefoxDriver();
			
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//browserDrivers//chromedriver.exe");
			driver=new ChromeDriver();
			
		}
		else
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"//browserDrivers//IEDriverServer.exe");
			driver=new InternetExplorerDriver();
			
		}
		
		test.log(Status.INFO,"opening browser "+table.get(dataKey));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public void click()
	{
		test.log(Status.INFO,"clicking "+normalProperties.getProperty(objectKey));
		getElement(objectKey).submit();
		//((JavascriptExecutor)driver).executeScript("arguments[0].click",getElement(objectKey));
		
		/*Actions builder=new Actions(driver);
		builder.moveToElement(getElement(objectKey)).click(getElement(objectKey));
		builder.perform();*/
		
		/*JavascriptExecutor js=((JavascriptExecutor)driver);
		js.executeScript("document.(getElement(objectKey).click()");
		*/
		
		/*WebElement we=getElement(objectKey);
		JavascriptLibrary jsLib = new JavascriptLibrary();
		jsLib.callEmbeddedSelenium(driver, "triggerMouseEventAt", we, "click", "0,0");*/
		
		//getElement(objectKey).sendKeys(Keys.RETURN);
		
		WebElement input =getElement(objectKey);
		new Actions(driver).moveToElement(input).click().perform();
		
		
	}
	
	public void type()
	{
		test.log(Status.INFO,"typing "+normalProperties.getProperty(objectKey)+" data is "+table.get(dataKey));
		getElement(objectKey).sendKeys(table.get(dataKey));
	}
	
	public void navigate()
	{
		test.log(Status.INFO,"navigating "+envSpecificProperties.getProperty(objectKey));
		driver.get(envSpecificProperties.getProperty(objectKey));
	}
	
	public void validateTitle()
	{
		String expectedTitle=normalProperties.getProperty(objectKey);
		System.out.println(expectedTitle);
		System.out.println(driver.getTitle());
		if(!expectedTitle.equalsIgnoreCase(driver.getTitle()))
			reportFailure(expectedTitle+" and "+driver.getTitle()+" mismatched");
		
		test.log(Status.INFO,"validating title "+expectedTitle.equalsIgnoreCase(driver.getTitle()));
	}
	
	public void validateElementPresent(String objectKey)
	{
		if(!isElementPresent(objectKey));
			//report failure
			reportFailure(objectKey+" not found");
	}
	
	/*****************************************utility methods*******************************************/
	public boolean isElementPresent(String objectKey)
	{
		List<WebElement> list;
		
			list = null;
			if(objectKey.endsWith("Xpath"))
				list=driver.findElements(By.xpath(normalProperties.getProperty(objectKey)));
			else if((objectKey.endsWith("Id")))
				list=driver.findElements(By.id(normalProperties.getProperty(objectKey)));
			else if(objectKey.endsWith("LinkText"))
				list=driver.findElements(By.linkText(normalProperties.getProperty(objectKey)));
			else if(objectKey.endsWith("PartialLinkText"))
				list=driver.findElements(By.partialLinkText(normalProperties.getProperty(objectKey)));
			else if(objectKey.endsWith("TagName"))
				list=driver.findElements(By.tagName(normalProperties.getProperty(objectKey)));
			else if(objectKey.endsWith("Name"))
				list=driver.findElements(By.name(normalProperties.getProperty(objectKey)));
			
		if(list.size()>0)
			return true;
		else
			return false;
	}
	
	public void quit()
	{
		if(driver!=null)
			driver.quit();
	}
	
	//centralised getElement
	
	public WebElement getElement(String objectKey)
	{
		WebElement e=null;
		try {
				if(objectKey.endsWith("Xpath"))
					e=driver.findElement(By.xpath(normalProperties.getProperty(objectKey)));
				else if((objectKey.endsWith("Id")))
					e=driver.findElement(By.id(normalProperties.getProperty(objectKey)));
				else if(objectKey.endsWith("LinkText"))
					e=driver.findElement(By.linkText(normalProperties.getProperty(objectKey)));
				else if(objectKey.endsWith("PartialLinkText"))
					e=driver.findElement(By.partialLinkText(normalProperties.getProperty(objectKey)));
				else if(objectKey.endsWith("TagName"))
					e=driver.findElement(By.tagName(normalProperties.getProperty(objectKey)));
				else if(objectKey.endsWith("Name"))
					e=driver.findElement(By.name(normalProperties.getProperty(objectKey)));
					
					//find the visibility of element
					WebDriverWait wait=new WebDriverWait(driver,20);
					wait.until(ExpectedConditions.visibilityOf(e));
					//check state of object
					wait.until(ExpectedConditions.elementToBeClickable(e));
		} catch (Exception e1) {
			e1.printStackTrace();
			reportFailure("element is not ending properly with suffix or element not found");
		}
		return e;
	}
	
	/**********************************************************reporting methods***************************************************/
	
	
		public void reportFailure(String failureMessage)
		{
			test.log(Status.FAIL,failureMessage);
			//take screenshot
			takeScreenshot();
			//Assert.fail(failureMessage);
			if(proceedOnFail.equalsIgnoreCase("y"))
				softAssert.fail(failureMessage);
			else
			{
				softAssert.fail(failureMessage);
				softAssert.assertAll();
			}
		}
		
		public void assertAll()
		{
			softAssert.assertAll();
		}
		
		public void takeScreenshot()
		{
			TakesScreenshot screen=(TakesScreenshot)(driver);
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File screenshotFile=screen.getScreenshotAs(OutputType.FILE);
			FileHandler handler=new FileHandler();
			Date fileDate=new Date();
			String replaceFile=fileDate.toString().replace(":","_").replace(" ", "_");
			try {
				handler.copy(screenshotFile, new File(ExtentManager.screenshotsFolderPath+replaceFile+".jpeg"));
				test.log(Status.INFO, "here is the screenshot "+test.addScreenCaptureFromPath(ExtentManager.screenshotsFolderPath+replaceFile+".jpeg"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}


















