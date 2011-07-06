/*
 * @(#)SecurityContextInterceptor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.lang.reflect.Method;

import javax.servlet.ServletException;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.springframework.aop.MethodBeforeAdvice;

//import com.painiu.core.model.Application;
import com.painiu.core.model.User;
import com.painiu.core.security.UserDetail;
import com.painiu.session.Session;
import com.painiu.session.SessionManagerFactory;

/**
 * This intercepter used for setup Acegi SecurityContext. The context is
 * based on ApiContext.
 * 
 * <p>
 * <a href="SecurityContextInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SecurityContextInterceptor.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SecurityContextInterceptor implements MethodBeforeAdvice {
	//~ Static fields/initializers =============================================
	
	private static final String ACEGI_SECURITY_CONTEXT_KEY = "ACEGI_SECURITY_CONTEXT";
	
	//~ Instance fields ========================================================

    private Class contextClass = SecurityContextImpl.class;
        
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void before(Method method, Object[] args, Object target) throws Throwable {
		/*Application app = ApiContextHolder.getContext().getApplication();
		// ApiContext must be setup and application should not be null.
		if (Application.Type.YUPOO.equals(app.getType())) {
			return ;
		}*/
		
		Call call = (Call) args[0];
		String token = call.getParameters().getString("auth_token");
		
		SecurityContext context = null;
		Session session = null;
		boolean ctxFromSession = false;
		
		if (token != null) {
			session = SessionManagerFactory.getSessionManager().getSession(token);
			if (session != null) {
				context = (SecurityContext) session.getAttribute(ACEGI_SECURITY_CONTEXT_KEY);
				if (context != null) {
					ctxFromSession = true;
				}
			}
		}
		
		if (context == null) {
			context = generateNewContext();
		}
		
		if (context.getAuthentication() == null) {
			User user = ApiContextHolder.getContext().getUser();
			if (user != null) {
				context.setAuthentication(createSuccessAuthentication(user));
			}
		}
		
		if (session != null && !ctxFromSession) {
			session.setAttribute(ACEGI_SECURITY_CONTEXT_KEY, context);
		}
		
		SecurityContextHolder.setContext(context);
	}

    public SecurityContext generateNewContext() throws ServletException {
        try {
            return (SecurityContext) this.contextClass.newInstance();
        } catch (InstantiationException ie) {
            throw new ServletException(ie);
        } catch (IllegalAccessException iae) {
            throw new ServletException(iae);
        }
    }

    protected Authentication createSuccessAuthentication(User user) {
    	UserDetail detail = new UserDetail(user);
    	
    	UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
    			detail, user.getPassword(), detail.getAuthorities());
    	
    	result.setDetails(detail);
    	
    	return result;
    }
}
