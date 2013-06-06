package com.github.pqian.wicket.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.wicket.protocol.http.WebApplication;

public class WebLoggerSettings {
	
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(WebLoggerSettings.class);

    private static String name = "WebLogger";
    private static String pattern = "%d %-5p [%c{1}] %m%n";
    private static Priority priority = null;
    private static String mountPath = "/logger";

    public static void configure(final WebApplication webapp)
    {
        final WebAppender appender = new WebAppender();
        appender.setName(name);
        appender.setThreshold(priority);
        appender.setLayout(new PatternLayout(pattern));
        appender.activateOptions();
        Logger.getRootLogger().addAppender(appender);
        LOG.info("WebAppender[name={}, priority={}] created", appender.getName(), appender.getThreshold());

        webapp.mountPage(mountPath, WebLoggerPage.class);
        LOG.info("WebLoggerPage mounted on path: {}", mountPath);
    }

    public static String getName()
    {
        return name;
    }

    public static void setName(final String name)
    {
        WebLoggerSettings.name = name;
    }

    public static String getPattern()
    {
        return pattern;
    }

    public static void setPattern(final String pattern)
    {
        WebLoggerSettings.pattern = pattern;
    }

    public static Priority getPriority()
    {
        return priority;
    }

    public static void setPriority(final Priority priority)
    {
        WebLoggerSettings.priority = priority;
    }

    public static String getMountPath()
    {
        return mountPath;
    }

    public static void setMountPath(final String mountPath)
    {
        WebLoggerSettings.mountPath = mountPath;
    }

}
