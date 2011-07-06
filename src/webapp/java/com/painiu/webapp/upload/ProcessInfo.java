/*
 * @(#)ProcessInfo.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.Serializable;

/**
 * <p>
 * <a href="ProcessInfo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ProcessInfo.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ProcessInfo implements Serializable {
	
	private long total = 0;

	private long finished = 0;

	private String current;
	
	private long elapsedTime = 0;

	private String status = ProgressEvent.FINISH;

	
	public ProcessInfo() {
	}

	public ProcessInfo(long total, long finished, String current, long elapsedTime, String status) {
		this.total = total;
		this.finished = finished;
		this.current = current;
		this.elapsedTime = elapsedTime;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public long getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean isInProgress() {
		return ProgressEvent.PROGRESS.equals(status) || ProgressEvent.START.equals(status);
	}

}
