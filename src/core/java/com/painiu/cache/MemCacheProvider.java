/*
 * @(#)MemCacheProvider.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.SockIOPool;
import com.painiu.util.ClassLoaderUtils;

/**
 * <p>
 * <a href="MemCacheProvider.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MemCacheProvider.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class MemCacheProvider implements CacheProvider {
	
	private static final Log log = LogFactory.getLog(MemCacheProvider.class);
	
	private static final String DEFAULT_SETTINGS = "memcached.properties";
	private static Properties settings = new Properties();
	
	/* (non-Javadoc)
	 * @see com.yupoo.cache.CacheProvider#start(java.util.Properties)
	 */
	public void start(Properties properties) throws CacheException {
		try {
			settings.load(ClassLoaderUtils.getResourceAsStream(DEFAULT_SETTINGS, MemCacheProvider.class));
		} catch (IOException e) {
			log.error("IOException occurred while loading file " + DEFAULT_SETTINGS, e);
			
			return;
		}
		
		initSockIOPool("default");
		initSockIOPool("session");
	}
	
	private void initSockIOPool(String poolName) {
		String[] servers = StringUtils.split(settings.getProperty(poolName + ".servers"), " ");
		
		int initialConnections  = Integer.parseInt(settings.getProperty("initialConnections", "10"));
		int minSpareConnections = Integer.parseInt(settings.getProperty("minSpareConnctions", "5"));
		int maxSpareConnections = Integer.parseInt(settings.getProperty("maxSpareConnections", "50"));
		long maxIdleTime        = Long.parseLong(settings.getProperty("maxIdleTime", "1800000"));   // 30 minutes
		long maxBusyTime        = Long.parseLong(settings.getProperty("maxBusyTime", "300000"));    // 5 minutes
		long maintThreadSleep   = Long.parseLong(settings.getProperty("maintThreadSleep", "5000")); // 5 seconds
		int	socketTimeOut       = Integer.parseInt(settings.getProperty("socketTimeOut", "3000")); 	// 3 seconds to block on reads
		
		//int	socketConnectTO     = 1000 * 3;			// 3 seconds to block on initial connections.  If 0, then will use blocking connect (default)
		boolean failover          = false;			// turn off auto-failover in event of server down	
		//boolean nagleAlg        = false;			// turn off Nagle's algorithm on all sockets in pool	
		boolean aliveCheck        = false;			// enable health check of socket on checkout

		SockIOPool pool = SockIOPool.getInstance(poolName);
		pool.setServers( servers );

		if (settings.containsKey("weights")) {
			String[] weightsStr = StringUtils.split(settings.getProperty("weights"), ",");
			Integer[] weights = new Integer[weightsStr.length];
			for (int i = 0; i < weightsStr.length; i++) {
				weights[i] = new Integer(weightsStr[i]);
			}
			pool.setWeights( weights );	
		}
		
		pool.setInitConn( initialConnections );
		pool.setMinConn( minSpareConnections );
		pool.setMaxConn( maxSpareConnections );
		pool.setMaxIdle( maxIdleTime );
		pool.setMaxBusyTime( maxBusyTime );
		pool.setMaintSleep( maintThreadSleep );
		pool.setSocketTO( socketTimeOut );
		pool.setFailover( failover );
		//pool.setNagle( nagleAlg );
		//pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH );
		pool.setAliveCheck( aliveCheck );
		pool.initialize();	
	}

	/* (non-Javadoc)
	 * @see com.yupoo.cache.CacheProvider#stop()
	 */
	public void stop() {
		SockIOPool.getInstance("default").shutDown();
		SockIOPool.getInstance("session").shutDown();
	}

	/* (non-Javadoc)
	 * @see com.yupoo.cache.CacheProvider#buildCache(java.lang.String, java.util.Properties)
	 */
	public Cache buildCache(String regionName, Properties properties) throws CacheException {
		long timeToLiveSeconds = Long.parseLong(settings.getProperty("cache." + regionName + ".timeToLiveSeconds", "0"));
		boolean addPrefix = Boolean.parseBoolean(settings.getProperty("cache." + regionName + ".addPrefix", "false"));
		String poolName = properties != null ? properties.getProperty("poolName") : "default";
		return new MemCache(regionName, poolName, timeToLiveSeconds, addPrefix);
	}
}
