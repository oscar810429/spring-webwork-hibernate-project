/*
 * @(#)CollaboratorDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.CollaboratorDAO;
import com.painiu.core.model.Collaborator;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="CollaboratorDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CollaboratorDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class CollaboratorDAOHibernate extends BaseDAOHibernate implements CollaboratorDAO {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(CollaboratorDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	/*
	 * @see com.painiu.core.dao.CollaboratorDAO#getCollaborator(java.lang.String)
	 */
	public Collaborator getCollaborator(String id) {
		Collaborator colla = (Collaborator) getHibernateTemplate().get(Collaborator.class, id);
		if (colla == null) {
			log.warn("uh, oh, collaborator[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(Collaborator.class, id);
		}
		
		return colla;
	}

	/*
	 * @see com.painiu.core.dao.CollaboratorDAO#removeCollaborator(com.painiu.core.model.Collaborator)
	 */
	public void removeCollaborator(Collaborator collaborator) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + collaborator);
		}
		
		getHibernateTemplate().delete(collaborator);
	}

	/*
	 * @see com.painiu.core.dao.CollaboratorDAO#saveCollaborator(com.painiu.core.model.Collaborator)
	 */
	public void saveCollaborator(Collaborator collaborator) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + collaborator);
		}
		
		getHibernateTemplate().saveOrUpdate(collaborator);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.CollaboratorDAO#getCollaborators(int, int)
	 */
	public Result getCollaborators(int start, int limit) {
		String countSql = "select count(c.id) from Collaborator c";
        String sql = "from Collaborator c order by c.createdDate desc";
        return find(countSql, sql,
        		null,null,
    			start, limit);
	}

}
