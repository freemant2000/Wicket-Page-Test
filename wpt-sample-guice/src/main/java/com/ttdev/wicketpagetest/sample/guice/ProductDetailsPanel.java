package com.ttdev.wicketpagetest.sample.guice;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ProductDetailsPanel extends BreadCrumbPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String productID;

	public ProductDetailsPanel(String id, IBreadCrumbModel breadCrumbModel, String productID) {
		super(id, breadCrumbModel);
		this.productID = productID;
	}

	public IModel<String> getTitle() {
		return new Model<String>("Product Details");
	}

	public String getProductID() {
		return productID;
	}
}
