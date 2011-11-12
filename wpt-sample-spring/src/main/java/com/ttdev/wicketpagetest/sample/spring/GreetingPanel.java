package com.ttdev.wicketpagetest.sample.spring;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class GreetingPanel extends Panel {
	private static final long serialVersionUID = 1L;
	@SpringBean
	private GreetingService gs;

	public GreetingPanel(String id) {
		super(id);
		add(new Label("name", gs.getName()));
	}
}
