/**
 * Copyright (C) 2014 Andy Chu <andychu@gmail.com>
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

package com.ttdev.wicketpagetest.sample.junit;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Test;
import org.openqa.selenium.By;

import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class MountPageTest {
	@Test
	public void testDisplay() {
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		PageParameters parameters = new PageParameters();
		parameters.add("name", "Peter");
		ws.openMountedPage("mount", parameters);
		assert ws.getText(By.id("name")).equals("Peter");
	}
	
	@Test
	public void testDisplay2() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		PageParameters parameters = new PageParameters();
		parameters.add("name", "Peter");
		parameters.add("name2", "Pen");
		ws.openMountedPage("mount2", parameters);
		assert ws.getText("//name").equals("Peter");
		assert ws.getText("//name2").equals("Pen");
	}

}
