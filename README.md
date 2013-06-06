wicket-additional
=================

Additional tools/components to Wicket

#### weblogger
It is used to dispatch web server logging to browser on the client side. You can use it in development mode. It is useful and friendly to QA users who could not get logging file directly from web server sometimes. All logging information will be appended forward to more page instances by a custom WriterAppender, then these pages push log text to user browser using Ajax.

Usage example:

	public class MyWebAppliction extends WebApplication {
		@Override
		public void init() {
			if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
				WebLoggerSettings.setPattern("%d %-5p [%t] [%X{sid}] %c - %m%n");
				WebLoggerSettings.setMountPath("/logger");
				WebLoggerSettings.configure(this);
			}
		}
	}
