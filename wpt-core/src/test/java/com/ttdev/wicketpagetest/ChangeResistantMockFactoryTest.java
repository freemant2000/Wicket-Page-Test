package com.ttdev.wicketpagetest;

import org.testng.annotations.Test;

@Test
public class ChangeResistantMockFactoryTest {
	private int n = 0;

	public interface Foo {
		void m1();

		void m2();

		int m3();
	}

	public abstract class MyMock implements Foo {
		public void m1() {
			n++;
		}
	}

	public void testExplicitlyProvidedMethod() {
		ChangeResistantMockFactory f = new ChangeResistantMockFactory(this);
		Foo foo = f.implementAbstractMethods(MyMock.class);
		assert n == 0;
		foo.m1();
		assert n == 1;
	}

}
