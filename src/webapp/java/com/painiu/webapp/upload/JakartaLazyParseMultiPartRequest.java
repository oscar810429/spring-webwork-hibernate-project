/*
 * @(#)JakartaLazyParseMultiPartRequest.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest;

/**
 * <p>
 * <a href="JakartaLazyParseMultiPartRequest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: JakartaLazyParseMultiPartRequest.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class JakartaLazyParseMultiPartRequest extends MultiPartRequest implements LazyParseMultipartRequest {

	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(JakartaLazyParseMultiPartRequest.class);
	
	//~ Instance fields ========================================================

    // maps parameter name -> List of FileItem objects
    private Map files = new HashMap();
    // maps parameter name -> List of param values
    private Map params = new HashMap();
    // any errors while processing this request
    private List errors = new ArrayList();
    
    protected HttpServletRequest request;
    protected String saveDir;
    protected int maxSize;
    
    private boolean parsed = false;
	
	//~ Constructors ===========================================================

	/**
     * Creates a new request wrapper to handle multi-part data using methods adapted from commons-upload's
     * multipart classes (see class description).
     *
     * @param maxSize        maximum size post allowed
     * @param saveDir        the directory to save off the file
     * @param servletRequest the request containing the multipart
     */
    public JakartaLazyParseMultiPartRequest(HttpServletRequest request, String saveDir, int maxSize) {
    	this.request = request;
    	this.saveDir = saveDir;
    	this.maxSize = maxSize;
    	
    	request.setAttribute(LAZY_PARSE_MULTIPART_REQUEST, this);
    }
    
	//~ Methods ================================================================
    
    /*
	 * @see com.yupoo.webapp.upload.LazyParseMultipartRequest#getOriginalRequest()
	 */
	public HttpServletRequest getOriginalRequest() {
		return request;
	}

	/*
	 * @see com.yupoo.webapp.upload.LazyParseMultipartRequest#isParsed()
	 */
	public boolean isParsed() {
		return parsed;
	}
    
    public void parse() throws IOException {
    	parsed = true;
    	
        ServletFileUpload upload = getServletFileUpload();
        
        // Parse the request
        try {
//        	long contentLength = request.getContentLength();
        	
        	beforeParseRequest();
//        	listener.progressStart(new ProgressEvent(this, contentLength, 0, "start"));
            List items = upload.parseRequest(request);
//            listener.progressFinish(new ProgressEvent(this, contentLength, contentLength, "finish"));
            afterParseRequest();
            
            for (int i = 0; i < items.size(); i++) {
                FileItem item = (FileItem) items.get(i);
                if (log.isDebugEnabled()) {
                	log.debug("Found item " + item.getFieldName());
                }
                if (item.isFormField()) {
                	if (log.isDebugEnabled()) {
                		log.debug("Item is a normal form field");
                	}
                    List values;
                    if (params.get(item.getFieldName()) != null) {
                        values = (List) params.get(item.getFieldName());
                    } else {
                        values = new ArrayList();
                    }

                    // note: see http://jira.opensymphony.com/browse/WW-633
                    // basically, in some cases the charset may be null, so
                    // we're just going to try to "other" method (no idea if this
                    // will work)
                    String charset = request.getCharacterEncoding();
                    if (charset != null) {
                        values.add(item.getString(charset));
                    } else {
                        values.add(item.getString());
                    }
                    params.put(item.getFieldName(), values);
                    
                } else if (item.getSize() == 0) {
                	if (log.isDebugEnabled()) {                		
                		log.debug("Item is a file upload of 0 size, ignoring");
                	}
                } else {
                	if (log.isDebugEnabled()) {
                		log.debug("Item is a file upload");
                	}

                    List values;
                    if (files.get(item.getFieldName()) != null) {
                        values = (List) files.get(item.getFieldName());
                    } else {
                        values = new ArrayList();
                    }

                    values.add(item);
                    files.put(item.getFieldName(), values);
                }
            }
        } catch (FileUploadException e) {
            log.error(e);
            e.printStackTrace();
            errors.add(e.getMessage());
        }
    }
    
    protected FileItemFactory getFileItemFactory() {
    	DiskFileItemFactory factory = new DiskFileItemFactory();
    	
    	factory.setSizeThreshold(10240); // 10k

        if (saveDir != null) {
        	factory.setRepository(new File(saveDir));
        }
        
        return factory;
    }
    
    protected ServletFileUpload getServletFileUpload() {
    	FileItemFactory factory = getFileItemFactory();
    	
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        upload.setSizeMax(maxSize);
        
        return upload;
    }
    
    protected void beforeParseRequest() {
    	// do nothing
    }
    
    protected void afterParseRequest() {
    	// do nothing
    }
    
    
	//~ MultiPartRequest methods ===========================================
    
    public Enumeration getFileParameterNames() {
        return Collections.enumeration(files.keySet());
    }

    public String[] getContentType(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List contentTypes = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            FileItem fileItem = (FileItem) items.get(i);
            contentTypes.add(fileItem.getContentType());
        }

        return (String[]) contentTypes.toArray(new String[contentTypes.size()]);
    }

    public File[] getFile(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List fileList = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            DiskFileItem fileItem = (DiskFileItem) items.get(i);
            fileList.add(fileItem.getStoreLocation());
        }

        return (File[]) fileList.toArray(new File[fileList.size()]);
    }

    /**
     * Returns the canonical name of the given file
     */
    private String getCanonicalName(String filename) {
        int forwardSlash = filename.lastIndexOf("/");
        int backwardSlash = filename.lastIndexOf("\\");
        if (forwardSlash != -1 && forwardSlash > backwardSlash) {
            filename = filename.substring(forwardSlash + 1, filename.length());
        } else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
            filename = filename.substring(backwardSlash + 1, filename.length());
        }

        return filename;
    }

    public String[] getFileNames(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List fileNames = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            DiskFileItem fileItem = (DiskFileItem) items.get(i);
            fileNames.add(getCanonicalName(fileItem.getName()));
        }

        return (String[]) fileNames.toArray(new String[fileNames.size()]);
    }

    public String[] getFilesystemName(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List fileNames = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            DiskFileItem fileItem = (DiskFileItem) items.get(i);
            fileNames.add(fileItem.getStoreLocation().getName());
        }

        return (String[]) fileNames.toArray(new String[fileNames.size()]);
    }

    public String getParameter(String name) {
        List v = (List) params.get(name);
        if (v != null && v.size() > 0) {
            return (String) v.get(0);
        }

        return null;
    }

    public Enumeration getParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    public String[] getParameterValues(String name) {
        List v = (List) params.get(name);
        if (v != null && v.size() > 0) {
            return (String[]) v.toArray(new String[v.size()]);
        }

        return null;
    }

    public List getErrors() {
        return errors;
    }

}
