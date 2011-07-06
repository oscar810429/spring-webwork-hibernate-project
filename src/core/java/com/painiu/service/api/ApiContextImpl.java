/*
 * @(#)ApiContextImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;


import com.painiu.core.model.User;
import com.painiu.core.model.Application;
import com.painiu.service.api.ApiAuth;
import com.painiu.service.api.ApiContextImpl;

/**
 * <p>
 * <a href="ApiContextImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiContextImpl.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApiContextImpl implements ApiContext {

	private User user;
	private Application app;
	private ApiAuth auth;
	
	public ApiContextImpl(Application app, User user, ApiAuth auth) {
		this.app = app;
		this.user = user;
		this.auth = auth;
	}
	
	/**
	 * @return the app
	 */
	public Application getApp() {
		return app;
	}
	
	/*
	 * @see com.yupoo.service.api.ApiContext#getUser()
	 */
	public User getUser() {
		return user;
	}
	
	/*
	 * @see com.yupoo.service.api.ApiContext#getAuth()
	 */
	public ApiAuth getAuth() {
		return auth;
	}
	
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ApiContextImpl) {
			ApiContextImpl test = (ApiContextImpl) obj;

            if ((this.getAuth() == null) &&
                    (test.getAuth() == null)) {
                return true;
            }

            if ((this.getAuth() != null) &&
                    (test.getAuth() != null) &&
                    this.getAuth().equals(test.getAuth())) {
                return true;
            }
		}
		return false;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.auth == null) {
            return -1;
        }
		return this.auth.hashCode();
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());

        if (this.auth == null) {
            sb.append(": Null authentication");
        } else {
            sb.append(": Authentication: " + this.auth);
        }

        return sb.toString();
	}

}
