package keywords.suiteRediff;

import java.util.Hashtable;

import keywords.base.BaseTest;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;







import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reports.ExtentManager;
import driver.DriverScript;
import utility.Constants;
import utility.DataUtil;
import utility.XlsReader;

public class RediffLoginTest extends BaseTest{
	
	@Test(dataProvider="giveData")
	public void LoginTest(Hashtable<String,String> table) throws Exception
	{
		test.log(Status.INFO, "checking runmode of "+testCaseName);
		if(DataUtil.isRunnable(testCaseName,reader)||table.get(Constants.RUNMODE_COL).equals(Constants.RUNMODE_COL_NO))
		{
			test.log(Status.SKIP, " runmode is n of "+testCaseName);
			throw new SkipException(testCaseName+" runmode is n");
		}
		System.out.println("running the "+testCaseName);
		driverScript.exexuteKeywords(testCaseName, reader, table);
		
	}
	
	

}
