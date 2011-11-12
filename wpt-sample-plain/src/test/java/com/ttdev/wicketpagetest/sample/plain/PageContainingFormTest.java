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

import com.ttdev.wicketpagetest.SerializableProxyFactory;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class PageContainingFormTest {
	public void testSubmitForm() {
		MyService mockService = new MyService() {

			public String getDefaultInput() {
				return "xyz";
			}

			public String getResult(String input) {
				return input + input;
			}
		};
		SerializableProxyFactory factory = new SerializableProxyFactory();
		MyService proxyService = factory.createProxy(MyService.class,
				mockService);
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(PageContainingForm.class, proxyService);
		assert ws.getValue(By.name("input")).equals("xyz");
		ws.click(By.xpath("//input[@type='submit']"));
		assert ws.getText(By.id("result")).equals("xyzxyz");
	}

}
