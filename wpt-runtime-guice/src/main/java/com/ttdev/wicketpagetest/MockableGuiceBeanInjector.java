/**
 * Copyright (C) 2009 Andy Chu<andychu@gmail.com>
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

package com.ttdev.wicketpagetest;

import org.apache.wicket.Application;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.Inject;


/**
 * Use this class to install an injector that can inject both mocked objects and
 * Guice beans (tried in this order). This is usually done in
 * {@link Application#init()}. Then you can provide mocked objects by calling
 * {@link #mockBean(String, Object)} usually in your unit tests.
 * 
 * @author Andy Chu
 * @author Kent Tong
 * 
 */
public class MockableGuiceBeanInjector extends MockableBeanInjector {

	public MockableGuiceBeanInjector(GuiceComponentInjector guiceInjector) {
		super(Inject.class, guiceInjector);
	}

	public static void installInjector(WebApplication webapp,
			GuiceComponentInjector guiceInjector) {
		MockableBeanInjector.installInjector(webapp,
				new MockableGuiceBeanInjector(guiceInjector));
	}
}
