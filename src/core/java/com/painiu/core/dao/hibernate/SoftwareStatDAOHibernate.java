/**
 * @(#)SoftwareStatDAOHibernate.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import com.painiu.core.dao.SoftwareStatDAO;
import com.painiu.core.model.SoftwareStat;

/**
 * <p>
 * <a href="SoftwareStatDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareStatDAOHibernate.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class SoftwareStatDAOHibernate extends BaseDAOHibernate implements SoftwareStatDAO{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	public void saveSoftwareStat(SoftwareStat stat){
		 getHibernateTemplate().update(stat);
	}


	//~ Accessors ======================================================

}
