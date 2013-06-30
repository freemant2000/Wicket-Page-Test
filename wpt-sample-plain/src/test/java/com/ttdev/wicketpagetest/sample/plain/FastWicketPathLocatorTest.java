package com.ttdev.wicketpagetest.sample.plain;

import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class FastWicketPathLocatorTest {
	@Test
	public void testFastWicketPathLocator() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(PageContainingNonWicketElement.class);
		assert ws.getText("//a//name.zh[0]").equals("aA");
		assert ws.getText("//name.zh[0]").equals("aA");
		assert ws.getText("//name.zh[1]").equals("aB");
		assert ws.getText("//content[0]").equals("bA");
		assert ws.getText("//content[1]").equals("bB");
		assert ws.getText("//content[2]").equals("bC");
		assert ws.getText("//content[3]").equals("bD");
	}
}
