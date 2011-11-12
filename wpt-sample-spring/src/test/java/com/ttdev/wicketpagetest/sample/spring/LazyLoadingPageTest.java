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

package com.ttdev.wicketpagetest.sample.spring;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class LazyLoadingPageTest {
	public void testAjaxLazyPanel() {
		MockableSpringBeanInjector.mockBean("lps",
				new LengthyProcessingService() {
					private String result = null;

					public void start() {
						result = "abc123";
					}

					public String waitForResult() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						return result;
					}

				});
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(LazyLoadingPage.class);
		new WebDriverWait(ws.getSelenium(), 3)
				.until(new Predicate<WebDriver>() {

					public boolean apply(WebDriver input) {
						return input
								.findElement(By.xpath("//span[@class=\"c1\"]"))
								.getText().equals("abc123");
					}
				});
	}

}
