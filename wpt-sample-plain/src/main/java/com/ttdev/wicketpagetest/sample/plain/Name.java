package com.ttdev.wicketpagetest.sample.plain;

import java.io.Serializable;

public class Name implements Serializable{
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
