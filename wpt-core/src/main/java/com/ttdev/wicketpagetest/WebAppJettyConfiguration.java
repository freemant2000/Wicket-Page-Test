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

/**
 * This class allows you to specify options to control the behavior of
 * {@link WebAppJettyLauncher}. For example, by default it assumes that your web
 * content folder is src/main/webapp. If it is not the case, you must set the
 * {@link #docBase} property.
 * 
 * @author Kent Tong
 * 
 */
public class WebAppJettyConfiguration {
	/**
	 * The path to the root of your html files (default: src/main/webapp).
	 */
	private String docBase = "src/main/webapp";
	/**
	 * The port of the Jetty server (default: 8888).
	 */
	private int jettyServerPort = 8888;
	/**
	 * You can provide an overriding web.xml file by putting a web-test.xml file
	 * into the classpath and then specifying "web-test.xml" here. This is
	 * useful, e.g., to override contextConfigLocation so that Spring loads the
	 * additional beans for unit testing web pages.
	 */
	private String overrideWebXml = null;

	public void setOverrideWebXml(String overrideWebXml) {
		this.overrideWebXml = overrideWebXml;
	}

	public String getDocBase() {
		return docBase;
	}

	public void setDocBase(String docBase) {
		this.docBase = docBase;
	}

	public int getJettyServerPort() {
		return jettyServerPort;
	}

	public void setJettyServerPort(int jettyServerPort) {
		this.jettyServerPort = jettyServerPort;
	}

	public String getOverrideWebXml() {
		return overrideWebXml;
	}
}
