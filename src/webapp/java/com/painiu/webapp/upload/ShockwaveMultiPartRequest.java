/*
 * @(#)ShockwaveMultiPartRequest.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * <p>
 * <a href="ShockwaveMultiPartRequest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ShockwaveMultiPartRequest.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ShockwaveMultiPartRequest extends JakartaLazyParseMultiPartRequest {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

    /**
     * Creates a new request wrapper to handle multi-part data using methods adapted from commons-upload's
     * multipart classes (see class description).
     *
     * @param maxSize        maximum size post allowed
     * @param saveDir        the directory to save off the file
     * @param servletRequest the request containing the multipart
     */
    public ShockwaveMultiPartRequest(HttpServletRequest request, String saveDir, int maxSize) {
    	super(request, saveDir, maxSize);
    }
	
	//~ Methods ================================================================

    /*
     * @see com.yupoo.webapp.upload.JakartaLazyParseMultiPartRequest#getServletFileUpload()
     */
    protected ServletFileUpload getServletFileUpload() {
    	FileItemFactory factory = getFileItemFactory();
        
        ServletFileUpload upload = new ShockwaveFileUpload(factory);
        upload.setSizeMax(maxSize);
        
        return upload;
    }
    
	//~ Accessors ==============================================================
}
