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

public class GuardPayTest extends BaseTest {

	String testName = "GuardPayCalculatorForm";
	Xls_Reader xls;

	@Test(dataProvider = "getData")
	public void guardpayEnlisted(Hashtable<String, String> data) throws IOException {

		// gives data set
		test.log(Status.INFO, data.toString());

		if (!DataUtil.isRunnable(testName, xls) || data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Test skipped since rumode is N");
			throw new SkipException("Test skipped since rumode is N");

		}

		test.log(Status.INFO, "Guard Pay Enlisted");

		// opening chrome browser
		openBrowser(data.get("Browser"));
		// navigate to given url
		navigate("appurl");
		// click on hamburger button
		click("hamburgebutton_xpath");
		//click on pay calculator
		click("paycalculator_xpath");
		//choose enlistmenttype
		choose(data.get("EnlistmentType_xpath"));
		click("continuebutton_xpath");
		wait(5);
		//select rank and years
		select("rank_xpath", data.get("Rank"));
		select("years_xpath", data.get("Year"));
		click("continuebutton_xpath");
		//verify text
		String text = verifyTextPresent("annualtotalmoney_xpath");
		System.out.println("The annual total for "+data.get("Rank")+" with "+data.get("Year")+" year of Experience is "+text+" is displayed on the page");
		test.log(Status.PASS, "The annual total for "+data.get("Rank")+"with "+data.get("Year")+" year of Experience is  "+text+" is displayed on the page");

		/*
		 * driver.findElement(By.xpath("//button[@class='hamburger tab-focus']")).click(
		 * ); driver.findElement(By.xpath("//a[@href='/pay/calculator']")).click();
		 * 
		 * // WebDriver wait WebDriverWait wait = new WebDriverWait(driver, 20);
		 * wait.until(ExpectedConditions .visibilityOfElementLocated(By.
		 * xpath("//label[@for='current_status_1' and @class='btn-outline']")));
		 * 
		 * // select enlisted driver.findElement(By.
		 * xpath("//label[@for='current_status_1' and @class='btn-outline']")).click();
		 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click(); //
		 * select rank Select s = new
		 * Select(driver.findElement(By.xpath("//select[@name='rank']")));
		 * s.selectByValue("E-1");
		 * 
		 * // select years Select s1 = new
		 * Select(driver.findElement(By.xpath("//select[@name='years']")));
		 * s1.selectByValue("1");
		 * 
		 * // continue
		 * driver.findElement(By.xpath("//
		 * button[text()=\"Continue\"]")).click(); // pay
		 * calculator page
		 * driver.findElement(By.xpath("//*[text()=\"Annual Total:\"]")).getText();
		 * System.out.println("Annual total is displayed on the page");
		 * test.log(Status.PASS, "Annual total is displayed on the page");
		 * test.pass("Test Passed",
		 * MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(
		 * driver)).build()); }
		 * 
		 * // Enlistment type : Officer
		 * 
		 * @Test(priority = 2) public void guardpayOfficer() throws IOException {
		 * 
		 * test = extent.createTest("Guard Pay Officer"); test.log(Status.INFO,
		 * "Guard Pay Officer");
		 * 
		 * driver.findElement(By.xpath("//button[@class='hamburger tab-focus']")).click(
		 * ); driver.findElement(By.xpath("//a[@href='/pay/calculator']")).click();
		 * 
		 * // WebDriver wait WebDriverWait wait = new WebDriverWait(driver, 20);
		 * wait.until(ExpectedConditions .visibilityOfElementLocated(By.
		 * xpath("//label[@for='current_status_2' and @class='btn-outline']"))); //
		 * select enlisted driver.findElement(By.
		 * xpath("//label[@for='current_status_2' and @class='btn-outline']")).click();
		 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click(); //
		 * select rank Select s = new
		 * Select(driver.findElement(By.xpath("//select[@name='rank']")));
		 * s.selectByValue("O-7");
		 * 
		 * // select years Select s1 = new
		 * Select(driver.findElement(By.xpath("//select[@name='years']")));
		 * s1.selectByValue("1");
		 * 
		 * // continue
		 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click(); // pay
		 * calculator page
		 * driver.findElement(By.xpath("//*[text()=\"Annual Total:\"]")).getText();
		 * System.out.println("Annual total is displayed on the page");
		 * test.log(Status.PASS, "Annual total is displayed on the page");
		 * test.pass("Test Passed",
		 * MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(
		 * driver)).build());
		 */
	}

	@DataProvider
	public Object[][] getData() {
		xls = new Xls_Reader(prop.getProperty("xlsPath"));
		return DataUtil.getTestData(xls, testName);
	}
}
