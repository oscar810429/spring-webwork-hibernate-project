/**
 * @(#)BeforeRemoveSoftwareEvent.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event.software;

import com.painiu.core.model.Software;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="BeforeRemoveSoftwareEvent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: BeforeRemoveSoftwareEvent.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class BeforeRemoveSoftwareEvent extends SoftwareEvent{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================

	//~ Constructors ====================================================
	
	public BeforeRemoveSoftwareEvent() {
		
	}
	
	public BeforeRemoveSoftwareEvent(Software software) {
		super(software,software.getUser());
	}
	
	public BeforeRemoveSoftwareEvent(Software software, User user) {
		super(software, user);
	}


	//~ Methods =======================================================

	//~ Accessors ======================================================

}
