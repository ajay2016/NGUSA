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

public class WeightCalculatorTest extends BaseTest {

	String testName = "WeightCalculatorForm";
	Xls_Reader xls;

	@Test(dataProvider = "getData")
	public void weightCalculatorMale(Hashtable<String, String> data) throws IOException {

		// gives data set
		test.log(Status.INFO, data.toString());

		if (!DataUtil.isRunnable(testName, xls) || data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Test skipped since rumode is N");
			throw new SkipException("Test skipped since rumode is N");

		}
		test.log(Status.INFO, "Weight CaLculator Test Male");

		// opening chrome browser
		openBrowser(data.get("Browser"));
		// navigate to given url
		navigate("appurl");
		// click on hamburger button
		click("hamburgebutton_xpath");
		// click on weight calculator link
		click("weightcalculator_xpath");
		// wait for 2 seconds
		wait(2);
		// choose gender and click continue
		choose(data.get("Gender_xpath"));
		click("continuebutton_xpath");
		// Age
		click("age_xpath");
		click("continuebutton_xpath");
		// select height
		select("heightfeet_xpath", "5");
		select("heightinches_xpath", "10");
		click("continuebutton_xpath");
		// Military Experience
		click("militaryexperienceweightcal_xpath");
		click("continuebutton_xpath");
		// click confirm button
		click("confirmbutton_xpath");
		test.log(Status.INFO, "Result is displayed");
		System.out.println("Weight result is displayed");

		/*
		 * driver.findElement(By.xpath("//button[@class='hamburger tab-focus']")).click(
		 * ); driver.findElement(By.xpath("//a[@href='/weight/calculator']")).click();
		 * 
		 * // WebDriver wait WebDriverWait wait = new WebDriverWait(driver, 30);
		 * wait.until(ExpectedConditions .visibilityOfElementLocated(By.
		 * xpath("//label[@for='gender_0' and @class='btn-outline']")));
		 * 
		 * driver.findElement(By.
		 * xpath("//label[@for='gender_0' and @class='btn-outline']")).click();
		 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click();
		 * 
		 * // age
		 * driver.findElement(By.xpath("//label[@for='age_0' and @class='btn-outline']")
		 * ).click();
		 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click();
		 * 
		 * // Height Selection Select s = new
		 * Select(driver.findElement(By.xpath("//*[@id='feet']")));
		 * s.selectByVisibleText("5");
		 * 
		 * Select s1 = new Select(driver.findElement(By.xpath("//*[@id='inches']")));
		 * s1.selectByVisibleText("10");
		 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click(); //
		 * Military experience
		 * driver.findElement(By.xpath("//label[@for='military_exp_1']")).click();
		 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click();
		 * 
		 * // click confirm
		 * driver.findElement(By.xpath("//button[contains(., 'Confirm') ]")).click();
		 * 
		 * System.out.println("Weight calculator result is displayed");
		 * test.log(Status.PASS, "Weight calculator result is displayed");
		 */
	}

	/*
	 * @Test(priority = 2) public void weightCalculatorFemale() throws IOException {
	 * 
	 * test.log(Status.INFO, "Weight CaLculator Test Female");
	 * driver.findElement(By.xpath("//button[@class='hamburger tab-focus']")).click(
	 * ); driver.findElement(By.xpath("//a[@href='/weight/calculator']")).click();
	 * 
	 * // WebDriver wait WebDriverWait wait = new WebDriverWait(driver, 30);
	 * wait.until(ExpectedConditions .visibilityOfElementLocated(By.
	 * xpath("//label[@for='gender_1' and @class='btn-outline']")));
	 * 
	 * driver.findElement(By.
	 * xpath("//label[@for='gender_1' and @class='btn-outline']")).click();
	 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click();
	 * 
	 * // age
	 * driver.findElement(By.xpath("//label[@for='age_0' and @class='btn-outline']")
	 * ).click();
	 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click();
	 * 
	 * // Height Selection Select s = new
	 * Select(driver.findElement(By.xpath("//*[@id='feet']")));
	 * s.selectByVisibleText("5");
	 * 
	 * Select s1 = new Select(driver.findElement(By.xpath("//*[@id='inches']")));
	 * s1.selectByVisibleText("10");
	 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click(); //
	 * Military experience
	 * driver.findElement(By.xpath("//label[@for='military_exp_1']")).click();
	 * driver.findElement(By.xpath("//button[text()=\"Continue\"]")).click();
	 * 
	 * // click confirm
	 * driver.findElement(By.xpath("//button[contains(., 'Confirm') ]")).click();
	 * 
	 * 
	 * 
	 * System.out.println("Weight calculator result is displayed");
	 * test.log(Status.PASS, "Weight calculator result is displayed");
	 * test.pass("Test Passed",
	 * MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(
	 * driver)).build());
	 * 
	 * }
	 */
	@DataProvider
	public Object[][] getData() {
		xls = new Xls_Reader(prop.getProperty("xlsPath"));
		return DataUtil.getTestData(xls, testName);
	}
}
