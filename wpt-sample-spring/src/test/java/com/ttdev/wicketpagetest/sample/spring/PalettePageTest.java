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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

@Test
public class PalettePageTest {
	private Product deleted;

	public void testPalette() {
		MockableSpringBeanInjector.mockBean("ps", new ProductService() {

			public List<Product> getAll() {
				List<Product> products = new ArrayList<Product>();
				products.add(new Product(0L, "ball pen"));
				products.add(new Product(1L, "eraser"));
				products.add(new Product(2L, "paper clip"));
				return products;
			}

			public void delete(Product p) {
				deleted = p;
			}

			public void add(Product p) {
			}
		});
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openBookmarkablePage(PalettePage.class);
		Select leftSelect = new Select(ws.findWicketElement("//choices"));
		List<WebElement> allProducts = leftSelect.getOptions();
		assert allProducts.size() == 3;
		assert allProducts.get(0).getText().equals("ball pen");
		assert allProducts.get(1).getText().equals("eraser");
		assert allProducts.get(2).getText().equals("paper clip");
		leftSelect.selectByVisibleText("eraser");
		ws.click("//addButton");
		leftSelect = new Select(ws.findWicketElement("//choices"));
		allProducts = leftSelect.getOptions();
		assert allProducts.size() == 2;
		assert allProducts.get(0).getText().equals("ball pen");
		assert allProducts.get(1).getText().equals("paper clip");
		Select rightSelect = new Select(ws.findWicketElement("//selection"));
		List<WebElement> selectedProducts = rightSelect.getOptions();
		assert selectedProducts.size() == 1;
		assert selectedProducts.get(0).getText().equals("eraser");
		ws.click(By.xpath("//input[@value='OK']"));
		assert deleted.getId() == 1L;
		assert deleted.getName().equals("eraser");
	}

}
