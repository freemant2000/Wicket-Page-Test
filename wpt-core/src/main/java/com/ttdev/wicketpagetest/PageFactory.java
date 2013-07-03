package com.ttdev.wicketpagetest;

import org.apache.wicket.Page;
import org.apache.wicket.util.io.IClusterable;

/**
 * It is used by the {@link LauncherPage} to create the target page in the
 * request-handling thread. You should not use this interface nor the
 * {@link LauncherPage} directly. Instead, use
 * {@link WicketSelenium#openNonBookmarkablePage(Class, Object...)}.
 * 
 * @author Kent Tong
 * 
 */
public interface PageFactory extends IClusterable {

	Page createPage();

}
