package com.ttdev.wicketpagetest;

import org.apache.wicket.Component;
import org.apache.wicket.Page;

/**
 * The default implementation of {@link PageFlowNavigator} which will simply
 * call {@link Component#setResponsePage(Page)}. As it is most likely used as a
 * injected bean, you need to define such a Spring/Guice bean for your
 * application if you do use it.
 * 
 * @deprecated Please use CatchResponsePageListener instead.
 * 
 * @author Kent Tong
 * 
 */
public class DefaultPageFlowNavigator implements PageFlowNavigator {

	public void setResponsePage(Component from, Page to) {
		from.setResponsePage(to);
	}

}
