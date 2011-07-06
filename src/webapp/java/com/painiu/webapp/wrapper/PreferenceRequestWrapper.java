/*
 * @(#)PreferenceRequestWrapper.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.wrapper;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;

import com.painiu.Context;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="PreferenceRequestWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PreferenceRequestWrapper.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class PreferenceRequestWrapper extends HttpServletRequestWrapper {
	//~ Instance fields ========================================================

    //~ Constructors ===========================================================

    public PreferenceRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    //~ Methods ================================================================

    /*
     * @see javax.servlet.ServletRequestWrapper#getLocale()
     */
    public Locale getLocale() {
    	Locale locale = null;
    	
    	LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
    	
    	if (localeContext != null) {
    		locale = localeContext.getLocale();
    	}
    	
    	if (locale == null) {
    		User user = Context.getContext().getCurrentUser();
    		if (user != null) {
    			locale = user.getLocale();
    		}
    	}
    	
    	if (locale == null) {
    		locale = super.getLocale();
    	}
    	
    	return locale;
    }
    
}
