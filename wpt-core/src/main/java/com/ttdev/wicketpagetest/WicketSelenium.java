package com.ttdev.wicketpagetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.thoughtworks.selenium.Selenium;

/**
 * This class is a wrapper around {@link Selenium} that adds some handy
 * functions for Wicket-generated HTML pages. In particular, it allows you to
 * wait for Ajax processing to be completed. This way, you don't need to use
 * {@link WebDriverWait} which is more complicated and error-prone.
 * 
 * @author Kent Tong
 * @author Andy Chu
 */
public class WicketSelenium {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WicketSelenium.class);
	protected static final int WAIT_TIMEOUT_IN_SECONDS = 10;
	private WebDriver selenium;
	private Configuration cfg;
	private String pageMarker;
	private Random randMarkerGenerator;

	public WicketSelenium(Configuration cfg, WebDriver selenium) {
		this.selenium = selenium;
		this.cfg = cfg;
		randMarkerGenerator = new Random();
		configToWaitOnNotFound();
	}

	// By default Wicket uses redirects for page navigation, but the
	// Selenium's click() method doesn't wait for redirects. So,
	// tell Selenium to wait some seconds if an element is not found.
	// This will work if the response page is a different page so
	// only it has that element.
	private void configToWaitOnNotFound() {
		selenium.manage().timeouts()
				.implicitlyWait(WAIT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
		LOGGER.debug("Setting the implicit wait timeout to {}",
				WAIT_TIMEOUT_IN_SECONDS);
	}

	public void subscribeAjaxDoneHandler() {
		JavascriptExecutor jsExec = (JavascriptExecutor) selenium;
		String defineAjaxDoneIndicatorExpr = "if (typeof wicketPageTestAjaxDone === 'undefined') { var wicketPageTestAjaxDone = false;"
				+ "Wicket.Event.subscribe('/ajax/call/complete', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) { window.wicketPageTestAjaxDone = true; });"
				+ " }";
		jsExec.executeScript(defineAjaxDoneIndicatorExpr);
	}

	private void clearAjaxDoneIndicator() {
		JavascriptExecutor jsExec = (JavascriptExecutor) selenium;
		jsExec.executeScript("window.wicketPageTestAjaxDone = false;");
	}

	/**
	 * It waits until the Wicket Ajax processing has completed. It does that by
	 * waiting until all the Wicket Ajax channels to become idle.
	 * 
	 * This method heavily depends on the Wicket internal Ajax implementation
	 * and thus is subject to changes.
	 */
	public void waitUntilAjaxDone() {
		new WebDriverWait(selenium, WAIT_TIMEOUT_IN_SECONDS)
				.until(new Predicate<WebDriver>() {
					public boolean apply(WebDriver input) {
						JavascriptExecutor jsExec = (JavascriptExecutor) input;
						Boolean ajaxDone = (Boolean) jsExec
								.executeScript("return window.wicketPageTestAjaxDone == true;");
						return ajaxDone;
					}
				});
		clearAjaxDoneIndicator();
	}

	/**
	 * It tells the server side to include a random string as the marker (a
	 * cookie) in the response page. See {@link #setResponsePageMarker(String)}.
	 */
	public void setResponsePageMarker() {
		setResponsePageMarker(Long.toString(randMarkerGenerator.nextLong()));
	}

	/**
	 * It tells the server side to include a page marker as a cookie in the next
	 * response page, so that you can then call {@link #waitForMarkedPage()} to
	 * wait for it. This way you can be sure that it is a new page and won't
	 * suffer from the StaleElementReferenceException.
	 * 
	 * @param marker
	 *            the marker to be used
	 */
	public void setResponsePageMarker(String marker) {
		pageMarker = marker;
		PageMarkingListener pml = findPageMarkingListener();
		LOGGER.debug("Setting page marker to {}", pageMarker);
		pml.setMarker(pageMarker);
	}

	private PageMarkingListener findPageMarkingListener() {
		for (IRequestCycleListener l : Application.get()
				.getRequestCycleListeners()) {
			if (l instanceof PageMarkingListener) {
				PageMarkingListener pml = (PageMarkingListener) l;
				return pml;
			}
		}
		throw new RuntimeException(
				PageMarkingListener.class.getSimpleName()
						+ " not found. Have you installed it in your Wicket application?");
	}

	/**
	 * It waits until the browser sees a page with the page marker. Usually you
	 * don't need to use this method because when you try to get an element and
	 * if it is not found, Selenium has been configured to wait a few seconds.
	 * But if the response page is the same as the original page, then it won't
	 * work as that element will be found but when you try to use it, you will
	 * get an StaleElementReferenceException. To work around the problem, call
	 * {@link #setResponsePageMarker(String)} and then call this method.
	 */
	public void waitForMarkedPage() {
		if (pageMarker == null) {
			throw new RuntimeException(
					"Must call setPageMarker() before calling this method");
		}
		new WebDriverWait(selenium, WAIT_TIMEOUT_IN_SECONDS)
				.until(new Predicate<WebDriver>() {
					public boolean apply(WebDriver input) {
						Cookie marker = input
								.manage()
								.getCookieNamed(
										PageMarkingListener.WPT_PAGE_MARKER_COOKIE_NAME);
						if (marker != null) {
							LOGGER.debug("Marker retrieved is {}",
									marker.getValue());
							if (marker.getValue().equals(pageMarker)) {
								LOGGER.debug("Marker matched. Clearing it.");
								pageMarker = null;
								return true;
							} else {
								LOGGER.debug("Marker not matched");
								return false;
							}
						} else {
							LOGGER.debug("No marker found");
							return false;
						}
					}
				});
	}

	/**
	 * Open a bookmarkable Wicket page. That is, it has a no-arg constructor.
	 * 
	 * @param pageClass
	 *            the class of the page
	 */
	public void openBookmarkablePage(Class<? extends Page> pageClass) {
		selenium.get(getWicketAppBase()
				+ String.format("wicket/bookmarkable/%s", pageClass.getName()));

	}

	/**
	 * Open a mounted Wicket page.
	 * 
	 * @param mountPoint
	 *            the mount point of the page
	 * 
	 * @param parameters
	 *            the page parameters
	 */
	public void openMountedPage(String mountPoint, PageParameters parameters) {
		String appBase = getWicketAppBase();
		String fullQuery = getFullQueryString(parameters);
		String url = fullQuery.isEmpty() ? (appBase + mountPoint) : appBase
				+ mountPoint + "?" + fullQuery;
		selenium.get(url);
	}

	private String getFullQueryString(PageParameters parameters) {
		List<String> queries = new ArrayList<String>();
		Set<String> keys = parameters.getNamedKeys();
		for (String key : keys) {
			queries.add(String.format("%s=%s", key, parameters.get(key)
					.toString()));
		}
		String fullQuery = "";
		if (queries.size() == 1) {
			fullQuery = queries.get(0);
		}
		if (queries.size() > 1) {
			fullQuery = queries.get(0);
			for (int i = 1; i < queries.size(); i++) {
				fullQuery += ("&" + queries.get(i));
			}
		}
		return fullQuery;
	}

	private String getWicketAppBase() {
		return String.format("http://localhost:%d/%s",
				cfg.getJettyServerPort(), cfg.getWicketFilterPrefix().trim()
						.isEmpty() ? "" : (cfg.getWicketFilterPrefix() + "/"));
	}

	/**
	 * Open the home page of the Wicket application.
	 */
	public void openHomePage() {
		selenium.get(getWicketAppBase());
	}

	/**
	 * Open a non-bookmarkable Wicket page. That is, its constructor takes one
	 * or more arguments.
	 * 
	 * @param pageClass
	 *            the class of the page
	 * @param constructorArgs
	 *            the constructor arguments
	 */
	public void openNonBookmarkablePage(Class<? extends Page> pageClass,
			Object... constructorArgs) {
		// use plain mocking possible? unlikely as it is Wicket that creates
		// the instance from the class.
		MockableBeanInjector.mockBean(LauncherPage.PAGE_FACTORY_FIELD_NAME,
				new DefaultPageFactory(pageClass, constructorArgs));
		openBookmarkablePage(LauncherPage.class);
	}

	/**
	 * Open a page containing ONLY the Component created by the specified
	 * ComponentFactory.
	 * 
	 * @param factory
	 *            the ComponentFactory creating the testing component
	 */
	public void openComponent(ComponentFactory factory) {
		openNonBookmarkablePage(ComponentTestPage.class, factory);
	}

	/**
	 * Open a BreadCrumbPanel created by the IBreadCrumbPanelFactory in a page
	 * 
	 * @param factory
	 *            the IBreadCrumbPanelFactory creating the testing panel
	 */
	public void openBreadCrumbPanel(IBreadCrumbPanelFactory factory) {
		openNonBookmarkablePage(BreadCrumbPanelTestPage.class, factory);
	}

	/**
	 * Locate the HTML slot of a Wicket component using the specified path. For
	 * the syntax of the path, please see {@link ByWicketIdPath}.
	 * 
	 * @param path
	 *            The path to the Wicket component
	 * @return The corresponding HTML slot
	 */
	public WebElement findWicketElement(String path) {
		return findElement(new ByWicketIdPathFastVersion(path));
	}

	/**
	 * Get the {@link WebElement} located by the specified locator.
	 * 
	 * @param locator
	 *            locate the element using this locator
	 * @return the element
	 */
	public WebElement findElement(By locator) {
		return selenium.findElement(locator);
	}

	/**
	 * Get the underlying Selenium {@link WebDriver}.
	 * 
	 * @return the Selenium WebDriver
	 */
	public WebDriver getSelenium() {
		return selenium;
	}

	/**
	 * Get the body text of the element located by the specified locator.
	 * 
	 * @param elementLocator
	 *            locate the element using this locator
	 * @return the body text of that element
	 */
	public String getText(By elementLocator) {
		return findElement(elementLocator).getText();
	}

	/**
	 * Get the value of the "value" attribute of the element located by the
	 * specified locator.
	 * 
	 * @param elementLocator
	 *            locate the element using this locator
	 * @return the value of the "value" attribute of that element
	 */
	public String getValue(By elementLocator) {
		return findElement(elementLocator).getAttribute("value");
	}

	/**
	 * Click the element located by the specified locator.
	 * 
	 * @param elementLocator
	 *            locate the element using this locator
	 */
	public void click(By elementLocator) {
		findElement(elementLocator).click();
	}

	/**
	 * Send the keys to the element located by the specified locator.
	 * 
	 * @param elementLocator
	 *            locate the element using this locator
	 * @param keysToSend
	 *            the keys to be sent to that element
	 */
	public void sendKeys(By elementLocator, CharSequence... keysToSend) {
		findElement(elementLocator).sendKeys(keysToSend);
	}

	/**
	 * Clear the value of the text input element located by the specified
	 * locator.
	 * 
	 * @param elementLocator
	 *            locate the element using this locator
	 */
	public void clear(By elementLocator) {
		findElement(elementLocator).clear();
	}

	public void setPreferredLocale(Locale locale) {
		openNonBookmarkablePage(SwitchLocalePage.class, locale);
	}

	public void switchDefaultLocale() {
		openNonBookmarkablePage(SwitchLocalePage.class, Locale.getDefault());
	}
}
