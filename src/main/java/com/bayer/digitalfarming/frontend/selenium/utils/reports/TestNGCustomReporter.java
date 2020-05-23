package com.bayer.digitalfarming.frontend.selenium.utils.reports;

import org.apache.log4j.Logger;

import org.testng.Reporter;

public class TestNGCustomReporter extends Reporter {

	public static void log(Logger logger, String log) {
		logger.info(log);
		TestNGCustomReporter.customLog(log + "</br>");
	}

	public static void customLog(String str) {
		log(getCurrentTestResult().getMethod().getMethodName() + " : " + str);
		ReportHelper.log(getCurrentTestResult().getMethod().getMethodName(), str);
		ExtentReportManager.log(getCurrentTestResult().getMethod().getMethodName(), str);
	}
}