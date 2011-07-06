/**
 * @(#)GroupManagerAction.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.model.Category;
//import com.painiu.core.model.Group;
import com.painiu.core.model.Software;
//import com.painiu.core.model.Group.GroupState;
//import com.painiu.core.model.GroupStat;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;
import com.painiu.webapp.views.freemarker.ValidationResultAware;
import com.painiu.webapp.util.Pinying4jUtils;

/**
 * <p>
 * <a href="GroupManagerAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareManagerAction.java 137 2010-11-23 10:21:18Z zhangsf $
 */
public class SoftwareManagerAction extends BaseAction{

	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(SoftwareManagerAction.class);

	//~ Instance fields ========================================================
	
	private String id;
	private String cid;
	private List areas;
	private Result result;
	private User user;
	private String groupId;
	private String categoryName;
	private String userId;
	private String categoryid;
	private List categories;
	private Software software;
	private Category category;
	private String tags;
	

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String create_group() throws Exception {
		assertParamExists("cid", cid);
		user = getCurrentUser();
		categories = categoryManager.getSoftwareCategories();
		//areas = areaManager.getAreas(1);
		return SUCCESS;
	}
	
	public String edit_group() throws Exception {
		assertParamExists("id", id);
		user = getCurrentUser();
		//group  =groupManager.getGroup(id);
        categories = categoryManager.getSoftwareCategories();
		//areas = areaManager.getAreas(1);
		return SUCCESS;
	}
	
	public String save() throws Exception{
		
        /*if (log.isDebugEnabled()) {
			log.debug("create new group[" + group.getName() + "]");
		}
        user = getCurrentUser();
        //Set pinyinString = Pinying4jUtils.getPinyin(group.getName());
        if(group.getId()==null){
		  Category category = categoryManager.getCategory(categoryId);
		  group.setCategory(category);
		  group.setCreatedDate(new Date());
		  group.setCreator(user);
		  group.setGroupState(GroupState.ENABLE);
		  group.setUrlName(Pinying4jUtils.PingyinToString(group.getName()));
		  group.setProvince(group.getProvince());
		  group.setCity(group.getCity());
		  group.setCityTag(Pinying4jUtils.PingyinToString(group.getCity()));
          try {
			groupManager.saveGroup(group);
		  } catch (Exception e) {
			log.debug(e.getMessage());
		 }
		  List args = new ArrayList(1);
		  args.add(group.getName());
		  saveMessage(getText("messages.group.created", args));
        }else{
        	  Group old  = groupManager.getGroup(group.getId());
        	  Category category = categoryManager.getCategory(categoryId);
   		  old.setCategory(category);
   		  old.setCreatedDate(new Date());
   		  old.setCreator(user);
   		  old.setGroupTelephone(group.getGroupTelephone());
   		  old.setGroupAddress(group.getGroupAddress());
   		  old.setGroupState(GroupState.ENABLE);
   		  old.setUrlName(Pinying4jUtils.PingyinToString(group.getName()));
   		  old.setProvince(group.getProvince());
   		  old.setCity(group.getCity());
   		  old.setCityTag(Pinying4jUtils.PingyinToString(group.getCity()));
   		  old.setOwner(group.getOwner());
   		  old.setGroupRole(group.getGroupRole());
   		  old.setPostcode(group.getPostcode());
   		  old.setWebsite(group.getWebsite());
   		  old.setGroupDevice(group.getGroupDevice());
   		  old.setNickname(group.getNickname());
             try {
   			groupManager.saveGroup(old);
   		  } catch (Exception e) {
   			log.debug(e.getMessage());
   		 }
   		  List args = new ArrayList(1);
   		  args.add(group.getName());
   		  saveMessage(getText("messages.group.updated", args));
        	
        }
		
		*/
		
		return SUCCESS;
	}
	
	
	public String software_save() throws Exception{
		
        if (log.isDebugEnabled()) {
		log.debug("create new software[" + software.getName() + "]");
	    }
        
        if(software.getId()==null){
        	
        }else{
        	  Software old = softwareManager.getSoftware(software.getId());
        	  Category category = categoryManager.getCategory(categoryid);
        	  old.setCategory(category);
        	  old.setName(software.getName());
        	  old.setContent(software.getContent());
        	  old.setState(software.getState());
          try{
        	   softwareManager.saveSoftware(old);
        	   softwareManager.addSoftwareTags(old, tags);
          }catch(Exception e){
        	   log.debug(e.getMessage());
          }
        	  List args = new ArrayList(1);
       	  args.add(software.getName());
          saveMessage(getText("messages.software.updated", args));
        }
       
		
		return SUCCESS;
	}
	
	public String software_edit() throws Exception {
		assertParamExists("id", id);
		user = getCurrentUser();
		software = softwareManager.getSoftware(id);
		categories = categoryManager.getSoftwareCategories();
		category = categoryManager.getCategory(cid);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		assertParamExists("id", id);
		/*group =  groupManager.getGroup(id);
		groupManager.removeGroup(group);*/
		return SUCCESS;
	}
	
	
	/**
	 * @return the categories
	 */
	public List getCategories() {
		return categories;
	}


	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List categories) {
		this.categories = categories;
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
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the areas
	 */
	public List getAreas() {
		return areas;
	}
	/**
	 * @param areas the areas to set
	 */
	public void setAreas(List areas) {
		this.areas = areas;
	}
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*
	 * @see com.opensymphony.xwork.ActionSupport#validate()
	 */
	public void validate() {
		/*if (group != null && group.getUrlName() != null) {
			String urlName = group.getUrlName();
			try {
				Group g = groupManager.getGroupByUrlName(urlName);

				if (group.getId() == null || !group.getId().equals(g.getId())) {
					if (log.isDebugEnabled()) {
						log.debug(urlName + " has been taken, please choose another one.");
					}
					List args = new ArrayList(1);
					args.add(urlName);
					addFieldError("group.urlName", getText("errors.group.urlName.exists", args));
					saveError(getText("errors.group.urlName.exists", args));
					try {
						//selectPrivacy();
					} catch (Exception e) {
					}
				}
			} catch (ObjectRetrievalFailureException e) {
			}
		}*/
	}

	/**
	 * @return the software
	 */
	public Software getSoftware() {
		return software;
	}

	/**
	 * @param software the software to set
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the categoryid
	 */
	public String getCategoryid() {
		return categoryid;
	}

	/**
	 * @param categoryid the categoryid to set
	 */
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	
	
	
	

	//~ Accessors ==============================================================
}
