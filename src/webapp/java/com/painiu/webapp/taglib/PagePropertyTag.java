/*
 * @(#)PagePropertyTag.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.taglib;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.RequestConstants;

/**
 * <p>
 * <a href="PagePropertyTag.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PagePropertyTag.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class PagePropertyTag extends ConditionalTagSupport {

	private String propertyName;
	private String condition;

    public void setProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setCondition(String condition) {
		this.condition = condition;
	}
	
	/*
	 * @see javax.servlet.jsp.jstl.core.ConditionalTagSupport#condition()
	 */
	protected boolean condition() throws JspTagException {
		try {
            HTMLPage htmlPage = (HTMLPage) getPage();
            String propertyValue = htmlPage.getProperty(propertyName);

            if ("exists".equalsIgnoreCase(condition)) {
            	return propertyValue != null && propertyValue.trim().length() > 0;
            }
			return propertyValue == null || propertyValue.trim().length() == 0;
        }
        catch (Exception e) {
            
        }
		return false;
	}

    /**
     * Return the Page object from the PAGE scope. If this is found in REQUEST scope
     * instead, it will be moved into PAGE scope - to handle multi-level includes.
     */
    protected Page getPage() {
		Page p = (Page) pageContext.getAttribute(RequestConstants.PAGE, PageContext.PAGE_SCOPE);

        if (p == null) {
            p = (Page)pageContext.getAttribute(RequestConstants.PAGE, PageContext.REQUEST_SCOPE);
            if (p == null) {
                pageContext.removeAttribute(RequestConstants.PAGE, PageContext.PAGE_SCOPE);
            }
            else {
                pageContext.setAttribute(RequestConstants.PAGE, p, PageContext.PAGE_SCOPE);
            }
            pageContext.removeAttribute(RequestConstants.PAGE, PageContext.REQUEST_SCOPE);
        }
        return p;
    }
}
