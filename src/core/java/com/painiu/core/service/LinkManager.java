/*
 * @(#)LinkManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.LinkDAO;
import com.painiu.core.model.link.Link;
import com.painiu.core.model.link.PartnerImage;
import com.painiu.core.model.link.PictureLink;
import com.painiu.core.model.link.TagLink;
import com.painiu.core.model.link.TextLinkage;
import com.painiu.core.search.Result;
/**
 * <p>
 * <a href="LinkManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LinkManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface LinkManager {
	
	public void setLinkDAO(LinkDAO linkDAO);
	
	public Result getPartnerImage(int start,int limit);
	
	public Result getPictureLink(int start,int limit);
	
	public Result getTextLinkage(int start,int limit);
	
	public List getPartnerImage(int amount);
	
	public List getPictureLink(int amount);
	
	public List getTextLinkage(int amount);
	
	public void saveLink(Link link);
	
	public void removeLink(Link link);
	
	public Link getLink(String id);
	
	public PictureLink getPictureLink(String id);
	
	public TextLinkage getTextLinkage(String id);
	
	public PartnerImage getPartnerImage(String id);
	
	//tag subject
	public Result getTagResult(int kind,int start,int limit);
	
	
	public TagLink getTag(String id);
	
	public List getTagList(int kind,int amount);
		
	public List getRandomTag(int kind,int amount);
	
	public int getTagAmountByTitle(int kind,String title);
	
	public TagLink getTagByTitle(int kind, String title);
	
}
