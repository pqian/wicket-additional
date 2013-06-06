package com.github.pqian.wicket.logger;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;

public class WebLoggerPage extends WebPage
{
    private static final long serialVersionUID = 1L;

    private LogViewer logViewer;

    public WebLoggerPage()
    {}

    public WebLoggerPage(final PageParameters params)
    {
        super(params);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        logViewer = new LogViewer(new StringBuilder());
        WebLogger.addLogViewer(logViewer);

        final IndicatingAjaxBuffer buffer = new IndicatingAjaxBuffer("buffer");
        add(buffer.setOutputMarkupId(true));

        final Label data = new Label("data", new AbstractReadOnlyModel<String>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public String getObject()
            {
                final Appendable oldBuffer = logViewer.getAndSet(new StringBuilder());
                return oldBuffer.toString();
            }
        });
        buffer.add(data);

        buffer.add(new AbstractAjaxTimerBehavior(Duration.seconds(2))
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onTimer(final AjaxRequestTarget target)
            {
                target.add(buffer);
            }

            @Override
            protected void updateAjaxAttributes(final AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);

                final AjaxCallListener ajaxCallListener = new AjaxCallListener()
                {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public CharSequence getSuccessHandler(final Component component)
                    {
                        final String id = buffer.getMarkupId();
                        final String js = "" //
                                + "setTimeout(function() {" // delay 0.5 s to show indicator, our server response is too fast ;-)
                                + "    var buffer=$('#" + id + "');" // find buffer area
                                + "    var data=$('pre',buffer);" //  find buffer data area
                                + "    if (data.is(':empty')) return;" // skip if no new log retrieved
                                + "    var pre=$('<pre></pre>').html(data.html());" // create new pre tag for new log
                                + "    data.empty();" // clear buffer data
                                + "    pre.insertBefore(buffer);" // insert new log
                                + "    $('body').animate({ scrollTop: $(document).height() }, 1000);" // scroll down to bottom
                                + "}, 1000);"; // delay 500 ms
                        return js;
                    }

                    @Override
                    public CharSequence getBeforeSendHandler(final Component component)
                    {
                        return "" + super.getBeforeSendHandler(component);
                    }
                };
                attributes.getAjaxCallListeners().add(ajaxCallListener);
            }

        });
    }

    private class IndicatingAjaxBuffer extends WebMarkupContainer implements IAjaxIndicatorAware
    {
        private static final long serialVersionUID = 1L;
        private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender();

        public IndicatingAjaxBuffer(final String id)
        {
            super(id);
            add(indicatorAppender);
        }

        @Override
        public String getAjaxIndicatorMarkupId()
        {
            return indicatorAppender.getMarkupId();
        }
    }
}
