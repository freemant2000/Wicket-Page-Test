package com.ttdev.wicketpagetest.sample.guice;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.breadcrumb.DefaultBreadCrumbsModel;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.ComponentFactory;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

@Test
public class SimpleBreadCrumbPanelTest {

	public void testOpenBreadCrumbPanel() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBreadCrumbPanel(new IBreadCrumbPanelFactory() {

			private static final long serialVersionUID = 1L;

			public BreadCrumbPanel create(String componentId,
					IBreadCrumbModel breadCrumbModel) {
				return new SimpleBreadCrumbPanel(componentId, breadCrumbModel);
			};
		});
		assert ws.getText(By.id("info")).equals("Hello");
		String breadCrumbText = ws.getText("//crumb//link");
		assert breadCrumbText.equals("Simple");
	}

	public void testOpenBreadCrumbPanelLikeOtherComponent() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openComponent(new ComponentFactory() {
			private static final long serialVersionUID = 1L;

			public Component createComponent(String id) {
				return new SimpleBreadCrumbPanel(id,
						new DefaultBreadCrumbsModel());
			}
		});
		assert ws.getText(By.id("info")).equals("Hello");
		boolean linkPresent = false;
		// there should be no crumb link by calling openComponent
		try {
			ws.getText("//crumb//link");
			linkPresent = true;
		} catch (Throwable e) {
		}
		assert !linkPresent;
	}
}
