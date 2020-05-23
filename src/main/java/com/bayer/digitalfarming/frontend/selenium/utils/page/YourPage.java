package com.bayer.digitalfarming.frontend.selenium.utils.page;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bayer.digitalfarming.frontend.selenium.utils.config.PropertiesRepository;
import com.bayer.digitalfarming.frontend.selenium.utils.handlers.ElementHandler;
import com.bayer.digitalfarming.frontend.selenium.utils.test.BaseTest;

public class YourPage extends BaseTest {

	private WebDriver driver;
	public YourPage(WebDriver webDriver) {
		this.driver=webDriver;
	}

	private void loadMainPageURL() {
		driver.get("https://www.google.co.in/");
		System.out.println("My URL :"+PropertiesRepository.getString("mainpage.url"));
		//setDriverWait(PropertiesRepository.getString("jblearning.mainpage.url.waitfor"));
	}

	public void functionalityMethod() {
		// Load main page
		loadMainPageURL();
		ElementHandler eleHandler=new ElementHandler(driver);
		eleHandler.findElement(By.xpath(PropertiesRepository.getString("com.mainpage.extertext")),200);
		
	}
}
