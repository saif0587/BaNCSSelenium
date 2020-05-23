package com.bayer.digitalfarming.frontend.selenium.pages;

import java.util.Set;

import org.openqa.selenium.WebDriver;

import com.bayer.digitalfarming.frontend.selenium.utils.config.PropertiesRepository;
import com.bayer.digitalfarming.frontend.selenium.utils.handlers.ActionHandler;
import com.bayer.digitalfarming.frontend.selenium.utils.handlers.ElementHandler;
import com.bayer.digitalfarming.frontend.selenium.utils.handlers.WindowHandler;
import com.bayer.digitalfarming.frontend.selenium.utils.reports.TestNGCustomReporter;
import com.bayer.digitalfarming.frontend.selenium.utils.test.BaseTest;

public class AmazonHomePage extends BaseTest {

	private WebDriver driver;
	private ElementHandler elementHandler;
	private ActionHandler actionHandler;
	private WindowHandler windowHandler;
	
	public AmazonHomePage(WebDriver driver) {
		this.driver = driver;
		elementHandler= new ElementHandler(driver);
		actionHandler=new ActionHandler(driver);
		windowHandler=new WindowHandler(driver);
	}
	public void openAmazonURL(){
		driver.get(PropertiesRepository.getString("amazon.url"));
		TestNGCustomReporter.log(logger, "Entered The URL");
		TestNGCustomReporter.log(logger, "Home Page is Displaed Sucessfully ");
	}
	public void enterUser(String user){
		elementHandler.writeText("amazon.user", user);
		TestNGCustomReporter.log(logger, "Entered User Name Sucessfully ");
	}
	
	public void enterPwd(String pwd){
		elementHandler.writeText("amazon.pwd", pwd);
		TestNGCustomReporter.log(logger, "Entered Password sucessfully ");
	}
	public void clkSubmitButton(){
		elementHandler.clickElement("amazon.submitbtn");
		TestNGCustomReporter.log(logger, "Clicked On Submit");
	}
	public void clkHome(){
		elementHandler.clickWebElement("amazon.home");
		TestNGCustomReporter.log(logger, "Login Sucessfull");
		TestNGCustomReporter.log(logger, "Well Come Page Available");
	}
	public void logOut(){
		elementHandler.clickWebElement("amazon.logout");
		TestNGCustomReporter.log(logger, "Click on Logput");
		TestNGCustomReporter.log(logger, "Logout Sucessfully ");
	}
}
