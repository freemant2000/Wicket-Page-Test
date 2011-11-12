package com.ttdev.wicketpagetest;

/**
 * A holder containing an object reference. Its main purpose is to allow you to
 * create a serializable proxy around an object of a concrete class (usually a
 * test case class), so that a Wicket page can access the fields in that object
 * through this path: page => serializable holder proxy => object => fields.
 * 
 * If you don't use a holder, the access path will be: page => serializable
 * object proxy => object, but using this path only method calls will pass
 * through, but field accesses will hit those in the proxy itself.
 * 
 * @author Kent Tong
 * 
 * @param <T>
 *            the type of the object being held
 */
public interface Holder<T> {
	public T get();
}
