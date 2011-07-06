/*
 * @(#)InviteDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.InviteDAO;
import com.painiu.core.model.Invite;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="InviteDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: InviteDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class InviteDAOHibernate extends BaseDAOHibernate implements InviteDAO {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(InviteDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.dao.InviteDAO#getInvite(java.lang.String)
	 */
	public Invite getInvite(String id) {
		Invite invite = (Invite) getHibernateTemplate().get(Invite.class, id);
		if (invite == null) {
			log.warn("uh oh, Invite[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(Invite.class, id);	
		}
		return invite;
	}

	/*
	 * @see com.painiu.core.dao.InviteDAO#getInvites(com.painiu.core.model.User)
	 */
	public List getInvites(User user) {
		return getReadOnlyHibernateTemplate().find(
				"from Invite invite where invite.from = ? and invite.email != null", user);
	}

	/*
	 * @see com.painiu.core.dao.InviteDAO#saveInvite(com.painiu.core.model.Invite)
	 */
	public void saveInvite(Invite invite) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + invite);
		}
		getHibernateTemplate().saveOrUpdate(invite);
	}

	/*
	 * @see com.painiu.core.dao.InviteDAO#removeInvite(com.painiu.core.model.Invite)
	 */
	public void removeInvite(Invite invite) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + invite);
		}
		getHibernateTemplate().delete(invite);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.InviteDAO#getInvites(com.painiu.core.model.User, boolean)
	 */
	public Result getInvites(User from, boolean isAcceted, int start, int limit) {
		String countSql = "select count(invite.id) from Invite invite where invite.from = ? " +
				"and invite.email != null and invite.acceptedDate " +(isAcceted?" != null ":" = null");
        String sql = "from Invite invite where invite.from = ? " +
        		"and invite.email != null and invite.acceptedDate " +(isAcceted?" != null ":" = null") +
        		" order by invite.invitedDate desc";
        return find(countSql, sql,
        		new Object[] {from,},
				new Type[] {Hibernate.entity(User.class)},
    			start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.InviteDAO#getInviteAddress(com.painiu.core.model.User, com.painiu.core.model.Relation)
	 */
	public Invite getInviteAddress(User from, Relation relation) {
		List list = findForUpdate(
				"from Invite invite where invite.from = ? and invite.relation = ? and invite.to = null and invite.email = null and invite.acceptedDate = null ",
				new Object[]{from, relation},
				new Type[]{Hibernate.entity(User.class), UserTypes.relation()});
		if(list != null && list.size() > 0) {
			return (Invite)list.get(0);
		}
		return null;
		
	}

}
