package com.ttdev.wicketpagetest;

import org.apache.wicket.Component;
import org.apache.wicket.Page;

/**
 * If you have a page that passes some data to the response page, in order to
 * test the passed data, the original page can use a bean of this class to set
 * the response page, then provide a mock implement to check the passed data.
 * For production, you should probably use the {@link DefaultPageFlowNavigator}
 * as the implementation.
 * 
 * @author Kent Tong
 * 
 */
public interface PageFlowNavigator {

	void setResponsePage(Component from, Page to);

}
