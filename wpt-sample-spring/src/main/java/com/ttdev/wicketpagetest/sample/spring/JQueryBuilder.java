package com.ttdev.wicketpagetest.sample.spring;

public class JQueryBuilder {
	
	private StringBuffer buffer;

	public JQueryBuilder(String locator) {
		buffer = new StringBuffer(String.format("$('%s')", locator));
	}

	public String show() {
		return buffer.append(".show()").toString();
	}

	public String hide() {
		return buffer.append(".hide()").toString();
	}
}
