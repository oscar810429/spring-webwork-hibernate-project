/**
 * @(#)UploadAction.java Sep 26, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.opensymphony.xwork.ActionContext;
import com.painiu.webapp.upload.UploadProcessor;

/**
 * <p>
 * <a href="UploadAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UploadAction.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class UploadAction extends BaseAction implements ServletRequestAware {

	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(UploadAction.class);
	
    public static final String UPLOADED_FILES_KEY = "__uploaded_files_";
    public static final String UPLOAD_SUCCEED_KEY = "__upload_succeed_";
    public static final String UPLOAD_ERRORS_KEY = "__upload_errors_";

	//~ Instance fields ========================================================
    
    private HttpServletRequest request = null;
	private UploadProcessor uploadProcessor;

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	 public String save() throws Exception {
	    	ActionContext ac = ActionContext.getContext();
	        List uploadedFiles = (List) ac.get(UPLOADED_FILES_KEY);
	        // OK, process it...
	    	UploadProcessor.Result result = uploadProcessor.process(request,uploadedFiles);
	    	
	    	//List<Photo> succeed = result.getSucceed();
	    	//List<String> failed = result.getErrors();
	    	
	    	return SUCCESS;
	    }
	 
	    public void setServletRequest(HttpServletRequest request) {
		    this.request = request;	
	    }

		public void setUploadProcessor(UploadProcessor uploadProcessor) {
			this.uploadProcessor = uploadProcessor;
		}
		

}
