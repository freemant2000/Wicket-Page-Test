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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import com.google.inject.Inject;

public class AjaxPage extends WebPage {
	@Inject
	private CalcService service;
	private int current = 0;
	private Label currentLabel;

	public AjaxPage() {
		AjaxLink<Void> calcNext = new AjaxLink<Void>("calcNext") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				current = service.calcNext(current);
				target.addComponent(currentLabel);
			}
		};
		add(calcNext);
		currentLabel = new Label("current", new PropertyModel<Integer>(this,
				"current"));
		add(currentLabel);
		currentLabel.setOutputMarkupId(true);
	}
}
