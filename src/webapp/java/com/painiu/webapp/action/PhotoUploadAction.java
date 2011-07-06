/**
 * @(#)PhotoUploadAction.java 2010-3-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
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
import com.painiu.Painiu;
import com.painiu.cache.Cache;
import com.painiu.cache.CacheManager;
//import com.painiu.core.exception.GroupPhotoExistsException;
//import com.painiu.core.exception.GroupPhotoThrottleException;
//import com.painiu.core.exception.GroupUserExistsException;
//import com.painiu.core.service.EventManager;
//import com.painiu.core.model.Album;
import com.painiu.core.model.Event;
//import com.painiu.core.model.Flowmeter;
//import com.painiu.core.model.Group;
import com.painiu.core.model.Photo;
import com.painiu.core.model.User;
import com.painiu.core.model.UserPreference;
import com.painiu.util.Blacklist;
import com.painiu.webapp.upload.UploadMonitor;
import com.painiu.webapp.upload.UploadProcessor;


/**
 * <p>
 * <a href="PhotoUploadAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoUploadAction.java 41 2010-06-10 17:30:08Z zhangsf $
 */

public class PhotoUploadAction extends BaseAction implements ServletRequestAware{
	
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(PhotoUploadAction.class);
	
//	public static final String API_CALL_KEY       = "__called_from_api_";
    public static final String UPLOADED_FILES_KEY = "__uploaded_files_";
    public static final String UPLOAD_SUCCEED_KEY = "__upload_succeed_";
    public static final String UPLOAD_ERRORS_KEY = "__upload_errors_";
	
	//~ Instance fields ========================================================

	private HttpServletRequest request = null;
	
//	private long startTime;
	
	private String ids;
	private List photos;
	private List errors;
	
//	private UserPreference preference = null;
	
	//private Flowmeter flowmeter;
	
	private String alt;
	private String ppu;
	private String purl;
	
	private String activity;
	private String forward;
	private String group_id;
	
	//private EventManager eventManager;
	
	private Event event;
	private String eventGroupID;
	
	private UploadProcessor uploadProcessor;
	
	private String callback;
	private boolean closewin = true;
	
