package com.ttdev.wicketpagetest.sample.plain;

import org.apache.wicket.util.io.IClusterable;

public class Name implements IClusterable {
	private static final long serialVersionUID = 1L;
	private String zh;
	private String en;

	public Name() {
	}

	public String getZh() {
		return zh;
	}

	public void setZh(String zh) {
		this.zh = zh;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

}
