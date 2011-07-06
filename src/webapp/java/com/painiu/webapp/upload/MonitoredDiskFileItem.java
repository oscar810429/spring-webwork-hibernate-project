/*
 * @(#)MonitoredDiskFileItem.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;

/**
 * <p>
 * <a href="MonitoredDiskFileItem.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MonitoredDiskFileItem.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MonitoredDiskFileItem extends DiskFileItem {
	private MonitoredOutputStream mos = null;

	private ProgressListener listener;

	public MonitoredDiskFileItem(String fieldName, String contentType,
			boolean isFormField, String fileName, int sizeThreshold,
			File repository, ProgressListener listener) {
		
		super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
		
		this.listener = listener;
	}

	public OutputStream getOutputStream() throws IOException {
		if (mos == null) {
			mos = new MonitoredOutputStream(super.getOutputStream(), listener);
		}
		return mos;
	}
}
