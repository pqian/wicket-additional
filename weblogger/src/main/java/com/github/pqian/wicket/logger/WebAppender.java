package com.github.pqian.wicket.logger;

import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;

public class WebAppender extends WriterAppender {

	    public WebAppender()
	    {}

	    public WebAppender(final Layout layout)
	    {
	        setLayout(layout);
	        activateOptions();
	    }

	    @Override
	    public void activateOptions()
	    {
	        setWriter(WebLogger.getWriter());
	        super.activateOptions();
	    }
	
}
