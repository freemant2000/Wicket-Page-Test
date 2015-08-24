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
import org.apache.wicket.protocol.http.WebApplication;

import com.ttdev.wicketpagetest.MockableBeanInjector;
import com.ttdev.wicketpagetest.PageMarkingListener;

public class MyApp extends WebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return GreetingPage.class;
	}

	@Override
	protected void init() {
		super.init();
		MockableBeanInjector.installInjector(this, new MockableBeanInjector());
		getRequestCycleListeners().add(new PageMarkingListener());
	}

}
