package com.ttdev.wicketpagetest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class WicketWebElement {
	private WicketWebElement parent;
	private int depth;
	private WebElement element;
	private List<WicketWebElement> children;

	public WicketWebElement() {
		this(null);
	}

	public WicketWebElement(WebElement element) {
		this.children = new ArrayList<WicketWebElement>();
		this.element = element;
		this.depth = (element == null ? 0 : element.getAttribute("wicketpath")
				.split("_").length);
	}

	public WicketWebElement getParent() {
		return parent;
	}

	public int getDepth() {
		return depth;
	}

	public void setParent(WicketWebElement parent) {
		this.parent = parent;
	}

	public void add(WicketWebElement child) {
		children.add(child);
		child.setParent(this);
	}

	public WicketWebElement findParent(WicketWebElement element) {
		if (parent == null) {
			return this;
		}
		if (this.depth == element.depth) {
			return parent;
		}
		if (this.depth < element.depth) {
			return this;
		}
		return parent.findParent(element);
	}

	public WebElement getElement() {
		return element;
	}

	public List<WicketWebElement> getChildren() {
		return children;
	}
}
