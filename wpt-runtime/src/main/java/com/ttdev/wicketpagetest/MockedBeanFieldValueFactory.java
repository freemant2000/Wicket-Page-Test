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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.IClusterable;
import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.apache.wicket.proxy.LazyInitProxyFactory;

/**
 * An {@link IFieldValueFactory} that provides mocked objects to fields
 * annotated by {@link #annotClass} or by {@link Mock}.
 * 
 * @author Andy Chu
 * @author Kent Tong
 * 
 */
public class MockedBeanFieldValueFactory implements IFieldValueFactory,
		IClusterable {

	private static final long serialVersionUID = 1L;

	private Class<? extends Annotation> annotClass;

	transient private Map<String, Object> mockedBeans;

	/**
	 * @param annotClass
	 *            annotation class for fields to be injected
	 */
	public MockedBeanFieldValueFactory(Class<? extends Annotation> annotClass) {
		this.annotClass = annotClass;
		mockedBeans = new HashMap<String, Object>();
	}

	public void mockBean(String fieldName, Object mockedBean) {
		mockedBeans.put(fieldName, mockedBean);
	}

	public Object getFieldValue(Field field, Object fieldOwner) {
		final String fieldName = field.getName();
		if (!hasValueForField(fieldName)) {
			return null;
		}
		return LazyInitProxyFactory.createProxy(field.getType(),
				new IProxyTargetLocator() {

					private static final long serialVersionUID = 1L;

					public Object locateProxyTarget() {
						return mockedBeans.get(fieldName);
					}
				});
	}

	private boolean isFieldMocked(String fieldName) {
		// Could the map become null in a cluster environment? Better be safe
		// than sorry.
		return mockedBeans != null && mockedBeans.containsKey(fieldName);
	}

	public boolean supportsField(Field field) {
		// In order for the cache in the Injector object to be independent of
		// the mocks, this method returns true as long as the field is
		// annotated.
		return isAnnotated(field);
	}

	private boolean isAnnotated(Field field) {
		return field.isAnnotationPresent(Mock.class)
				|| field.isAnnotationPresent(annotClass);
	}

	public void clearMockedBeans() {
		mockedBeans.clear();
	}

	public boolean hasValueForField(String fieldName) {
		return isFieldMocked(fieldName);
	}
}
