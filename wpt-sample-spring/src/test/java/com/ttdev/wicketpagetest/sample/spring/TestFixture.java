package com.ttdev.wicketpagetest.sample.spring;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ttdev.wicketpagetest.Configuration;
import com.ttdev.wicketpagetest.WebPageTestContext;

public class TestFixture {
	@BeforeSuite
	public void setUp() throws Exception {
		Configuration cfg = new Configuration();
		// tell wpt to provide an overriding web.xml to Jetty
		cfg.setOverrideWebXml("web-test.xml");
		// create HtmlUnit driver. true means enable Javascript
		// HtmlUnitDriver selenium = new HtmlUnitDriver(true);
		// use this web driver instance
		// cfg.setSelenium(selenium);
		// go ahead to launch Jetty and Selenium
		WebPageTestContext.beforePageTests(cfg);
	}

	@AfterSuite
	public void tearDown() throws Exception {
		// shutdown Jetty and Selenium
		WebPageTestContext.afterPageTests();
	}

}
