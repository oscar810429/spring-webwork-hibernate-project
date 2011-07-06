package com.painiu.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.InviteDAO;
import com.painiu.core.dao.LookupDAO;
import com.painiu.core.service.LookupManager;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * <p><a href="LookupManagerImpl.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupManagerImpl extends BaseManager implements LookupManager, InitializingBean {
    //~ Instance fields ========================================================

    private LookupDAO lookupDAO;

    //~ Methods ================================================================

    @NonTransactional
    public void setLookupDAO(LookupDAO lookupDAO) {
        this.lookupDAO = lookupDAO;
    }
    
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(lookupDAO);
    } 
    
    /**
     * @see com.painiu.core.service.LookupManager#getAllRoles()
     */
    @Transactional(readOnly=true)
    public List getAllRoles() {
        return lookupDAO.getRoles();
    }
}
