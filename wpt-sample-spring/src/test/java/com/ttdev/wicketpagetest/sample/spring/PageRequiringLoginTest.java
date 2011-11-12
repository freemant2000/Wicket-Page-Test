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

import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class PageRequiringLoginTest {
	@BeforeMethod
	public void mockLogin() {
		MyApp app = (MyApp) WebPageTestContext.getWebApplication();
		app.setMockedUser("u1");
	}

	@AfterMethod
	public void mockLogout() {
		MyApp app = (MyApp) WebPageTestContext.getWebApplication();
		app.setMockedUser(null);
	}

	public void testAlreadyLoggedIn() {
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(PageRequiringLogin.class);
		assert ws.findElement(By.id("name")).getText().equals("u1");
	}

	public void testNeedToLogIn() {
		mockLogout();
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(PageRequiringLogin.class);
		assert ws.getText(By.tagName("html")).contains("Please login");
	}
}
