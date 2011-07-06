/*
 * @(#)PhotoTagsSecurityAspect.aj Jan 19, 2007
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.aop.security;

import com.painiu.core.aop.BaseAspect;
import com.painiu.core.service.SoftwareManager;
import com.painiu.core.model.Software;
import com.painiu.core.model.SoftwareTag;
import com.painiu.core.security.advices.SoftwareTagsSecurity;

/**
 * <p>
 * <a href="PhotoTagsSecurityAspect.aj.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id$
 */
public aspect SoftwareTagsSecurityAspect extends BaseAspect {

	private SoftwareTagsSecurity security = SoftwareTagsSecurity.getSecurity();
	
	before(Software software): execution(* SoftwareManager.addSoftwareTags(..)) && args(software,..) {
		security.addSoftwareTags(software);
	}
	
	before(SoftwareTag softwareTag): execution(* SoftwareManager.removeSoftwareTag(..)) && args(softwareTag) {
		security.removeSoftwareTag(softwareTag);
	}

	before(SoftwareTag softwareTag): execution(* SoftwareManager.replaceSoftwareTag(..)) && args(softwareTag,..) {
		security.replaceSoftwareTag(softwareTag);
	}
}

