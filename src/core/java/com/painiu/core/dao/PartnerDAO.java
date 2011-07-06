/*
 * @(#)Partner.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;
import com.painiu.core.model.Partner;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="Partner.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PartnerDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface PartnerDAO extends DAO{
	public Partner getPartner(String id);
    
    public Result findPartnerList(int start, int limit);
    
    public List findPartnerList(int amount);
    
    public void save(Partner partner);
    
    public void remove(Partner partner);	

}
