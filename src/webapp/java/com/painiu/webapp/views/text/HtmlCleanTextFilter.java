/*
 * @(#)HtmlCleanTextFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.text;

import com.painiu.webapp.util.HtmlUtils;

/**
 * <p>
 * <a href="HtmlCleanTextFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: HtmlCleanTextFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class HtmlCleanTextFilter implements TextFilter {

	/*
	 * @see com.yupoo.webapp.views.text.TextFilter#filter(java.lang.String)
	 */
	public String filter(String text) {
		return HtmlUtils.cleanHTML(text);
	}

}
