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

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.ChangeResistantMockFactory;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

@Test
public class ProductListingPageTest {
	private ChangeResistantMockFactory mockFactory = new ChangeResistantMockFactory(
			this);

	public abstract class MockService implements ProductService {
		public List<Product> getAll() {
			List<Product> products = new ArrayList<Product>();
			products.add(new Product("ball pen"));
			products.add(new Product("eraser"));
			return products;
		}
	}

	public void testListing() {
		ProductService mock = mockFactory
				.implementAbstractMethods(MockService.class);
		MockableSpringBeanInjector.mockBean("productService", mock);
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(ProductListingPage.class);
		assert ws.getText("//eachProduct[0]//name").equals(
				"ball pen");
		assert ws.getText("//eachProduct[1]//name").equals(
				"eraser");
	}

}
