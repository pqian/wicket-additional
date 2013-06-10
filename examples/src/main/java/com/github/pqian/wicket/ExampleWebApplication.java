package com.github.pqian.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;

import com.github.pqian.wicket.logger.WebLogger;
import com.github.pqian.wicket.logger.WebLoggerSettings;

public class ExampleWebApplication extends WebApplication {

	@Override
	protected void init() {
		super.init();
		if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
			WebLoggerSettings.setPattern("%d %-5p [%t] %c - %m%n");
			WebLoggerSettings.setMountPath("/logger");
			WebLoggerSettings.configure(this);
		}
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return ExampleHome.class;
	}

	@Override
	public void sessionUnbound(String sessionId) {
		super.sessionUnbound(sessionId);
		if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
			WebLogger.removeLogViewer(sessionId);
		}
	}
}
