package com.ttdev.wicketpagetest.sample.cdi;

public class DefaultLowerCaseService implements LowerCaseService {

	@Override
	public String lowerCase(String s) {
		return s.toLowerCase();
	}

}
