/*
 * @(#)UploadListener.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.Painiu;
/**
 * <p>
 * <a href="UploadListener.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UploadListener.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UploadListener implements ProgressListener, Serializable {
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(UploadListener.class);

	//~ Instance fields ========================================================

//	private HttpServletRequest request;
	private UploadMonitor monitor;
	
	private long totalToRead = 0;
	private long totalBytesRead = 0;
	private long bytesRead = 0;
	
	private long delay = 0;
	private long lastUpdated = 0;
	private long updateInterval = 2000;
	
//	private Cache cache = CacheManager.getCache(UploadInfo.class.getName());
	
	//~ Constructors ===========================================================

	public UploadListener(UploadMonitor monitor) {
		this.monitor = monitor;
		this.delay = Painiu.getPhotoConfig().getProgressConfig().getDelay();
		this.updateInterval = Painiu.getPhotoConfig().getProgressConfig().getUpdateInterval();
	}
	
	//~ Methods ================================================================

	public void progressFinish(ProgressEvent e) {
		updateUploadInfo(e);
	}

	public void progressStart(ProgressEvent e) {
		totalToRead = e.getExpected();
		updateUploadInfo(e);
	}

	public void progressUpdate(ProgressEvent e) {
		if (ProgressEvent.ERROR.equals(e.getStatus())) {
			updateUploadInfo(e);
		} else {
			totalBytesRead += e.getProgress();
			bytesRead += e.getProgress();
			updateUploadInfo(e);
			
			if (this.delay > 0) {
				if (log.isDebugEnabled()) {
					log.debug("progressUpdate: total=" + totalToRead + "; finished=" + totalBytesRead);
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void updateUploadInfo(ProgressEvent e) {
		if (System.currentTimeMillis() - lastUpdated >= updateInterval) {
			e.setProgress(bytesRead);
			monitor.updateUploadProgress(e);
			lastUpdated = System.currentTimeMillis();
			bytesRead = 0;
		}
	}

}
