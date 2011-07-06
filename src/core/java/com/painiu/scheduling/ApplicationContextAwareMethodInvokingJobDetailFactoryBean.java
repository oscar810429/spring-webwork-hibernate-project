/*
 * @(#)ApplicationContextAwareMethodInvokingJobDetailFactoryBean.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.scheduling;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.ClassUtils;

/**
 * <p>
 * <a href="ApplicationContextAwareMethodInvokingJobDetailFactoryBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApplicationContextAwareMethodInvokingJobDetailFactoryBean.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApplicationContextAwareMethodInvokingJobDetailFactoryBean 
		implements FactoryBean, BeanNameAware, BeanClassLoaderAware, InitializingBean {

	private String name;

	private String group = Scheduler.DEFAULT_GROUP;

	private String[] jobListenerNames;

	private String beanName;
	private String targetMethod;

	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	private JobDetail jobDetail;


	/**
	 * Set the name of the job.
	 * Default is the bean name of this FactoryBean.
	 * @see org.quartz.JobDetail#setName
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the group of the job.
	 * Default is the default group of the Scheduler.
	 * @see org.quartz.JobDetail#setGroup
	 * @see org.quartz.Scheduler#DEFAULT_GROUP
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Set a list of JobListener names for this job, referring to
	 * non-global JobListeners registered with the Scheduler.
	 * <p>A JobListener name always refers to the name returned
	 * by the JobListener implementation.
	 * @see SchedulerFactoryBean#setJobListeners
	 * @see org.quartz.JobListener#getName
	 */
	public void setJobListenerNames(String[] names) {
		this.jobListenerNames = names;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	/**
	 * @param targetMethod the targetMethod to set
	 */
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	protected Class resolveClassName(String className) throws ClassNotFoundException {
		return ClassUtils.forName(className, this.beanClassLoader);
	}


	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
		// Use specific name if given, else fall back to bean name.
		String name = (this.name != null ? this.name : this.beanName);

		// Consider the concurrent flag to choose between stateful and stateless job.
		Class jobClass = ApplicationContextAwareMethodInvokingJob.class;

		// Build JobDetail instance.
		this.jobDetail = new JobDetail(name, this.group, jobClass);
//		this.jobDetail.setVolatility(false);
//		this.jobDetail.setDurability(false);

		JobDataMap dataMap = this.jobDetail.getJobDataMap();
		dataMap.put("beanName", this.beanName);
		dataMap.put("targetMethod", this.targetMethod);
		
		// Register job listener names.
		if (this.jobListenerNames != null) {
			for (int i = 0; i < this.jobListenerNames.length; i++) {
				this.jobDetail.addJobListener(this.jobListenerNames[i]);
			}
		}

		postProcessJobDetail(this.jobDetail);
	}

	/**
	 * Callback for post-processing the JobDetail to be exposed by this FactoryBean.
	 * <p>The default implementation is empty. Can be overridden in subclasses.
	 * @param jobDetail the JobDetail prepared by this FactoryBean
	 */
	protected void postProcessJobDetail(JobDetail jobDetail) {
	}


	public Object getObject() {
		return this.jobDetail;
	}

	public Class getObjectType() {
		return JobDetail.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
