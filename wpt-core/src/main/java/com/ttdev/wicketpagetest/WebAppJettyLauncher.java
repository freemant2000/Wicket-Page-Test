package com.ttdev.wicketpagetest;

import javax.servlet.ServletContext;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * This class allows you to launch Jetty to run the specified webapp.
 * 
 * @see WebAppJettyConfiguration
 * 
 * @author Kent Tong
 * 
 */
public class WebAppJettyLauncher {
	private WebAppContext context;
	private Server server;

	public void startAppInJetty(WebAppJettyConfiguration cfg) {
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(cfg.getJettyServerPort());
		server.addConnector(connector);
		context = new WebAppContext();
		context.setContextPath("/");
		context.setWar(cfg.getDocBase());
		context.setOverrideDescriptor(cfg.getOverrideWebXml());
		server.setHandler(context);
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stopJetty() {
		try {
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ServletContext getServletContext() {
		return context.getServletHandler().getServletContext();
	}

	public FilterHolder[] getFilters() {
		return context.getServletHandler().getFilters();
	}
}
