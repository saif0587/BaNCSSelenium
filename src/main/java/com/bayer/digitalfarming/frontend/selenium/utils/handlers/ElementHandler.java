package com.bayer.digitalfarming.frontend.selenium.utils.handlers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.bayer.digitalfarming.frontend.selenium.utils.config.PropertiesRepository;

public class ElementHandler extends BaseHandler {

	private static Logger LOGGER = LogManager.getLogger(BaseHandler.class);

	public ElementHandler(WebDriver driver) {
		super(driver);
	}

	public WebElement getElement(String selector) {
		return findElement(selector);
	}

	public List<WebElement> getElements(String selector) {
		return findElements(selector);
	}

	public void clickElement(String selector, String... waitFor) {
		try {
			WebElement element = findElement(selector);
			waitforElementTobeClickable(element);
			if (element != null) {
				element.click();
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to click on the element: " + selectorName(selector) + "\n ");
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void clickWebElement(String selector) {
		try {
			WebElement element = findElement(selector);
			waitforElementTobeClickable(element);
			if (element != null) {
				element.click();
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to click on the element: " + selectorName(selector) + "\n ");
		}
	}

	public void writeText(String selector, String text, String... waitFor) {
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				textElement.click();
				textElement.clear();
				textElement.click();
				textElement.sendKeys(text);
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to click on the element: " + selectorName(selector) + "\n ");
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public String getText(String selector) {
		String text = null;
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				text = textElement.getText();
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to get the text from element: " + selectorName(selector) + "\n ");
		}
		return text;
	}

	public String getTextFromValueAttribute(String selector) {
		String text = null;
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				text = textElement.getAttribute("value");
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to locate the element: " + selectorName(selector));
		}
		return text;
	}

	public String getTextFromAttribute(String selector, String attribute) {
		String text = null;
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				text = textElement.getAttribute(attribute);
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to get the text from " + attribute + " attribute of an element: "
					+ selectorName(selector) + "\n ");
		}
		return text;
	}

	public void waitForTextTobePresentTextbox(String selector) {
		final WebElement textElement = findElement(selector);
		(new WebDriverWait(driver, PropertiesRepository.getInt("global.driver.wait")))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver webDriver) {
						return textElement.getAttribute("value").length() != 0;
					}
				});
	}

	public Select getDropdown(String selector) {
		Select dropDown = null;
		try {
			WebElement we = findElement(selector);
			if (we != null) {
				dropDown = new Select(we);
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to get the dropdwon element: " + selectorName(selector));
		}
		return dropDown;
	}

	public boolean isDropDownDisplayed(String selector) {
		boolean isTrue = false;
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				isTrue = true;
			}
		} catch (WebDriverException e) {
			Assert.fail("Dropdown is not displayed \n " + e.getMessage());
		}
		return isTrue;
	}

	public void selectByVisibleText(String selector, String textToSelect, String... waitFor) {
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				dropdown.selectByVisibleText(textToSelect);
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to select value from the dropdown:" + selectorName(selector) + "\n ");
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void selectByValue(String selector, String value, String... waitFor) {
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				dropdown.selectByValue(value);
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to select value from the dropdown:" + selectorName(selector) + "\n ");
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void selectByIndex(String selector, int index, String... waitFor) {
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				dropdown.selectByIndex(index);
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to select value from the dropdown:" + selectorName(selector) + "\n ");
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public String getSelectedValueFromDropDwon(String selector, String... waitFor) {
		String selectedValue = null;
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				selectedValue = dropdown.getFirstSelectedOption().getText();
			}
		} catch (WebDriverException e) {
			LOGGER.error("Unable to get the selected value from the dropdown:" + selector + "\n " + e.getMessage());
			throw new WebDriverException("Unable to get the selected value from the dropdown:" + selector + "\n " + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
		return selectedValue;
	}

	public void launchApplication(String url, String... waitFor) {
		try {
			driver.get(url);
		} catch (WebDriverException e) {
			Assert.fail("Unable to launch the application url: " + url + "\n" + e.getMessage());
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public boolean isElementDisplayed(String selector) {
		boolean isTrue = false;
		try {
			WebElement element = findElement(selector);
			if (element != null) {
				isTrue = element.isDisplayed();
			}
		} catch (WebDriverException e) {
			Assert.fail("Element " + selectorName(selector) + " is not displayed \n ");
		}
		return isTrue;
	}

	public boolean isElementNotDisplayed(String selector) {
		boolean isTrue = false;
		try {
			List<WebElement> element = findElements(selector);
			if (element.isEmpty()) {
				isTrue = true;
			}
		} catch (WebDriverException e) {
			Assert.fail("Element " + selectorName(selector) + " is displayed \n ");
		}
		return isTrue;
	}

	public List<WebElement> getAllOptionsFromDropdown(String selector) {
		List<WebElement> allOptions = null;
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				allOptions = dropdown.getOptions();
			}
		} catch (WebDriverException e) {
			Assert.fail("Unable to get all the options from the dropdown:" + selectorName(selector) + "\n ");
		}
		return allOptions;
	}

	public boolean isElementPresent(String selector) {
		boolean isTrue = false;
		List<WebElement> element = findElements(selector);
		if (element.size() > 0) {
			isTrue = true;
		}
		return isTrue;
	}

}
