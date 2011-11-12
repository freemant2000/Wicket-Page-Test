package com.ttdev.wicketpagetest;

import org.apache.wicket.extensions.breadcrumb.BreadCrumbBar;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;
import org.apache.wicket.markup.html.WebPage;

public class BreadCrumbPanelTestPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public BreadCrumbPanelTestPage(IBreadCrumbPanelFactory panelFactory) {
		BreadCrumbBar breadCrumbBar = new BreadCrumbBar("breadCrumbBar");
		add(breadCrumbBar);
		BreadCrumbPanel testPanel = panelFactory.create("testPanel",
				breadCrumbBar);
		add(testPanel);
		breadCrumbBar.setActive(testPanel);
	}
}
