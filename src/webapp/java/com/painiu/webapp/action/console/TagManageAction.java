/**
 * @(#)TagManageAction.java 2010-6-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.painiu.core.service.LinkManager;
import com.painiu.core.service.TagsManager;
import com.painiu.core.model.Tag;
import com.painiu.core.model.link.TagLink;
import com.painiu.core.search.Result;
import com.painiu.util.URLUtils;
import com.painiu.webapp.action.BaseAction;

/**
 * <p>
 * <a href="TagManageAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TagManageAction.java 138 2010-11-23 10:21:39Z zhangsf $
 */

public class TagManageAction extends BaseAction{

	//~ Static fields/initializers =============================================
	private static final long ONE_DAY = 1000 * 60 * 60 * 24;

	private static final DateFormat TF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

	//~ Instance fields ===================================================
	
	public TagsManager tagsManager;

	public LinkManager linkManager;

	private int kind = -100;

	private int page = 1;
	
	private int averageCount = 1;

	private int pagesize = 20;

	private int size = 200;
	
	private int usedTimes = 100;

	private String id;

	private String startDate;

	private String endDate;

	private Result tags;

	private TagLink tag;

	private List popularTags;

	private String[] ids;

	private String tagId;

	private Integer weight;

	private Integer order;

	private Integer NewWindow;

	private Integer tagKind;

	private int tokind = -100;
	
	private int maxTimes = 0;
	
	private int minTimes = 0;
	
	private int levelTimes;

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	public String list() {
		if (kind > 0 && kind < 6) {
			tags = linkManager.getTagResult(kind, (page - 1) * pagesize,
					pagesize);
		}
		return SUCCESS;
	}

	public String search() throws Exception {
		if (StringUtils.isNotEmpty(startDate)) {
			Date sDate = TF.parse(startDate);
			Date[] dates = parseDates(startDate.substring(0, 10));
			Date eDate = null;

			if (StringUtils.isNotEmpty(endDate)) {
				eDate = TF.parse(endDate);
			} else {
				eDate = dates[1];
			}
			popularTags = tagsManager.getPopularTags(sDate, eDate, size,usedTimes);
			
		} else {
			popularTags = tagsManager.getPopularTags(size,usedTimes);
		}
		filterTags(popularTags);
		averageCount = getTagCount(popularTags) / size;
		if (averageCount <=0 ) {
			averageCount = 1;
		}
		if (minTimes <= 0) {
			minTimes = 3;
		}
		if (maxTimes <= 23) {
			maxTimes = 23;
		}
		levelTimes = (maxTimes - minTimes ) /20;
		return SUCCESS;
	}

	public String edit() {
		if (StringUtils.isNotEmpty(id)) {
			tag = linkManager.getTag(id);
		}
		return SUCCESS;
	}

	public String add() throws Exception {
		if (ids != null && ids.length > 0 && tokind > 0 && tokind < 6) {
			Date date = new Date();
			for (int i = 0; i < ids.length; i++) {
				try {
					int index = ids[i].indexOf('/');
					if(index <= 0) {
						continue;
					}
					TagLink tag = linkManager.getTagByTitle(tokind, ids[i].substring(index + 1));
					if(tag == null) {
						tag = new TagLink();
						tag.setCreatedDate(date);
						tag.setModifiedDate(date);
						tag.setKind(tokind);
						tag.setOrder(i + 1);
						tag.setTitle(ids[i].substring(index + 1));
					}	
					tag.setWeight( Integer.parseInt( ids[i].substring(0,index) ) );
					linkManager.saveLink(tag);
					} catch (Exception e) {
				}
			}
		}
		saveMessage("The tags have been added.");
		if (from != null) {
			return redirect(from);
		}

		return SUCCESS;
	}

	public String delete() throws Exception {
		assertParamExists("id", id);
		tag = linkManager.getTag(id);
		kind = tag.getKind();
		linkManager.removeLink(tag);
		saveMessage("The tag has been deleted.");
		if (kind > 0 && kind < 6) {
			tags = linkManager.getTagResult(kind, (page - 1) * pagesize,
					pagesize);
		}
		return SUCCESS;
	}
	
