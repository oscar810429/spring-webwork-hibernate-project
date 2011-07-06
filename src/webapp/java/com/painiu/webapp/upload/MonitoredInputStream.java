/*
 * @(#)MonitoredInputStream.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * <a href="MonitoredInputStream.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MonitoredInputStream.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MonitoredInputStream extends InputStream {
    private InputStream target;

    private ProgressListener listener;
    
    private ProgressEvent event = new ProgressEvent(this, 0L, 0L, ProgressEvent.PROGRESS);

    public MonitoredInputStream(InputStream target, ProgressListener listener) {
        this.target = target;
        this.listener = listener;
    }

    public int read(byte b[], int off, int len) throws IOException {
        int length = target.read(b, off, len);
        event.setProgress(len - off);
        listener.progressUpdate(event);
        return length;
    }

    public int read(byte b[]) throws IOException {
        int length = target.read(b);
        event.setProgress(b.length);
        listener.progressUpdate(event);
        return length;
    }

    public int read() throws IOException {
        int length = target.read();
        event.setProgress(1);
        listener.progressUpdate(event);
        return length;
    }

    public void close() throws IOException {
        target.close();
    }

}
