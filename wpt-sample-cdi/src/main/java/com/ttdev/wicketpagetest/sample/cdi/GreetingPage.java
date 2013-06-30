package com.ttdev.wicketpagetest.sample.cdi;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;

import com.ttdev.wicketpagetest.MockableCDIBeanInjector;

public class GreetingPage extends WebPage {
	private static final long serialVersionUID = 1L;
	@Inject
	private GreetingService greetingService;
	@Inject
	private PeopleService peopleService;

	class LowerCaseModel extends AbstractReadOnlyModel<String> {
		private static final long serialVersionUID = 1L;
		@Inject
		private LowerCaseService lowerCaseService;

		public LowerCaseModel() {
			MockableCDIBeanInjector.injectBeans(this);
		}

		@Override
		public String getObject() {
			return lowerCaseService.lowerCase(greetingService.getSubject());
		}
	}

	class UpperCaseModel extends AbstractReadOnlyModel<String> {
		private static final long serialVersionUID = 1L;
		@Inject
		private UpperCaseService upperCaseService;

		public UpperCaseModel() {
			MockableCDIBeanInjector.injectBeans(this);
		}

		@Override
		public String getObject() {
			return upperCaseService.upperCase(peopleService.getName());
		}

	}

	public GreetingPage() {
		add(new Label("subject", new LowerCaseModel()));
		add(new Label("name", new UpperCaseModel()));
	}
}
