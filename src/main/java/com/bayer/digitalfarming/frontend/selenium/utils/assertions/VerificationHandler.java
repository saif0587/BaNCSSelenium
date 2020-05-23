package com.bayer.digitalfarming.frontend.selenium.utils.assertions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.testng.Assert;

public class VerificationHandler {
	private static Logger logger = LogManager.getLogger(VerificationHandler.class);

	public static void verifyTrue(boolean flag) {
		try {
			Assert.assertTrue(flag);
		} catch (AssertionError e) {
			logger.error("False returned", e);
			Assert.fail("False returned", e);
		}
	}

	public static void verifyFalse(boolean flag) {
		try {
			Assert.assertFalse(flag);
		} catch (AssertionError e) {
			logger.error("True returned", e);
			Assert.fail("True returned", e);
		}
	}

	public static void verifyTrue(boolean flag, String message) {
		try {
			Assert.assertTrue(flag, message);
		} catch (AssertionError e) {
			logger.error(message);
			Assert.fail(message);
		}
	}

	public static void verifyFalse(boolean flag, String message) {
		try {
			Assert.assertFalse(flag, message);
		} catch (AssertionError e) {
			logger.error(message);
			Assert.fail(message);
		}
	}

	public static void verifyEquals(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			logger.error("Value miss match", e);
			Assert.fail("Value miss mismatch", e);
		}
	}

	public static void verifyContains(String mainString, String subString, String message) {
		try {
			mainString.contains(subString);
		} catch (AssertionError e) {
			logger.error(subString + " is not present in " + mainString, e);
			Assert.fail(message);
		}
	}

	public static void verifyNotEquals(String actual, String expected) {
		try {
			Assert.assertNotEquals(actual, expected);
		} catch (AssertionError e) {
			logger.error("Values miss match", e);
			Assert.fail("Values miss match", e);
		}
	}

	public static void verifyEquals(String actual, String expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			logger.error("Values miss match", e);
			Assert.fail(message);
		}
	}

	public static void verifyNotEquals(String actual, String expected, String message) {
		try {
			Assert.assertNotEquals(actual, expected, message);
		} catch (AssertionError e) {
			logger.error("Values miss match", e);
			Assert.fail(message);
		}
	}

}
