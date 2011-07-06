/*
 * @(#)UserSecurity.java Jan 20, 2007
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security.advices;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.Painiu;
import com.painiu.core.model.Role;
import com.painiu.core.model.User;
import com.painiu.core.security.SecurityAssert;
import com.painiu.core.security.SecurityUtils;

/**
 * <p>
 * <a href="UserSecurity.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserSecurity.java 44 2010-06-12 18:40:46Z zhangsf $
 */
public class UserSecurity {	
	//~ Static fields/initializers =============================================

	public final static String ACCESS_DENIED = "Access Denied: Only administrators are allowed to modify other users.";
    protected final Log log = LogFactory.getLog(UserSecurity.class);
	
	private static UserSecurity security = new UserSecurity();
	
	public static UserSecurity getSecurity() {
		return security;
	}
	
	//~ Instance fields ========================================================
	
	//~ Constructors ===========================================================

	private UserSecurity() {}
	
	//~ Methods ================================================================

	public void editUserRole(User user) {
		if (user.getId() != null) {
			SecurityAssert.isStaff();
		}
	}
	
    /**
     * Security check for UserManager.saveUser(User)
     * 
     * @param user
     * @throws Throwable
     */
    public void saveUser(User user) {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            boolean administrator = false;
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i=0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.ADMIN_ROLE)) {
                    administrator = true;
                    break;
                }
            }

            String userId = user.getId();
            
            String currentUserId = null;
            if (auth.getPrincipal() instanceof UserDetails) {
                currentUserId = ((UserDetails) auth.getPrincipal()).getUsername();
            } else {
                currentUserId = String.valueOf(auth.getPrincipal());
            }

            if (userId == null || !userId.equals(currentUserId)) {
                AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
                // allow new users to signup - this is OK b/c Signup doesn't allow setting of roles
                boolean signupUser = resolver.isAnonymous(auth);
                if (!signupUser) {
                    if (log.isDebugEnabled()) {
                        log.debug("Verifying that '" + currentUserId + "' can modify '" + userId + "'");
                    }
                    if (!administrator) {
                        log.warn("Access Denied: '" + currentUserId + "' tried to modify '" + userId + "'!");
                        throw new AccessDeniedException(ACCESS_DENIED);
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Registering new user '" + userId + "'");
                    }
                }
            }

            // fix for http://issues.appfuse.org/browse/APF-96
            // don't allow users with "user" role to upgrade to "admin" role
            else if (userId.equalsIgnoreCase(currentUserId) && !administrator) {

                // get the list of roles the user is trying add
                Set userRoles = new HashSet();
                if (user.getRoles() != null) {
                    for (Iterator it = user.getRoles().iterator(); it.hasNext();) {
                        Role role = (Role) it.next();
                        userRoles.add(role.getName());
                    }
                }

                // get the list of roles the user currently has
                Set authorizedRoles = new HashSet();
                for (int i=0; i < roles.length; i++) {
                    authorizedRoles.add(roles[i].getAuthority());
                }

                // if they don't match - access denied
                // users aren't allowed to change their roles
                if (!CollectionUtils.isEqualCollection(userRoles, authorizedRoles)) {
                    log.warn("Access Denied: '" + currentUserId + "' tried to change their role(s)!");
                    throw new AccessDeniedException(ACCESS_DENIED);
                }
            }
        } else {
        	if (user.getId() != null) {
        		throw new AccessDeniedException(ACCESS_DENIED);
        	}
			if (log.isDebugEnabled()) {
			    log.debug("Registering new user '" + user.getId() + "'");
			}
        }
    	
    }
    
    public void removeUser() {
    	if (!SecurityUtils.isAdministrator()) {
    		log.warn("Access Denied: non administrator user tried to remove user");
    		throw new AccessDeniedException(ACCESS_DENIED);
    	}
    }
    
    public void setUserScore() {
    	if (!SecurityUtils.isStaff()) {
    		log.warn("Access Denied: non administrator/manager user tried to set user score");
    		throw new AccessDeniedException(ACCESS_DENIED);
    	}
    }
    
    public void setUserRank() {
    	if (!SecurityUtils.isStaff()) {
    		log.warn("Access Denied: non administrator/manager user tried to set user score");
    		throw new AccessDeniedException(ACCESS_DENIED);
    	}
    }
    
    public void setUserType() {
    	if (!SecurityUtils.isStaff()) {
    		log.warn("Access Denied: non administrator/manager user tried to set user score");
    		throw new AccessDeniedException(ACCESS_DENIED);
    	}
    }
    
    public void getOrSetUserLoginIP() {
    	if (!SecurityUtils.isStaff()) {
    		log.warn("Access Denied: non administrator/manager user tried to set user score");
    		throw new AccessDeniedException(ACCESS_DENIED);
    	}
    }
    
    public void mergeUser() {
    	if (!(SecurityUtils.isVip() || SecurityUtils.isStaff())) {
    		log.warn("Access Denied: non administrator/manager/vip user tried to merge user");
    		throw new AccessDeniedException(ACCESS_DENIED);
    	}
    }
	//~ Accessors ==============================================================

}
