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

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.ttdev.wicketpagetest.Configuration;
import com.ttdev.wicketpagetest.WebPageTestContext;

public class TestFixture {
	@BeforeTest
	public void setUp() throws Exception {
		Configuration cfg = new Configuration();
		cfg.setOverrideWebXml("web-test.xml");
		WebPageTestContext.beforePageTests(cfg);

	}

	@AfterTest
	public void tearDown() throws Exception {
		WebPageTestContext.afterPageTests();
	}
}
