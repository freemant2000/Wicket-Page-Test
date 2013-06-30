package com.ttdev.wicketpagetest;

import org.apache.wicket.Component;
import org.apache.wicket.util.io.IClusterable;

/**
 * It is used by the {@link ComponentTestPage} to create the target component in the
 * request-handling thread. You should not use this interface nor the
 * {@link ComponentTestPage} directly. Instead, use
 * {@link WicketSelenium#openComponent(ComponentFactory)}.
 * 
 * @author Andy Chu
 * 
 */
public interface ComponentFactory extends IClusterable {
	Component createComponent(String id);
}
