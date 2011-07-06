/*
 * @(#)MonitoredMultiPartRequest.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * <p>
 * <a href="MonitoredMultiPartRequest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MonitoredMultiPartRequest.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MonitoredMultiPartRequest extends JakartaLazyParseMultiPartRequest {

	//~ Static fields/initializers =============================================
	
	//~ Instance fields ========================================================
	
	private ProgressListener listener;
	
	//~ Constructors ===========================================================
    
    /**
     * Creates a new request wrapper to handle multi-part data using methods adapted from commons-upload's
     * multipart classes (see class description).
     *
     * @param maxSize        maximum size post allowed
     * @param saveDir        the directory to save off the file
     * @param servletRequest the request containing the multipart
     */
    public MonitoredMultiPartRequest(HttpServletRequest request, String saveDir, int maxSize) {
    	super(request, saveDir, maxSize);
    }
    
	//~ Methods ================================================================
    
    /*
     * @see com.yupoo.webapp.upload.JakartaLazyParseMultiPartRequest#getFileItemFactory()
     */
    protected FileItemFactory getFileItemFactory() {
    	UploadMonitor monitor = new UploadMonitor(request.getSession());
    	listener = new UploadListener(monitor);
    	
    	DiskFileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
    	
    	factory.setSizeThreshold(10240); // 10k

        if (saveDir != null) {
        	factory.setRepository(new File(saveDir));
        }
        
        return factory;
    }
    
    /*
     * @see com.yupoo.webapp.upload.JakartaLazyParseMultiPartRequest#beforeParseRequest()
     */
    protected void beforeParseRequest() {
    	listener.progressStart(new ProgressEvent(this, request.getContentLength(), 0, ProgressEvent.START));
    }
    
    /*
     * @see com.yupoo.webapp.upload.JakartaLazyParseMultiPartRequest#afterParseRequest()
     */
    protected void afterParseRequest() {
    	listener.progressFinish(new ProgressEvent(this, request.getContentLength(), request.getContentLength(), ProgressEvent.FINISH));
    }
}
