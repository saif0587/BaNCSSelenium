package com.bayer.digitalfarming.frontend.selenium.testcases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.bayer.digitalfarming.frontend.selenium.pages.AmazonHomePage;
import com.bayer.digitalfarming.frontend.selenium.utils.test.BaseTest;

@Listeners(com.bayer.digitalfarming.frontend.selenium.utils.reports.ExtentReportManager.class)
public class AccessAmazon extends BaseTest{
	@Test(description="Login on Application")
	public void login(){
		AmazonHomePage amazonHomepage=new AmazonHomePage(driver);
		amazonHomepage.openAmazonURL();
		amazonHomepage.enterUser("sysadmin1");
		amazonHomepage.enterPwd("abcd1234");
		amazonHomepage.clkSubmitButton();
		amazonHomepage.clkHome();
		amazonHomepage.logOut();
	}
}
