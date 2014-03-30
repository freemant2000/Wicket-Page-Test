package com.ttdev.wicketpagetest.sample.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ttdev.wicketpagetest.Configuration;
import com.ttdev.wicketpagetest.WebPageTestBasicContext;

@RunWith(Suite.class)
@SuiteClasses( { BookmarkablePageTest.class, MountPageTest.class })
public class MyTestSuite {
	@BeforeClass
	static public void setUp() throws Exception {
		Configuration configuration = new Configuration();
		configuration.setWicketFilterPrefix("");
		WebPageTestBasicContext.beforePageTests(configuration);
	}

	@AfterClass
	static public void tearDown() throws Exception {
		WebPageTestBasicContext.afterPageTests();
	}

}
