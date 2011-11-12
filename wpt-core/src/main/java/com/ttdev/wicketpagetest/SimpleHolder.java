package com.ttdev.wicketpagetest;

/**
 * A simple implementation for {@link Holder}.
 * 
 * @author Kent Tong
 * 
 * @param <T>
 *            the type of the object being held
 */
public class SimpleHolder<T> implements Holder<T> {
	private T e;

	public SimpleHolder(T e) {
		this.e = e;
	}

	public T get() {
		return e;
	}

	public static SimpleHolder<Object> make(Object obj) {
		return new SimpleHolder<Object>(obj);
	}

}
