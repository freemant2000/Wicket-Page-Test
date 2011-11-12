package com.ttdev.wicketpagetest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ByWicketIdPathTest {
	private ChangeResistantMockFactory f = new ChangeResistantMockFactory(this);
	private String wicketpathInElement;
	private ByWicketIdPath locator;

	@BeforeMethod
	public void setUp() {
		locator = new ByWicketIdPath("//unused");
	}

	public abstract class WebElementForTest1 implements WebElement {
		public String getAttribute(String name) {
			return name.equals("wicket:id") ? "foo" : "";
		}
	}

	public abstract class WebElementForTest2 implements WebElement {
		public String getAttribute(String name) {
			return name.equals("wicketpath") ? wicketpathInElement : "";
		}
	}

	public abstract class WebElementForTest3 implements WebElement {
		private List<WebElement> children = new ArrayList<WebElement>();

		public List<WebElement> findElements(By by) {
			return children;
		}
	}

	public void testMatchesWicketId() throws Exception {
		WebElement element = f
				.implementAbstractMethods(WebElementForTest1.class);
		assert locator.matchesWicketId(element, "foo");
		assert !locator.matchesWicketId(element, "bar");
	}

	public void testMatchesWicketPath() throws Exception {
		WebElement element = f
				.implementAbstractMethods(WebElementForTest2.class);
		wicketpathInElement = "foo";
		assert locator.matchesWicketId(element, "foo");
		assert !locator.matchesWicketId(element, "bar");
		wicketpathInElement = "bar_foo"; // "foo" in a "bar"
		assert locator.matchesWicketId(element, "foo");
		wicketpathInElement = "bar__foo"; // for wicket:id="bar_foo"
		assert !locator.matchesWicketId(element, "foo");
		assert locator.matchesWicketId(element, "bar_foo");
		wicketpathInElement = "baz_bar__foo"; // wicket:id="bar_foo" in a "baz"
		assert locator.matchesWicketId(element, "bar_foo");
		wicketpathInElement = "baz_bar_foo";
		assert !locator.matchesWicketId(element, "bar_foo");
		wicketpathInElement = "bar_foo";
		assert !locator.matchesWicketId(element, "bar_foo");
	}

	public void testMatchesWicketPathForListItem() throws Exception {
		WebElement element = f
				.implementAbstractMethods(WebElementForTest2.class);
		wicketpathInElement = "foo_0";
		assert locator.matchesWicketId(element, "foo");
		wicketpathInElement = "foo_1";
		assert locator.matchesWicketId(element, "foo");
		wicketpathInElement = "foo__1";
		assert !locator.matchesWicketId(element, "foo");
		wicketpathInElement = "foo_1_bar";
		assert !locator.matchesWicketId(element, "foo");
		wicketpathInElement = "foo__";
		assert !locator.matchesWicketId(element, "foo");
	}

	public void testFindByWicketIdInFlatTree() throws Exception {
		final WebElementForTest3 e1 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e2 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e3 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e4 = f
				.implementAbstractMethods(WebElementForTest3.class);
		e1.children = Arrays.asList(new WebElement[] { e2, e3, e4 });
		locator = new ByWicketIdPath("//unused") {
			@Override
			public boolean matchesWicketId(WebElement element, String wicketId) {
				return element == e2 || element == e4;
			}
		};
		assert locator.findByWicketId(e1, false, "foo", 0) == e2;
		assert locator.findByWicketId(e1, false, "foo", 1) == e4;
		assert locator.findByWicketId(e1, false, "foo", 2) == null;

	}

	public void testFindByWicketIdInDeepTree() throws Exception {
		final WebElementForTest3 e1 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e2 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e3 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e4 = f
				.implementAbstractMethods(WebElementForTest3.class);
		e1.children = Arrays.asList(new WebElement[] { e2 });
		e2.children = Arrays.asList(new WebElement[] { e3 });
		e3.children = Arrays.asList(new WebElement[] { e4 });
		locator = new ByWicketIdPath("//unused") {
			@Override
			public boolean matchesWicketId(WebElement element, String wicketId) {
				return element == e2 || element == e4;
			}
		};
		assert locator.findByWicketId(e1, true, "foo", 0) == e2;
		assert locator.findByWicketId(e1, true, "foo", 1) == e4;
		assert locator.findByWicketId(e1, true, "foo", 2) == null;
		assert locator.findByWicketId(e1, false, "foo", 0) == e2;
		assert locator.findByWicketId(e1, false, "foo", 1) == null;
	}

	public void testFindByPath() throws Exception {
		final WebElementForTest3 e1 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e2 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e3 = f
				.implementAbstractMethods(WebElementForTest3.class);
		final WebElementForTest3 e4 = f
				.implementAbstractMethods(WebElementForTest3.class);
		e1.children = Arrays.asList(new WebElement[] { e2 });
		e2.children = Arrays.asList(new WebElement[] { e3 });
		e3.children = Arrays.asList(new WebElement[] { e4 });
		locator = new ByWicketIdPath("//unused") {
			@Override
			public WebElement findByWicketId(WebElement baseElement,
					boolean isAnyLevelDeep, String wicketId, int index) {
				if (baseElement == e1 && !isAnyLevelDeep
						&& wicketId.equals("abc") && index == 0) {
					return e2;
				}
				if (baseElement == e1 && isAnyLevelDeep
						&& wicketId.equals("xyz") && index == 3) {
					return e3;
				}
				return null;
			}
		};
		assert locator.findByPath(e1, "/abc") == e2;
		assert locator.findByPath(e1, "//xyz[3]") == e3;
	}
}
