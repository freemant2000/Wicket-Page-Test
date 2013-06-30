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

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.ByWicketIdPath;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

@Test
public class PageExtractedByWicketIdsTest {
	public void testWicketLocator() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(PageExtractedByWicketIds.class);
		ws.subscribeAjaxDoneHandler();
		assert ws.getValue("//eachRow[0]//v").equals("3");
		assert ws.getValue("//eachRow[1]//v").equals("2");
		assert ws.getValue("//eachRow[2]//v").equals("8");
		assert ws.getText("//total").equals("13");
		WebElement v1 = ws.findWicketElement("//eachRow[1]//v");
		v1.clear();
		v1.sendKeys("5");
		WebElement ok = ws.findWicketElement("//eachRow[1]//ok");
		ok.click();
		ws.waitUntilAjaxDone();
		assert ws.getText("//total").equals("16") : ws.getText("//total");
		assert ws.getText("//id.containing.dots").equals(
				"xyz");
	}
}
