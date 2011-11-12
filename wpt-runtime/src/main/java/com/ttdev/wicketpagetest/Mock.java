package com.ttdev.wicketpagetest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a field that is to be injected with a mock object (not even a Spring
 * bean). This should only be used on pages that are used for testing only and
 * will never be used in production.
 * 
 * @author Kent Tong
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
@Documented
public @interface Mock {

}
