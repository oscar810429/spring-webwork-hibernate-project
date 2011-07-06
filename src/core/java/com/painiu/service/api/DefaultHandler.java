/*
 * @(#)DefaultHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.acegisecurity.AccessDeniedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * <a href="DefaultHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DefaultHandler.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class DefaultHandler implements Handler {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(DefaultHandler.class);
	
	//~ Instance fields ========================================================

	private Map targetMap;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/**
	 * @param targetMap The targetMap to set.
	 */
	public void setTargetMap(Map targetMap) {
		this.targetMap = targetMap;
	}

	/*
	 * @see com.yupoo.service.api.Handler#execute(com.yupoo.service.api.Call)
	 */
	public Object execute(Call call) throws ApiException {
		Target target = (Target) targetMap.get(call.getTargetName());
		
		if (target == null) {
			if (log.isWarnEnabled()) {
				log.warn("Target not found: " + call.getTargetName());
			}
			throw ApiException.METHOD_NOT_FOUND();
		}

		Method method;
		try {
			method = target.getClass().getMethod(call.getMethodName(), new Class[] {Parameters.class});
		} catch (NoSuchMethodException e) {
			if (log.isWarnEnabled()) {
				log.warn("Method[" + call.getMethodName() + "] of target[" + call.getTargetName() + "] not found");
			}
			throw ApiException.METHOD_NOT_FOUND();
		}
		
		if (log.isInfoEnabled()) {
			log.info("Executing target[" + call.getTargetName() + "] method = " + call.getMethodName());
		}
		
		try {
			return method.invoke(target, new Object[] {call.getParameters()});
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			// try to return the source exception.
			Throwable t = e.getTargetException();

            if (t instanceof ApiException) {
                throw (ApiException) t;
            } else if (t instanceof AccessDeniedException) {
            	throw ApiException.NOT_AUTHORIZED();
            }
            
            log.error(t.getMessage());
            t.printStackTrace();
            
			throw ApiException.SERVICE_UNAVAILABLE();
		}
	}

}
