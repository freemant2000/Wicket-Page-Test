package com.ttdev.wicketpagetest.sample.plain;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	private Name name;

	public Student(String cName) {
		this.name = new Name();
		this.name.setZh(cName);
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
}
