/*
 * @(#)SecurityUtils.java  2009-12-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security;

import java.util.Set;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;

import com.painiu.Painiu;
import com.painiu.Context;
import com.painiu.core.model.User;
import com.painiu.core.service.UserManager;

/**
 * <p>
 * <a href="SecurityUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SecurityUtils.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SecurityUtils {
//~ Static fields/initializers =============================================
	
	private static final AuthenticationTrustResolver RESOLVER = new AuthenticationTrustResolverImpl();
	
	private static final String ANONYMOUS_PRINCIPAL = "anonymous";
	
	private static UserManager userManager = null;
	
	//~ Methods ================================================================
	
    /**
     * Convenience method to get current user.
     * 
     * @deprecated Use Context.getCurrentUser()
     * @return User
     */
    public static User getCurrentUser() {
    	return getAuthenticatedUser();
    }
    
    public static User getAuthenticatedUser() {
    	User currentUser = null;
    	
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	if (ctx.getAuthentication() != null) {
    		Authentication auth = ctx.getAuthentication();

    		if (ANONYMOUS_PRINCIPAL.equals(auth.getPrincipal())) {
				return null;
			}
    		
    		String userId = null;
    		
    		if (auth.getPrincipal() instanceof String) {
    			userId = (String) auth.getPrincipal();
    		} else if (auth.getPrincipal() instanceof UserDetail) {
    			userId = ((UserDetail) auth.getPrincipal()).getUsername();
    		}
    		
    		if (userId != null) {
                currentUser = userManager.getUser(userId);
                
                Set<String> roles = currentUser.getRoleNames();
                GrantedAuthority[] authorities = auth.getAuthorities();
                
                if (roles.size() != authorities.length) {
                	ctx.setAuthentication(updateAuthentication(auth, currentUser));
                } else {
                	int i = 0;
                	for (String roleName : roles) {
                		if (!roleName.equals(authorities[i].getAuthority())) {
                			ctx.setAuthentication(updateAuthentication(auth, currentUser));
                			break;
                		}
                		i++;
                	}
                }
    		}
    	}
    	
    	return currentUser;
    }
    
   
   /*public static void setCurrentUser(User user) {
    	User currentUser = getAuthenticatedUser();
    	if (currentUser == null || !currentUser.getId().equals(user.getId())) {
    		// You can not replace the authenticated user
    		throw new RuntimeException("You can not replace authenticated user.");
    	}
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	if (ctx.getAuthentication() != null) {
    		Authentication auth = ctx.getAuthentication();
    		if (auth.getPrincipal() instanceof String) {
    			//UserDetail detail = (UserDetail) auth.getPrincipal();
    			//detail.setUser(user);
    			ctx.setAuthentication(updateAuthentication(auth, user));
            }
    	}
    }*/
    
    
    public static Authentication updateAuthentication(Authentication auth, User user) {
    	UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(auth.getPrincipal(),
                auth.getCredentials(), new UserDetail(user).getAuthorities());
        result.setDetails(auth.getDetails());
        return result;
    }
    
    /**
     * @return true if current user is an administrator.
     */
    public static boolean isAdministrator() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.ADMIN_ROLE)) {
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    /**
     * @return true if current user is a manager.
     */
    public static boolean isManager() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.MANAGER_ROLE)) {
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    /**
     * @return true if current user is a cs.
     */
    public static boolean isCS() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.CS_ROLE)) {
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    /**
     * @return true if current user is a agent.
     */
    public static boolean isAgent() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.AGENT_ROLE)) {
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    /**
     * @return true if current user is a finance.
     */
    public static boolean isFinance() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.FINANCE_ROLE)) {
                    return true;
                }
            }
    	}
    	return false;
    }
    
    /**
     * @return true if current user is a finance.
     */
    public static boolean isAgentManager() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.AGENT_MANAGER_ROLE)) {
                    return true;
                }
            }
    	}
    	return false;
    }
    
    public static boolean isStaff() {
    	return isManager() || isAdministrator() || isCS();
    }

    /**
     * 
     * @return true if current user is an anonymous user.
     */
    public static boolean isAnonymous() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
    	return auth == null || RESOLVER.isAnonymous(auth);
    }
    
    public static boolean isCurrentUser(User user) {
    	User currentUser = Context.getContext().getCurrentUser();
    	
    	return currentUser != null && currentUser.equals(user);
    }
    
    public void setUserManager(UserManager userManager) {
    	SecurityUtils.userManager = userManager;
    }
    
    public static boolean isVipNormal() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.VIP_NORMAL_ROLE)) {
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    public static boolean isVipBusiness() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
    	
    	if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Painiu.VIP_BUSINESS_ROLE)) {
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    public static boolean isVip() {
    	return (isVipNormal() || isVipBusiness());
    }
}
