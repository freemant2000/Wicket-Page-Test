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

import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.ttdev.wicketpagetest.MockableGuiceBeanInjector;
import com.ttdev.wicketpagetest.PageMarkingListener;

public class MyApp extends WebApplication {
	private String mockedUser;

	@Override
	public Class<PageContainingForm> getHomePage() {
		return PageContainingForm.class;
	}

	@Override
	protected void init() {
		super.init();
		// Starting with wpt 2.0, you don't need and must NOT install
		// the Guice component injector.
		MockableGuiceBeanInjector.installInjector(this,
				new GuiceComponentInjector(this, new MyModule()));
		getRequestCycleListeners().add(new PageMarkingListener());
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new MySession(request);
	}

	public String getMockedUser() {
		return mockedUser;
	}

	public void setMockedUser(String mockedUser) {
		this.mockedUser = mockedUser;
	}
}