	//private List albums;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	public String execute() throws Exception {
    	User user = getCurrentUser();
    	//flowmeter = flowmeterManager.getFlowmeter(user);
    	//albums = albumManager.getUserAlbums(user);
        return SUCCESS;
    }
	
	public String activity() throws Exception {
		User user = getCurrentUser();
    	//flowmeter = flowmeterManager.getFlowmeter(user);
    	
    	if (StringUtils.isNotEmpty(activity)) {
    		event = eventManager.getEventByAlias(Painiu.EVENT,activity);
    		if (event == null) {
    			saveError(getText("errors.movement.inValidMovement"));
    			return ERROR;
    		}
			/*if (event.getGroup()!= null) {
				eventGroupID = event.getGroup().getId();
			}*/
    	}
    	
        return SUCCESS;
	}
	
	public String simple() throws Exception {
		return SUCCESS;
	}
	
	public String widget() throws Exception {
		return SUCCESS;
	}
    
	
    public String save() throws Exception {
    	ActionContext ac = ActionContext.getContext();
    	
    	String title = request.getParameter("title");
    	String description = request.getParameter("description");
    	String tags = request.getParameter("tags");
    	
    	from = request.getParameter("from");
    	group_id = request.getParameter("group_id");
          
        String input = ("activity".equals(from) || "simple".equals(from)) ? from : null;
    	
    	//if ( Blacklist.getBlacklist().isBlacklisted(title)) {
    	title = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm");
    	//}
    	//if ( Blacklist.getBlacklist().isBlacklisted(description)) {
    	description = "";
    	//}
    	//if ( Blacklist.getBlacklist().isBlacklisted(tags)) {
    	tags = "";
    	//}
    	
        List uploadedFiles = (List) ac.get(UPLOADED_FILES_KEY);
        
    	if (uploadedFiles == null || uploadedFiles.size() == 0) {
    		if (log.isInfoEnabled()) {
    			log.info("no valid photo files uploaded.");
    		}
    		addActionError(getText("errors.upload.noValidFile"));

    		if (input != null) {
    			return input;
    		}
    		
    		if (from != null) {
    			persistMessages();
    			return redirect(from);
    		}
    		
    		return INPUT;
    	}
    	
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
    	UploadProcessor.Result result = uploadProcessor.process(request,  
    			user, preference, uploadedFiles, monitor, this);
    	
    	List<Photo> succeed = result.getSucceed();
    	List<String> failed = result.getErrors();
    	
    	sendToCollection(user, succeed);
    	
    	/////////////////////////////////////////////////////////////
    	
    	if (failed.size() > 0) {
    		for (Iterator<String> i = failed.iterator(); i.hasNext();) {
				 saveError(i.next());
			}
    	}
    	
    	if (succeed.size() == 0) {
    		if (input != null) {
    			return input;
    		}
    		if (from != null) {
    			persistMessages();
    			return redirect(from);
    		}
    		return INPUT;
    	}
    	
    	persistMessages(); // save messages to session
    	
    	ids = joinIds(succeed);
        
        String forward = request.getParameter("forward");
        if (StringUtils.isNotEmpty(forward)) {
        	if (forward.indexOf("?") != -1) {
        		forward += "&ids=" + ids;
        	} else {
        		forward += "?ids=" + ids;
        	}
        	return redirect(forward);
        }
        
    	if (succeed.size() <= 20) { // redirect to batch edit
    		return "batch";
    	}
    	
    	return SUCCESS;
    }
    
    public String widgetUpload() throws Exception {
    	ActionContext ac = ActionContext.getContext();
        List uploadedFiles = (List) ac.get(UPLOADED_FILES_KEY);
        
        String callback = request.getParameter("callback");
        
        if (uploadedFiles == null || uploadedFiles.size() == 0) {
    		if (log.isInfoEnabled()) {
    			log.info("no valid photo files uploaded.");
    		}
    		
    		photos = new ArrayList(0);
    		errors = mergeActionErrors();
    		if (errors.size() == 0) {
    			errors.add(getText("errors.upload.noValidFile"));
    		}
    		
    		saveResult();
    		return redirect(callback);
    	}
    	
        User user = getCurrentUser(); // should not be null.
        UserPreference preference = null;
        
        try {
            //preference = userManager.getUserPreference(user.getId());
        } catch (Exception e) {
            preference = new UserPreference();
        }
        
        // OK, process it...
    	UploadProcessor.Result result = uploadProcessor.process(request,  
    			user, preference, uploadedFiles, null, this);
    	
    	List<Photo> succeed = result.getSucceed();
    	List<String> failed = result.getErrors();
    	
    	sendToCollection(user, succeed);
    	
    	/////////////////////////////////////////////////////////////
    	
    	if (failed.size() > 0) {
    		for (Iterator<String> i = failed.iterator(); i.hasNext();) {
				 addActionError(i.next());
			}
    	}
    	
    	photos = succeed;
    	errors = mergeActionErrors();
    	
    	saveResult();
    	return redirect(callback);
    }
    
    private void saveResult() throws Exception {
    	Cache cache = CacheManager.getCache("upload.result");
		cache.put(getSession(true).getId(), new UploadProcessor.Result(photos, errors));
    }
    
    public String uploadResult() throws Exception {
    	Cache cache = CacheManager.getCache("upload.result");
		UploadProcessor.Result result = (UploadProcessor.Result) cache.get(getSession(true).getId());
		if (result == null) {
			photos = new ArrayList(0);
			errors = new ArrayList(0);
		} else {
			photos = result.getSucceed();
			errors = result.getErrors();
		}
		HttpServletRequest request = getRequest();
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
    
    public String apiUpload() throws Exception {
    	ActionContext ac = ActionContext.getContext();
        List uploadedFiles = (List) ac.get(UPLOADED_FILES_KEY);
        
    	if (uploadedFiles == null || uploadedFiles.size() == 0) {
    		if (log.isInfoEnabled()) {
    			log.info("no valid photo files uploaded.");
    		}
    		addActionError(getText("errors.upload.noValidFile"));
    		return NONE;
    	}
        
        User user = getCurrentUser(); // should not be null.
        UserPreference preference = null;
        
        try {
            //preference = userManager.getUserPreference(user.getId());
        } catch (Exception e) {
            preference = new UserPreference();
        }
        
        // OK, process it...
    	UploadProcessor.Result result = uploadProcessor.process(request,  
    			user, preference, uploadedFiles, null, this);
    	
    	List<Photo> succeed = result.getSucceed();
    	List<String> errors = result.getErrors();
    	
    	sendToCollection(user, succeed);
    	
    	/////////////////////////////////////////////////////////////
    	errors.addAll(0, mergeActionErrors());
    	
    	request.setAttribute(UPLOAD_SUCCEED_KEY, succeed);
    	request.setAttribute(UPLOAD_ERRORS_KEY, errors);
    	
    	return NONE;
    }
    
    private void sendToCollection(User user, List<Photo> photos) {
    	if (photos == null || photos.size() == 0) {
    		return;
    	}
    	
    	Collections.reverse(photos);
    	
    	//Album album = null;
    	
    	if (StringUtils.isNotEmpty(request.getParameter("album_id"))) {
    		try {
    			//album = albumManager.getAlbum(request.getParameter("album_id"));
    		} catch (Exception e) {
    			log.warn("I can't find album[" + request.getParameter("album_id") + "], ignore");
    		}
    	}
    	
    /*Group group = null;
    	
    	if (StringUtils.isNotEmpty(request.getParameter("group_id"))) {
    		try {
    			group = groupManager.getGroup(request.getParameter("group_id"));
    		} catch (Exception e) {
    			log.warn("I can't load group[" + request.getParameter("group_id") + "], ignore");
    		}
    	}
    	
        String activity = request.getParameter("activity");
        
        if (activity != null && group != null) {
        	try {
        		groupManager.addGroupUser(group, user);
        	} catch (GroupUserExistsException e) {
        		//  User exists in Group
        	}
        }*/
        
        // add to album
        /*if (album != null) {
        	for (Photo photo : photos) {
				album.addPhoto(photo);
			}
			try {
				albumManager.saveAlbum(album);
			} catch (Exception e) {
				log.error("Failed to add uploaded photos to " + album);
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
				addActionError(getText("errors.group.pool.send.outoflimit", args));
			}
        }*/
    }

    public void setServletRequest(HttpServletRequest request) {
	    this.request = request;	
    }
    
	public String getIds() {
		return ids;
	}
	
    //~ Utitlities ================================================================

	private static String joinIds(List photos) {
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
	
	//~ Accessors ==============================================================


	/**
	 * @return Returns the alt.
	 */
	public String getAlt() {
		return alt;
	}

	/**
	 * @param alt The alt to set.
	 */
	public void setAlt(String alt) {
		this.alt = alt;
	}

	/**
	 * @return Returns the ppu.
	 */
	public String getPpu() {
		return ppu;
	}

	/**
	 * @param ppu The ppu to set.
	 */
	public void setPpu(String ppu) {
		this.ppu = ppu;
	}

	/**
	 * @return the purl
	 */
	public String getPurl() {
		return purl;
	}

	/**
	 * @param purl the purl to set
	 */
	public void setPurl(String purl) {
		this.purl = purl;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the forward
	 */
	public String getForward() {
		return forward;
	}

	/**
	 * @param forward the forward to set
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getEventGroupID() {
		return eventGroupID;
	}

	public void setEventGroupID(String eventGroupID) {
		this.eventGroupID = eventGroupID;
	}

	/**
	 * @param uploadProcessor the uploadProcessor to set
	 */
	public void setUploadProcessor(UploadProcessor uploadProcessor) {
		this.uploadProcessor = uploadProcessor;
	}
	
	/**
	 * @return the photos
	 */
	public List getPhotos() {
		return photos;
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
