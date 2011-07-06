/*
 * @(#)Pager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="Pager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Pager.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class Pager extends UIBean {
	//~ Static fields/initializers =============================================
//	private static final Log log = LogFactory.getLog(Pager.class);
	
	public static final String TEMPLATE = "pager";
	
	//~ Instance fields ========================================================

	private String result = "result";
	private String url;
	
	//~ Constructors ===========================================================
    
    public Pager(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }
    
	//~ Methods ================================================================

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
    
    /**
	 * @param result The result to set.
	 */
	public void setResult(String result) {
		this.result = result;
	}
	
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	protected void evaluateExtraParams() {
		addParameter("result", findValue(result, Result.class));
        addParameter("url", url);
    }
	
}
