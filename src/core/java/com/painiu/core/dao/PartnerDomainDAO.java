/*
 * @(#)PartnerDomainDAO.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.PartnerDomain;

/**
 * <p>
 * <a href="PartnerDomainDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PartnerDomainDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface PartnerDomainDAO extends DAO {
	public void savePartnerDomain(PartnerDomain partnerDomain);
	public PartnerDomain getPartnerDomain(String id);
	public List getPartnerDomains();
}
