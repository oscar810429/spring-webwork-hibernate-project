/*
 * @(#)DummyMultiPartRequest.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest;

/**
 * <p>
 * <a href="DummyMultiPartRequest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DummyMultiPartRequest.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DummyMultiPartRequest extends MultiPartRequest {

	public DummyMultiPartRequest(HttpServletRequest request, String saveDir, int maxSize) {	
	}
	
	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getContentType(java.lang.String)
	 */
	public String[] getContentType(String fieldName) {
		return new String[0];
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getErrors()
	 */
	public List getErrors() {
		return new ArrayList(0);
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFile(java.lang.String)
	 */
	public File[] getFile(String fieldName) {
		return new File[0];
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFileNames(java.lang.String)
	 */
	public String[] getFileNames(String fieldName) {
		return new String[0];
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFileParameterNames()
	 */
	public Enumeration getFileParameterNames() {
		return new EmptyEnumerator();
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFilesystemName(java.lang.String)
	 */
	public String[] getFilesystemName(String fieldName) {
		return new String[0];
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getParameter(java.lang.String)
	 */
	public String getParameter(String name) {
		return null;
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getParameterNames()
	 */
	public Enumeration getParameterNames() {
		return new EmptyEnumerator();
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String name) {
		return new String[0];
	}

	private static class EmptyEnumerator implements Enumeration {

		EmptyEnumerator() {
		}

		public boolean hasMoreElements() {
			return false;
		}

		public Object nextElement() {
			throw new NoSuchElementException("Hashtable Enumerator");
		}
	}
}
