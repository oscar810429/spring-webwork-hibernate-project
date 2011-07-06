/*
 * @(#)PartnerManager.java 2007-9-6
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.model.Partner;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="PartnerManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 5jxiang
 * @version $Id: PartnerManager.java 7 2010-05-11 16:23:49Z zhangsf $
 */
public interface PartnerManager {

	public Partner getPartner(String id);
    
    public Result findPartnerList(int start, int limit);
    
    public List findPartnerList(int amount);
    
    public void save(Partner partner);
    
    public void remove(Partner partner);	
}
