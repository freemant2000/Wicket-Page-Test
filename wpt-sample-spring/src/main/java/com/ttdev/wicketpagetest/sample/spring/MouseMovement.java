package com.ttdev.wicketpagetest.sample.spring;

import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.CssResourceReference;

public class MouseMovement extends WebPage {
	private static final long serialVersionUID = 1L;

	public MouseMovement() {
		Label label = new Label("label", "Hello,World");
		label.setOutputMarkupId(true);
		label.add(AttributeModifier.replace("class", "invisible"));
		WebMarkupContainer container = new WebMarkupContainer("container");
		container
				.add(AttributeModifier.replace("onmouseover", $(label).show()));
		container.add(AttributeModifier.replace("onmouseout", $(label).hide()));
		add(container.add(label));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(Application.get()
				.getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				MouseMovement.class, "style.css")));
	}

	private JQueryBuilder $(Component component) {
		return new JQueryBuilder("#" + component.getMarkupId());
	}

}
