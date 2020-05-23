package com.bayer.digitalfarming.frontend.selenium.utils.reports;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogHandler {
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class className) {
		return LogManager.getLogger(className);
	}

}
