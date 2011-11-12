package com.ttdev.wicketpagetest;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jetty.util.ArrayQueue;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * It is a wicket locator for Selenium. For example, Using //myform[2]//name as
 * the path it will first locate the 3rd element (breadth-first search) with
 * wicket:id="myform" and then the first element in it with wicket:id="name". If
 * it must be an immediate child, use / instead of //.
 * 
 * @author Kent Tong
 * 
 */
public class ByWicketIdPath extends By {
	private String path;
	private Pattern stepPattern;

	public ByWicketIdPath(String path) {
		this.path = path;
		this.stepPattern = Pattern.compile("(//?)([\\w\\.]+)(\\[(\\d+)\\])?");
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		WebElement element = findWicketElementFrom(getRootElement(context),
				path);
		return element == null ? null : Arrays
				.asList(new WebElement[] { element });
	}

	private WebElement getRootElement(SearchContext context) {
		if (context instanceof WebElement) {
			return (WebElement) context;
		} else {
			List<WebElement> children = getChildren(context);
			return children.isEmpty() ? null : children.get(0);
		}
	}

	private WebElement findWicketElementFrom(WebElement baseElement, String path) {
		Matcher matcher = stepPattern.matcher(path);
		while (matcher.find()) {
			boolean isAnyLevelDeep = matcher.group(1).equals("//");
			String wicketId = matcher.group(2);
			String indexString = matcher.group(4);
			int index = 0;
			if (indexString != null) {
				index = Integer.parseInt(indexString);
			}
			WebElement child = findChild(baseElement, isAnyLevelDeep, wicketId,
					index);
			if (child == null) {
				return null;
			}
			baseElement = child;
		}
		return baseElement;
	}

	private WebElement findChild(WebElement baseElement,
			boolean isAnyLevelDeep, String wicketId, int index) {
		int noElementsFound = 0;
		Queue<WebElement> queue = new ArrayQueue<WebElement>();
		queue.addAll(getChildren(baseElement));
		while (!queue.isEmpty()) {
			WebElement child = queue.poll();
			if (matchesWicketId(child, wicketId)) {
				if (noElementsFound == index) {
					return child;
				}
				noElementsFound++;
			}
			if (isAnyLevelDeep) {
				queue.addAll(getChildren(child));
			}
		}
		return null;
	}

	private boolean matchesWicketId(WebElement element, String wicketId) {
		String wicketIdValue = element.getAttribute("wicket:id");
		if (wicketIdValue != null && wicketIdValue.equals(wicketId)) {
			return true;
		}
		// wicket:id may not be there (see WICKET-2832), also try wicketpath.
		// It must be prepared to deal with wicketpath values like ???_foo and
		// ???_foo_0.
		// In addition, if the wicket ID contains a dot, it must be escaped.
		String wicketPathPattern = "^((.*[^_])_)*"
				+ wicketId.replace(".", "\\.").replace("_", "__") + "(_\\d+)?$";
		String pathValue = element.getAttribute("wicketpath");
		if (pathValue != null && pathValue.matches(wicketPathPattern)) {
			return true;
		}
		return false;
	}

	private List<WebElement> getChildren(SearchContext context) {
		return context.findElements(By.xpath("*"));
	}

	@Override
	public String toString() {
		return ByWicketIdPath.class.getSimpleName() + ": " + path;
	}
}
