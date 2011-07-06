/*
 * @(#)UploadInfo.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.Serializable;

/**
 * <p>
 * <a href="UploadInfo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UploadInfo.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UploadInfo implements Serializable {
	
	private long totalSize = 0;

	private long bytesRead = 0;

	private long elapsedTime = 0;

	private String status = ProgressEvent.START;

	public UploadInfo() {
	}

	public UploadInfo(long totalSize, long bytesRead, long elapsedTime, String status) {
		this.totalSize = totalSize;
		this.bytesRead = bytesRead;
		this.elapsedTime = elapsedTime;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public long getBytesRead() {
		return bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
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
