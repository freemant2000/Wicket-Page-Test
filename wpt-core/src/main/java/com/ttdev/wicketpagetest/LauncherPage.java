package com.ttdev.wicketpagetest;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;

/**
 * In order to open a page whose constructor takes arguments, the page must be
 * somehow created on the server side (or rather, in a thread that is associated
 * with a request). It means that you can't just create the page instance
 * directly in your test case.
 * 
 * To solve this problem, we will open this launcher page which will create the
 * target page through a {@link PageFactory} callback and then open it. As the
 * thread is running in the context of a request, it can freely create the
 * target page.
 * 
 * @author Kent Tong
 * 
 */
public class LauncherPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public final static String PAGE_FACTORY_FIELD_NAME = "pageFactory";

	@Mock
	private PageFactory pageFactory;

	public LauncherPage() {
		throw new RestartResponseException(pageFactory.createPage());
	}
}
