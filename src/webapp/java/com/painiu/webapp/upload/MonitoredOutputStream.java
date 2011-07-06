/*
 * @(#)MonitoredOutputStream.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * <a href="MonitoredOutputStream.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MonitoredOutputStream.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MonitoredOutputStream extends OutputStream {
	private OutputStream target;

	private ProgressListener listener;
	
	private ProgressEvent event = new ProgressEvent(this, 0L, 0L, ProgressEvent.PROGRESS);

	public MonitoredOutputStream(OutputStream target, ProgressListener listener) {
		this.target = target;
		this.listener = listener;
	}

	public void write(byte b[], int off, int len) throws IOException {
		target.write(b, off, len);
		event.setProgress(len - off);
		listener.progressUpdate(event);
	}

	public void write(byte b[]) throws IOException {
		target.write(b);
		event.setProgress(b.length);
		listener.progressUpdate(event);
	}

	public void write(int b) throws IOException {
		target.write(b);
		event.setProgress(1);
		listener.progressUpdate(event);
	}

	public void close() throws IOException {
		target.close();
	}

	public void flush() throws IOException {
		target.flush();
	}
}
