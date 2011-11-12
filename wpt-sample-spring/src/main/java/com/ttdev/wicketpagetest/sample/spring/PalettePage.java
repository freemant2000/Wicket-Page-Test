/**
 * Copyright (C) 2009 Kent Tong <freemant2000@yahoo.com>
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

package com.ttdev.wicketpagetest.sample.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class PalettePage extends WebPage {
	private static final long serialVersionUID = 1L;
	@SpringBean
	private ProductService ps;
	private List<Product> selectedProducts;

	public PalettePage() {
		Form<Void> f = new Form<Void>("f") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				for (Product p : selectedProducts) {
					ps.delete(p);
				}
			}
		};
		add(f);
		selectedProducts = new ArrayList<Product>();
		final List<Product> availableProducts = ps.getAll();
		Palette<Product> palette = new Palette<Product>("palette",
				new PropertyModel<List<Product>>(this, "selectedProducts"),
				new AbstractReadOnlyModel<List<Product>>() {

					private static final long serialVersionUID = 1L;

					@Override
					public List<Product> getObject() {
						return availableProducts;
					}

				}, new ChoiceRenderer<Product>("name", "id"), 3, true);
		f.add(palette);
	}
}
