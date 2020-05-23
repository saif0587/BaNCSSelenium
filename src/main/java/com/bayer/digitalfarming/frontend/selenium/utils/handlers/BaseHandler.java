package com.bayer.digitalfarming.frontend.selenium.utils.handlers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.bayer.digitalfarming.frontend.selenium.utils.config.GlobalProperties;
import com.bayer.digitalfarming.frontend.selenium.utils.config.PropertiesRepository;
import com.bayer.digitalfarming.frontend.selenium.utils.reports.LogHandler;

public class BaseHandler {

	private Logger LOGGER = LogManager.getLogger(BaseHandler.class);

	protected WebDriver driver;
	protected static long config_wait_timeout = 0L;
	protected static long config_verify_interval = 0L;

	public BaseHandler(WebDriver driver) {
		this.driver = driver;
		setupConfigValue();
	}

	private void setupConfigValue() {
		config_wait_timeout = PropertiesRepository.getLong("waittimeout");
		config_verify_interval = PropertiesRepository.getLong("verifyinterval");
	}

	private String[] stringSplit(String selector) {
		return selector.split("=", 2);

	}

	public void setWebDriverWait(String selector) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, GlobalProperties.EXPLICIT_WAIT);
			setWebDriverWaitWithDifferentLocators(wait, selector);
		} catch (WebDriverException e) {
			LogHandler.getLogger(this.getClass()).error("Element " + selector + " is not visible \n " + e.getMessage());
			throw new WebDriverException("Element " + selector + " is not visible \n " + e);
		}
	}

	private void setWebDriverWaitWithDifferentLocators(WebDriverWait wait, String selector) {
		String[] locatorWithValue = stringSplit(selector);
		if (locatorWithValue != null && locatorWithValue.length > 1) {
			switch (locatorWithValue[0].toLowerCase()) {
			case GlobalProperties.ID:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorWithValue[1])));
				break;
			case GlobalProperties.NAME:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorWithValue[1])));
				break;
			case GlobalProperties.CLASSNAME:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorWithValue[1])));
				break;
			case GlobalProperties.LINKTEXT:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorWithValue[1])));
				break;
			case GlobalProperties.PARTIALLINKTEXT:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorWithValue[1])));
				break;
			case GlobalProperties.CSS_SELECTOR:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorWithValue[1])));
				break;
			case GlobalProperties.XPATH:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorWithValue[1])));
				break;
			case GlobalProperties.TAGNAME:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorWithValue[1])));
				break;
			default:
				LOGGER.error("Please select/give the locator");
				break;
			}
		} else {
			LOGGER.error(
					"locator/selector input is not in proper format, please pass in this format in loctors.properties file, identifername=loctor=locatorvalue");
		}
	}

	public void waitforElementTobeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, GlobalProperties.EXPLICIT_WAIT);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement findElement(String selector) {
		WebDriverWait wait;
		WebElement element = null;
		try {
			wait = new WebDriverWait(driver, GlobalProperties.EXPLICIT_WAIT);
			element = findElementWithDifferentLocators(wait, PropertiesRepository.getString(selector));
		} catch (WebDriverException e) {
			Assert.fail("Unable to locate the element: " + selectorName(selector));
		}
		return element;
	}

	private WebElement findElementWithDifferentLocators(WebDriverWait wait, String selector) {
		WebElement element = null;
		String[] locatorWithValue = stringSplit(selector);
		if (locatorWithValue != null && locatorWithValue.length > 1) {
			switch (locatorWithValue[0].toLowerCase()) {
			case GlobalProperties.ID:
				element = findElement(By.id(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.NAME:
				element = findElement(By.name(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.CLASSNAME:
				element = findElement(By.className(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.LINKTEXT:
				element = findElement(By.linkText(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.PARTIALLINKTEXT:
				element = findElement(By.partialLinkText(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.CSS_SELECTOR:
				element = findElement(By.cssSelector(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.XPATH:
				element = findElement(By.xpath(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.TAGNAME:
				element = findElement(By.tagName(locatorWithValue[1]), config_wait_timeout);
				break;
			default:
				LOGGER.error("Please select/give the locator");
				break;
			}
		} else {
			LOGGER.error(
					"locator/selector input is not in proper format, please pass in this format in loctors.properties file, identifername=loctor=locatorvalue");
		}
		return element;
	}

	public WebElement findElement(By by, long timeout) {
		WebElement ele = null;
		long startTime = System.currentTimeMillis();
		long stopTime = startTime + timeout;
		while (System.currentTimeMillis() < stopTime) {
			ele = driver.findElement(by);
			if (ele != null) {
				break;
			}
			if (config_verify_interval > 0L) {
				if (timeout < config_verify_interval) {
					sleep(timeout);
				} else {
					sleep(config_verify_interval);
				}
			}
		}
		ele = driver.findElement(by);

		return ele;
	}

	public List<WebElement> findElements(String selector) {
		WebDriverWait wait;
		List<WebElement> element = null;
		try {
			wait = new WebDriverWait(driver, GlobalProperties.EXPLICIT_WAIT);
			element = findElementsWithDifferentLocators(wait, PropertiesRepository.getString(selector));
		} catch (WebDriverException e) {
			Assert.fail("Unable to locate the element: " + selectorName(selector));
		}
		return element;
	}

	private List<WebElement> findElementsWithDifferentLocators(WebDriverWait wait, String selector) {
		List<WebElement> elements = null;
		String[] locatorWithValue = stringSplit(selector);
		if (locatorWithValue != null && locatorWithValue.length > 1) {
			switch (locatorWithValue[0].toLowerCase()) {
			case GlobalProperties.ID:
				elements = findElements(By.id(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.NAME:
				elements = findElements(By.name(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.CLASSNAME:
				elements = findElements(By.className(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.LINKTEXT:
				elements = findElements(By.linkText(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.PARTIALLINKTEXT:
				elements = findElements(By.partialLinkText(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.CSS_SELECTOR:
				elements = findElements(By.cssSelector(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.XPATH:
				elements = findElements(By.xpath(locatorWithValue[1]), config_wait_timeout);
				break;
			case GlobalProperties.TAGNAME:
				elements = findElements(By.tagName(locatorWithValue[1]), config_wait_timeout);
				break;
			default:
				LOGGER.error("Please select/give the locator");
				break;
			}
		} else {
			LOGGER.error(
					"locator/selector input is not in proper format, please pass in this format in loctors.properties file, identifername=loctor=locatorvalue");
		}
		return elements;
	}

	private List<WebElement> findElements(By by, long timeout) {
		List<WebElement> ele = null;
		long startTime = System.currentTimeMillis();
		long stopTime = startTime + timeout;
		while (System.currentTimeMillis() < stopTime) {
			ele = driver.findElements(by);
			if (ele != null) {
				break;
			}
			if (config_verify_interval > 0L) {
				if (timeout < config_verify_interval) {
					sleep(timeout);
				} else {
					sleep(config_verify_interval);
				}
			}
		}
		ele = driver.findElements(by);

		return ele;
	}

	public void sleep(long ms) {
		if (ms > 0L) {
			try {
				LOGGER.debug("Sleeping for " + ms + " " + TimeUnit.MILLISECONDS.toString() + ". ");
				TimeUnit.MILLISECONDS.sleep(ms);
			} catch (InterruptedException e) {
				LOGGER.error("Caught InterruptedException", e);
			}
		}
	}
	
	public String selectorName(String selector){
		return String.format("</br><font color=%s>%s %s %s</font>","red",selector.split("\\.",2)[1], ":- ", PropertiesRepository.getString(selector));
	}
}
