/*
 * @(#)ProgressListener.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.util.EventListener;

/**
 * <p>
 * <a href="ProgressListener.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ProgressListener.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface ProgressListener extends EventListener {
	
	public void progressStart(ProgressEvent e);
	
	public void progressUpdate(ProgressEvent e);
	
	public void progressFinish(ProgressEvent e);
}
