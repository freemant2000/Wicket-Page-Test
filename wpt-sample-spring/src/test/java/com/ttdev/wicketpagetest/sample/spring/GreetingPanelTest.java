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

package com.ttdev.wicketpagetest.sample.spring;

import org.apache.wicket.Component;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.ComponentFactory;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

@Test
public class GreetingPanelTest {

	public void testOpenComponent() {
		MockableSpringBeanInjector.mockBean("gs", new GreetingService() {
			public String getName() {
				return "Peter";
			}
		});
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openComponent(new ComponentFactory() {
			private static final long serialVersionUID = 1L;

			public Component createComponent(String id) {
				return new GreetingPanel(id);
			}
		});
		assert ws.getText("//name").equals("Peter");
	}
}
