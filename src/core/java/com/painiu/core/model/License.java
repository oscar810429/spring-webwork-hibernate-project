/*
 * @(#)License.java Dec 13, 2009
 * 
 * Copyright 2009 Mingda. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;


/**
 * <p>
 * <a href="License.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: License.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class License extends IntegerEnum implements Serializable  {
	
    private static final long serialVersionUID = -6324507105285025702L;
    public static final License NONE                                 = new License(0);
	public static final License ATTRIBUTION                          = new License(1);
	public static final License ATTRIBUTION_NODERIVS                 = new License(2);
	public static final License ATTRIBUTION_NONCOMMERCIAL_NODERIVS   = new License(3);
	public static final License ATTRIBUTION_NONCOMMERCIAL            = new License(4);
	public static final License ATTRIBUTION_NONCOMMERCIAL_SHAREALIKE = new License(5);
	public static final License ATTRIBUTION_SHAREALIKE               = new License(6);
    
	private License(int value) {
		super(value);
	}
	
	public static License valueOf(int value) {
		return (License) IntegerEnum.valueOf(License.class, value);
	}
}
