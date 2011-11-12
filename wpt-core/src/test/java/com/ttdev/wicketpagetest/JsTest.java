package com.ttdev.wicketpagetest;

import org.testng.annotations.Test;

@Test
public class JsTest {
	public void testWicketPathMatching() {
		String wicketPathPattern = makeRegExPattern("foo");
		assert "foo".matches(wicketPathPattern);
		assert "bar_foo".matches(wicketPathPattern);
		assert !"bar__foo".matches(wicketPathPattern); // wicket:id="bar_foo"
		wicketPathPattern = makeRegExPattern("bar_foo");
		assert "bar__foo".matches(wicketPathPattern);
		assert "baz_bar__foo".matches(wicketPathPattern);
		assert !"baz_bar_foo".matches(wicketPathPattern);
		assert !"bar_foo".matches(wicketPathPattern);

	}

	public void testWicketPathMatchingForListItem() {
		String wicketPathPattern = makeRegExPattern("foo");
		assert "foo_0".matches(wicketPathPattern);
		assert "foo_1".matches(wicketPathPattern);
		assert !"foo__1".matches(wicketPathPattern);
		assert !"foo_1_bar".matches(wicketPathPattern);
		assert !"foo__".matches(wicketPathPattern);
	}

	private String makeRegExPattern(String lastWicketId) {
		String wicketPathPattern = "^((.*[^_])_)*"
				+ lastWicketId.replace("_", "__") + "(_\\d+)?$";
		return wicketPathPattern;
	}
}
