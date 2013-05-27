package com.ttdev.wicketpagetest;

import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	private static final int AJAX_TIMEOUT_IN_SECONDS = 3;
	private static final long AJAX_CHECK_INTERVAL_IN_MILLI_SECONDS = 100L;
	private WebDriver selenium;
	private Configuration cfg;
	private String ajaxDoneExpr;

	public WicketSelenium(Configuration cfg, WebDriver selenium) {
		this.selenium = selenium;
		this.cfg = cfg;
		initAjaxDoneExpr();
	}

	private void initAjaxDoneExpr() {
		String defineWicketAjaxBusy = String.format(
				"wicketAjaxBusy = function () {"
						+ "for (var channelName in %1$s) {"
						+ "if (%1$s[channelName].busy) { return true; }" + "}"
						+ "return false;};", "Wicket.channelManager.channels");
		String defineWicketThrottlingInProgress = String
				.format("wicketThrottlingInProgress = function () {"
						+ "for (var property in %1$s) {"
						+ "if (property.match(/th[0-9]+/) && %1$s[property] != undefined) { return true; }"
						+ "}" + "return false;};", "Wicket.throttler.entries");
		String defineWicketAjaxComplete = "Wicket.Event.subscribe('/ajax/call/complete', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {})";
		ajaxDoneExpr = defineWicketAjaxBusy + defineWicketThrottlingInProgress
				+ "return !wicketAjaxBusy() && !wicketThrottlingInProgress();";

	}

	public void subscribeAjaxDoneHandler() {
		JavascriptExecutor jsExec = (JavascriptExecutor) selenium;
		String defineAjaxDoneIndicatorExpr = "if (typeof wicketPageTestAjaxDone === 'undefined') { var wicketPageTestAjaxDone = false;" +
				"Wicket.Event.subscribe('/ajax/call/complete', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) { window.wicketPageTestAjaxDone = true; });"+
				" }";
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
		new WebDriverWait(selenium, AJAX_TIMEOUT_IN_SECONDS,
				AJAX_CHECK_INTERVAL_IN_MILLI_SECONDS)
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
	 * Open a bookmarkable Wicket page. That is, it has a no-arg constructor.
	 * 
	 * @param pageClass
	 *            the class of the page
	 */
	public void openBookmarkablePage(Class<? extends Page> pageClass) {
		selenium.get(getWicketAppBase()
				+ String.format("wicket/bookmarkable/%s", pageClass.getName()));

	}

	private String getWicketAppBase() {
		return String.format("http://localhost:%d/%s",
				cfg.getJettyServerPort(), cfg.getWicketFilterPrefix() + "/");
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
		return findElement(new ByWicketIdPath(path));
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
