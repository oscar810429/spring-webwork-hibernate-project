/*
 * @(#)LinkAction.java 2009-11-28
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;
import java.util.Date;
import java.util.List;

import com.painiu.Painiu;
import com.painiu.core.model.link.Link;
import com.painiu.core.model.link.PartnerImage;
import com.painiu.core.model.link.PictureLink;
import com.painiu.core.model.link.TagLink;
import com.painiu.core.model.link.TextLinkage;
import com.painiu.core.search.Result;
import com.painiu.webapp.action.BaseAction;

/**
 * <p>
 * <a href="LinkAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LinkAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class LinkAction extends BaseAction{
	//~ Static fields/initializers =============================================
	private static final int PAGE_SIZE = 15;
	private static final int IMAGE_PAGE_SIZE = 8;
	
	//~ Instance fields ========================================================	
	private String id;
	
	private Link link;
	
	private PartnerImage partnerImage;
	
	private PictureLink pictureLink;
	
	private TextLinkage textLinkage;
	
	private TagLink homeTag;
	
	private TagLink indexTag;
	
	private int page = 1;
	
	private Result result;
	
	private List partners;
	
	private List pictureLinks;
	
	private List textLinks;
	
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//----- some link including textLink、pictureLink and partner---
	
	public String editPartnerImage() throws Exception {
		assertParamExists("id", id);
		
		partnerImage = linkManager.getPartnerImage(id);
		
		return SUCCESS;
	}
	
	public String editPictureLink() throws Exception {
		assertParamExists("id", id);		
		pictureLink = linkManager.getPictureLink(id);		
		return SUCCESS;
	}
	
	public String editTextLinkage() throws Exception {
		assertParamExists("id", id);		
		textLinkage = linkManager.getTextLinkage(id);		
		return SUCCESS;
	}
	
	public String partnerImageList() throws Exception {
		result = linkManager.getPartnerImage((page - 1) * IMAGE_PAGE_SIZE, IMAGE_PAGE_SIZE);
		return SUCCESS;
	}
	
	public String pictureLinkList() throws Exception {
		result = linkManager.getPictureLink((page - 1) * IMAGE_PAGE_SIZE, IMAGE_PAGE_SIZE);
		return SUCCESS;
	}
	
	public String textLinkageList() throws Exception {
		result = linkManager.getTextLinkage((page - 1) * PAGE_SIZE, PAGE_SIZE);
		return SUCCESS;
	}
	
	public String savePartnerImage() throws Exception {
		Date now = new Date();
		if (partnerImage.getId() == null) {
			partnerImage.setCreatedDate(now);
			partnerImage.setModifiedDate(now);
			
			linkManager.saveLink(partnerImage);
		} else {
			PartnerImage old = linkManager.getPartnerImage(partnerImage.getId());
			old.setModifiedDate(now);
			old.setExpiredDate(partnerImage.getExpiredDate());
			old.setAlt(partnerImage.getAlt());
			old.setOrder(partnerImage.getOrder());
			old.setSrc(partnerImage.getSrc());
			old.setTitle(partnerImage.getTitle());
			old.setUrl(partnerImage.getUrl());
			
			linkManager.saveLink(old);
		}		
		saveMessage("The partner image has been saved.");		
		return SUCCESS;
	}
	
	public String savePictureLink() throws Exception {
		Date now = new Date();
		if (pictureLink.getId() == null) {
			pictureLink.setCreatedDate(now);
			pictureLink.setModifiedDate(now);
			
			linkManager.saveLink(pictureLink);
		} else {
			PictureLink old = linkManager.getPictureLink(pictureLink.getId());
			old.setModifiedDate(now);
			old.setExpiredDate(pictureLink.getExpiredDate());
			old.setAlt(pictureLink.getAlt());
			old.setOrder(pictureLink.getOrder());
			old.setSrc(pictureLink.getSrc());
			old.setTitle(pictureLink.getTitle());
			old.setUrl(pictureLink.getUrl());
			
			linkManager.saveLink(old);
		}		
		saveMessage("The picture link has been saved.");		
		return SUCCESS;
	}
	
	public String saveTextLinkage() throws Exception {
		Date now = new Date();
		if (textLinkage.getId() == null) {
			textLinkage.setCreatedDate(now);
			textLinkage.setModifiedDate(now);
			
			linkManager.saveLink(textLinkage);
		} else {
			TextLinkage old = linkManager.getTextLinkage(textLinkage.getId());
			old.setModifiedDate(now);
			old.setExpiredDate(textLinkage.getExpiredDate());
			old.setContent(textLinkage.getContent());
			old.setTitle(textLinkage.getTitle());
			old.setOrder(textLinkage.getOrder());
			old.setUrl(textLinkage.getUrl());
			old.setTitle(textLinkage.getTitle());
			old.setUrl(textLinkage.getUrl());
			linkManager.saveLink(old);
		}		
		saveMessage("The picture link has been saved.");		
		return SUCCESS;
	}
	
	
	public String delete() throws Exception {
		assertParamExists("id", id);
		link = linkManager.getLink(id);		
		linkManager.removeLink(link);		
		saveMessage("The item has been deleted.");		
		return SUCCESS;
	}
	
	//----- some tag including homeTag and indexTag---
	public String editHomeTag() throws Exception {
		assertParamExists("id", id);		
		homeTag = linkManager.getTag(id);		
		return SUCCESS;
	}
	
	public String editIndexTag() throws Exception {
		assertParamExists("id", id);		
		indexTag = linkManager.getTag(id);		
		return SUCCESS;
	}
	
	public String homeTagList() throws Exception {
		result = linkManager.getTagResult(Painiu.USER_HOME_TAG, (page - 1) * PAGE_SIZE, PAGE_SIZE);
		return SUCCESS;
	}
	
	public String indexTagList() throws Exception {
		result = linkManager.getTagResult(Painiu.HOME_TAG,(page - 1) * PAGE_SIZE, PAGE_SIZE);
		return SUCCESS;
	}
	
	public String saveHomeTag() throws Exception {
		Date now = new Date();
		if (homeTag.getId() == null) {
			homeTag.setCreatedDate(now);
			homeTag.setModifiedDate(now);
			if(linkManager.getTagAmountByTitle(Painiu.USER_HOME_TAG,homeTag.getTitle()) > 0) {
				saveMessage("Save tag faild, because the tag named "+homeTag.getTitle()+" has been existent.");		
				return INPUT;
			}
			linkManager.saveLink(homeTag);
		} else {
			TagLink old = linkManager.getTag(homeTag.getId());
			old.setModifiedDate(now);
			old.setExpiredDate(homeTag.getExpiredDate());
			old.setWeight(homeTag.getWeight());
			old.setOrder(homeTag.getOrder());
			old.setUrlBlank(homeTag.getUrlBlank());
			old.setTitle(homeTag.getTitle());
			old.setUrl(homeTag.getUrl());
			linkManager.saveLink(old);
		}		
		saveMessage("The home tag has been saved.");		
		return SUCCESS;
	}
	
	public String saveIndexTag() throws Exception {
		Date now = new Date();
		if (indexTag.getId() == null) {
			indexTag.setCreatedDate(now);
			indexTag.setModifiedDate(now);
			if(linkManager.getTagAmountByTitle(Painiu.HOME_TAG,indexTag.getTitle()) > 0) {
				saveMessage("Save tag faild, because the tag named "+indexTag.getTitle()+" has been existent.");		
				return INPUT;
			}
			linkManager.saveLink(indexTag);
		} else {
			TagLink old = linkManager.getTag(indexTag.getId());
			old.setModifiedDate(now);
			old.setExpiredDate(indexTag.getExpiredDate());
			old.setWeight(indexTag.getWeight());
			old.setOrder(indexTag.getOrder());
			old.setUrlBlank(indexTag.getUrlBlank());
			old.setTitle(indexTag.getTitle());
			old.setUrl(indexTag.getUrl());
			linkManager.saveLink(old);
		}		
		saveMessage("The index tag has been saved.");		
		return SUCCESS;
	}
	
	public String links() {
		partners = linkManager.getPartnerImage(20);
		pictureLinks = linkManager.getPictureLink(50);
		textLinks = linkManager.getTextLinkage(100);
		return SUCCESS;
	}
	
	//~ Accessors ==============================================================

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PartnerImage getPartnerImage() {
		return partnerImage;
	}

	public void setPartnerImage(PartnerImage partnerImage) {
		this.partnerImage = partnerImage;
	}

	public PictureLink getPictureLink() {
		return pictureLink;
	}

	public void setPictureLink(PictureLink pictureLink) {
		this.pictureLink = pictureLink;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public TextLinkage getTextLinkage() {
		return textLinkage;
	}

	public void setTextLinkage(TextLinkage textLinkage) {
		this.textLinkage = textLinkage;
	}

	public TagLink getHomeTag() {
		return homeTag;
	}

	public void setHomeTag(TagLink homeTag) {
		this.homeTag = homeTag;
	}

	public TagLink getIndexTag() {
		return indexTag;
	}

	public void setIndexTag(TagLink indexTag) {
		this.indexTag = indexTag;
	}

	public List getPartners() {
		return partners;
	}

	public void setPartners(List partners) {
		this.partners = partners;
	}

	public List getPictureLinks() {
		return pictureLinks;
	}

	public void setPictureLinks(List pictureLinks) {
		this.pictureLinks = pictureLinks;
	}

	public List getTextLinks() {
		return textLinks;
	}

	public void setTextLinks(List textLinks) {
		this.textLinks = textLinks;
	}


}
