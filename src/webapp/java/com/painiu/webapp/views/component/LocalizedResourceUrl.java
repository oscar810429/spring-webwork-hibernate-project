/*
 * @(#)LocalizedResourceUrl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.component;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.painiu.Painiu;

/**
 * <p>
 * <a href="LocalizedResourceUrl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LocalizedResourceUrl.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class LocalizedResourceUrl extends Component {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(LocalizedResourceUrl.class);
	
	private static Set supportedLocales;
	
	//~ Instance fields ========================================================

	private String value;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	//~ Constructors ===========================================================

	public LocalizedResourceUrl(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack);
        this.request = req;
        this.response = res;
    }
	
	//~ Methods ================================================================

	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
    public boolean start(Writer writer) {
        boolean result = super.start(writer);

        try {
            writer.write(resolveUrl(value, request, response));
        } catch (IOException e) {
        	if (log.isInfoEnabled()) {
        		log.info("Could not print out value '" + value + "'", e);
        	}
        }

        return result;
    }
	
    //*********************************************************************
    // Utility methods

    public static String resolveUrl(String url, HttpServletRequest request, HttpServletResponse response) {
    	String result = null;
    	
    	// normalize relative URLs against a context root

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
    	
    	return response.encodeURL(result);
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
