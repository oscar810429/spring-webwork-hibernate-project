/*
 * @(#)PartnerDomainDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.PartnerDomainDAO;
import com.painiu.core.model.PartnerDomain;

/**
 * <p>
 * <a href="PartnerDomainDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PartnerDomainDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class PartnerDomainDAOHibernate extends BaseDAOHibernate implements
		PartnerDomainDAO {

	/*
	 * @see com.painiu.core.dao.PartnerDomainDAO#getPartnerDomain(java.lang.String)
	 */
	public PartnerDomain getPartnerDomain(String id) {
		PartnerDomain partnerDomain = (PartnerDomain) getHibernateTemplate().get(PartnerDomain.class, id);
		if (partnerDomain == null) {
			log.warn("uh oh, partner[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(PartnerDomain.class, id);
		}		
		return partnerDomain;
	}

	/*
	 * @see com.painiu.core.dao.PartnerDomainDAO#getPartnerDomains()
	 */
	public List getPartnerDomains() {
		// TODO Auto-generated method stub
		return find("from PartnerDomain", -1);
	}

	/*
	 * @see com.painiu.core.dao.PartnerDomainDAO#savePartnerDomain(com.painiu.core.model.PartnerDomain)
	 */
	public void savePartnerDomain(PartnerDomain partnerDomain) {
		getHibernateTemplate().saveOrUpdate(partnerDomain);
	}

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
}
