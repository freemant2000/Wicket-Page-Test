package com.ttdev.wicketpagetest;

import net.sf.cglib.proxy.NoOp;

import org.apache.wicket.util.io.IClusterable;

public class ClusterableNoOp implements NoOp, IClusterable {
	private static final long serialVersionUID = 1L;
}
