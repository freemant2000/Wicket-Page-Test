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

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.ttdev.wicketpagetest.MockableSpringBeanInjector;

public class MyApp extends WebApplication {
	private String mockedUser;
	
	@Override
	public Class<PageContainingForm> getHomePage() {
		return PageContainingForm.class;
	}

	@Override
	protected void init() {
		super.init();
		MockableSpringBeanInjector.installInjector(this); 
		
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
