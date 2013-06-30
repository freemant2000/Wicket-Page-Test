package com.ttdev.wicketpagetest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * It is a Wicket locator for Selenium. For example, Using //myform[2]//name as
 * the path it will first locate the 3rd element (breadth-first search) with
 * wicket:id="myform" and then the first element in it with wicket:id="name". If
 * it must be an immediate child, use / instead of //. By narrowing down the
 * search scope, we can speed up the efficiency of the algorithm.
 * 
 * @author Andy Chu
 * 
 */
public class ByWicketIdPathFastVersion extends By {
	private String path;
	private Pattern stepPattern;

	public ByWicketIdPathFastVersion(String path) {
		this.path = path;
		this.stepPattern = Pattern.compile("(//?)([\\w\\.]+)(\\[(\\d+)\\])?");
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		WebElement element = findByPath(context, path);
		return element == null ? null : Arrays
				.asList(new WebElement[] { element });
	}

	private WicketWebElement getRootElement(SearchContext context,
			String wicketId) {
		List<WebElement> elements = context
				.findElements(By
						.xpath("//*["
								+ "(@wicketpath = '"
								+ wicketId
								+ "')"
								+ " or "
								+ "(starts-with(@wicketpath, '"
								+ wicketId
								+ "_'))"
								+ " or "
								+ "(contains(@wicketpath, '_"
								+ wicketId
								+ "_'))"
								+ " or "
								+ "('_"
								+ wicketId
								+ "' = substring(@wicketpath, string-length(@wicketpath)- string-length('_"
								+ wicketId + "') +1))" + "]"));
		WicketWebElement root = new WicketWebElement();
		WicketWebElement last = root;
		for (WebElement element : elements) {
			WicketWebElement node = new WicketWebElement(element);
			last.findParent(node).add(node);
			last = node;
		}
		return root;
	}

	public WebElement findByPath(SearchContext context, String path) {
		Matcher matcher = stepPattern.matcher(path);
		WicketWebElement baseElement = null;
		int loopCount = 0;
		while (matcher.find()) {
			boolean isAnyLevelDeep = matcher.group(1).equals("//");
			String wicketId = matcher.group(2);
			String indexString = matcher.group(4);
			int index = 0;
			if (indexString != null) {
				index = Integer.parseInt(indexString);
			}
			if (loopCount == 0) {
				baseElement = getRootElement(context, wicketId);
			}
			WicketWebElement child = findByWicketId(baseElement,
					isAnyLevelDeep, wicketId, index);
			if (child == null) {
				return null;
			}
			baseElement = child;
			loopCount++;
		}
		return baseElement.getElement();
	}

	private WicketWebElement findByWicketId(WicketWebElement baseElement,
			boolean isAnyLevelDeep, String wicketId, int index) {
		int noElementsFound = 0;
		Queue<WicketWebElement> queue = new ArrayDeque<WicketWebElement>();
		queue.addAll(baseElement.getChildren());
		while (!queue.isEmpty()) {
			WicketWebElement child = queue.poll();
			if (matchesWicketId(child.getElement(), wicketId)) {
				if (noElementsFound == index) {
					return child;
				}
				noElementsFound++;
			}
			if (isAnyLevelDeep) {
				queue.addAll(child.getChildren());
			}
		}
		return null;
	}

	public boolean matchesWicketId(WebElement element, String wicketId) {
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

	@Override
	public String toString() {
		return ByWicketIdPathFastVersion.class.getSimpleName() + ": " + path;
	}
}
