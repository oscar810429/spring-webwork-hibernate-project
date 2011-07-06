/**
 * @(#)SoftwareTagsSecurity.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security.advices;

import org.acegisecurity.AccessDeniedException;

import com.painiu.Context;
import com.painiu.core.model.Software;
import com.painiu.core.model.SoftwareTag;
import com.painiu.core.security.SecurityAssert;
import com.painiu.core.security.SecurityUtils;

/**
 * <p>
 * <a href="SoftwareTagsSecurity.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareTagsSecurity.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class SoftwareTagsSecurity {

	//~ Static fields/initializers =============================================

	private static SoftwareTagsSecurity security = new SoftwareTagsSecurity();
	
	public static SoftwareTagsSecurity getSecurity() {
		return security;
	}

	//~ Instance fields ===================================================

	//~ Constructors ====================================================
	
	public SoftwareTagsSecurity(){}

	//~ Methods =======================================================
	
	public void addSoftwareTags(Software software) {
		SecurityAssert.currentUserExists();
		
		if (SecurityUtils.isStaff() || SecurityUtils.isCurrentUser(software.getUser())) {
			// the calling user is the owner of the photo, and owner of photo 
			// can add/remove any tags of the photo, even it is not tagged by
			// him/her.	
			return ;
		}
		
		SecurityAssert.grant(software.getPermission().getTag(), 
				"You have no permission to add tag on the photo");
	}
	
	public void removeSoftwareTag(SoftwareTag softwareTag) {
		SecurityAssert.currentUserExists();
		
		if (!SecurityUtils.isStaff() && !SecurityUtils.isCurrentUser(softwareTag.getSoftware().getUser())) {			
			if (!softwareTag.getUserId().equals(Context.getContext().getCurrentUser().getId())) { // only the author of the tag can remove it now. 
				throw new AccessDeniedException("You have not permission to delete the tag");
			}
		}
	}

	public void replaceSoftwareTag(SoftwareTag softwareTag) {
		SecurityAssert.currentUserExists();
		
		if (!SecurityUtils.isStaff()) {
			SecurityAssert.isOwner(softwareTag.getSoftware());
		}
	}

	//~ Accessors ======================================================

}
