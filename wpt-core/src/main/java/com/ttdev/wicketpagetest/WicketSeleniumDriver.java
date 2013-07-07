package com.ttdev.wicketpagetest;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WicketSeleniumDriver extends WicketSelenium {

	public WicketSeleniumDriver(Configuration cfg, WebDriver selenium) {
		super(cfg, selenium);
	}

	public void click(String wicketPath) {
		findWicketElement(wicketPath).click();
	}

	public void clear(String wicketPath) {
		findWicketElement(wicketPath).clear();
	}

	public void sendKeys(String wicketPath, CharSequence... keysToSend) {
		findWicketElement(wicketPath).sendKeys(keysToSend);
	}

	public String getText(String wicketPath) {
		return findWicketElement(wicketPath).getText();
	}

	public String getValue(String wicketPath) {
		return getAttribute(wicketPath, "value");
	}

	public String getAttribute(String wicketPath, String attrName) {
		return findWicketElement(wicketPath).getAttribute(attrName);
	}

	public void wait(ExpectedCondition<?> condition) {
		try {
			new WebDriverWait(getSelenium(), AJAX_TIMEOUT_IN_SECONDS)
					.until(condition);
		} catch (StaleElementReferenceException e) {
			// NOTE: wait for the latest page for StaleElementReferenceException
			wait(condition);
		}
	}

	public void waitUntilElementVisible(final String wicketPath) {
		wait(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return isElementVisible(wicketPath);
			}
		});
	}

	public void waitUntilAttributeChanged(final String wicketPath,
			final String attrName, final String changeTo) {
		wait(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return getAttribute(wicketPath, attrName).equals(changeTo);
			}
		});
	}

	public void waitUntilTextChanged(final String wicketPath,
			final String changeTo) {
		wait(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return getText(wicketPath).equals(changeTo);
			}
		});
	}

	public boolean isElementPresent(String wicketPath) {
		try {
			findWicketElement(wicketPath);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementVisible(String wicketPath) {
		return findWicketElement(wicketPath).isDisplayed();
	}

	public void mouseMoveOver(String wicketPath) {
		WebElement targetElement = findWicketElement(wicketPath);
		Actions builder = new Actions(getSelenium());
		builder.moveToElement(targetElement).build().perform();
	}
}
