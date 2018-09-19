package utility;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	private static ExtentReports extent;
	
	public static ExtentReports getInstance()
	{
		if(extent==null)
		{
			Date date=new Date();
			String fileName=date.toString().replace(":", "_").replace(" ", "_")+".html";
			String reportPath=System.getProperty("user.dir")+"//reports//"+fileName;
			extent=new ExtentReports(reportPath,true,DisplayOrder.NEWEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
			extent.addSystemInfo("author", "murthy");
			
		}
		return extent;
	}

}
