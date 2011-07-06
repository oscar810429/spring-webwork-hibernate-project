/**
 * @(#)RemoveTagSoftwareEvent.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event.software;

import com.painiu.core.model.Software;
import com.painiu.core.model.User;
import com.painiu.event.RemoveEvent;

/**
 * <p>
 * <a href="RemoveTagSoftwareEvent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: RemoveTagSoftwareEvent.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class RemoveTagSoftwareEvent extends AddTagSoftwareEvent implements RemoveEvent{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================

	//~ Constructors ====================================================
	
	public RemoveTagSoftwareEvent(){}
	
	public RemoveTagSoftwareEvent(Software software, String tagName, User user){
        super(software,tagName,user);
	}

	//~ Methods =======================================================

	//~ Accessors ======================================================

}
