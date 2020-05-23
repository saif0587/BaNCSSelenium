package com.bayer.digitalfarming.frontend.selenium.utils.config;

public final class GlobalProperties {

	// Exceptions constants
	public static final String PROPERTIES_EXCEPTION = "Properties Exception : There was an error processing properties";

	// Browser Constants
	public static final String FIREFOX = "firefox";
	public static final String CHROME = "chrome";
	public static final String IE = "internet explorer";
	public static final String SAFARI = "safari";

	// Default wait - 10s
	public static final int EXPLICIT_WAIT = PropertiesRepository.getInt("global.driver.wait");

	// Default Properties Files
	public static final String PROPS_LIST = "prop-files.properties";
	public static final String GLOBAL_PROPS = "global.properties";
	public static final String LOG_PROPS = "log4j.properties";
	public static final String EXTENT_REPORT_CONFIG = System.getProperty("user.dir")
			+ "/src/main/resources/ExtentReportConfig.xml";

	// Selector Types
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String LINKTEXT = "linktext";
	public static final String PARTIALLINKTEXT = "partiallink";
	public static final String CLASSNAME = "classname";
	public static final String TAGNAME = "tag";
	public static final String CSS_SELECTOR = "css";
	public static final String XPATH = "xpath";

}
