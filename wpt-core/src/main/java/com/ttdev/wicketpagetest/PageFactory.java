package com.ttdev.wicketpagetest;

import org.apache.wicket.Page;

/**
 * It is used by the {@link LauncherPage} to create the target page in the
 * request-handling thread. You should not use this interface nor the
 * {@link LauncherPage} directly. Instead, use
 * {@link WicketSelenium#openNonBookmarkablePage(Class, Object...)}.
 * 
 * @author Kent Tong
 * 
 */
public interface PageFactory {

	Page createPage();

}
