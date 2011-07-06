/*
 * @(#)ServiceAction.java  2008-3-4
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.apache.commons.lang.StringUtils;

import com.painiu.core.model.Abuse;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;
import com.painiu.util.URLUtils;


/**
 * <p>
 * <a href="ServiceAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: ServiceAction.java 138 2010-11-23 10:21:39Z zhangsf $
 */
public class ServiceAction extends BaseAction {
	//~ Static fields/initializers =============================================
	
	//~ Instance fields ========================================================
	
	private Abuse abuse;
	
	private boolean done = false;
	
	private Result result;
	
	private int page = 1;
	
	private String id;
	
	private static int PAGE_SIZE = 15;
	
	private List auths;
	
	private List slides;
	
	private List urlSlides;
	
	private List blogs;
	
	private static final DateFormat TF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private String startDate;
	private String endDate;
	
	private String feedback_id;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * 
	 */
	public ServiceAction() {
	}
	
	public String execute() {
		return SUCCESS;
	}

	/**
	 * @return the abuse
	 */
	public Abuse getAbuse() {
		return abuse;
	}

	/**
	 * @param abuse the abuse to set
	 */
	public void setAbuse(Abuse abuse) {
		this.abuse = abuse;
	}

	public String create()  {
		return SUCCESS;
	}
	
	public String listAbuse() {
		result = abuseManager.findAbuseList(Abuse.ABUSE_KIND_DEFAULT, Abuse.State.ENABLE, (page-1)*PAGE_SIZE,PAGE_SIZE);
		return SUCCESS;
	}
	
	public String doneAbuse() {
		
		Abuse abuse = abuseManager.getAbuse(id);
		abuse.setState(Abuse.State.ENABLE);
		abuseManager.save(abuse);
		return SUCCESS;
	}
	
	public String deleteAbuse() {
		
		Abuse abuse = abuseManager.getAbuse(id);
		abuseManager.removeAbuse(abuse);
		return SUCCESS;
	}
	
	public String saveAbuse()  {
		
		if (!done) {
			User user = getCurrentUser();
			abuse.setUser(user);
			abuse.setEmail(user.getEmail());
			abuse.setKind(Abuse.ABUSE_KIND_DEFAULT);
			abuse.setState(Abuse.State.DISABLE);
			abuseManager.save(abuse);
		}
		saveMessage(getText("label.abuse.submitted"));
		
		done = true;
		
		return SUCCESS;
	}

	public String extendedInfo(){
		/*auths = authManager.getAuthentications(getCurrentUser());
		blogs = userBlogManager.getUserBlogs(getCurrentUser());
		slides = slideManager.getSlides(getCurrentUser(), false);
		if(slides !=null){
			urlSlides = new ArrayList();
			for (Iterator i = slides.iterator(); i.hasNext();) {
				Slide slide = (Slide)i.next();
				SlideUrl slideUrl = new SlideUrl();
				slideUrl.setSlide(slide);
				String url = null;
				User user = null;
				Album album = null;
				Group group = null;
				String []tagsArray = null;
				if (slide.getAlbumId() != null) {
					url = URLUtils.getURL("/albums/view")+"?id="+slide.getAlbumId();
					album = albumManager.getAlbum(slide.getAlbumId());
				} else if (slide.getGroupId() != null) {
					url = URLUtils.getURL("/groups/view")+"?id="+slide.getGroupId();
					group = groupManager.getGroup(slide.getGroupId());
				} else if (slide.getUserId() != null) {
					user = userManager.getUser(slide.getUserId());
					if(slide.getTags() == null) {
						if (slide.getFavorite().booleanValue()) {
							url = URLUtils.getUserURL(user, "/photos/favorites/");
						} else {
							url = URLUtils.getUserHomeURL(user);
						}
					} else {
						url = URLUtils.getUserURL(user,"/photos/tags/")+"?tag=";
						String tags = slide.getTags();
						if (tags.indexOf('+') != -1) {
							tagsArray = StringUtils.split(tags, "+");
						} else {
							tagsArray = StringUtils.split(tags, ",");
						}
					}
				} else if (slide.getTags() != null) {
					url = URLUtils.getURL("/photos/tags/")+"?tag=";
					String tags = slide.getTags();
					if (tags.indexOf('+') != -1) {
						tagsArray = StringUtils.split(tags, "+");
					} else {
						tagsArray = StringUtils.split(tags, ",");
					}
				} 				
				slideUrl.setUrl(url);
				slideUrl.setTags(tagsArray);
				slideUrl.setUser(user);
				slideUrl.setAlbum(album);
				slideUrl.setGroup(group);
				urlSlides.add(slideUrl);
			}
		}*/
		return SUCCESS;
	}
	
