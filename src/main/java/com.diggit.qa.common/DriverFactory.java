package com.diggit.qa.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

	private static ChromeDriverService service;
	public static String  downloadPath;

	private enum LoadType {
		HtmlUnit,
		Firefox,
		InternetExplorer,
		Chrome
	}

	public static WebDriver getWebDriver(String browser) {

		LoadType type = LoadType.valueOf(browser);
		WebDriver driver ;

		switch (type) {
		case HtmlUnit:
			return new HtmlUnitDriver();
		case Firefox:
			
			driver = new FirefoxDriver();
			break;
		case InternetExplorer:
			driver = new InternetExplorerDriver();
			break;
		case Chrome:
			service = new ChromeDriverService.Builder()
			.usingDriverExecutable(new File("src/main/resources/chromedriver.exe"))
			.usingAnyFreePort()
			.build();
			try {
				service.start();
			} catch (IOException e) {
				stopService();
				e.printStackTrace();
			}
			driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
			break;
		default:
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driver; 
	}

	public static void stopService() {
		if(service != null){
			service.stop();
		}
	}
	
	public static void setDownloadPath(String path){
		downloadPath = path;
	}
}
