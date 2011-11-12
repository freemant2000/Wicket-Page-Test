package com.ttdev.wicketpagetest;

import java.util.List;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbParticipant;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;

public class DefaultBreadCrumbNavigator implements BreadCrumbNavigator {

	public void activate(BreadCrumbPanel from, IBreadCrumbPanelFactory factory) {
		from.activate(factory);
	}

	public void removeLastParitcipant(BreadCrumbPanel from) {
		List<IBreadCrumbParticipant> participants = from.getBreadCrumbModel()
				.allBreadCrumbParticipants();
		if (participants.isEmpty()) {
			return;
		}
		participants.remove(participants.size() - 1);
	}
}
