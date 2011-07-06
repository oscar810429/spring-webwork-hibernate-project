/*
 * @(#)PartnerDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;
import java.util.List;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.painiu.core.dao.PartnerDAO;
import com.painiu.core.model.Partner;
import com.painiu.core.search.Result;
import com.painiu.util.RandomGUID;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * <a href="PartnerDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PartnerDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class PartnerDAOHibernate extends BaseDAOHibernate implements PartnerDAO{

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PartnerDAO#findPartnerList(int, int)
	 */
	public Result findPartnerList(int start, int limit) {
		String countSql = "select count(p.id) from Partner p ";
        String sql = "from Partner p order by p.id ";
        return find(countSql, sql,
        		null,null,
    			start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PartnerDAO#findPartnerList()
	 */
	public List findPartnerList(int amount) {
		return find("from Partner p  order by p.id",amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PartnerDAO#getPartner(java.lang.String)
	 */
	public Partner getPartner(String id) {
		Partner partner = (Partner) getHibernateTemplate().get(Partner.class, id);
		if (partner == null) {
			log.warn("uh oh, partner[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(Partner.class, id);
		}		
		return partner;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PartnerDAO#removeCamera(com.painiu.core.dao.PartnerDAO)
	 */
	public void remove(Partner partner) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + partner);
		}
		getHibernateTemplate().delete(partner);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PartnerDAO#save(com.painiu.core.dao.PartnerDAO)
	 */
	public void save(Partner partner) {
		if(StringUtils.isEmpty(partner.getKey())){
			partner.setKey(RandomGUID.generate());
		}
		if (log.isDebugEnabled()) {
			log.debug("saving " + partner);
		}
		getHibernateTemplate().saveOrUpdate(partner);	
	}
}
