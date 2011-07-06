/*
 * @(#)JavascriptEscapeTextFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.text;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * <p>
 * <a href="JavascriptEscapeTextFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: JavascriptEscapeTextFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class JavascriptEscapeTextFilter implements TextFilter {

    /*
     * @see com.yupoo.webapp.views.text.TextFilter#filter(java.lang.String)
     */
    public String filter(String text) {
        return StringEscapeUtils.escapeJavaScript(text);
    }

}
