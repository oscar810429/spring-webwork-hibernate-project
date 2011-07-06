/*
 * @(#)ImageLink.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model.link;

/**
 * <p>
 * <a href="ImageLink.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageLink.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public abstract class ImageLink extends Link{
	//~ Static fields/initializers =============================================
	
	//~ Instance fields ========================================================
	protected int order;
	
	protected String src;
	
	protected String alt;
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	//~ Accessors ==============================================================

}
