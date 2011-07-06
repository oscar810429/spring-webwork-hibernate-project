/*
 * @(#)ResponseFormat.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 * <a href="ResponseFormat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ResponseFormat.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface ResponseFormat {
	
	public String getContentType();
	
	public void format(Writer out, Object object) throws IOException;
}
