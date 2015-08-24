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

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class PageRefreshingTR extends WebPage {
	private static final long serialVersionUID = 1L;
	private List<Integer> values;

	public PageRefreshingTR() {
		values = Arrays.asList(new Integer[] { 0, 0, 0 });
		ListView<Integer> eachRow = new ListView<Integer>("eachRow", values) {
 
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Integer> item) {
				item.setOutputMarkupId(true);
				item.add(new Label("count",
						new AbstractReadOnlyModel<Integer>() {

							private static final long serialVersionUID = 1L;

							@Override
							public Integer getObject() {
								return getRowCount(item);
							}

						}));
				item.add(new Label("sum", new AbstractReadOnlyModel<Integer>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Integer getObject() {
						Integer c = getRowCount(item);
						int sum = 0;
						for (int i = 0; i <= c; i++) {
							sum += i;
						}
						return sum;
					}
				}));
				item.add(new AjaxLink<Void>("inc") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						values.set(item.getIndex(), getRowCount(item) + 1);
						target.add(item);
					}
				});
			}
		};
		add(eachRow);
	}

	private Integer getRowCount(ListItem<Integer> item) {
		int i = item.getIndex();
		return values.get(i);
	}

}
