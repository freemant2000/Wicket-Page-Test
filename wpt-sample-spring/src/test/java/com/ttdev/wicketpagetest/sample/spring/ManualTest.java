package com.ttdev.wicketpagetest.sample.spring;

import com.ttdev.wicketpagetest.WebAppJettyConfiguration;
import com.ttdev.wicketpagetest.WicketAppJettyLauncher;

public class ManualTest {
	public static void main(String[] args) {
		WicketAppJettyLauncher launcher = new WicketAppJettyLauncher();
		WebAppJettyConfiguration cfg = new WebAppJettyConfiguration();
		cfg.setOverrideWebXml("web-test.xml");
		launcher.startAppInJetty(cfg);
	}
}
