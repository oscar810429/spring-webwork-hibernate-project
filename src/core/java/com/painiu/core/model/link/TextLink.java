/*
 * @(#)TextLink.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model.link;

/**
 * <p>
 * <a href="TextLink.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TextLink.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public abstract class TextLink extends Link{
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	protected String content;
		
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	//~ Accessors ==============================================================


}
