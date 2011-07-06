/*
 * @(#)I18NConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * <a href="I18NConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: I18NConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class I18NConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private Set locales = new HashSet();
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public void addLocale(String locale) {
		locales.add(locale);
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the locales
	 */
	public Set getLocales() {
		return locales;
	}
	
	/**
	 * @param locales the locales to set
	 */
	public void setLocales(Set locales) {
		this.locales = locales;
	}
}
