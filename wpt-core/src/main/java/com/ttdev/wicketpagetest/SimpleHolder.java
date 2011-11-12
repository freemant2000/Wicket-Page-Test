package com.ttdev.wicketpagetest;

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
