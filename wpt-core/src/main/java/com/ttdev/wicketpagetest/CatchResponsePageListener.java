package com.ttdev.wicketpagetest;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.RenderPageRequestHandler;

/**
 * If you are unit testing a page that renders another page as the response, in
 * order to check if the first page is passing the right info to the second
 * page, you can use an instance of this class to catch the response page. It
 * will store the response page (or more preciously, the {@link IRequestHandler}
 * as it may not be a page but binary download) into its field for you to
 * inspect later, then it will schedule a dummy page for rendering.
 * <p>
 * To avoid looping, an instance is meant to catch the response page only once.
 * To avoid catching the wrong thing, you should specify the class of the page
 * you're expecting. If you're expecting something more special, you can
 * override the {@link #isWaitingFor()} (for something not a page) or
 * {@link #isWaitingForPage()} (for a page but not defined by page class).
 * <p>
 * See {@link IRequestCycleListener} to see how to install the listener.
 * 
 * @author Kent Tong
 * 
 */
public class CatchResponsePageListener extends AbstractRequestCycleListener {
	private IRequestHandler handlerCaught;
	private Class<? extends IRequestablePage> pageClassExpected;

	/**
	 * Create a listener to wait for a page render handler for a page instance
	 * belong to a specified class.
	 * 
	 * @param pageClassExpected
	 *            the page instance is expected to this class
	 */
	public CatchResponsePageListener(
			Class<? extends IRequestablePage> pageClassExpected) {
		this.pageClassExpected = pageClassExpected;
	}

	/**
	 * Store the handler into a field if one hasn't been stored before and if
	 * that handler is what you're waiting for.
	 */
	@Override
	public void onRequestHandlerScheduled(RequestCycle cycle,
			IRequestHandler handler) {
		if (handlerCaught != null) {
			return; // used only once
		}
		if (isWaitingFor(handler)) {
			this.handlerCaught = handler;
			cycle.setResponsePage(DummyResponsePage.class);
		}
	}

	public void setPageClassExpected(
			Class<? extends IRequestablePage> pageClassExpected) {
		this.pageClassExpected = pageClassExpected;
	}

	/**
	 * Check if the handler is what you're waiting for. If so, it will not do
	 * anything more.
	 * 
	 * @param handler
	 *            the handler being checked
	 */
	protected boolean isWaitingFor(IRequestHandler handler) {
		return pageClassExpected != null && isWaitingForPage(handler);
	}

	/**
	 * Check if the handler is a page render handler belonging to the specified
	 * page class.
	 * 
	 * @param handler
	 *            the handler being checked
	 */
	protected boolean isWaitingForPage(IRequestHandler handler) {
		IRequestablePage page = getPageToRender(handler);
		return page != null && pageClassExpected.isInstance(page);
	}

	public IRequestHandler getHandlerCaught() {
		return handlerCaught;
	}

	/**
	 * It assumes that the request handler caught is to render a page instance
	 * and returns that page instance.
	 * 
	 * @return the page instance (null if it is not a page render handler)
	 */
	public IRequestablePage getPageCaught() {
		return getPageToRender(handlerCaught);
	}

	private IRequestablePage getPageToRender(IRequestHandler handler) {
		try {
			RenderPageRequestHandler pageRenderHandler = (RenderPageRequestHandler) handler;
			return pageRenderHandler.getPage();
		} catch (Exception e) {
			return null;
		}
	}
}
