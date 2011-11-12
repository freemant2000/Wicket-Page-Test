/**
 * Copyright (C) 2009 Andy Chu<andychu@gmail.com>
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

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.IClusterable;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.apache.wicket.proxy.LazyInitProxyFactory;

/**
 * In order to put a mock object into a field of your page, you need to make it
 * serializable. To do that, use this factory to create a serializable proxy for
 * your mock object (usually non-serializable).
 * 
 * The proxy created can be saved, but actually it will lose the target object
 * after it is loaded. However, this is sufficient for unit testing Wicket pages
 * as only a single page is activated and used.
 * 
 * @author Kent Tong
 * 
 */
public class SerializableProxyFactory implements IClusterable {

	private static final long serialVersionUID = 1L;

	/**
	 * The originals are generally not serializable, so make sure this field is
	 * marked as transient.
	 */
	transient private Map<Object, Object> proxyToOriginal;

	private class TargetLocatorUsingProxy implements IProxyTargetLocator {
		private static final long serialVersionUID = 1L;

		private Object resultedProxy;

		public Object locateProxyTarget() {
			return proxyToOriginal.get(resultedProxy);
		}

	}

	public SerializableProxyFactory() {
		proxyToOriginal = new HashMap<Object, Object>();
	}

	public <T> T createProxy(Class<T> iface, T original) {
		TargetLocatorUsingProxy locator = new TargetLocatorUsingProxy();
		@SuppressWarnings("unchecked")
		T proxy = (T) LazyInitProxyFactory.createProxy(iface, locator);
		locator.resultedProxy = proxy;
		proxyToOriginal.put(proxy, original);
		return proxy;
	}
}
