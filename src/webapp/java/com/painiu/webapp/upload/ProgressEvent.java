/*
 * @(#)ProgressEvent.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.util.EventObject;

/**
 * <p>
 * <a href="ProgressEvent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ProgressEvent.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ProgressEvent extends EventObject {
	//~ Static fields/initializers =============================================

	public static final String START = "start";
	
	public static final String PROGRESS = "progress";
	
	public static final String FINISH = "finish";
	
	public static final String ERROR = "error";
	
	//~ Instance fields ========================================================
	
	private long progress;
	
	private long expected;
	
	private String description;

	private String status = START;
	
	//~ Constructors ===========================================================
	
	public ProgressEvent(Object source) {
		super(source);
	}
	
	public ProgressEvent(Object source, long expected, long progress, String status) {
		this(source, expected, progress, status, null);
	}
	
	public ProgressEvent(Object source, long expected, long progress, String status, String description) {
		super(source);
		this.expected = expected;
		this.progress = progress;
		this.status = status;
		this.description = description;
	}

	//~ Methods ================================================================
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getExpected() {
		return expected;
	}

	public void setExpected(long expected) {
		this.expected = expected;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getProgress() {
		return progress;
	}

	public void setProgress(long progress) {
		this.progress = progress;
	}
}
