package com.ttdev.wicketpagetest;

import org.apache.wicket.Page;

/**
 * The default implementation for {@link PageFactory}.
 * 
 * @author Kent Tong
 * 
 */
public class DefaultPageFactory implements PageFactory {
	private Class<? extends Page> pageClass;
	private Object[] constructorArgs;

	public DefaultPageFactory(Class<? extends Page> pageClass,
			Object... constructorArgs) {
		this.pageClass = pageClass;
		this.constructorArgs = constructorArgs;
	}

	public Page createPage() {
		return new ObjectFactory().create(pageClass, constructorArgs);
	}

}
