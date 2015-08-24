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

package com.ttdev.wicketpagetest.sample.plain;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.Mock;
import com.ttdev.wicketpagetest.MockableBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

// Must NOT mark the class as @Test, otherwise it will treat
// the setter as test methods!
public class PageCallingBackTestWithMethodCalls {
	private String input;

	public void setInput(String input) {
		this.input = input;
	}

	public static class MyPageCallingBack extends PageCallingBack {

		private static final long serialVersionUID = 1L;

		// get access to the test case instance
		@Mock
		private PageCallingBackTestWithMethodCalls test;

		@Override
		protected void onInputAvailable(String input) {
			// It is very important that you access the test object through
			// its methods instead of direct field access! This is because
			// the proxy is a CGLIB proxy (which is required to proxy a class
			// instead of an interface) and thus can forward method access only
			// to the target object. You can indeed access the fields in proxy,
			// but they aren't the same fields in the target.
			test.setInput(input);
		}

	}

	@Test
	public void testCallBack() {
		MockableBeanInjector.mockBean("test", this);
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(MyPageCallingBack.class);
		ws.sendKeys(By.name("input"), "abc");
		ws.setResponsePageMarker(); 
		ws.click(By.xpath("//input[@type='submit']"));
		ws.waitForMarkedPage();
		assert input.equals("abc");
	}
}
