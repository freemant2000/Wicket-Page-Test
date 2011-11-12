package com.ttdev.wicketpagetest.sample.guice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.inject.Inject;

public class GreetingPanel extends Panel {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GreetingService gs;

	public GreetingPanel(String id) {
		super(id);
		add(new Label("name", gs.getName()));
	}
}
