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

import com.ttdev.wicketpagetest.Holder;
import com.ttdev.wicketpagetest.Mock;
import com.ttdev.wicketpagetest.MockableBeanInjector;
import com.ttdev.wicketpagetest.SimpleHolder;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class PageCallingBackTestWithHolder {
	private String input;

	public static class MyPageCallingBack extends PageCallingBack {

		private static final long serialVersionUID = 1L;

		// inject a holder containing the test case instance
		@Mock
		private Holder<PageCallingBackTestWithHolder> test;

		@Override
		protected void onInputAvailable(String input) {
			// You have to call get() to get the test case instance, but
			// then you can access its fields directly because that instance
			// is the real thing, but a proxy.
			test.get().input = input;
		}

	}

	public void testCallBack() {
		MockableBeanInjector.mockBean("test", SimpleHolder.make(this));
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(MyPageCallingBack.class);
		ws.sendKeys(By.name("input"), "abc");
		ws.click(By.xpath("//input[@type='submit']"));
		assert input.equals("abc");
	}
}
