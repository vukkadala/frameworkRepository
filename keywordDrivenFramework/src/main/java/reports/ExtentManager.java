package reports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
private static ExtentReports extent;
public static String screenshotsFolderPath;
    
    public static ExtentReports getInstance() {
    	if (extent == null)
    	{
    		String fileName="report.html";
    		Date date=new Date();
    		String folderName=date.toString().replace(":","_").replace(" ", "_");
    		//full folder path
    		String fullFolderPath=System.getProperty("user.dir")+"//testReports//"+folderName+"//";
    		    		
    		//directory to report folder
    		new File(fullFolderPath+"//screenshots").mkdirs();
    		screenshotsFolderPath=fullFolderPath+"//screenshots//";
    		createInstance(fullFolderPath+fileName);
    	}
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("automation report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("test results");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }

}
