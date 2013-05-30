package com.github.pqian.wicket.logger;

import java.io.Reader;
import java.io.Writer;

import org.apache.wicket.protocol.http.WebApplication;

public class WebLogger {

	public static void configure(WebApplication webApplication) {
		webApplication.mountPage("/logger", WebLoggerPage.class);
	}
	
	public static Writer getWriter() {
		return null;
	}
	
	public static Reader getReader() {
		return null;
	}
}
