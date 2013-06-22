package com.ttdev.wicketpagetest.sample.cdi;

import javax.enterprise.inject.Default;

@Default
public class DefaultPeopleService implements PeopleService {

	@Override
	public String getName() {
		return "n";
	}

}
