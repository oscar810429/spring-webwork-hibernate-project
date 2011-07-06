/*
 * @(#)SystemMovementAction.java 2007-8-1
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.Painiu;
//import com.painiu.core.service.GroupManager;
import com.painiu.core.model.Event;
//import com.painiu.core.model.Group;
import com.painiu.core.search.Result;
import com.painiu.webapp.action.BaseAction;

/**
 * <p>
 * <a href="SystemEventAction.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: SystemEventAction.java 41 2010-06-10 17:30:08Z zhangsf $
 */
public class SystemEventAction extends BaseAction {
	// ~ Static fields/initializers =============================================

//	private static final int PAGE_SIZE = 20;

	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

	// ~ Instance fields ========================================================

	private String id;

	private String name;
	
	private int kind = -1;

	private String alias;

	private String tags;

	private String logo;
	
	private String smallLogo;

	private String url;
	
	private int eventOrder;
	
	private int weight;

	private String description;

	private String groupId;

	private Date startDate;

	private Date endDate;

	private String startDateStr;

	private String endDateStr;

	private Event event;

	private int page = 1;

	private Result result;

	private String expiredDate;
	
	private int pageSize = 20;
	

	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String edit() throws Exception {
		assertParamExists("id", id);
		event = eventManager.getEvent(id);
		//Group group = event.getGroup();
		//if (group != null) {
		//	groupId = event.getGroup().getId();
		//}
		startDateStr = DF.format(event.getStartDate());
		endDateStr = DF.format(event.getEndDate());
		return SUCCESS;
	}
	
	public String editTheme() throws Exception {
		assertParamExists("id", id);
		event = eventManager.getEvent(id);
		startDateStr = DF.format(event.getStartDate());
		endDateStr = DF.format(event.getEndDate());
		return SUCCESS;
	}
	
	public String editCollection() throws Exception {
		assertParamExists("id", id);
		event = eventManager.getEvent(id);
		//Group group = event.getGroup();
		//if (group != null) {
			//groupId = event.getGroup().getId();
		//}
		startDateStr = DF.format(event.getStartDate());
		endDateStr = DF.format(event.getEndDate());
		return SUCCESS;
	}

	public String list() throws Exception {
		GregorianCalendar firstFlight = new GregorianCalendar(2007,Calendar.JANUARY, 1);
		Date date = firstFlight.getTime();

		// 查询2007年1月1号之后创建的活动列表
		if (kind < 0) {
			kind = Painiu.EVENT;
		}
		result = eventManager.getEventsStartAfterDate(kind,date, (page - 1)* pageSize, pageSize);
		return SUCCESS;
	}

	public String save() throws Exception {
		Date now = new Date();
		
		if ((alias == null) || ("".equals(alias))) {
			saveMessage("Saving the event fail! some attribution should not be empty!");
			return "input";
		} else if (!eventManager.aliasIsUnique(kind,alias, id)) {
			saveMessage("alias have exist!");
			return "input";
		}
		
		if ((startDate == null) || (endDate == null) || (name == null)
				|| ("".equals(name))) {
			saveMessage("Saving the event fail! some attribution should not be empty!");
			return "input";
		}
		
		endDate.setHours(23);
		endDate.setMinutes(59);
		
		if (id == null) {
			Event event = new Event();
			event.setCreatedDate(now);
			event.setModifiedDate(now);
			event.setKind(kind);
			event.setDescription(description);
			event.setStartDate(startDate);
			event.setEndDate(endDate);
			if (StringUtils.isNotEmpty(groupId)) {
				//Group group = null;
				try {
					//group = groupManager.getGroup(groupId);
				} catch (ObjectRetrievalFailureException e) {
					saveActionError("group not found: " + groupId);
					return INPUT;
				}
				
				//event.setGroup(group);
			}			
			event.setLogo(logo);
			if(StringUtils.isNotEmpty(smallLogo))
				event.setSmallLogo(smallLogo);
			event.setName(name);
			event.setAlias(alias);
			event.setTags(tags);
			event.setUrl(url);
			event.setEventOrder(eventOrder);
			event.setWeight(weight);
			eventManager.saveEvent(event);
		} else {
			Event old = eventManager.getEvent(id);
			old.setKind(kind);
			old.setDescription(description);
			old.setStartDate(startDate);
			old.setEndDate(endDate);
			old.setModifiedDate(now);
			
			if (StringUtils.isNotEmpty(groupId)) {
				//Group group = null;
				try {
					//group = groupManager.getGroup(groupId);
				} catch (ObjectRetrievalFailureException e) {
					saveActionError("group not found: " + groupId);
					return INPUT;
				}
				
				//old.setGroup(group);
			}
			old.setLogo(logo);
			if(StringUtils.isNotEmpty(smallLogo))
				old.setSmallLogo(smallLogo);
			else 
				old.setSmallLogo(null);
			old.setName(name);
			old.setAlias(alias);
			old.setTags(tags);
			old.setUrl(url);
			old.setEventOrder(eventOrder);
			old.setWeight(weight);
			eventManager.saveEvent(old);
		}
		saveMessage("Event have been saved.");
		return SUCCESS;
	}
	
