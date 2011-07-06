/*
 * @(#)SchedulingAction.java Jan 8, 2007
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.painiu.scheduling.ApplicationContextAwareMethodInvokingJob;

/**
 * <p>粗略的系统任务管理Action， 察看，新建，删除任务</p>
 * <p>目前只支持调用Spring容器中的Bean的某个无参数方法的Job</b>
 * <p>
 * TODO: <br>
 * <ol>
 * <li> Validation inputs (alought only admin can call this action)</li>
 * <li> List availiable spring beans</li>
 * <li> More job types, for example: scripts job.</li>
 * </ol>
 * </p>
 * <p>
 * <a href="SchedulingAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @see ApplicationContextAwareMethodInvokingJob
 * @author Zola Zhou
 * @version $Id: SchedulingAction.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class SchedulingAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final Class JOB_CLASS = ApplicationContextAwareMethodInvokingJob.class;
	
	//~ Instance fields ========================================================

	private Scheduler scheduler;
	
	private Map triggerMap;
	
	private String group;
	private String name;
	private String description;
	private String target;
	private String method;
	private String cron;
	private long delay;
	private long interval;
	
	private JobDetail jobDetail;
	private Trigger trigger;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String list() throws Exception {
		String[] groupNames = scheduler.getTriggerGroupNames();
		
		if (groupNames != null) {
			triggerMap = new HashMap(groupNames.length);
			
			for (int i = 0; i < groupNames.length; i++) {
				String[] names = scheduler.getTriggerNames(groupNames[i]);
				
				if (names != null) {
					Map nameMap = new HashMap(names.length);
					
					for (int j = 0; j < names.length; j++) {
						Trigger trigger = scheduler.getTrigger(names[j], groupNames[i]);
						
						nameMap.put(names[j], trigger);
					}
					
					triggerMap.put(groupNames[i], nameMap);
				}
			}
		} else {
			triggerMap = new HashMap(0);
		}
		
		return SUCCESS;
	}
	
	public String addJob() throws Exception {
		if (scheduler.getJobDetail(name, group) != null) {
			saveActionError("The job name(" + name + ") in group(" + group + ") already exists");
			return INPUT;
		}
		
		// Build JobDetail instance.
		jobDetail = new JobDetail(name, group, JOB_CLASS);
		jobDetail.setDescription(description);
		
		JobDataMap dataMap = jobDetail.getJobDataMap();
		dataMap.put("beanName", target);
		dataMap.put("targetMethod", method);
		
		if (StringUtils.isBlank(cron)) {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setStartTime(new Date(System.currentTimeMillis() + delay));
			simpleTrigger.setRepeatInterval(interval);
			simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
			trigger = simpleTrigger;
		} else {
			CronTrigger cronTrigger = new CronTrigger();
			cronTrigger.setCronExpression(cron);
			
			trigger = cronTrigger;
		}
		
		trigger.setName(jobDetail.getName());
		trigger.setGroup(jobDetail.getGroup());
		trigger.setJobName(jobDetail.getName());
		trigger.setJobGroup(jobDetail.getGroup());
		
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			saveActionError("Error occurred while scheduling the job: " + e.getMessage());
			return INPUT;
		}
		
		saveMessage("Job has been scheduled.");
		
		return SUCCESS;
	}
	
	public String removeJob() throws Exception {
		try {
			scheduler.unscheduleJob(name, group);
			saveMessage("Job has been removed.");
		} catch (SchedulerException e) {
			saveMessage("Error occurred while unschduling the job: " + e.getMessage());
		}
		return SUCCESS;
	}
	
	public String viewJob() throws Exception {
		jobDetail = scheduler.getJobDetail(name, group);
		trigger = scheduler.getTrigger(name, group);
		
		return SUCCESS;
	}
	
	//~ Accessors ==============================================================

	/**
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	/**
	 * @return the triggerMap
	 */
	public Map getTriggerMap() {
		return triggerMap;
	}

	/**
	 * @return the cron
	 */
	public String getCron() {
		return cron;
	}

	/**
	 * @param cron the cron to set
	 */
	public void setCron(String cron) {
		this.cron = cron;
	}

	/**
	 * @return the delay
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the interval
	 */
	public long getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the jobDetail
	 */
	public JobDetail getJobDetail() {
		return jobDetail;
	}

	/**
	 * @return the trigger
	 */
	public Trigger getTrigger() {
		return trigger;
	}
	
}
