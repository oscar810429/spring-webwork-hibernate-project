/*
 * @(#)MultipartExtractInterceptor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.interceptor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.painiu.webapp.upload.UploadAction;
import com.painiu.webapp.upload.UploadFileValidator;
import com.painiu.webapp.upload.UploadedFile;

/**
 * <p>
 * <a href="MultipartExtractInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MultipartExtractInterceptor.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MultipartExtractInterceptor implements Interceptor {
	//~ Static fields/initializers =============================================

	protected static final Log log = LogFactory.getLog(MultipartExtractInterceptor.class);
	
	public static final String EXTRACTED_FILE_ITEMS_KEY = "fileItem_extracted_from_zip";
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#init()
	 */
	public void init() {
	}
	
	/*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#destroy()
	 */
	public void destroy() {
	}
	
	/*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ac.get(WebWorkStatics.HTTP_REQUEST);

        //inal Object action = invocation.getAction();
        final UploadAction action = (UploadAction) invocation.getAction();
        ValidationAware validation = null;

        if (action instanceof ValidationAware) {
            validation = (ValidationAware) action;
        }
        

        if (!(request instanceof MultiPartRequestWrapper)) {
            if (log.isDebugEnabled()) {
                ActionProxy proxy = invocation.getProxy();
                log.debug(getTextMessage("messages.bypass.request", new Object[]{proxy.getNamespace(), proxy.getActionName()}, ActionContext.getContext().getLocale()));
            }

            return invocation.invoke();
        }

        MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
        
        if (multiWrapper.hasErrors()) {
            for (Iterator errorIter = multiWrapper.getErrors().iterator(); errorIter.hasNext();) {
                String error = (String) errorIter.next();

                 if (validation != null) {
                    validation.addActionError(error);
                 }
                 // action.addUploadError(error);

                log.error(error);
            }
        }

        long fileLength = 0;
        
        List uploadedFiles = new ArrayList();

        // Bind allowed Files
        Enumeration fileParameterNames = multiWrapper.getFileParameterNames();
        while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
            // get the value of this input tag
            String inputName = (String) fileParameterNames.nextElement();

            // get the content type
            String[] contentType = multiWrapper.getContentTypes(inputName);

            if (isNonEmpty(contentType)) {
                // get the name of the file from the input tag
                String[] fileName = multiWrapper.getFileNames(inputName);

                if (isNonEmpty(fileName)) {
                    // Get a File object for the uploaded File
                    File[] files = multiWrapper.getFiles(inputName);
                    if (files != null) {
                        for (int i = 0; i < files.length; i++) {
                        	if (log.isDebugEnabled()) {
                        		log.debug("Current file: " + fileName[i] + ", content-type: " + contentType[i]);
                        	}
                            if (acceptFile(fileName[i], files[i], contentType[i], inputName, validation, ac.getLocale())) {
                        		if (UploadFileValidator.isZipFile(contentType[i])) {
                        			uploadedFiles.addAll(extractImagesFromZipFile(inputName, files[i], validation, ac));
                        		} else if (UploadFileValidator.isImageFile(contentType[i])) {
                        			uploadedFiles.add(new UploadedFile(fileName[i], contentType[i], files[i]));
                        		}
                            }
                            fileLength += files[i].length();
                        }
                    }
                } else {
                    log.error(getTextMessage("messages.invalid.file", new Object[]{inputName}, ActionContext.getContext().getLocale()));
                }
            } else {
                log.error(getTextMessage("messages.invalid.content.type", new Object[]{inputName}, ActionContext.getContext().getLocale()));
            }
        }
        
        //ac.put(FlowmeterInterceptor.UPLOADED_FILE_LENGTH_KEY, new Long(fileLength));
        //ac.put(UploadAction.UPLOADED_FILES_KEY, uploadedFiles);
        action.setUploadedFiles(uploadedFiles);
        //ac.put(FlowmeterInterceptor.UPLOADED_FILE_LENGTH_KEY, new Long(fileLength));

        // invoke action
        String result = invocation.invoke();
        
        // cleanup
        fileParameterNames = multiWrapper.getFileParameterNames();
        while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
            String inputValue = (String) fileParameterNames.nextElement();
            File[] file = multiWrapper.getFiles(inputValue);
            for (int index = 0; index < file.length; index++) {
                File currentFile = file[index];
                if (log.isDebugEnabled()) {
                	log.debug("Removing temp file: " + currentFile);
                }
                if ((currentFile != null) && currentFile.isFile()) {
                    currentFile.delete();
                }
            }
        }
        
        List items = (List) ac.get(EXTRACTED_FILE_ITEMS_KEY);
        if (items != null) {
        	ac.put(EXTRACTED_FILE_ITEMS_KEY, null);
        	if (log.isDebugEnabled()) {
        		log.debug("Removing " + items.size() + " files extracted from zip file");
        	}
        	for (Iterator i = items.iterator(); i.hasNext();) {
				DiskFileItem item = (DiskFileItem) i.next();
				item.delete();
			}
        }
        
        return result;
    }
	
	/**
     * Override for added functionality. Checks if the proposed file is acceptable based on contentType and size.
     *
     * @param file        - proposed upload file.
     * @param contentType - contentType of the file.
     * @param inputName   - inputName of the file.
     * @param validation  - Non-null ValidationAware if the action implements ValidationAware, allowing for better
     *                    logging.
     * @param locale
     * @return true if the proposed file is acceptable by contentType and size.
     */
    protected boolean acceptFile(String filename, File file, String contentType, String inputName, ValidationAware validation, Locale locale) {
        boolean fileIsAcceptable = false;

        // If it's null the upload failed
        if (file == null) {
            if (validation != null) {
                validation.addFieldError(inputName, "Could not upload file.");
            }

            log.error(getTextMessage("errors.uploading", new Object[]{filename}, locale));
        } else if (UploadFileValidator.isImageFile(contentType) && UploadFileValidator.isTooLarge(file.length())) {
            String errMsg = getTextMessage("errors.file.too.large", new Object[]{filename, "" + file.length()}, locale);
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            log.error(errMsg);
        } else if ((!UploadFileValidator.isImageFile(contentType)) && (!UploadFileValidator.isZipFile(contentType))) {
            String errMsg = getTextMessage("errors.content.type.not.allowed", new Object[]{filename, contentType}, locale);
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            log.error(errMsg);
        } else {
            fileIsAcceptable = true;
        }

        return fileIsAcceptable;
    }

    private static boolean isNonEmpty(Object[] objArray) {
        boolean result = false;
        for (int index = 0; index < objArray.length && !result; index++) {
            if (objArray[index] != null) {
                result = true;
            }
        }
        return result;
    }

	// todo: move this to utilities
	private static final String DEFAULT_MESSAGE = "no.message.found";
	
    protected String getTextMessage(String messageKey, Object[] args, Locale locale) {
        if (args == null || args.length == 0) {
            return LocalizedTextUtil.findText(this.getClass(), messageKey, locale);
        }
		return LocalizedTextUtil.findText(this.getClass(), messageKey, locale, DEFAULT_MESSAGE, args);
    }
    
    protected List extractImagesFromZipFile(String fieldName, File zipFile, ValidationAware validation, ActionContext ac) {
//		Map imageFiles = new HashMap();
        List imageFiles = new ArrayList();
		
		List extractedItems = new ArrayList();  

		File dir = zipFile.getParentFile();
		
		ZipFile zip = null;

		try {
			zip = new ZipFile(zipFile, "GBK");

			Enumeration e = zip.getEntries();

			while (e.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) e.nextElement();

				String filename = FilenameUtils.getName(entry.getName());
				String contentType = getContentType(ac, filename);
				
				if (!entry.isDirectory() && UploadFileValidator.isImageFile(contentType) 
						&& !UploadFileValidator.isTooLarge(entry.getSize())) {
					
					DiskFileItem item = new DiskFileItem(
							fieldName, contentType, false, filename, 0, dir);
					// We use DiskFileItem to generate temp filename, and it will delete 
					// the generated file when DiskFileItem instance is reclaimed by the 
					// garbage collector, so we put temporary put them in ActionContext to
					// avoid the deletion take place.
					extractedItems.add(item);
					
					OutputStream output = item.getOutputStream();
					try {
						IOUtils.copy(zip.getInputStream(entry), output);
					} finally {
						output.close();
					}
					
					imageFiles.add(new UploadedFile(filename, contentType, item.getStoreLocation()));
				}
			}

		} catch (IOException e) {
			log.error("IOException occurred while extracting images from uploaded zip file: ", e);
			if (validation != null) {
				validation.addActionError(getTextMessage("errors.extract.zip.file", null, ac.getLocale()));
			}
		} finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (IOException e) {
				}
			}
		}
		
		ac.put(EXTRACTED_FILE_ITEMS_KEY, extractedItems);

		return imageFiles;
	}
    
    private String getContentType(ActionContext ac, String filename) {
    	HttpServletRequest request = (HttpServletRequest) ac.get(WebWorkStatics.HTTP_REQUEST);
    	return request.getSession().getServletContext().getMimeType(filename.toLowerCase());
    }
}
