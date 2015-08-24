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
public class AjaxPageTest {
	public void testEasyWaitingForAjax() {
		MockableSpringBeanInjector.mockBean("service", new CalcService() {

			public int calcNext(int current) {
				return current + 1; 
			}
		});
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(AjaxPage.class);
		ws.subscribeAjaxDoneHandler();
		WebElement output = ws.findElement(By.id("output"));
		assert output.getText().equals("Current: 0");
		ws.findElement(By.linkText("Calculate next")).click();
		ws.waitUntilAjaxDone();
		assert output.getText().equals("Current: 1");
		ws.findElement(By.linkText("Calculate next")).click();
		ws.waitUntilAjaxDone();
		assert output.getText().equals("Current: 2");
	}

}
