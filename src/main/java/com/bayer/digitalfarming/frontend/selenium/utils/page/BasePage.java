package com.bayer.digitalfarming.frontend.selenium.utils.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bayer.digitalfarming.frontend.selenium.utils.config.GlobalProperties;

public class BasePage {
	public WebDriver driver = null;
	
	protected BasePage(WebDriver webDriver) {
		driver = webDriver;		
	}
	
	protected void setDriverWait(String selector) {
		WebDriverWait driverWait = new WebDriverWait(driver, GlobalProperties.EXPLICIT_WAIT);
		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
	}
}