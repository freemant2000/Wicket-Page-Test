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
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

public class ProductIDPageTest {

	public void catchResponsePage() {
		MyApp app = (MyApp) WebPageTestContext.getWebApplication();
		// expect a response page of the ProductDetailsPage class.
		app.getCrpl().setPageClassExpected(ProductDetailsPage.class);
	}

	public void stopCatchingResponsePage() {
		MyApp app = (MyApp) WebPageTestContext.getWebApplication();
		app.getCrpl().setPageClassExpected(null);
	}

	@Test
	public void testProvidingCorrectProductID() {
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(ProductIDPage.class);
		ws.findElement(By.name("productID")).sendKeys("p123");
		ws.setResponsePageMarker();
		catchResponsePage();
		try {
			ws.click(By.xpath("//input[@type='submit']"));
			ws.waitForMarkedPage();
		} finally {
			stopCatchingResponsePage();
		}
		// check the page caught
		MyApp app = (MyApp) WebPageTestContext.getWebApplication();
		ProductDetailsPage p = (ProductDetailsPage) app.getCrpl()
				.getPageCaught();
		assert p.getProductID().equals("p123");
	}
}
