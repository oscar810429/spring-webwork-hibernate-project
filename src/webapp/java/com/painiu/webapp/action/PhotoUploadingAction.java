/*
 * @(#)PhotoUploadingAction.java Jun 4, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.painiu.cache.Cache;
import com.painiu.cache.CacheManager;
//import com.painiu.core.exception.GroupPhotoExistsException;
//import com.painiu.core.exception.GroupPhotoThrottleException;
//import com.painiu.core.exception.GroupUserExistsException;
//import com.painiu.core.model.Album;
//import com.painiu.core.model.Flowmeter;
//import com.painiu.core.model.Group;
import com.painiu.core.model.Photo;
import com.painiu.core.model.User;
import com.painiu.core.model.UserPreference;
import com.painiu.util.URLUtils;
import com.painiu.webapp.upload.UploadAction;
import com.painiu.webapp.upload.UploadMonitor;
import com.painiu.webapp.upload.UploadProcessor;
import com.painiu.webapp.upload.UploadedFile;
import com.painiu.webapp.upload.UploadProcessor.Result;

/**
 * <p>
 * <a href="PhotoUploadingAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: PhotoUploadingAction.java 41 2010-06-10 17:30:08Z zhangsf $
 */
public class PhotoUploadingAction extends BaseAction implements ServletRequestAware, UploadAction {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(PhotoUploadingAction.class);
	
	private static final String UPLOAD_RESULT_CACHE_NAME = "upload.result";
	
	public static final String UPLOAD_RESULT_KEY          = "__upload_result_";
	public static final String RESPONSE_FORMAT_KEY        = "__upload_response_";
	public static final String RESPONSE_FORMAT_PARAM_NAME = "response_format";
	
	public enum Response { HTML, REDIRECT, JSON, XML, NONE }
	
	//~ Instance fields ========================================================
	
	protected HttpServletRequest request;
	
	//private List albums;
	private List groups;
	private List systemEvents; // system ads, to be removed
	
	private List<UploadedFile> files;
	private List<UploadedFile> invalidFiles;
	private List<UploadedFile> overflowedFiles;
	private List<String>       uploadErrors;
	
	private long maxFileLength;
	private boolean overflowed;
	
	private UploadProcessor uploadProcessor;
	
	protected String ids; // uploaded photos ids
	protected Result result;
	protected String id;
	//protected Group group;
	protected String group_id;
	
	protected String callback;
	protected String from;
	protected String next;
	private boolean closewin = true;
	protected Response response = Response.HTML;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.opensymphony.xwork.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		User user = getCurrentUser();
		if (user == null) {
			return loginRequired();
		} 
    	
   /*try {
    		groups = groupManager.getUserGroups(user);
    	} catch (DataAccessException e) {
    		log.error("DataAccessException occurred while loading groups for " + user, e);
    	}*/
    	
    	//if (eventManager != null) {
    	//	systemEvents = eventManager.getEvents(0, 3).getData();
    	//}
    	
