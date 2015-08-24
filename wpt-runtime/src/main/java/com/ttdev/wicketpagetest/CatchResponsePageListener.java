package com.ttdev.wicketpagetest;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * override the {@link #isWaitingFor(IRequestHandler)} (for something not a
 * page) or {@link #isWaitingForPage(IRequestHandler)} (for a page but not
 * defined by page class).
 * <p>
 * See {@link IRequestCycleListener} to see how to install the listener. Note
 * that the listener list in the application is implemented by a copy-on-write
 * array list. As a result, if you try to add the listener in your testing
 * thread, the change will not be seen on the server side.
 * 
 * @author Kent Tong
 * 
 */
public class CatchResponsePageListener extends AbstractRequestCycleListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CatchResponsePageListener.class);
	private IRequestHandler handlerCaught;
	private Class<? extends IRequestablePage> pageClassExpected;

	/**
	 * Create a listener. You need to call {@link #setPageClassExpected(Class)}
	 * in your testing thread.
	 */
	public CatchResponsePageListener() {

	}

	/**
	 * Store the handler into a field if one hasn't been stored before and if
	 * that handler is what you're waiting for.
	 */
	@Override
	public void onRequestHandlerScheduled(RequestCycle cycle,
			IRequestHandler handler) {
		LOGGER.debug("Called");
		if (handlerCaught != null) {
			LOGGER.debug("Already caught");
			return; // used only once
		}
		if (pageClassExpected == null) {
			LOGGER.debug("Nothing is being expected");
			return;
		}
		if (isWaitingFor(handler)) {
			LOGGER.debug("Found handler");
			this.handlerCaught = handler;
			cycle.setResponsePage(DummyResponsePage.class);
		} else {
			LOGGER.debug("Handler not matched");
		}
	}

	/**
	 * Start wait for a page render handler for a page instance belong to a
	 * specified class.
	 * 
	 * @param pageClassExpected
	 *            the page instance is expected to this class
	 */
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
	 * @return true if the handler is what you're waiting for.
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
	 * @return true if the handler is a page render handler belonging to the
	 *         specified page class.
	 */
	protected boolean isWaitingForPage(IRequestHandler handler) {
		IRequestablePage page = getPageToRender(handler);
		LOGGER.debug("Got a page: {}", page.getClass().getSimpleName());
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
