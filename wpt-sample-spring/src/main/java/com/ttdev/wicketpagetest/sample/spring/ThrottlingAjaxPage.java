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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;

public class ThrottlingAjaxPage extends WebPage {
	private static final long serialVersionUID = 1L;
	private String input = "";
	private Label outputLabel;

	public ThrottlingAjaxPage() {
		TextField<String> inputField = new TextField<String>("input",
				new PropertyModel<String>(this, "input"));
		add(inputField);
		AjaxFormComponentUpdatingBehavior onkeyupBehavior = new AjaxFormComponentUpdatingBehavior(
				"onkeyup") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(outputLabel);
			}
		};
		inputField.add(onkeyupBehavior);
		outputLabel = new Label("output", new AbstractReadOnlyModel<Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Integer getObject() {
				return input.length();
			}
		});
		add(outputLabel);
		outputLabel.setOutputMarkupId(true);
	}
}
