/**
 * Copyright (C)  2009 Andy Chu <andychu@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * Free Software Foundation version 3.
 *
 * program is distributed in the hope that it will be useful,
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ttdev.wicketpagetest.sample.guice;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import com.google.inject.Inject;
import com.ttdev.wicketpagetest.PageFlowNavigator;

public class ProductIDPage extends WebPage {
	private static final long serialVersionUID = 1L;
	private String productID;
	@Inject
	private PageFlowNavigator navigator;

	public ProductIDPage() {
		Form<ProductIDPage> form = new Form<ProductIDPage>("form",
				new CompoundPropertyModel<ProductIDPage>(this)) {
			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
				ProductDetailsPage r = new ProductDetailsPage(productID);
				navigator.setResponsePage(this, r);
			}
		};
		add(form);
		form.add(new TextField<String>("productID"));
	}

}
