package com.ttdev.wicketpagetest;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * This class helps you create instances of a Class object, with constructor
 * argument values.
 * 
 * @author Kent Tong
 * 
 */
public class ObjectFactory {

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER;

	static {
		PRIMITIVE_TO_WRAPPER = new HashMap<Class<?>, Class<?>>();
		PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
		PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
		PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
		PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
		PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
		PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
		PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);
		PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
	}

	/**
	 * Create an instance of the specified class, using the specified
	 * constructor arguments. It will try to find a constructor that is
	 * compatible. However, it will only use the first one that is compatible.
	 * It will NOT find the best fit.
	 * 
	 * @param <T>
	 *            The type of the class
	 * @param objClass
	 *            The class from which to create instances
	 * @param constructorArgs
	 *            The constructor arguments
	 * @return The instance created
	 */
	@SuppressWarnings("unchecked")
	public <T> T create(Class<T> objClass, Object... constructorArgs) {
		for (Constructor<?> c : objClass.getConstructors()) {
			Constructor<T> ctor = (Constructor<T>) c;
			Class<?>[] types = ctor.getParameterTypes();
			if (typesMatchingArgs(types, constructorArgs)) {
				try {
					return ctor.newInstance(constructorArgs);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new IllegalArgumentException(
				"Not matching constructor found in class: "
						+ objClass.getName());

	}

	private boolean typesMatchingArgs(Class<?>[] types, Object[] constructorArgs) {
		if (types.length != constructorArgs.length) {
			return false;
		}
		for (int i = 0; i < types.length; i++) {
			if (!isAssignableFrom(types[i], constructorArgs[i].getClass())) {
				return false;
			}
		}
		return true;
	}

	private boolean isAssignableFrom(Class<?> to, Class<?> from) {
		if (PRIMITIVE_TO_WRAPPER.get(from) == to
				|| PRIMITIVE_TO_WRAPPER.get(to) == from) {
			return true;
		}
		return to.isAssignableFrom(from);
	}

}