	public String taobaoFeedback() throws ParseException {
		Date sDate = TF.parse("2008-10-10 00:00");
		Date eDate = new Date();
		if (StringUtils.isNotEmpty(startDate))
			sDate = TF.parse(startDate);
		if (StringUtils.isNotEmpty(endDate))
			eDate = TF.parse(endDate);
		result = abuseManager.findAbuseResult(Abuse.ABUSE_KIND_TAOBAO, Abuse.State.DISABLE, sDate, eDate, (page-1)*PAGE_SIZE,PAGE_SIZE);
		return SUCCESS;
	}
	
	public String passedTaobaoFeedbackList() throws ParseException {
		Date sDate = TF.parse("2008-10-10 00:00");
		Date eDate = new Date();
		if (StringUtils.isNotEmpty(startDate))
			sDate = TF.parse(startDate);
		if (StringUtils.isNotEmpty(endDate))
			eDate = TF.parse(endDate);
		result = abuseManager.findAbuseResult(Abuse.ABUSE_KIND_TAOBAO, Abuse.State.ENABLE, sDate, eDate, (page-1)*PAGE_SIZE,PAGE_SIZE);
		return SUCCESS;
	}
	

	public String passTaobaoFeedback() throws IOException {
		assertParamExists("feedback_id", feedback_id);
		if(isAdmin()||isManager()||isCs()) {
			Abuse abuse = abuseManager.getAbuse(feedback_id);
			if(abuse != null && abuse.getState().value() == Abuse.State.DISABLE.value()) {
				abuse.setState(Abuse.State.ENABLE);
				abuseManager.save(abuse);
			}
		} else {
			throw new AccessDeniedException("Taobao feedback");
		}
		if(StringUtils.isNotEmpty(from)) {
			return redirect(from);
		}
		return SUCCESS;
	}
	
	public String unpassTaobaoFeedback() throws IOException {
		assertParamExists("feedback_id", feedback_id);
		if(isAdmin()||isManager()||isCs()) {
			Abuse abuse = abuseManager.getAbuse(feedback_id);
			if(abuse != null && abuse.getState().value() == Abuse.State.ENABLE.value()) {
				abuse.setState(Abuse.State.DISABLE);
				abuseManager.save(abuse);
			}
		} else {
			throw new AccessDeniedException("Taobao feedback");
		}
		if(StringUtils.isNotEmpty(from)) {
			return redirect(from);
		}
		return SUCCESS;
	}
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
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

	public List getAuths() {
		return auths;
	}

	public void setAuths(List auths) {
		this.auths = auths;
	}

	public List getBlogs() {
		return blogs;
	}

	public void setBlogs(List blogs) {
		this.blogs = blogs;
	}

	public List getSlides() {
		return slides;
	}

	public void setSlides(List slides) {
		this.slides = slides;
	}

	public List getUrlSlides() {
		return urlSlides;
	}

	public void setUrlSlides(List urlSlides) {
		this.urlSlides = urlSlides;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFeedback_id() {
		return feedback_id;
	}

	public void setFeedback_id(String feedback_id) {
		this.feedback_id = feedback_id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
