package com.painiu.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.RoleDAO;
import com.painiu.core.service.RoleManager;
import com.painiu.core.model.Role;

/**
 * Implementation of RoleManager interface.</p>
 * 
 * <p><a href="RoleManagerImpl.java.html"><i>View Source</i></a></p>
 * 
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Transactional
public class RoleManagerImpl extends BaseManager implements RoleManager, InitializingBean {
    private RoleDAO roleDAO;

    @NonTransactional
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(roleDAO);

	} 

    @Transactional(readOnly=true)
    public List getRoles(Role role) {
        return roleDAO.getRoles(role);
    }

    @Transactional(readOnly=true)
    public Role getRole(String rolename) {
        return roleDAO.getRole(rolename);
    }

    public void saveRole(Role role) {
    	roleDAO.saveRole(role);
    }

    public void removeRole(String rolename) {
    	roleDAO.removeRole(rolename);
    }
}
