/**
 * Copyright (C) 2009 Kent Tong <freemant2000@yahoo.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * Free Software Foundation version 3.
 *
 * program is distributed in the hope that it will be useful,
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ttdev.wicketpagetest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class allows you to specify options to control the behavior of
 * {@link WebPageTestContext}. For example, by default it assumes that your web
 * content folder is src/main/webapp. If it is not the case, you must set the
 * {@link #docBase} property. Also, it uses Firefox by default. If you'd like to
 * use another browser such as Chrome or IE, you need to set {@link #selenium}
 * yourself.
 * 
 * @author Kent Tong
 * 
 */
public class Configuration extends WebAppJettyConfiguration {
	private static final String FIREFOX_DRIVER_FULL_NAME = "org.openqa.selenium.firefox.FirefoxDriver";

	private static final String PROPERTY_KEY_WEBDRIVER_CLASS = "com.ttdev.wicketpagetest.webdriver.class";

	/**
	 * The URL prefix that maps to the Wicket filter (default: app).
	 */
	private String wicketFilterPrefix = "app";

	/**
	 * If you need to customize the {@link WebDriver} instance, you can create
	 * it yourself and store it into this field. If it is not provided, it will
	 * create one using the {@link #driverClass}.
	 */
	private WebDriver selenium = null;

	public String getWicketFilterPrefix() {
		return wicketFilterPrefix;
	}

	public void setWicketFilterPrefix(String wicketFilterPrefix) {
		this.wicketFilterPrefix = wicketFilterPrefix;
	}

	/**
	 * Get the {@link #selenium} field if it is non-null. Otherwise, try to use
	 * the value of the system property {@link #PROPERTY_KEY_WEBDRIVER_CLASS} as
	 * the class of the driver to create a new instance and store it into the
	 * field. If there is no such system property, use the Firefox driver.
	 * 
	 * @return the Selenium ({@link WebDriver}) object
	 */
	public WebDriver getSelenium() {
		if (selenium == null) {
			try {
				String className = System.getProperty(
						PROPERTY_KEY_WEBDRIVER_CLASS, FIREFOX_DRIVER_FULL_NAME);
				Class<?> driverClass = Class.forName(className);
				selenium = (WebDriver) driverClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return selenium;
	}

	public void setSelenium(WebDriver selenium) {
		this.selenium = selenium;
	}

}
