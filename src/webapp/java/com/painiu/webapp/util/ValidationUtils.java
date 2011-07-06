/*
 * @(#)ValidationUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * <a href="ValidationUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ValidationUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ValidationUtils {
	//~ Static fields/initializers =============================================
	
	public static final Pattern EMAIL_ADDRESS_PATTERN = 
				Pattern.compile("\\b(^(\\S+@).+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|(\\.arpa)|(\\.coop)|(\\.int)|(\\.jobs)|(\\.museum)|(\\.name)|(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,2}))$)\\b");
	
	public static final Pattern USERNAME_PATTERN = Pattern.compile("^[0-9a-z-]{4,32}$");
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	private ValidationUtils() {
		
	}
	
	//~ Methods ================================================================
	
	public static boolean validUsername(String value) {
		if (value == null) {
			return false;
		}
		Matcher matcher = USERNAME_PATTERN.matcher(value);
		return matcher.matches();
	}

	public static boolean isEmail(String value) {
		Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(value);
		return matcher.matches();
	}
	
	//~ Accessors ==============================================================

}
