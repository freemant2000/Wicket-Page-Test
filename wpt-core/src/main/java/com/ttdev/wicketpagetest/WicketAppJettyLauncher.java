/**
 * Copyright (C) 2009 Kent Tong <freemant2000@yahoo.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * Free Software Foundation version 3.
 *
 * program is distributed in the hope that it will be useful,
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ttdev.wicketpagetest;

import org.apache.wicket.ThreadContext;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.eclipse.jetty.servlet.FilterHolder;

/**
 * This class allows you to launch Jetty to run the specified Wicket app.
 * 
 * @author Kent Tong
 * 
 */
public class WicketAppJettyLauncher extends WebAppJettyLauncher {

	public void startAppInJetty(WebAppJettyConfiguration cfg) {
		super.startAppInJetty(cfg);
		// So that we can access the app and the injector
		waitForApplication();
		// Need to use the wicketpath attribute instead of wicket:id if it
		// is refreshed by AJAX (see WICKET-2832)
		enableOutputWicketPath();
	}

	private void enableOutputWicketPath() {
		getApplication().getDebugSettings().setOutputComponentPath(true);
	}

	private void waitForApplication() {
		for (;;) {
			WebApplication app = getApplication();
			if (app != null) {
				ThreadContext.setApplication(app);
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public WebApplication getApplication() {
		return (WebApplication) PropertyResolver.getValue("application",
				getWicketFilter());
	}

	private WicketFilter getWicketFilter() {
		for (FilterHolder h : getFilters()) {
			if (h.getHeldClass().equals(WicketFilter.class)) {
				return (WicketFilter) h.getFilter();
			}
		}
		throw new RuntimeException("WicketFilter not found");
	}

}
