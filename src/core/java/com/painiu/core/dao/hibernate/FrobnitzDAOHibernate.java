/*
 * @(#)FrobnitzDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.FrobnitzDAO;
import com.painiu.core.model.Frobnitz;

/**
 * <p>
 * <a href="FrobnitzDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FrobnitzDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class FrobnitzDAOHibernate extends BaseDAOHibernate implements FrobnitzDAO {
	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(FrobnitzDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.dao.FrobnitzDAO#getFrobnitz(java.lang.String)
	 */
	public Frobnitz getFrobnitz(String id) {
		Frobnitz frobnitz = (Frobnitz) getHibernateTemplate().get(Frobnitz.class, id);
		
		if (frobnitz == null) {
			log.warn("uh, oh, frobnitz[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(Frobnitz.class, id);
		}
		
		return frobnitz;
	}

	/*
	 * @see com.painiu.core.dao.FrobnitzDAO#saveFrobnitz(com.painiu.core.model.Frobnitz)
	 */
	public void saveFrobnitz(Frobnitz frobnitz) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + frobnitz);
		}
		
		getHibernateTemplate().save(frobnitz); // no update.
		// necessary to throw a DataIntegrityViolation
		getHibernateTemplate().flush();
	}

	/*
	 * @see com.painiu.core.dao.FrobnitzDAO#removeFrobnitz(com.painiu.core.model.Frobnitz)
	 */
	public void removeFrobnitz(Frobnitz frobnitz) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + frobnitz);
		}
		
		getHibernateTemplate().delete(frobnitz);
	}

}
