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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.CatchResponsePageListener;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class ProductIDPageTest {

	private CatchResponsePageListener listener;

	@BeforeMethod
	public void catchResponsePage() {
		MyApp app = (MyApp) WebPageTestContext.getWebApplication();
		// key step: this listener will try to catch a response page
		// belonging to the ProductDetailsPage class. It will catch it
		// then schedule a dummy page (basically empty).
		listener = new CatchResponsePageListener(ProductDetailsPage.class);
		app.getRequestCycleListeners().add(listener);
	}

	@AfterMethod
	public void stopCatchingResponsePage() {
		MyApp app = (MyApp) WebPageTestContext.getWebApplication();
		app.getRequestCycleListeners().remove(listener);
	}

	public void testProvidingCorrectProductID() {
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(ProductIDPage.class);
		ws.findElement(By.name("productID")).sendKeys("p123");
		ws.click(By.xpath("//input[@type='submit']"));
		// check the page caught
		ProductDetailsPage p = (ProductDetailsPage) listener.getPageCaught();
		assert p.getProductID().equals("p123");
	}
}
