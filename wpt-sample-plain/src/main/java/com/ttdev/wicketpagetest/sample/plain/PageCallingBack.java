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
package com.ttdev.wicketpagetest.sample.plain;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

public abstract class PageCallingBack extends WebPage {
	private static final long serialVersionUID = 1L;
	private String input;

	public PageCallingBack() {
		Form<PageCallingBack> form = new Form<PageCallingBack>("form",
				new CompoundPropertyModel<PageCallingBack>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				onInputAvailable(input);
			}
		};
		add(form);
		form.add(new TextField<String>("input"));
	}

	abstract protected void onInputAvailable(String result);
}
