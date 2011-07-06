/*
 * @(#)UploadMonitor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.painiu.cache.Cache;
import com.painiu.cache.CacheManager;

/**
 * <p>
 * <a href="UploadMonitor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UploadMonitor.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UploadMonitor {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private HttpSession session;
	
	private long total;
	private long finished;
	
	private long startTime;

	//~ Constructors ===========================================================

	public UploadMonitor() {}
	
	public UploadMonitor(HttpSession session) {
		this.session = session;
	}

	//~ Methods ================================================================
	
	public void updateUploadProgress(ProgressEvent e) {
		if (ProgressEvent.PROGRESS.equals(e.getStatus())) {
			finished += e.getProgress();
		} else if (ProgressEvent.START.equals(e.getStatus())) {
			total = e.getExpected();
			startTime = System.currentTimeMillis();
		}
		
		long delta = (System.currentTimeMillis() - startTime) / 1000;
		
		CacheManager.getCache(UploadInfo.class.getName())
					.put("uploadInfo_" + session.getId(), 
							new UploadInfo(total, finished, delta, e.getStatus()));
	}
	
	public void updateProcessProgress(ProgressEvent e) {
		if (ProgressEvent.PROGRESS.equals(e.getStatus())) {
			finished += e.getProgress();
		} else if (ProgressEvent.START.equals(e.getStatus())) {
			total = e.getExpected();
			startTime = System.currentTimeMillis();
		}
		
		long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
		
		CacheManager.getCache(ProcessInfo.class.getName())
					.put("processInfo_" + session.getId(), 
							new ProcessInfo(total, finished, 
									e.getDescription(), elapsedTime, e.getStatus()));
	}
	
	public UploadInfo getUploadInfo() {
		Cache cache = CacheManager.getCache(UploadInfo.class.getName());
		HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
		return (UploadInfo) cache.get("uploadInfo_" + req.getSession().getId());
	}
	
	public ProcessInfo getProcessInfo() {
		Cache cache = CacheManager.getCache(ProcessInfo.class.getName());
		HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
		return (ProcessInfo) cache.get("processInfo_" + req.getSession().getId());
	}

	//~ Accessors ==============================================================

}
