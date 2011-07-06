/*
 * @(#)PrivacyRestricted.java 2010-03-15
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security;

import com.painiu.core.model.Privacy;

/**
 * <p>
 * <a href="PrivacyRestricted.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PrivacyRestricted.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface PrivacyRestricted extends Personal {

	public Privacy getPrivacy();
}
