package com.ttdev.wicketpagetest.sample.cdi;

public class DefaultUpperCaseService implements UpperCaseService{
	@Override
	public String upperCase(String s) {
		return s.toUpperCase();
	}
}
