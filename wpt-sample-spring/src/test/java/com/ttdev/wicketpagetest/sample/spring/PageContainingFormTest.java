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
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class PageContainingFormTest {
	public void testSubmitForm() {
		MockableSpringBeanInjector.mockBean("service", new MyService() {

			public String getDefaultInput() {
				return "xyz";
			}

			public String getResult(String input) {
				return input + input;
			}

		});
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(PageContainingForm.class);
		assert ws.getValue(By.name("input")).equals("xyz");
		ws.click(By.xpath("//input[@type='submit']"));
		ws.waitUntilDomReady(); // needed only if response page is same page
		assert ws.getText(By.id("result")).equals("xyzxyz");
	}

	public void testSubmitFormWithDefaultMyService() {
		// the predefine mocked beans in testSubmitForm may affect this test. it
		// is better to clear all mocked beans right before any action.
		MockableSpringBeanInjector.clearMockBeans();
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openHomePage();
		assert ws.getValue(By.name("input")).equals("abc");
		ws.click(By.xpath("//input[@type='submit']"));
		ws.waitUntilDomReady();
		assert ws.getText(By.id("result")).equals("ABC");
	}
}
