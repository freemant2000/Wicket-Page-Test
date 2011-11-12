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

import com.google.inject.AbstractModule;
import com.ttdev.wicketpagetest.BreadCrumbNavigator;
import com.ttdev.wicketpagetest.DefaultBreadCrumbNavigator;
import com.ttdev.wicketpagetest.DefaultPageFlowNavigator;
import com.ttdev.wicketpagetest.PageFlowNavigator;

public class MyModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MyService.class).to(DefaultMyService.class);
		bind(GreetingService.class).to(DefaultGreetingService.class);
		// NOTE: there should be a "place holder" implementation to avoid an
		// exception
		// thrown from Guice if the default implementation is not available
		bind(LengthyProcessingService.class).toInstance(
				new LengthyProcessingService() {
					public void start() {
					}

					public String waitForResult() {
						return "abc";
					}
				});
		bind(CalcService.class).to(DefaultCalcService.class);
		bind(PageFlowNavigator.class).to(DefaultPageFlowNavigator.class);
		bind(BreadCrumbNavigator.class).to(DefaultBreadCrumbNavigator.class);
	}
}
