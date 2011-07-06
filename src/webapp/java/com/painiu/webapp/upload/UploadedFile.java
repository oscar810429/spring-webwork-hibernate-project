/*
 * @(#)UploadedFile.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;

/**
 * <p>
 * <a href="UploadedFile.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UploadedFile.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UploadedFile implements Comparable {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String originalFilename;	
	private String contentType;
	private File diskFile;
	private long length;

	
	//~ Constructors ===========================================================

	public UploadedFile(String filename, String contentType, File diskFile) {
		this.originalFilename = filename;
		this.diskFile = diskFile;
		this.contentType = contentType;
		this.length = diskFile.length();

	}
	
	//~ Methods ================================================================

	
	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the originalFilename.
	 */
	public String getOriginalFilename() {
		return originalFilename;
	}
	
	/**
	 * @param originalFilename The originalFilename to set.
	 */
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	/**
	 * @return Returns the diskFile.
	 */
	public File getDiskFile() {
		return diskFile;
	}

	/**
	 * @param diskFile The diskFile to set.
	 */
	public void setDiskFile(File diskFile) {
		this.diskFile = diskFile;
	}

	/**
	 * @return Returns the contentType.
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType The contentType to set.
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if (o instanceof UploadedFile) {
            return originalFilename.compareTo(((UploadedFile) o).getOriginalFilename());
        }
		return 0;
	}
	
	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}
	
	/**
	 * @param length the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}

}
