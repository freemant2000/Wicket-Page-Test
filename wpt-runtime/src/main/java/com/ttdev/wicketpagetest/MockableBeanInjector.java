package com.ttdev.wicketpagetest;

import java.lang.annotation.Annotation;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Use this class to install an injector that can inject mocked objects before
 * considering the real beans. This is usually done in
 * {@link Application#init()}. Then you can provide mocked objects by calling
 * {@link #mockBean(String, Object)} usually in your unit tests.
 * 
 * @author Kent Tong
 * 
 */
public class MockableBeanInjector extends Injector implements
		IComponentInstantiationListener {
	private MockedBeanFieldValueFactory mockedBeansFactory;
	private Injector originalInjector;

	/**
	 * @param injectionAnnotClass
	 *            the injector will handle fields with this annotation. This is
	 *            usually SpringBean.class or Inject.class.
	 * @param originalInjector
	 *            the original injector to be used in production (e.g., to look
	 *            up Spring beans).
	 */
	public MockableBeanInjector(
			Class<? extends Annotation> injectionAnnotClass,
			Injector originalInjector) {
		this.mockedBeansFactory = new MockedBeanFieldValueFactory(
				injectionAnnotClass);
		this.originalInjector = originalInjector;
	}

	/**
	 * Install the specified MockableBeanInjector into the specified webapp.
	 * 
	 * @param webapp
	 *            the webapp
	 * @param mockableBeanInjector
	 *            the injector to be installed.
	 */
	public static void installInjector(WebApplication webapp,
			MockableBeanInjector mockableBeanInjector) {
		mockableBeanInjector.bind(webapp);
		webapp.getComponentInstantiationListeners().add(mockableBeanInjector);
	}

	private static MockedBeanFieldValueFactory getMockedBeanFactory() {
		MockableBeanInjector injector = (MockableBeanInjector) Injector.get();
		return injector.mockedBeansFactory;
	}

	public static void mockBean(String fieldName, Object mockedBean) {
		getMockedBeanFactory().mockBean(fieldName, mockedBean);
	}

	/**
	 * Remove all mocked objects.
	 */
	public static void clearMockBeans() {
		getMockedBeanFactory().clearMockedBeans();
	}

	@Override
	public void inject(Object object) {
		inject(object, mockedBeansFactory);
		// The injector will inject a bean into the fields that are not null.
		// So, if a mocked object has been injected in the previous step, the
		// injector won't touch it.
		originalInjector.inject(object);

	}

	public void onInstantiation(Component component) {
		inject(component);
	}
}