	public String clear() throws Exception {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				try {
					assertParamExists("id", ids[i]);
					tag = linkManager.getTag(ids[i]);
					linkManager.removeLink(tag);
					} catch (Exception e) {
						saveMessage("Clear tags faild!");
						return INPUT;
				}
			}
		}
		saveMessage("The tags have been clear.");
		return redirect(URLUtils.getURL("/console/tags/tags?kind="+kind));
	}

	public String save() throws Exception {
		Date now = new Date();
		if (tag.getId() == null) {
			tag.setCreatedDate(now);
			tag.setModifiedDate(now);
			if (linkManager.getTagAmountByTitle(tag.getKind(), tag.getTitle()) > 0) {
				addActionMessage("Save tag faild, because the tag named "
						+ tag.getTitle() + " has been existent.");
				return INPUT;
			}
			linkManager.saveLink(tag);
		} else {
			TagLink old = linkManager.getTag(tag.getId());
			old.setModifiedDate(now);
			old.setKind(tag.getKind());
			old.setExpiredDate(tag.getExpiredDate());
			old.setWeight(tag.getWeight());
			old.setOrder(tag.getOrder());
			old.setUrlBlank(tag.getUrlBlank());
			old.setTitle(tag.getTitle());
			old.setUrl(tag.getUrl());
			linkManager.saveLink(old);
		}
		addActionMessage("The tag has been saved.");
		kind = tag.getKind();
		if (kind > 0 && kind < 6) {
			tags = linkManager.getTagResult(kind, (page - 1) * pagesize, pagesize);
		}
		return SUCCESS;
	}

	public String saveTags() throws Exception {
		Date now = new Date();
		HttpServletRequest request = getRequest();
		if (kind > 0 && kind < 6) {
			tags = linkManager.getTagResult(kind, (page - 1) * pagesize, pagesize);
			for (int i = 0; i < tags.getData().size(); i++) {
				String id = ((TagLink) (tags.getData().get(i))).getId();
				String tagId = request.getParameter("tagId_" + id);
				TagLink old = linkManager.getTag(tagId);
				if (old != null) {
					try {
						old.setOrder(Integer.parseInt(request.getParameter("order_" + id)));
					} catch (NumberFormatException e) {
						// ignore
					}
					try {
						old.setWeight(Integer.parseInt(request.getParameter("weight_" + id)));
					} catch (NumberFormatException e) {
						
					}
					try {
						old.setUrlBlank(Integer.parseInt(request.getParameter("newWindow_" + id)));
					} catch (NumberFormatException e) {
						
					}
					old.setModifiedDate(now);
					linkManager.saveLink(old);
				}
			}
			saveMessage("The tags has been saved.");
			return redirect("/console/tags/tags?kind="+kind+"&page="+page+"&pagesize="+pagesize);
		}
		return SUCCESS;
	}
	public void filterTags(List tags) {
		for (Iterator i = tags.iterator(); i.hasNext();) {
			Tag tag = (Tag) i.next();
			if (StringUtils.isNumeric(tag.getName())) {
				i.remove();
			}
		}
	}
	
	public int getTagCount(List tags){
		int icount = 0;
		if(tags.toArray().length>0){
		minTimes = ((Tag)tags.get(0)).getTimes().intValue();
		for (Iterator i = tags.iterator(); i.hasNext();) {
			Tag tag = (Tag) i.next();
			icount = icount+tag.getTimes().intValue();
			if (tag.getTimes().intValue() > maxTimes) {
				maxTimes = tag.getTimes().intValue();
			}
			if (tag.getTimes().intValue() < minTimes) {
				minTimes = tag.getTimes().intValue();
			}
		}
		}
		return icount;
	}
	private static Date[] parseDates(String dateStr) {
		Date date = new Date();
		try {
			date = DF.parse(dateStr);
		} catch (ParseException e) {
		}
		return new Date[] { date, new Date(date.getTime() + ONE_DAY) };
	}

	// ~ Methods
	// ================================================================
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public LinkManager getLinkManager() {
		return linkManager;
	}

	public void setLinkManager(LinkManager linkManager) {
		this.linkManager = linkManager;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List getPopularTags() {
		return popularTags;
	}

	public void setPopularTags(List popularTags) {
		this.popularTags = popularTags;
	}

	public Result getTags() {
		return tags;
	}

	public void setTags(Result tags) {
		this.tags = tags;
	}

	public TagsManager getTagsManager() {
		return tagsManager;
	}

	public void setTagsManager(TagsManager tagsManager) {
		this.tagsManager = tagsManager;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getTokind() {
		return tokind;
	}

	public void setTokind(int tokind) {
		this.tokind = tokind;
	}

	public TagLink getTag() {
		return tag;
	}

	public void setTag(TagLink tag) {
		this.tag = tag;
	}

	public Integer getNewWindow() {
		return NewWindow;
	}

	public void setNewWindow(Integer newWindow) {
		NewWindow = newWindow;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public Integer getTagKind() {
		return tagKind;
	}

	public void setTagKind(Integer tagKind) {
		this.tagKind = tagKind;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public int getAverageCount() {
		return averageCount;
	}

	public void setAverageCount(int averageCount) {
		this.averageCount = averageCount;
	}

	public int getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(int usedTimes) {
		this.usedTimes = usedTimes;
	}

	public int getLevelTimes() {
		return levelTimes;
	}

	public void setLevelTimes(int levelTimes) {
		this.levelTimes = levelTimes;
	}

	public int getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(int maxTimes) {
		this.maxTimes = maxTimes;
	}

	public int getMinTimes() {
		return minTimes;
	}

	public void setMinTimes(int minTimes) {
		this.minTimes = minTimes;
	}

	//~ Accessors ======================================================

}
