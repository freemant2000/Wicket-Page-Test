package com.ttdev.wicketpagetest.sample.guice;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanelFactory;
import org.apache.wicket.markup.html.link.Link;

import com.google.inject.Inject;
import com.ttdev.wicketpagetest.BreadCrumbNavigator;

public class FirstBreadCrumbPanel extends BreadCrumbPanel {

	private static final long serialVersionUID = 1L;
	@Inject
	private BreadCrumbNavigator crumbNavigator;

	public FirstBreadCrumbPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
		add(new Link("next") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				crumbNavigator
						.activate(FirstBreadCrumbPanel.this,
								new BreadCrumbPanelFactory(
										SimpleBreadCrumbPanel.class));
			}
		});
		add(new Link("replace") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				crumbNavigator.removeLastParitcipant(FirstBreadCrumbPanel.this);
				crumbNavigator
						.activate(FirstBreadCrumbPanel.this,
								new BreadCrumbPanelFactory(
										SimpleBreadCrumbPanel.class));
			}
		});
	
	}

	public String getTitle() {
		return "first";
	}
}
