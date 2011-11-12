package com.ttdev.wicketpagetest;

import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;

public interface BreadCrumbNavigator {

	void activate(BreadCrumbPanel from, IBreadCrumbPanelFactory factory);

	void removeLastParitcipant(BreadCrumbPanel from);
}
