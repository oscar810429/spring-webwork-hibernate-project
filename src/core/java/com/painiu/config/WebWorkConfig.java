/*
 * @(#)WebWorkConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * <a href="WebWorkConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: WebWorkConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class WebWorkConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private Set excludeDirectories = new HashSet();
	private Set excludeExtensions = new HashSet();
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public void addExcludeDirectory(String dir) {
		excludeDirectories.add(dir);
	}
	
	public void addExcludeExtension(String ext) {
		excludeExtensions.add(ext);
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the excludeDirectories
	 */
	public Set getExcludeDirectories() {
		return excludeDirectories;
	}
	
	/**
	 * @param excludeDirectories the excludeDirectories to set
	 */
	public void setExcludeDirectories(Set excludeDirectories) {
		this.excludeDirectories = excludeDirectories;
	}
	
	/**
	 * @return the excludeExtensions
	 */
	public Set getExcludeExtensions() {
		return excludeExtensions;
	}
	
	/**
	 * @param excludeExtensions the excludeExtensions to set
	 */
	public void setExcludeExtensions(Set excludeExtensions) {
		this.excludeExtensions = excludeExtensions;
	}
}
