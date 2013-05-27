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

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;
import com.ttdev.wicketpagetest.BreadCrumbNavigator;

public class ProductIDPanel extends BreadCrumbPanel {
	
	private static final long serialVersionUID = 1L;

	private String productID;

	@Inject
	private BreadCrumbNavigator crumbNavigator;;

	public ProductIDPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
		Form<ProductIDPanel> form = new Form<ProductIDPanel>("form",
				new CompoundPropertyModel<ProductIDPanel>(this)) {
			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
				crumbNavigator.activate(ProductIDPanel.this,
						new IBreadCrumbPanelFactory() {
							private static final long serialVersionUID = 1L;

							public BreadCrumbPanel create(String componentId,
									IBreadCrumbModel breadCrumbModel) {
								return new ProductDetailsPanel(componentId,
										breadCrumbModel, productID);
							}
						});
			}
		};
		add(form);
		form.add(new TextField<String>("productID"));
	}

	public IModel<String> getTitle() {
		return new Model<String>("Product ID");
	}
}
