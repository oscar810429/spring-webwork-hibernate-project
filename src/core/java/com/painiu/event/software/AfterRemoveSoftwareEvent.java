/**
 * @(#)AfterRemoveSoftwareEvent.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event.software;

import com.painiu.core.model.Software;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="AfterRemoveSoftwareEvent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AfterRemoveSoftwareEvent.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class AfterRemoveSoftwareEvent extends SoftwareEvent{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================

	//~ Constructors ====================================================
	
	public AfterRemoveSoftwareEvent() {
		
	}
	
	public AfterRemoveSoftwareEvent(Software software) {
		super(software, software.getUser());
	}
	
	public AfterRemoveSoftwareEvent(Software software, User user) {
		super(software, user);
	}


	//~ Methods =======================================================

	//~ Accessors ======================================================

}
