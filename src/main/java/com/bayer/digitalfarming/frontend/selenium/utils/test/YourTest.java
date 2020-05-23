package com.bayer.digitalfarming.frontend.selenium.utils.test;

import java.util.jar.Manifest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.bayer.digitalfarming.frontend.selenium.utils.assertions.VerificationHandler;
import com.bayer.digitalfarming.frontend.selenium.utils.config.PropertiesRepository;
import com.bayer.digitalfarming.frontend.selenium.utils.driverfactory.DriverFactory;
import com.bayer.digitalfarming.frontend.selenium.utils.page.YourPage;
import com.bayer.digitalfarming.frontend.selenium.utils.reports.ExtentReportManager;

@Listeners(com.bayer.digitalfarming.frontend.selenium.utils.reports.ExtentReportManager.class)
//public class YourTest extends BaseTest {

public class YourTest extends BaseTest {
		Logger logger = LogManager.getLogger(this.getClass());
		@Test(description="Search in Google Home Page")
		public void testFunctionality() {
			YourPage yPage = new YourPage(driver);
			//String result = yPage.functionalityMethod();
			yPage.functionalityMethod();
			VerificationHandler.verifyEquals("test Cases Pass","test Cases Pass");
		}
	

}
