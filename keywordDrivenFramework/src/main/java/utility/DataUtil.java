package utility;

import java.util.Hashtable;

public class DataUtil {
	
	 	public static Object[][] giveData(String testCaseName,XlsReader reader) {
		
		//finding the test case starting number
		int testCaseNameStartingRowNumber=1;
		while(!reader.getCellData(Constants.DATA_SHEET, 0, testCaseNameStartingRowNumber).equalsIgnoreCase(testCaseName))
		{
			testCaseNameStartingRowNumber++;
		}
		System.out.println(testCaseNameStartingRowNumber);
			
		//finding the no of columns of the test case
		int testCaseColumnsStartingRowNumber=testCaseNameStartingRowNumber+1;
		int totalTestCaseColumnsNumber=0;
		while(!reader.getCellData(Constants.DATA_SHEET,totalTestCaseColumnsNumber ,testCaseColumnsStartingRowNumber).equals(""))
		{
			totalTestCaseColumnsNumber++;
		}
		System.out.println(testCaseName+" having "+totalTestCaseColumnsNumber+" columns");
		
		//finding the no of rows of the test case
		
		int testCaseDataStartingRowNumber=testCaseNameStartingRowNumber+2;
		int totalTestCaseDataRows=0;
		while(!reader.getCellData(Constants.DATA_SHEET, 0,testCaseDataStartingRowNumber+totalTestCaseDataRows).equals(""))
		{
			totalTestCaseDataRows++;
		}
		
		System.out.println(testCaseName+" having "+totalTestCaseDataRows+" rows");
		
		//extracting data from the test case
		Hashtable<String,String> table;
		int i=0;
		Object[][] data=new Object[totalTestCaseDataRows][1];
		for(int rNum=testCaseDataStartingRowNumber;rNum<testCaseDataStartingRowNumber+totalTestCaseDataRows;rNum++)
		{
			table=new Hashtable<String,String>();
			for(int cNum=0;cNum<totalTestCaseColumnsNumber;cNum++)
			{
				String key=reader.getCellData(Constants.DATA_SHEET,cNum,testCaseColumnsStartingRowNumber);
				String value=reader.getCellData(Constants.DATA_SHEET, cNum, rNum);
				System.out.println(key+"------"+value);
				table.put(key, value);
				
			}
			System.out.println(table);
			data[i][0]=table;
			i++;
		}
		
		return data;
	}
	 	
	 	public static boolean isRunnable(String testCaseName,XlsReader reader)
	 	{
	 		int totalNumberOfRows =reader.getRowCount(Constants.TESTCASES_SHEET);
	 		for(int rNum=2;rNum<=totalNumberOfRows;rNum++)
	 		{
	 			String TCID=reader.getCellData(Constants.TESTCASES_SHEET,Constants.TCID_COL, rNum);
	 			if(testCaseName.equalsIgnoreCase(TCID))
	 			{
	 					String runmode=reader.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
	 					if(runmode.equals(Constants.RUNMODE_COL_NO))
	 						return true;
	 			}
	 			
	 			
	 		}
	 		
	 		return false;
	 	}

}
