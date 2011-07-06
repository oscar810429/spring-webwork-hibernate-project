/*
 * @(#)LinkManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.LinkDAO;
import com.painiu.core.service.LinkManager;
import com.painiu.core.model.link.Link;
import com.painiu.core.model.link.PartnerImage;
import com.painiu.core.model.link.PictureLink;
import com.painiu.core.model.link.TagLink;
import com.painiu.core.model.link.TextLinkage;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="LinkManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LinkManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */

@Transactional
public class LinkManagerImpl implements LinkManager{
	//~ Static fields/initializers =============================================
	
	//~ Instance fields ========================================================
	
	private LinkDAO linkDAO;
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getPartnerImage(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getPartnerImage(int start, int limit) {
		return linkDAO.getPartnerImage(start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getPictureLink(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getPictureLink(int start, int limit) {
		return linkDAO.getPictureLink(start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTextLinkage(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getTextLinkage(int start, int limit) {
		return linkDAO.getTextLinkage(start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#setLinkDAO(com.painiu.core.dao.LinkDAO)
	 */
	@NonTransactional
	public void setLinkDAO(LinkDAO linkDAO) {
		this.linkDAO = linkDAO;
	}
	
	//	~ Accessors ==============================================================

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#removeLink(com.painiu.core.model.link.Link)
	 */
	public void removeLink(Link link) {
		linkDAO.removeLink(link);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#saveLink(com.painiu.core.model.link.Link)
	 */
	public void saveLink(Link link) {
		linkDAO.saveLink(link);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getPartnerImage(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public PartnerImage getPartnerImage(String id) {
		return linkDAO.getPartnerImage(id);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getPictureLink(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public PictureLink getPictureLink(String id) {
		return linkDAO.getPictureLink(id);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTextLinkage(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public TextLinkage getTextLinkage(String id) {
		return linkDAO.getTextLinkage(id);
	}


	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getPartnerImage(int)
	 */
	@Transactional(readOnly=true)
	public List getPartnerImage(int amount) {
		return linkDAO.getPartnerImage(amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getPictureLink(int)
	 */
	@Transactional(readOnly=true)
	public List getPictureLink(int amount) {
		return linkDAO.getPictureLink(amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTextLinkage(int)
	 */
	@Transactional(readOnly=true)
	public List getTextLinkage(int amount) {
		return linkDAO.getTextLinkage(amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getRandomTag(int, int)
	 */
	@Transactional(readOnly=true)
	public List getRandomTag(int kind, int amount) {
		return linkDAO.getRandomTag(kind, amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTag(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public TagLink getTag(String id) {
		return linkDAO.getTag(id);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTagAmountByTitle(int, java.lang.String)
	 */
	@Transactional(readOnly=true)
	public int getTagAmountByTitle(int kind, String title) {
		return linkDAO.getTagAmountByTitle(kind, title);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTagList(int, int)
	 */
	@Transactional(readOnly=true)
	public List getTagList(int kind, int amount) {
		return linkDAO.getTagList(kind, amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTagResult(int, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getTagResult(int kind, int start, int limit) {
		return linkDAO.getTagResult(kind, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getLink(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Link getLink(String id) {
		return linkDAO.getLink(id);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.LinkManager#getTagByTitle(int, java.lang.String)
	 */
	@Transactional(readOnly=true)
	public TagLink getTagByTitle(int kind, String title) {
		return linkDAO.getTagByTitle(kind, title);
	}	

}
