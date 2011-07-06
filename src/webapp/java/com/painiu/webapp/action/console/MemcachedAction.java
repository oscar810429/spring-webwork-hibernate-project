/*
 * @(#)MemcachedAction.java Feb 3, 2007
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.MemCachedClient;
import com.painiu.webapp.action.BaseAction;

/**
 * <p>
 * <a href="MemcachedAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: MemcachedAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MemcachedAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(MemcachedAction.class);
	
	//~ Instance fields ========================================================

	private Map stats;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String execute() throws Exception {
		MemCachedClient client = new MemCachedClient();
		
		stats = client.stats();
		
		return SUCCESS;
	}
	
	public String flush() throws Exception {
		MemCachedClient client = new MemCachedClient();
		
		if (log.isInfoEnabled()) {
			log.info("Flushing all cache server...");
		}
		client.flushAll();
		
		saveMessage("All cache server have been flushed.");
		
		return SUCCESS;
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the stats
	 */
	public Map getStats() {
		return stats;
	}
}
