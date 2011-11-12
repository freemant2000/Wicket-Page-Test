package com.ttdev.wicketpagetest;

import org.apache.wicket.Component;

/**
 * It is used by the {@link ComponentTestPage} to create the target component in the
 * request-handling thread. You should not use this interface nor the
 * {@link ComponentTestPage} directly. Instead, use
 * {@link WicketSelenium#openComponent(ComponentFactory)}.
 * 
 * @author Andy Chu
 * 
 */
public interface ComponentFactory {
	Component createComponent(String id);
}
