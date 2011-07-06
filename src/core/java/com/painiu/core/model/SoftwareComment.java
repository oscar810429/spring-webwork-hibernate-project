/**
 * @(#)SoftwareComment.java 2010-6-13
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

/**
 * <p>
 * <a href="SoftwareComment.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareComment.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class SoftwareComment extends Comment{

	//~ Static fields/initializers =============================================
	
	private static final long serialVersionUID = 831471852792906653L;

	//~ Instance fields ===================================================
	
	 protected Software software;

	//~ Constructors ====================================================

	//~ Methods =======================================================
	 

		/**
		 * @return the software
		 */
		public Software getSoftware() {
			return software;
		}

		/**
		 * @param software the software to set
		 */
		public void setSoftware(Software software) {
			this.software = software;
		}


	//~ Accessors ======================================================

}
