package com.ttdev.wicketpagetest.sample.guice;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;

public class ProductDetailsPanel extends BreadCrumbPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String productID;

	public ProductDetailsPanel(String id, IBreadCrumbModel breadCrumbModel, String productID) {
		super(id, breadCrumbModel);
		this.productID = productID;
	}

	public String getTitle() {
		return "Product Details";
	}

	public String getProductID() {
		return productID;
	}
}
