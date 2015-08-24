package com.ttdev.wicketpagetest.sample.cdi;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.ttdev.wicketpagetest.MockableCDIBeanInjector;
import com.ttdev.wicketpagetest.PageMarkingListener;

public class MyApp extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return GreetingPage.class;
	}

	@Override
	protected void init() {
		super.init();
		MockableCDIBeanInjector.installInjector(this);
		getRequestCycleListeners().add(new PageMarkingListener());
	}
}
