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

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.PageFlowNavigator;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class ProductIDPageTest {
	private String log;

	public void testProvidingCorrectProductID() {
		MockableSpringBeanInjector.mockBean("navigator",
				new PageFlowNavigator() {

					public void setResponsePage(Component from, Page to) {
						ProductDetailsPage r = (ProductDetailsPage) to;
						log = r.getProductID();
						// don't really display the next page which you probably
						// haven't worked on yet. Just redisplay the current
						// page.
					}
				});
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(ProductIDPage.class);
		ws.findElement(By.name("productID")).sendKeys("p123");
		ws.click(By.xpath("//input[@type='submit']"));
		assert log.equals("p123");
	}
}