        return SUCCESS;
	}
	
	public String upload() throws Exception {
		assertParamExists("id", id);
		User user = getCurrentUser();
		if (user == null) {
			return loginRequired();
		} 
    	
   /* try {
    		group =  groupManager.getGroup(id);;
    	} catch (DataAccessException e) {
    		log.error("DataAccessException occurred while loading groups for " + group, e);
    	}*/
    	
    	//if (eventManager != null) {
    	//	systemEvents = eventManager.getEvents(0, 3).getData();
    	//}
    	
        return SUCCESS;
	}
	
	public String simple() throws Exception {
		User user = getCurrentUser();
		if (user == null) {
			return loginRequired();
		}
		return SUCCESS;
	}
	
	public String save() throws Exception {
		if (overflowed || files == null || files.size() == 0) {
			return response();
		}
		
		doUpload();
		
		return response();
	}
	
	protected String response() throws Exception {
		if (result == null) {
			result = new Result();
		}
		
		ids = joinIds(result.getSucceed());
		saveUploadingResult();
		
		if (overflowed) {
			addActionError(getText("errors.flowmeter.overflow"));
		}
		
		if (invalidFiles != null) {
			for (UploadedFile file : invalidFiles) {
				addActionError(getText("errors.content.type.not.allowed", new String[] { file.getOriginalFilename(), file.getContentType() }));
			}
		}
		
		if (overflowedFiles != null) {
			for (UploadedFile file : overflowedFiles) {
				addActionError(getText("errors.file.too.large", new String[] { file.getOriginalFilename(), String.valueOf(file.getLength()) }));
			}
		}
		
		if (uploadErrors != null) {
			for (String error : uploadErrors) {
				addActionError(error);
			}
		}
		
		if (result.getErrors().size() > 0) {
			for (Iterator i = result.getErrors().iterator(); i.hasNext();) {
				String error = (String) i.next();
				addActionError(error);
			}
		}
		
		callback = request.getParameter("callback");
		from = request.getParameter("from");
		next = request.getParameter("next");
		if (next == null) {
			next = request.getParameter("forward");
		}
		
		if (request.getAttribute(RESPONSE_FORMAT_KEY) != null) {
			response = (Response) request.getAttribute(RESPONSE_FORMAT_KEY);
		} else if (callback != null || next != null) {
			response = Response.REDIRECT;
		} else if (request.getParameter(RESPONSE_FORMAT_PARAM_NAME) != null) {
			try {
				response = Response.valueOf(request.getParameter(RESPONSE_FORMAT_PARAM_NAME).toUpperCase());
			} catch (IllegalArgumentException e) {}
		}
		
		if (response == Response.NONE) {
			return NONE;
		}
		if (response == Response.JSON) {
			return "json";
		}
		if (response == Response.XML) {
			return "xml";
		}
		if (response == Response.REDIRECT) {
			return redirectResponse();
		}
		
		if (response == Response.HTML) {
			if (result.getSucceed().size() == 0) {
				execute();
				if ("basic".equals(request.getParameter("uploadr"))) {
					return "basic";
				}
				return INPUT;
			}
		}
		
		return SUCCESS;
	}
	
	protected String redirectResponse() throws Exception {
		persistMessages(); // save messages to session

		if (callback != null) {
			from = next = callback;
		}
		
		if (from == null) {
			from = URLUtils.getURL("/photos/upload/");
		}
		
		if (next == null) {
			next = URLUtils.getUserHomeURL(getCurrentUser());
		}
		
		if (result == null || result.getSucceed().size() == 0) {
			return redirect(from);
		}
		
		if (request.getParameter("append_ids") != null) {
			if (next.indexOf("?") != -1) {
				next += "&ids=" + ids;
        	} else {
        		next += "?ids=" + ids;
        	}
		}
		
		return redirect(next);
	}
	
	protected void doUpload() {
		UploadMonitor monitor = null;
    	
    	if (request.getParameter("monitored") != null) {
    		monitor = new UploadMonitor(request.getSession());
    	}
        
        User user = getCurrentUser(); // should not be null.
        UserPreference preference = null;
        
        try {
            //preference = userManager.getUserPreference(user.getId());
        } catch (Exception e) {
            preference = new UserPreference();
        }
        
        // OK, process it...
    	result = uploadProcessor.process(request, user, preference, files, monitor, this);
    	
    	sendToCollection(user, result.getSucceed());
	}
	
	public String uploadResult() throws Exception {
		result = getUploadingResult();
		if (result == null) {
			result = new Result(new ArrayList(0), new ArrayList(0));
		}
		
		callback = request.getParameter("callback");
		String close = request.getParameter("closewin");
		if (close != null) {
			closewin = "1".equals(close) || "true".equals(close) || "yes".equals(close);
		}

		HttpServletResponse response = getResponse();
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		
		return SUCCESS;
    }
	
	protected static Cache getUploadingResultCache() {
		return CacheManager.getCache(UPLOAD_RESULT_CACHE_NAME);
	}
	
	protected void saveUploadingResult() throws Exception {
		request.setAttribute(UPLOAD_RESULT_KEY, result);
		
    	Cache cache = getUploadingResultCache();
    	List photoIds = new ArrayList(result.getSucceed().size());
    	for (Iterator i = result.getSucceed().iterator(); i.hasNext();) {
			Photo photo = (Photo) i.next();
			photoIds.add(photo.getId());
		}
		cache.put(getSession(true).getId(), new Result(photoIds, result.getErrors()));
    }
	
	protected Result getUploadingResult() throws Exception {
		Cache cache = getUploadingResultCache();
		Result savedResult = (Result) cache.get(getSession(true).getId());
		
		if (savedResult == null) {
			return null;
		}
		
		List photos = new ArrayList(savedResult.getSucceed().size());
		for (Iterator i = savedResult.getSucceed().iterator(); i.hasNext();) {
			String id = (String) i.next();
			try {
				photos.add(photoManager.getPhoto(id));
			} catch (Exception e) {}
		}
		
		return new Result(photos, savedResult.getErrors());
	}
	
	/*protected void sendToAlbum(User user, List<Photo> photos) {
		Album album = null;
    	
    	if (StringUtils.isNotEmpty(request.getParameter("album_id"))) {
    		try {
    			album = albumManager.getAlbum(request.getParameter("album_id"));
    		} catch (Exception e) {
    			log.warn("I can't find album[" + request.getParameter("album_id") + "], ignore");
    		}
    	}
    	
    	// add to album
        if (album != null) {
        	for (Photo photo : photos) {
				album.addPhoto(photo);
			}
			try {
				albumManager.saveAlbum(album);
			} catch (Exception e) {
				log.error("Failed to add uploaded photos to " + album);
			}
        }
	}*/
	
	protected void sendToGroup(User user, List<Photo> photos) {
		//Group group = null;
    	
    	if (StringUtils.isNotEmpty(request.getParameter("group_id"))) {
    		try {
    			//group = groupManager.getGroup(request.getParameter("group_id"));
    		} catch (Exception e) {
    			log.warn("I can't load group[" + request.getParameter("group_id") + "], ignore");
    		}
    	}
    	
    	String activity = request.getParameter("activity");
        
        /*if (activity != null && group != null) {
        	try {
        		groupManager.addGroupUser(group, user);
        	} catch (GroupUserExistsException e) {
        		//  User exists in Group
        	}
        }*/
        
        // add to group
        /*if (group != null) {
        	int i = 0; //to record how many photo add group successed; 
        	for (Photo photo : photos) {
				try {
					//groupManager.addGroupPhoto(group, photo);
					i++;
				} catch (GroupPhotoExistsException e) {
					// should not happen
				} catch (GroupPhotoThrottleException e) {
					break;
				}
			}
			if (i < photos.size()) {
				String[] args = { group.getName(), String.valueOf(i), String.valueOf(photos.size() - i) };
				addUploadError(getText("errors.group.pool.send.outoflimit", args));
			}
        }*/
	}
	
	protected void sendToCollection(User user, List<Photo> photos) {
    	if (photos == null || photos.size() == 0) {
    		return;
    	}
    	
    	Collections.reverse(photos);
    	
    	//sendToAlbum(user, photos);
    	sendToGroup(user, photos);
    }
	
    //~ Utitlities ================================================================

	protected static String joinIds(List photos) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < photos.size(); i++) {
			Photo photo = (Photo) photos.get(i);
			if (i > 0) {
				sb.append(",");
			}
			sb.append(photo.getId());
		}
		return sb.toString();
	}
	
	/*******************************************************************************
	 * implementation of ServletRequestAware
	 *******************************************************************************/
	
	/*
	 * @see com.opensymphony.webwork.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	
	/*******************************************************************************
	 * implementation of UploadAction
	 *******************************************************************************/
	/*
	 * @see com.painiu.webapp.upload.UploadAction#addInvalidFile(com.painiu.core.webapp.upload.UploadedFile)
	 */
	public void addInvalidFile(UploadedFile file) {
		if (invalidFiles == null) {
			invalidFiles = new ArrayList<UploadedFile>();
		}
		invalidFiles.add(file);
	}

	/*
	 * @see com.painiu.webapp.upload.UploadAction#addOverflowedFile(com.painiu.core.webapp.upload.UploadedFile)
	 */
	public void addOverflowedFile(UploadedFile file) {
		if (overflowedFiles == null) {
			overflowedFiles = new ArrayList<UploadedFile>();
		}
		overflowedFiles.add(file);
	}

	/*
	 * @see com.painiu.webapp.upload.UploadAction#addUploadError(java.lang.String)
	 */
	public void addUploadError(String error) {
		if (uploadErrors == null) {
			uploadErrors = new ArrayList<String>();
		}
		uploadErrors.add(error);
	}

	/*
	 * @see com.painiu.webapp.upload.UploadAction#isOverflowed()
	 */
	public boolean isOverflowed() {
		return overflowed;
	}

	/*
	 * @see com.painiu.webapp.upload.UploadAction#setFlowmeter(com.painiu.core.model.Flowmeter)
	 */
	//public void setFlowmeter(Flowmeter flowmeter) {
	//	this.flowmeter = flowmeter;
	//}

	/*
	 * @see com.painiu.webapp.upload.UploadAction#setOverflowed(boolean)
	 */
	public void setOverflowed(boolean overflowed) {
		this.overflowed = overflowed;
	}

	/*
	 * @see com.painiu.webapp.upload.UploadAction#setUploadedFiles(java.util.List)
	 */
	public void setUploadedFiles(List<UploadedFile> files) {
		this.files = files;
	}

	//~ Accessors ==============================================================

	/**
	 * @param uploadProcessor the uploadProcessor to set
	 */
	public void setUploadProcessor(UploadProcessor uploadProcessor) {
		this.uploadProcessor = uploadProcessor;
	}
	
	
	/**
	 * @return the maxFileLength
	 */
	public long getMaxFileLength() {
		return maxFileLength;
	}
	
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	
	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}
	
	/**
	 * @return the albums
	 */
	//public List getAlbums() {
	//	return albums;
	//}
	
	/**
	 * @return the groups
	 */
	public List getGroups() {
		return groups;
	}
	
	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}
	
	/**
	 * @return the closewin
	 */
	public boolean isClosewin() {
		return closewin;
	}
	
	/**
	 * @return the systemEvents
	 */
	public List getSystemEvents() {
		return systemEvents;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the group_id
	 */
	public String getGroup_id() {
		return group_id;
	}

	/**
	 * @param groupId the group_id to set
	 */
	public void setGroup_id(String groupId) {
		group_id = groupId;
	}
	
	
	
}
