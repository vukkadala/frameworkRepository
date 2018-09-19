package driver;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import keywords.AppKeywords;

import org.testng.SkipException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utility.Constants;
import utility.XlsReader;

public class DriverScript {
	
	public Properties normalProperties;
	public Properties envSpecificProperties;
	public AppKeywords appKeywords;
	public ExtentTest test;

	public void exexuteKeywords(String testCaseName,XlsReader reader,Hashtable<String,String> table) throws Exception
	{
		if(table.get("runmode").equalsIgnoreCase("n"))
			throw new SkipException(testCaseName+" data tab runmode column having n option");
		
		int numberOfRows=reader.getRowCount(Constants.KEYWORDS_SHEET);
		System.out.println("total no of rows in keywords tab are "+numberOfRows);
		appKeywords=new AppKeywords();
		//sending app specific and normal properties 
		appKeywords.setEnvSpecificProperties(envSpecificProperties);
		appKeywords.setNormalProperties(normalProperties);
		appKeywords.setTable(table);
		appKeywords.setTest(test);
		for(int rNum=2;rNum<=numberOfRows;rNum++)
		{
			String TCID=reader.getCellData("keywords",Constants.TCID_COL, rNum);
			if(TCID.equalsIgnoreCase(testCaseName))
			{
				String keyword=reader.getCellData(Constants.KEYWORDS_SHEET,Constants.KEYWORD_COL, rNum);
				String objectKey=reader.getCellData(Constants.KEYWORDS_SHEET,Constants.OBJECT_COL, rNum);
				String dataKey=reader.getCellData(Constants.KEYWORDS_SHEET,Constants.DATA_COL, rNum);
				String proceedOnFail=reader.getCellData(Constants.KEYWORDS_SHEET,Constants.PROCEED_COL,rNum);
				String data=table.get(dataKey);
				test.log(Status.INFO,TCID+"-----"+keyword+"------"+objectKey+"------"+normalProperties.getProperty(objectKey)+"----------"+proceedOnFail+"----------"+dataKey+"------"+data);
				appKeywords.setObjectKey(objectKey);
				appKeywords.setDataKey(dataKey);
				appKeywords.setProceedOnFail(proceedOnFail);
				//using java reflection API
				
				Method method;
				
					method=appKeywords.getClass().getMethod(keyword);
					method.invoke(appKeywords);
					
				
				
				
			}
			
		}
		
		appKeywords.assertAll();	
	}
				
			
	public void quit()
	{
		if(appKeywords!=null)
			appKeywords.quit();
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
	
	

}
