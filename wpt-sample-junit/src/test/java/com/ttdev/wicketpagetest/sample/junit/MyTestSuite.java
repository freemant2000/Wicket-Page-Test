package com.ttdev.wicketpagetest.sample.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ttdev.wicketpagetest.WebPageTestBasicContext;

@RunWith(Suite.class)
@SuiteClasses( { BookmarkablePageTest.class })
public class MyTestSuite {
	@BeforeClass
	static public void setUp() throws Exception {
		WebPageTestBasicContext.beforePageTests();
	}

	@AfterClass
	static public void tearDown() throws Exception {
		WebPageTestBasicContext.afterPageTests();
	}

}
