/*
 * @(#)I18nResourceURLTag.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.taglib;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.io.FilenameUtils;

import com.painiu.Painiu;

/**
 * <p>
 * <a href="I18nResourceURLTag.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: I18nResourceURLTag.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class I18nResourceURLTag extends BodyTagSupport {

	private static Set supportedLocales;
	
    //*********************************************************************
    // Protected state

    protected String value;                      // 'value' attribute
    
    protected boolean encode = false;

    //*********************************************************************
    // Constructor and initialization

    public I18nResourceURLTag() {
    	super();
    	init();
    }

    private void init() {
    	value = null;
    }
    
    public void setValue(String value) {
    	this.value = value;
    }
    
    public void setEncode(boolean encode) {
    	this.encode = encode;
    }


    //*********************************************************************
    // Tag logic

    // resets any parameters that might be sent
    public int doStartTag() throws JspException {
    	return EVAL_BODY_BUFFERED;
    }

    // gets the right value, encodes it, and prints or stores it
    public int doEndTag() throws JspException {
    	// add (already encoded) parameters
    	String result = resolveUrl(value, pageContext);
    	
    	if (encode) {
    		HttpServletResponse response =
    			((HttpServletResponse) pageContext.getResponse());
    		result = response.encodeURL(result);
    	}

    	try {
    		pageContext.getOut().print(result);
    	} catch (java.io.IOException ex) {
    		throw new JspTagException(ex.getMessage());
    	}
    	
    	return EVAL_PAGE;
    }

    // Releases any resources we may have (or inherit)
    public void release() {
    	init();
    }

    //*********************************************************************
    // Utility methods

    public static String resolveUrl(String url, PageContext pageContext) {
    	String result = null;
    	
    	// normalize relative URLs against a context root
    	HttpServletRequest request =
    		(HttpServletRequest) pageContext.getRequest();

    	if (url.startsWith("/"))
    		result = (request.getContextPath() + url);
    	else
    		result = url;
    	
    	Locale locale = request.getLocale();
    	
    	if (locale != null) {
    		result = FilenameUtils.removeExtension(result)
    				+ getLocaleSuffix(locale) + "."
    				+ FilenameUtils.getExtension(result);
    	}
    	
    	return result;
    }

    public static String getLocaleSuffix(Locale locale) {
    	if (supportedLocales == null) {
    		supportedLocales = Painiu.getI18nConfig().getLocales();
    	}
    	
    	String localeString = locale.toString();
    	
    	if (supportedLocales.contains(localeString)) {
    		return "_" + localeString;
    	}
    	
    	String lang = locale.getLanguage();
    	
    	for (Iterator i = supportedLocales.iterator(); i.hasNext();) {
			String string = (String) i.next();
			if (string.startsWith(lang)) {
				return "_" + string;
			}
		}
    	
    	return "";
    }
}
