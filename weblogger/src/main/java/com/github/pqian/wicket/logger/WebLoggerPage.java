package com.github.pqian.wicket.logger;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class WebLoggerPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	public WebLoggerPage() {
		add(new Label("loggerView", new AbstractReadOnlyModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				// WebLogger.getReader();
				return "";
			}
		}));
	}

}
