package com.ttdev.wicketpagetest.sample.cdi;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class GreetingPage extends WebPage {
	private static final long serialVersionUID = 1L;
	@Inject
	private GreetingService greetingService;
	@Inject
	private PeopleService peopleService;
	
	public GreetingPage() {
		add(new Label("subject", new AbstractReadOnlyModel<String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public String getObject() {
				return greetingService.getSubject();
			}
		}));
		add(new Label("name", new AbstractReadOnlyModel<String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public String getObject() {
				return peopleService.getName();
			}
		}));
	}
}
