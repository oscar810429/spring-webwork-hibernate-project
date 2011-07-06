/*
 * @(#)ApplicationContextAwareMethodInvokingJob.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.scheduling;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.MethodInvoker;

/**
 * <p>
 * <a href="ApplicationContextAwareMethodInvokingJob.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApplicationContextAwareMethodInvokingJob.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApplicationContextAwareMethodInvokingJob extends QuartzJobBean {
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(ApplicationContextAwareMethodInvokingJob.class);
	
	//~ Instance fields ========================================================

	protected String beanName;
	protected String targetMethod;
	protected Object[] arguments = null;
	
	protected ApplicationContext applicationContext = null;

	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		Object bean = getTargetBean();
		MethodInvoker methodInvoker = new ArgumentConvertingMethodInvoker();
		methodInvoker.setTargetObject(bean);
		methodInvoker.setTargetMethod(getTargetMethod());
		methodInvoker.setArguments(getArguments());

		try {
			methodInvoker.prepare();
			methodInvoker.invoke();
		} catch (InvocationTargetException ex) {
			log.warn("InvocationTargetException occurred", ex.getTargetException());
			if (ex.getTargetException() instanceof JobExecutionException) {
				throw (JobExecutionException) ex.getTargetException();
			}
			Exception jobEx = (ex.getTargetException() instanceof Exception) ?
					(Exception) ex.getTargetException() : ex;
					throw new JobExecutionException(jobEx, false);
		} catch (Exception ex) {
			log.warn(ex);
			throw new JobExecutionException(ex, false);
		}
		
	}
	
	protected Object getTargetBean() {
		return applicationContext.getBean(getBeanName());
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the beanName
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @param beanName the beanName to set
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * @return the targetMethod
	 */
	public String getTargetMethod() {
		return targetMethod;
	}

	/**
	 * @param targetMethod the targetMethod to set
	 */
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

}