	public String saveCollection() throws Exception {
		Date now = new Date();
		
		if ((startDate == null) || (endDate == null) || (name == null)
				|| ("".equals(name))) {
			saveMessage("Saving the event fail! some attribution should not be empty!");
			return INPUT;
		}
		
		endDate.setHours(23);
		endDate.setMinutes(59);
		
		if (id == null) {
			Event event = new Event();
			event.setCreatedDate(now);
			event.setModifiedDate(now);
			event.setKind(kind);
			event.setDescription(description);
			event.setStartDate(startDate);
			event.setEndDate(endDate);
			if (StringUtils.isNotEmpty(groupId)) {
				//Group group = null;
				try {
					//group = groupManager.getGroup(groupId);
				} catch (ObjectRetrievalFailureException e) {
					saveActionError("group not found: " + groupId);
					return INPUT;
				}
				
				//event.setGroup(group);
			}			
			event.setLogo(logo);
			if(StringUtils.isNotEmpty(smallLogo))
				event.setSmallLogo(smallLogo);
			event.setName(name);
			event.setAlias(name);
			event.setEventOrder(eventOrder);
			event.setWeight(weight);
			event.setTags(tags);
			event.setUrl(url);
			eventManager.saveEvent(event);
		} else {
			Event old = eventManager.getEvent(id);
			old.setKind(kind);
			old.setDescription(description);
			old.setStartDate(startDate);
			old.setEndDate(endDate);
			old.setModifiedDate(now);
			
			if (StringUtils.isNotEmpty(groupId)) {
				//Group group = null;
				try {
					//group = groupManager.getGroup(groupId);
				} catch (ObjectRetrievalFailureException e) {
					saveActionError("group not found: " + groupId);
					return INPUT;
				}
				
				//old.setGroup(group);
			}
			old.setLogo(logo);
			if(StringUtils.isNotEmpty(smallLogo))
				old.setSmallLogo(smallLogo);
			else 
				old.setSmallLogo(null);
			old.setName(name);
			old.setAlias(name);
			old.setEventOrder(eventOrder);
			old.setWeight(weight);
			old.setTags(tags);
			old.setUrl(url);
			eventManager.saveEvent(old);
		}
		saveMessage("collection have been saved.");
		return SUCCESS;
	}
	
	public String saveTheme() throws Exception {
		Date now = new Date();
		if ((startDate == null) || (endDate == null) || (name == null)
				|| ("".equals(name))) {
			saveMessage("Saving the theme fail! some attribution should not be empty!");
			return INPUT;
		}
		
		endDate.setHours(23);
		endDate.setMinutes(59);
		
		if (id == null) {
			Event event = new Event();
			event.setCreatedDate(now);
			event.setModifiedDate(now);
			event.setAlias(name);
			event.setKind(kind);
			event.setDescription(description);
			event.setStartDate(startDate);
			event.setEndDate(endDate);
				
			event.setLogo(logo);
			if(StringUtils.isNotEmpty(smallLogo))
				event.setSmallLogo(smallLogo);
			event.setName(name);
			event.setTags(tags);
			event.setUrl(url);
			event.setEventOrder(eventOrder);
			event.setWeight(weight);
			eventManager.saveEvent(event);
		} else {
			Event old = eventManager.getEvent(id);
			old.setAlias(name);
			old.setKind(kind);
			old.setDescription(description);
			old.setStartDate(startDate);
			old.setEndDate(endDate);
			old.setModifiedDate(now);
			old.setLogo(logo);
			if(StringUtils.isNotEmpty(smallLogo))
				old.setSmallLogo(smallLogo);
			else
				old.setSmallLogo(null);
			old.setName(name);
			old.setTags(tags);
			old.setUrl(url);
			old.setEventOrder(eventOrder);
			old.setWeight(weight);
			eventManager.saveEvent(old);
		}
		saveMessage("Theme have been saved.");
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		assertParamExists("id", id);
		event = eventManager.getEvent(id);
		kind = event.getKind();
		eventManager.removeEvent(event);
		saveMessage("Event have been deleted.");
		return SUCCESS;
	}

	// ~ Accessors
	// ==============================================================

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event Event) {
		this.event = Event;
	}

	/**
	 * @return Returns the page.
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            The page to set.
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return Returns the result.
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result
	 *            The result to set.
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the kind
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(int kind) {
		this.kind = kind;
	}

	/**
	 * @return the expiredDate
	 */
	public String getExpiredDate() {
		return expiredDate;
	}

	/**
	 * @param expiredDate
	 *            the expiredDate to set
	 */
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url.toLowerCase().indexOf("http://") != 0) {
			this.url = "http://" + url;
		} else {
			this.url = url;
		}
	}

	/*public GroupManager getGroupManager() {
		return groupManager;
	}

	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}*/

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
		try {
			endDate = DF.parse(endDateStr);
		} catch (ParseException e) {
			saveMessage("The format of endDate is error!");
		}
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
		try {
			startDate = DF.parse(startDateStr);
		} catch (ParseException e) {
			saveMessage("The format of startDate is error!");
		}

	}

	public int getEventOrder() {
		return eventOrder;
	}

	public void setEventOrder(int eventOrder) {
		this.eventOrder = eventOrder;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getSmallLogo() {
		return smallLogo;
	}

	public void setSmallLogo(String smallLogo) {
		this.smallLogo = smallLogo;
	}
}
