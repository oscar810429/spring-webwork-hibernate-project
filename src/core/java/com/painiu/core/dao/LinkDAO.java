/*
 * @(#)LinkDAO.java 2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;
import com.painiu.core.model.link.*;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="LinkDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LinkDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface LinkDAO {
	
	public Link getLink(String id);
	
	public Result getTextLink(int start, int limit); //get the news list;
	
	public Result getImageLink(int start, int limit); //get the image list;
	
	public void saveLink(Link link);
	
	public void removeLink(Link link);
	
	//The textLink type method============================================
	public Result getSystemNews(int start,int limit);
	
	public Result getGroupNews(int start,int limit);
	
	public Result getAdvertisement(int start,int limit);
	
	public Result getImportantNews(int start,int limit);
	
	public Result getTagNews(int start,int limit);
	
	public Result getTextLinkage(int start,int limit);
	
	public List getTextLinkage(int amount);
	
	public TextLinkage getTextLinkage(String id);
	
	//The imageLink type method============================================
	public Result getActivityImage(int start,int limit);
	
	public Result getIndexImage(int start,int limit);
	
	public Result getPartnerImage(int start,int limit);
	
	public List getPartnerImage(int amount);
	
	public PartnerImage getPartnerImage(String id);
	
	public Result getPictureLink(int start,int limit);
	
	public List getPictureLink(int amount);
	
	public PictureLink getPictureLink(String id);
	
//	The tagLink type method============================================
	public Result getTagResult(int kind,int start,int limit);
	
	public TagLink getTag(String id);
	
	public TagLink getTagByTitle(int kind,String title);
	
	public List getTagList(int kind,int amount);
	
	public List getRandomTag(int kind,int amount);
	
	public int getTagAmountByTitle(int kind,String title);
}
