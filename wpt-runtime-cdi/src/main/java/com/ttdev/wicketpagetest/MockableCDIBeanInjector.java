package com.ttdev.wicketpagetest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Args;
import org.jboss.weld.environment.servlet.Listener;

public class MockableCDIBeanInjector extends MockableBeanInjector {

	public MockableCDIBeanInjector(
			final IComponentInstantiationListener listener) {
		super(Inject.class, new Injector() {
			@Override
			public void inject(Object component) {
				listener.onInstantiation((Component) component);
			}
		});
	}

	public static void installInjector(final WebApplication webapp) {
		BeanManager manager = (BeanManager) webapp.getServletContext()
				.getAttribute(Listener.BEAN_MANAGER_ATTRIBUTE_NAME);
		new CdiConfiguration(manager).configure(webapp);
		ComponentInstantiationListenerCollection listeners = webapp
				.getComponentInstantiationListeners();
		Iterator<IComponentInstantiationListener> iterator = listeners
				.iterator();
		IComponentInstantiationListener instantiationListener = null;
		while (iterator.hasNext()) {
			instantiationListener = iterator.next();
			if (instantiationListener.getClass().getSimpleName()
					.equals("ComponentInjector")) {
				break;
			}
		}
		Args.notNull(instantiationListener, "instantiationListener");
		listeners.remove(instantiationListener);
		MockableBeanInjector.installInjector(webapp,
				new MockableCDIBeanInjector(instantiationListener));
	}

	@Override
	public void inject(Object object) {
		injectOriginals(object);
		injectMocks(object);
	}
	
	@Override
	protected void inject(Object object, IFieldValueFactory factory) {
		final Class<?> clazz = object.getClass();

		Field[] fields = findFields(clazz, factory);

		for (final Field field : fields) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
				Object value = factory.getFieldValue(field, object);
				if (value != null) {
					field.set(object, value);
				}

			} catch (IllegalArgumentException e) {
				throw new RuntimeException("error while injecting object ["
						+ object.toString() + "] of type ["
						+ object.getClass().getName() + "]", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("error while injecting object ["
						+ object.toString() + "] of type ["
						+ object.getClass().getName() + "]", e);
			}
		}
	}

	private Field[] findFields(Class<?> clazz, final IFieldValueFactory factory) {
		List<Field> matched = new ArrayList<Field>();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (final Field field : fields) {
				if (factory.supportsField(field)) {
					matched.add(field);
				}
			}
			clazz = clazz.getSuperclass();
		}

		return matched.toArray(new Field[matched.size()]);
	}
}
