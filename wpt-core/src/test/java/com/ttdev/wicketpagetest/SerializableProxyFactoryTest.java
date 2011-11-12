package com.ttdev.wicketpagetest;

import java.io.Serializable;

import org.testng.annotations.Test;

@Test
public class SerializableProxyFactoryTest {
	private boolean called;

	public interface Foo {
		void m1();
	}

	public void testSerialzable() {
		SerializableProxyFactory factory = new SerializableProxyFactory();
		Foo original = new Foo() {

			public void m1() {
				called = true;
			}

		};
		Foo proxy = factory.createProxy(Foo.class, original);
		proxy.m1();
		assert called;
		assert proxy instanceof Serializable;
	}

}
