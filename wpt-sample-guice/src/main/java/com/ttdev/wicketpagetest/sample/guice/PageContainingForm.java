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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import com.google.inject.Inject;

public class PageContainingForm extends WebPage {
	@Inject
	private MyService service;
	private String input;
	private String result;

	public PageContainingForm() {
		input = service.getDefaultInput();
		Form<PageContainingForm> form = new Form<PageContainingForm>("form",
				new CompoundPropertyModel<PageContainingForm>(this)) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				result = service.getResult(input);
			}
		};
		add(form);
		form.add(new TextField<String>("input"));
		add(new Label("result", new PropertyModel<String>(this, "result")));
	}
}
