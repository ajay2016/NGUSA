package com.nationalguard.ddf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.nationalguard.ddf.reports.ExtentManager;

public class BaseTest {

	public WebDriver driver;
	public Properties prop;
	public ExtentReports rep;
	public ExtentTest test;
	public String testName;
	public SoftAssert softAssert;

	@BeforeTest
	public void initialize() throws IOException {
		testName = this.getClass().getSimpleName();
		if (prop == null) {
			prop = new Properties();
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "//src//test//resources//projectconfig.properties");
			prop.load(file);
		}

	}

	@BeforeMethod
	public void initTest() {
		rep = ExtentManager.getInstance(prop.getProperty("reportPath"));
		test = rep.createTest(testName);
		softAssert = new SoftAssert();

	}

	@AfterMethod
	public void quit() throws IOException {

		try {
			softAssert.assertAll();
		} catch (Error e) {
			test.log(Status.FAIL, e.getMessage());
			takeScreenShot();
		}

		// softAssert.assertAll();

		if (driver != null) {
			driver.quit();
		}

		if (rep != null)
			rep.flush();
	}

	public void openBrowser(String browserType) throws IOException {

		test.log(Status.INFO, "Opening browser  " + browserType);

		/*
		 * if (prop == null) { prop = new Properties(); FileInputStream file = new
		 * FileInputStream( System.getProperty("user.dir") +
		 * "//src//test//resources//projectconfig.properties"); prop.load(file); }
		 */
		// System.out.println(prop.get("appurl"));
		if (browserType.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "null");
			driver = new ChromeDriver();

		} else if (browserType.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
			driver = new FirefoxDriver();

		} else if (browserType.equals("ie")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		} else if (browserType.equals("Edge")) {
			System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, "null");
			System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY, "null");
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// System.out.println("Open Browser");

	}

	public void navigate(String urlKey) {

		test.log(Status.INFO, "Navigate to  " + urlKey);

		driver.get(prop.getProperty(urlKey));

		// System.out.println("Navigate");

	}

	public void click(String xpathElementKey) throws IOException {
		// driver.findElement(By.xpath(prop.getProperty(xpathElementKey))).click();
		test.log(Status.INFO, "Clicking on element");
		getElement(xpathElementKey).click();
		// System.out.println("Click");

	}

	public void type(String xpathElementKey, String data) throws IOException {
		test.log(Status.INFO, "Typing data  " + data);
		getElement(xpathElementKey).sendKeys(data);
		// driver.findElement(By.xpath(prop.getProperty(xpathElementKey))).sendKeys(data);
		// System.out.println("Type");

	}

	public void select(String locatorKey, String data) throws IOException {
		test.log(Status.INFO, "Selecting number  " + data);
		Select s = new Select(getElement(locatorKey));
		s.selectByVisibleText(data);

	}

	public void closeBrowser() {
		test.log(Status.INFO, "Closing Browser");

		// System.out.println("Close Browser");

	}

	public void choose(String xpath) {

		test.log(Status.INFO, "Choosing Gender");
		driver.findElement(By.xpath(xpath)).click();

	}

	/**************************
	 * Funtions to find elements by id xpath name
	 * 
	 * @throws IOException
	 ********************/
	public WebElement getElement(String locatorKey) throws IOException {
		WebElement e = null;
		try {
			if (locatorKey.endsWith("_id")) {
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_name")) {
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			} else {
				reportFail("Locator is not correct " + locatorKey);
				Assert.fail("Locator is not correct " + locatorKey);
			}

		} catch (Exception e1) {
			reportFail(e1.getMessage());
			e1.printStackTrace();
			reportFail("TestFailed");
			Assert.fail("Element is not found " + e1.getMessage());
		}
		return e;
	}

	/*****************************
	 * Validation Functions
	 * 
	 * @throws IOException
	 ***************************/

	public boolean verifyTitle() {
		return false;

	}

	public boolean verifyText(String xpathTextKey, String expectedTextKey) throws IOException {
		String actualText = getElement(xpathTextKey).getText().trim();
		String expectedText = prop.getProperty(expectedTextKey);
		if (actualText.equalsIgnoreCase(expectedText)) {
			System.out.println("Text is verified as  " + expectedText);
			return true;

		} else

			System.out.println("Text not found");
		softAssert.assertEquals(false, true);
		// softAssert.assertAll();
		/*
		 * reportFail("Text doesnot match"); Assert.fail("Text doesnot match");
		 */

		return false;

	}

	public String verifyTextPresent(String locatorKey) throws IOException {
		String text = getElement(locatorKey).getText().trim();
		if (!text.equals("")) {
			System.out.println("Text is present " + text);
			test.log(Status.PASS, "Text is present " + text);
			return text;

		} else {
			System.out.println("Recruiter is not Present");
			test.log(Status.FAIL, "Recruiter is not Present");
			return null;
		}
	}

	public boolean isElementPresent(String locatorKey) throws IOException {
		List<WebElement> elementList = null;
		if (locatorKey.endsWith("_id")) {
			elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
		} else if (locatorKey.endsWith("_name")) {
			elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
		} else if (locatorKey.endsWith("_xpath")) {
			elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		} else {
			reportFail("Locator is not correct" + locatorKey);
			Assert.fail("Locator is not correct" + locatorKey);
		}

		if (elementList.size() == 0)
			return false;
		else
			return true;

	}

	/**********************************
	 * Reporting Functions
	 *******************************/

	public void reportPass(String msg) {
		test.log(Status.PASS, "Test passed");

	}

	public void reportFail(String msg) throws IOException {
		test.log(Status.FAIL, msg);
		takeScreenShot();
		// Assert.fail(msg);

	}

	public void takeScreenShot() throws IOException {
		// fileName of the screenshot
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		// store screenshot in that file
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(prop.getProperty("screenShotPath") + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// put screenshot file in reports
		// test.log(Status.INFO, "Screenshot-> "
		// + test.addScreenCaptureFromPath(System.getProperty("user.dir") +
		// "//reports//screenshots//" + screenshotFile));
		test.fail("Test Failed   " + testName, MediaEntityBuilder.createScreenCaptureFromPath(
				System.getProperty("user.dir") + "//reports//screenshots//" + screenshotFile).build());
	}

	/***************************
	 * Explicit Wait
	 * 
	 * @throws InterruptedException
	 ***********************************/

	public void wait(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
