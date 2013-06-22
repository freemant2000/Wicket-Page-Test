package com.ttdev.wicketpagetest.sample.cdi;

import javax.enterprise.inject.Default;

@Default
public class DefaultGreetingService implements GreetingService {
	@Override
	public String getSubject() {
		return "s";
	}
}
