package com.ttdev.wicketpagetest.sample.spring;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ProductListingPage extends WebPage {
	private static final long serialVersionUID = 1L;
	@SpringBean
	private ProductService productService;

	public ProductListingPage() {
		List<Product> products = productService.getAll();
		ListView<Product> eachProduct = new ListView<Product>("eachProduct",
				products) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Product> item) {
				item.add(new Label("name", item.getModelObject().getName()));
			}
		};
		add(eachProduct);
	}
}
