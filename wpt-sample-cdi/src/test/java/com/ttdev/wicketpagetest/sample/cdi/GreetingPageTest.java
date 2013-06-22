package com.ttdev.wicketpagetest.sample.cdi;

import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.ChangeResistantMockFactory;
import com.ttdev.wicketpagetest.MockableCDIBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class GreetingPageTest {

	private ChangeResistantMockFactory factory = new ChangeResistantMockFactory(
			this);

	abstract class MockedGreetingService implements GreetingService {
		@Override
		public String getSubject() {
			return "m";
		}
	}

	@Test
	public void testMockCDIInjection() {
		MockableCDIBeanInjector.mockBean("greetingService",
				factory.implementAbstractMethods(MockedGreetingService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(GreetingPage.class);
		assert ws.getText("//subject").equals("m");
		assert ws.getText("//name").equals("n");
	}
}
