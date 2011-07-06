/*
 * @(#)Handler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

/**
 * <p>
 * <a href="Handler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Handler.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface Handler {
	
	public Object execute(Call call) throws ApiException;
	
}
