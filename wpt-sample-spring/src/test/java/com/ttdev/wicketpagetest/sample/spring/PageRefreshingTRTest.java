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

import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

@Test
public class PageRefreshingTRTest {
	public void testWicketLocatorOnRefreshedTR() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(PageRefreshingTR.class);
		ws.subscribeAjaxDoneHandler();
		assert ws.getText("//eachRow[0]//count").equals("0");
		assert ws.getText("//eachRow[0]//sum").equals("0");
		assert ws.getText("//eachRow[1]//count").equals("0");
		assert ws.getText("//eachRow[1]//sum").equals("0");
		assert ws.getText("//eachRow[2]//count").equals("0");
		assert ws.getText("//eachRow[2]//sum").equals("0");
		ws.click("//eachRow[1]//inc");
		ws.waitUntilAjaxDone();
		assert ws.getText("//eachRow[0]//count").equals("0");
		assert ws.getText("//eachRow[0]//sum").equals("0");
		assert ws.getText("//eachRow[1]//count").equals("1");
		assert ws.getText("//eachRow[1]//sum").equals("1");
		assert ws.getText("//eachRow[2]//count").equals("0");
		assert ws.getText("//eachRow[2]//sum").equals("0");
		ws.click("//eachRow[1]//inc");
		ws.waitUntilAjaxDone();
		assert ws.getText("//eachRow[0]//count").equals("0");
		assert ws.getText("//eachRow[0]//sum").equals("0");
		assert ws.getText("//eachRow[1]//count").equals("2");
		assert ws.getText("//eachRow[1]//sum").equals("3");
		assert ws.getText("//eachRow[2]//count").equals("0");
		assert ws.getText("//eachRow[2]//sum").equals("0");
	}

}
