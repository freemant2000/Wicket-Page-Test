package com.ttdev.wicketpagetest;

import javax.servlet.http.Cookie;

import org.apache.wicket.core.request.handler.BookmarkablePageRequestHandler;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This listener is used to "mark" the next response page by setting a cookie.
 * The purpose is to let the client side (the test case) to set a random marker
 * and then wait for a response with such a marker, so that it can be sure that
 * the page has been loaded.
 * 
 * @author Kent Tong
 */
public class PageMarkingListener extends AbstractRequestCycleListener {
	public static final String WPT_PAGE_MARKER_COOKIE_NAME = "wptPageMarker";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PageMarkingListener.class);
	private String marker;

	@Override
	public void onRequestHandlerScheduled(RequestCycle cycle,
			IRequestHandler handler) {
		LOGGER.debug("Called");
		if (handler instanceof RenderPageRequestHandler
				|| handler instanceof BookmarkablePageRequestHandler) {
			if (marker != null) {
				LOGGER.debug("Setting page marker cookie to {} for {}", marker,
						getPageClassName(handler));
				WebResponse res = (WebResponse) (RequestCycle.get()
						.getResponse());
				Cookie cookie = new Cookie(WPT_PAGE_MARKER_COOKIE_NAME, marker);
				cookie.setPath("/app");
				res.addCookie(cookie);
			}
		}
	}

	private String getPageClassName(IRequestHandler handler) {
		if (handler instanceof RenderPageRequestHandler) {
			return ((RenderPageRequestHandler) handler).getPage().getClass()
					.getSimpleName();
		}
		if (handler instanceof BookmarkablePageRequestHandler) {
			return ((BookmarkablePageRequestHandler) handler).getPageClass()
					.getSimpleName();
		}
		return null;
	}

	/**
	 * @param marker
	 *            the marker to be used
	 */
	public void setMarker(String marker) {
		this.marker = marker;
	}
}
