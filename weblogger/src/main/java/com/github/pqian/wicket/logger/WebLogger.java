package com.github.pqian.wicket.logger;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public class WebLogger {

	private static final Set<LogViewer> LOG_VIEWERS = new HashSet<LogViewer>();

    private static class WriterHolder
    {
        private static final Writer WRITER = new AppendableWriter(new Dispatcher());
    }

    public static Writer getWriter()
    {
        return WriterHolder.WRITER;
    }

    public static void addLogViewer(final LogViewer logViewer)
    {
        LOG_VIEWERS.add(logViewer);
    }

    public static void removeLogViewer(final LogViewer logViewer)
    {
        LOG_VIEWERS.remove(logViewer);
    }

    private static class Dispatcher implements Appendable
    {

        @Override
        public Appendable append(final CharSequence csq, final int start, final int end) throws IOException
        {
            for (final LogViewer viewer : LOG_VIEWERS)
            {
                viewer.append(csq, start, end);
            }
            return this;
        }

        @Override
        public Appendable append(final char c) throws IOException
        {
            for (final LogViewer viewer : LOG_VIEWERS)
            {
                viewer.append(c);
            }
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq) throws IOException
        {
            for (final LogViewer viewer : LOG_VIEWERS)
            {
                viewer.append(csq);
            }
            return this;
        }
    };

    private static class AppendableWriter extends Writer
    {
        private final Appendable target;

        AppendableWriter(final Appendable target)
        {
            this.target = target;
        }

        @Override
        public void write(final char cbuf[], final int off, final int len) throws IOException
        {
            target.append(new String(cbuf, off, len));
        }

        @Override
        public void flush() throws IOException
        {}

        @Override
        public void close() throws IOException
        {}

        @Override
        public void write(final int c) throws IOException
        {
            target.append((char) c);
        }

        @Override
        public void write(final String str) throws IOException
        {
            target.append(str);
        }

        @Override
        public void write(final String str, final int off, final int len) throws IOException
        {
            target.append(str, off, off + len);
        }

        @Override
        public Writer append(final char c) throws IOException
        {
            target.append(c);
            return this;
        }

        @Override
        public Writer append(final CharSequence charSeq) throws IOException
        {
            target.append(charSeq);
            return this;
        }

        @Override
        public Writer append(final CharSequence charSeq, final int start, final int end) throws IOException
        {
            target.append(charSeq, start, end);
            return this;
        }
    }
}
