package com.ttdev.wicketpagetest;

import java.util.Locale;

public class SwitchLocalePage extends DummyResponsePage {
	private static final long serialVersionUID = 1L;

	public SwitchLocalePage(Locale locale) {
		getSession().setLocale(locale);
	}
}
