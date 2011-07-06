package com.painiu.core.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.DAO;
import com.painiu.core.service.Manager;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * 
 * <p><a href="BaseManager.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Transactional
public class BaseManager implements Manager , InitializingBean{
    protected final Log log = LogFactory.getLog(getClass());
    protected DAO dao = null;
    
    /**
     * @see com.painiu.core.service.Manager#setDAO(com.painiu.core.dao.DAO)
     */
    @NonTransactional
    public void setDAO(DAO dao) {
        this.dao = dao;
    }
    
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dao);

	} 
    
    /**
     * @see com.painiu.core.service.Manager#getObject(java.lang.Class, java.io.Serializable)
     */
    @Transactional(readOnly=true)
    public Object getObject(Class clazz, Serializable id) {
        return dao.getObject(clazz, id);
    }
    
    /**
     * @see com.painiu.core.service.Manager#getObjects(java.lang.Class)
     */
    @Transactional(readOnly=true)
    public List getObjects(Class clazz) {
        return dao.getObjects(clazz);
    }
    
    /**
     * @see com.painiu.core.service.Manager#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject(Class clazz, Serializable id) {
        dao.removeObject(clazz, id);
    }
    
    /**
     * @see com.painiu.core.service.Manager#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        dao.saveObject(o);
    }
}
