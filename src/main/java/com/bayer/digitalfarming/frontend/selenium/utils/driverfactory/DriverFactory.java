package com.bayer.digitalfarming.frontend.selenium.utils.driverfactory;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.bayer.digitalfarming.frontend.selenium.utils.config.GlobalProperties;
import com.bayer.digitalfarming.frontend.selenium.utils.config.PropertiesRepository;

public final class DriverFactory {
	private Logger logger = LogManager.getLogger(DriverFactory.class);
	public DesiredCapabilities capabilities;

	public DriverFactory() {
	}

	private static DriverFactory instance = new DriverFactory();

	public static DriverFactory getInstance() {
		return instance;
	}

	/***
	 *  Thread local threadDriver object for webdriver
	 */
	ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
		@SuppressWarnings("deprecation")
		@Override
		protected WebDriver initialValue() {

			//String browserType = PropertiesRepository.getString("global.browser.name");
			/* ----------------Pushpendra 
			 * Changing to Chrome Browser
			 */
			String browserType = PropertiesRepository.getString("global.browser.capability.browserName");
			DesiredCapabilities dc = CapabilityGenerator.getCapabilities(browserType);
			logger.info("Desired Capabilities : " + dc);

			if (PropertiesRepository.getBoolean("global.grid.mode")) {
				try {
					URL hubURL = new URL(PropertiesRepository.getString("global.grid.hub"));
					logger.info("Hub URL : " + hubURL);
					return new RemoteWebDriver(hubURL, dc);
				} catch (MalformedURLException e) {
					logger.error(GlobalProperties.PROPERTIES_EXCEPTION, e);
					return null;
				}
			} else {
				switch (browserType) {
					case GlobalProperties.FIREFOX:
						return new FirefoxDriver(dc);
					case GlobalProperties.IE:
						return new InternetExplorerDriver(dc);
					case GlobalProperties.CHROME:
						//return new ChromeDriver(dc);
						try {
							//return new RemoteWebDriver(new URL("https://www.google.co.in/"), dc);
							return new ChromeDriver(dc);
						} catch (Exception e) {
							e.printStackTrace();
						}
					default:
						return new FirefoxDriver(dc);
				}
			}
		}
	};

	/***
	 * call this method to get the threadDriver object and launch the browser
	 *
	 * @return currentWebDriver object
	 * @throws MalformedURLException
	 */
	public WebDriver getDriver() {

		return threadDriver.get();
	}

	/***
	 * Quits the threadDriver and closes the browser
	 */
	public void removeDriver() {

		WebDriver driver = threadDriver.get();
		//driver.close();
		driver.quit();
		threadDriver.remove();
	}

}
