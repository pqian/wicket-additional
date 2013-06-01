package com.github.pqian.wicket.logger;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.wicket.protocol.http.WebApplication;

public class WebLogger {

	private static class ReaderWriterHolder {
		private static Reader reader;
		private static Writer writer;
		
		static {
			reader = new PipedReader();
			try {
				writer = new PipedWriter((PipedReader)reader);
			} catch (IOException e) {
				new RuntimeException("Cannot instantiate a PipedWriter with the given PipedReader", e);
			}
		}
		
	}
	
	public static void configure(WebApplication webApplication) {
		webApplication.mountPage("/logger", WebLoggerPage.class);
	}
	
	public static Writer getWriter() {
		return ReaderWriterHolder.writer;
	}
	
	public static Reader getReader() {
		return ReaderWriterHolder.reader;
	}
}
