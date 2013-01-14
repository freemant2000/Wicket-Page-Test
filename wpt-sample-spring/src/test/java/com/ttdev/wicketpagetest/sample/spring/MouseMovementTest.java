/**
 * Copyright (C) 2009 Andy Chu <andy@pisoft.com.mo>
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

import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

@Test
public class MouseMovementTest {

	public void testVisibleOnHover() throws Throwable {
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(MouseMovement.class);
		assert ws.getText("//container").equals("Container:");
		assert ws.isElementPresent("//label");
		assert !ws.isElementVisible("//label");
		ws.getAttribute("//container", "onmouseout").equals(
				String.format("$('#%s').hide()",
						ws.getAttribute("//label", "id")));
		ws.mouseMoveOver("//container");
		ws.waitUntilElementVisible("//label");
	}
}
