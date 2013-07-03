package com.ttdev.wicketpagetest;

import org.apache.wicket.util.io.IClusterable;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * In unit tests, typically we need to create a mock object implementing a one
 * or two methods only of a certain interface. As we don't care about the other
 * methods, a dummy implementation (e.g., throwing an "unsupported" exception)
 * is enough for them. However, manually defining all such boring methods is no
 * good: on one hand, it is just noise in the code; on the other hand, if new
 * methods are added to the interface, the code will break.
 * 
 * To avoid these problems, you can define a class implement those methods
 * concerned only, then use this class to generate a sub-class of it at runtime
 * to implement all the rest abstract methods and create an instance for you.
 * <p>
 * In addition, it is assumed that the your class (and thus the sub-class
 * generated) is an inner class of an outer class (your unit test class) so the
 * instance will have access to the outer instance.
 * <p>
 * See {@link ChangeResistantMockFactoryTest} for how to use.
 * 
 * @author Kent Tong
 * 
 */
public class ChangeResistantMockFactory implements IClusterable {
	private static final long serialVersionUID = 1L;
	private Object outerInstance;

	public ChangeResistantMockFactory(Object outerInstance) {
		this.outerInstance = outerInstance;
	}

	@SuppressWarnings("unchecked")
	public <T> T implementAbstractMethods(Class<T> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(NoOp.INSTANCE);
		return (T) enhancer.create(new Class[] { outerInstance.getClass() },
				new Object[] { outerInstance });
	}
}
