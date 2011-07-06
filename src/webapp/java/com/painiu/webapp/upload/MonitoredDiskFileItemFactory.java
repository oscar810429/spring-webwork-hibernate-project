/*
 * @(#)MonitoredDiskFileItemFactory.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * <p>
 * <a href="MonitoredDiskFileItemFactory.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MonitoredDiskFileItemFactory.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MonitoredDiskFileItemFactory extends DiskFileItemFactory {
	
    private ProgressListener listener = null;

	public MonitoredDiskFileItemFactory(ProgressListener listener) {
		super();
		this.listener = listener;
	}

	public MonitoredDiskFileItemFactory(int sizeThreshold, File repository,
			ProgressListener listener) {
		super(sizeThreshold, repository);
		this.listener = listener;
	}

	public FileItem createItem(String fieldName, String contentType,
			boolean isFormField, String fileName) {
		if (isFormField) {
			return new MonitoredDiskFileItem(fieldName, contentType, isFormField,
					fileName, getSizeThreshold(), getRepository(), listener);			
		}
		return new MonitoredDiskFileItem(fieldName, contentType, isFormField,
				fileName, 0, getRepository(), listener);	
	}
}
