package com.ttdev.wicketpagetest.sample.spring;

import org.apache.wicket.markup.html.WebPage;

public class ProductDetailsPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private String productID;

	public ProductDetailsPage(String productID) {
		this.productID = productID;
	}

	public String getProductID() {
		return productID;
	}
}
