package com.ttdev.wicketpagetest.sample.plain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class PageContainingNonWicketElement extends WebPage {
	private static final long serialVersionUID = 1L;

	public PageContainingNonWicketElement() {
		WebMarkupContainer b = new WebMarkupContainer("b");
		add(b.add(new WebMarkupContainer("a").add(new RefreshingView<String>(
				"row") {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<String>> getItemModels() {
				List<String> data = Arrays.asList("bA", "bB", "bC", "bD");
				List<IModel<String>> models = new ArrayList<IModel<String>>();
				for (String d : data) {
					models.add(new Model<String>(d));
				}
				return models.iterator();
			}

			@Override
			protected void populateItem(Item<String> item) {
				item.add(new Label("content", item.getModel()));
				item.add(new Label("content2", item.getModel()));
			}
		})));
		WebMarkupContainer a = new WebMarkupContainer("a");
		add(a);
		a.add(new RefreshingView<Student>("row") {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<Student>> getItemModels() {
				List<String> data = Arrays.asList("aA", "aB", "aC");
				List<IModel<Student>> models = new ArrayList<IModel<Student>>();
				for (String d : data) {
					models.add(new CompoundPropertyModel<Student>(new Student(d)));
				}
				return models.iterator();
			}

			@Override
			protected void populateItem(Item<Student> item) {
				item.add(new Label("name.zh"));				
			}
		});
	}
}
