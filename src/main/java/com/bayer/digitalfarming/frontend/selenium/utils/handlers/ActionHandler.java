package com.bayer.digitalfarming.frontend.selenium.utils.handlers;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.bayer.digitalfarming.frontend.selenium.utils.reports.LogHandler;

import java.util.ArrayList;

public class ActionHandler extends BaseHandler {

	public ActionHandler(WebDriver driver) {
		super(driver);
	}

	/***
	 * Method is used to performs double click on the specified element
	 * 
	 * @param selector
	 *            Element to be double click
	 * @param waitFor
	 *            Element to be waiting for after the double click
	 */
	public void doubleClick(String selector, String... waitFor) {
		try {
			WebElement onElement = findElement(selector);
			Actions actions = new Actions(driver);
			if (onElement != null) {
				actions.doubleClick(onElement).build().perform();
			}
		} catch (WebDriverException e) {
			LogHandler.getLogger(this.getClass())
					.error("Unable to double click on the element: " + selector + "\n" + e.getMessage());
			throw new WebDriverException("Unable to double click on the element: " + selector + "\n" + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	/***
	 * Method is used to perform
	 * 
	 * @param selector
	 * @param waitFor
	 */
	public void moveToElementsAndClick(String selector, String... waitFor) {
		try {
			List<WebElement> elements = findElements(selector);
			if (elements.size() > 0) {
				for (WebElement element : elements) {
					Actions actions = new Actions(driver);
					if (element != null) {
						actions.moveToElement(element).build().perform();
						element.click();
					}
				}
			}
		} catch (WebDriverException e) {
			LogHandler.getLogger(this.getClass())
					.error("Unable to double click on the elements: " + selector + "\n" + e.getMessage());
			throw new WebDriverException("Unable to double click on the elements: " + selector + "\n" + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void moveToElementAndClick(String selector, String... waitFor) {
		try {
			WebElement element = findElement(selector);
			if (element != null) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).build().perform();
				element.click();
			}
		} catch (WebDriverException e) {
			LogHandler.getLogger(this.getClass())
					.error("Unable to move to element and click on the element: " + selector + "\n" + e.getMessage());
			throw new WebDriverException("Unable to move to element and click on the element: " + selector + "\n" + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void moveToElement(String selector, String... waitFor) {
		try {
			WebElement element = findElement(selector);
			if (element != null) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).build().perform();
			}
		} catch (WebDriverException e) {
			LogHandler.getLogger(this.getClass())
					.error("Unable to move to element: " + selector + "\n" + e.getMessage());

			throw new WebDriverException("Unable to move to element: " + selector + "\n" + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void keyboardAction(Keys key, String... waitFor) {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(key).build().perform();
		} catch (WebDriverException e) {
			LogHandler.getLogger(this.getClass()).error("Unable do the " + key + " key actions \n" + e.getMessage());
			throw new WebDriverException("Unable do the " + key + " key actions \n" + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void mouseclick() {
		Actions builder = new Actions(driver);
		builder.click().build().perform();
	}

	public Action createMouseClicks(ArrayList<Point> points) {
		Actions builder = new Actions(driver);
		for (Point point : points) {
			builder = builder.moveByOffset(point.getX(), point.getY()).click();
		}
		return builder.build();
	}

	public void waitForSomeTime(int timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
