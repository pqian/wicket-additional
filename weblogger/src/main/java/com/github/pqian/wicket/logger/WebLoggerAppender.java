package com.github.pqian.wicket.logger;

import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;

public class WebLoggerAppender extends WriterAppender {

	public WebLoggerAppender(Layout layout) {
		setLayout(layout);
		activateOptions();
	}

	@Override
	public void activateOptions() {
		setWriter(WebLogger.getWriter());
		super.activateOptions();
	}

}
