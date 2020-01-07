package com.nationalguard.ddf.reports;



import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    
    private static ExtentReports extent;
    public static String screenshotFolderPath;
    
    public static ExtentReports getInstance(String reportPath) {
    	if (extent == null){
    		//String fileName="Report.html";
    		Date d = new Date();
    		String fileName =d.toString().replace(":", "_").replace(" ", "_")+".html";
			/*
			 * Date d = new Date(); //folder name dynamic creation String
			 * folderName=d.toString().replace(":", "_").replace(" ", "_");
			 * 
			 * // directory of the report folder, mkdirs makes a folder under folder //makes
			 * screenshot folder under foldername new
			 * File(reportPath+folderName+"//screenshots").mkdirs(); //now actual report
			 * path reportPath=reportPath+folderName+"//";
			 * screenshotFolderPath=reportPath+"screenshots//";
			 * System.out.println(reportPath+fileName);
			 */
    		createInstance(reportPath+fileName);
    	}
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("Reports");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Reports - Automation Testing");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }
}