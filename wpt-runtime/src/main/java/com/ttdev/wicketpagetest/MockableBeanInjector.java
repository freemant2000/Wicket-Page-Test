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
	 * Create an instance that can inject into fields with the {@link Mock}
	 * annotation and that has no chained injector. You should use this
	 * constructor if you aren't using Spring nor Guice.
	 */
	public MockableBeanInjector() {
		this(Mock.class, null);
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

	/**
	 * Add a mocked object for the specified field into the instance of this
	 * class which has been installed as the injector for the application. When
	 * a page containing such a named field with the annotation, the mocked
	 * object will be injected into there.
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @param mockedBean
	 *            the mocked object
	 */
	public static void mockBean(String fieldName, Object mockedBean) {
		getMockedBeanFactory().mockBean(fieldName, mockedBean);
	}

	/**
	 * Remove all mocked objects.
	 */
	public static void clearMockBeans() {
		getMockedBeanFactory().clearMockedBeans();
	}

	/**
	 * Called by Wicket to inject field values into the object. Here it will
	 * consider the mocked objects first before calling the original injector.
	 */
	@Override
	public void inject(Object object) {
		inject(object, mockedBeansFactory);
		// The injector will inject a bean into the fields that are not null.
		// So, if a mocked object has been injected in the previous step, the
		// injector won't touch it.
		if (originalInjector != null) {
			originalInjector.inject(object);
		}

	}

	/**
	 * Called by Wicket when a component is being constructed. Here is the
	 * opportunity to inject field values into it.
	 */
	public void onInstantiation(Component component) {
		inject(component);
	}
}
