/*
 * @(#)Repository.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

//import com.mingda.core.model.Photo;

/**
 * <p>
 * <a href="Repository.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Repository.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface Repository {
	
	public Integer getHost();
	
	public String getDirectory();
	
	//public void deletePhoto(Photo photo);
	
	public boolean readOnly();
	
	public int getWeight();
}
