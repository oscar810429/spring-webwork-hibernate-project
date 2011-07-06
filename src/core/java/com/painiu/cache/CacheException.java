/*
 * @(#)CacheException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

/**
 * <p>
 * <a href="CacheException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CacheException.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class CacheException extends RuntimeException {
	
	public CacheException(String s) {
		super(s);
	}

	public CacheException(String s, Exception e) {
		super(s, e);
	}
	
	public CacheException(Exception e) {
		super(e);
	}
	
}
