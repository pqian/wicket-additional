package com.github.pqian.wicket.logger;

import java.io.IOException;
import java.io.LineNumberReader;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.time.Duration;

public class WebLoggerPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	public WebLoggerPage() {
		final LineNumberReader reader = new LineNumberReader(WebLogger.getReader());
		final Label buffer = new Label("buffer", new AbstractReadOnlyModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				StringBuilder lines = new StringBuilder();
				String line = null;
				int count = 0;
				try {
					while(count++<5 && (line = reader.readLine())!=null) {
						lines.append(line).append('\n');
					}
				} catch (IOException e) {
					throw new RuntimeException("Read logger failed", e);
				}
				return lines.toString();
			}
		});
		add(buffer.setOutputMarkupId(true));
		buffer.add(new AbstractAjaxTimerBehavior(Duration.seconds(2)) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTimer(AjaxRequestTarget target) {
				target.add(buffer);
			}
			
			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);
				attributes.getAjaxCallListeners().add(new AjaxCallListener() {

					private static final long serialVersionUID = 1L;
				
					@Override
					public CharSequence getSuccessHandler(Component component) {
						return "def pre = $(<pre></pre>).html($(#buffer).html());$(body).append(pre);" + super.getSuccessHandler(component);
					}
				});
			}
		});
	}

}
