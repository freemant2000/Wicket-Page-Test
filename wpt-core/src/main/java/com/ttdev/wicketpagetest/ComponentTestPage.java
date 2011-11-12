package com.ttdev.wicketpagetest;

import org.apache.wicket.markup.html.WebPage;

public class ComponentTestPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public ComponentTestPage(ComponentFactory factory) {
		add(factory.createComponent("testComp"));
	}
}
