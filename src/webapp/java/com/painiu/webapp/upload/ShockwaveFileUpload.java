/*
 * @(#)ShockwaveFileUpload.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * <p>
 * <a href="ShockwaveFileUpload.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ShockwaveFileUpload.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ShockwaveFileUpload extends ServletFileUpload {
	
	private static final String SHOCKWAVE_DATA_FIELD_NAME = "Filedata";

    /**
     * Constructs an uninitialised instance of this class. A factory must be
     * configured, using <code>setFileItemFactory()</code>, before attempting
     * to parse requests.
     *
     * @see FileUpload#FileUpload(FileItemFactory)
     */
    public ShockwaveFileUpload() {
        super();
    }


    /**
     * Constructs an instance of this class which uses the supplied factory to
     * create <code>FileItem</code> instances.
     *
     * @see FileUpload#FileUpload()
     */
    public ShockwaveFileUpload(FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }
    
    public List /* FileItem */ parseRequest(RequestContext ctx)
    throws FileUploadException {
    	if (ctx == null) {
    		throw new NullPointerException("ctx parameter");
    	}

    	ArrayList items = new ArrayList();
    	String contentType = ctx.getContentType();

    	if ((null == contentType)
    			|| (!contentType.toLowerCase().startsWith(MULTIPART))) {
    		throw new InvalidContentTypeException(
    				"the request doesn't contain a "
    				+ MULTIPART_FORM_DATA
    				+ " or "
    				+ MULTIPART_MIXED
    				+ " stream, content type header is "
    				+ contentType);
    	}
    	int requestSize = ctx.getContentLength();

    	if (requestSize == -1) {
    		throw new UnknownSizeException(
    		"the request was rejected because its size is unknown");
    	}

    	if (getSizeMax() >= 0 && requestSize > getSizeMax()) {
    		throw new SizeLimitExceededException(
    				"the request was rejected because its size (" + requestSize
    				+ ") exceeds the configured maximum (" + getSizeMax() + ")",
    				requestSize, getSizeMax());
    	}

    	String charEncoding = getHeaderEncoding();
    	if (charEncoding == null) {
    		charEncoding = ctx.getCharacterEncoding();
    	}

    	try {
    		byte[] boundary = getBoundary(contentType);
    		if (boundary == null) {
    			throw new FileUploadException(
    					"the request was rejected because "
    					+ "no multipart boundary was found");
    		}

    		InputStream input = ctx.getInputStream();

    		MultipartStream multi = new MultipartStream(input, boundary);
    		multi.setHeaderEncoding(charEncoding);

    		String fieldName = null;
    		
    		boolean nextPart = multi.skipPreamble();
    		while (nextPart) {
    			Map headers = null;
    			
    			try {
    				headers = parseHeaders(multi.readHeaders());
    			} catch (IOException e) {
    				if (SHOCKWAVE_DATA_FIELD_NAME.equals(fieldName)) {
    					break;
    				}
    				throw e;
    			}
    			fieldName = getFieldName(headers);
    			if (fieldName != null) {
    				String subContentType = getHeader(headers, CONTENT_TYPE);
    				if (subContentType != null && subContentType
    						.toLowerCase().startsWith(MULTIPART_MIXED)) {
    					// Multiple files.
    					byte[] subBoundary = getBoundary(subContentType);
    					multi.setBoundary(subBoundary);
    					boolean nextSubPart = multi.skipPreamble();
    					while (nextSubPart) {
    						headers = parseHeaders(multi.readHeaders());
    						if (getFileName(headers) != null) {
    							FileItem item =
    								createItem(headers, false);
    							OutputStream os = item.getOutputStream();
    							try {
    								multi.readBodyData(os);
    							} finally {
    								os.close();
    							}
    							items.add(item);
    						} else {
    							// Ignore anything but files inside
    							// multipart/mixed.
    							multi.discardBodyData();
    						}
    						nextSubPart = multi.readBoundary();
    					}
    					multi.setBoundary(boundary);
    				} else {
    					FileItem item = createItem(headers,
    							getFileName(headers) == null);
    					OutputStream os = item.getOutputStream();
    					try {
    						multi.readBodyData(os);
    					} finally {
    						os.close();
    					}
    					items.add(item);
    				}
    			} else {
    				// Skip this part.
    				multi.discardBodyData();
    			}
    			nextPart = multi.readBoundary();
    		}
    	} catch (IOException e) {
    		throw new FileUploadException(
    				"Processing of " + MULTIPART_FORM_DATA
    				+ " request failed. " + e.getMessage());
    	}

    	return items;
    }
    
    protected FileItem createItem(Map /* String, String */ headers,
    		boolean isFormField) throws FileUploadException {
    	String contentType = isFormField ? getHeader(headers, CONTENT_TYPE) : "image/jpg";
    	return getFileItemFactory().createItem(getFieldName(headers),
    			contentType,
    			isFormField,
    			getFileName(headers));
    }
}
