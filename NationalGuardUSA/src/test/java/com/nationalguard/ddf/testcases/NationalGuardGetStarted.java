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

public class NationalGuardGetStarted extends BaseTest {

	String testName = "GetStartedForm";
	Xls_Reader xls;

	@Test(dataProvider = "getData")
	public void getStartedForm(Hashtable<String, String> data) throws IOException {

		// gives data set
		test.log(Status.INFO, data.toString());

		if (!DataUtil.isRunnable(testName, xls) || data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Test skipped since rumode is N");
			throw new SkipException("Test skipped since rumode is N");

		}

		test.log(Status.INFO, "Get Started Form");
		// opening chrome browser
		openBrowser(data.get("Browser"));
		// navigate to given url
		navigate("appurl");
		// click on the get startd button
		click("getstartedbutton_name");
		// type data to the fields from excel database
		type("firstname_name", data.get("FirstName"));
		type("lastname_name", data.get("LastName"));
		type("email_name", data.get("Email"));
		type("phone_name", data.get("PhoneNumber"));
		type("zip_name", data.get("Zipcode"));
		// click
		click("submit_name");
		// data input and click
		type("birthdate_name", data.get("Birthdate"));
		click("submit_name");
		// phone
		type("phone_name", data.get("PhoneNumber"));
		click("submit_name");
		// gender selection
		click("gender_xpath");
		click("submit_name");

		// Military Experience
		click("militaryexperience_xpath");
		click("submit_name");
		// diploma
		click("diploma_xpath");
		click("submit_name");
		// college
		click("college_xpath");
		click("submit_name");
		// college degree
		click("collegedegree_xpath");
		click("submit_name");
		// select height
		select("heightfeet_xpath", "5");
		select("heightinches_xpath", "10");
		click("submit_name");
		// weight
		click("weight_xpath");
		click("submit_name");
		// motivation
		click("motivation_xpath");
		click("submit_name");
		// final submit
		click("submit_name");
		// check recruiter is present and print it with zipcode
		String recruiterName = verifyTextPresent("recruitername_xpath");
		System.out.println("The Recruiter appointed on Zipcode "+data.get("Zipcode")+" is " +recruiterName);

		
	}

	@DataProvider
	public Object[][] getData() {
		xls = new Xls_Reader(prop.getProperty("xlsPath"));
		return DataUtil.getTestData(xls, testName);
	}

}
