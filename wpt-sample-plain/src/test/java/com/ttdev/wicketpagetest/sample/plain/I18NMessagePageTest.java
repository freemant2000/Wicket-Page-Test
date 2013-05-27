package com.ttdev.wicketpagetest.sample.plain;

import java.util.Locale;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSelenium;

@Test
public class I18NMessagePageTest {

	public void testDisplayI18NMessageIfLocaleSwitched() {
		WicketSelenium ws = WebPageTestContext.getWicketSelenium();
		ws.switchDefaultLocale();
		ws.openBookmarkablePage(I18NMessagePage.class);
		assert ws.getText(By.id("m")).equals("Hello");
		ws.setPreferredLocale(Locale.TRADITIONAL_CHINESE);
		ws.openBookmarkablePage(I18NMessagePage.class);
		assert ws.getText(By.id("m")).equals("您好") : ws.getSelenium()
				.getPageSource();
		ws.switchDefaultLocale();
		ws.openBookmarkablePage(I18NMessagePage.class);
		assert ws.getText(By.id("m")).equals("Hello") : ws.getSelenium()
				.getPageSource();
	}
}
