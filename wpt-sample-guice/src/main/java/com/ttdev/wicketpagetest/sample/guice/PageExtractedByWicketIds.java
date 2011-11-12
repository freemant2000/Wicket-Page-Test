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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

public class PageExtractedByWicketIds extends WebPage {
	private Label totalLabel;
	private List<Integer> values;

	public PageExtractedByWicketIds() {
		values = new ArrayList<Integer>();
		values.add(3);
		values.add(2);
		values.add(8);
		ListView<Integer> eachRow = new ListView<Integer>("eachRow", values) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Integer> item) {
				Form<Void> f = new Form<Void>("f");
				f.add(new TextField<Integer>("v", item.getModel(),
						Integer.class));
				f.add(new AjaxButton("ok") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target,
							Form<?> form) {
						target.addComponent(totalLabel);
					}

					@Override
					protected void onError(AjaxRequestTarget target,
							Form<?> form) {
						
					}

				});
				item.add(f);
			}
		};
		add(eachRow);
		WebMarkupContainer container = new WebMarkupContainer("container");
		add(container);
		totalLabel = new Label("total",
				new PropertyModel<String>(this, "total"));
		totalLabel.setOutputMarkupId(true);
		container.add(totalLabel);
	}

	public String getTotal() {
		Integer v1 = values.get(0);
		Integer v2 = values.get(1);
		Integer v3 = values.get(2);
		return Integer.toString(v1 + v2 + v3);
	}
}
