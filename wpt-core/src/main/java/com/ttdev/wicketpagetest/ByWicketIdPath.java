package com.ttdev.wicketpagetest;

/**
 * It is a Wicket locator for Selenium. For example, Using //myform[2]//name as
 * the path it will first locate the 3rd element (breadth-first search) with
 * wicket:id="myform" and then the first element in it with wicket:id="name". If
 * it must be an immediate child, use / instead of //.
 * 
 * {@link ByWicketIdPathFastVersion} was an experimental implementation. As it
 * is working well, the official version now simply subclass it. In the future
 * {@link ByWicketIdPathFastVersion} will be removed.
 * 
 * @author Kent Tong
 */
public class ByWicketIdPath extends ByWicketIdPathFastVersion {

	public ByWicketIdPath(String path) {
		super(path);
	}

}
