package com.github.pqian.wicket.logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

public class LogViewer implements Appendable, Serializable {

	private static final long serialVersionUID = 1L;

	private final AtomicReference<Appendable> ref;

	public LogViewer(final Appendable appendable) {
		ref = new AtomicReference<Appendable>(appendable);
	}

	public Appendable getAndSet(final Appendable appendable) {
		return ref.getAndSet(appendable);
	}

	@Override
	public Appendable append(final CharSequence csq) throws IOException {
		ref.get().append(csq);
		return this;
	}

	@Override
	public Appendable append(final CharSequence csq, final int start,
			final int end) throws IOException {
		ref.get().append(csq, start, end);
		return this;
	}

	@Override
	public Appendable append(final char c) throws IOException {
		ref.get().append(c);
		return this;
	}
}
