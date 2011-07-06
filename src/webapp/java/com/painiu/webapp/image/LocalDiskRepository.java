/*
 * @(#)LocalDiskRepository.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.Painiu;
import com.painiu.core.model.Photo;
import com.painiu.core.model.PhotoAddress;

/**
 * <p>
 * <a href="LocalDiskRepository.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LocalDiskRepository.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class LocalDiskRepository implements Repository {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(LocalDiskRepository.class);
	
	//~ Instance fields ========================================================

	private Integer host;
	
	private String directory;
	
	private boolean readOnly = false;
	
	private int weight = 1;
	
	//~ Constructors ===========================================================

	public LocalDiskRepository() {}
	
	public LocalDiskRepository(Integer host, String directory, boolean readOnly, int weight) {
		this.host = host;
		this.directory = directory;
		this.readOnly = readOnly;
		this.weight = weight;
	}

	//~ Methods ================================================================
	
	/*
	 * @see com.painiu.image.Repository#deletePhoto(com.painiu.model.Photo)
	 */
	public void deletePhoto(Photo photo) {
		PhotoAddress address = photo.getAddress();
		String dir = address.getDir();
		String filename = address.getFilename();
		String secret = address.getSecret();
		
		deleteFile(dir, filename, Painiu.SUFFIX_SQUARE);
		deleteFile(dir, filename, Painiu.SUFFIX_THUMB);
		deleteFile(dir, filename, Painiu.SUFFIX_SMALL);
		deleteFile(dir, filename, Painiu.SUFFIX_MEDUIM);
		deleteFile(dir, filename, Painiu.SUFFIX_LARGE);
		deleteFile(dir, filename, "_" + secret + ".jpg");
		//deleteFile(dir, filename, secret != null ? ("_" + secret + ".jpg") : Painiu.SUFFIX_LARGE);		
	}
	
	private void deleteFile(String dir, String filename, String suffix) {
		String path = new StringBuffer().append(dir)
				.append(Painiu.FILE_SEPARATOR)
				.append(filename)
				.append(suffix).toString();
		
		File file = new File(directory, path);

		if (log.isDebugEnabled()) {
			log.debug("delete photo file: " + file.getAbsolutePath());
		}
		
		if (file.exists()) {			
			file.delete();
		}
	}
	
	
	/*
	 * @see com.painiu.image.Repository#readOnly()
	 */
	public boolean readOnly() {
		return readOnly;
	}
	
	/*
	 * @see com.painiu.image.Repository#getWeight()
	 */
	public int getWeight() {
		return weight;
	}	
	
	//~ Accessors ==============================================================

	/**
	 * @return Returns the directory.
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @param directory The directory to set.
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	/**
	 * @return Returns the host.
	 */
	public Integer getHost() {
		return host;
	}

	/**
	 * @param host The host to set.
	 */
	public void setHost(Integer host) {
		this.host = host;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
