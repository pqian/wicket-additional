package com.github.pqian.wicket.logger;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebLoggerExample extends WebPage {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(WebLoggerExample.class);

	private int count;
	public WebLoggerExample() {
		add(new AjaxLink<Void>("writeLog") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				LOG.info("This is a log for test. {}", ++count);
			}
		});
	}
}
