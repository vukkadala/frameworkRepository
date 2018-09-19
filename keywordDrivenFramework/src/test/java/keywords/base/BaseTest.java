package keywords.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import reports.ExtentManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import driver.DriverScript;
import utility.DataUtil;
import utility.XlsReader;

public class BaseTest {
	
	public Properties normalProperties;
	public Properties envSpecificProperties;
	public XlsReader reader;
	public String testCaseName;
	public DriverScript driverScript;
	public ExtentReports extentReports;
	public ExtentTest test;
	
	@BeforeTest
	public void init()
	{
		System.out.println("iniliazing base test");
		//System.out.println("test case name is "+this.getClass().getSimpleName());
		//testCaseName=this.getClass().getSimpleName();
		String[] getSuiteName=this.getClass().getPackage().getName().split("\\.");
		String suiteName=getSuiteName[getSuiteName.length-1];
		
		normalProperties=new Properties();
		envSpecificProperties=new Properties();
		try {
			FileInputStream normalPro=new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//normalEnvProperties.properties");
			normalProperties.load(normalPro);
			String env=normalProperties.getProperty("env");
			FileInputStream envSpecificPro=new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+env+".properties");
			envSpecificProperties.load(envSpecificPro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//initialize excel file
		reader=new XlsReader(System.getProperty("user.dir")+"//dataSheets//"+suiteName+".xlsx");
		//initialize the driver script
		driverScript=new DriverScript();
		driverScript.setEnvSpecificProperties(envSpecificProperties);
		driverScript.setNormalProperties(normalProperties);
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		extentReports=ExtentManager.getInstance();
		test=extentReports.createTest(testCaseName);
		driverScript.setTest(test);
	}
	
	@AfterMethod
	public void afterMethod()
	{
		if(driverScript!=null)
		driverScript.quit();
		if(extentReports!=null)
			extentReports.flush();
	}
	

	@DataProvider
	public Object[][] giveData(Method method)
	{
		testCaseName=method.getName();
		System.out.println("starting "+testCaseName);
		return DataUtil.giveData(testCaseName, reader);
	}

	
}
