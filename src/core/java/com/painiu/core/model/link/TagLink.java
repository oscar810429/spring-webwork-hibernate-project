/*
 * @(#)TagLink.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model.link;

/**
 * <p>
 * <a href="TagLink.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TagLink.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class TagLink extends Link{
	//~ Static fields/initializers =============================================
	//~ Instance fields ========================================================
	protected int weight;
	
	protected int order;
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	//~ Accessors ==============================================================


}
