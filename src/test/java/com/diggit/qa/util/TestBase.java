package com.diggit.qa.util;

import com.diggit.qa.common.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class TestBase extends TestListenerAdapter {

	static String BASE_URL = "";
	protected WebDriver DRIVER;
	static String ROOT = "";
	protected String UNIQUE_ID = "";




	public TestBase() {

		try {

			ROOT = new File(".").getCanonicalPath() + "\\";
			//ROOT = "C:\\Build\\CallRapido\\trunk\\";
			UNIQUE_ID = UUID.randomUUID().toString().split("-")[0];

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@BeforeClass
	public void initSelenium() {
		BASE_URL = "http://management.diggit.com/login";
		DRIVER = DriverFactory.getWebDriver("Chrome");
		DRIVER.navigate().to(BASE_URL);

	}




	

	@Override
	public void onTestSuccess(ITestResult itr) {


	}

	@Override
	public void onTestFailure(ITestResult itr) {



	}

	public void initDomainObjects(WebDriver driver) {


	}

	public void initPageObjects(WebDriver driver){


		
	}
	
	public int daysBetween(Date startDate, Date endDate){

		long diff = endDate.getTime() - startDate.getTime();
		int diffInDays =  (int) (diff / (1000 * 60 * 60 * 24));	
		return diffInDays;
	}

	public String captureScreen(String testName) {

		String path = ROOT;
		try {
			File source = ((TakesScreenshot) DRIVER).getScreenshotAs(OutputType.FILE);
			path = "./target/screenshots/" + testName;
			FileUtils.copyFile(source, new File(path)); 
		}
		catch(Exception e) {
			path = "Failed to capture screenshot: " + e.getMessage();
		}
		return path;

	}

}
