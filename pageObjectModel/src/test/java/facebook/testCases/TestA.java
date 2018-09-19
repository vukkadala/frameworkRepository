package facebook.testCases;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utility.XlsReader;

public class TestA {
	
	@Test(dataProvider="giveData")
	public void initial(Hashtable<String,String> table)
	{
		System.out.println(table.get("runmode")+"-----"+table.get("col1")+"------"+table.get("col2"));
		
	}
	
	
	
	
	
	
	
	
	@DataProvider
	Object[][] giveData()
	{
		XlsReader reader = new XlsReader(System.getProperty("user.dir")+"//dataSheets//pageObjectData.xlsx");
		String testCaseName="TestC";
		int testCaseNameStartingRowNumber=1;
		while(!reader.getCellData("data", 0, testCaseNameStartingRowNumber).equalsIgnoreCase(testCaseName))
		{
			testCaseNameStartingRowNumber++;
		}
		System.out.println(testCaseName+" starting at "+testCaseNameStartingRowNumber);
		//finding the total no. of rows
		int testCaseColumnsStartingRowNumber=testCaseNameStartingRowNumber+1;
		int testCaseDataStartingRowNumber=testCaseNameStartingRowNumber+2;
		int testCaseRows=0;
		while(!reader.getCellData("data", 0,testCaseDataStartingRowNumber+testCaseRows).equals(""))
			{
				testCaseRows++;
			}
		
		System.out.println(testCaseName+" has "+testCaseRows+" rows");
		
		//finding the columns
		int testCaseColumns=0;
		
		while(!reader.getCellData("data",testCaseColumns ,testCaseColumnsStartingRowNumber).equals(""))
			{
				testCaseColumns++;
			}
		
			System.out.println(testCaseName+" has "+testCaseColumns+" columns");
			
			//reading data
			Object[][] data=new Object[testCaseRows][1];
			int dataRow=0;
			Hashtable<String,String> table;
			for(int rNum=testCaseDataStartingRowNumber;rNum<testCaseDataStartingRowNumber+testCaseRows;rNum++)
			{
				table=new Hashtable<String,String>();
				for(int cNum=0;cNum<testCaseColumns;cNum++)
				{
					String key=reader.getCellData("data", cNum, testCaseColumnsStartingRowNumber);
					String value=reader.getCellData("data", cNum, rNum);
					table.put(key, value);
				}
				
				data[dataRow][0]=table;
				dataRow++;
			}
			
			return data;
	}

}
