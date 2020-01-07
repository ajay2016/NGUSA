package com.nationalguard.ddf.testcases;

import java.io.IOException;
import java.util.Hashtable;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.nationalguard.ddf.base.BaseTest;
import com.nationalguard.ddf.utility.DataUtil;
import com.nationalguard.ddf.utility.Xls_Reader;

public class FitnessCal extends BaseTest {

	String testName = "FitnessCalculatorForm";
	Xls_Reader xls;

	@Test(dataProvider = "getData")
	public void fitnessCalculator(Hashtable<String, String> data) throws IOException {

		// gives data set
		test.log(Status.INFO, data.toString());

		if (!DataUtil.isRunnable(testName, xls) || data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Test skipped since rumode is N");
			throw new SkipException("Test skipped since rumode is N");

		}

		test.log(Status.INFO, "Fitness Calculator Test Started");

		// opening chrome browser
		openBrowser(data.get("Browser"));
		// navigate to given url
		navigate("appurl");
		// click on hamburger button
		click("hamburgebutton_xpath");
		// click on fitness calculator link
		click("fitnesscalculator_xpath");
		// wait for 2 seconds
		wait(2);
		// choose gender and click continue
		choose(data.get("Gender_xpath"));
		click("continuebutton_xpath");
		// type birthdate
		type("birthdate_id", "11-30-2000");
		click("continuebutton_xpath");
		// input fitness statistics
		type("pushups_id", "40");
		type("situps_id", "40");
		type("twomile_id", "10:05");
		click("continuebutton_xpath");
		// click confirm button
		click("confirmbutton_xpath");
		test.log(Status.INFO, "result is displayed");
		System.out.println("Fitness result is displayed");
	}

	@DataProvider
	public Object[][] getData() {
		xls = new Xls_Reader(prop.getProperty("xlsPath"));
		return DataUtil.getTestData(xls, testName);
	}

}
