package com.ttdev.wicketpagetest;

import org.testng.annotations.Test;

@Test
public class ObjectFactoryTest {
	public static class Foo {
		public String out;

		public Foo(String x, int y) {
			out = x + y;
		}
	}

	public void testCreate() {
		ObjectFactory factory = new ObjectFactory();
		Foo f1 = (Foo) factory.create(Foo.class, "a", 100);
		assert f1.out.equals("a100");
	}
}
