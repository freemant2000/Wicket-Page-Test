package com.ttdev.wicketpagetest.sample.guice;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;

public class SimpleBreadCrumbPanel extends BreadCrumbPanel {

	private static final long serialVersionUID = 1L;

	public SimpleBreadCrumbPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
	}

	public String getTitle() {
		return "Simple";
	}
}
